/**
* This class provides a way to manage a staircase procedure for finding
* the threshold of psychophysical stimuli (for example, hearing screenings or
* color discrimination tests). When a test subject responds to a stimulus,
* the stimulus should decrease in intensity. If they do not respond to a stimulus,
* the stimulus should increase in intensity so they have a better chance of 
* sensing it. 
* 
* The threshold is the intensity at which 50% of the responses are 'yes'. According to
* the American Speech and Hearing Association, "[y]ou have found threshold when you find 
* the lowest level at which the person responds in at least 50% of three trials”  
* 
* @author Caroline Danzi
* @version 2017-05-07 
*/
import java.util.HashMap;

public class StaircaseManager {
	
	private double curVal;
	private double stepSize;
	private double correctDirection;
	private boolean thresholdReached;
	private double threshold;
	HashMap<Double, Integer> intensityTrials;
	
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
		this.thresholdReached = false;
		this.threshold = -1;
		intensityTrials = new HashMap<Double, Integer>();
	}
	
	/**
	* Gets the next stimulus value to give to the user based on whether
	* they responded correctly to the previous stimulus. 
	* 
	* @param isCorrect true if the user correctly responded to the last stimulus, false otherwise 
	* @return The value of the next stimulus 
	*/
	public double getNextVal(boolean isCorrect) {
		// TODO: implement logic for testing for the stop condition and 
		// update thresholdReached as needed 
		if(isCorrect) {
			curVal += correctDirection * stepSize;
		} else {
			curVal -= correctDirection * stepSize;
		}
		return curVal;
	}
	
	/**
	* Returns true if we have reached the threshold according to the 
	* termination condition; false otherwise. False indicates that 
	* more trials are needed in order to determine the threshold. 
	* 
	* @return true if the threshold can be determined from the given data; false otherwise 
	*/
	public boolean thresholdReached() {
		return thresholdReached;
	}
	
	/**
	* Gets the threshold if one has been reached. If the termination condition for
	* this staircase procedure has not yet been reached, an exception will be thrown
	* since a threshold is unable to be determined from the given data. 
	* 
	* @return the threshold, if one has been reached 
	*/
	public double getThreshold() throws Exception {
		if(thresholdReached) {
			// TODO: add logic for determining the threshold, which is the intensity level
			// at which >= 50% of the responses are 'yes' 
			return threshold;
		} else {
			throw new Exception("Tried to get threshold before threshold was reached");
		}
	}
	
}