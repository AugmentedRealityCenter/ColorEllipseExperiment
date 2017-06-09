public class Trials {
	public int yes;
	public int total;
	
	public Trials(int yes, int total) {
		this.yes = yes;
		this.total = total;
	}
	
	public Trials() {
		this(0, 0);
	}
	
	public String toString() {
		return this.yes + " yes out of " + this.total + " trials";
	}
}