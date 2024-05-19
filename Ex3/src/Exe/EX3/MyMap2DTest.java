package Exe.EX3;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class MyMap2DTest {
	static final int WHITE = Color.WHITE.getRGB();
	static final int BLACK = Color.BLACK.getRGB();
	static final int BLUE = Color.BLUE.getRGB();
	
	@Test
	void testDrawSegment() {
		int[][] arr1 = {{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE}};
		MyMap2D map = new MyMap2D(arr1);
		Point2D p1 = new Point2D(0,0);
		Point2D p2 = new Point2D(2,2);
		map.drawSegment(p1,p2,BLACK);
		int[][] ans1 = {{BLACK,WHITE,WHITE},{WHITE,BLACK,WHITE},{WHITE,WHITE,BLACK}};
		MyMap2D ansMap1 = new MyMap2D(ans1);
		assertTrue(equals(ansMap1,map));
		
		int[][] arr2 = {{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE}};
		map = new MyMap2D(arr2);
		p1 = new Point2D(0,0);
		p2 = new Point2D(3,2);
		map.drawSegment(p1,p2,BLACK);
		int[][] ans2 = {{BLACK,WHITE,WHITE},{WHITE,BLACK,WHITE},{WHITE,BLACK,WHITE},{WHITE,WHITE,BLACK}};
		MyMap2D ansMap2 = new MyMap2D(ans2);
		assertTrue(equals(ansMap2,map));
		
		MyMap2D map2 = new MyMap2D(100,100);
		map2.fill(WHITE);
		p1 = new Point2D(Math.random()*100,Math.random()*100);
		p2 = new Point2D(Math.random()*100,Math.random()*100);
		int minX = Math.min(p1.ix(),p2.ix());
		int minY = Math.min(p1.iy(),p2.iy());
		int maxX = Math.max(p1.ix(),p2.ix());
		int maxY = Math.max(p1.iy(),p2.iy());
		map2.drawSegment(p1, p2, BLUE);
		for (int i = 0; i < map2.getWidth(); i++) {
			for (int j = 0; j < map2.getHeight(); j++) {
				if (i > maxX && i < minX && j > maxY && j < minY && map2.getPixel(i,j) == BLUE)
					fail();
			}
		}
		
	}

	@Test
	void testDrawRect() {
		int[][] arr1 = {{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE},{WHITE,WHITE,WHITE}};
		MyMap2D map = new MyMap2D(arr1);
		Point2D p1 = new Point2D(0,0);
		Point2D p2 = new Point2D(1,1);
		map.drawRect(p1,p2,BLACK);
		int[][] ans1 = {{BLACK,BLACK,WHITE},{BLACK,BLACK,WHITE},{WHITE,WHITE,WHITE}};
		MyMap2D ansMap1 = new MyMap2D(ans1);
		assertTrue(equals(ansMap1,map));
		
		MyMap2D map2 = new MyMap2D(100,100);
		map2.fill(WHITE);
		p1 = new Point2D(Math.random()*100,Math.random()*100);
		p2 = new Point2D(Math.random()*100,Math.random()*100);
		int minX = Math.min(p1.ix(),p2.ix());
		int minY = Math.min(p1.iy(),p2.iy());
		int maxX = Math.max(p1.ix(),p2.ix());
		int maxY = Math.max(p1.iy(),p2.iy());
		map2.drawRect(p1, p2, BLUE);
		for (int i = 0; i < map2.getWidth(); i++) {
			for (int j = 0; j < map2.getHeight(); j++) {
				if (i < maxX && i > minX && j < maxY && j > minY && map2.getPixel(i,j) != BLUE)
					fail();
			}
		}
	}

	@Test
	void testDrawCircle() {
		MyMap2D map1 = new MyMap2D(80,80);
		map1.fill(WHITE);
		Point2D p1 = new Point2D(40,40);
		map1.drawCircle(p1, 20, BLACK);
		for (int i = 0; i < map1.getWidth(); i++) {
			for (int j = 0; j < map1.getHeight(); j++) {
				Point2D current = new Point2D(i,j);
				if (map1.getPixel(current) == BLACK && current.distance(p1)>21)
					fail();
				if (map1.getPixel(current) == WHITE && current.distance(p1)<20)
					fail();
			}
		}
	}

	@Test
	void testFillPoint2DInt() {
		int[][] arr = {{BLACK,WHITE,WHITE},{BLACK,BLACK,WHITE},{BLACK,BLACK,WHITE},{WHITE,WHITE,WHITE}};
		int[][] ans1 = {{BLUE,WHITE,WHITE},{BLUE,BLUE,WHITE},{BLUE,BLUE,WHITE},{WHITE,WHITE,WHITE}};
		int[][] ans2 = {{BLACK,BLUE,BLUE},{BLACK,BLACK,BLUE},{BLACK,BLACK,BLUE},{BLUE,BLUE,BLUE}};
		MyMap2D map1 = new MyMap2D(arr);
		MyMap2D map2 = new MyMap2D(arr);
		MyMap2D ansMap1 = new MyMap2D(ans1);
		MyMap2D ansMap2 = new MyMap2D(ans2);
		Point2D p1 = new Point2D(1,0);
		Point2D p2 = new Point2D(0,2);
		int count1 = map1.fill(p1,BLUE);
		int count2 = map2.fill(p2,BLUE);
		assertTrue(equals(ansMap1,map1));
		assertTrue(equals(ansMap2,map2));
		assertEquals(4,count1);
		assertEquals(6,count2);
	}

	@Test
	void testFillIntIntInt() {
		int[][] arr = {{BLACK,WHITE,WHITE},{BLACK,BLACK,WHITE},{BLACK,BLACK,WHITE},{WHITE,WHITE,WHITE}};
		int[][] ans1 = {{BLUE,WHITE,WHITE},{BLUE,BLUE,WHITE},{BLUE,BLUE,WHITE},{WHITE,WHITE,WHITE}};
		int[][] ans2 = {{BLACK,BLUE,BLUE},{BLACK,BLACK,BLUE},{BLACK,BLACK,BLUE},{BLUE,BLUE,BLUE}};
		MyMap2D map1 = new MyMap2D(arr);
		MyMap2D map2 = new MyMap2D(arr);
		MyMap2D ansMap1 = new MyMap2D(ans1);
		MyMap2D ansMap2 = new MyMap2D(ans2);
		int count1 = map1.fill(1,0,BLUE);
		int count2 = map2.fill(0,2,BLUE);
		assertTrue(equals(ansMap1,map1));
		assertTrue(equals(ansMap2,map2));
		assertEquals(4,count1);
		assertEquals(6,count2);
	}

	@Test
	void testFillIntIntIntInt() {
		MyMap2D map1 = new MyMap2D(100,100);
		map1.fill(WHITE);
		Point2D p1 = new Point2D(25,25);
		Point2D p2 = new Point2D(75,75);
		map1.drawRect(p1,p2,BLUE);
		MyMap2D map2 = new MyMap2D(100,100);
		map2.fill(WHITE);
		map2.drawRect(p1,p2,BLACK);
		
		int count = map1.fill(p1.ix(),p1.iy(),BLACK,map1.getPixel(p1));
		assertTrue(equals(map1,map2));
		assertEquals(2600,count);
	}

	@Test
	void testShortestPath() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		Point2D p1 = new Point2D(0,2);
		Point2D p2 = new Point2D(1,1);
		Point2D p3 = new Point2D(0,1);
		Point2D p4 = new Point2D(1,2);
		Point2D[] points1 = {p1,p3};
		Point2D[] points2 = {p1,p4};
		Point2D[] answer = map1.shortestPath(p1,p2);
		assertTrue(equals(points1,answer)||equals(points2,answer));
	}

	@Test
	void testShortestPathDist() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		Point2D p1 = new Point2D(0,2);
		Point2D p2 = new Point2D(1,1);
		assertEquals(2,map1.shortestPathDist(p1,p2));
		assertEquals(map1.shortestPathDist(p1,p2),map1.shortestPathDist(p2,p1));
	}

	@Test
	void testConvertMapToInt() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		int[][] arr1 = map1.convertMapToInt(BLACK);
		int[][] ans1 = {{-999,-1,-1},{-999,-1,-1},{-999,-999,-999}};
		assertTrue(equals(ans1,arr1));
		
		int[][] mapArr2 = {{BLUE,BLACK,WHITE},{WHITE,BLUE,BLUE},{BLACK,BLACK,WHITE}};
		map1 = new MyMap2D(mapArr2);
		int[][] arr2 = map1.convertMapToInt(BLUE);
		int[][] ans2 = {{-1,-999,-999},{-999,-1,-1},{-999,-999,-999}};
		assertTrue(equals(ans2,arr2));
	}

	@Test
	void testFindDistance() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		int[][] arr1 = map1.convertMapToInt(BLACK);
		arr1[1][1] = 0;
		Point2D p1 = new Point2D(0,2);
		map1.findDistance(arr1,p1);
		int[][] ans = {{-999,1,2},{-999,0,1},{-999,-999,-999}};
		assertTrue(equals(arr1,ans));
	}

	@Test
	void testFindPath() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		int[][] arr1 = map1.convertMapToInt(BLACK);
		arr1[1][1] = 0;
		Point2D p1 = new Point2D(0,2);
		map1.findDistance(arr1,p1);
		Point2D[] answer = map1.findPath(arr1,p1);
		Point2D p2 = new Point2D(1,1);
		Point2D p3 = new Point2D(0,1);
		Point2D p4 = new Point2D(1,2);
		Point2D[] points1 = {p2,p3};
		Point2D[] points2 = {p2,p4};
		assertTrue(equals(points1,answer)||equals(points2,answer));
	}

	@Test
	void testNextGenGol() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		MyMap2D map2 = new MyMap2D(mapArr);
		map2.nextGenGol();
		assertTrue(equals(map1,map2));
		
		int[][] mapArr2 = {{WHITE,BLACK,WHITE},{WHITE,BLACK,BLACK},{WHITE,WHITE,WHITE}};
		map2 = new MyMap2D(mapArr2);
		map2.nextGenGol();
		assertTrue(equals(map1,map2));
	}

	@Test
	void testFillInt() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,WHITE},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		map1.fill(BLACK);
		int i,j;
		for (i = 0; i < map1.getHeight(); i++) {
			for (j = 0; j < map1.getHeight(); j++) {
				if (map1.getPixel(i,j) != BLACK)
					fail();
			}
		}
	}

	@Test
	void testIsLegal() {
		MyMap2D map1 = new MyMap2D(2,2);
		assertTrue(map1.isLegal(0,1));
		assertTrue(map1.isLegal(1,1));
		assertFalse(map1.isLegal(-2,0));
		assertFalse(map1.isLegal(1,2));
	}

	@Test
	void testCountNeighbours() {
		int[][] mapArr = {{WHITE,BLACK,BLACK},{WHITE,BLACK,WHITE},{WHITE,WHITE,WHITE}};
		MyMap2D map1 = new MyMap2D(mapArr);
		assertEquals(2,map1.countNeighbours(0,0));
		assertEquals(2,map1.countNeighbours(1,1));
		assertEquals(3,map1.countNeighbours(1,2));
	}
	
	
	boolean equals(MyMap2D map1, MyMap2D map2) {
		for (int i = 0; i < map1.getWidth(); i++) {
			for (int j = 0; j < map1.getHeight(); j++) {
				if (map1.getPixel(i,j) != map2.getPixel(i,j))
					return false;
			}
		}
		return true;
	}
	boolean equals(int[][] map1, int[][] map2) {
		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[i].length; j++) {
				if (map1[i][j] != map2[i][j])
					return false;
			}
		}
		return true;
	}
	boolean equals(Point2D[] map1, Point2D[] map2) {
		for (int i = 0; i < map1.length; i++) {
			if (!map1[i].equals(map2[i]))
				return false;
		}
		return true;
	}
	void printArr(int[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
	void printArr(Point2D[] map) {
		for (int i = 0; i < map.length; i++) {
			System.out.print("("+map[i].ix()+","+map[i].iy()+") ");
		}
		System.out.println();
	}

}
