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
	
	public RGBLabConverter(String matrixFile) throws FileNotFoundException {
		this.matrices = parseCSV(matrixFile);
	}
	
	/**
	* Converts an RGB color to an equivalent color in the CIEL*a*b* color space 
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
	*/
	public int LabToRGB(Vector<Double> lab) {
		XYZToRGB(null);
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
	* @param n The number of columns
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
	* equivalent device-specific, linear RGB color using device readings
	* from a given CSV file 
	*/
	private void compressedRGBToLinearRGB(int compRGB) {
		
	}
	
	/**
	* Converts device-specific linear RGB color to an equivalent
	* gamma-compressed, device-specific RGB color using device
	* readings from given CSV file
	*/
	private int linearRGBToCompressedRGB() {
		return 0;
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
	private int XYZToRGB(Vector<Double> xyz) {
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
		
		for(Double d : xyzTorgb) {
			System.out.println(d);
		}
		
		return 0;
	}
	
	/**
	* Converts a color in the CIEXYZ color space to an
	* equivalent color in CIEL*a*b* using formulas from
	* https://en.wikipedia.org/wiki/Lab_color_space
	*/
	private Vector<Double> XYZToLab() {
		return null;
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to
	* an equivalent color in CIEXYZ using formulas from
	* https://en.wikipedia.org/wiki/Lab_color_space 
	*/
	private void LabToXYZ(Vector<Double> lab) {
		
	}
	
}