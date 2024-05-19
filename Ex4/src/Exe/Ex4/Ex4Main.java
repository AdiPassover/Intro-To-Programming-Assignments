package Exe.Ex4;
import java.awt.Color;

import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.gui.Ex4;

/**
 * A very simple main class for basic code for Ex4
 * 
 * t2: output:
 * GUIShape,0,true,1,Circle2D,3.0,4.0, 2.0
 * GUIShape,255,false,2,Circle2D,6.0,8.0, 4.0
 * 
 * @author boaz.ben-moshe
 *
 */
public class Ex4Main {

	public static void main(String[] args) {
		t1();
		//t2();
		// t3(); // won't work "out of the box" - requires editing the code (save, load..)
	}
	// Minimal empty frame (no shapes)
	public static void t1() {
		Ex4 ex4 = Ex4.getInstance();
		ex4.show();
	} 
	// Two simple circles
	public static void t2() {
		Ex4 ex4 = Ex4.getInstance();
		ShapeCollectionable shapes = ex4.getShape_Collection();
		/*Point2D p1 = new Point2D(3,4);
		Point2D p2 = new Point2D(6,8);
		Circle2D c1 = new Circle2D(p1,2);
		Circle2D c2 = new Circle2D(p2,4);
		GUI_Shapeable gs1 = new GUIShape(c1, true, Color.black, 1);
		GUI_Shapeable gs2 = new GUIShape(c2, false, Color.blue, 2);
		
		double[] x3 = {0,10,15,20,20,5};
		double[] y3 = {0,5,15,0,20,20};
		for (int i = 0; i < x3.length; i++) {
			x3[i] = x3[i]/3;
			y3[i] = y3[i]/3;
		}
		Polygon2D poly3 = new Polygon2D(x3,y3);
		GUI_Shapeable gs3 = new GUIShape(poly3, true, Color.green, 3);
		
		Point2D pa = new Point2D(2, 3);
		Point2D pb = new Point2D(5, 8);
		Rect2D r4 = new Rect2D(pa,pb);
		GUI_Shapeable gs4 = new GUIShape(r4, true, Color.blue, 4);
		Circle2D circ1 = new Circle2D(pa,0.2);
		Circle2D circ2 = new Circle2D(pb,0.2);
		GUI_Shapeable gsa = new GUIShape(circ1, true, Color.black, 5);
		GUI_Shapeable gsb = new GUIShape(circ2, true, Color.black, 6);
		//System.out.println("minPoint: " + r4.getMinPoint());
		//System.out.println("maxPoint: " + r4.getMaxPoint());
		*/
		Point2D p1 = new Point2D(1,1);
		Point2D p2 = new Point2D(3,1);
		Point2D p3 = new Point2D(1,3);
		Point2D p4 = new Point2D(3,3);
		Rect2D r1 = new Rect2D(p3,p1,p4,p2);
		GUI_Shapeable gs1 = new GUIShape(r1, true, Color.black, 1);
		shapes.add(gs1);
		//shapes.add(gs2);
		//shapes.add(gs3);
		//shapes.add(gs4);
		//shapes.add(gsa);
		//shapes.add(gsb);
		//ex4.init(shapes);
		ex4.show();
		System.out.print(ex4.getInfo());
	}
	// Loads a file from file a0.txt (Circles only).
	public static void t3() {
		Ex4 ex4 = Ex4.getInstance();
		ShapeCollectionable shapes = ex4.getShape_Collection();
		String file = "a0.txt"; //make sure the file is your working directory.
		shapes.load(file);
		ex4.init(shapes);
		ex4.show();
	}

}
