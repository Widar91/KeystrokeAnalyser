package it.unito.di.educ.bachelor_thesis.data_collection.users;

/**
 * This class implements an Impostor as a collection of samples.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class Impostor extends AbstractUser {
	
	/**
	* Constructor for this class
	* @param userName the name for this user.
	* @param userPath the user's profiles path.
	*/
	public Impostor(String userName, String userPath) {
		super(userName, userPath);
	}
	
	/**
	 * Collects and stores into class field allUserSamples a series of Sample objects created by reading 
	 * the samples from this user's userPath.<br> 
	 * @return false if the number of samples for this User are less then the minNumberOfSamples or cannot be read correctly, true otherwise. 
	 */
	public boolean collectSamples() {
		
		return super.collectSamples(Integer.MAX_VALUE);
		
	}
	
}
