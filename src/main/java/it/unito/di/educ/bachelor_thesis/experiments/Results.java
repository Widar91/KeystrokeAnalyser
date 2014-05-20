package it.unito.di.educ.bachelor_thesis.experiments;

import it.unito.di.educ.bachelor_thesis.utils.Options;

import java.text.DecimalFormat;

/**
 * Utility class for the collection of experiments' results.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class Results {
	
	private String task;
	private int numberOfAttempts;
	private int numberOfSuccesses;
	private int numberOfFailures;
	private int numberOfFalseAcceptances;
	private int numberOfFalseRejections;
	private int numberOfUsers;
	private int numberOfImpostors;
	private int numberOfSamplesPerUser;
	private int min;
	private int sec;
	
	/**
	* Constructor for this class.
	* @param task the string representing the task to be performed by the tester.
	*/
	public Results(String task) {
		this.task = task; 
		this.numberOfAttempts = 0;
		this.numberOfSuccesses = 0;
		this.numberOfFailures = 0;
		this.numberOfFalseAcceptances = 0;
		this.numberOfFalseRejections = 0;
		this.numberOfUsers = 0;
		this.numberOfImpostors = 0;
		this.numberOfSamplesPerUser = 0;
		this.min = 0;
		this.sec = 0;
	}
	
	/**
	 * Returns the total number of Classification/Identification/Authentication attempts made by the system.
	 * @return the total number of attempts.
	 */
	public int getNumberOfAttempts() {
		return numberOfAttempts;
	}
	
	/**
	 * Increases the number of attempts.
	 */
	public synchronized void increaseNumberOfAttempts() {
		this.numberOfAttempts++;
	}
	
	/**
	 * Returns the total number of Classification/Identification/Authentication successes made by the system.
	 * @return the total number of successes.
	 */
	public int getNumberOfSuccesses() {
		return numberOfSuccesses;
	}
	
	/**
	 * Increases the number of successes.
	 */
	public synchronized void increaseNumberOfSuccesses() {
		this.numberOfAttempts++;
		this.numberOfSuccesses++;
	}
	
	/**
	 * Returns the total number of Classification/Identification/Authentication failures made by the system.
	 * @return the total number of failures.
	 */
	public int getNumberOfFailures() {
		return numberOfFailures;
	}
	
	/**
	 * Increases the number of failures.
	 */
	public synchronized void increaseNumberOfFailures() {
		this.numberOfAttempts++;
		this.numberOfFailures++;
	}
	
	/**
	 * Returns the total number of Identification/Authentication false acceptances made by the system.
	 * @return the total number of false acceptances.
	 */
	public int getNumberOfFalseAcceptances() {
		return numberOfFalseAcceptances;
	}
	
	/**
	 * Increases the number of false acceptances.
	 */
	public synchronized void increaseNumberOfFalseAcceptances() {
		this.numberOfFalseAcceptances++;
	}
	
	/**
	 * Returns the total number of Identification/Authentication false rejections made by the system.
	 * @return the total number of false rejections.
	 */
	public int getNumberOfFalseRejections() {
		return numberOfFalseRejections;
	}
	
	/**
	 * Increases the number of false rejections.
	 */
	public synchronized void increaseNumberOfFalseRejections() {
		this.numberOfFalseRejections++;
	}
	
	/**
	 * Sets the number of users collected.
	 * @param numberOfUsers number of collected users.
	 */
	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}
	
	/**
	 * Sets the number of impostors collected.
	 * @param numberOfImpostors number of collected impostors.
	 */
	public void setNumberOfImpostors(int numberOfImpostors) {
		this.numberOfImpostors = numberOfImpostors;
	}
	
	/**
	 * Sets the number of samples collected per user.
	 * @param numberOfSamplesPerUser number of collected samples per user.
	 */
	public void setNumberOfSamplesPerUser(int numberOfSamplesPerUser) {
		this.numberOfSamplesPerUser = numberOfSamplesPerUser;
	}

	/**
	 * Sets the seconds for the task performance time.
	 */
	public void setSec(int sec) {
		this.sec = sec;
	}
	
	/**
	 * Sets the minutes for the task performance time.
	 */
	public void setMin(int min) {
		this.min = min;
	}
	
	/**
	 * Returns the value of FAR for this results.
	 * @return the FAR.
	 */
	public double getFAR() {
		return (double)(numberOfFalseAcceptances * 100)/((numberOfUsers*(numberOfUsers-1)*numberOfSamplesPerUser)+numberOfImpostors*numberOfUsers);
	}
	
	/**
	 * Returns the value of FRR for this results.
	 * @return the FRR.
	 */
	public double getFRR() {
		return (double)(numberOfFalseRejections * 100)/(numberOfUsers*numberOfSamplesPerUser);
	}
	
	/**
	 * Returns the % of Errors.
	 * @return the % of Errors.
	 */
	public double getErrorsPercentage() {
		return (double)numberOfFailures*100/numberOfAttempts;
	}

	public String toString() {
		return 	"Task: "+ task +
				"\nElapsed Time: "+ min +"m "+ sec +"s" +
				"\nNumber of Users: " + numberOfUsers +
				"\nNumber of Samples per User: " + numberOfSamplesPerUser +
				"\nNumber of Impostors: " + numberOfImpostors +
				"\nAttempts: "+ numberOfAttempts +
				"\nSuccesses: ["+ numberOfSuccesses +"]" +
				"\nFailures: ["+ numberOfFailures +"]" +
				"\nErrors: " + new DecimalFormat("#.####").format(getErrorsPercentage()) + "%" +
				"\nFalse Acceptances: ["+ numberOfFalseAcceptances +"]" +
				"\nFalse Rejections: ["+ numberOfFalseRejections +"]" +
				"\nFAR: " + new DecimalFormat("#.####").format(getFAR()) + "%" +
				"\nFRR: " + new DecimalFormat("#.####").format(getFRR()) + "%" +
				"\n\nOptions:\n"+ Options.getInstance().optionsSummary();
	}
	
}
