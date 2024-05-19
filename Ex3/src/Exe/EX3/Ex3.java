package Exe.EX3;

import java.awt.Color;

/**
 * Adi Peisach | ID: 326627635
 * 
 * This class is a simple "inter-layer" connecting (aka simplifing) the
 * StdDraw_Ex3 with the Map2D interface.
 * Written for 101 java course it uses simple static functions to allow a 
 * "Singleton-like" implementation.
 */
public class Ex3 {
	private static  Map2D _map = null; // The map that is presented in the GUI
	private static Color _color = Color.BLACK; // current color
	private static String _mode = ""; // current mode
	public static final int WHITE = Color.WHITE.getRGB();
	public static final int BLACK = Color.BLACK.getRGB();
	
	private static Point2D point1 = null; // Some function require 2 points, so this variable save the first.

	public static void main(String[] args) {
		int dim = 10;  // init matrix (map) 10*10
		init(dim);
	}
	private static void init(int x) {
		StdDraw_Ex3.clear();
		_map = new MyMap2D(x);
		StdDraw_Ex3.setScale(-0.5, _map.getHeight()-0.5);
		StdDraw_Ex3.enableDoubleBuffering();
		_map.fill(WHITE);
		drawArray(_map);		
	}
	
	/*
	 * This function prints the starting grid on screen. 
	 */
	 public static void drawGrid(Map2D map) {
		 int w = map.getWidth();
		 int h = map.getHeight();
		 for(int i=0;i<w;i++) {
			 StdDraw_Ex3.line(i, 0, i, h);
		 }
		 for(int i=0;i<h;i++) {
			 StdDraw_Ex3.line(0, i, w, i);
		 }
	}
	/*
	* This function goes over the map and prints it on the screen.
	* In other words, this function updates the screen.
	*/
	static public void drawArray(Map2D a) {
		StdDraw_Ex3.clear();
		StdDraw_Ex3.setPenColor(Color.gray);
		drawGrid(_map);
		for(int y=0;y<a.getWidth();y++) {
			for(int x=0;x<a.getHeight();x++) {
				int c = a.getPixel(x, y);
				StdDraw_Ex3.setPenColor(new Color(c));
				drawPixel(x,y);
			}
		}		
		StdDraw_Ex3.show();
	}
	/*
	* This function is called each time the user selects an action.
	* In case the user chose a screen size or color, we'll change those features.
	* In case the user chose a mode, we'll change the mode accordingly.
	*/
	public static void actionPerformed(String p) {
		_mode = p;
		if(p.equals("Blue")) {_color = Color.BLUE; }
		if(p.equals("White")) {_color = Color.WHITE; }
		if(p.equals("Black")) {_color = Color.BLACK; }
		if(p.equals("Red")) {_color = Color.RED; }
		if(p.equals("Green")) {_color = Color.GREEN; }
		if(p.equals("Yellow")) {_color = Color.YELLOW; }
		
		if(p.equals("Clear")) {_map.fill(WHITE);}
		if(p.equals("20x20")) {init(20);}
		if(p.equals("40x40")) {init(40);}
		if(p.equals("80x80")) {init(80);}
		if(p.equals("160x160")) {init(160);}
		
		point1 = null; // restart point1 every time a new action is clicked

		drawArray(_map);
		
	}
	/*
	 * This function is called each time the mouse is clicked on the map.
	 * This function checks which action to perform using mode, and acts accordingly.
	 */
	public static void mouseClicked(Point2D p) {
		System.out.println(p);
		int col = _color.getRGB();
		if(_mode.equals("Point")) {
			_map.setPixel(p,col );
		}
		if(_mode.equals("Fill")) {
			/*
			 * This function works by converting the map to a int matrix where:
			 * 0: filled points
			 * -1: same color as starting point
			 * -2: different color from starting point
			 * This function goes over each 0 and fills every legal neighbor by converting him to 0.
			 * This process continue until there are no changes made.
			 */
			System.out.println("Tiles filled: " + _map.fill(p, col));
			_mode = "none";
		}
		if(_mode.equals("Gol")) {
			/*
			 * This function simulates a generation in the game of life by following the game's rules.
			 * In order to keep the previous gen unchanged while we create the next gen, we'll create a new map.
			 * The new map will be initialized as an entirely dead map.
			 * Now we'll go over each point in the original map to see who's alive in the next gen:
			 * 1. If an alive point has 2 or 3 neighbors, it stays alive.
			 * 2. If a dead point has 3 neighbors, it's revived.
			 * All of the other points die or stay dead.
			 */
			_map.nextGenGol();	
		}
		if(_mode.equals("ShortestPath")) {
			if (point1 == null)
				point1 = p; // if the first point isn't chosen yet, choose it.
			else {
				/*
				 * This function works by converting the map to a "heat map".
				 * The heat map is represented as an int matrix where:
				 * -1: not explored yet
				 * -999: obstacle
				 * and every other number is the distance from the starting point.
				 * This function loops until it finds the distance from p1 to p2.
				 * Each time, it goes over all of the points with the greatest distance.
				 * For each point, the function checks if the neighbors are passable.
				 * If they are, they will be converted to the distance+1.
				 * 
				 * After we have the heat map, we can take the point at p2's place in order to get the path length.
				 * In order to create an array for the path we need to start from the end and find the previous tile each
				 * time until we get to the starting point.
				 */
				Point2D[] points = _map.shortestPath(point1,p);
				if (points != null)
					printShortestPath(points,col);
				else
					System.out.println("No legal path from "+point1.toStringInt()+" to "+p.toStringInt());
				
				point1 = null;
				_mode = "none";
			}
		}
		if(_mode.equals("Rect")) {
			if (point1 == null)
				point1 = p; // if the first point isn't chosen yet, choose it.
			else {
				/*
				 * This function works by finding all of the edges of the rectangle.
				 * Our two parameter points are two edges: (x1,y1), (x2,y2)
				 * The other edges will be: (x1,y2), (x2,y1)
				 * We'll start from the upper left edge and color each point until the bottom right edge.
				 */
				_map.drawRect(point1, p, col);
				point1 = null;
				_mode = "none";
			}
		}
		if(_mode.equals("Segment")) {
			if (point1 == null)
				point1 = p; // if the first point isn't chosen yet, choose it.
			else {
				/*
				 * This function works by using the linear line formula: y = mx + b
				 * We are given 2 points (x1,y1),(x2,y2) as parameters.
				 * We can find m and b using those parameters in the following formulas:
				 * m = (y1-y2)/(x1-x2)
				 * b = y1 + (-1)*m*x1 = y2 + (-1)*m*x2 (it doesn't matter which point we use so we'll use the first)
				 * 
				 * Now we have 2 possibilities:
				 * (1) Each line has one point.(width > height)
				 * (2) Each column has one point. (width < height)
				 * In (1) We'll calculate the y value for each line by y = mx + b.
				 * In (2) We'll calculate the x value for each column by x = (y-b) / m.
				 * Each time we'll find the closest point to the function point and color it.
				 */
				_map.drawSegment(point1, p, col);
				point1 = null;
				_mode = "none";
			}
		}
		if(_mode.equals("Circle")) {
			if (point1 == null)
				point1 = p; // if the first point isn't chosen yet, choose it.
			else {
				double radius = point1.distance(p);
				/*
				 * This function works by using the definition of the circle:
				 * A circle is a group of all of the points with a distance of radius from a single point.
				 * We are given the center point and the radius as parameters.
				 * The function goes over each point in the map and checks if its' distance from the center is lower 
				 * than radius.
				 * If it is, we'll color it.
				 */
				_map.drawCircle(point1, radius, col);
				point1 = null;
				_mode = "none";
			}
		}
		
		drawArray(_map);
	}
	static private void drawPixel(int x, int y) {
		StdDraw_Ex3.filledCircle(x, y, 0.3);
	}
	
	public static void printShortestPath(Point2D[] points, int color) {
		System.out.print("ShortestPath: "+points[0].toStringInt()+" --> ");
		System.out.print(points[points.length-1].toStringInt());
		System.out.println(" length: "+points.length);
		for (int i = 0; i < points.length; i++) {
			_map.setPixel(points[i],color);
			System.out.println(i + ") " + points[i]);
		}
	}
}
