/*package utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame; 

import org.junit.Before;
import org.junit.Test;

public class TestVector2D {

	public static final double APPROX_ZERO = 1E-10;
	public static final int WIDTH = 200;
	public static final int HEIGHT = 100;
	public static final double RADIUS = Math.random() * 0.1 * WIDTH;
	public final Vector2D V = new Vector2D(Math.random() * WIDTH, Math.random() * HEIGHT);
	public final Vector2D W = new Vector2D(Math.random() * WIDTH, Math.random() * HEIGHT);

	public Vector2D v, w;

	// reset v and w to be equal to V and W before every test
	@Before
	public void reset() {
		v = new Vector2D(V.x, V.y);
		w = new Vector2D(W.x, W.y);
	}

	// approximate equality of two double values
	public static void approxEquals(double d1, double d2) {
		assertEquals(d1, d2, APPROX_ZERO);
	}

	// approximate equality of two Vector2D objects
	public static void approxEquals(Vector2D a, Vector2D b) {
		approxEquals(a.x, b.x);
		approxEquals(a.y, b.y);
	}

	@Test
	public void testConstructor0() {
		v = new Vector2D();
		approxEquals(v.x, 0);
		approxEquals(v.y, 0);
	}

	@Test
	public void testConstructor1() {
		w = new Vector2D(v);
		approxEquals(v, w);
	}

	@Test
	public void testConstructor2() {
		w = new Vector2D(v.x, v.y);
		approxEquals(v, w);
	}

	@Test
	public void testSet2() {
		Vector2D z = v.set(W.x, W.y);
		approxEquals(v, w);
		assertSame(z, v);
	}

	@Test
	public void testSet1() {
		Vector2D z = v.set(W);
		approxEquals(v, w);
		approxEquals(w, W);
		assertSame(z, v);
	}

	@Test
	public void testEquals() {
		Vector2D z = new Vector2D(V.x, V.y);
		assertTrue(v.equals(z));
		assertTrue(z.equals(v));
		assertFalse(v.equals(new Vector2D(V.x - 1, V.y)));
		assertFalse(v.equals(new Vector2D(V.x, V.y - 1)));
		approxEquals(v, V);
		approxEquals(w, W);
	}

	@Test
	public void testMag() {
		approxEquals(v.mag(), Math.hypot(V.x, V.y));
		approxEquals(v, V);
	}

	@Test
	public void testAngle() {
		for (int i : new int[] { -1, 0, 1 }) {
			for (int j : new int[] { -1, 0, 1 }) {
				v = new Vector2D(V.x * i, V.y * j);
				approxEquals(v.angle(), Math.atan2(V.y * j, V.x * i));
				approxEquals(v, new Vector2D(V.x * i, V.y * j));
			}
		}
	}
	
	@Test
	public void testAngle2() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				double vangle = i*0.25 * Math.PI;
				double wangle = j*0.25 * Math.PI; 
				double vmag = 1 + Math.random()*10; 
				double wmag = 1 + Math.random()*10; 
				v = new Vector2D(vmag*Math.cos(vangle), vmag*Math.sin(vangle)); 
				w = new Vector2D(wmag*Math.cos(wangle), wmag*Math.sin(wangle));
				double result = w.angle() - v.angle(); 
				if (result < 0) result += 2*Math.PI; 
				approxEquals(v.angle(w),result);
				approxEquals(v,new Vector2D(vmag*Math.cos(vangle), vmag*Math.sin(vangle)));
				approxEquals(w,new Vector2D(wmag*Math.cos(wangle), wmag*Math.sin(wangle)));
			}
		}
	

	@Test
	public void testAdd1() {
		Vector2D z = v.add(w);
		approxEquals(v, new Vector2D(V.x + W.x, V.y + W.y));
		assertSame(z, v);
		approxEquals(w, W);
	}

	@Test
	public void testAdd2() {
		Vector2D z = v.add(w.x, w.y);
		approxEquals(v, new Vector2D(V.x + W.x, V.y + W.y));
		assertSame(z, v);
	}

	@Test
	public void testAddScaled() {
		double factor = Math.random();
		Vector2D z = v.addScaled(w, factor);
		approxEquals(v, new Vector2D(V.x + factor * W.x, V.y + factor * W.y));
		assertSame(z, v);
		approxEquals(w, W);
	}

	@Test
	public void testSubtract1() {
		Vector2D z = v.subtract(w);
		approxEquals(v, new Vector2D(V.x - W.x, V.y - W.y));
		assertSame(z, v);
		approxEquals(w, W);
	}

	@Test
	public void testSubtract2() {
		Vector2D z = v.subtract(w.x, w.y);
		approxEquals(v, new Vector2D(V.x - W.x, V.y - W.y));
		assertSame(z, v);
	}

	@Test
	public void testMult() {
		Vector2D z = v.mult(RADIUS);
		approxEquals(v, new Vector2D(V.x * RADIUS, V.y * RADIUS));
		assertSame(z, v);
	}

	@Test
	public void testRotate() {
		Vector2D z = v.rotate(RADIUS);
		double x = V.x * Math.cos(RADIUS) - V.y * Math.sin(RADIUS);
		double y = V.x * Math.sin(RADIUS) + V.y * Math.cos(RADIUS);
		approxEquals(v, new Vector2D(x, y));
		assertSame(z, v);
	}

	@Test
	public void testDot() {
		approxEquals(v.dot(w), V.x * W.x + V.y * W.y);
		approxEquals(v, V);
		approxEquals(w, W);
	}

	@Test
	public void testDist() {
		approxEquals(v.dist(w), Math.hypot(W.x - V.x, W.y - V.y));
		approxEquals(v, V);
		approxEquals(w, W);
	}

	@Test
	public void testNormalise() {
		double len = Math.hypot(v.x, v.y);
		Vector2D z = v.normalise();
		approxEquals(Math.hypot(v.x, v.y), 1.0);
		approxEquals(v.x, V.x / len);
		approxEquals(v.y, V.y / len);
		assertSame(z, v);
	}

	@Test
	public void testWrap() {
		for (int i : new int[] { -1, 0, 1 }) {
			for (int j : new int[] { -1, 0, 1 }) {
				double dx = Math.random();
				double dy = Math.random();
				v.set((i + dx) * WIDTH, (j + dy) * HEIGHT);
				w.set(dx * WIDTH, dy * HEIGHT);
				Vector2D z = v.wrap(WIDTH, HEIGHT);
				approxEquals(v, w);
				assertSame(z, v);
			}
		}
	}
	

	@Test
	public void testPolar() {
		Vector2D vCopy = new Vector2D(Vector2D.polar(Math.atan2(v.y, v.x), 
				Math.hypot(v.x,v.y))); 
		Vector2D wCopy = new Vector2D(Vector2D.polar(Math.atan2(w.y, w.x), 
				Math.hypot(w.x,w.y))); 
		approxEquals(vCopy,v);
		approxEquals(wCopy,w); 
		
	}

}
*/