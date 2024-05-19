package Exe.Ex4.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.stream;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import Exe.Ex4.GUIShape;
import Exe.Ex4.GUI_Shapeable;
import Exe.Ex4.ShapeCollection;
import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Segment2D;
import Exe.Ex4.geo.Triangle2D;
import Exe.Ex4.gui.Ex4;

class Ex4Test {
	private Circle2D circ1 = new Circle2D(new Point2D(5,12), 10);
	private GUI_Shapeable circ = new GUIShape(circ1,true,new Color(256),0);
	private Segment2D seg1 = new Segment2D(-1,2,10,12);
	private GUI_Shapeable seg = new GUIShape(seg1,true,new Color(22),5);
	private Triangle2D tri1 = new Triangle2D(0,4,8,5,5,1);
	private GUI_Shapeable tri = new GUIShape(tri1,false,new Color(200),56);
	private Rect2D rect1 = new Rect2D(8,5,6,7);
	private GUI_Shapeable rect = new GUIShape(rect1,false,new Color(75),567);
	double[] xArr = {0,10,15,20,20,5};
	double[] yArr = {0,5,15,0,20,20};
	private Polygon2D poly1 = new Polygon2D(xArr,yArr);
	private GUI_Shapeable poly = new GUIShape(poly1,true,new Color(-58),5678);
	
	private ShapeCollection shapes = new ShapeCollection();
	private Ex4 ex4 = Ex4.getInstance();

	@Test
	void testInit() {
		shapes.add(seg);
		shapes.add(circ);
		shapes.add(rect);
		ex4.init(shapes);
		//System.out.println(ex4.getShape_Collection().get(0).getShape().getPoints()[0]);
		assertTrue(new Point2D(-1,2).equals(ex4.getShape_Collection().get(0).getShape().getPoints()[0]));
		assertTrue(new Point2D(5,12).equals(ex4.getShape_Collection().get(1).getShape().getPoints()[0]));
		assertTrue(new Point2D(6,5).equals(ex4.getShape_Collection().get(2).getShape().getPoints()[0]));
	}

	@Test
	void testGetShape_Collection() {
		shapes.add(circ);
		shapes.add(poly);
		shapes.add(seg);
		shapes.add(tri);
		ex4.init(shapes);
		assertEquals(circ.toString(),ex4.getShape_Collection().get(0).toString());
		assertEquals(poly.toString(),ex4.getShape_Collection().get(1).toString());
		assertEquals(seg.toString(),ex4.getShape_Collection().get(2).toString());
		assertEquals(tri.toString(),ex4.getShape_Collection().get(3).toString());
	}

	@Test
	void testGetInfo() {
		shapes.add(seg);
		ex4.init(shapes);
		String test1 = ex4.getInfo();
		assertEquals(seg.toString()+"\n",test1);
		shapes.add(circ);
		ex4.init(shapes);
		String test2 = ex4.getInfo();
		assertEquals(seg.toString()+"\n"+circ.toString()+"\n",test2);
	}

}
