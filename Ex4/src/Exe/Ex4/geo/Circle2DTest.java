package Exe.Ex4.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Circle2DTest {
	private Point2D p1 = new Point2D(50,60);
	Circle2D c1 = new Circle2D(p1,10);
	private Point2D p2 = new Point2D(12.001,390.45);
	Circle2D c2 = new Circle2D(p2,31.05);
	private Point2D p3 = new Point2D(100,620);
	Circle2D c3 = new Circle2D(p3,1);

	@Test
	void testToString() {
		assertEquals("50.0,60.0, 10.0",c1.toString());
		assertEquals("12.001,390.45, 31.05",c2.toString());
		assertEquals("100.0,620.0, 1.0",c3.toString());
	}

	@Test
	void testContains() {
		Point2D p = new Point2D(55,55);
		assertTrue(c1.contains(p));
		p = new Point2D(50,50);
		assertTrue(c1.contains(p));
		p = new Point2D(50,49.99);
		assertFalse(c1.contains(p));
	}

	@Test
	void testArea() {
		assertEquals(Math.PI*100,c1.area());
		assertEquals(Math.PI*964.1025,c2.area());
		assertEquals(Math.PI*1,c3.area());
	}

	@Test
	void testPerimeter() {
		assertEquals(Math.PI*20,c1.perimeter());
		assertEquals(Math.PI*62.1,c2.perimeter());
		assertEquals(Math.PI*2,c3.perimeter());
	}

	@Test
	void testMove() {
		Circle2D c = new Circle2D(c1.getCenter(),c1.getRadius());
		c.move(new Point2D(8,15));
		assertEquals(17,c.getCenter().distance(c1.getCenter()));
		assertEquals(c1.getRadius(),c.getRadius());
	}

	@Test
	void testCopy() {
		Circle2D c = (Circle2D)c1.copy();
		assertTrue(c.getCenter().equals(c1.getCenter()));
		assertEquals(c1.getRadius(),c.getRadius());
		c.move(new Point2D(0.1,0.04));
		assertFalse(c.getCenter().equals(c1.getCenter()));
		assertEquals(c1.getRadius(),c.getRadius());
		
		c = (Circle2D)c2.copy();
		assertTrue(c.getCenter().equals(c2.getCenter()));
		assertEquals(c2.getRadius(),c.getRadius());
		c.move(new Point2D(0.1,0.04));
		assertFalse(c.getCenter().equals(c2.getCenter()));
		assertEquals(c2.getRadius(),c.getRadius());
	}

	@Test
	void testGetPoints() {
		Point2D[] arr = c1.getPoints();
		assertTrue(arr[0].equals(c1.getCenter()));
		assertEquals(c1.getRadius(),c1.getCenter().distance(arr[1]));
		
		arr = c2.getPoints();
		assertTrue(arr[0].equals(c2.getCenter()));
		assertEquals(c2.getRadius(),c2.getCenter().distance(arr[1]),0.001);
		
		arr = c3.getPoints();
		assertTrue(arr[0].equals(c3.getCenter()));
		assertEquals(c3.getRadius(),c3.getCenter().distance(arr[1]));
	}

	@Test
	void testScale() {
		Circle2D cBig = (Circle2D)c1.copy();
		Circle2D cSmall = (Circle2D)c1.copy();
		cBig.scale(new Point2D(0,0),2);
		cSmall.scale(new Point2D(0,0),0.5);

		assertEquals(c1.perimeter()*2,cBig.perimeter());
		assertEquals(c1.perimeter()*0.5,cSmall.perimeter());
		assertTrue(c1.getCenter().x() < cBig.getCenter().x());
		assertTrue(c1.getCenter().x() > cSmall.getCenter().x());

	}

	@Test
	void testRotate() {
		Circle2D test1 = (Circle2D)c1.copy();
		test1.rotate(test1.getCenter(),Math.random()*900-450);
		assertTrue(test1.getCenter().equals(c1.getCenter()));
		
		Circle2D test2 = (Circle2D)c3.copy();
		test2.rotate(new Point2D(0,0),90);
		assertTrue(test2.getCenter().close2equals(new Point2D(-620,100)));
	}

}
