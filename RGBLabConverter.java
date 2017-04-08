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
	
	private String matrixFile;
	
	public RGBLabConverter(String matrixFile) {
		this.matrixFile = matrixFile;
	}
	
	/**
	* Converts an RGB color to an equivalent color in the CIEL*a*b* color space 
	*/
	public Vector<Double> RGBToLab(int rgbColor) {
		return null;
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to an equivalent RGB color
	*/
	public int LabToRGB(Vector<Double> lab) {
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
	*/
	private void RGBToXYZ() {
		
	}
	
	/**
	* Converts a color in the CIEXYZ color space to device-specific 
	* RGB using a matrix loaded from a given CSV file
	*/
	private void XYZToRGB() {
		
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