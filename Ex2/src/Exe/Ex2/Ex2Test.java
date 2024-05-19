package Exe.Ex2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// Adi Peisach | 326627635

class Ex2Test {
	static double[] po1={2,0,3, -1,0}, po2 = {0.1,0,1, 0.1,3};

	@Test
	void testPolynomFromPoints() {
		double[] xx = {-3,-2,1}; // check a normal case
		double[] yy = {-1,-8,7};
		double[] newPol = Ex2.PolynomFromPoints(xx, yy);
		double[] pol = {-4,8,3};
		assertTrue(Ex2.equals(pol,newPol));
		
		double[] p2 = {-3, 0.61, 0.2};
		for(int x = 1;x<=3;x++) {			
			xx[x-1] = x;
			yy[x-1] = Ex2.f(p2,x);
		}
		double[] p2a = Ex2.PolynomFromPoints(xx, yy);
		assertTrue(Ex2.equals(p2,p2a));
		
		double[] p3 = {-1,0,1}; // check for division by 0
		double[] xx1 = {0,-1,1};
		double[] yy1 = {-1,0,0};
		double[] p3a = Ex2.PolynomFromPoints(xx1, yy1);
		assertTrue(Ex2.equals(p3, p3a));
		
		double[] p4 = {0,1,0};
		double[] xx2 = {0,1,2};
		double[] yy2 = {0,1,2};
		double[] p4a = Ex2.PolynomFromPoints(xx2, yy2);
		assertEquals(Ex2.poly(p4),Ex2.poly(p4a));
	}

	@Test
	void testEqualsDoubleArrayDoubleArray() {
		double[] po1a = {2,0,3, -1,0};
		double[] po1b = {2.00001,0,3,-1,0};
		double[] po1c = {2.1,0,3,-1,0};
		double[] po1d = {2,0,3, -1,0,0,0,0,0};
		assertTrue(Ex2.equals(po1a, po1));
		assertFalse(Ex2.equals(po1, po2));
		assertTrue(Ex2.equals(po1b, po1));
		assertFalse(Ex2.equals(po1c, po1));
		assertTrue(Ex2.equals(po1d, po1));
	}

	@Test
	void testF() {
		double fx0 = Ex2.f(po1, 0);
		double fx1 = Ex2.f(po1, 1);
		double fx2 = Ex2.f(po1, 2);
		assertEquals(fx0,2);
		assertEquals(fx1,4);
		assertEquals(fx2,6);
	}

	@Test
	void testPoly() {
		String s1 = Ex2.poly(po1);
		String s2 = Ex2.poly(po2);
		double[] p = {0,1,2};
		assertEquals("-1.0x^3 +3.0x^2 +2.0 ",s1);
		assertEquals("3.0x^4 +0.1x^3 +1.0x^2 +0.1 ",s2);
		assertEquals("2.0x^2 +1.0x ",Ex2.poly(p));
	}

	@Test
	void testSameValue() {
		double x = Ex2.sameValue(po1,po2,0,3,Ex2.EPS);
		assertEquals(Math.abs(Ex2.f(po1,x) - Ex2.f(po2,x)),0,Ex2.EPS);
	}

	@Test
	void testRoot() {
		double x12 = Ex2.root(po1, 0, 10, Ex2.EPS);
		assertEquals(x12, 3.1958, Ex2.EPS);
	}

	@Test
	void testRoot_rec() {
		double x12 = Ex2.root(po1, 0, 10, Ex2.EPS);
		assertEquals(x12, 3.1958, Ex2.EPS);
	}

	@Test
	void testArea() {
		double area1 = Ex2.area(po1,po2,-1,0.5,100);
		area1 = (double)(Math.round(area1*100.0)/100.0);
		double area2 = Ex2.area(po1,po2,2,6,1000000);
		area2 = (double)(Math.round(area2*100.0)/100.0);
		assertEquals(3.24,area1,Ex2.EPS);
		assertEquals(4852.14,area2,Ex2.EPS);
	}

	@Test
	void testGetPolynomFromString() {
		double[] newPo1 = Ex2.getPolynomFromString(Ex2.poly(po1));
		double[] newPo2 = Ex2.getPolynomFromString(Ex2.poly(po2));
		assertEquals(Ex2.poly(po1),Ex2.poly(newPo1));
		assertEquals(Ex2.poly(po2),Ex2.poly(newPo2));
	}

	@Test
	void testAdd() {
		double[] p12 = Ex2.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex2.mul(po2, minus1);
		double[] p1 = Ex2.add(p12, pp2);
		assertEquals(Ex2.poly(po1), Ex2.poly(p1));
	}

	@Test
	void testMul() {
		double[] p1 = {1,2};
		double[] p2 = {21,7,-9,2};
		double[] p3 = {-2,-6,88};
		
		double[] ans12 = {21,49,5,-16,4};
		double[] ans13 = {-2,-10,76,176};
		assertEquals(Ex2.poly(ans12),Ex2.poly(Ex2.mul(p1,p2)));
		assertEquals(Ex2.poly(ans13),Ex2.poly(Ex2.mul(p1,p3)));
		
		double[] ans1a = {-1,-2};
		double[] ans3a = {-1,-3,44};
		assertEquals(Ex2.poly(ans1a),Ex2.poly(Ex2.mul(p1,-1)));
		assertEquals(Ex2.poly(ans3a),Ex2.poly(Ex2.mul(p3,0.5)));
	}

	@Test
	void testDerivative() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] dp1 = {2,6}; // 6x+2
		double[] dp2 = Ex2.derivative(p);
		assertEquals(dp1[0], dp2[0],Ex2.EPS);
		assertEquals(dp1[1], dp2[1],Ex2.EPS);
		assertEquals(dp1.length, dp2.length);
	}
	
	///////////////////// Private /////////////////////
	
	@Test
	void testSubtract() {
		double[] poly1 = Ex2.add(po1,Ex2.mul(po1,-1));
		double[] poly2 = Ex2.subtract(po1,po1);
		assertEquals(Ex2.poly(poly1),Ex2.poly(poly2));
		
		double[] poly3 = Ex2.add(po1,Ex2.mul(po2,-1));
		double[] poly4 = Ex2.subtract(po1,po2);
		assertEquals(Ex2.poly(poly3),Ex2.poly(poly4));
	}
	
	@Test
	void testPolyLength() {
		assertEquals(Ex2.polyLength(Ex2.poly(po2)),po2.length);
		double[] po3 = {0};
		assertEquals(Ex2.polyLength(Ex2.poly(po3)),po3.length);
	}

}
