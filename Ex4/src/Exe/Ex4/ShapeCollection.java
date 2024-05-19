package Exe.Ex4;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFileChooser;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Exe.Ex4.geo.GeoShapeable;
import Exe.Ex4.geo.*;

/**
 * This class represents a collection of GUI_Shape.
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class ShapeCollection implements ShapeCollectionable{
	private ArrayList<GUI_Shapeable> _shapes;

	public ShapeCollection() {
		_shapes = new ArrayList<GUI_Shapeable>();
	}
	public ShapeCollection(ArrayList<GUI_Shapeable> list) {
		_shapes = list;
	}

	@Override
	public GUI_Shapeable get(int i) {
		return _shapes.get(i);
	}

	@Override
	public int size() {
		return _shapes.size();
	}

	@Override
	public GUI_Shapeable removeElementAt(int i) {
		if (_shapes == null || i >= _shapes.size() || i < 0 || _shapes.get(i) == null) { return null; }
		return _shapes.remove(i);
	}

	@Override
	public void addAt(GUI_Shapeable s, int i) {
		if (_shapes != null && i <= _shapes.size() && i >= 0)
			_shapes.add(i,s);
	}
	@Override
	public void add(GUI_Shapeable s) {
		if(s!=null && s.getShape()!=null) {
			_shapes.add(s);
		}
	}
	@Override
	public ShapeCollectionable copy() {
		if (_shapes == null) { return null; }
		ArrayList<GUI_Shapeable> copy = new ArrayList<GUI_Shapeable>(_shapes.size());
		for (int i = 0; i < _shapes.size(); i++)
			copy.add(i,_shapes.get(i));
		return new ShapeCollection(copy);
	}
	@Override
	public void sort(Comparator<GUI_Shapeable> comp) {
		_shapes.sort(comp);
	}

	@Override
	public void removeAll() {
		if (_shapes == null) { return; }
		while (!_shapes.isEmpty())
			_shapes.remove(0);
	}

	@Override
	public void save(String file) {
		try {
			FileWriter fw = new FileWriter(file); // opens the file
			PrintWriter outs = new PrintWriter(fw);
			for (int i = 0; i < size(); i++) {				
				outs.println(_shapes.get(i).toString()); // writes the string of every shape in a new line in the file
			}
			outs.close();
			fw.close();
		}
		catch(IOException e) {
			System.out.print("Error writing file\n" + e);
			System.exit(2);
		}
	}

	@Override
	public void load(String file) {
		try {
			//System.out.println(System.getProperty("user.dir"));
			//System.out.println(file);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str;

			ShapeCollection backup = (ShapeCollection)copy(); // in case of loading an invalid file
			removeAll();

			str = br.readLine();
			GUI_Shapeable gs = convertLineToShape(str);
			_shapes.add(gs); // reads the first line, converts it to a shape and adds it to the list
			while (str != null) { // goes over the rest of the file
				str = br.readLine();
				if (str != null) {
					gs = convertLineToShape(str);
					if (gs != null) // if the shape is null we don't need to add it to the list
						_shapes.add(gs);
					if (size()-1>=0&&_shapes.get(size()-1) == null) { _shapes.remove(size()-1); } // if we somehow added a null shape, we'll delete it
				}
			}
			br.close();
			/*if (size() == 0) {
				System.out.println("invalid file failure");
				for (int i = 0; i < backup.size(); i++) { add(backup.get(i)); }
				throw new IOException("Invalid file");
			}*/
			//printShapes();
		}
		catch(IOException e) {
			System.out.print("Error reading file\n" + e);
			System.exit(2);
		}
	}
	@Override
	public Rect2D getBoundingBox() {
		Rect2D ans = null;
		Point2D firstP = _shapes.get(0).getShape().getPoints()[0];
		double minX = firstP.x();
		double maxX = firstP.x();
		double minY = firstP.y();
		double maxY = firstP.y();
		for (int i = 0; i < _shapes.size(); i++) { // goes over all of the shapes in the list
			GUI_Shapeable gs = _shapes.get(i);
			if (gs != null) {
				GeoShapeable shape = gs.getShape();
				Point2D[] points = shape.getPoints();
				if (shape instanceof Circle2D) { // if the shape is a circle we'll change the points array to the bounding box of the circle
					Point2D center = points[0];
					double r = points[0].distance(points[1]);
					points[0] = new Point2D(center.x()-r,center.y()-r);
					points[1] = new Point2D(center.x()+r,center.y()+r);
				}
				for (int j = 0; j < points.length; j++) { // goes over the points array in order to find the min and max coords
					minX = Math.min(minX,points[j].x());
					maxX = Math.max(maxX,points[j].x());
					minY = Math.min(minY,points[j].y());
					maxY = Math.max(maxY,points[j].y());
				}
				//System.out.println("minPoint: "+)
			}
		}
		ans = new Rect2D(new Point2D(minX,minY), new Point2D(maxX,maxY)); // creates a rect with the min and max coords
		return ans;
	}
	@Override
	public String toString() {
		String ans = "";
		for(int i=0;i<size();i=i+1) {
			ans += this.get(i);
		}
		return ans;
	}

	public GUI_Shapeable convertLineToShape(String str) { // converts a String line in a saved file to a shape
		String[] data = str.split(",");
		if (!data[0].equals("GUIShape") || data.length < 5) { return null; } // if the first string isn't "GUIShape", then it's illegal
		Color color = new Color(Integer.parseInt(data[1]));
		if (color == null) { return null; }
		boolean isFill = Boolean.parseBoolean(data[2]);
		int tag = Integer.parseInt(data[3]); // saves the features of the the GUIShape
		GeoShapeable shape = null;
		if (data[4].equals("Circle2D")) { // if the shape is a circle, we need both points and radius so we'll deal with that
			Point2D center = new Point2D(Double.parseDouble(data[5]),Double.parseDouble(data[6]));
			shape = new Circle2D(center, Double.parseDouble(data[7]));
		}
		else { // otherwise, we only need points so we'll collect the points in an array
			Point2D[] points = new Point2D [(data.length-5)/2];
			int count = 5;
			for (int i = 0; i < points.length; i++) { 
				points[i] = new Point2D(Double.parseDouble(data[count]),Double.parseDouble(data[count+1]));
				count += 2;
			}
			if (points.length == 2) { shape = new Segment2D(points[0],points[1]); } // create a shape according to the number of points
			else if (points.length == 3) { shape = new Triangle2D(points[0],points[1],points[2]); }
			else if (points.length == 4) { shape = new Rect2D(points[0],points[1],points[2],points[3]); }
			else { shape = new Polygon2D(points); }
		}
		GUI_Shapeable gs = new GUIShape(shape,isFill,color,tag); // finally we'll create a GUIShape with the features we saved
		return gs;
	}

	private void printShapes() {
		for (int i = 0; i < size(); i++)
			System.out.println(get(i));
	}


}
