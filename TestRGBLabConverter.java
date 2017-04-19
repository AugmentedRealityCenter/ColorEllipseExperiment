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
			/*Vector<String> list = RGBLabConverter.parseCSV("DisplayMeasurement\\dell_s2240m_matrices.csv");
			System.out.println(list.size());
			for(String s : list) {
				System.out.println(s);
			}*/
			Vector<Double> D65 = new Vector<Double>();
			D65.add(95.047);
			D65.add(100.0);
			D65.add(108.883);
			RGBLabConverter converter = new RGBLabConverter("DisplayMeasurement\\dell_s2240m_matrices.csv", "DisplayMeasurement\\model.csv", D65);
			converter.RGBToLab(65793);
			Vector<Double> test = new Vector<Double>();
			test.add(70.0);
			test.add(40.0);
			test.add(50.0);
			converter.LabToRGB(test);
		} catch(Exception e) {
			System.out.println("Try again - you can do it!");
			System.out.println(e);
		}
	}
}