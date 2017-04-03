/**
* 
* @author Caroline Danzi
* @version 2017-04-02
*/
public class RGBLabConverter {
	
	/**
	* Converts an RGB color to an equivalent color in the CIEL*a*b* color space 
	*/
	public void RGBToLab(int r, int g, int b) {
		
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to an equivalent RGB color
	*/
	public void LabToRGB(double l, double a, double b) {
		
	}
	
	/**
	* Converts a device-specific, gamma-compressed RGB color to an 
	* equivalent device-specific, linear RGB color using device readings
	* from a given CSV file 
	*/
	private void compressedRGBToLinearRGB() {
		
	}
	
	/**
	* Converts device-specific linear RGB color to an equivalent
	* gamma-compressed, device-specific RGB color using device
	* readings from given CSV file
	*/
	private void linearRGBToCompressedRGB() {
		
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
	private void XYZToLab() {
		
	}
	
	/**
	* Converts a color in the CIEL*a*b* color space to
	* an equivalent color in CIEXYZ using formulas from
	* https://en.wikipedia.org/wiki/Lab_color_space 
	*/
	private void LabToXYZ() {
		
	}
	
}