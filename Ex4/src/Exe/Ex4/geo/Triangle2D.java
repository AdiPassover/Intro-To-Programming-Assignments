package Exe.Ex4.geo;

/**
 * This class represents a 2D Triangle in the plane.
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class Triangle2D implements GeoShapeable{
	
	private Point2D p1;
	private Point2D p2;
	private Point2D p3;
	
	public Triangle2D(double x1, double y1, double x2, double y2, double x3, double y3) {
		p1 = new Point2D(x1,y1);
		p2 = new Point2D(x2,y2);
		p3 = new Point2D(x3,y3);
	}
	
	public Triangle2D(Point2D p1, Point2D p2, Point2D p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public Triangle2D(Point2D p1, Segment2D seg) {
		this.p1 = p1;
		this.p2 = seg.getP1();
		this.p3 = seg.getP2();
	}

	public Point2D getP1() {return p1;}
	public Point2D getP2() {return p2;}
	public Point2D getP3() {return p3;}
	
	// checks if the Point p is on the the same side as Point sideP of the segment from pStart to pEnd
	private boolean rightSide(Point2D p, Point2D pStart, Point2D pEnd, Point2D sideP) {
		Segment2D seg = new Segment2D(pStart,pEnd);
		if (seg.getM() == Double.POSITIVE_INFINITY || seg.getM() == Double.NEGATIVE_INFINITY) {
			if (pStart.x() == p.x()) { return true; }
			if (pStart.x() > p.x() && pStart.x() < sideP.x()) { return false; }
			if (pStart.x() < p.x() && pStart.x() > sideP.x()) { return false; }
			return true;
		}
		
		double sideSegY = sideP.x()*seg.getM() + seg.getB();
		int side = 1; // sideP.y() >= sideY
		if (sideSegY > sideP.y()) { side = -1; }
		double pSegY = p.x()*seg.getM() + seg.getB();
		double diff = p.y() - pSegY;
		if (diff*side >= 0)
			return true;
		return false;
	}

	@Override
	public boolean contains(Point2D ot) { // checks if ot is in the correct side of all of the edges
		if (!rightSide(ot,p1,p2,p3)) { return false; }
		if (!rightSide(ot,p2,p3,p1)) { return false; }
		if (!rightSide(ot,p3,p1,p2)) { return false; }
		return true;
	}
	
	// this function uses the distance of a segment (y=mx+b) from a point (x,y) formula:
	// |m*x-y+b| / sqrt(m^2+1)
	@Override
	public double area() { // calcs the are by the formula: a = height*width/2
		Segment2D base = new Segment2D(p2,p3);
		double height;
		if (base.getM() == Double.POSITIVE_INFINITY || base.getM() == Double.NEGATIVE_INFINITY)
			height = Math.abs(p1.x()-p2.x());
		else {
			double numerator = Math.abs(base.getM()*p1.x() - p1.y() + base.getB());
			double denominator = Math.sqrt(base.getM()*base.getM() + 1);			
			height = numerator / denominator; // calcs the width using the distance from p1 to the segment p2 to p3 using the formula
		}
		return (0.5 * height * p2.distance(p3));
	}

	@Override
	public double perimeter() {
		return (p1.distance(p2) + p2.distance(p3) + p3.distance(p1));
	}

	@Override
	public void move(Point2D vec) {
		p1.move(vec);
		p2.move(vec);
		p3.move(vec);
		
	}
	
	@Override
	public String toString() {
		return p1.toString()+", "+p2.toString()+", "+p3.toString();
	}

	@Override
	public GeoShapeable copy() {
		Point2D pa = new Point2D(p1.x(),p1.y());
		Point2D pb = new Point2D(p2.x(),p2.y());
		Point2D pc = new Point2D(p3.x(),p3.y());
		Triangle2D newTri = new Triangle2D(pa,pb,pc);
		return newTri;
	}

	@Override
	public void scale(Point2D center, double ratio) {
		Point2D[] points = {p1,p2,p3};
		for (int i =0; i < points.length; i++)
			points[i].scale(center,ratio);
		
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		Point2D[] points = {p1,p2,p3};
		for (int i =0; i < points.length; i++)
			points[i].rotate(center,angleDegrees);
		
	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] arr = {p1, p2, p3};
		return arr;
	}
	
}
