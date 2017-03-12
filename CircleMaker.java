/**
* This class creates an ArrayList of Circle objects that represent
* a collection of circles which fill a larger circle. The location and radii
* of the circles are chosen randomly, though no two circles touch. 
* 
* @author Bo Brinkman
* @version 2017-03-11
*/

import java.util.ArrayList;

public class CircleMaker {
    static final int maxN = 10000;
    static int n = 0;
    static final double[] x = new double[maxN];
    static final double[] y = new double[maxN];
    static final double[] r = new double[maxN];
    static final double minR = 1/80.0;
    static final double maxR = 1/20.0;
    static final double fillArea = Math.PI;
    static double area = 0.0;
    static final double targetRatio = 0.7;

	/**
	* Create an ArrayList of Circle objects that randomly fill a larger circle. No two
	* circles touch, and the total area of the circles combined divided by 
	* the total area to fill is less than the target ratio.
	* 
	* @return an ArrayList of Circle objects
	*/
    public static ArrayList<Circle> makeCircles() {
		n = 0;
		area = 0.0;

		// Continue generating circles until we either draw the max number
		// of circles or until the area of how much we have filled is greater
		// than our target ratio
		while (area/fillArea < targetRatio && n < maxN) {
			// Select a random point inside the window and 
			// a random radius size that is within the range [minR, maxR) 
			x[n] = -1.0 + 2.0*Math.random();
			y[n] = -1.0 + 2.0*Math.random();
			r[n] = minR + (maxR-minR)*Math.random();

			// Go through each existing circle - if the new circle overlaps
			// any of them, decrease its radius by the distance they overlap
			for (int i=0; i < n; i++) {
				double d = Math.sqrt((x[n]-x[i])*(x[n]-x[i]) +
					(y[n]-y[i])*(y[n]-y[i]));
				if (d < r[n]+r[i]) r[n] = d - r[i];
			}

			// If the radius is greater than the minimum radius and
			// the circle is actually inside the larger circle we are filling,
			// move on to the next circle. Otherwise, try again. 
			double d = Math.sqrt(x[n]*x[n] + y[n]*y[n]) + Math.abs(r[n]);
			if (r[n] >= minR && d <= 1.0) {
				area += Math.PI*r[n]*r[n];
				n++;
			}
		}

		// Build Circle objects using the xy-coordinate data and radii 
		// and put each Circle object in the ArrayList
		ArrayList<Circle> ret = new ArrayList<Circle>();
		for (int i=0; i < n; i++) {
			ret.add(new Circle(x[i], y[i], r[i]));
		}
		return ret;
	}
}
