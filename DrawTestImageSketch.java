/**
* Draws a test image of a C inside a circle
*
* @author Caroline Danzi
* @version 2017-03-26
*/
import processing.core.PApplet;
import java.util.ArrayList;
public class DrawTestImageSketch extends PApplet{
	private int[] center;
	private int[] test;
	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	public static void main(String[] args) {
		PApplet.main("DrawTestImageSketch");
	}
	
	public void settings() {
		size(400, 400);
	}
	
	public void setup() {
		// This size refers to the size of the window that appears???
		size(400, 400);
		center = new int[5];
		test = new int[5];
		center[0] = color(42, 230, 237);
		center[1] = color(33, 183, 188);
		center[2] = color(24, 138, 142);
		center[3] = color(14, 177, 183);
		center[4] = color(97, 239, 244);
		test[0] = color(244, 241, 97);
		test[1] = color(216, 213, 80);
		test[2] = color(232, 228, 55);
		test[3] = color(186, 182, 14);
		test[4] = color(239, 234, 4);
		drawTestImage(center, test, Direction.UP, 0, 0, 0, 0);
	}
	
	public void draw() {
		
	}
	
		/**
	* Note: color() returns an int so we may need to use that instead of color type
	* Takes in a list of center colors, a list of test colors, a direction for the opening in the C,
	* and the dimensions of the screen in pixels and meters. Draws a circle made of smaller
	* circles, where a C appears inside the circle. The cut of the C can face up, down, left, or right
	* and is 1/3 of a degree when viewed at a distance of 2.4 meters. The overall image is 
	* 2 degrees wide when viewed at a distance of 2.4 meters. 
	
	From Dr. Brinkman: 
	Goal: Pick a random color for each circle. If it is not inside the C, use the center color. 
	If it is inside the C, use a test color. The overall image should be 2 degrees wide when
	viewed at a distance of 2.4m, and centered in the screen. I think the thickness of the C 
	should be 1/3 of a degree, and the cut should also be 1/3 of a degree. Not sure about this.
	
	*/
	public void drawTestImage(int[] center, int[] ring, Direction dir, int pixWidth, int pixHeight, double mWidth, double mHeight) {
		// Set background color to black
		background(0);
		
		// Create an ArrayList of circle objects to draw
		ArrayList<Circle> circles = CircleMaker.makeCircles();

		scale(width/2);
		// Puts the origin at the center of the window rather than
		// the upper-left corner
		translate(1, 1);
		// No line
		noStroke();
		ellipseMode(CENTER);
		
		for(Circle c : circles) {
			// Calculate the distance between the center
			// of the circle and the origin. The main large circle will
			// be split into three rings - the two outer rings will be
			// filled with circles that are lighter gray than the middle ring
			double d = Math.sqrt(c.x*c.x + c.y*c.y);
			int index;
			if (d > 2/3.0 || d < 1/3.0) {
				index = (int)(Math.random()*center.length);
				fill(center[index]);
			} else {
				index = (int)(Math.random()*ring.length);
				fill(test[index]);
			}
			// Draw the circle 
			ellipse((float)c.x, (float)c.y, 2*(float)c.r, 2*(float)c.r);
		}
	}
}