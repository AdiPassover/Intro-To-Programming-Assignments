package Exe.Ex4.geo;


/**
 * This class represents a 2D segment on the plane, 
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class Segment2D implements GeoShapeable{
	
	private Point2D p1;
	private Point2D p2;
	
	public Segment2D(double x1, double y1, double x2, double y2) {
		this.p1 = new Point2D(x1,y1);
		this.p2 = new Point2D(x2,y2);
	}
	
	public Point2D getP1() { return p1; }
	public Point2D getP2() { return p2; }
	public double getM() { return (p1.y() - p2.y()) / (p1.x() - p2.x()); } // calcs the slope of the segment
	public double getB() { return p1.y() + (-1)*getM()*p1.x(); } // calcs the b of the segment from the formula y=mx+b
	
	public Segment2D(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	@Override
	public boolean contains(Point2D ot) {
		if (ot.x() == p1.x() && ot.x() == p2.x())
			return true;
		if (ot.x() > Math.max(p1.x(),p2.x()) || ot.x() < Math.min(p1.x(),p2.x()))
			return false;
		return (ot.y() == (getM()*ot.x() + getB()));
	}

	@Override
	public double area() {
		return 0;
	}

	@Override
	public double perimeter() {
		return (p1.distance(p2)*2);
	}

	@Override
	public void move(Point2D vec) {
		p1.move(vec);
		p2.move(vec);
		
	}
	
	@Override
	public String toString() {
		return p1.toString()+", "+p2.toString();
	}

	@Override
	public GeoShapeable copy() {
		Segment2D newSeg = new Segment2D(p1.x(),p1.y(),p2.x(),p2.y());
		return newSeg;
	}

	@Override
	public void scale(Point2D center, double ratio) {
		p1.scale(center,ratio);
		p2.scale(center,ratio);
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		p1.rotate(center, angleDegrees);
		p2.rotate(center, angleDegrees);
	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] arr = {p1,p2};
		return arr;
	}
	
}