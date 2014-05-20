package it.unito.di.educ.bachelor_thesis.data_collection;


import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class implements a Sample as a collection of NGraphSet.
 * 
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class Sample {
	
	private String sampleName;			/* The sample's name */
	private String samplePath;			/* The sample's path */
	private String sampleUserName;		/* The sample's user's name */
	private NGraphSet[] nGraphSets;		/*An array containing an NGraphSet for each of this sample's [2-5]-graph sets. */
	
	
	/**
	* Constructor for this class
	* @param sampleName the name for this sample.
	* @param samplePath the user's profiles path.
	* @param sampleUserName the name of the sample's user.
	*/
	public Sample(String sampleName, String samplePath, String sampleUserName) {
		this.sampleName = sampleName;
		this.samplePath = samplePath;
		this.sampleUserName = sampleUserName;
	}
	
	/**
	 * Returns this sample's name.
	 * @return this sample's name.
	 */
	public String getSampleName() {
		return sampleName;
	}

	/**
	 * Returns this sample's user's name.
	 * @return this sample's user's name.
	 */
	public String getSampleUserName() {
		return sampleUserName;
	}
	
	/**
	 * Returns an array of four elements each containing an NGraphSet for this sample's [2-5]-graphs. 
	 * @return an array of NGraphSet representing this sample's [2-5]-graphs.
	 */
	public NGraphSet[] getnGraphSets() {
		return nGraphSets;
	}
	
	/**
	 * Reads the file specified by this field samplePath and generates the n-graphs which will be stored in nGraphSets field.
	 * @return true if the sample was collected correctly, false otherwise. 
	 */
	public boolean collectNGraphs() {
		FileReader reader = null;
		BufferedReader in = null;
		String when;
		String character;
		int ascii;
		Keystroke keystroke = null;
		NGraphCollector nGraphCollector = new NGraphCollector(); 
		int sampleKeystrokesCount = 0, notAcceptedKeystrokeCount = 0;
		
		try {
			reader = new FileReader(samplePath);
			in = new BufferedReader(reader);
			
			when = in.readLine();
			character = in.readLine();
			while(when != null && character != null) {
				try {
					
					ascii = Integer.parseInt(character.trim());		/* retrieves the ASCII code of the typed character */
				
					if (Options.getInstance().getLowerCase() && ascii >= 65 && ascii <= 90)		/* converts it to lower case */
						ascii = ascii + 32;
					
					if(ascii >= 0) {
						keystroke = new Keystroke(Character.toChars(ascii)[0],Long.parseLong(when));
						nGraphCollector.generateNGraphs(keystroke);
						sampleKeystrokesCount++;
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
					System.err.println("Error in sample: " + this.sampleName);
					SystemController.getInstance().sendMessageToGui("ERROR: Invalid Sample <"+ sampleName +">.");
					return false;
				} catch (IllegalArgumentException e) {
					System.err.println("Error in sample: " + this.sampleName);
					SystemController.getInstance().sendMessageToGui("ERROR: Invalid Sample <"+ sampleName +">.");
					return false;
				}
			
				when = in.readLine();
				character = in.readLine();
			}
			
			nGraphSets = nGraphCollector.collectNGraphSets();
			for(NGraphSet ngs : nGraphSets)
				ngs.setRepeatedNGraphSet(null);
			
			Utils.debug("SYSTEM: Loaded sample <"+ sampleName +">\tfrom user <"+ sampleUserName +">.\tKeystrokes <"+ sampleKeystrokesCount +">\n" +
					"\t 2-Graphs <"+ nGraphSets[0].getNGraphSetSize() +"> "+ nGraphSets[0].addedNGraphNumber +" added ["+ nGraphSets[0].notAddedNGraphNumber +" not in range, "+ nGraphSets[0].repeatedNGraphNumber +" repeated], "+ notAcceptedKeystrokeCount +" not accepted.\n" +
					"\t 3-Graphs <"+ nGraphSets[1].getNGraphSetSize() +"> "+ nGraphSets[1].addedNGraphNumber +" added ["+ nGraphSets[1].notAddedNGraphNumber +" not in range, "+ nGraphSets[1].repeatedNGraphNumber +" repeated], "+ notAcceptedKeystrokeCount +" not accepted.\n" +
					"\t 4-Graphs <"+ nGraphSets[2].getNGraphSetSize() +"> "+ nGraphSets[2].addedNGraphNumber +" added ["+ nGraphSets[2].notAddedNGraphNumber +" not in range, "+ nGraphSets[2].repeatedNGraphNumber +" repeated], "+ notAcceptedKeystrokeCount +" not accepted.\n" +
					"\t 5-Graphs <"+ nGraphSets[3].getNGraphSetSize() +"> "+ nGraphSets[3].addedNGraphNumber +" added ["+ nGraphSets[3].notAddedNGraphNumber +" not in range, "+ nGraphSets[3].repeatedNGraphNumber +" repeated], "+ notAcceptedKeystrokeCount +" not accepted.\n");
		}
		catch (FileNotFoundException exc1) {System.err.println("ERROR: File <"+ sampleName +"> not found at location <" + samplePath + ">");}
		catch (IOException exc2) {System.err.println("ERROR: I/O error while parsing file <" + sampleName + ">");}
		finally {
			try {
				reader.close();
				in.close();
			} 
			catch (IOException e) {e.printStackTrace();}
		}
		return true;
	}

	public String toString(){
		String result ="Sample: " + sampleName + "\nPath: " + samplePath + "\n\n";
		result += "\t------------ N-Graphs ------------\n";
		for(int i = 0; i < nGraphSets.length; i++)
			result += "\n\n\n\n\t------------" + (i+2) + "-Graps ------------\n\n\n" + nGraphSets[i].toString();
		return result;
	}
}
