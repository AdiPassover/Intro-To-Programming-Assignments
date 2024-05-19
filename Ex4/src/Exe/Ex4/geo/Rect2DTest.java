package Exe.Ex4.geo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

class Rect2DTest {
	
	private Rect2D rect1 = new Rect2D(50,50,100,100);
	private Rect2D rect2 = new Rect2D(25.4,57.34,29.4,71.56);
	private Rect2D rect3 = new Rect2D(70,10,69.99,10.01);
	private Rect2D rect4 = new Rect2D(0,0,-2,2,6,6,4,8);

	@Test
	void testContains() {
		Point2D p = new Point2D(75,75);
		assertTrue(rect1.contains(p));
		p = new Point2D(27.01,60);
		assertTrue(rect2.contains(p));
		p = new Point2D(69.999,10.01);
		assertTrue(rect3.contains(p));
		assertFalse(rect1.contains(p));
		p = new Point2D(0,2);
		assertTrue(rect4.contains(p));
	}

	@Test
	void testArea() {
		assertEquals(2500,rect1.area());
		assertEquals(56.88,rect2.area(),0.001);
		assertEquals(0.0001,rect3.area(),0.001);
		assertEquals(24,rect4.area());
	}

	@Test
	void testPerimeter() {
		assertEquals(200,rect1.perimeter());
		assertEquals(36.44,rect2.perimeter());
		assertEquals(0.04,rect3.perimeter(),0.001);
	}

	@Test
	void testMove() {
		Point2D vec = new Point2D(25,-20);
		Rect2D rect = new Rect2D(50,50,100,100);
		rect.move(vec);
		assertEquals(rect1.area(),rect.area());
		assertEquals(rect1.perimeter(),rect.perimeter());
		Point2D p1 = new Point2D(75,30);
		assertEquals(0,p1.distance(rect.getMinPoint()));
		p1 = new Point2D(125,80);
		assertEquals(0,p1.distance(rect.getMaxPoint()));
	}

	@Test
	void testToString() {
		assertEquals("50.0,50.0, 100.0,100.0, 50.0,100.0, 100.0,50.0",rect1.toString());
		assertEquals("25.4,57.34, 29.4,71.56, 25.4,71.56, 29.4,57.34",rect2.toString());
		assertEquals("69.99,10.0, 70.0,10.01, 69.99,10.01, 70.0,10.0",rect3.toString());
	}

	@Test
	void testCopy() {
		Rect2D rect = (Rect2D)rect2.copy();
		assertEquals(rect2.area(),rect.area());
		assertEquals(rect2.perimeter(),rect.perimeter());
		assertEquals(0,rect2.getMinPoint().distance(rect.getMinPoint()));
		assertEquals(0,rect2.getMaxPoint().distance(rect.getMaxPoint()));
		rect.move(new Point2D(Math.random()*3+1,Math.random()*3+1));
		assertEquals(rect2.area(),rect.area(),0.000001);
		assertEquals(rect2.perimeter(),rect.perimeter(),0.00001);
		assertNotEquals(0,rect2.getMinPoint().distance(rect.getMinPoint()),0.00001);
		assertNotEquals(0,rect2.getMaxPoint().distance(rect.getMaxPoint()),0.00001);
	}

	@Test
	void testScale() {
		Rect2D rBig = (Rect2D)rect1.copy();
		Rect2D rSmall = (Rect2D)rect1.copy();
		rBig.scale(new Point2D(0,0),2);
		rSmall.scale(new Point2D(0,0),0.5);
		assertEquals(rect1.area()*4,rBig.area());
		assertEquals(rect1.area()*0.25,rSmall.area());
		assertTrue(rect1.getMinPoint().x() < rBig.getMinPoint().x());
		assertTrue(rect1.getMinPoint().x() > rSmall.getMinPoint().x());
	}

	@Test
	void testRotate() {
		Rect2D r = (Rect2D)rect1.copy(); //Rect2D(50,50,100,100);
		r.rotate(new Point2D(0,0),-90);
		assertEquals(r.getP1().x(),r.getP4().x(),0.0001);
		assertEquals(r.getP2().x(),r.getP3().x(),0.0001);
		assertEquals(r.getP1().y(),r.getP2().y(),0.0001);
		assertEquals(r.getP3().y(),r.getP4().y(),0.0001);
		assertEquals(rect1.area(),r.area(),0.001);
		
		Rect2D test2 = (Rect2D)rect1.copy();
		test2.rotate(new Point2D(75,75),90);
		assertTrue(new Point2D(100,50).close2equals(test2.getP1()));
		System.out.println(test2.getP2());
		assertTrue(new Point2D(50,50).close2equals(test2.getP2()));
		assertTrue(new Point2D(50,100).close2equals(test2.getP3()));
		assertTrue(new Point2D(100,100).close2equals(test2.getP4()));
		assertEquals(rect1.area(),test2.area(),0.001);
	}

	@Test
	void testGetPoints() {
		Point2D[] arr1 = rect1.getPoints();
		assertTrue(arr1[0].equals(rect1.getMinPoint()));
		assertTrue(arr1[1].equals(rect1.getMaxPoint()));
		arr1 = rect2.getPoints();
		assertTrue(arr1[0].equals(rect2.getMinPoint()));
		assertTrue(arr1[1].equals(rect2.getMaxPoint()));
	}

}
