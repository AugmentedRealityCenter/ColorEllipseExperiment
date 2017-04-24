/**
* Used to test the RGBLabConverter class
* Allows a user to input a RGB color to test, and then a Lab color to test 
* 
* @author Caroline Danzi
* @version 2017-04-08
*/
import java.util.Vector;
import java.util.Scanner;
public class TestRGBLabConverter {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		try {
			// Set the white point 
			Vector<Double> D65 = new Vector<Double>();
			D65.add(95.047);
			D65.add(100.0);
			D65.add(108.883);
			
			// Create the color converter using the matrices and model loaded from files 
			RGBLabConverter converter = new RGBLabConverter("DisplayMeasurement\\dell_s2240m_matrices.csv", "DisplayMeasurement\\model.csv", D65);
			
			// Convert an RGB color to lab 
			System.out.println("Converting RGB to Lab");
			System.out.print("R: ");
			int rgb = in.nextInt() << 16;
			System.out.print("G: ");
			rgb = rgb | (in.nextInt() << 8);
			System.out.print("B: ");
			rgb = rgb | in.nextInt();
			Vector<Double> lab = converter.RGBToLab(rgb);
			System.out.println("Resulting Lab color:");
			System.out.println("L: " + lab.get(0) + "; a: " + lab.get(1) + "; b: " + lab.get(2) + "\n");
			
			// Convert a Lab color to RGB  
			Vector<Double> test = new Vector<Double>();
			System.out.println("Converting Lab to RGB");
			System.out.print("L: ");
			test.add(in.nextDouble());
			System.out.print("a: ");
			test.add(in.nextDouble());
			System.out.print("b: ");
			test.add(in.nextDouble());
			int ret = converter.LabToRGB(test);
			System.out.println("Resulting rgb: " + ret + "(R: " + (ret >> 16) + "; G: " + (ret >> 8 & 0xFF) + "; B: " + (ret & 0xFF) + ")");
			
		} catch(Exception e) {
			System.out.println("Try again - you can do it!");
			System.out.println(e);
		}
	}
}