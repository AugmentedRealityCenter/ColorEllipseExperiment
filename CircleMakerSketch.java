/**
* Creates a Processing sketch that contains a large circle filled with 
* smaller circles. The large circle is divided evenly into three rings,
* where the smaller circles that fill the middle ring are colored darker
* than those that fill the outer and innermost rings. 
* 
* @author Bo Brinkman
* @version 2017-03-11
*/

import processing.core.PApplet;
import java.util.ArrayList;

public class CircleMakerSketch extends PApplet {
	
	/**
	* Starts the sketch
	*/
    public static void main(String[] args){
		PApplet.main("CircleMakerSketch");
    }

	/**
	* Sets the size of the window
	*/
    public void settings() {
    	size(512, 512);
    }

	/**
	* Required method for Processing. Will draw 60 times per second
	*/
    public void draw()  {
    }

	/**
	* Sets up the sketch by drawing 
	*/
    public void setup() {
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
		
		// For each circle in the ArrayList, 
		for (int i=0; i < circles.size(); i++) {
			// Calculate the distance between the center
			// of the circle and the origin. The main large circle will
			// be split into three rings - the two outer rings will be
			// filled with circles that are lighter gray than the middle ring
			double d = Math.sqrt(circles.get(i).x*circles.get(i).x
						+circles.get(i).y*circles.get(i).y);
			if (d > 2/3.0 || d < 1/3.0) {
				fill(255 - random(63));
			} else {
				fill(127 - random(63));
			}
			// Draw the circle 
			ellipse((float)circles.get(i).x, (float)circles.get(i).y, 
				  2*(float)circles.get(i).r, 2*(float)circles.get(i).r);
		}
    }
}
