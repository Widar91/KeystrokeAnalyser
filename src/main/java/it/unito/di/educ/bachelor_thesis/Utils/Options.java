package it.unito.di.educ.bachelor_thesis.Utils;

/**
 * Utility class for the storage of the various options for the experiments.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class Options {
	
	private static Options instance;
	
	private boolean[] rNGraphs; 			/* array of boolean for n-graph usage */
	private boolean[] aNGraphs;			/* array of boolean for n-graph usage */
	private boolean rNormalizedWeight;	/* true if the system must normalize weight for R-measures in the OLD way */
	private boolean aNormalizedWeight;	/* true if the system must normalize weight for A-measures in the OLD way */
	private double t,k;					/* value of T and K constants */
	private int maxNGraphDuration;		/* max duration for an n-graph */
	private int minNGraphDuration;		/* min duration for an n-graph */
	private int maxNumberOfSamples;		/* max number of samples to be collected for each user */
	private int minNumberOfSamples;		/* min number of samples for user validity */
	private int minSharedNGraphs;		/* minimum number of shared graphs (if the quantity is not reached then the distance is set to 1)*/
	private boolean lowerCase;			/* true if keystrokes have to be converted to lower case */
	private String usersFolderPath;		/* the user directory */
	private String impostorsFolderPath;	/* the impostors directory */
	private String task;					/* the task to be performed */


	public static Options getInstance() {
		
		if(instance == null)
			instance = new Options();
		
		return instance;
	}
	
	/**
	Constructor of this class.
	*/
	private Options() {

		rNGraphs = new boolean[4];
		aNGraphs = new boolean[4];
		for(int i = 0; i < 2; i++){
			rNGraphs[i]= true;
			aNGraphs[i]= true;
			rNGraphs[i+2]= false;
			aNGraphs[i+2]= false;
		}
		
		rNormalizedWeight = true;
		aNormalizedWeight = true;
		
		t = 1.05;
		k = 0.5;
		
		maxNGraphDuration = 1000;
		minNGraphDuration = 20;
		maxNumberOfSamples = 15;
		minNumberOfSamples = 15;
		minSharedNGraphs = 10;
		lowerCase = true;
		
		usersFolderPath = "";
		impostorsFolderPath = "";
		task = "";
		
	}

	/**
	* Sets the users' directory.
	* @param path the new users' directory.
	*/
	public void setUserFolder(String path) {
		usersFolderPath = path;
	}
	
	/**
	* Sets the impostors' directory.
	* @param path the new impostors' directory.
	*/
	public void setImpostorsFolder(String path) {
		impostorsFolderPath = path;
	}

	/**
	* Sets if the system has to include
	* in the computation the value of n-graphs
	* for R measure.
	* @param value the boolean value to be set.
	* @param n dimension.
	*/
	public void setRNGraph(boolean value, int n) {
		rNGraphs[n-2] = value;
	}

	/**
	* Sets if the system has to include
	* in the computation the value of n-graphs
	* for A measure.
	* @param value the boolean value to be set.
	* @param n dimension.
	*/
	public void setANGraph(boolean value, int n) {
		aNGraphs[n-2] = value;
	}
	
	/**
	 * Sets if the keystrokes collected have to be converted into lower case.
	 * @param lowerCase
	 */
	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}
	
	/**
	* Sets if the system has to normalize
	* the distances (OLD way) according to the number of common n-graph
	* for R measure.
	* @param value the new value for this option.
	*/
	public void normalizedWeightR(boolean value) {
		rNormalizedWeight = value;
	}

	/**
	* Sets if the system has to normalize
	* the distances (OLD way) according to the number of common n-graph
	* for A measure.
	* @param value the new value for this option.
	*/
	public void normalizedWeightA(boolean value) {
		aNormalizedWeight = value;
	}

	/**
	* Sets the value of T.
	* @param value the new value for this option.
	*/
	public void setT(double value) {
		t = value;
	}

	/**
	* Sets the value of K.
	* @param value the new value for this option.
	*/
	public void setK(double value) {
		k = value;
	}

	/**
	* Sets the maximum value for a n-graph duration.
	* @param value the new value for this option.
	*/
	public void setMaxNGraphDuration(int value) {
		maxNGraphDuration = value;
	}
	
	/**
	* Sets the minimum value for a n-graph duration.
	* @param value the new value for this option.
	*/
	public void setMinNGraphDuration(int minNGraphDuration) {
		this.minNGraphDuration = minNGraphDuration;
	}

	/**
	* Sets the max number of samples to be collected for each user.
	* @param value the new value for this option.
	*/
	public void setMaxNumberOfSamples(int value) {
		maxNumberOfSamples = value;
	}
	
	/**
	* Sets the min number of samples to consider valid one user.
	* @param value the new value for this option.
	*/
	public void setMinNumberOfSamples(int value) {
		minNumberOfSamples = value;
	}
	
	/**
	* Sets the minimum number of shared graphs needed to set the distance between 2 samples != 1.
	* @param value the new value for this option.
	*/
	public void setMinSharedNGraphs(int value) {
		minSharedNGraphs = value;
	}
	
	/**
	* Sets the the task that will be performed.
	* @param value the new value for this option.
	*/
	public void setTask(String task) {
		this.task = task;
	}
	
	/**
	* Returns the users' directory.
	* @return a String representing the users' directory.
	*/
	public String getUserFolderPath() {
		return usersFolderPath;
	}

	/**
	* Returns the impostors' directory.
	* @return a String representing the impostors' directory.
	*/
	public String getImpostorsFolderPath() {
		return impostorsFolderPath;
	}
	
	/**
	* Returns true if the system
	* has to include n-graph in R measure, false otherwise.
	* @param n the dimension.
	*/
	public boolean getRNGraph(int n) {
		return rNGraphs[n-2];
	}

	/**
	* Returns true if the system
	* has to include n-graph in A measure, false otherwise.
	* @param n the dimension.
	* @return the value of this option.
	*/
	public boolean getANGraph(int n) {
		return aNGraphs[n-2];
	}
	
	/**
	 * Specifies if the keystrokes collected have to be converted into lower case.
	 * @return true if they have to be converted, false otherwise.
	 */
	public boolean getLowerCase() {
		return lowerCase;
	}

	/**
	* Returns true if the system
	* has to normalize the distances (OLD way) in R measure, false otherwise.
	* @return the value of this option.
	*/
	public boolean getNormalizedWeightR() {
		return rNormalizedWeight;
	}

	/**
	* This method returns true if the system
	* has to normalize the distances (OLD way) in A measure, false otherwise.
	* @return the value of this option.
	*/
	public boolean getNormalizedWeightA() {
		return aNormalizedWeight;
	}

	/**
	* Returns the value of T.
	* @return the value for this option.
	*/
	public double getT() {
		return t;
	}

	/**
	* Returns the value of K.
	* @return the value for this option.
	*/
	public double getK() {
		return k;
	}

	/**
	* Returns the value of the max n-graph's duration.
	* @return the value for this option.
	*/
	public int getMaxNGraphDuration() {
		return maxNGraphDuration;
	}
	
	/**
	* Returns the value of the min n-graph's duration.
	* @return the value for this option.
	*/
	public int getMinNGraphDuration() {
		return minNGraphDuration;
	}

	/**
	* Returns the max number of sample to be collected for each user.
	* @return the value for this option.
	*/
	public int getMaxNumberOfSamples() {
		return maxNumberOfSamples;
	}
	
	/**
	* Returns the min number of sample for user validity.
	* @return the value for this option.
	*/
	public int getMinNumberOfSamples() {
		return minNumberOfSamples;
	}
	
	/**
	* Returns the minimum number of shared graphs needed to set the distance between 2 samples != 1.
	* @return the value for this option.
	*/
	public int getMinSharedNGraphs() {
		return minSharedNGraphs;
	}
	
	/**
	* Returns the String representing the task that needs to be performed.
	* @return the value for this option.
	*/
	public String getTask() {
		return task;
	}

	/**
	 * Implementation of a static toString.
	 * @return the String summarizing the options.
	 */
	public String optionsSummary(){
		return 	"---------------------------------------------\n" +
				"\tR measures:\n"+
				(rNGraphs[0]? "\t\t     2-graphs: ["+ rNGraphs[0] + "]\n" : "") +
				(rNGraphs[1]? "\t\t     3-graphs: ["+ rNGraphs[1] + "]\n" : "") +
				(rNGraphs[2]? "\t\t     4-graphs: ["+ rNGraphs[2] + "]\n" : "")+
				(rNGraphs[3]? "\t\t     5-graphs: ["+ rNGraphs[3] + "]\n" : "") +
				(rNormalizedWeight? "\t\t     normalize: ["+ rNormalizedWeight + "]\n" : "") +
				"\tA measures:\n"+
				(aNGraphs[0]? "\t\t     2-graphs: ["+ aNGraphs[0] + "]\n" : "") +
				(aNGraphs[1]? "\t\t     3-graphs: ["+ aNGraphs[1] + "]\n" : "") +
				(aNGraphs[2]? "\t\t     4-graphs: ["+ aNGraphs[2] + "]\n" : "") +
				(aNGraphs[3]? "\t\t     5-graphs: ["+ aNGraphs[3] + "]\n" : "") +
				(aNormalizedWeight? "\t\t     normalize: ["+ aNormalizedWeight + "]\n" : "") +
				"\tK Value: " + k + "\n" +
				"\tT Value: " + t + "\n" +
				"\tMin N-graphs duration: " + minNGraphDuration + "\n" +
				"\tMax N-graphs duration: " + maxNGraphDuration + "\n" +
				"\tMin number of Samples: " + minNumberOfSamples + "\n"+
				"\tMax number of Samples: " + maxNumberOfSamples + "\n"+
				"\tMin shared N-Graphs: " + minSharedNGraphs + "\n" +
				"\tConvert to Lower Case: [" + lowerCase + "]\n"+
				"\tTask: " + task;
	}	
}