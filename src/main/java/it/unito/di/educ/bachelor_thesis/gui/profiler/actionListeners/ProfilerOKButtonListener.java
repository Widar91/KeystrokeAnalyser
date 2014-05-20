package it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners;

import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;
import it.unito.di.educ.bachelor_thesis.gui.profiler.ProfilerPanel;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * This class implements a listener for the Profiler OK button.
 * 
 * @author Eddy Bertoluzzo
 */
public class ProfilerOKButtonListener implements ActionListener {
	
	private ProfilerPanel profilerPanel;
	
	public ProfilerOKButtonListener(ProfilerPanel profilerPanel) {
		this.profilerPanel = profilerPanel;
	}

	public void actionPerformed(ActionEvent e){
		ProfilerTaskThread profilerTaskThread = new ProfilerTaskThread();
		profilerTaskThread.start(); 
	}

	/**
	 * Private Thread class for the execution of the specified task. 
	 */
	private class ProfilerTaskThread extends Thread {
		
		public void run() {
			
			int resetChoice;
			String sampleName, samplePath, resultMessage;
			String userName = profilerPanel.getProfilerUserName().getText();
			String userPath = profilerPanel.getProfilerUserPathTextField().getText();
			String typedText = profilerPanel.getProfilerInputArea().getText();
			String task = (String)profilerPanel.getProfilerTaskComboBox().getSelectedItem();
			SystemController gc = SystemController.getInstance();
			
			if(userName.equals("")) 
				JOptionPane.showMessageDialog(profilerPanel.getParent(), "You MUST enter a User Name", "Error", JOptionPane.ERROR_MESSAGE);
			if(typedText.equals(""))
				JOptionPane.showMessageDialog(profilerPanel.getParent(), "You MUST enter a some text in order to create a sample", "Error", JOptionPane.ERROR_MESSAGE);
			if(userPath.equals(""))
				JOptionPane.showMessageDialog(profilerPanel.getParent(), "You MUST choose a path in which to save the sample", "Error", JOptionPane.ERROR_MESSAGE);
			
			
			sampleName = gc.saveSampleIfNoDuplicate(userName, userPath);
			samplePath = userPath + Utils.getOSPathSeparator() + sampleName;
			
			if(task.equalsIgnoreCase("PROFILER")){
				
				JOptionPane.showMessageDialog(profilerPanel.getParent(), "Sample <"+ sampleName +"> saved succesfully in folder: " + userPath + ".", "Saved Sample", JOptionPane.INFORMATION_MESSAGE);
				profilerPanel.getProfilerTaskComboBox().removeItem("PROFILER");
				
			} else {
					
				profilerPanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				profilerPanel.getProfilerOKButton().setEnabled(false);
				profilerPanel.getProfilerClearButton().setEnabled(false);
				
				Utils.debug("Beginning test with the following options:\n" + Options.getInstance().optionsSummary());
				Utils.setDebugMode(false);
				
				resultMessage = gc.performSingleTask(sampleName, samplePath, userName, task);
				
				profilerPanel.getParent().setCursor(null);
				profilerPanel.getProfilerOKButton().setEnabled(true);
				profilerPanel.getProfilerClearButton().setEnabled(true);
				
				JOptionPane.showMessageDialog(profilerPanel.getParent(), resultMessage, "Result", JOptionPane.INFORMATION_MESSAGE);	
				
			}
			
			resetChoice = JOptionPane.showConfirmDialog(profilerPanel.getParent(), "Do you want to reset the Profiler in order to write a new sample?", "Clear", JOptionPane.YES_NO_OPTION);
			if(resetChoice == JOptionPane.YES_NO_OPTION) 
				gc.resetProfiler();
				
		}
	}
	
}
