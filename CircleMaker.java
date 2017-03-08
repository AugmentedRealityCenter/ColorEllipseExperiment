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

    public static ArrayList<Circle> makeCircles() {
	n = 0;
	area = 0.0;

	while (area/fillArea < targetRatio && n < maxN) {
	    x[n] = -1.0 + 2.0*Math.random();
	    y[n] = -1.0 + 2.0*Math.random();
	    r[n] = minR + (maxR-minR)*Math.random();

	    for (int i=0; i < n; i++) {
		double d = Math.sqrt((x[n]-x[i])*(x[n]-x[i]) +
			(y[n]-y[i])*(y[n]-y[i]));
		if (d < r[n]+r[i]) r[n] = d - r[i];
	    }

	    double d = Math.sqrt(x[n]*x[n] + y[n]*y[n]) + Math.abs(r[n]);
	    if (r[n] >= minR &&
		    d <= 1.0) {
		area += Math.PI*r[n]*r[n];
		n++;
	    }
	}

	ArrayList<Circle> ret = new ArrayList<Circle>();
	for (int i=0; i < n; i++) {
	    ret.add(new Circle(x[i], y[i], r[i]));
	}
	return ret;
    }
}
