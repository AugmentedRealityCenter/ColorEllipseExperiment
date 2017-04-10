/**
* 
* @author Caroline Danzi
* @version 2017-04-02
*/
import java.util.Vector;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class RGBLabConverter {
	
	private Vector<String> matrices;
	private Vector<Double> whitePoint;
	private final double delta = 6.0/29.0;
	
	/**
	* Constructor
	* 
	* @param matrixFile The name of the CSV file containing the RGB-XYZ matrices 
	* @param whitePoint A vector representing the XYZ values for the white point (typically D65) to use in conversions 
	*/ 
	public RGBLabConverter(String matrixFile, Vector<Double> whitePoint) throws FileNotFoundException {
		this.matrices = parseCSV(matrixFile);
		this.whitePoint = whitePoint;
	}
	
	/**
	* Converts an RGB color to an equivalent color in the CIEL*a*b* color space 
	* 
	* @param rgbColor The integer representation of the device-specific, gamma-compressed RGB color to convert
	* @return A vector of doubles that represent the L*a*b* color equivalent to the given RGB color 
	*/
	public Vector<Double> RGBToLab(int rgbColor) {
		Vector<Double> ret = RGBToXYZ(rgbColor);
		for(Double d : ret) {
			System.out.println(d);
		}
		return null;
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to an equivalent RGB color
	* 
	* @param lab The L*a*b* color to convert
	* @return The device-specific, gamma-compressed RGB color equivalent to the given L*a*b* color 
	*/
	public int LabToRGB(Vector<Double> lab) {
		Vector<Double> ret = XYZToRGB(lab);
		for(Double d : ret) {
			System.out.println(d);
		}
		return 0;
	}
	
	/**
	* Reads in a CSV file and returns a Vector representation of its contents. It will
	* split the contents based on commas and whitespace and will trim white space off each element
	* before adding it to the Vector. 
	* 
	* @param file The name of the CSV file
	* @return A Vector<String> representing the contents of the CSV file
	*/
	public static Vector<String> parseCSV(String file) throws FileNotFoundException{
		Scanner in = new Scanner(new File(file));
		// Split on commas or whitespace (such as \n)  
		in.useDelimiter(",|\\s+");
		Vector<String> list = new Vector<String>();
		while(in.hasNext()) {
			list.add(in.next().trim());
		}
		in.close();
		return list;
	}
	
	/**
	* Checks to see if a String represents a number
	* Help from http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	* 
	* @param The String to check 
	* @return true if the String could be parsed to a number, false otherwise
	*/
	private boolean isNumeric(String s) {
		return s.matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	* Matrix-vector multiplication (y = A * x). It takes in a one-dimensional Vector
	* to represent the matrix to multiply. It uses the number of rows and columns
	* (given as parameters) to determine the dimensions of the matrix. 
	* Adapted from http://introcs.cs.princeton.edu/java/22library/Matrix.java.html
	* 
	* @param a The one-dimensional Vector representing the matrix to mulitply 
	* @param x The scalar vector to multiply by the matrix
	* @param m The number of rows in the matrix
	* @param n The number of columns in the matrix 
	* @return A Vector that is the product of the matrix and vector 
	*/
    public static Vector<Double> multiply(Vector<Double> a, Vector<Double> x, int m, int n) {
        if (x.size() != n) throw new RuntimeException("Illegal matrix dimensions.");
        Vector<Double> y = new Vector<Double>();
		double sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum += a.get(m*i + j) * x.get(j);
			}
			y.add(sum);
			sum = 0;
		}
        return y;
    }
	
	/**
	* Converts a device-specific, gamma-compressed RGB color to an 
	* equivalent device-specific, linear RGB color using the formulas 
	* described in https://en.wikipedia.org/wiki/SRGB 
	* 
	* @param compRGB A vector representing the device-specific, gamma-compressed RGB color to convert 
	* @return A vector representing the equivalent linear RGB color 
	*/
	private Vector<Double> compressedRGBToLinearRGB(Vector<Double> compRGB) {
		return null;
	}
	
	/**
	* Converts device-specific linear RGB color to an equivalent
	* gamma-compressed, device-specific RGB color using the formulas
	* described in https://en.wikipedia.org/wiki/SRGB
	* 
	* @param linRGB A vector representing the linear RGB color to convert
	* @return A vector representation of the equivalent device-specific, gamma-compressed RGB color 
	*/
	private Vector<Double> linearRGBToCompressedRGB(Vector<Double> linRGB) {
		return null;
	}
	
	/**
	* Converts a device-specific RGB color to an equivalent color
	* in the CIEXYZ color space using a matrix loaded from a given CSV file
	* 
	* @param rgb The RGB color to convert 
	* @return A Vector representing the X, Y, and Z values for this RGB color 
	*/
	private Vector<Double> RGBToXYZ(int rgb) {
		// Get the actual, numerical matrix from the parsed file
		// since it will still have the String labels 
		Vector<Double> rgbToxyz = new Vector<Double>();
		String s;
		int row = 0, col = 0;
		// Use half the length since this also contains the XYZ to RGB matrix 
		for(int i = 0; i < matrices.size()/2; i++) {
			s = matrices.get(i);
			if(isNumeric(s)) {
				rgbToxyz.add(Double.parseDouble(s));
			}
		}
		
		// Multiply the RGB vector by the matrix to get the XYZ values
		// First we need to convert the color into a vector 
		Vector<Double> rgbVector = new Vector<Double>();
		rgbVector.add((double)(rgb >> 16 & 0xFF));
		rgbVector.add((double)(rgb >> 8 & 0xFF));
		rgbVector.add((double)(rgb & 0xFF));
		
		return  multiply(rgbToxyz, rgbVector, 3, 3);
	}
	
	/**
	* Converts a color in the CIEXYZ color space to device-specific 
	* RGB using a matrix loaded from a given CSV file
	* 
	* @param xyz The XYZ color to convert
	* @return The RGB color that corresponds to the given XYZ color 
	*/
	private Vector<Double> XYZToRGB(Vector<Double> xyz) {
		// Get the actual, numerical matrix from the parsed file
		// since it will still have the String labels 
		Vector<Double> xyzTorgb = new Vector<Double>();
		String s;
		// Use half the length since this also contains the XYZ to RGB matrix 
		for(int i = matrices.size()/2; i < matrices.size(); i++) {
			s = matrices.get(i);
			if(isNumeric(s)) {
				xyzTorgb.add(Double.parseDouble(s));
			}
		}
		//Multiply the matrix by the xyz vector to get the rgb colors
		Vector<Double> rgbVector = multiply(xyzTorgb, xyz, 3, 3);
		
		return rgbVector;
	}
	
	/**
	* Converts a color in the CIEXYZ color space to an
	* equivalent color in CIEL*a*b* using formulas from
	* https://en.wikipedia.org/wiki/Lab_color_space
	* 
	* Expected vector format: <x, y, z>
	* Return vector format: <L*, a*, b*> 
	* 
	* @param xyz A vector representation of the CIEXYZ color to convert 
	* @return A vector representing the L*a*b* color euqivalent to the given CIEXYZ color 
	*/
	private Vector<Double> XYZToLab(Vector<Double> xyz) {
		Vector<Double> lab = new Vector<Double>();
		double yOverYn = xyz.get(1) / whitePoint.get(1);
		
		lab.add(116 * f(yOverYn) - 16);
		lab.add(500 * (f(xyz.get(0) / whitePoint.get(0)) - f(yOverYn)));
		lab.add(200 * (f(yOverYn) - f(xyz.get(2) / whitePoint.get(2))));
		return lab; 
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to
	* an equivalent color in CIEXYZ using formulas from
	* https://en.wikipedia.org/wiki/Lab_color_space 
	* 
	* Expected vector format: <L*, a*, b*>
	* Returns vector <x, y, z> 
	* 
	* @param lab A vector representation of the CIEL*a*b* color to convert 
	* @return A vector representing the CIEXYZ color equivalent to the given CIEL*a*b*color 
	*/
	private Vector<Double> LabToXYZ(Vector<Double> lab) {
		Vector<Double> xyz = new Vector<Double>();
		double fraction = (lab.get(0) + 16) / 116.0;
		
		xyz.add(whitePoint.get(0) * fInverse(fraction + lab.get(1)/500.0));
		xyz.add(whitePoint.get(1) * fInverse(fraction));
		xyz.add(whitePoint.get(2) * fInverse(fraction - lab.get(2)/200.0));
		return xyz; 
	}
		
	/**
	* Function used in the conversion of CIEXYZ to CIEL*a*b* 
	* From https://en.wikipedia.org/wiki/Lab_color_space
	* f(x) = cube root of x if x > delta^3 
	*           t/3*delta^2 + 4/29 otherwise 
	* 
	* @param x The input value 
	* @return The output corresponding to the piecewise function 
	*/ 
	private double f(double x) {
		if(x > Math.pow(delta, 3)) {
			return Math.cbrt(x);
		} else {
			return x/(3*delta*delta) + 4.0/29.0;
		}
	}
	
	/**
	* Function used in the conversion of CIEL*a*b* to CIEXYZ
	* From https://en.wikipedia.org/wiki/Lab_color_space
	* f(x) = x^3 if x > delta 
	*           3*delta^2 (x - 4/29) otherwise 
	* 
	* @param x The input value 
	* @return The output corresponding to the piecewise function 
	*/ 
	private double fInverse(double x) {
		if(x > delta) {
			return x * x * x;
		} else {
			return 3 * delta * delta * (x - 4.0/29.0); 
		}
	}
}