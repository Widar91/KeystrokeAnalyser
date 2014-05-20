package it.unito.di.educ.bachelor_thesis.data_collection.users;

import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.utils.Options;

/**
 * This class implements a User as a collection of samples.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */
public class User extends AbstractUser{
	
	private double distanceModel;		/* The model of this user's typing habits */
	
	/**
	* Constructor for this class
	* @param userName the name for this user.
	* @param userPath the user's profiles path.
	*/
	public User(String userName, String userPath) {
		super(userName, userPath);
		distanceModel = 0.0;
	}
	
	/**
	 * Returns the value of m(User) where m is the model of this user's typing habits.
	 * @return a double representing the model of this user's typing habits.
	 */
	public double getDistanceModel() {
		return distanceModel;
	}
	
	/**
	 * Sets the value of distanceModel to the one passed as parameter.
	 * @param distanceModel the value of the model of this user's typing habits.
	 */
	public void setDistanceModel(double distanceModel) {
		this.distanceModel = distanceModel;
	}
	
	/**
	 * Returns all samples collected for this user .
	 * @return an array of Sample objects containing all samples collected for this user except for the one at the specified index.
	 */
	public User getReducedUserCopy(int sampleToSkipIndex) {
		
		User u = new User(this.userName, this.userPath);
		
		int j = 0;
		Sample[] reducedUserSamples = new Sample[this.allUserSamples.length - 1];
		
		for(int i = 0; i< allUserSamples.length; i++) {
			if(i != sampleToSkipIndex) {
					reducedUserSamples[j] = allUserSamples[i];
					j++;
			}
		}
		
		u.allUserSamples = reducedUserSamples;
		
		return u;
	}
	
	/**
	 * Collects and stores into class field allUserSamples a series of Sample objects created by reading 
	 * the samples from this user's userPath.<br> 
	 * The maximum number of collected Samples is specified by the field maxNumberOfSamples in class Options.<br>
	 * @return false if the number of samples for this User are less then the minNumberOfSamples or cannot be read correctly, true otherwise. 
	 */
	public boolean collectSamples() {
		
		return super.collectSamples(Options.getInstance().getMaxNumberOfSamples());
		
	}
	
}