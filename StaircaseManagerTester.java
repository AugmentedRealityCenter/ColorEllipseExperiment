public class StaircaseManagerTester {
	public static void main(String[] args) {
		StaircaseManager s = new StaircaseManager(5, 1, false);
		System.out.println("Expected 4, actual: " + s.getNextVal(true));
		System.out.println("Expected 5, actual: " + s.getNextVal(false));
		StaircaseManager m = new StaircaseManager(5.5, 0.5, true);
		System.out.println("Expected 5, actual: " + m.getNextVal(false));
		System.out.println("Expected 5.5, actual: " + m.getNextVal(true));
	}
}