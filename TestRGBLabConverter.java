/**
* Used to test the RGBLabConverter class
* 
* @author Caroline Danzi
* @version 2017-04-08
*/
import java.util.Vector;
public class TestRGBLabConverter {
	public static void main(String[] args) {
		try {
			Vector<String> list = RGBLabConverter.parseCSV("DisplayMeasurement\\dell_s2240m_matrices.csv");
			System.out.println(list.size());
			for(String s : list) {
				System.out.print(s + ", ");
			}
		} catch(Exception e) {
			System.out.println("Try again - you can do it!");
			System.out.println(e);
		}
	}
}