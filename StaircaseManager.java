/**
* This class provides a way to manage a staircase procedure for finding
* the threshold of psychophysical stimuli (for example, hearing screenings or
* color discrimination tests). When a test subject responds to a stimulus,
* the stimulus should become weaker. If they do not respond to a stimulus,
* the stimulus should become stronger so they have a better chance of 
* sensing it. 
*/

public class StaircaseManager {
	
	private double curVal;
	private double stepSize;
	private double correctDirection;
	
	/**
	* Constructor - requires a starting value, a step size, and an indication of whether 
    * the stimulus should increase or decrease numerically when a test subject responds. 
	* (For example, if the starting value is 5 and the step size is 1 and a user can detect the 
	* stimulus at 5, then we move to 4. If they cannot detect it, we move to 6. So,
	* when a test subject detects the stimulus, we actually decrease it - the third
	* parameter of this method should be false. If we instead went from 5 to 6 when the
	* test subject responded, the third parameter would be true to indicate that the stimulus
	* value should increase when they detect it.) 
	* 
	* @param startValue The value at which to start the staircase 
	* @param stepSize The size of each step - when a user responds to a stimulus, what is the amount we should increase or decrease?
	* @param correctDirection true if a correct response indicates the stimulus should be increased, false otherwise 
	*/
	public StaircaseManager(double startValue, double stepSize, boolean shouldIncrease) {
		this.curVal = startValue;
		this.stepSize = stepSize;
		this.correctDirection = shouldIncrease ? 1 : -1;
	}
	
	/**
	* Gets the next stimulus value to give to the user based on whether
	* they responded correctly to the previous stimulus. 
	* 
	* @param isCorrect true if the user correctly responded to the last stimulus, false otherwise 
	* @return The value of the next stimulus 
	*/
	public double getNextVal(boolean isCorrect) {
		if(isCorrect) {
			curVal += correctDirection * stepSize;
		} else {
			curVal -= correctDirection * stepSize;
		}
		return curVal;
	}
	
}