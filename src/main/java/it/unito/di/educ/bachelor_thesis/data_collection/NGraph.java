package it.unito.di.educ.bachelor_thesis.data_collection;

/**
 * This class implements an N-Graph.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class NGraph implements Comparable<NGraph> {
	private String keystroke; 	/* the n-graph text */
	private int dimension;		/* the n of n-graph */
	private long duration;		/* the duration of the n-graph */

	/**
	* Constructor for this class.
	* @param s n-graph's text.
	* @param time n-graph's duration.
	*/
	public NGraph(String s, long time) {
		keystroke = s;
		dimension = s.length();
		if (time < 0) {
			/* Absolute duration's range is [0,99999], the following line is needed when */ 
			/* the computed duration becomes negative due to the reset of the range */
			duration = (long)Math.pow(10,5) + time;
		}
		else duration = time;
		
		//TO BE REMOVED IF YOU WANT TO TAKE NEGATIVE VALUES INTO CONSIDERATION
		duration = time;
	}

	/**
	* Returns the duration of this n-graph.
	* @return n-graph 's duration.
	*/
	public long getDuration() {
		return duration;
	}

	/**
	* Returns n of this n-graph.
	* @return dimension.
	*/
	public int getDimension() {
		return dimension;
	}

	/**
	* Returns the text of this n-graph.
	* @return text.
	*/
	public String getText() {
		return keystroke;
	}
	
	/**
	 * Sets the duration of this n-graph.
	 * @param long representing the duration of the n-graph.
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	* This method creates and returns a copy of this n-graph.
	* @return the copy of this n-graph.
	*/
	public NGraph clone() {
		return new NGraph(keystroke, duration);
	}
	
	public int compareTo(NGraph nGraph) {
		if		(dimension < nGraph.getDimension()) return -1;
		else if	(dimension > nGraph.getDimension()) return 1;
		else 	return keystroke.compareTo(nGraph.getText());
	}
	
	public String toString() {
		return dimension + "-Graph: [" + keystroke + "]\t[" +  duration + "]";
		//return ""+duration;
	}

}