package it.unito.di.educ.bachelor_thesis.utils;


import it.unito.di.educ.bachelor_thesis.data_collection.Keystroke;
import it.unito.di.educ.bachelor_thesis.experiments.Results;
import it.unito.di.educ.bachelor_thesis.main.SystemController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class.
 * @author Eddy Bertoluzzo
 * @version 1.0 26/04/2013
 */

public class Utils {
	
	private static boolean DEBUG_MODE = true;
	
	/**
	 * Prints the message on the System.err if DEBUG_MODE is set to true.
	 * @param message the message to print.
	 */
	public static void debug(String message) {
		if(DEBUG_MODE)
			System.err.println("DEBUG:\t" + message);
	}
	
	/**
	 * Sets the DEBUG_MODE variable to the value of b.
	 * @param b true or false.
	 */
	public synchronized static void setDebugMode(boolean b) {
		DEBUG_MODE = b;
	}
	
	/**
	 * Returns a String containing the right Path Separator depending on the current OS.
	 * @return a String containing the Path Separator.
	 */
	public static String getOSPathSeparator(){
		if(System.getProperty("os.name").equalsIgnoreCase("linux"))
			return "/"; 
		else
			return "\\";
	}
	
	/**
	 * Saves the sample into a file.
	 * @param userName the name of the user.
	 * @param userPath the path in which to save the sample.
	 * @return a String representing the sample's name.
	 * @throws Exception
	 */
	public static String saveSample(String userName, String userPath) throws IOException{
		
		String newFileName, sampleName;
		File userDir = new File(userPath);
		File[] userSamples = userDir.listFiles();
		int sampleIndex = 0;
		
		for(int i = 0; i < userSamples.length; i++){
			if(userSamples[i].isFile() && userSamples[i].getName().length() > userName.length() && userSamples[i].getName().substring(0, userName.length()).equalsIgnoreCase(userName))
				sampleIndex++;
		}
		
		sampleName = userName + "-" + (sampleIndex + 1);
		newFileName = userPath + Utils.getOSPathSeparator() + sampleName;
		
		FileWriter w = new FileWriter(newFileName);
		BufferedWriter b = new BufferedWriter (w);
		
		List<Keystroke> keystrokeList = SystemController.getInstance().getKeystrokeList();
		Iterator<Keystroke> iter = keystrokeList.iterator();
		while(iter.hasNext()){
			b.write(iter.next().toASCIIString());
		}
	    b.flush();
	    
	    w.close();
	    b.close();
	    
		return sampleName;
	}
	
	/**
	 * Saves the results of the experiments in to a Log File.
	 * @throws IOException
	 */
	public static void saveLog(Results results) throws IOException {
		
		String dirSeparator;
		String userHome = System.getProperty("user.home");
		
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		
		dirSeparator = Utils.getOSPathSeparator();
		
		new File(userHome + dirSeparator + "keystrokeLog").mkdir();
		
		FileWriter w = new FileWriter(userHome + dirSeparator + "keystrokeLog" + dirSeparator + Options.getInstance().getTask().substring(0, 4) + "_Log-" + sdf.format(cal.getTime()));
		BufferedWriter b = new BufferedWriter (w);
		
		b.write(results.toString());
	    b.flush();
	    
	    w.close();
	    b.close();
		
	}
}
