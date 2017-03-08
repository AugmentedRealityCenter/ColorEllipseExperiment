import processing.core.PApplet;
import java.util.ArrayList;

public class CircleMakerSketch extends PApplet {
    public static void main(String[] args){
	PApplet.main("CircleMakerSketch");
    }

    public void settings() {
    	size(512, 512);
    }

    public void draw()  {
    }

    public void setup() {
	background(0);

	ArrayList<Circle> circles = CircleMaker.makeCircles();

	scale(width/2);
	translate(1, 1);
	noStroke();
	ellipseMode(CENTER);
	for (int i=0; i < circles.size(); i++) {
	    double d = Math.sqrt(circles.get(i).x*circles.get(i).x
	    			+circles.get(i).y*circles.get(i).y);
	    if (d > 2/3.0 || d < 1/3.0) {
		fill(255 - random(63));
	    } else {
		fill(127 - random(63));
	    }
	    ellipse((float)circles.get(i).x, (float)circles.get(i).y, 
	          2*(float)circles.get(i).r, 2*(float)circles.get(i).r);
	}
    }
}
