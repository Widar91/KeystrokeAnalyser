package it.unito.di.educ.bachelor_thesis.data_collection;

import it.unito.di.educ.bachelor_thesis.utils.Options;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements an NGraphSet, which consists in a Set of NGraphs of the same dimension.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class NGraphSet {
	
	public int addedNGraphNumber;						/* A variable that counts all the attempts to add an NGraph to the set (for debugging only) */
	public int repeatedNGraphNumber;					/* A variable that counts all the attempts to add a Repeated NGraph to the set (for debugging only) */
	public int notAddedNGraphNumber;					/* A variable that counts all the attempts to add a not acceptable(w.r.t. the duration) NGraph to the set (for debugging only) */
	
	private Set<NGraph> nGraphSet;						/* The Set of all accepted NGraphs */
	private Set<RepeatedNGraph> repeatedNGraphSet;		/* A Set that contains a trace of all the repeated NGraphs in a nGraphSet */
	
	
	/**
	 * The constructor for this class.
	 */
	public NGraphSet() {
		addedNGraphNumber = 0;
		notAddedNGraphNumber = 0;
		nGraphSet = new TreeSet<NGraph>();
		repeatedNGraphSet = new TreeSet<RepeatedNGraph>();
	}
	
	/**
	 * Adds an NGraph to the Set(nGraphSet).
	 * <p>Implementation note: the NGraph is added if and only if it's duration does not exceed the maxDuration variable 
	 * found in Options. If the same n-graph occurs more than once, then the n-graph is reported only once and the mean 
	 * duration of its occurrences is used. In order to do so, an object of the class RepeatedNGraph is created and put 
	 * in the repeatedNGraphSet (or updated if it was already present).</p>
	 * @param nGraph the NGraph to be added.
	 */
	protected void addNGraph(NGraph nGraph) {
		
		addedNGraphNumber++;
		
		if(nGraph.getDuration() > Options.getInstance().getMaxNGraphDuration() * (nGraph.getDimension()-1) || nGraph.getDuration() < Options.getInstance().getMinNGraphDuration() * (nGraph.getDimension()-1)){
			notAddedNGraphNumber++;
			return;
		}
		
		//Debug.debug("Adding nGraph --> " + nGraph.toString());
		if(!nGraphSet.add(nGraph)){
			
			repeatedNGraphNumber++;
			
			boolean newRepeatedNGraph = false;
			RepeatedNGraph repeatedNGraph;
			Iterator<NGraph> iterator = nGraphSet.iterator();
			
			repeatedNGraph = getRepeatedNGraphFromSet(nGraph.getText());
			if(repeatedNGraph == null) {	
				repeatedNGraph = new RepeatedNGraph(nGraph.getText());
				repeatedNGraphSet.add(repeatedNGraph); 
				newRepeatedNGraph = true;
				//Debug.debug("nGraph --> " + nGraph.toString() + " rejected! No RepeatedNGraph found in the system");
			}
			//else 
			//	Debug.debug("nGraph --> " + nGraph.toString() + " rejected! RepeatedNGraph found in the system --> \n\t\t" + repeatedNGraph.toString());
			
			repeatedNGraph.addDuration(nGraph.getDuration());
			
			while(iterator.hasNext()) {
				NGraph storedNGraph = iterator.next();
				if(storedNGraph.getText().equals(nGraph.getText())){
					
					if(newRepeatedNGraph) 
						repeatedNGraph.addDuration(storedNGraph.getDuration());
					
					storedNGraph.setDuration(repeatedNGraph.getMeanDuration());
					
					//Debug.debug(repeatedNGraph.toString() + "\n\t\tStored Mean Duration: "+ storedNGraph.getDuration());
					break;
				}
			}					
		}
		
	}
	
	/**
	 * Sets repeatedNGraphSet field to the value passed as parameter.
	 * @param repeatedNGraphSet the value to set.
	 */
	protected void setRepeatedNGraphSet(Set<RepeatedNGraph> repeatedNGraphSet) {
		this.repeatedNGraphSet = repeatedNGraphSet;
	}
	
	/**
	 * Returns the number of NGraph actually added to the Set.
	 * @return the number of NGraph actually added to the Set.
	 */
	public int getNGraphSetSize(){
		return nGraphSet.size();
	}
	
	/**
	 * Returns a copy of the nGraphSet field.
	 * @return a copy of the nGraphSet field.
	 */
	public synchronized Set<NGraph> getNGraphSetCopy() {
		Set<NGraph> nGraphSetCopy = new TreeSet<NGraph>();
		
		for(NGraph ng : nGraphSet) 
			nGraphSetCopy.add(ng.clone());

		return nGraphSetCopy;
	}
	
	/**
	 * Searches for the RepeatedNGraph in the Set which has the same text as the one passed as parameter. Returns it if it is found.
	 * @param text the n-graph text to be searched.
	 * @return the RepeatedNGraph if found, null otherwise.
	 */
	private RepeatedNGraph getRepeatedNGraphFromSet(String text) {
		for(RepeatedNGraph storedNGraph : repeatedNGraphSet) {
			if(storedNGraph.getText().equals(text))
				return storedNGraph;
		}
		return null;
	}
	
	public String toString(){
		String result = "";
		
		for(NGraph ng : nGraphSet) 
			result += "\n" + ng.toString() + "\t";
		
		return result;
	}
	
	/**
	 * This private class implements an NGraph with more the one duration. 
	 */
	private class RepeatedNGraph implements Comparable<RepeatedNGraph>{
		
		private String keystroke;
		private List<Long> durations;
		
		/**
		 * The constructor for this class.
		 * @param keystroke the n-graph text.
		 */
		public RepeatedNGraph(String keystroke) {
			this.keystroke = keystroke;
			durations = new LinkedList<Long>();
		}
		
		/**
		 * Returns the n-graph text.
		 * @return the n-graph text.
		 */
		public String getText() {
			return keystroke;
		}
		
		/**
		 * Adds a duration value to the List of durations.
		 * @param duration to be added.
		 */
		public void addDuration(long duration){
			durations.add(new Long(duration));
		}
		
		/**
		 * Returns the mean value of all the durations found in the List.
		 * @return the mean of the durations.
		 */
		public long getMeanDuration(){
			long listSize = durations.size();
			long result = 0;
			
			for(Long l : durations)
				result += l;
			
			return result/listSize;
		}
		
		public int compareTo(RepeatedNGraph repeatedNGraph) {
			return keystroke.compareTo(repeatedNGraph.getText());
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof RepeatedNGraph) && (this.compareTo((RepeatedNGraph) obj) == 0);
		}

		public String toString(){
			return 	"RepeatedNGraph:" +
					"\tKeystroke: [" + keystroke + "]" +
					"\tDurations: " + durations.toString() +
					"\tMean Duration: " + getMeanDuration();
		}
	}
}
