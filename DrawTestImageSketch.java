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
	
	public static void main(String[] args) {
		// Run the main applet
		PApplet.main("DrawTestImageSketch");
	}
	
	/**
	* Required for Processing in Java 
	*/
	public void settings() {
		size(512, 512);
	}
	
	/**
	* Sets up the image to display 
	*/ 
	public void setup() {
		// Create two arrays of different colors 
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
		
		// Make the circles 
		ArrayList<Circle> circles = CircleMaker.makeCircles();
		
		// Make a new test image and draw it for this applet using the two color arrays 
		DrawTestImage dti = new DrawTestImage(this);
		dti.drawTestImage(circles, center, test, DrawTestImage.Direction.RIGHT, 2.4, 4481.6273);
	}
	
	/**
	* Required to display the image. Redraws 60 times per second. 
	*/
	public void draw() {
		
	}
}