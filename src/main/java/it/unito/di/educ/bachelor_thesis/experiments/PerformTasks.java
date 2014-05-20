package it.unito.di.educ.bachelor_thesis.experiments;

import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.data_collection.UserCollector;
import it.unito.di.educ.bachelor_thesis.data_collection.users.AbstractUser;
import it.unito.di.educ.bachelor_thesis.data_collection.users.Impostor;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;
import it.unito.di.educ.bachelor_thesis.utils.Options;

import java.util.TreeMap;

/**
 * This class implements the methods for the Classification/Identification/Authentication tasks.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class PerformTasks {
	
	private static Results results;		/* Results object that will contain the test results */
	private static User[] usersList;	/* Array of all the system users */
	
	/**
	 * Performs the (Threaded) Classification Test, in which each sample from each user is used as a new sample to be classified by the system.
	 * In the case, for example, of a system with 40 users and 15 samples per user, the Test will perform (15 * 40 = 600) classification attempts.
	 * <p>Implementation note: this method will create a Thread for each User in usersList in order to divide it's workload. As a consequence each 
	 * Thread will perform the Classification Test for each of it's User's samples.</p>
	 * @param usersList the list of all system users.
	 * @return the Results object containing the results of the test.
	 */
	public static Results performClassificationTest(User[] usersList) {
		
		PerformTasks.results = new Results("Classification");
		PerformTasks.usersList = usersList;
		PerformTasks pt = new PerformTasks(); 
		PerformClassificationThread[] threadList = new PerformClassificationThread[usersList.length];
		
		//Creates threads
		for(int i = 0; i < usersList.length; i++) {
			threadList[i] = pt.new PerformClassificationThread(usersList[i]);
			threadList[i].start();
		}
		
		//Waits for threads
		for(PerformClassificationThread thread : threadList) {
			try {
				thread.join();
			} 
			catch (InterruptedException e) {}
		}
		
		return results;
	}
	
	/**
	 *  This class is used by PerformTasks.performClassificationTest(User[] usersList) in order to implement a threaded
	 *  building of the result.
	 */
	private class PerformClassificationThread extends Thread {
		
		private User user;
		
		/**
		 * Constructor for this class.
		 * @param user containing all samples to be tested by this thread.
		 */
		public PerformClassificationThread(User user) {
			this.user = user;
		}
		
		public void run() {
			
			String resultUserName;
			Sample[] allUserSamples;
			
			allUserSamples = user.getAllUserSamples();
			
			for(Sample s : allUserSamples) {
				
				resultUserName = performClassification(s, usersList/*PerformTasks.usersList*/);
				
				if(resultUserName.equals(s.getSampleUserName()))	
					results.increaseNumberOfSuccesses();
				else	
					results.increaseNumberOfFailures();
				
			}
			
		}
		
	}
	
	/**
	* This method performs the Classification of the given Sample with regards to the given userList.
	* @param sample the sample to be classified.
	* @param userList the list of all system users.
	* @return the name of the user to whom this sample was attributed.
	*/
	public static String performClassification(Sample sample, User[] usersList) {
		
		String classificatedUserName;
		TreeMap<Double, String> meanDistances = new TreeMap<Double, String>();
		
		for(User u : usersList) 
			meanDistances.put(new Double(Distance.getMeanDistance(sample, u)), u.getUserName());
		
		classificatedUserName = meanDistances.get(meanDistances.firstKey());
		
		/* Instructions for a correct printing if the result */
		String toPrint = "SYSTEM: Sample <" + sample.getSampleName() + ">";
		if(sample.getSampleName().length() < 11)
			for(int j = sample.getSampleName().length(); j <= 10; j++)
				toPrint += " ";
		toPrint += " belongs to <" + classificatedUserName + ">";
		if(classificatedUserName.length() < 10)
			for(int j = classificatedUserName.length(); j <= 10; j++)
				toPrint += " ";
		System.out.println(toPrint + " with meanDistance = " + meanDistances.firstKey());
		
		return classificatedUserName;
	}
	
	/**
	 * Performs the (Threaded) Authentication Test, in which each sample from each user is used as a new sample to be authenticated 
	 * by the system(Legal attempts).<br>The method will also use each sample from each user (after having removed the sample's 
	 * user's profile from the system) as an impostor sample unknown to the system.<br>At last the system will also be tested with 
	 * a number of samples unknown to the system and known as impostors.<br>In order to increase the number of attempts the system will
	 * use n-1 samples for each user (where n is the num of samples for that user) for the comparison; in this way the system will be able to create 15 
	 * different profiles for each user.<br>
	 * In the case, for example, of a system with 40 users and 15 samples per user + 165 impostors, the Test will perform 
	 * ((40*15) * 40 [Legal Attempts] + (40*15) * 39 [Users' samples used as Impostors] * 15 + 165 * 40 [Impostors' Attempts] * 15) authentication attempts.
	 * <p>Implementation note: this method will create a Thread for each User in usersList and a single one for the User impostor in order to divide 
	 * it's workload. As a consequence each Thread will perform the Authentication Test for each of it's User's samples.</p>
	 * @param usersList the list of all system users.
	 * @param impostorSamples a User object containing all the impostors' samples.
	 * @return the Results object containing the results of the test.
	 */
	public static Results performAuthenticationTest(User[] usersList, Impostor impostorSamples) {
		
		PerformTasks.results = new Results("Authentication");
		PerformTasks.usersList = usersList;
		
		PerformTasks pt = new PerformTasks(); 
		PerformAuthenticationThread[] threadList = new PerformAuthenticationThread[usersList.length+1];
		
		//Creates threads
		for(int i = 0; i < usersList.length; i++) {
			threadList[i] = pt.new PerformAuthenticationThread(usersList[i]);
			threadList[i].start();
		}
		threadList[threadList.length-1] = pt.new PerformAuthenticationThread(impostorSamples);
		threadList[threadList.length-1].start();
		
		//Waits for threads
		for(PerformAuthenticationThread thread : threadList) {
			try {
				thread.join();
			} 
			catch (InterruptedException e) {}
		}
		
		return results;
	}
	
	/**
	 *  This class is used by PerformTasks.performAuthenticationTest(User[] usersList) in order to implement a threaded
	 *  building of the result.
	 */
	private class PerformAuthenticationThread extends Thread {
		
		private AbstractUser abstractUser;
		
		/**
		 * Constructor for this class.
		 * @param abstractUser containing all samples to be tested by this thread.
		 */
		public PerformAuthenticationThread(AbstractUser abstractUser) {
			this.abstractUser = abstractUser;
		}
		
		public void run() {
			
			String resultUserName;
			Sample[] allUserSamples;
			User[] reducedUsersList;
			
			allUserSamples = abstractUser.getAllUserSamples();
			
			/* Performs authentication for each sample in the Users's profile */
			for(Sample sample : allUserSamples) {		
				
				/* LEGAL CONNECTIONS */
				if(abstractUser instanceof User) { 
					
					resultUserName = performIdentification(sample, PerformTasks.usersList, false);									
					manageResult(resultUserName, sample.getSampleUserName(), sample.getSampleUserName(), sample.getSampleName());
					
				}
				
				/* each iteration removes a sample from all profiles in order to increase the number of attempts */
				for(int j = 0; j < Math.min(allUserSamples.length, Options.getInstance().getMaxNumberOfSamples()); j++) { /* This condition is forced by the fact that an impostor is treated as a user with more than the max number of samples
				 																											 I'd like to discuss it in person anyway, so I can code the condition in a better way. */	
					
					reducedUsersList = UserCollector.getInstance().getReducedUsersList(j);
					
					/* if the sample is an Impostor then let's try to authenticate it as each of the legal users --> IMPOSTORS CONNECTION*/
					if(abstractUser instanceof Impostor) { 			
						
						resultUserName = performIdentification(sample, reducedUsersList, false);
						
						for(User u : reducedUsersList) 
							manageResult(resultUserName, u.getUserName(), "impostors", sample.getSampleName());
					
					}
					
					/* else the sample is from a Legal User so let's use it as in Impostor */
					else { 															
						
						/* User's samples used as Impostors (Users's sample's profile is removed from the system) */
						resultUserName = performIdentification(sample, reducedUsersList, true);										
						
						/* for each user */
						for(User u : reducedUsersList)		
							
							/* if the sample does not come from the same user then try to authenticate it */
							if(!(sample.getSampleUserName().equals(u.getUserName())))								
								manageResult(resultUserName, u.getUserName(), "impostors", sample.getSampleName());
						
					}
					
				}
			}
		}
		
		/**
		 * Manages the results of an Authentication task basing on the first 3 parameters.
		 * @param resultUserName the result of the Authentication Task.
		 * @param authenticationUserName the name used for the authentication attempt.
		 * @param actualUserName the real sample's user name.
		 * @param sampleName the name of the sample;
		 */
		private void manageResult(String resultUserName, String authenticationUserName, String actualUserName, String sampleName) {
			
			String resultString;
			
			if(resultUserName == null) {
				if(actualUserName.equalsIgnoreCase("impostors")) {
					results.increaseNumberOfSuccesses();
					resultString = "[OK]";
				}
				else {
					results.increaseNumberOfFailures();
					results.increaseNumberOfFalseRejections();
					resultString = "[FR]";
				}
			}
			else { 
				if(resultUserName.equalsIgnoreCase(authenticationUserName)) {
					if(authenticationUserName.equalsIgnoreCase(actualUserName)) {
						results.increaseNumberOfSuccesses();
						resultString = "[OK]";
					}
					else {
						results.increaseNumberOfFailures();
						results.increaseNumberOfFalseAcceptances();
						resultString = "[FA]";
					}
				}
				else {
					if(authenticationUserName.equalsIgnoreCase(actualUserName)) {
						results.increaseNumberOfFailures();
						results.increaseNumberOfFalseRejections();
						resultString = "[FR]";
					}
					else {
						results.increaseNumberOfSuccesses();
						resultString = "[OK]";
					}
				}
			}
			
			/* Instructions for a correct printing if the result (COMMENT THESE LINES FOR BETTER PERFORMANCE!)
			String toPrint = "SYSTEM: Sample <" + sampleName + ">";
			if(sampleName.length() < 11)
				for(int j = sampleName.length(); j <= 10; j++)
					toPrint += " ";
			toPrint += " as <" + authenticationUserName + ">";
			if(authenticationUserName.length() < 10)
				for(int j = authenticationUserName.length(); j <= 10; j++)
					toPrint += " ";
			toPrint += " Result <" + resultUserName + ">";
			if(resultUserName != null && resultUserName.length() < 10)
				for(int j = resultUserName.length(); j <= 10; j++)
					toPrint += " ";
			else if(resultUserName == null)
				toPrint += "       ";
			System.out.println(resultString + "\t" + toPrint + "Actual User Name <" + actualUserName + ">");*/
		}
	}
	
	/**
	 * Performs the (Threaded) Identification Test, in which each sample from each user is used as a new sample to be identified 
	 * by the system(Legal attempts).<br>The method will also use each sample from each user (after having removed the sample's 
	 * user's profile from the system) as an impostor sample unknown to the system.<br>At last the system will also be tested with 
	 * a number of samples unknown to the system and known as impostors.<br>
	 * In the case, for example, of a system with 40 users and 15 samples per user + 165 impostors, the Test will perform 
	 * ((40*15) [Legal Attempts] + (40*15) [Users' samples used as Impostors] + 165 [Impostors' Attempts]) identification attempts.
	 * <p>Implementation note: this method will create a Thread for each User in usersList and a single one for the User impostor in order to divide 
	 * it's workload. As a consequence each Thread will perform the Identification Test for each of it's User's samples.</p>
	 * @param usersList the list of all system users.
	 * @param impostors a User object containing all the impostors' samples.
	 * @return the Results object containing the results of the test.
	 */
	public static Results performIdentificationTest(User[] usersList, Impostor impostors) {
		
		PerformTasks.results = new Results("Identification");
		PerformTasks.usersList = usersList;
		PerformTasks pt = new PerformTasks(); 
		performIdentificationThread[] threadList = new performIdentificationThread[usersList.length+1];
		
		//Creates threads
		for(int i = 0; i < usersList.length; i++) {
			threadList[i] = pt.new performIdentificationThread(usersList[i]);
			threadList[i].start();
		}
		threadList[threadList.length-1] = pt.new performIdentificationThread(impostors);
		threadList[threadList.length-1].start();
		
		//Waits for threads
		for(performIdentificationThread thread : threadList) {
			try {
				thread.join();
			} 
			catch (InterruptedException e) {}
		}
		
		return results;
	}
	
	/**
	 *  This class is used by PerformTasks.performIdentificationTest(User[] usersList) in order to implement a threaded
	 *  building of the result.
	 */
	private class performIdentificationThread extends Thread {
		
		private AbstractUser abstractUser;
		
		/**
		 * Constructor for this class.
		 * @param abstractUser containing all samples to be tested by this thread.
		 */
		public performIdentificationThread(AbstractUser abstractUser) {
			this.abstractUser = abstractUser;
		}
		
		public void run() {
			
			String resultUserName;
			Sample[] allUserSamples;
			
			allUserSamples = abstractUser.getAllUserSamples();
			
			for(Sample sample : allUserSamples) {
				
				/* Legal Connections + Impostors */
				resultUserName = performIdentification(sample, PerformTasks.usersList, false);
				manageResult(resultUserName, sample.getSampleUserName(), sample.getSampleName());
				
				/* User's samples used as Impostors (Users's sample's profile is removed from the system) */
				if(abstractUser instanceof User) {
					resultUserName = performIdentification(sample, PerformTasks.usersList, true);
					manageResult(resultUserName, "impostors", sample.getSampleName());
				}
			}
		}
		
		/**
		 * Manages the results of an Identification task basing on the first 2 parameters.
		 * @param resultUserName the result of the Identification Task.
		 * @param expectedUserName the expected user name.
		 * @param sampleName the name of the sample;
		 */
		private void manageResult(String resultUserName, String expectedUserName, String sampleName) {
			
			String resultString;
			
			if(resultUserName == null) {
				if(expectedUserName.equalsIgnoreCase("impostors")) {
					results.increaseNumberOfSuccesses();
					resultString = "[OK]";
				}
				else {
					results.increaseNumberOfFailures();
					results.increaseNumberOfFalseRejections();
					resultString = "[FR]";
				}
			}
			else if(resultUserName.equalsIgnoreCase(expectedUserName)) {
				results.increaseNumberOfSuccesses();
				resultString = "[OK]";
			}
			else {
				results.increaseNumberOfFailures();
				results.increaseNumberOfFalseAcceptances();
				resultString = "[FA]";
			}
			
			/* Instructions for a correct printing if the result */
			String toPrint = "SYSTEM: Sample <" + sampleName + ">";
			if(sampleName.length() < 11)
				for(int j = sampleName.length(); j <= 10; j++)
					toPrint += " ";
			toPrint += " Expected <" + expectedUserName + ">";
			if(expectedUserName.length() < 10)
				for(int j = expectedUserName.length(); j <= 10; j++)
					toPrint += " ";
			System.out.println(resultString + "\t" + toPrint + " Result <" + resultUserName + ">");

		}
	}
	
	/**
	* This method performs the Identification of the given Sample with regards to the the given userList.<br> 
	* If removeSampleUserProfile is specified as true, the sample's user's profile is removed from the system before the beginning of the task.
	* @param sample the sample to be identified
	 * @param usersList the list of all system users.
	 * @param removeSampleUserProfile true if the sample's user's profile has to be removed from the system.
	 * @return the name of the user to which this sample was attributed.
	*/
	public static String performIdentification(Sample sample, User[] usersList, boolean removeSampleUserProfile) {
		
		String userName1;
		double mdDistance1, mdDistance2, modelDistance1;
		User user1 = null;
		TreeMap<Double, String> meanDistances = new TreeMap<Double, String>();
		
		for(User u : usersList) {
			
			/* performs identification excluding the sample's user profile if so specified by the boolean removeSampleUserProfile */
			if(!(removeSampleUserProfile && (u.getUserName()).equals(sample.getSampleUserName()))){
				
				//Debug.debug("IF: Sample ["+sample.getSampleName()+"] from <"+ sample.getSampleUserName() +"> confronted with ["+ usersList[i].getUserName()+"]");
				meanDistances.put(new Double(Distance.getMeanDistance(sample, u)), u.getUserName());
				
			}
		}
		//Debug.debug("Sample ["+sample.getSampleName()+"] from <"+ sample.getSampleUserName() +"> removeSampleUserProfile = "+ removeSampleUserProfile);
		
		mdDistance1 = meanDistances.firstKey();
		userName1 = meanDistances.get(mdDistance1);
		
		meanDistances.remove(mdDistance1);
		mdDistance2 = meanDistances.firstKey();
		
		for(User u : usersList) {
			if((u.getUserName()).equals(userName1)) {
				user1 = u;
				break;
			}
		}
		
		modelDistance1 = Distance.getUserModelDistance(user1);

		if(mdDistance1 < modelDistance1)
			return userName1;
		else {
			if(mdDistance1 < modelDistance1 + (Options.getInstance().getK() * (mdDistance2 - modelDistance1)))
				return userName1;
			else
				return null;
		}
	}
	
}
