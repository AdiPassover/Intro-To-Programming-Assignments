package Exe.Ex4.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Triangle2DTest {
	private Triangle2D t1 = new Triangle2D(50,50,60,60,60,50);
	private Triangle2D t2 = new Triangle2D(20,10.5,23,11,30,59);
	private Triangle2D t3 = new Triangle2D(10,10,15,12,19,20);
	private Triangle2D t4 = new Triangle2D(0,0,3,4,6,0);

	@Test
	void testContains() {
		Point2D p = new Point2D(52,51);
		assertTrue(t1.contains(p));
		assertFalse(t2.contains(p));
		assertFalse(t3.contains(p));
		assertFalse(t4.contains(p));
		p = new Point2D(22,12);
		assertFalse(t1.contains(p));
		assertTrue(t2.contains(p));
		assertFalse(t3.contains(p));
		assertFalse(t4.contains(p));
		p = new Point2D(14,12);
		assertFalse(t1.contains(p));
		assertFalse(t2.contains(p));
		assertTrue(t3.contains(p));
		assertFalse(t4.contains(p));
		p = new Point2D(0,0);
		assertFalse(t1.contains(p));
		assertFalse(t2.contains(p));
		assertFalse(t3.contains(p));
		assertTrue(t4.contains(p));
		
	}

	@Test
	void testArea() {
		assertEquals(50,t1.area());
		assertEquals(70.25,t2.area(),0.01);
		assertEquals(12,t4.area());
	}

	@Test
	void testPerimeter() {
		assertEquals(34.14,t1.perimeter(),0.01);
		assertEquals(101.06,t2.perimeter(),0.01);
		assertEquals(16,t4.perimeter());
	}

	@Test
	void testMove() {
		Triangle2D tri = new Triangle2D(50,50,60,60,60,50);
		tri.move(new Point2D(3,4));
		assertEquals(t1.area(),tri.area());
		assertEquals(t1.perimeter(),tri.perimeter());
		assertEquals(5,t1.getP1().distance(tri.getP1()));
		assertEquals(5,t1.getP2().distance(tri.getP2()));
		assertEquals(5,t1.getP3().distance(tri.getP3()));
	}
	
	@Test
	void testToString() {
		assertEquals("50.0,50.0, 60.0,60.0, 60.0,50.0",t1.toString());
		assertEquals("20.0,10.5, 23.0,11.0, 30.0,59.0",t2.toString());
	}

	@Test
	void testCopy() {
		Triangle2D tri = (Triangle2D)t1.copy();
		assertEquals(t1.area(),tri.area());
		assertEquals(t1.perimeter(),tri.perimeter());
		assertTrue(t1.getP1().equals(tri.getP1()));
		assertTrue(t1.getP2().equals(tri.getP2()));
		assertTrue(t1.getP3().equals(tri.getP3()));
		tri.move(new Point2D(Math.random()*3+1,Math.random()*3+1));
		assertEquals(t1.area(),tri.area());
		assertEquals(t1.perimeter(),tri.perimeter());
		assertFalse(t1.getP1().equals(tri.getP1()));
		assertFalse(t1.getP2().equals(tri.getP2()));
		assertFalse(t1.getP3().equals(tri.getP3()));
	}

	@Test
	void testScale() {
		Triangle2D tBig = (Triangle2D)t1.copy();
		Triangle2D tSmall = (Triangle2D)t1.copy();
		tBig.scale(new Point2D(0,0),2);
		tSmall.scale(new Point2D(0,0),0.5);
		assertEquals(t1.area()*4,tBig.area());
		assertEquals(t1.area()*0.25,tSmall.area());
		assertTrue(t1.getP1().x() < tBig.getP1().x());
		assertTrue(t1.getP1().x() > tSmall.getP1().x());
	}

	@Test
	void testRotate() {
		Triangle2D test1 = (Triangle2D)t1.copy();
		test1.rotate(new Point2D(0,0),45);
		Point2D[] arr = test1.getPoints();
		assertTrue(arr[0].close2equals(new Point2D(0,70.71),0.001));
		assertTrue(arr[1].close2equals(new Point2D(0,84.852),0.001));
		assertTrue(arr[2].close2equals(new Point2D(7.071,77.781),0.001));
		
		Triangle2D test2 = (Triangle2D)t4.copy();
		test2.rotate(new Point2D(1,1),90);
		System.out.println(test2);
		arr = test2.getPoints();
		assertTrue(arr[0].close2equals(new Point2D(2,0)));
		assertTrue(arr[1].close2equals(new Point2D(-2,3)));
		assertTrue(arr[2].close2equals(new Point2D(2,6)));
	}

	@Test
	void testGetPoints() {
		Point2D[] arr = t1.getPoints();
		assertTrue(t1.getP1().equals(arr[0]));
		assertTrue(t1.getP2().equals(arr[1]));
		assertTrue(t1.getP3().equals(arr[2]));
		arr = t2.getPoints();
		assertTrue(t2.getP1().equals(arr[0]));
		assertTrue(t2.getP2().equals(arr[1]));
		assertTrue(t2.getP3().equals(arr[2]));
	}

}
