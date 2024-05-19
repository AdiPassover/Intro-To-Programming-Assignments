package Exe.Ex4.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Polygon2DTest {
	double[] x1 = {0,3,6};
	double[] y1 = {50,54,50};
	Polygon2D p1 = new Polygon2D(x1,y1);
	double[] x2 = {0,6,3,-7};
	double[] y2 = {0,0,4,6};
	Polygon2D p2 = new Polygon2D(x2,y2);
	double[] x3 = {0,10,15,20,20,5};
	double[] y3 = {0,5,15,0,20,20};
	Polygon2D p3 = new Polygon2D(x3,y3);
	double[] x4 = {0,2,4,2};
	double[] y4 = {0,2,2,-2};
	Polygon2D p4 = new Polygon2D(x4,y4);
	

	@Test
	void testContains() {
		Point2D p = new Point2D(1,2);
		assertFalse(p1.contains(p));
		assertTrue(p2.contains(p));
		assertTrue(p3.contains(p));
		
		p = new Point2D(-4,4);
		assertFalse(p1.contains(p));
		assertTrue(p2.contains(p));
		assertFalse(p3.contains(p));
		
		p = new Point2D(15,17);
		assertFalse(p1.contains(p));
		assertFalse(p2.contains(p));
		assertTrue(p3.contains(p));
		
		p = new Point2D(3,51);
		assertTrue(p1.contains(p));
		assertFalse(p2.contains(p));
		assertFalse(p3.contains(p));
		
		assertTrue(p4.contains(new Point2D(2,0)));
		assertFalse(p4.contains(new Point2D(2,-3)));
	}

	@Test
	void testArea() {
		assertEquals(12,p1.area());
		assertEquals(35,p2.area());
		assertEquals(237.5,p3.area());
	}

	@Test
	void testPerimeter() {
		assertEquals(16,p1.perimeter());
		assertEquals(16,p1.perimeter());
		assertEquals(16,p1.perimeter());
	}

	@Test
	void testMove() {
		Polygon2D p = new Polygon2D(x2,y2);
		p.move(new Point2D(5,12));
		Point2D[] points1 = p.getPoints();
		Point2D[] points2 = p2.getPoints();
		for (int i = 0; i < points1.length; i++)
			assertEquals(13,points1[i].distance(points2[i]));
		assertEquals(p2.area(),p.area());
		assertEquals(p2.perimeter(),p.perimeter());
	}

	@Test
	void testToString() {
		assertEquals("0.0,0.0, 10.0,5.0, 15.0,15.0, 20.0,0.0, 20.0,20.0, 5.0,20.0",p3.toString());
	}

	@Test
	void testCopy() {
		Polygon2D p = new Polygon2D(x2,y2);
		Point2D[] points1 = p.getPoints();
		Point2D[] points2 = p2.getPoints();
		for (int i = 0; i < points1.length; i++)
			assertTrue(points1[i].equals(points2[i]));
		assertEquals(p2.area(),p.area());
		assertEquals(p2.perimeter(),p.perimeter());
		
		p.move(new Point2D(0.2,5));
		points1 = p.getPoints();
		for (int i = 0; i < points1.length; i++)
			assertFalse(points1[i].equals(points2[i]));
		assertEquals(p2.area(),p.area(),0.000000001);
		assertEquals(p2.perimeter(),p.perimeter());
	}

	@Test
	void testScale() {
		Polygon2D pBig = (Polygon2D)p3.copy();
		Polygon2D pSmall = (Polygon2D)p3.copy();
		pBig.scale(new Point2D(-1,-1),2);
		pSmall.scale(new Point2D(-1,-1),0.5);
		assertEquals(p3.area()*4,pBig.area());
		assertEquals(p3.area()*0.25,pSmall.area());
		assertTrue(p3.getPoints()[0].x() < pBig.getPoints()[0].x());
		assertTrue(p3.getPoints()[0].x() > pSmall.getPoints()[0].x());
	}

	@Test
	void testRotate() {
		Polygon2D test1 = (Polygon2D)p1.copy();
		test1.rotate(new Point2D(0,0),90);
		Point2D[] arr = test1.getPoints();
		assertTrue(arr[0].close2equals(new Point2D(-50,0)));
		assertTrue(arr[1].close2equals(new Point2D(-54,3)));
		assertTrue(arr[2].close2equals(new Point2D(-50,6)));
		
		Polygon2D test2 = (Polygon2D)p3.copy();
		test2.rotate(new Point2D(1,1),45);
		System.out.println(test2);
		arr = test2.getPoints();
		assertTrue(arr[0].close2equals(new Point2D(1,-0.41),0.01));
		assertTrue(arr[1].close2equals(new Point2D(4.53,10.19),0.01));
		assertTrue(arr[2].close2equals(new Point2D(1,20.8),0.01));
		assertTrue(arr[3].close2equals(new Point2D(15.14,13.72),0.01));
		assertTrue(arr[4].close2equals(new Point2D(1,27.87),0.01));
		assertTrue(arr[5].close2equals(new Point2D(-9.61,17.26),0.01));
	}

	@Test
	void testGetPoints() {
		Point2D[] points = p3.getPoints();
		for (int i = 0; i < points.length; i++) {
			assertEquals(x3[i],points[i].x());
			assertEquals(y3[i],points[i].y());
		}
	}

}
