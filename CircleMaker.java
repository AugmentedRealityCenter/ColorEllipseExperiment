/**
* This class creates an ArrayList of Circle objects that represent
* a collection of circles which fill a larger circle. The location and radii
* of the circles are chosen randomly, though no two circles touch. 
* 
* @author Bo Brinkman and Caroline Danzi
* @version 2017-03-11
*/

import java.util.ArrayList;

public class CircleMaker {
    static final int maxN = 10000;
    static int n = 0;
    static final double minR = 1/80.0;
    static final double maxR = 1/20.0;
    static final double fillArea = Math.PI;
    static double area = 0.0;
    static final double targetRatio = 0.7;
	/* The bucket size should be maxR*2, so that any circle that falls within that bucket 
	* will only have to be checked against the immediately surrounding buckets, since
	* it could not overlap with circles outside those buckets. For example, if a circle
	* falls in bucket 4 (below), we would need to check buckets 0,1,2,3,4,5,6,7,8 
	*  | 0 | 1 | 2 |
	*  | 3 | 4 | 5 |
	*  | 6 | 7 | 8 |	
	*/
	static final double bucketSize = 2*maxR;
	static final int rows = (int)(Math.ceil(2/(bucketSize)));
	static final int totalBuckets = rows * rows;

	/**
	* Create an ArrayList of Circle objects that randomly fill a larger circle. No two
	* circles touch, and the total area of the circles combined divided by 
	* the total area to fill is less than the target ratio.
	* 
	* @return an ArrayList of Circle objects
	*/
    public static ArrayList<Circle> makeCircles() {
		
		// Create an ArrayList to hold all the buckets, which are just ArrayLists of Circles
		ArrayList<ArrayList<Circle>> buckets = new ArrayList<ArrayList<Circle>>(totalBuckets);
		for(int i = 0; i < totalBuckets; i++) {
			buckets.add(new ArrayList<Circle>());
		}

		// Continue generating circles until we either draw the max number
		// of circles or until the area of how much we have filled is greater
		// than our target ratio
		while (area/fillArea < targetRatio && n < maxN) {			
			// Select a random (x,y) offset and radius 
			// Note: the true (x,y) coordinates will be -1.0 + the offset 
			double xOffset = 2.0 * Math.random();
			double yOffset = 2.0 * Math.random();
			double radius = minR + (maxR-minR)*Math.random();
			double x = -1.0 + xOffset;
			double y = -1.0 + yOffset;
			
			// Determine the row and column of the bucket
			// this circle falls into
			int col = (int)(xOffset / (bucketSize));
			int row = (int)(yOffset / (bucketSize));
			
			// Go through the surrounding buckets and check to make sure
			// the new Circle does not overlap any of the existing circles
			int bucket, dy, dx;
			for(dy = -1; dy <= 1; dy++) {
				// If it is not a valid row, go to the next one
				if(row + dy < 0 || row + dy >= rows) { continue; }
				
				for(dx = -1; dx <= 1; dx++) {
					// If it is not a valid column, go to the next one
					if(col + dx < 0 || col + dx >= rows) { continue; }
					
					// Determine the bucket number and check all Circles in that
					// bucket. If the new Circle overlaps, decrease its radius
					// by the distance they overlap 
					bucket = (col + dx)+ (row + dy)*rows;
					for(Circle c : buckets.get(bucket)) {
						// Check for overlap
						double d = Math.sqrt((x-c.x)*(x-c.x) + (y-c.y)*(y-c.y));
						if(d < radius + c.r) {
							radius = d - c.r;
						}
					}
				}				
			}			
			// Add circle to correct bucket if radius > minR and it is still in the larger circle
			double d = Math.sqrt(x*x + y*y) + Math.abs(radius);
			if(radius >= minR && d <= 1.0) {
				area += Math.PI*radius*radius;
				n++;
				buckets.get(col + row*rows).add(new Circle(x, y, radius));
			}
		}
		
		// Put all the Circles into one ArrayList and return 
		ArrayList<Circle> ret = new ArrayList<Circle>();
		for(ArrayList<Circle> a : buckets) {
			for(Circle c : a) {
				ret.add(c);
			}
		}
		return ret;
	}
}