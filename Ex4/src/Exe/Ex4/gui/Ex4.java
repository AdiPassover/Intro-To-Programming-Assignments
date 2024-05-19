package Exe.Ex4.gui;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Exe.Ex4.Ex4_Const;
import Exe.Ex4.GUIShape;
import Exe.Ex4.GUI_Shapeable;
import Exe.Ex4.ShapeCollection;
import Exe.Ex4.ShapeCollectionable;
import Exe.Ex4.geo.Circle2D;
import Exe.Ex4.geo.GeoShapeable;
import Exe.Ex4.geo.Point2D;
import Exe.Ex4.geo.Polygon2D;
import Exe.Ex4.geo.Rect2D;
import Exe.Ex4.geo.Segment2D;
import Exe.Ex4.geo.ShapeComp;
import Exe.Ex4.geo.Triangle2D;

/**
 * 
 * |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
 * |=-= Adi Peisach | 326627635 =-=|
 * |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
 * 
 * This class is a simple "inter-layer" connecting (aka simplifying) the
 * StdDraw with the Map class.
 * Written for 101 java course it uses simple static functions to allow a 
 * "Singleton-like" implementation.
 * @author adi.peisach
 *
 */
public class Ex4 implements Ex4_GUI{
	private  ShapeCollectionable _shapes = new ShapeCollection(); // arraylist of the shapes on screens
	private  GUI_Shapeable _gs; // current shape the user is drawing
	private  Color _color = Color.black; // paint color
	private  boolean _fill = true; // is the shape filled
	private  String _mode = ""; // current mode
	private  Point2D _p1; // points the user clicked
	private  Point2D _p2;
	private  boolean finished = false; 
	private  int count = 0; // counting how many shapes the user drew in order to tag them

	private ArrayList<Double> xArr = new ArrayList<Double>(10);
	private ArrayList<Double> yArr = new ArrayList<Double>(10);

	private  static Ex4 _winEx4 = null;
	private Color[] colors = {Color.red,Color.pink,Color.orange,Color.yellow,Color.green,Color.magenta,Color.cyan,Color.blue};

	private Ex4() {
		init(null);
	}
	public void init(ShapeCollectionable s) { // Updates the collection of shapes to g
		if(s==null) {_shapes = new ShapeCollection();}
		else {_shapes = s.copy();}
		GUI_Shapeable _gs = null;
		Polygon2D _pp = null;
		_color = Color.black;
		_fill = true;
		_mode = "";
		count = 0;
		Point2D _p1 = null;
	}
	public void show(double d) { // shows the shapes on screen
		StdDraw_Ex4.setScale(0,d);
		StdDraw_Ex4.show();
		drawShapes();
	}
	public static Ex4 getInstance() { 
		if(_winEx4 ==null) {
			_winEx4 = new Ex4();
		}
		return _winEx4;
	}

	public void drawShapes() {
		StdDraw_Ex4.clear();
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable sh = _shapes.get(i);

			drawShape(sh);
		}
		if(_gs!=null) {drawShape(_gs);}
		StdDraw_Ex4.show();
	}
	private static void drawShape(GUI_Shapeable g) {
		//if (g == null) { System.out.println("g is null in Ex4"); }
		StdDraw_Ex4.setPenColor(g.getColor());
		if(g.isSelected()) {StdDraw_Ex4.setPenColor(Color.gray);}
		GeoShapeable gs = g.getShape();
		boolean isFill = g.isFilled();
		if(gs instanceof Circle2D) {
			Circle2D c = (Circle2D)gs;
			Point2D cen = c.getPoints()[0];
			double rad = c.getRadius();
			if(isFill) {
				StdDraw_Ex4.filledCircle(cen.x(), cen.y(), rad);
			}
			else { 
				StdDraw_Ex4.circle(cen.x(), cen.y(), rad);
			}
		}
		else if(gs instanceof Rect2D) {
			Rect2D rect = (Rect2D)gs;
			rect.sortPoints();
			Point2D r1 = rect.getP1();
			Point2D r2 = rect.getP2();
			Point2D r3 = rect.getP3();
			Point2D r4 = rect.getP4();
			double[] x = {r1.x(),r2.x(),r3.x(),r4.x()};
			double[] y = {r1.y(),r2.y(),r3.y(),r4.y()};
			if(isFill) {
				StdDraw_Ex4.filledPolygon(x,y);
			}
			else { 
				StdDraw_Ex4.polygon(x,y);
			}
		}
		else if(gs instanceof Segment2D) {
			Segment2D seg = (Segment2D)gs;
			Point2D point1 = seg.getPoints()[0];
			Point2D point2 = seg.getPoints()[1];
			StdDraw_Ex4.line(point1.x(), point1.y(), point2.x(), point2.y());
		}
		else if (gs instanceof Triangle2D) {
			Triangle2D tri = (Triangle2D)gs;
			Point2D[] pointsArr = tri.getPoints();
			double[] x = new double [pointsArr.length];
			double[] y = new double [pointsArr.length];
			for (int i = 0; i < pointsArr.length; i++) {
				x[i] = pointsArr[i].x();
				y[i] = pointsArr[i].y();
			}
			if (isFill)
				StdDraw_Ex4.filledPolygon(x,y);
			else
				StdDraw_Ex4.polygon(x,y);
		}
		else if (gs instanceof Polygon2D) {
			Polygon2D poly = (Polygon2D)gs;
			double[] x = poly.getX();
			double[] y = poly.getY();
			if (isFill)
				StdDraw_Ex4.filledPolygon(x,y);
			else
				StdDraw_Ex4.polygon(x,y);
		}
	}
	private void setColor(Color c) {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			if(s.isSelected()) {
				s.setColor(c);
			}
		}
	}
	private void setFill() {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			if(s.isSelected()) {
				s.setFilled(_fill);
			}
		}
	}

	public void actionPerformed(String p) {
		_mode = p;
		if(p.equals("Blue")) {_color = Color.BLUE; setColor(_color);}
		if(p.equals("Red")) {_color = Color.RED; setColor(_color);}
		if(p.equals("Green")) {_color = Color.GREEN; setColor(_color);}
		if(p.equals("White")) {_color = Color.WHITE; setColor(_color);}
		if(p.equals("Black")) {_color = Color.BLACK; setColor(_color);}
		if(p.equals("Yellow")) {_color = Color.YELLOW; setColor(_color);}
		if(p.equals("Fill")) {_fill = true; setFill();}
		if(p.equals("Empty")) {_fill = false; setFill();}
		if(p.equals("Clear")) {
			_shapes.removeAll();
			_gs = null;
			_p1 = null;
			_p2 = null;
			drawShapes();
		}
		
		if(_mode.equals("Save")) {
			File defaultFolder = new File(System.getProperty("user.dir")); // sets the default folder for saving
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(defaultFolder);
		    int status = fileChooser.showSaveDialog(null); // opens a file explorer file for the user
		    if (status == JFileChooser.APPROVE_OPTION) { // if the user selected name and file
		      File selectedFile = fileChooser.getSelectedFile();
		      if (selectedFile.exists()) { // if the name is already taken
		        int overwriteResult = JOptionPane.showConfirmDialog(null, "The file already exists. Do you want to overwrite it?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
		        if (overwriteResult != JOptionPane.YES_OPTION) { return; }
		      }
		      String name = selectedFile.getAbsolutePath();
		      _shapes.save(name); 
		    } 
		}
		if(_mode.equals("Load")) {
			File defaultFolder = new File(System.getProperty("user.dir")); // sets the default folder for loading
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(defaultFolder);
		    int status = fileChooser.showOpenDialog(null); // opens a file explorer file for the user
		    if (status == JFileChooser.APPROVE_OPTION) {
		      File selectedFile = fileChooser.getSelectedFile();
		      String fileName = selectedFile.getAbsolutePath();
		      _shapes.load(fileName);
		    }
		}

		if(_mode.equals("All")) {
			for (int i = 0; i < _shapes.size(); i++)
				_shapes.get(i).setSelected(true);
		}
		if(_mode.equals("Anti")) {
			for (int i = 0; i < _shapes.size(); i++)
				_shapes.get(i).setSelected(!_shapes.get(i).isSelected());
		}
		if(_mode.equals("None")) {
			for (int i = 0; i < _shapes.size(); i++)
				_shapes.get(i).setSelected(false);
		}

		if(_mode.equals("Remove")) {
			for (int i = 0; i < _shapes.size(); i++) {				
				if (_shapes.get(i).isSelected()) {
					_shapes.removeElementAt(i);
					i--;
				}
			}
		}

		if(_mode.contains("By")) {
			ShapeComp comp = new ShapeComp(Ex4_Const.Sort_By_Tag);
			if(_mode.equals("ByTag")) { comp = new ShapeComp(Ex4_Const.Sort_By_Tag); }
			if(_mode.equals("ByAntiTag")) { comp = new ShapeComp(Ex4_Const.Sort_By_Anti_Tag); }
			if(_mode.equals("ByArea")) { comp = new ShapeComp(Ex4_Const.Sort_By_Area); }
			if(_mode.equals("ByAntiArea")) { comp = new ShapeComp(Ex4_Const.Sort_By_Anti_Area); }
			if(_mode.equals("ByPerimeter")) { comp = new ShapeComp(Ex4_Const.Sort_By_Perimeter); }
			if(_mode.equals("ByAntiPerimeter")) { comp = new ShapeComp(Ex4_Const.Sort_By_Anti_Perimeter); }
			if(_mode.equals("ByToString")) { comp = new ShapeComp(Ex4_Const.Sort_By_toString); }
			if(_mode.equals("ByAntiToString")) { comp = new ShapeComp(Ex4_Const.Sort_By_toString); }
			_shapes.sort(comp);
		}
		
		if(_mode.equals("Info")) {
			for (int i = 0; i < _shapes.size(); i++)				
				System.out.println(_shapes.get(i).toString());
		}

		drawShapes();

	}


	public void mouseClicked(Point2D p) {
		System.out.println("Mode: "+_mode+"  "+p);

		if(_mode.equals("Move")) {
			if(_p1==null) {_p1 = new Point2D(p);}
			else {
				_p1 = new Point2D(p.x()-_p1.x(), p.y()-_p1.y());
				move();
				_p1 = null;
			}
		}
		if(_mode.equals("Rotate")) {
			if(_p1==null) {_p1 = new Point2D(p);}
			else {
				double angle = Math.toDegrees(Math.atan2(p.y()-_p1.y(),p.x()-_p1.x()));
				rotate(angle);
				_p1 = null;
			}
		}
		if(_mode.equals("Scale_90%")) {
			_p1 = new Point2D(p);
			scale(0.9);
			_p1 = null;
		}
		if(_mode.equals("Scale_110%")) {
			_p1 = new Point2D(p);
			scale(1.1);
			_p1 = null;
		}

		if(_mode.equals("Point")) {
			select(p);
		}

		if(_mode.equals("Copy")) {
			if(_p1==null) {
				_p1 = new Point2D(p);
			}
			else {
				int length = _shapes.size();
				for (int i = 0; i < length; i++) {
					if (_shapes.get(i).isSelected()) {
						GUI_Shapeable guiShape = _shapes.get(i);
						GeoShapeable shape = guiShape.getShape().copy();
						shape.move(new Point2D(p.x()-_p1.x(), p.y()-_p1.y()));
						GUIShape guis = new GUIShape(shape,guiShape.isFilled(),guiShape.getColor(),guiShape.getTag());
						_shapes.add(guis);
					}
				}
				_gs = null;
				_p1 = null;
			}
		}

		if(_mode.equals("Circle")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(count);
				count++;
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
			}
		}
		if(_mode.equals("Rect")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(count);
				count++;
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
			}
		}
		if(_mode.equals("Segment")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(count);
				count++;
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
			}
		}
		if(_mode.equals("Triangle") || _mode.equals("Triangle2")) {
			if(_gs==null) {
				_p1 = new Point2D(p);
				finished = false;
			}
			else if (!finished) { // if only the first point is selected
				_p2 = new Point2D(p);
				_mode = "Triangle2";
				finished = true;
			}
			else {
				_gs.setColor(_color);
				_gs.setFilled(_fill);
				_gs.setTag(count);
				count++;
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
				_p2 = null;
				finished = false;
				_mode = "Triangle";
			}
		}

		if(_mode.equals("Polygon")) {
			if(_gs==null) {
				xArr = new ArrayList<Double>(10);
				yArr = new ArrayList<Double>(10);
				_p1 = new Point2D(p);
				xArr.add(_p1.x());
				yArr.add(_p1.y());
			}
			else {
				_p1 = new Point2D(p);
				xArr.add(_p1.x());
				yArr.add(_p1.y());
			}
		}

		drawShapes();
	}

	public void mouseMoved(MouseEvent e) {
		if(_p1!=null) {
			double x1 = StdDraw_Ex4.mouseX();
			double y1 = StdDraw_Ex4.mouseY();
			GeoShapeable gs = null;
			Point2D p = new Point2D(x1,y1);
			if(_mode.equals("Circle")) {
				double r = _p1.distance(p);
				gs = new Circle2D(_p1,r);
			}
			if(_mode.equals("Rect")) {
				gs = new Rect2D(_p1,p);
			}
			if(_mode.equals("Segment")) {
				gs = new Segment2D(_p1,p);
			}
			if(_mode.equals("Triangle")) {
				gs = new Segment2D(_p1,p);
			}
			if(_mode.equals("Triangle2")) {
				gs = new Triangle2D(_p1,_p2,p);
			}
			if(_mode.equals("Polygon")) {
				xArr.add(p.x());
				yArr.add(p.y());
				gs = new Polygon2D(xArr,yArr);
				xArr.remove(xArr.size()-1);
				yArr.remove(yArr.size()-1);
			}

			_gs = new GUIShape(gs,false, colors[count%8], 0);
			drawShapes();
		}
	}

	private void select(Point2D p) {
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(g!=null && g.contains(p)) {
				s.setSelected(!s.isSelected());
			}
		}
	}

	private void move() { // moves all of the selected points
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				g.move(_p1);
			}
		}
	}

	private void rotate(double angle) { // rotates all of the selected points
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				g.rotate(_p1,angle);
			}
		}
	}

	private void scale(double ratio) { // scales all of the selected points
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			GeoShapeable g = s.getShape();
			if(s.isSelected() && g!=null) {
				g.scale(_p1,ratio);
			}
		}
	}

	public void mouseRightClicked(Point2D p) {
		finished = true;
		if (_mode.equals("Polygon")) {
			if (_gs != null) {
				_gs = new GUIShape(new Polygon2D(xArr,yArr),_fill, _color,count);
				count++;
				_shapes.add(_gs);
				_gs = null;
				_p1 = null;
				_mode = "Polygon";
				drawShapes();
			}
		}
		else {
			_p1 = null;
			_p2 = null;
			_gs = null;
			if (_mode.equals("Triangle2")) { _mode = "Triangle"; }
			finished = true;
			drawShapes();
		}
	}

	@Override
	public ShapeCollectionable getShape_Collection() {
		return this._shapes;
	}
	@Override
	public void show() {show(Ex4_Const.DIM_SIZE); }
	@Override
	public String getInfo() {
		String ans = "";
		for(int i=0;i<_shapes.size();i++) {
			GUI_Shapeable s = _shapes.get(i);
			ans +=s.toString()+"\n";
		}
		return ans;
	}
}
