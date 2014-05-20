package it.unito.di.educ.bachelor_thesis.experiments;

import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;
import it.unito.di.educ.bachelor_thesis.data_collection.NGraph;
import it.unito.di.educ.bachelor_thesis.data_collection.NGraphSet;
import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 * Utility class which implements methods for the calculation of various types of Distances.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class Distance {

	/**
	* Returns the distance md(X,U) between the Sample object(X) and the User object(U) passed as parameters.
	* <p>Implementation note: the computation of the distance between 2 equal samples is skipped.</p>
	* @param sample1 the sample to be confronted.
	* @param user the user to confront the sample with.
	* @return the computed distance.
	*/
	public static double getMeanDistance(Sample sample1, User user) {
		
		Sample[] allUserSamples = user.getAllUserSamples();
		String sampleName1, sampleName2, sampleUserName1, sampleUserName2;
		double distances = 0;
		int equalSamplesNotConfronted = 0;
		
		for(Sample sample2 : allUserSamples) {
			
			sampleName1 = sample1.getSampleName(); 
			sampleName2 = sample2.getSampleName();
			sampleUserName1 = sample1.getSampleUserName();
			sampleUserName2 = sample2.getSampleUserName();
			
			Utils.debug("\nSYSTEM: Comparison sample <" + sampleName1 + "> <" + sampleName2 + "> :");
			
			if(sampleUserName1.equals(sampleUserName2) && sampleName1.equals(sampleName2))		/* Skips the computation of the distance between 2 equal samples */
				equalSamplesNotConfronted++;
			else
				distances += getRDistance(sample1, sample2) + getADistance(sample1, sample2);
			
			Utils.debug("\nSYSTEM: Comparison sample <" + sampleName1 + "> <" + sampleName2 + "> distance: "+distances);
			
		}
		
		Utils.debug("md("+ sample1.getSampleName() +", "+ user.getUserName() +"), equalSamplesNotConfronted: " + equalSamplesNotConfronted + " distances: "+distances+" length: "+allUserSamples.length);
		return distances/(allUserSamples.length - equalSamplesNotConfronted);
	}
	
	/**
	 * Returns the model m(X) of the typing habits of some user X passed as parameter. 
	 * @param user for which to calculate m(X).
	 * @return the value of the computed m(X).
	 */
	public static double getUserModelDistance(User user) {
		
		if(user.getDistanceModel() != 0.0)	
			return user.getDistanceModel();
		
		int distanceCounter = 0;
		double distances = 0.0;
		Sample[] allUserSamples = user.getAllUserSamples();
		
		for(int i = 0; i < allUserSamples.length; i++) {
			for(int j = i+1; j < allUserSamples.length; j++) {
				//Debug.debug("SYSTEM: model <" + allUserSamples[i].getSampleName() + "> <" + allUserSamples[j].getSampleName() + "> ");
				distances += getRDistance(allUserSamples[i], allUserSamples[j]) + getADistance(allUserSamples[i], allUserSamples[j]);
				distanceCounter++;
			}
		}
		
		user.setDistanceModel(distances/distanceCounter);
		
		return distances/distanceCounter;
	}
	
	/**
	* Returns the relative (R)-distance between the 2 Sample objects passed as parameters. 
	* @param sample1 the first Sample object.
	* @param sample2 the second Sample object.
	* @return the computed distance.
	*/
	public static double getRDistance(Sample sample1, Sample sample2) {
		
		Set<NGraph> nGraphSetS1, nGraphSetS2;
		NGraph[] graphArray1, graphArray2;
		int NGraphTypeNumber = 0;										/* this variable keeps track of how many n-graph are being considered (n in [2, 5]) */
		double rDistanceResult = 0.0;
		double maximumDisorder;
		double[] resultDistances = new double[4];
		int[] sharedNGraphCounters = new int[4];
		Options options = Options.getInstance();
		
		
		NGraphSet[] nGraphSetsArrayS1 = sample1.getnGraphSets();		/* retrieves the sets of collected n-graphs from sample1 */
		NGraphSet[] nGraphSetsArrayS2 = sample2.getnGraphSets();		/* retrieves the sets of collected n-graphs from sample2 */
		
		for(int i = 0; i < 4; i++) {
			
			if(!options.getRNGraph(i+2)) {		/* if the R-distance for the n-graph was not selected by the user, the computation is skipped and result is set to 0.0 */
				resultDistances[i] = 0.0;
				continue;
			}
			
			NGraphTypeNumber++;		/* this variable keeps track of how many n-graph are being considered (n in [2, 5]) */
			Utils.debug("NGraphTypeNumber: " + NGraphTypeNumber);
			
			nGraphSetS1 = nGraphSetsArrayS1[i].getNGraphSetCopy();		/* retrieves a copy of the specified NGraph Set from sample1 */
			nGraphSetS2 = nGraphSetsArrayS2[i].getNGraphSetCopy();		/* retrieves a copy of the specified NGraph Set from sample2 */
			
			Utils.debug(""+ (i+2) +"-Graphs s1: "+ nGraphSetS1.size());
			
			nGraphSetS1.retainAll(nGraphSetS2);		/* keeps only the shared NGraph objects */
			nGraphSetS2.retainAll(nGraphSetS1);		/* keeps only the shared NGraph objects */
			
			Utils.debug("nGraphSets shared are equal in size? " + (nGraphSetS1.size() == nGraphSetS2.size()) +" ["+ nGraphSetS1.size() +"]["+ nGraphSetS2.size() +"]");
			
			graphArray1 = new NGraph[nGraphSetS1.size()];
			graphArray2 = new NGraph[nGraphSetS2.size()];
			
			graphArray1 = nGraphSetS1.<NGraph>toArray(graphArray1);		/* puts the elements in the Set into an Array */
			graphArray2 = nGraphSetS2.<NGraph>toArray(graphArray2);		/* puts the elements in the Set into an Array */
			
			Arrays.<NGraph>sort(graphArray1, new NGraphComparer());		/* sorts the Array w.r.t. the NGraph duration */
			Arrays.<NGraph>sort(graphArray2, new NGraphComparer());		/* sorts the Array w.r.t. the NGraph duration */
			
			sharedNGraphCounters[i] = graphArray1.length;				/* saves the number of shared NGraph objects */ 
			
			Utils.debug("Shared "+ (i+2) +"-Graphs ["+ sharedNGraphCounters[i] + "]["+ nGraphSetS1.size() + "]: ");
			
			if(sharedNGraphCounters[i] >= options.getMinSharedNGraphs()){	/* if the shared NGraph num is greater then the min specified by the user */
				if(sharedNGraphCounters[i]%2 == 0)							/* compute the correct value for the Maximum Disorder */
					maximumDisorder = (Math.pow(graphArray1.length, 2))/2;
				else	
					maximumDisorder = (Math.pow(graphArray1.length, 2) -1)/2;
				
				Utils.debug("Max Disorder:" + maximumDisorder);
				resultDistances[i] = ((double)arrayDisorder(graphArray1, graphArray2))/maximumDisorder;		/* compute the R-Distance */
				
			} 
			else 	
				resultDistances[i] = 1;										/* else R-Distance equals 1 */
			
			
			
			nGraphSetS1 = null;
			nGraphSetS2 = null;
			
		}
		
		rDistanceResult = 0.0;
		for(int i = 0; i < 4; i++) {
			if(options.getRNGraph(i+2)){
				if(options.getNormalizedWeightR()) {
					rDistanceResult += resultDistances[i] * sharedNGraphCounters[i]/sharedNGraphCounters[0];
				}
				else 
					rDistanceResult += resultDistances[i];
				//Debug.debug("R"+(i+2)+"= "+resultDistances[i]+" (sh"+sharedNGraphCounters[i]+"); ");
			}
		}
		//Debug.debug("rDistanceResult = " + rDistanceResult);
		//Debug.debug("returned rDistanceResult = " + rDistanceResult/NGraphTypeNumber);

		if(options.getNormalizedWeightR() || rDistanceResult == 0.0)
			return rDistanceResult;
		else
			return rDistanceResult/NGraphTypeNumber;
		
	}
	
	/**
	* Returns the absolute (A)-distance between the 2 Sample objects passed as parameters. 
	* @param sample1 the first Sample object.
	* @param sample2 the second Sample object.
	* @return the computed distance.
	*/
	public static double getADistance(Sample sample1, Sample sample2) {
		
		Set<NGraph> nGraphSetS1, nGraphSetS2;
		int NGraphTypeNumber = 0;
		double aDistanceResult = 0.0;
		double[] resultDistances = new double[4];
		int[] sharedNGraphCounters = new int[4];
		int[] similarNGraphCounters = new int[4];
		Options options = Options.getInstance();

		
		NGraphSet[] nGraphSetsArrayS1 = sample1.getnGraphSets();
		NGraphSet[] nGraphSetsArrayS2 = sample2.getnGraphSets();
		
		for(int i = 0; i < 4; i++) {
			
			if(!options.getANGraph(i+2)) {
				resultDistances[i] = 0.0;
				continue;
			}
			
			NGraphTypeNumber++;
			
			nGraphSetS1 = nGraphSetsArrayS1[i].getNGraphSetCopy();
			nGraphSetS2 = nGraphSetsArrayS2[i].getNGraphSetCopy();
			
			nGraphSetS1.retainAll(nGraphSetS2);
			nGraphSetS2.retainAll(nGraphSetS1);
			
			sharedNGraphCounters[i] = nGraphSetS1.size(); 
			similarNGraphCounters[i] = getSimilarNGraphsCount(nGraphSetS1, nGraphSetS2, options.getT());
			
			if(sharedNGraphCounters[i] >= options.getMinSharedNGraphs())
				resultDistances[i] = 1 - (double)similarNGraphCounters[i] / (double)sharedNGraphCounters[i];
			else 
				resultDistances[i] = 1;
			
			nGraphSetS1 = null;
			nGraphSetS2 = null;
			
		}
		
		for(int i = 0; i < 4; i++) {
			if(options.getANGraph(i+2)){
				
				if(options.getNormalizedWeightA()) {
					aDistanceResult += resultDistances[i] * sharedNGraphCounters[i]/sharedNGraphCounters[0];
				}
				else 
					aDistanceResult += resultDistances[i];
				
				Utils.debug("A"+(i+2)+"("+sample1.getSampleName()+", "+sample2.getSampleName()+") = "+resultDistances[i]+" (sh%"+sharedNGraphCounters[i]+") (similar"+similarNGraphCounters[i]+")");
			}
		}
		
		if(options.getNormalizedWeightA() || aDistanceResult == 0.0)
			return aDistanceResult;
		else
			return aDistanceResult/NGraphTypeNumber;
		
	}
	
	/**
	* Calculates the disorder of two given arrays of NGraphs.
	* @param g1 an array of NGraph.
	* @param g2 another array of NGraph.
	* @return the value of the calculated disorder.
	*/
	private static int arrayDisorder(NGraph[] g1, NGraph[] g2) {
		int disorder = 0;
		
		//Debug.debug("Start disorder");
		for(int i = 0; i < g1.length; i++) {
			for(int j = 0; j < g2.length; j++) {
				if((g1[i].getText()).equals(g2[j].getText())) {
					disorder += Math.abs(i-j);
					//Debug.debug("i: "+i+" j: "+j+" disorder: "+disorder);
					break;
				}
			}
		}
		Utils.debug("Returned disorder: "+disorder);
		return disorder;
	}
	
	/**
	* Returns the count of similar NGraphs between 2 Sets of shared NGraphs.
	* @param s1 the first Set containing n-graphs.
	* @param s2 the second Set containing n-graphs.
	* @param t the constant T.
	* @return the count of similar NGraphs.
	*/
	public static int getSimilarNGraphsCount(Set<NGraph> s1, Set<NGraph> s2, double t) {
		int similarNGraphCounter = 0;
		int similarINIF = 0;
		double tmp = 0.0;
		
		for(NGraph nGraph1 : s1) {
			
			for(NGraph nGraph2 : s2) {
				
				if(nGraph1.compareTo(nGraph2) == 0) {
					
					similarINIF++;
					tmp = (double) Math.max(nGraph1.getDuration(), nGraph2.getDuration()) / (double) Math.min(nGraph1.getDuration(), nGraph2.getDuration());
					if(1 <= tmp && tmp <= t) similarNGraphCounter++;
					
					//tmp = (double) nGraph1.getDuration() / (double) nGraph2.getDuration();
					//if(tmp < 1) tmp = 1/tmp;
					//if(tmp <= t) similarNGraphCounter++;
					
				}
			}
		}
		Utils.debug("Similar n-graph count IN IF(sh:"+s1.size()+"): " + similarINIF);
		Utils.debug("Similar n-graph count: " + similarNGraphCounter + " T value: " + t);
		return similarNGraphCounter;
	}
	
	/**
	* This private class gives a comparison function, which
	* imposes a total ordering on a collection of NGraph.
	*/
	private static class NGraphComparer implements Comparator<NGraph> {

		public int compare(NGraph arg0, NGraph arg1) {
			if		(arg0.getDuration() < arg1.getDuration()) return -1;
			else if	(arg0.getDuration() > arg1.getDuration()) return 1;
			else return arg0.getText().compareTo(arg1.getText());
		}
	}

}
