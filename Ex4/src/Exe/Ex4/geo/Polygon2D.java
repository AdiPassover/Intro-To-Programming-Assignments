package Exe.Ex4.geo;

import java.util.ArrayList;

/**
 * This class represents a 2D polygon, as in https://en.wikipedia.org/wiki/Polygon
 * This polygon can be assumed to be simple in terms of area and contains.
 * 
 * You should update this class!
 * @author boaz.benmoshe
 *
 */
public class Polygon2D implements GeoShapeable{

	private double[] x;
	private double[] y;

	public Polygon2D(double[] newX, double[] newY) {
		x = new double [newX.length];
		y = new double [newY.length];
		for (int i = 0; i < newX.length; i++) {			
			x[i] = newX[i];
			y[i] = newY[i];
		}
	}
	
	public Polygon2D(ArrayList<Double> newX, ArrayList<Double> newY) {
		x = new double [newX.size()];
		y = new double [newY.size()];
		for (int i = 0; i < x.length; i++) {			
			x[i] = newX.get(i);
			y[i] = newY.get(i);
		}
	}

	public Polygon2D(Point2D[] points) {
		double[] x = new double [points.length];
		double[] y = new double [points.length];
		for (int i = 0; i < points.length; i++) {
			x[i] = points[i].x();
			y[i] = points[i].y();
		}
		this.x = x;
		this.y = y;
	}
	
	public double[] getX() { return x; }
	public double[] getY() { return y; }

	private boolean inBounds(double num, double min, double max) { return (num >= min) && (num < max); }
	
	// this function works by drawing a line upwards from the point.
	// if the point is inside the polygon, the line should intersect with the lines of the polygon an odd number of times.
	@Override
	public boolean contains(Point2D ot) {
		int count = 0; // counts how many edges the line intersects
		for (int i = 0; i < x.length-1; i++) {
			if (ot.y() <= Math.max(y[i],y[i+1])) { // goes over all of the lines in order to see if the point is in the correct X coords.
				if (inBounds(ot.x(),Math.min(x[i],x[i+1]),Math.max(x[i],x[i+1]))) {					
					Segment2D seg = new Segment2D(x[i],y[i],x[i+1],y[i+1]);
					double segY = seg.getM()*ot.x() + seg.getB();
					if (ot.y() <= segY) { count++; } // checks if the segment is above the point
				}
			}
		}
		
		int l = x.length-1;
		if (ot.y() <= Math.max(y[0],y[l])) { // does the same thing for the last line
			if (inBounds(ot.x(),Math.min(x[0],x[l]),Math.max(x[0],x[l]))) {					
				Segment2D seg = new Segment2D(x[0],y[0],x[l],y[l]);
				double segY = seg.getM()*ot.x() + seg.getB();
				if (ot.y() <= segY) { count++; }
			}
		}

		return (count % 2 != 0);
	}

	// calculates the are according to the simple polygon formula: https://en.wikipedia.org/wiki/Polygon
	@Override
	public double area() {
		double sum = 0;
		for (int i = 0; i < x.length-1; i++) {
			sum += x[i]*y[i+1] - x[i+1]*y[i];
		}
		sum += x[x.length-1]*y[0] - x[0]*y[x.length-1];
		return Math.abs(sum/2);
	}

	@Override
	public double perimeter() {
		double sum = 0;
		for (int i = 1; i < x.length; i++) {
			Point2D p1 = new Point2D (x[i-1],y[i-1]);
			Point2D p2 = new Point2D (x[i],y[i]);
			sum += p1.distance(p2);
		}
		Point2D p1 = new Point2D (x[0],y[0]);
		Point2D p2 = new Point2D (x[x.length-1],y[x.length-1]);
		sum += p1.distance(p2);
		return sum;
	}

	@Override
	public void move(Point2D vec) {
		for (int i = 0; i < x.length; i++) {
			x[i] += vec.x();
			y[i] += vec.y();
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < x.length; i++) {
			str += x[i]+","+y[i];
			if (i+1 != x.length)
				str += ", ";
		}
		return str;
	}

	@Override
	public GeoShapeable copy() {
		double[] xNew = new double [x.length];
		double[] yNew = new double [y.length];
		for (int i = 0; i < x.length; i++) {
			xNew[i] = x[i];
			yNew[i] = y[i];
		}
		Polygon2D newPoly = new Polygon2D(xNew,yNew);
		return newPoly;
	}

	@Override
	public void scale(Point2D center, double ratio) {
		Point2D[] points = new Point2D [x.length];
		for (int i = 0; i < points.length; i++)
			points[i] = new Point2D(x[i],y[i]);
		for (int i = 0; i < points.length; i++) {
			points[i].scale(center,ratio);
			x[i] = points[i].x();
			y[i] = points[i].y();
		}
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		Point2D[] points = new Point2D [x.length];
		for (int i = 0; i < points.length; i++)
			points[i] = new Point2D(x[i],y[i]);
		for (int i = 0; i < points.length; i++) {
			points[i].rotate(center, angleDegrees);
			x[i] = points[i].x();
			y[i] = points[i].y();
		}

	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] arr = new Point2D [x.length];
		for (int i = 0; i < arr.length; i++) {
			Point2D p = new Point2D(x[i],y[i]);
			arr[i] = p;
		}
		return arr;
	}
	
	public String getClassString() { return "Polygon2D"; }

}
