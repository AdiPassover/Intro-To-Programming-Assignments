package Exe.Ex2;
/** 
 * Adi Peisach | 326627635
 * 
 * This class represents a set of functions on a polynom - represented as array of doubles.
 * The array is represented in the next logic:
 * The value is the coefficients and the index is the exponent of X.
 * For example: {1,2,3} = 1*x^0 + 2*x^1 + 3*x^2 = 3x^2 + 2x + 1
 * @author boaz.benmoshe
 *
 */
public class Ex2 {
	/** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynom is represented as an array with a single (0) entry. */
	public static final double[] ZERO = {0};
	/**
	 * This function computes a polynomial representation from a set of 2D points on the polynom.
	 * Note: this function only works for a set of points containing three points, else returns null.
	 * 
	 * This function works by using the parabola formula: ax^2 + bx + c = y
	 * The formula has 3 variables: a,b,c.
	 * We receive 3 points. This means we have 3 x values and 3 y values.
	 * We're able to create 3 equations for 3 variables by replacing the point's x with x and the point's y with y
	 * In order to solve the equations matrix, we'll use the row reduction algorithm.
	 * 
	 * @param xx - array of the x values of 3 points
	 * @param yy - array of the y values of 3 points
	 * @return an array of doubles representing the coefficients of the polynom.
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy) {
		if(xx==null || yy==null || xx.length!=3 || yy.length!=3)
			return null;
		if (xx[0] == xx[1] || xx[1] == xx[2] || xx[2] == xx[0])
			return null;
		double [] ans = new double [3];

		if (xx[0] == 0) // prevent division by 0
			swap(0,xx,yy); // swap eq1 with eq3
		else if (xx[1] == 0)
			swap(1,xx,yy); // swap eq2 with eq3

		double[] eq1 = { Math.pow(xx[0],2), xx[0], 1, yy[0]}; // ax^2 + bx + c = y
		double[] eq2 = { Math.pow(xx[1],2), xx[1], 1, yy[1]};
		double[] eq3 = { Math.pow(xx[2],2), xx[2], 1, yy[2]};

		eq1 = mul(eq1, 1/eq1[0]); 			 // eq1[0] -> 1

		eq2 = subtract(eq2,mul(eq1,eq2[0])); // eq2[0] -> 0
		eq2 = mul(eq2, 1/eq2[1]);			 // eq2[1] -> 1

		eq3 = subtract(eq3,mul(eq1,eq3[0])); // eq3[0] -> 0
		eq3 = subtract(eq3,mul(eq2,eq3[1])); // eq3[1] -> 0
		eq3 = mul(eq3, 1/eq3[2]); 			 // eq3[2] -> 1

		double c = eq3[3];
		ans[0] = c;
		double b = eq2[3] - eq2[2]*c;
		ans[1] = b;
		double a = eq1[3] - eq1[2]*c - eq1[1]*b;
		ans[2] = a;
		return ans;
	}
	/** Two polynoms are equal if and only if the have the same coefficients - up to an epsilon (aka EPS) value.
	 * @param p1 first polynom
	 * @param p2 second polynom
	 * @return true iff p1 represents the same polynom as p2.
	 */
	public static boolean equals(double[] p1, double[] p2) {
		int max = Math.max(p1.length,p2.length);
		int min = Math.min(p1.length,p2.length);
		if (max != min) { // checking the case where the length is different but the arrays are still equals.
			for (int j = min; j < max; j++) { // i.e: {1,2} = {1,2,0,0,0,0}
				if (j < p1.length && p1[j] != 0)
					return false;
				if (j < p2.length && p2[j] != 0)
					return false;
			}
		}
		for (int i = 0; i < min; i++) {
			if (Math.abs(p1[i] - p2[i]) > EPS) // check if the distance between the coefs is lower the epsilon
				return false;
		}
		return true;
	}
	/**
	 * Computes the f(x) value of the polynom at x.
	 * @param poly
	 * @param x
	 * @return f(x) - the polynom value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		for (int i = 0; i < poly.length; i++) {
			double num = poly[i]*Math.pow(x,i);
			ans += num;
		}
		return ans;
	}
	/** 
	 * Computes a String representing the polynom.
	 * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
	 * @param poly the polynom represented as an array of doubles
	 * @return String representing the polynom: 
	 */
	public static String poly(double[] poly) {

		String ans = "";
		for (int i = poly.length-1; i >= 0; i--) {
			if (poly[i] != 0) {
				ans += poly[i];
				if (i > 1) { // if the exponent is greater than 1 we want to write it
					ans += "x^";
					ans += i;
				}
				else if (i == 1) // if the exponent is 1 we don't want to write it
					ans += "x";
				if (i-1 >= 0 && poly[i-1] >= 0)
					ans += " +";
				else
					ans += " ";
			}
			else if (i == 0) { // if the last number equals 0
				if (ans.length() > 0) // usually, we don't want to write it, so we'll delete the last "+"
					ans = ans.substring(0,ans.length()-1);
				else // unless it's the only number
					ans += poly[i] + " ";
			}
		}
		return ans;
	}
	/**
	 * Given two polynoms (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
	 * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * 
	 * This function works by using the equation:
	 * (When I write "= 0", I mean close to 0 up to an epsilon) 
	 * 		p1(x) = p2(x)
	 * 		p1(x) - p2(x) = 0
	 * Let p(x) = p1(x) - p2(x)
	 * If p(x) = 0 then p1(x) = p2(x).
	 * So we'll search for the root of p(x) and return it.
	 * 
	 * 
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) -p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		// assuring the assumption
		if (((f(p1,x1)-f(p2,x1)) * (f(p1,x2)-f(p2,x2)) > 0) || x2 < x1)
			return 0;
		
		double x = (x1+x2)/2;
		double[] diff = subtract(p1,p2);
		x = root(diff,x1,x2,eps);
		return x;
	}
	/**
	 * Given a polynom (p), a range [x1,x2] and an epsilon eps. 
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0. 
	 * This function should be implemented iteratively (none recursive).
	 * 
	 * This funtion uses the bisection method.
	 * We know that if p(x1) is pos, then p(x2) is neg, or the opposite.
	 * When p(x) changes from pos to neg or from neg to pos, it needs to be 0 while it passes zones.
	 * That's why we need to look for when the zones are switched.
	 * We'll start in the middle (x=(x1+x2)/2), divide the range to 2 zones.
	 * If p(x) is in the same zone as p(x1), we'll move x to the middle of the second zone.
	 * If p(x) is in the opposite zone of p(x1), we'll move x to the middle of the first zone.
	 * Otherwise, it means p(x) = 0, so we'll return x.
	 * We'll continue this process and divide the range into smaller zones until we find the root.
	 * 
	 * @param p - the polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root(double[] p, double x1, double x2, double eps) {
		if ((f(p,x1)*f(p,x2) > 0) || x2 < x1) // assuring the assumption
			return 0;

		
		double x = (x1+x2)/2;
		double range = x2-x1;
		int m; // m holds 1 if p(x1) is pos and -1 if p(x1) is neg
		double y1 = f(p,x1);
		if (y1 >= eps)
			m = 1;
		else if (y1 <= eps*-1)
			m = -1;
		else
			return x1;

		for (double i = 4; true; i *= 2) {
			double y = f(p,x);
			if (y*m >= eps) { // p(x) is in the same zone as p(x1)
				x += range/i;
			}
			else if (y*m <= eps*-1) { // p(x) is in the opposite zone of p(x1)
				x -= range/i;
			}
			else // p(x) = 0
				return x;
		}		
	}
	/** Given a polynom (p), a range [x1,x2] and an epsilon eps. 
	 * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps, 
	 * assuming p(x1)*p(x2) <= 0. 
	 * This function should be implemented recursively.
	 * 
	 * This funtion uses the bisection method.
	 * We know that if p(x1) is pos, then p(x2) is neg, or the opposite.
	 * When p(x) changes from pos to neg or from neg to pos, it needs to be 0 while it passes zones.
	 * That's why we need to look for when the zones are switched.
	 * We'll start in the middle (x=(x1+x2)/2), divide the range to 2 zones.
	 * If p(x) is in the same zone as p(x1), we'll move x to the middle of the second zone.
	 * If p(x) is in the opposite zone of p(x1), we'll move x to the middle of the first zone.
	 * Otherwise, it means p(x) = 0, so we'll return x.
	 * We'll continue this process and divide the range into smaller zones until we find the root.
	 * 
	 * @param p - the polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double x = (x1+x2)/2;
		double range = x2-x1;
		int m;
		double y1 = f(p,x1);
		if (y1 >= eps)
			m = 1;
		else if (y1 <= eps*-1)
			m = -1;
		else
			return x1;

		return root_rec(p,x,4,m,range,eps);
	}

	public static double root_rec(double[] p, double x, double i, int m, double range, double eps) {
		double y = f(p,x);
		if (y*m >= eps) {
			x += range/i;
		}
		else if (y*m <= eps*-1) {
			x -= range/i;
		}
		else
			return x;

		return root_rec(p,x,i*2,m,range,eps);
	}

	/**
	 * Given two polynoms (p1,p2), a range [x1,x2] and an integer representing the number of "boxes". 
	 * This function computes an approximation of the area between the polynoms within the x-range.
	 * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @param x1 - minimal value of the range
	 * @param x2 - maximal value of the range
	 * @param numberOfBoxes - a natural number representing the number of boxes between xq and x2.
	 * @return the approximated area between the two polynoms within the [x1,x2] range.
	 */
	public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfBoxes) {
		double areaSum = 0;
		double range = x2-x1;

		for (double start = x1; start < x2; start += range/numberOfBoxes) {

			double end = start + range/numberOfBoxes;
			double width = end - start;
			double height = Math.abs(f(p2,start) - f(p1,start));

			double boxArea = height * width;
			areaSum += boxArea;
		}

		return areaSum;
	}
	/**
	 * This function computes the array representation of a polynom from a String
	 * representation. Note:given a polynom represented as a double array,  
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynom.
	 * @return the polynom that p represents
	 */
	public static double[] getPolynomFromString(String p) {
		double[] pol = new double [polyLength(p)]; // this will be our poly's array
		boolean minus = false; // used for checking if a coef is negative
		boolean isCoef = true; // used for checking if a number is a coef or exponent
		String num = ""; // used for holding the coef value
		String power = ""; // usef for holding the exponet value

		for (int i = 0; i < p.length(); i++) {
			char c = p.charAt(i);
			if (c == '.' || (c >= '0' && c <= '9')) { // if we find a number, we add it to the correct String
				if (isCoef)
					num += c;
				else
					power += c;
			}
			else if (c == '-') // if we find '-', the next coef is negative
				minus = true;
			else if (c == '^') // if we find '^', the next number is a exponent
				isCoef = false;
			else if (c == ' ') { // if we find ' ', we'll enter our values 
				double coef = Double.parseDouble(num);
				int index = 0;
				if (power == "") { // when the exponent is 1 there is no '^'
					if (p.charAt(i-1) == 'x')
						index = 1;
				}
				else
					index = Integer.parseInt(power);
				if (minus)
					coef = coef * -1;
				pol[index] = coef;
				minus = false;
				isCoef = true;
				num = "";
				power = "";
			}
		}
		return pol;
	}
	/**
	 * This function computes the polynom which is the sum of two polynoms (p1,p2)
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @return the sum of p1 and p2
	 */
	public static double[] add(double[] p1, double[] p2) {
		double[] sum = new double [Math.max(p1.length, p2.length)];
		for (int i = 0; i < sum.length; i++) {
			if (i >= p2.length)
				sum[i] = p1[i];
			else if (i >= p1.length)
				sum[i] = p2[i];
			else
				sum[i] = p1[i] + p2[i];
		}
		return sum;
	}
	/**
	 * This function computes the polynom which is the multiplication of two polynoms (p1,p2)
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @return the multiplication of p1 and p2
	 */
	public static double[] mul(double[] p1, double[] p2) {
		double[] mulPoly = new double [p1.length+p2.length -1];
		for (int i = 0; i < p1.length; i++) {
			for (int j = 0; j < p2.length; j++) {
				mulPoly[i+j] += p1[i]*p2[j]; 
			}
		}
		return mulPoly;
	}
	/**
	 * This function computes the derivative of a polynom.
	 * @param po - the polynom
	 * @return the derivative of po
	 */
	public static double[] derivative (double[] po) {
		double[] derivative = new double [po.length -1];
		for (int i = 1; i < po.length; i++) {
			derivative[i-1] = i*po[i];
		}
		return derivative;
	}
	///////////////////// Private /////////////////////

	/**
	 * This function computes the polynom which is the difference of two polynoms (p1,p2)
	 * @param p1 - first polynom
	 * @param p2 - second polynom
	 * @return the difference of p1 and p2
	 */
	public static double[] subtract(double[] p1, double[] p2) {
		double[] minusOne = {-1};
		double[] minusP2 = mul(p2,minusOne);
		double[] ans = add(p1,minusP2);
		return ans;
	}

	/**
	 * This function computes the polynom which is the multiplication of a polynom and a number.
	 * @param p - the polynom
	 * @param num - real number
	 * @return the multiplication of p1 and num
	 */
	public static double[] mul(double[] p, double num) {
		double[] mulPoly = new double [p.length];
		for (int i = 0; i < p.length; i++) {
			mulPoly[i] += p[i]*num; 
		}
		return mulPoly;
	}

	/**
	 * This function computes the length of a polynom array the polynom's string representation.
	 * @param p - the polynom
	 * @return the length of p's array representation
	 */
	public static int polyLength(String p) {
		String length = "";
		boolean isPower = false;
		boolean over = false;
		boolean isZero = true; // in case there are no exponentiation
		for (int j = 0;!over && j < p.length(); j++) {
			char c = p.charAt(j);
			if (isPower) {
				if (c == ' ')
					over = true;
				else
					length += c;
			}
			else if (c == '^') {
				isPower = true;
			}
			else if (c == 'x')
				isZero = false;
		}
		int polLength = 1;
		if (isZero)
			polLength = 0;
		if (length != "")
			polLength = Integer.parseInt(length);

		return polLength+1;
	}

	/**
	 * This function changes the order of 2 points in given arrays.
	 * @param i - the index of the point we want to swap.
	 * @param xx - array of x values of points.
	 * @param yy - array of y valies of points.
	 */
	public static void swap(int i, double[] xx, double[] yy) {
		xx[i] = xx[2];
		xx[2] = 0;
		double temp = yy[2];
		yy[2] = yy[i];
		yy[i] = temp;
	}
}
