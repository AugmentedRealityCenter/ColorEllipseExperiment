/**
* This class provides a way to manage a staircase procedure for finding
* the threshold of psychophysical stimuli (for example, hearing screenings or
* color discrimination tests). When a test subject responds to a stimulus,
* the stimulus should decrease in intensity. If they do not respond to a stimulus,
* the stimulus should increase in intensity so they have a better chance of 
* sensing it. The trials stop when the test subject has not responded to a stimulus
* in at least X number of trials (defined in parameters). 
* 
* The threshold is the intensity at which >=50% of the responses are 'yes'. According to
* the American Speech and Hearing Association, "[y]ou have found threshold when you find 
* the lowest level at which the person responds in at least 50% of three trials”  
* 
* @author Caroline Danzi
* @version 2017-05-07 
*/
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class StaircaseManager {
	
	private double curVal;
	private double stepSize;
	private double correctDirection;
	private final int NUM_NOS_TO_STOP = 3;
	private boolean thresholdReached;
	HashMap<Double, Trials> intensityTrials;
	
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
	* @param correctDirection true if a correct response indicates the stimulus should increase numerically, false otherwise 
	*/
	public StaircaseManager(double startValue, double stepSize, boolean shouldIncrease) {
		this.curVal = startValue;
		this.stepSize = stepSize;
		this.correctDirection = shouldIncrease ? 1 : -1;
		this.thresholdReached = false;
		intensityTrials = new HashMap<Double, Trials>();
	}
	
	/**
	* Gets the next stimulus value to give to the user based on whether
	* they responded correctly to the previous stimulus. 
	* 
	* @param isCorrect true if the user correctly responded to the last stimulus, false otherwise 
	* @return The value of the next stimulus 
	*/
	public double getNextVal(boolean isCorrect) {
		Trials t = intensityTrials.get(curVal);
		if(isCorrect) {
			if(t == null) {
				// New intensity value 
				intensityTrials.put(curVal, new Trials(1, 1));
			} else {
				// Update the number of trials 
				t = new Trials(t.yes + 1, t.total + 1);
				intensityTrials.put(curVal, t);
			}
			// Add a step in the correct direction to get the next value 
			curVal += correctDirection * stepSize;
		} else {
			// Each time you update the 'no's', check to see if you have reached
			// three - if you have, you have reached the stop condition 
			if(t == null) {
				// New intensity value 
				intensityTrials.put(curVal, new Trials(0, 1));
			} else {
				// Update the number of trials
				t = new Trials(t.yes, t.total + 1);
				intensityTrials.put(curVal, t);
				// If the number of no's is equal to or greater than the number of no's 
				// required for our stop condition, then we have reached the threshold 
				if(t.total - t.yes >= NUM_NOS_TO_STOP) {
					thresholdReached = true;
				}
			}
			// Subtract a step in the correct direction to get the next value 
			curVal -= correctDirection * stepSize;
		}
		return curVal;
	}
	
	/**
	* Returns true if we have reached the threshold according to the 
	* termination condition; false otherwise. False indicates that 
	* more trials are needed in order to determine the threshold. The threshold
	* is reached when a test subject has not responded to the stimulus at a single
	* intensity in a specified number of trials. 
	* 
	* @return true if the threshold can be determined from the given data; false otherwise 
	*/
	public boolean thresholdReached() {
		return thresholdReached;
	}
	
	/**
	* Gets the threshold if one has been reached. If the termination condition for
	* this staircase procedure has not yet been reached, an exception will be thrown
	* since a threshold is unable to be determined from the given data. The threshold
	* is the least intense stimulus at which the test subject responded in at least 50% of the trials. 
	* 
	* @return the threshold, if one has been reached 
	*/
	public double getThreshold() throws Exception {
		if(thresholdReached) {
			// Help from http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
			// Based on the direction of increasing intensity, an increase in intensity may in some cases
			// mean increasing values and in some cases decreasing values 
			double threshold = correctDirection < 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
			for (Map.Entry<Double, Trials> entry : intensityTrials.entrySet()) {
				double intensity = entry.getKey();
				System.out.println(intensity+ " " + entry.getValue());
				// If the current intensity is less intense than the current threshold, see if the response 
				// rate is greater than or equal to 50% - if it is, then it is the new threshold 
				// Note that if the direction of increasing intensity is 1, this means that the threshold will be 
				// the LARGEST intensity, whereas if the direction of increase is -1, the threshold will be
				// the SMALLEST intensity 
				if((correctDirection == -1 && intensity < threshold) || (correctDirection == 1 && intensity > threshold)) {
					Trials t = entry.getValue();
					double percentCorrect = (double)t.yes / t.total;
					if(percentCorrect >= .5) {
						threshold = intensity;
					}
				}
			}
			return threshold;
		} else {
			throw new Exception("Tried to get threshold before threshold was reached");
		}
	}
	
}