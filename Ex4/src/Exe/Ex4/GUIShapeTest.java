package Exe.Ex4;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.GeoShapeable;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Segment2D;
import Exe.Ex4.geo.Triangle2D;

class GUIShapeTest {
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

	@Test
	void testGetShape() {
		assertEquals(circ1.toString(),circ.getShape().toString());
		assertEquals(seg1.toString(),seg.getShape().toString());
		assertEquals(poly1.toString(),poly.getShape().toString());
	}

	@Test
	void testIsFilled() {
		assertTrue(circ.isFilled());
		assertTrue(seg.isFilled());
		assertFalse(tri.isFilled());
	}

	@Test
	void testSetFilled() {
		assertTrue(circ.isFilled());
		circ.setFilled(false);
		assertFalse(circ.isFilled());

		assertFalse(tri.isFilled());
		tri.setFilled(true);
		assertTrue(tri.isFilled());
	}

	@Test
	void testGetColor() {
		assertEquals(new Color(-58).getRGB(),poly.getColor().getRGB());
		assertEquals(new Color(200).getRGB(),tri.getColor().getRGB());
		assertEquals(new Color(75).getRGB(),rect.getColor().getRGB());
	}

	@Test
	void testSetColor() {
		int col = (int)(Math.random()*500-250);
		circ.setColor(new Color(col));
		assertEquals(new Color(col).getRGB(),circ.getColor().getRGB());
		col = (int)(Math.random()*1750-904);
		seg.setColor(new Color(col));
		assertEquals(new Color(col).getRGB(),seg.getColor().getRGB());
	}

	@Test
	void testGetTag() {
		assertEquals(0,circ.getTag());
		assertEquals(5678,poly.getTag());
		assertEquals(56,tri.getTag());
	}

	@Test
	void testSetTag() {
		int tag = (int)(Math.random()*500-250);
		circ.setTag(tag);
		assertEquals(tag,circ.getTag());
		tag = (int)(Math.random()*1750-904);
		seg.setTag(tag);
		assertEquals(tag,seg.getTag());
	}

	@Test
	void testCopy() {
		GUIShape test1 = (GUIShape)circ.copy();
		assertEquals(circ.getColor(),test1.getColor());
		assertEquals(circ.getTag(),test1.getTag());
		assertEquals(circ.isFilled(),test1.isFilled());
		assertEquals(circ.getShape().toString(),test1.getShape().toString());
		
		test1.setFilled(!circ.isFilled());
		test1.setColor(new Color(((int)(260+Math.random()*700))));
		test1.setTag((int)(1+Math.random()*500));
		assertNotEquals(circ.getColor(),test1.getColor());
		assertNotEquals(circ.getTag(),test1.getTag());
		assertNotEquals(circ.isFilled(),test1.isFilled());
		assertEquals(circ.getShape().toString(),test1.getShape().toString());
	}

	@Test
	void testToString() {
		GeoShapeable c = new Circle2D(new Point2D(2.390625,8.65625), 1.2740843832435118);
		GUIShape test1 = new GUIShape(c,true,new Color(65280),0);
		GeoShapeable r = new Rect2D(0.578125,6.640625,0.578125,8.515625,5.078125,8.515625,5.078125,6.640625);
		GUIShape test2 = new GUIShape(r,true,new Color(255),0);
		GeoShapeable t = new Triangle2D(4.15625,8.765625,0.90625,4.78125,5.984375,7.65625);
		GUIShape test3 = new GUIShape(t,true,new Color(16711680),0);
		assertEquals("GUIShape,-16711936,true,0,Circle2D,2.390625,8.65625, 1.2740843832435118",test1.toString());
		assertEquals("GUIShape,-16776961,true,0,Rect2D,0.578125,6.640625, 0.578125,8.515625, 5.078125,8.515625, 5.078125,6.640625",test2.toString());
		assertEquals("GUIShape,-65536,true,0,Triangle2D,4.15625,8.765625, 0.90625,4.78125, 5.984375,7.65625",test3.toString());
	}

	@Test
	void testIsSelected() {
		assertFalse(circ.isSelected());
		assertFalse(rect.isSelected());
		assertFalse(seg.isSelected());
		assertFalse(poly.isSelected());
		poly.setSelected(!poly.isSelected());
		assertTrue(poly.isSelected());
		
	}

	@Test
	void testSetSelected() {
		assertFalse(poly.isSelected());
		poly.setSelected(!poly.isSelected());
		assertTrue(poly.isSelected());
		poly.setSelected(false);
		assertFalse(poly.isSelected());
	}

	@Test
	void testSetShape() {
		circ.setShape(seg1);
		assertEquals(seg1.toString(),circ.getShape().toString());
		seg.setShape(circ1);
		assertEquals(circ1.toString(),seg.getShape().toString());
	}

}
