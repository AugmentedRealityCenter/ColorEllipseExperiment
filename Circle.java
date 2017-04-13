/**
* Represents a circle as the center point (x,y) and the radius r
* @author Bo Brinkman
* @version 2017-03-11
*/
public class Circle {
    public double x;
    public double y;
    public double r;
	
	/**
	* Constructor for a Circle
	*
	* @param ix the x coordinate of the center of the circle
	* @param iy the y coordinate of the center of the circle
	* @param ir the radius of the circle 
	*/
    public Circle(double ix, double iy, double ir) {
		x = ix; 
		y = iy; 
		r = ir;
    }
}
