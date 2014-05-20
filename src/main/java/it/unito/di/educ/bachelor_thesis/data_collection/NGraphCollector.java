package it.unito.di.educ.bachelor_thesis.data_collection;

import it.unito.di.educ.bachelor_thesis.Utils.Options;

/**
 * This class implements a circular buffer for the collection of NGraphSets.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class NGraphCollector {
	
	private int[] tail,size; 				/* tail and size for the circular buffers */
	private Keystroke[][] keystrokeBuffer;	/* array of arrays of collected KeyStroke */
	private NGraphSet[] nGraphSets;			/* An array containing an NGraphSet for each of this sample's [2-5]-graph sets. */
	private int bufferedKeystrokes;			/* Number of total buffered keystrokes*/
	private boolean skipNextKeystroke;
	
	/**
	* Constructor for this class
	*/
	protected NGraphCollector(){
		tail = new int[4];
		size = new int[4];
		
		keystrokeBuffer = new Keystroke[4][5];
		
		nGraphSets = new NGraphSet[4];
		for (int i = 0; i < nGraphSets.length; i++)
			nGraphSets[i] = new NGraphSet();
		
		bufferedKeystrokes = 0;
		skipNextKeystroke = false;
	}
	
	/**
	 * Returns an array containing an NGraphSet for each of this sample's [2-5]-graph sets.  
	 * @return NGraphCollector's field nGraphSets.
	 */
	protected NGraphSet[] collectNGraphSets() {
		return nGraphSets;
	}
	
	/**
	* This method resets the buffers.
	*/
	protected void reset(int n) {
		tail[n-2] = 0;
		size[n-2] = 0;
		
		for(int i = 0; i < 5; i++)
			keystrokeBuffer[n-2][i] = null;
	}
	
	/**
	* Adds the given Keystroke to the buffers and generates the appropriate N-Graphs.<br>
	* <p>Implementation note: if neither Options.getRNGraph(n) nor Options.getANGraph(n) return true 
	* then the appropriate n-Graphs will not  be added to the system at all.</p>
	* @param keystroke to be computed.
	*/
	protected void generateNGraphs(Keystroke inserted) {
    	
    	Options options = Options.getInstance();
    	
    	boolean graph2 = options.getRNGraph(2) || options.getANGraph(2);
    	boolean graph3 = options.getRNGraph(3) || options.getANGraph(3);
    	boolean graph4 = options.getRNGraph(4) || options.getANGraph(4);
    	boolean graph5 = options.getRNGraph(5) || options.getANGraph(5);
    	
    	/* These instructions are used in order to reproduce 
    	 * the results of previous experiments by collecting the same
    	 * number of n-graphs the old C programs did.
    	 * */
    	if(skipNextKeystroke){
			skipNextKeystroke = false;
			
			if((int)inserted.getChar() >= 32){		/* backspaces and indentation chars are skipped */
	    		bufferedKeystrokes++;
	    		if(graph2)	generate2Graphs(inserted);
	    	}
	    	else 
	    		reset(2);
			
			return;
		}
    	
    	if((int)inserted.getChar() >= 32){			/* backspaces and indentation chars are skipped */
    		bufferedKeystrokes++;
    		
    		if(graph2)	generate2Graphs(inserted);
   			if(graph3)	generate3Graphs(inserted);
   			if(graph4)	generate4Graphs(inserted);
   			if(graph5)	generate5Graphs(inserted);
    	}
    	else {
    		if(bufferedKeystrokes >= 2) /*	this works for the correct reproduction of the 3-graph collection, 
    									 *	change it to 3 if you want it to work with the 4-graphs.
    									 *	Anyway, since this whole method is pretty messy, we can talk about it in person so I can explain myself better =) 
    									 */
    			
    			skipNextKeystroke = true;
    		
    		reset(2);
    	}
    	
	}
    
    /**
	* Adds the given Keystroke to the buffer and generates the appropriate 2-Graph.<br>
	* @param keystroke to be computed.
	*/
    private void generate2Graphs(Keystroke inserted) {
    	Keystroke ks1, ks2;
    	int size;

    	addKeystrokeToBuffer(inserted, 2);
    	size = this.size[0];
		
		switch(size) {
			case 0:
				break;
			case 1:
				break;
			default:
				ks1 = getBufferedKeystroke(2, size-1);
				ks2 = getBufferedKeystroke(2, size-2);
				nGraphSets[0].addNGraph(new NGraph("" + ks2.getChar() + ks1.getChar(), ks1.getWhen() - ks2.getWhen()));
				break;
		}
	}
    
    /**
	* Adds the given Keystroke to the buffer and generates the appropriate 3-Graph.<br>
	* @param keystroke to be computed.
	*/
    private void generate3Graphs(Keystroke inserted) {
    	Keystroke ks1, ks2, ks3;
    	int size;

    	addKeystrokeToBuffer(inserted, 3);
    	size = this.size[1];
    	
		switch(size) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			default:
				ks1 = getBufferedKeystroke(3, size-1);
				ks2 = getBufferedKeystroke(3, size-2);
				ks3 = getBufferedKeystroke(3, size-3);
				nGraphSets[1].addNGraph(new NGraph("" + ks3.getChar() + ks2.getChar() + ks1.getChar(), ks1.getWhen() - ks3.getWhen()));
				break;
		}
	}
    
    /**
	* Adds the given Keystroke to the buffer and generates the appropriate 4-Graph.<br>
	* @param keystroke to be computed.
	*/
    private void generate4Graphs(Keystroke inserted) {
    	Keystroke ks1, ks2, ks3, ks4;
    	int size;

    	addKeystrokeToBuffer(inserted, 4);
    	size = this.size[2];
		
		switch(size) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				ks1 = getBufferedKeystroke(4, size-1);
				ks2 = getBufferedKeystroke(4, size-2);
				ks3 = getBufferedKeystroke(4, size-3);
				ks4 = getBufferedKeystroke(4, size-4);
				nGraphSets[2].addNGraph(new NGraph("" + ks4.getChar() + ks3.getChar() + ks2.getChar() + ks1.getChar(), ks1.getWhen() - ks4.getWhen()));
				break;
		}
	}
    
    /**
	* Adds the given Keystroke to the buffer and generates the appropriate 5-Graph.<br>
	* @param keystroke to be computed.
	*/
    private void generate5Graphs(Keystroke inserted) {
    	Keystroke ks1, ks2, ks3, ks4, ks5;
    	int size;

    	addKeystrokeToBuffer(inserted, 5);
    	size = this.size[3];
		
		switch(size) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				ks1 = getBufferedKeystroke(5, size-1);
				ks2 = getBufferedKeystroke(5, size-2);
				ks3 = getBufferedKeystroke(5, size-3);
				ks4 = getBufferedKeystroke(5, size-4);
				ks5 = getBufferedKeystroke(5, size-5);
				nGraphSets[3].addNGraph(new NGraph("" + ks5.getChar() + ks4.getChar() + ks3.getChar() + ks2.getChar() + ks1.getChar(), ks1.getWhen() - ks5.getWhen()));
				break;
		}
	}
    
    /**
     * Adds the specified Keystroke to the buffers.
     * @param keystroke to be added.
     * @param n TODO
     */
	private void addKeystrokeToBuffer(Keystroke keystroke, int n){
		keystrokeBuffer[n-2][tail[n-2]] = keystroke;
		tail[n-2] = ((tail[n-2]) + 1) % keystrokeBuffer[n-2].length;
		size[n-2] = size[n-2] + 1;
    }
	
	/**
	 * Returns the Keystroke object found in the specified buffer at the index specified by the parameter index. 
	 * @param n index of the buffer where the Keystroke is.
	 * @param index of the Keystroke needed.
	 * @return the Keystroke object at the specified position.
	 */
	private Keystroke getBufferedKeystroke(int n, int index) {
		return keystrokeBuffer[n-2][(index % keystrokeBuffer[n-2].length)];
	}
}
