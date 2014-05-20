package it.unito.di.educ.bachelor_thesis.data_collection;

import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.data_collection.users.Impostor;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * This class defines the methods used to collect and load into the system a representation of the 
 * users and impostors from the file system.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class UserCollector {
	
	private static UserCollector instance;
	
	private static List<User> usersList; 				/* support variable for the correct working of the threaded collection of users */
	private static LinkedList<User> reducedUsersList;
	private static Impostor impostorSamples;
	
	public static UserCollector getInstance() {
		
		if(instance == null)
			instance = new UserCollector();
		
		return instance;
		
	}
	
	/**
	 * This method returns the list of collected users; always call the collectUsers method at least once before trying to retrieve the list.
	 * @return the list of collected users;
	 */
	public User[] getUsersList() {
		
		if(usersList != null)
			return usersList.toArray(new User[usersList.size()]);
		else 
			System.err.println("You must collect the impostors before trying to get them.");
		
		return null;
	}
	
	/**
	 * This method returns the list of collected impostors; always call the collectImpostors method at least once before trying to retrieve the list.
	 * @return the list of collected impostors;
	 */
	public Impostor getImpostors() {
		
		if(impostorSamples != null)
			return impostorSamples;
		else 
			System.err.println("You must collect the impostors before trying to get them.");
		
		return null;
	}
	
	/**
	 * This method returns the original list of users without the sample of the specified index.
	 * @param sampleToSkipIndex the index of the sample to skip.
	 * @return
	 */
	public User[] getReducedUsersList(int sampleToSkipIndex) {
		
		User[] reducedUsersList = new User[usersList.size()];
		
		int i = 0;
		for(User u : usersList) {
			reducedUsersList[i] = u.getReducedUserCopy(sampleToSkipIndex);
			i++;
		}
		
		return reducedUsersList;
			
	}
	
	/**
	 * Collects the impostors' samples from the directory specified in the parameter path and stores 
	 * them as samples in an object of the class User named as the directory specified.
	 * @param path the string representing the path of the folder containing the impostors' samples.
	 */
	public void collectImpostors(String path) {
		File impostorsDir = new File(path);
		Impostor impostorSamples = null;
		
		if(impostorsDir != null && impostorsDir.isDirectory()) {
			impostorSamples = new Impostor(impostorsDir.getName(), impostorsDir.getPath());
			impostorSamples.collectSamples();
		}
		
		UserCollector.impostorSamples = impostorSamples;
	}
	
	/**
	 * Collects all the users from the directory specified in the parameter path and stores them in 
	 * a List of User objects.
	 * @param path the string representing the path of the folder containing the users.
	 */
	public void collectUsers(String path) {
		File usersParentDir = new File(path);
		File[] usersDirList = usersParentDir.listFiles();
		User temp;
		usersList = new LinkedList<User>();
		reducedUsersList = new LinkedList<User>();
		
		String userName;
		String userPath;
		UserCollector sc = new UserCollector();
		SampleCollectorThread[] sampleCollectorThreadList = new SampleCollectorThread[usersDirList.length];
		
		if(usersDirList != null) {
			for(int i = 0; i < usersDirList.length; i++) {
				if(usersDirList[i].isDirectory()) {
					userName = usersDirList[i].getName();
					userPath = usersDirList[i].getPath();
					temp = new User(userName, userPath);
					usersList.add(temp);
					reducedUsersList.add(temp);
					sampleCollectorThreadList[i] = sc.new SampleCollectorThread(temp);
					sampleCollectorThreadList[i].start();
				}
			}
		}
		
		for(SampleCollectorThread s : sampleCollectorThreadList){
			try {
				s.join();
			} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		System.out.println("SYSTEM: Created <" +usersList.size()+"> users.");
		
	}
	
	/**
	 *  This class is used by UserCollector.collectUsers(String path) in order to implement a threaded
	 *  building of the result.
	 */
	private class SampleCollectorThread extends Thread {
		
		private User user;
		
		public SampleCollectorThread(User user){
			this.user = user;
		}
		
		public void run() {
			if(!user.collectSamples()) { 
				System.out.println("SIZE: "+usersList.size());
				System.out.println("SYSTEM: User <" +user.getUserName() +"> removed from the System: " + usersList.remove(user));
				SystemController.getInstance().sendMessageToGui("<"+user.getUserName()+"> skipped becouse of invalid Samples");
			}
		}
	}
}
