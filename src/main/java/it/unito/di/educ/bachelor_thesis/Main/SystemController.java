package it.unito.di.educ.bachelor_thesis.main;

import it.unito.di.educ.bachelor_thesis.data_collection.Keystroke;
import it.unito.di.educ.bachelor_thesis.data_collection.Sample;
import it.unito.di.educ.bachelor_thesis.data_collection.UserCollector;
import it.unito.di.educ.bachelor_thesis.data_collection.users.Impostor;
import it.unito.di.educ.bachelor_thesis.data_collection.users.User;
import it.unito.di.educ.bachelor_thesis.experiments.PerformTasks;
import it.unito.di.educ.bachelor_thesis.experiments.Results;
import it.unito.di.educ.bachelor_thesis.gui.Gui;
import it.unito.di.educ.bachelor_thesis.gui.experiments.ExperimentsPanel;
import it.unito.di.educ.bachelor_thesis.gui.experiments.actionListeners.ExperimentsChoosePathButtonListener;
import it.unito.di.educ.bachelor_thesis.gui.experiments.actionListeners.ExperimentsRunButtonListener;
import it.unito.di.educ.bachelor_thesis.gui.profiler.ProfilerPanel;
import it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners.ProfilerChoosePathButtonListener;
import it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners.ProfilerClearButtonListener;
import it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners.ProfilerInputAreaListener;
import it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners.ProfilerKeyPressedListener;
import it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners.ProfilerOKButtonListener;
import it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners.ProfilerTaskComboBoxListener;
import it.unito.di.educ.bachelor_thesis.utils.Options;
import it.unito.di.educ.bachelor_thesis.utils.Utils;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 * This class represents the interface between the Gui Action Listeners and the Application Layer of the project.<br>
 * It can only be instantiated once (Singleton) and gathers methods that connect the two different layers already specified.<br>
 * This class is also used to create the Gui.
 * @author Eddy Bertoluzzo
 */
public class SystemController {
	
	private static SystemController instance;
	
	private Gui userInterface;
	private ExperimentsPanel experimentsPanel;
	private ProfilerPanel profilerPanel;
	private UserCollector userCollector;
	
	private static int numberOfVisibleKeystrokes;
	private static List<Keystroke> keystrokeList;
	private static String lastSavedSample;
	
	
	public static SystemController getInstance() {
		if(instance == null)
			instance = new SystemController();
		
		return instance;
	}
	
	private SystemController() {
		userCollector = UserCollector.getInstance();
		numberOfVisibleKeystrokes = 0;
		keystrokeList = new LinkedList<Keystroke>();
		lastSavedSample = null;
	}
	
	
	public synchronized void addKeystroke(Keystroke k) {
		keystrokeList.add(k);
	}
	
	public List<Keystroke> getKeystrokeList() {
		return keystrokeList;
	}
	
	
	public synchronized int getNumberOfKeystrokes() {
		return keystrokeList.size();
	}
	
	public synchronized void increaseNumberOfVisibleKeystrokes(){
		numberOfVisibleKeystrokes++;
	}
	
	public synchronized void decreaseNumberOfVisibleKeystrokes(){
		if(numberOfVisibleKeystrokes > 0)
			numberOfVisibleKeystrokes--;
	}
	
	public synchronized int getNumberOfVisibleKeystrokes(){
		return numberOfVisibleKeystrokes;
	}
	
	
	/**
	 * Resets the Profiler variables for the insertion of new samples.
	 */
	public synchronized void resetProfiler() {
		numberOfVisibleKeystrokes = 0;
		keystrokeList = new LinkedList<Keystroke>();
		lastSavedSample = null;
		
		profilerPanel.getProfilerInputArea().setText("");
		profilerPanel.getProfilerCharacterCounterArea().setText("");
		profilerPanel.getProfilerUserName().setText("");
		profilerPanel.getProfilerTaskComboBox().removeAllItems();
		profilerPanel.getProfilerTaskComboBox().addItem("PROFILER");
		profilerPanel.getProfilerTaskComboBox().addItem("CLASSIFICATION");
		profilerPanel.getProfilerTaskComboBox().addItem("AUTHENTIFICATION");
		profilerPanel.getProfilerTaskComboBox().addItem("IDENTIFICATION");
		profilerPanel.getProfilerTaskComboBox().setSelectedIndex(0);
	}
	
	/**
	 * Creates and draws the Graphical User Interface; also connects it's elements with the right ActionListeners.
	 */
	public void createGui() {
		
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            	if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) {System.err.println(ex.getMessage());}
        catch (InstantiationException ex) {System.err.println(ex.getMessage());}
        catch (IllegalAccessException ex) {System.err.println(ex.getMessage());}
        catch (javax.swing.UnsupportedLookAndFeelException ex) {System.err.println(ex.getMessage());}
		
        userInterface = new Gui();
        experimentsPanel = userInterface.getExperimentsPanel();
        profilerPanel = userInterface.getProfilerPanel();
		
        experimentsPanel.getChooseUserPathButton().addActionListener(new ExperimentsChoosePathButtonListener(experimentsPanel));
        experimentsPanel.getChooseImpostorsPathButton().addActionListener(new ExperimentsChoosePathButtonListener(experimentsPanel));
        experimentsPanel.getRunButton().addActionListener(new ExperimentsRunButtonListener(experimentsPanel));
		
		profilerPanel.getProfilerTaskComboBox().addActionListener(new ProfilerTaskComboBoxListener(profilerPanel));
		profilerPanel.getProfilerChooseUserPathButton().addActionListener(new ProfilerChoosePathButtonListener(profilerPanel));
		profilerPanel.getProfilerInputArea().addKeyListener(new ProfilerKeyPressedListener(profilerPanel));
		profilerPanel.getProfilerInputArea().getDocument().addDocumentListener(new ProfilerInputAreaListener());
		profilerPanel.getProfilerOKButton().addActionListener(new ProfilerOKButtonListener(profilerPanel));
		profilerPanel.getProfilerClearButton().addActionListener(new ProfilerClearButtonListener());
		
	}
	
	/**
	 * Performs a single test with a specified sample.
	 * @return a String containing the result of the performed test.
	 */
	public String performSingleTask(String sampleName, String samplePath, String userName, String task){
		
		String resultUser, resultMessage = "";
		User[] usersList;	
		Options options = Options.getInstance();
		
		if(SystemController.getInstance().setExperimentOptions()){
			
			options.setTask(task);
			
			userCollector.collectUsers(options.getUserFolderPath());
			usersList = userCollector.getUsersList();
					
			if(usersList.length < 2) {
				sendAbortMessageToGui("There MUST be at least 2 Users in the Users' Directory in order for the application to work correctly.", true);
				return resultMessage;
			}
			
			Sample sample = new Sample(sampleName, samplePath, userName);
			sample.collectNGraphs();
			
			if(task.equalsIgnoreCase("CLASSIFICATION")) {
				resultUser = PerformTasks.performClassification(sample, usersList);
				resultMessage = "Sample <"+ sample.getSampleName() +"> has been Classified as belonging to User <"+ resultUser +">";
			}
			if(task.equalsIgnoreCase("AUTHENTICATION")) { 
				resultUser = PerformTasks.performIdentification(sample, usersList, false);
				resultMessage = "Authenticating Sample <"+ sample.getSampleName() +"> as <"+ userName +">...\nYou have been Authenticated as <"+ resultUser +">";
			}
			else if(task.equalsIgnoreCase("IDENTIFICATION")) {
				resultUser = PerformTasks.performIdentification(sample, usersList, false);
				resultMessage = "Sample <"+ sample.getSampleName() +"> has been Identified as belonging to User <"+ resultUser +">";
			}
			
		}
		
		return resultMessage;
		
	}
	
	/**
	 * This method saves the sample collected by the profiler if it has not been already saved.
	 * @param userName
	 * @param userPath
	 * @return the last saved sample name.
	 */
	public String saveSampleIfNoDuplicate(String userName, String userPath){
		
		if(lastSavedSample == null) {
			
			try {
				
				lastSavedSample = Utils.saveSample(userName, userPath);
				
			} catch (IOException e) {
				
				e.printStackTrace();
				lastSavedSample = null;
				sendAbortMessageToGui("An error has occourred while saving the sample: " + e.getMessage(), true);
				return null;
				
			}
			
		}
		
		return lastSavedSample;
		
	}
	
	/**
	 * Performs a set of tests and returns their results.
	 * @return the results of the performed tests.
	 */
	public Results performTestTask(String task){
		
		User[] usersList;				/* the list of all the users collected from the specified path */
		Impostor impostorSamples;		/* the list of all impostors' samples collected from the specified directory */
		Results results = null;			/* an object containing the experiments' results */
		Options options = Options.getInstance();
		
		int min, sec;
		long startTime, endTime;
		
		startTime = System.currentTimeMillis();
		options.setTask(task);
		
		//Collect Users
		userCollector.collectUsers(options.getUserFolderPath());
		usersList = userCollector.getUsersList();
				
		if(usersList.length < 2) {
			sendAbortMessageToGui("There MUST be at least 2 Users in the Users' Directory in order for the application to work correctly.", false);
			return null;
		}
		
		//Update progress bar
		experimentsPanel.getProgressBar().setString("Performing " + task);
		
		//Performs the selected Task
		if(task.equalsIgnoreCase("CLASSIFICATION"))
			results = PerformTasks.performClassificationTest(usersList);
		
		else {
			userCollector.collectImpostors(options.getImpostorsFolderPath());
			impostorSamples = userCollector.getImpostors();  
			
			if(task.equalsIgnoreCase("AUTHENTICATION")) 
				results = PerformTasks.performAuthenticationTest(usersList, impostorSamples);
			
			else if(task.equalsIgnoreCase("IDENTIFICATION"))
				results = PerformTasks.performIdentificationTest(usersList, impostorSamples);
			
			results.setNumberOfImpostors(impostorSamples.getAllUserSamples().length);
		}
		
		//Calculate the elapsed time
		endTime = System.currentTimeMillis();
		min = (int)((endTime - startTime)/(Math.pow(10, 3)))/60;
		sec = (int)((endTime - startTime)/(Math.pow(10, 3)))%60;
		
		//Sets some results fields
		results.setMin(min);
		results.setSec(sec);
		results.setNumberOfUsers(usersList.length);
		results.setNumberOfSamplesPerUser(usersList[0].getAllUserSamples().length);
		
		//Saves the log
		try {
			Utils.saveLog(results);
		} catch(IOException e){
			sendMessageToGui("Error while saving log!");
		}
		
		return results;
	}
	
	/**
	* Sets the values in Options class according to the choices made in the GUI.
	* @return true if all input was valid and everything is set correctly.
	*/
	public boolean setExperimentOptions() {
		double tValue, kValue;
		int minDuration, maxDuration, maxNumberOfSamples, minNumberOfSamples, minSharedNGraphs;
		Border defaultBorder = experimentsPanel.getConstantTTextField().getBorder();
		Border redBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red);
		
		Options options = Options.getInstance();
		
		//Set R-Measures
		if(experimentsPanel.isBoxSelected(0)) 
			options.setRNGraph(true,2);
		else 
			options.setRNGraph(false,2);
		
		if(experimentsPanel.isBoxSelected(1)) 
			options.setRNGraph(true,3);
		else 
			options.setRNGraph(false,3);
		
		if(experimentsPanel.isBoxSelected(2)) 
			options.setRNGraph(true,4);
		else 
			options.setRNGraph(false,4);
		
		if(experimentsPanel.isBoxSelected(3)) 
			options.setRNGraph(true,5);
		else 
			options.setRNGraph(false,5);
		
		if(experimentsPanel.isBoxSelected(4)) 
			options.normalizedWeightR(true);
		else 
			options.normalizedWeightR(false);

		////Set A-Measures
		if(experimentsPanel.isBoxSelected(5)) 
			options.setANGraph(true,2);
		else 
			options.setANGraph(false,2);
		
		if(experimentsPanel.isBoxSelected(6)) 
			options.setANGraph(true,3);
		else 
			options.setANGraph(false,3);
		
		if(experimentsPanel.isBoxSelected(7)) 
			options.setANGraph(true,4);
		else 
			options.setANGraph(false,4);
		
		if(experimentsPanel.isBoxSelected(8)) 
			options.setANGraph(true,5);
		else 
			options.setANGraph(false,5);
		
		if(experimentsPanel.isBoxSelected(9)) 
			options.normalizedWeightA(true);
		else 
			options.normalizedWeightA(false);

		//Set Constants
		tValue = Double.parseDouble(experimentsPanel.getConstantTTextField().getText());
		kValue = Double.parseDouble(experimentsPanel.getConstantKTextField().getText());
		
		//Set Other Options
		minDuration = Integer.parseInt(experimentsPanel.getNGraphMinDurationTextField().getText());
		maxDuration = Integer.parseInt(experimentsPanel.getNGraphMaxDurationTextField().getText());
		minNumberOfSamples = Integer.parseInt(experimentsPanel.getMinNumberOfSamplesTextField().getText());
		maxNumberOfSamples = Integer.parseInt(experimentsPanel.getMaxNumberOfSamplesTextField().getText());
		minSharedNGraphs = Integer.parseInt(experimentsPanel.getMinSharedNGraphsTextField().getText());
		
		if(experimentsPanel.getLowerCaseChoice()) 
			options.setLowerCase(true);
		else
			options.setLowerCase(false);
		
		
		//Controls on input validity
		options.setMinNGraphDuration(minDuration);
		
		
		if(maxDuration > 0) { 
			options.setMaxNGraphDuration(maxDuration);
			experimentsPanel.getNGraphMaxDurationTextField().setBorder(defaultBorder);
		}
		else {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "Max n-graph duration must be > 0", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getNGraphMaxDurationTextField().setBorder(redBorder);
			return false;
		}
		

		if(maxNumberOfSamples >= minNumberOfSamples) { 
			options.setMaxNumberOfSamples(maxNumberOfSamples);
			experimentsPanel.getMaxNumberOfSamplesTextField().setBorder(defaultBorder);
		}
		else {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "Max number of sample for users' profiles must be >= Min Number of Samples", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getMaxNumberOfSamplesTextField().setBorder(redBorder);
			return false;
		}
		
		
		if(minNumberOfSamples > 1) { 
			options.setMinNumberOfSamples(minNumberOfSamples);
			experimentsPanel.getMinNumberOfSamplesTextField().setBorder(defaultBorder);
		}
		else {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "Min number of sample for user's validity must be > 1", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getMinNumberOfSamplesTextField().setBorder(redBorder);
			return false;
		}
		
		
		if(minSharedNGraphs > 0) { 
			options.setMinSharedNGraphs(minSharedNGraphs);
			experimentsPanel.getMinSharedNGraphsTextField().setBorder(defaultBorder);
		}
		else {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "Min number of shared N-Graphs must be > 0", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getMinSharedNGraphsTextField().setBorder(redBorder);
			return false;
		}

		
		if(tValue > 1) { 
			options.setT(tValue);
			experimentsPanel.getConstantTTextField().setBorder(defaultBorder);
		}
		else {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "Invalid value for T constant: it must be t > 1", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getConstantTTextField().setBorder(redBorder);
			return false;
		}
		
		
		if(kValue > 0 && kValue <= 1) {
			options.setK(kValue);
			experimentsPanel.getConstantKTextField().setBorder(defaultBorder);
		}
		else {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "Invalid value for K constant: it must be 0 < k <= 1", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getConstantKTextField().setBorder(redBorder);
			return false;
		}
		
		
		if(((String)experimentsPanel.getTaskComboBox().getSelectedItem()).equalsIgnoreCase("Choose Task...") && options.getTask().equals("")) {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "You must choose a Task", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getTaskComboBox().setBorder(redBorder);
			return false;
		}
		else 
			experimentsPanel.getTaskComboBox().setBorder(defaultBorder);
		
		
		if(experimentsPanel.getUserPathTextField().getText().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "You must choose a Users' Path", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getUserPathTextField().setBorder(redBorder);
			return false;
		}
		else 
			experimentsPanel.getUserPathTextField().setBorder(defaultBorder);
		
		
		if(experimentsPanel.getImpostorsPathTextField().getText().equalsIgnoreCase("") && (((String)experimentsPanel.getTaskComboBox().getSelectedItem()).equalsIgnoreCase("AUTHENTICATION") || ((String)experimentsPanel.getTaskComboBox().getSelectedItem()).equalsIgnoreCase("IDENTIFICATION"))) {
			JOptionPane.showMessageDialog(userInterface.getRootPanel(), "You must choose an Impostors' Path", "Error", JOptionPane.ERROR_MESSAGE);
			experimentsPanel.getImpostorsPathTextField().setBorder(redBorder);
			return false;
		}
		else experimentsPanel.getImpostorsPathTextField().setBorder(defaultBorder);
		
		return true;
	}
	
	/**
	 * Notifies the user of the error occurred via Message Dialog (Used by the Application Layer).
	 * @param message the message to be shown in the error dialog.
	 */
	public void sendMessageToGui(String message){
		JOptionPane.showMessageDialog(userInterface.getRootPanel(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Notifies the user of the error occurred, aborts the operation and restores the GUI.
	 * @param message the message to be shown in the error dialog.
	 * @param fromProfiler: true if the message comes from the Profiler Panel. 
	 */
	public void sendAbortMessageToGui(String message, boolean fromProfiler) {
		JOptionPane.showMessageDialog(userInterface.getRootPanel(), message, "Error", JOptionPane.ERROR_MESSAGE);
		userInterface.getRootPanel().setCursor(null);
		
		if(fromProfiler){
			profilerPanel.getProfilerOKButton().setEnabled(true);
			profilerPanel.getProfilerClearButton().setEnabled(true);
		}
		else {
			experimentsPanel.getRunButton().setEnabled(true);
			experimentsPanel.getProgressBar().setString("Task Aborted");
			experimentsPanel.getProgressBar().setIndeterminate(false);
		}
		
	}
	
}
