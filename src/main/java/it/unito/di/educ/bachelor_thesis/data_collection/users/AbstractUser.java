package it.unito.di.educ.bachelor_thesis.data_collection.users;
import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.data_collection.Sample;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class represents an AbstractUser as a collection of samples.
 * 
 * @author Eddy Bertoluzzo
 */

public abstract class AbstractUser {
	
	protected String userName;			/* The user name */
	protected String userPath;			/* The user path */
	protected Sample[] allUserSamples;	/* An array containing all Samples collected for this user */
	
	/**
	* Constructor for this class
	* @param userName the name for this user.
	* @param userPath the user's profiles path.
	*/
	public AbstractUser(String userName, String userPath) {
		this.userName = userName;
		this.userPath = userPath;
	}
	
	/**
	 * Returns this user's name.
	 * @return this user's name.
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Returns all samples collected for this user.
	 * @return an array of Sample objects containing all samples collected for this user.
	 */
	public Sample[] getAllUserSamples() {
		return allUserSamples;
	}
	
	/**
	 * Collects and stores into class field allUserSamples a series of Sample objects created by reading 
	 * the samples from this user's userPath.<br> 
	 * The maximum number of collected Samples is specified by the parameter maxNumberSamples.<br>
	 * The samples are collected randomly if there are less then the maxNumberSamples specifies.
	 * @param maxNumSamples maximum number of samples that can be collected.
	 * @return false if the number of samples for this User are less then the minNumberOfSamples or cannot be read correctly, true otherwise. 
	 */
	public boolean collectSamples(int maxNumSamples) {
		
		String sampleName;
		String samplePath;
		int sampleIndex;
		Random rand;
		List<Integer> randomList;
		
		File samplesDir = new File(userPath);
		File[] samplesFileList = samplesDir.listFiles();
		Arrays.sort(samplesFileList);
		
		if(samplesFileList.length < Options.getInstance().getMinNumberOfSamples())		/* Checks user validity */
			return false;
		
		rand = new Random();							/* creates a list of random numbers (the size is defined by the total number of samples in the directory) */
		randomList = new ArrayList<Integer>();
		for(int i = 0; i < samplesFileList.length; i++)
			randomList.add(i);
		
		maxNumSamples = Math.min(samplesFileList.length, maxNumSamples);		/* sets the number of samples that will be collected */
		allUserSamples = new Sample[maxNumSamples];

		if(samplesFileList != null) {
			for(int i = 0; i < maxNumSamples; i++) {
				
				if(maxNumSamples <= Options.getInstance().getMaxNumberOfSamples()) 				/* if you can collect all the samples in the directory, do it in order */
					sampleIndex = i;
				else {																/* else choose the samples randomly */
					do {sampleIndex = rand.nextInt(samplesFileList.length);} 
					while (!randomList.remove(new Integer(sampleIndex)));
				}
				
				if(samplesFileList[sampleIndex].isFile()) {							/* collects the sample at the specified index */
					
					sampleName = samplesFileList[sampleIndex].getName();
					samplePath = samplesFileList[sampleIndex].getPath();
					
					allUserSamples[i] = new Sample(sampleName, samplePath, userName);
					if(allUserSamples[i].collectNGraphs())
						System.out.println("Loaded sample <"+ allUserSamples[i].getSampleName() +">");
					else
						return false;
				}
				
			}
			
			//Printing instructions
			String toPrint = "SYSTEM: Loaded  user <" + userName + ">";
			if(userName.length() < 11)
				for(int j = userName.length(); j < 10; j++)
					toPrint += " ";
			System.out.println(toPrint + "profile. Number of samples: <"+ allUserSamples.length +">.");
			
		}
		return true;
	}
	
	@Override
	public boolean equals(Object u){
		return ((User)this).getUserName().equals(((User)u).getUserName());
	}
	
	public String toString() {
		String result = "UserName:" + userName + "\nUserPath: " + userPath + "\n";
		for(Sample s : allUserSamples)
			result += s.toString();
		return result;
	}
}
