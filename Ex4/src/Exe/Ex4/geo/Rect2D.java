package Exe.Ex4.geo;

/**
 * This class represents a 2D rectangle (NOT necessarily axis parallel - this shape can be rotated!)
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class Rect2D implements GeoShapeable { //TODO: add test with rotated rectangle
	
	private Point2D p1;
	private Point2D p2;
	private Point2D p3;
	private Point2D p4;
		
	public Rect2D(double x1, double y1, double x2, double y2) {
		double minX = Math.min(x1,x2);
		double maxX = Math.max(x1,x2);
		double minY = Math.min(y1,y2);
		double maxY = Math.max(y1,y2);
		this.p1 = new Point2D(minX,minY);
		this.p2 = new Point2D(maxX,maxY);
		this.p3 = new Point2D(minX,maxY);
		this.p4 = new Point2D(maxX,minY);
	}

	public Rect2D(Point2D p1, Point2D p2) {

		this.p1 = new Point2D(Math.min(p1.x(),p2.x()),Math.min(p1.y(),p2.y()));
		this.p2 = new Point2D(Math.max(p1.x(),p2.x()),Math.max(p1.y(),p2.y()));
		this.p3 = new Point2D(Math.min(p1.x(),p2.x()),Math.max(p1.y(),p2.y()));
		this.p4 = new Point2D(Math.max(p1.x(),p2.x()),Math.min(p1.y(),p2.y()));
	}
	
	public Rect2D(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
		this.p1 = new Point2D(p1.x(),p1.y());
		this.p2 = new Point2D(p2.x(),p2.y());
		this.p3 = new Point2D(p3.x(),p3.y());
		this.p4 = new Point2D(p4.x(),p4.y());
		sortPoints();
	
	}
	public Rect2D(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		this.p1 = new Point2D(x1,y1);
		this.p2 = new Point2D(x2,y2);
		this.p3 = new Point2D(x3,y3);
		this.p4 = new Point2D(x4,y4);
		sortPoints();
	}
	
	public Point2D getMinPoint() {
		Point2D[] points = {p2,p3,p4};
		double minX = p1.x();
		double minY = p1.y();
		for (int i = 0; i < 3; i++) {
			minX = Math.min(minX,points[i].x());
			minY = Math.min(minY,points[i].y());
		}
		return new Point2D(minX,minY);
	}
	public Point2D getMaxPoint() {
		Point2D[] points = {p2,p3,p4};
		double maxX = p1.x();
		double maxY = p1.y();
		for (int i = 0; i < 3; i++) {
			maxX = Math.max(maxX,points[i].x());
			maxY = Math.max(maxY,points[i].y());
		}
		return new Point2D(maxX,maxY);
	}
	public Point2D getP1() { return p1; }
	public Point2D getP2() { return p2; }
	public Point2D getP3() { return p3; }
	public Point2D getP4() { return p4; }
	public double getWidth() {
		double minDist = Math.min(Math.min(p1.distance(p2),p1.distance(p3)),p1.distance(p4));
		double maxDist = Math.max(Math.max(p1.distance(p2),p1.distance(p3)),p1.distance(p4));
		double midDist = p1.distance(p2);
		if (midDist == minDist || midDist == maxDist) { midDist = p1.distance(p3); }
		if (midDist == minDist || midDist == maxDist) { midDist = p1.distance(p4); }
		return midDist;
	} 
	public double getHeight() { return Math.min(Math.min(p1.distance(p2),p1.distance(p3)),p1.distance(p4)); }
	
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
		sortPoints();
		if (!rightSide(ot,p1,p2,p3)) { return false; }
		if (!rightSide(ot,p1,p4,p3)) { return false; }
		if (!rightSide(ot,p3,p2,p1 )) { return false; }
		if (!rightSide(ot,p3,p4,p1)) { return false; }
		return true;
	}

	@Override
	public double area() {
		return getWidth()*getHeight();
	}

	@Override
	public double perimeter() {
		return getWidth()*2 + getHeight()*2;
	}

	@Override
	public void move(Point2D vec) {
		p1.move(vec);
		p2.move(vec);
		p3.move(vec);
		p4.move(vec);
	}
	
	@Override
	public String toString() { 
		return p1.toString()+", "+p2.toString()+", "+p3.toString()+", "+p4.toString();
	}

	@Override
	public GeoShapeable copy() {
		Point2D newP1 = new Point2D(p1.x(),p1.y());
		Point2D newP2 = new Point2D(p2.x(),p2.y());
		Point2D newP3 = new Point2D(p3.x(),p3.y());
		Point2D newP4 = new Point2D(p4.x(),p4.y());
		Rect2D newRect = new Rect2D(newP1,newP2,newP3,newP4);
		return newRect;
	}

	@Override
	public void scale(Point2D center, double ratio) {
		Point2D[] points = {p1,p2,p3,p4};
		for (int i = 0; i < points.length; i++)
			points[i].scale(center,ratio);
	}

	@Override
	public void rotate(Point2D center, double angleDegrees) {
		Point2D[] points = {p1,p2,p3,p4};
		for (int i = 0; i < points.length; i++)
			points[i].rotate(center, angleDegrees);
		sortPoints();
		
	}

	@Override
	public Point2D[] getPoints() {
		Point2D[] arr = {getMinPoint(), getMaxPoint()};
		return arr;
	}
	
	public void sortPoints() { // sorts the point so p1 connects to p2 connects to p3 connects to p4 connects to p1
		double slant = max(p1.distance(p2),p1.distance(p3),p1.distance(p4));
		if (p1.distance(p2) == slant) {
			Point2D temp = new Point2D(p3);
			p3 = new Point2D(p2);
			p2 = new Point2D(temp);
		}
		else if (p1.distance(p4) == slant) {
			Point2D temp = new Point2D(p3);
			p3 = new Point2D(p4);
			p4 = new Point2D(temp);
		}
	}
	
	private double min(double a, double b, double c) { return Math.min(Math.min(a,b),c); }
	private double max(double a, double b, double c) { return Math.max(Math.max(a,b),c); }
	private double min(double a, double b, double c, double d) { return Math.min(Math.min(Math.min(a,b),c),d); }
	private double max(double a, double b, double c, double d) { return Math.max(Math.max(Math.max(a,b),c),d); }

}
