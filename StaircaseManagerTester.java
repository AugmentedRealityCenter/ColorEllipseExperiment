import java.util.Scanner;
public class StaircaseManagerTester {
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(System.in);
		System.out.print("Starting value: ");
		double start = in.nextDouble();
		System.out.print("Step size: ");
		double step = in.nextDouble();
		System.out.print("Enter '1' if when a test subject responds correctly, the intensity should increase numerically ");
		System.out.print("ex: if the current intensity is 5 and the step size is 1 and a test subject is able to detect it, "); 
		System.out.print("enter true if the next intensity should be 6) or '0' if it should decrease numerically");
		int direction = in.nextInt();
		boolean increase = direction == 1 ? true : false;
		StaircaseManager s = new StaircaseManager(start, step, increase);
		String test;
		
		while(!s.thresholdReached()) {
			test = in.next();
			if(test.equalsIgnoreCase("t")) {
				System.out.println("Next value: " + s.getNextVal(true));
			} else if(test.equalsIgnoreCase("f")) {
				System.out.println("Next value: " + s.getNextVal(false));
			} else if(test.equalsIgnoreCase("q")) {
				System.exit(0);
			} else if(test.equalsIgnoreCase("break")) {
				break;
			}
		}
		System.out.println("Threshold: " + s.getThreshold());
	}
}