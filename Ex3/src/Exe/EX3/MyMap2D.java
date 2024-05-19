package Exe.EX3;

import java.awt.Color;

/**
 * This class implements the Map2D interface.
 * Adi Peisach | ID: 326627635 */
public class MyMap2D implements Map2D{
	private int[][] _map;
	
	public MyMap2D(int w, int h) {init(w,h);}
	public MyMap2D(int size) {this(size,size);}
	
	public MyMap2D(int[][] data) { 
		this(data.length, data[0].length);
		init(data);
	}
	@Override
	public void init(int w, int h) {
		_map = new int[w][h];
		
	}
	@Override
	public void init(int[][] arr) {
		init(arr.length,arr[0].length);
		for(int x = 0;x<this.getWidth()&& x<arr.length;x++) {
			for(int y=0;y<this.getHeight()&& y<arr[0].length;y++) {
				this.setPixel(x, y, arr[x][y]);
			}
		}
	}
	@Override
	public int getWidth() {return _map.length;}
	@Override
	public int getHeight() {return _map[0].length;}
	@Override
	public int getPixel(int x, int y) { return _map[x][y];}
	@Override
	public int getPixel(Point2D p) { 
		return this.getPixel(p.ix(),p.iy());
	}
	
	public void setPixel(int x, int y, int v) {_map[x][y] = v;}
	public void setPixel(Point2D p, int v) { 
		setPixel(p.ix(), p.iy(), v);
	}
	
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
	@Override
	public void drawSegment(Point2D p1, Point2D p2, int v) {
		int x1 = p1.ix();
		int x2 = p2.ix();
		int y1 = p1.iy();
		int y2 = p2.iy();
		
		double m = (double)(y1 - y2) / (double)(x1 - x2);
		double b = y1 + (-1)*m*x1;
		int width = Math.abs(x1-x2);
		int height = Math.abs(y1-y2);
		
		if (width > height) { // Each line has one point.
			for (int x = Math.min(x1,x2); x <= Math.max(x1,x2); x++) {
				double value = m*x+b; // y = mx + b
				double minDist = Math.abs(Math.min(y1,y2)-value);
				int minY = Math.min(y1,y2);
				for (int y = Math.min(y1,y2)+1; y <= Math.max(y1,y2); y++) {
					if (Math.abs(y-value) < minDist) {
						minDist = Math.abs(y-value);
						minY = y;
					}
				}
				setPixel(x,minY,v);
			}
		}
		else { // Each column has one point.
			for (int y = Math.min(y1,y2); y <= Math.max(y1,y2); y++) {
				double value = (y-b)/m; // x = (y-b)/m
				double minDist = Math.abs(Math.min(x1,x2)-value);
				int minX = Math.min(x1,x2);
				for (int x = Math.min(x1,x2)+1; x <= Math.max(x1,x2); x++) {
					if (Math.abs(x-value) < minDist) {
						minDist = Math.abs(x-value);
						minX = x;
					}
				}
				setPixel(minX,y,v);
			}
		}
	}
	
	/*
	 * This function works by finding all of the edges of the rectangle.
	 * Our two parameter points are two edges: (x1,y1), (x2,y2)
	 * The other edges will be: (x1,y2), (x2,y1)
	 * We'll start from the upper left edge and color each point until the bottom right edge.
	 */
	@Override
	public void drawRect(Point2D p1, Point2D p2, int col) {
		int minx = Math.min(p1.ix(),p2.ix());
		int maxx = Math.max(p1.ix(),p2.ix());
		int miny = Math.min(p1.iy(),p2.iy());
		int maxy = Math.max(p1.iy(),p2.iy());
		
		for (int x = minx; x <= maxx; x++) {
			for (int y = miny; y <= maxy; y++) {
				setPixel(x,y,col);
			}
		}
		
	}
	
	/*
	 * This function works by using the definition of the circle:
	 * A circle is a group of all of the points with a distance of radius from a single point.
	 * We are given the center point and the radius as parameters.
	 * The function goes over each point in the map and checks if its' distance from the center is lower 
	 * than radius.
	 * If it is, we'll color it.
	 */
	@Override
	public void drawCircle(Point2D p, double r, int col) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Point2D current = new Point2D(x,y);
				if (current.distance(p) <= r+0.5)
					setPixel(current,col);
			}
		}
	}
	
	/*
	 * This function is called when the map is too big for a recursive function,
	 * This function works by converting the map to a int matrix where:
	 * 0: filled points
	 * -1: same color as starting point
	 * -2: different color from starting point
	 * This function goes over each 0 and fills every legal neighbor by converting him to 0.
	 * This process continue until there are no changes made.
	 */
	@Override
	public int fill(Point2D p, int new_v) {
		if (getPixel(p) == new_v) // in case the point is already filled
			return 0;
		return fill(p.ix(),p.iy(),new_v,getPixel(p));
	}

	@Override
	public int fill(int x, int y, int new_v) {
		if (getPixel(x,y) == new_v) // in case the point is already filled
			return 0;
		return fill(x,y,new_v,getPixel(x,y));
	}
	
	public int fill(int x, int y, int col, int old_v) {
		int[][] map = convertMapToInt(getPixel(x,y));
		map[x][y] = 0;
		int count = 0;
		
		int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
		boolean changes = true;
		while (changes) {
			changes = false;
			for (int i = 0; i < getWidth(); i++) {
				for (int j = 0; j < getHeight(); j++) {
					if (map[i][j] == 0) {
						for (int k = 0; k < directions.length; k++) {
							int newX = i+directions[k][0];
							int newY = j+directions[k][1];
							if (isLegal(newX,newY) && map[newX][newY] == -1) {
								map[newX][newY] = 0;
								changes = true;
								count++;
							}
						}
					}
				}
			}
		}
		
		for (int a = 0; a < getWidth(); a++) {
			for (int b = 0; b < getHeight(); b++) {
				if (map[a][b] == 0)
					setPixel(a,b,col);
			}
		}
		return count;
	}
	
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
	@Override
	public Point2D[] shortestPath(Point2D p1, Point2D p2) {
		if (getPixel(p1) != getPixel(p2)) // in case the two points are on different color
			return null;
		int[][] map = convertMapToInt(getPixel(p2));
		map[p1.ix()][p1.iy()] = 0;
		if (!findDistance(map,p2)) // in case there is no legal path
			return null;
		Point2D[] arr = findPath(map,p2);
		return arr;
	}

	@Override
	public int shortestPathDist(Point2D p1, Point2D p2) {
		if (getPixel(p1) != getPixel(p2)) // in case the two points are on different color
			return -1;
		int[][] map = convertMapToInt(getPixel(p2));
		map[p1.ix()][p1.iy()] = 0;
		if (!findDistance(map,p2)) // in case there is no legal path
			return -1;
		return map[p2.ix()][p2.iy()];
	}
	
	public int[][] convertMapToInt(int color) {
		int[][] map = new int [getWidth()] [getHeight()];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (getPixel(i,j) == color)
					map[i][j] = -1;
				else
					map[i][j] = -999;
			}
		}
		return map;
	}
	
	public boolean findDistance(int[][] map, Point2D p2) {
		int distance = 1;
		int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
		while (map[p2.ix()][p2.iy()] == -1) {
			boolean changes = false;
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {
					if (map[x][y] == distance-1) {
						for (int i = 0; i < directions.length; i++) {
							int newX = x+directions[i][0];
							int newY = y+directions[i][1];
							if (isLegal(newX,newY) && map[newX][newY] == -1) {
								map[newX][newY] = distance;
								changes = true;
							}
						}
					}
				}
			}
			distance++;
			if (!changes)
				return false;
		}
		return true;
	}
	
	public Point2D[] findPath(int[][] map, Point2D p2) {
		int x = p2.ix();
		int y = p2.iy();
		Point2D[] path = new Point2D [map[x][y]+1];
		path[map[x][y]] = p2;
		
		int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
		System.out.println(map[x][y]);
		while (map[x][y] != 0) {
			boolean found = false;
			for (int i = 0; !found && i < directions.length; i++) {
				int newX = x+directions[i][0];
				int newY = y+directions[i][1];
				if (isLegal(newX,newY) && map[newX][newY] == map[x][y]-1) {
					x = newX;
					y = newY;
					path[map[x][y]] = new Point2D(x,y);
					found = true;
				}
			}
		}
		return path;
	}
	
	/*
	 * This function simulates a generation in the game of life by following the game's rules.
	 * In order to keep the previous gen unchanged while we create the next gen, we'll create a new map.
	 * The new map will be initialized as an entirely dead map.
	 * Now we'll go over each point in the original map to see who's alive in the next gen:
	 * 1. If an alive point has 2 or 3 neighbors, it stays alive.
	 * 2. If a dead point has 3 neighbors, it's revived.
	 * All of the other points die or stay dead.
	 */
	@Override
	public void nextGenGol() {
		for (int i = 0; i < getWidth(); i++) { // assuring all of the pixels are black or white
			for (int j = 0; j < getHeight(); j++) {
				if (getPixel(i,j) != BLACK && getPixel(i,j) != WHITE)
					setPixel(i,j,BLACK);
			}
		}
		
		MyMap2D nextGen = new MyMap2D(getWidth(),getHeight());
		nextGen.fill(WHITE);
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				int count = countNeighbours(x,y);
				if (getPixel(x,y) == BLACK) { // if alive
					if (count == 2 || count == 3)
						nextGen.setPixel(x,y,BLACK);
				}
				else if (count == 3) // if dead and 3 neighbors alive
					nextGen.setPixel(x,y,BLACK);
			}
		}
		init(nextGen._map);
	}
	
	@Override
	public void fill(int c) {
		for(int x = 0;x<this.getWidth();x++) {
			for(int y = 0;y<this.getHeight();y++) {
				this.setPixel(x, y, c);
			}
		}
		
	}
	
	/////// private ///////
	/*
	 * This function checks if a point is in the map
	 */
	public boolean isLegal(int x, int y) {
		return ((x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight()));
	}
	
	/*
	 * This function counts how much alive neighbors does a given point has. (used for game of life)
	 */
	public int countNeighbours(int x, int y) {
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (isLegal(x+i,y+j) && getPixel(x+i,y+j) == BLACK) {
					if (i == 0 && j == 0) {}
					else
						count++;
				}
			}
		}
		return count;
	}

}
