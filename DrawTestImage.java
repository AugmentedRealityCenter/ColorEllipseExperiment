/**
* Draws the image of a circle made up of differently colored circles. The circles of the middle ring form
* a C of a different color, which can face either up, down, left, or right as specified in the parameters. 
* The overall image is 2 degrees wide when viewed from a specified distance. 
* 
* @author Caroline Danzi
* @version 2017-04-01
*/

import processing.core.PApplet;
import java.util.ArrayList;

public class DrawTestImage {
	
	PApplet parent;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	/**
	* Constructor for use with PApplet
	* @param p the PApplet for displaying the image
	*/
	public DrawTestImage(PApplet p) {
		parent = p;
	}
	
	/**
	* Takes in a list of circles, a list of center colors, a list of test colors, a direction for the opening in the C,
	* the distance the observer is from the screen, and the dimensions of the screen in pixels and meters. 
	* Draws a circle made of smaller circles, with a C made of different colored circles. 
	* The cut of the C can face up, down, left, or right and is 1/3 of a degree when viewed at the 
	* specified distance. The overall image is 2 degrees wide when viewed at the specified distance. 
	* 
	* @param circles An ArrayList of Circle objects that gives the location and dimension of all circles in the image
	* @param center An array of colors for the center and outer rings of circles in the image
	* @param test An array of colors for the circles that make up the C 
	* @param dir The direction for the cut in the C 
	* @param distFromScreen The distance the observer is from the screen, in meters 
	* @param pixWidth The width of the screen in pixels 
	* @param pixHeight The height of the screen in pixels 
	* @param mWidth The width of the screen in meters 
	* @param mHeight The height of the screen in meters 
	*/
	public void drawTestImage(ArrayList<Circle> circles, int[] center, int[] test, Direction dir, double distFromScreen, int pixWidth, int pixHeight, double mWidth, double mHeight) {		
		// Set background color to black
		parent.background(0);

		parent.scale(parent.width/2);
		// Puts the origin at the center of the window rather than
		// the upper-left corner
		parent.translate(1, 1);
		// No line
		parent.noStroke();
		parent.ellipseMode(parent.CENTER);
		
		// For each circle, calculate its distance from the center of the image
		// to determine which color to use. The center ring in the image will be
		// colored using randomly selected colors from the test array, and the 
		// other circles will use randomly selected colors from the center array 
		for(Circle c : circles) {
			double d = Math.sqrt(c.x*c.x + c.y*c.y);
			int index;
			if (d > 2/3.0 || d < 1/3.0) {
				index = (int)(Math.random()*center.length);
				parent.fill(center[index]);
			} else {
				index = (int)(Math.random()*test.length);
				parent.fill(test[index]);
			}
			// Draw the circle 
			parent.ellipse((float)c.x, (float)c.y, 2*(float)c.r, 2*(float)c.r);
		}
	}
}