package Exe.Ex4.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Segment2DTest {
	
	private Segment2D s1 = new Segment2D(50,50,100,100);
	private Segment2D s2 = new Segment2D(25,34,12,47.5);
	private Segment2D s3 = new Segment2D(50,50,200,50);
	private Segment2D s4 = new Segment2D(50,50,50,300);
	
	@Test
	void testContains() {
		Point2D p = new Point2D(51,51);
		assertTrue(s1.contains(p));
		assertFalse(s2.contains(p));
		assertFalse(s3.contains(p));
		assertFalse(s4.contains(p));
		p = new Point2D(70,50);
		assertFalse(s1.contains(p));
		assertFalse(s2.contains(p));
		assertTrue(s3.contains(p));
		assertFalse(s4.contains(p));
		p = new Point2D(50,50);
		assertTrue(s1.contains(p));
		assertFalse(s2.contains(p));
		assertTrue(s3.contains(p));
		assertTrue(s4.contains(p));
	}

	@Test
	void testArea() {
		assertEquals(0,s1.area());
		assertEquals(0,s2.area());
		assertEquals(0,s3.area());
		assertEquals(0,s4.area());
	}

	@Test
	void testPerimeter() {
		assertEquals(100*Math.sqrt(2),s1.perimeter());
		assertEquals(37.48,s2.perimeter(),0.01);
		assertEquals(300,s3.perimeter());
		assertEquals(500,s4.perimeter());
	}

	@Test
	void testMove() {
		Segment2D s = new Segment2D(50,50,100,100);
		s.move(new Point2D(5,-12));
		assertEquals(s1.getM(),s.getM());
		assertEquals(13,s1.getP1().distance(s.getP1()));
		assertEquals(13,s1.getP2().distance(s.getP2()));
	}

	@Test
	void testToString() {
		assertEquals("50.0,50.0, 100.0,100.0",s1.toString());
		assertEquals("25.0,34.0, 12.0,47.5",s2.toString());
		assertEquals("50.0,50.0, 200.0,50.0",s3.toString());
		assertEquals("50.0,50.0, 50.0,300.0",s4.toString());
	}

	@Test
	void testCopy() {
		Segment2D s = (Segment2D)s1.copy();
		assertEquals(s1.getB(),s.getB());
		assertEquals(s1.getM(),s.getM());
		assertTrue(s1.getP1().equals(s.getP1()));
		assertTrue(s1.getP2().equals(s.getP2()));
		s.move(new Point2D(Math.random()*3+1,Math.random()*3+1));
		assertEquals(s1.getM(),s.getM(),0.0000001);
		assertFalse(s1.getP1().equals(s.getP1()));
		assertFalse(s1.getP2().equals(s.getP2()));
	}

	@Test
	void testScale() {
		Segment2D sBig = (Segment2D)s1.copy();
		Segment2D sSmall = (Segment2D)s1.copy();
		sBig.scale(new Point2D(0,0),2);
		sSmall.scale(new Point2D(0,0),0.5);
		assertEquals(s1.perimeter()*2,sBig.perimeter());
		assertEquals(s1.perimeter()*0.5,sSmall.perimeter());
		assertTrue(s1.getP1().x() < sBig.getP1().x());
	}

	@Test
	void testRotate() {
		Segment2D test1 = (Segment2D)s1.copy();
		test1.rotate(new Point2D(0,0),90);
		assertTrue(new Point2D(-50,50).close2equals(test1.getP1()));
		assertTrue(new Point2D(-100,100).close2equals(test1.getP2()));
		
		Segment2D test2 = (Segment2D)s3.copy();
		test2.rotate(new Point2D(50,50),45);
		System.out.println(test2);
		assertTrue(new Point2D(50,50).close2equals(test2.getP1()));
		assertTrue(new Point2D(156.066,156.066).close2equals(test2.getP2(),0.001));
	}

	@Test
	void testGetPoints() {
		Point2D[] arr = s1.getPoints();
		assertTrue(s1.getP1().equals(arr[0]));
		assertTrue(s1.getP2().equals(arr[1]));
		arr = s2.getPoints();
		assertTrue(s2.getP1().equals(arr[0]));
		assertTrue(s2.getP2().equals(arr[1]));
		arr = s3.getPoints();
		assertTrue(s3.getP1().equals(arr[0]));
		assertTrue(s3.getP2().equals(arr[1]));
		arr = s4.getPoints();
		assertTrue(s4.getP1().equals(arr[0]));
		assertTrue(s4.getP2().equals(arr[1]));
	}

}
