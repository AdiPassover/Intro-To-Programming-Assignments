package Exe.Ex4.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Point2DTest {
	Point2D p1 = new Point2D(1,1);
	Point2D p2 = new Point2D(-5,27.3);
	Point2D p3 = new Point2D(20,25);

	@Test
	void testAdd() {
		Point2D test1 = p1.add(p1);
		assertEquals(2,test1.x());
		assertEquals(2,test1.y());
		Point2D test2 = p1.add(p3);
		assertEquals(21,test2.x());
		assertEquals(26,test2.y());
	}

	@Test
	void testToString() {
		assertEquals("1.0,1.0",p1.toString());
		assertEquals("-5.0,27.3",p2.toString());
		assertEquals("20.0,25.0",p3.toString());
	}

	@Test
	void testDistance() {
		assertEquals(Math.sqrt(2),p1.distance());
		assertEquals(27.75,p2.distance(),0.01);
		assertEquals(5*Math.sqrt(41),p3.distance());
	}

	@Test
	void testDistancePoint2D() {
		assertEquals(26.975,p1.distance(p2),0.01);
		assertEquals(30.61,p1.distance(p3),0.01);
		assertEquals(25.10,p2.distance(p3),0.01);
	}

	@Test
	void testEqualsObject() {
		assertTrue(p1.equals(new Point2D(1,1)));
		assertTrue(p2.equals(new Point2D(-5,27.3)));
		assertTrue(p3.equals(new Point2D(20,25)));
		assertFalse(p1.equals(p2)&&p1.equals(p3)&&p2.equals(p3));
	}

	@Test
	void testClose2equalsPoint2DDouble() {
		assertTrue(p1.close2equals(new Point2D(1,1),0.0001));
		assertTrue(p2.close2equals(new Point2D(-5.001,27.301),0.01));
		assertTrue(p3.close2equals(new Point2D(20,25),0.001));
		assertFalse(p1.close2equals(p2,1)&&p1.close2equals(p3,2)&&p2.close2equals(p3,3));
	}

	@Test
	void testClose2equalsPoint2D() {
		assertTrue(p1.close2equals(new Point2D(1,1)));
		assertTrue(new Point2D(-5.00000001,27.2999999999).close2equals(p2));
		assertTrue(p3.close2equals(new Point2D(20.00000001,25.00000001)));
		assertFalse(p1.close2equals(p2)&&p1.close2equals(p3)&&p2.close2equals(p3));
	}

	@Test
	void testVector() {
		assertTrue((p2.vector(new Point2D(0,0))).equals(new Point2D(5,-27.3)));
		assertTrue((p3.vector(new Point2D(20,20))).equals(new Point2D(0,-5)));
	}

	@Test
	void testMove() {
		Point2D p = new Point2D(9,8);
		p.move(p1);
		assertTrue(new Point2D(10,9).equals(p));
		p = new Point2D(-100,850);
		p.move(p3);
		assertTrue(new Point2D(-80,875).equals(p));
		
	}

	@Test
	void testScale() {
		Point2D p = new Point2D(p1);
		p.scale(new Point2D(0,0),2);
		assertEquals(2,p.x());
		assertEquals(2,p.y());
		p = new Point2D(p2);
		p.scale(new Point2D(0,0),0.5);
		assertEquals(-2.5,p.x());
		assertEquals(13.65,p.y());
		p = new Point2D(p3);
		p.scale(new Point2D(20,20),3);
		assertEquals(20,p.x());
		assertEquals(35,p.y());
	}

	@Test
	void testRotate() {
		Point2D p = new Point2D(p1);
		p.rotate(new Point2D(1,1),Math.random()*900-500);
		assertEquals("1.0,1.0",p.toString());
		
		p = new Point2D(p2);
		p.rotate(new Point2D(0,0),90);
		assertEquals(-27.3,p.x(),0.00001);
		assertEquals(-5,p.y(),0.00001);
		
		p = new Point2D(p3);
		p.rotate(new Point2D(15,25),45);
		assertEquals(18.53,p.x(),0.01);
		assertEquals(28.53,p.y(),0.01);
	}

}
