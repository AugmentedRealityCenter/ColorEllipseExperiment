/**
* Converts gamma-compressed, device-specific RGB colors to equivalent CIEL*a*b* colors
* and vice versa. It requires a CSV file containing the matrices for converting XYZ to RGB and
* vice versa, as well as a CSV file containing the gamma value and lower limits for each color
* channel for use when converting compressed RGB to linear RGB and vice versa. The lower
* limit is meant to correct for the fact that device readings usually do not reach 0 when 
* measuring color. 
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
	private Vector<String> rgbModelValues;
	private Vector<Double> whitePoint;
	private final double delta = 6.0/29.0;
	
	/**
	* Constructor
	* 
	* @param matrixFile The name of the CSV file containing the RGB-XYZ matrices 
	* @param modelFile The name of the CSV file containing the gamma value and lower limits for each color channel
	* @param whitePoint A vector representing the XYZ values for the white point (typically D65) to use in conversions 
	*/ 
	public RGBLabConverter(String matrixFile, String modelFile, Vector<Double> whitePoint) throws FileNotFoundException {
		this.matrices = parseCSV(matrixFile);
		this.rgbModelValues = parseCSV(modelFile);
		this.whitePoint = whitePoint;
	}
	
	/**
	* Converts an RGB color to an equivalent color in the CIEL*a*b* color space 
	* 
	* @param rgbColor The integer representation of the device-specific, gamma-compressed RGB color to convert
	* @return A vector of doubles that represent the L*a*b* color equivalent to the given RGB color 
	*/
	public Vector<Double> RGBToLab(int rgbColor) {
		// First we need to convert the color into a vector 
		Vector<Double> rgbVector = new Vector<Double>();
		rgbVector.add((double)(rgbColor >> 16 & 0xFF));
		rgbVector.add((double)(rgbColor >> 8 & 0xFF));
		rgbVector.add((double)(rgbColor & 0xFF));
		
		// Compressed RGB to Linear RGB
		Vector<Double> uncompressed = compressedRGBToLinearRGB(rgbVector);
		for(Double d : uncompressed) {
			System.out.println("Uncompressed value: " + d);
		}
		
		// Linear RGB to CIEXYZ 		
		Vector<Double> xyz = RGBToXYZ(uncompressed); 
		for(Double d : xyz) {
			System.out.println("RGB to XYZ: " + d);
		}
		
		// CIEXYZ to CIEL*a*b* 
		Vector<Double> lab = XYZToLab(xyz);
		return lab;
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to an equivalent RGB color
	* 
	* @param lab The L*a*b* color to convert
	* @return The device-specific, gamma-compressed RGB color equivalent to the given L*a*b* color 
	*/
	public int LabToRGB(Vector<Double> lab) {
		// Lab to XYZ
		Vector<Double> xyz = LabToXYZ(lab);
		for(Double d : xyz) {
			System.out.println("Lab to XYZ: " + d);
		}
		
		// XYZ to linear RGB 
		Vector<Double> rgb = XYZToRGB(xyz);
		for(Double d : rgb) {
			System.out.println("XYZ to RGB: " + d);
		}
		
		// Linear RGB to gamma-compressed RGB 
		Vector<Double> compressed = linearRGBToCompressedRGB(rgb);
		for(Double d : compressed) {
			System.out.println("Compressed value: " + d); 
		}
		
		// Change each double to an int and return an individual int 
		// TODO: intValue() only truncates; it does not round 
		return (compressed.get(0).intValue() << 16) | (compressed.get(1).intValue() << 8) | compressed.get(2).intValue(); 
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
		// Ignore the labels in the rgbModelValues vector 
		Vector<Double> linear = new Vector<Double>();
		double gamma = Double.parseDouble(rgbModelValues.get(1));
		linear.add(uncompress(compRGB.get(0), gamma, Double.parseDouble(rgbModelValues.get(3))));
		linear.add(uncompress(compRGB.get(1), gamma, Double.parseDouble(rgbModelValues.get(5))));
		linear.add(uncompress(compRGB.get(2), gamma, Double.parseDouble(rgbModelValues.get(7))));	
		return linear;
	}
	
	/**
	* Takes a compressed RGB value from a color channel (red, green, or blue) and 
	* uncompresses it according to the model lowerLimit+(1-lowerLimit)*POW(value,gamma)
	* 
	* @param value The value of the gamma-compressed color channel to convert
	* @param gamma The value of gamma
	* @param lowerLimit The lower limit (correction) based on the data for a given monitor 
	* @return The uncompressed (linear RGB) value of the given compressed RGB value 
	*/
	private double uncompress(double value, double gamma, double lowerLimit) {
		return lowerLimit + (1-lowerLimit)*Math.pow(value/255, gamma);
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
		// Ignore the labels in the rgbModelValues vector 
		Vector<Double> compressed = new Vector<Double>();
		double gamma = Double.parseDouble(rgbModelValues.get(1));
		compressed.add(compress(linRGB.get(0), gamma, Double.parseDouble(rgbModelValues.get(3))));
		compressed.add(compress(linRGB.get(1), gamma, Double.parseDouble(rgbModelValues.get(5))));
		compressed.add(compress(linRGB.get(2), gamma, Double.parseDouble(rgbModelValues.get(7))));	
		return compressed;
	}
	
	/**
	* Converts a linear RGB value for a specific color channel (red, green, or blue) 
	* to a gamma-compressed, device-specific RGB value based on a value of
	* gamma and a lower limit (the lowest value in the data for a given display).
	* Uses the model: POWER((value-lowerLimit)/(1-lowerLimit), 1/gamma)
	* 
	* @param value The value of the linear RGB color channel to compress 
	* @param gamme The value of gamma
	* @param lowerLimit The value of the lower limit (correction) based on display data 
	* @return The gamma-compressed value of the given linear RGB value in the range [0,255] 
	*/
	private double compress(double value, double gamma, double lowerLimit) {
		return 255*constrain(Math.pow((value - lowerLimit)/(1-lowerLimit), 1/gamma), 0, 1);
	}
	
	/**
	* Converts a device-specific RGB color to an equivalent color
	* in the CIEXYZ color space using a matrix loaded from a given CSV file
	* 
	* @param rgb The RGB color to convert 
	* @return A Vector representing the X, Y, and Z values for this RGB color 
	*/
	private Vector<Double> RGBToXYZ(Vector<Double> rgbVector) {
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
		for(int i = 0; i < rgbVector.size(); i++) {
			// Linear RGB should be in the range [0,1] 
			rgbVector.set(i, constrain(rgbVector.get(i), 0, 1));
		}
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
	
	/**
	* Ensures that a value falls in a range given by min and max.
	* Help from http://stackoverflow.com/questions/17933493/java-limit-number-between-min-and-max 
	* 
	* @param value The value on which to do the range check
	* @param min The lowest valid value in the range 
	* @param max The highest valid value in the range, inclusive
	* @return The value if it falls within the range; min if the value is less than min; max if the value is greater than max
	*/
	private double constrain(double value, double min, double max) {
		return Math.min(Math.max(min, value), max);
	}
	
	/**
	* Reads in a CSV file and returns a Vector representation of its contents. It will
	* split the contents based on commas and whitespace and will trim white space off each element
	* before adding it to the Vector. 
	* 
	* @param file The name of the CSV file
	* @return A Vector<String> representing the contents of the CSV file
	*/
	private Vector<String> parseCSV(String file) throws FileNotFoundException{
		Scanner in = new Scanner(new File(file));
		// Split on commas or whitespace (such as \n)  
		in.useDelimiter(",|\\n+");
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
    private static Vector<Double> multiply(Vector<Double> a, Vector<Double> x, int m, int n) {
        if (x.size() != n) throw new RuntimeException("Illegal matrix dimensions.");
        Vector<Double> y = new Vector<Double>();
		double sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum += a.get(i + m*j) * x.get(j);
			}
			y.add(sum);
			sum = 0;
		}
        return y;
    }
}
