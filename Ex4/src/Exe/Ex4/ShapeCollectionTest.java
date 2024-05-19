package Exe.Ex4;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.GeoShapeable;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Segment2D;
import Exe.Ex4.geo.ShapeComp;
import Exe.Ex4.geo.Triangle2D;

class ShapeCollectionTest {
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

	@Test
	void testRemoveElementAt() {
		shapes.add(circ);
		shapes.add(poly);
		shapes.add(seg);
		shapes.add(rect);
		shapes.removeElementAt(2);
		for (int i = 0; i < shapes.size(); i++)
			assertFalse(shapes.get(i).getShape() instanceof Segment2D);
		shapes.removeElementAt(2);
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(1).getShape() instanceof Polygon2D);
	}

	@Test
	void testAddAt() {
		shapes.add(seg);
		shapes.add(circ);
		shapes.add(poly);
		shapes.addAt(rect,1);
		shapes.addAt(tri,2);
		
		assertTrue(shapes.get(0).getShape() instanceof Segment2D);
		assertTrue(shapes.get(1).getShape() instanceof Rect2D);
		assertTrue(shapes.get(2).getShape() instanceof Triangle2D);
		assertTrue(shapes.get(3).getShape() instanceof Circle2D);
		assertTrue(shapes.get(4).getShape() instanceof Polygon2D);
	}

	@Test
	void testCopy() {
		shapes.add(circ);
		shapes.add(poly);
		shapes.add(seg);
		shapes.add(rect);
		
		ShapeCollectionable shapes1 = shapes.copy();
		for (int i = 0; i < shapes.size(); i++)
			assertTrue(shapes.get(i).getShape().toString().equals(shapes1.get(i).getShape().toString()));
		shapes1.removeElementAt(0);
		assertFalse(shapes1.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
	}

	@Test
	void testSort() {
		shapes.add(circ);
		shapes.add(rect);
		shapes.add(poly);
		
		shapes.sort(new ShapeComp(Ex4_Const.Sort_By_Area));
		assertTrue(shapes.get(2).getShape() instanceof Circle2D);
		assertTrue(shapes.get(1).getShape() instanceof Polygon2D);
		assertTrue(shapes.get(0).getShape() instanceof Rect2D);
		shapes.sort(new ShapeComp(Ex4_Const.Sort_By_Anti_Area));
		assertTrue(shapes.get(0).getShape() instanceof Circle2D);
		assertTrue(shapes.get(1).getShape() instanceof Polygon2D);
		assertTrue(shapes.get(2).getShape() instanceof Rect2D);
		
		shapes.sort(new ShapeComp(Ex4_Const.Sort_By_Perimeter));
		assertTrue(shapes.get(2).getShape() instanceof Polygon2D);
		assertTrue(shapes.get(1).getShape() instanceof Circle2D);
		assertTrue(shapes.get(0).getShape() instanceof Rect2D);
		shapes.sort(new ShapeComp(Ex4_Const.Sort_By_Anti_Perimeter));
		assertTrue(shapes.get(0).getShape() instanceof Polygon2D);
		assertTrue(shapes.get(1).getShape() instanceof Circle2D);
		assertTrue(shapes.get(2).getShape() instanceof Rect2D);
	}

	@Test
	void testRemoveAll() {
		shapes.add(seg);
		shapes.add(circ);
		shapes.add(rect);
		shapes.removeAll();
		assertEquals(0,shapes.size());
		for (int i = 0; i < 100; i++) { shapes.add(seg); }
		shapes.removeAll();
		assertEquals(0,shapes.size());
	}

	@Test
	void testSave() {
		shapes.add(seg);
		shapes.add(circ);
		shapes.add(poly);
		shapes.add(rect);
		shapes.add(tri);
		shapes.save("C:\\Users\\adipe\\eclipse-workspace\\Ex4\\test1");
		try {
			File file = new File("C:\\Users\\adipe\\eclipse-workspace\\Ex4\\test1");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str;
			
			str = br.readLine();
			assertEquals(seg.toString(),str);
			str = br.readLine();
			assertEquals(circ.toString(),str);
			str = br.readLine();
			assertEquals(poly.toString(),str);
			str = br.readLine();
			assertEquals(rect.toString(),str);
			str = br.readLine();
			assertEquals(tri.toString(),str);
			str = br.readLine();
			assertTrue(str == null);
			
			br.close();
		}
		catch(IOException e) {
			System.out.print("Error reading file\n" + e);
			System.exit(2);
			fail("Error reading file\n" + e);
		}
	}

	@Test
	void testLoad() {
		shapes.load("C:\\Users\\adipe\\eclipse-workspace\\Ex4\\a5");
		GeoShapeable c = new Circle2D(new Point2D(2.390625,8.65625), 1.2740843832435118);
		GUIShape gsCircle = new GUIShape(c,true,new Color(65280),0);
		assertEquals(gsCircle.getColor(),shapes.get(0).getColor());
		assertEquals(gsCircle.getTag(),shapes.get(0).getTag());
		assertEquals(gsCircle.getShape().toString(),shapes.get(0).getShape().toString());
		
		GeoShapeable r = new Rect2D(0.578125,6.640625,0.578125,8.515625,5.078125,8.515625,5.078125,6.640625);
		GUIShape gsRect = new GUIShape(r,true,new Color(255),0);
		assertEquals(gsRect.getColor(),shapes.get(1).getColor());
		assertEquals(gsRect.getTag(),shapes.get(1).getTag());
		assertEquals(gsRect.getShape().toString(),shapes.get(1).getShape().toString());
		
		GeoShapeable t = new Triangle2D(4.15625,8.765625,0.90625,4.78125,5.984375,7.65625);
		GUIShape gsTri = new GUIShape(t,true,new Color(16711680),0);
		assertEquals(gsTri.getColor(),shapes.get(2).getColor());
		assertEquals(gsTri.getTag(),shapes.get(2).getTag());
		assertEquals(gsTri.getShape().toString(),shapes.get(2).getShape().toString());
		
		GeoShapeable s = new Segment2D(1.140625,9.609375, 3.90625,5.609375);
		GUIShape gsSeg = new GUIShape(s,true,new Color(-256),0);
		assertEquals(gsSeg.getColor(),shapes.get(3).getColor());
		assertEquals(gsSeg.getTag(),shapes.get(3).getTag());
		assertEquals(gsSeg.getShape().toString(),shapes.get(3).getShape().toString());
		
		assertEquals(4,shapes.size());
	}

	@Test
	void testGetBoundingBox() {
		shapes.add(rect);
		shapes.add(poly);
		Rect2D box = shapes.getBoundingBox();
		assertTrue(box.getMinPoint().equals(new Point2D(0,0)));
		assertTrue(box.getMaxPoint().equals(new Point2D(20,20)));
		shapes.add(circ);
		box = shapes.getBoundingBox();
		assertTrue(box.getMinPoint().equals(new Point2D(-5,0)));
		assertTrue(box.getMaxPoint().equals(new Point2D(20,22)));
	}

	@Test
	void testConvertLineToShape() {
		String str1 = "GUIShape,65280,true,0,Circle2D,2.390625,8.65625, 1.2740843832435118";
		String str2 = "GUIShape,255,true,0,Rect2D,0.578125,6.640625,0.578125,8.515625,5.078125,8.515625,5.078125,6.640625";
		String str3 = "GUIShape,16711680,true,0,Triangle2D,4.15625,8.765625,0.90625,4.78125,5.984375,7.65625";
		String str4 = "GUIShape,16776960,true,1,Segment2D,1.734375,7.71875,1.796875,3.59375";
		
		GeoShapeable c = new Circle2D(new Point2D(2.390625,8.65625), 1.2740843832435118);
		GUIShape gsCircle = new GUIShape(c,true,new Color(65280),0);
		GUI_Shapeable circFromStr = shapes.convertLineToShape(str1);
		assertEquals(gsCircle.getColor(),circFromStr.getColor());
		assertEquals(gsCircle.getTag(),circFromStr.getTag());
		assertEquals(gsCircle.getShape().toString(),circFromStr.getShape().toString());
		
		GeoShapeable r = new Rect2D(0.578125,6.640625,0.578125,8.515625,5.078125,8.515625,5.078125,6.640625);
		GUIShape gsRect = new GUIShape(r,true,new Color(255),0);
		GUI_Shapeable rectFromStr = shapes.convertLineToShape(str2);
		assertEquals(gsRect.getColor(),rectFromStr.getColor());
		assertEquals(gsRect.getTag(),rectFromStr.getTag());
		assertEquals(gsRect.getShape().toString(),rectFromStr.getShape().toString());
		
		GeoShapeable t = new Triangle2D(4.15625,8.765625,0.90625,4.78125,5.984375,7.65625);
		GUIShape gsTri = new GUIShape(t,true,new Color(16711680),0);
		GUI_Shapeable triFromStr = shapes.convertLineToShape(str3);
		assertEquals(gsTri.getColor(),triFromStr.getColor());
		assertEquals(gsTri.getTag(),triFromStr.getTag());
		assertEquals(gsTri.getShape().toString(),triFromStr.getShape().toString());
		

		GeoShapeable s = new Segment2D(1.734375,7.71875,1.796875,3.59375);
		GUIShape gsSeg = new GUIShape(s,true,new Color(16776960),1);
		GUI_Shapeable segFromStr = shapes.convertLineToShape(str4);
		assertEquals(gsSeg.getColor(),segFromStr.getColor());
		assertEquals(gsSeg.getTag(),segFromStr.getTag());
		System.out.println(segFromStr.getShape().getClass());
		assertTrue(segFromStr.getShape() instanceof Segment2D);
		assertEquals(gsSeg.getShape().toString(),segFromStr.getShape().toString());
	}

}
