package it.unito.di.educ.bachelor_thesis.gui.experiments.actionListeners;

import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;
import it.unito.di.educ.bachelor_thesis.experiments.Results;
import it.unito.di.educ.bachelor_thesis.gui.experiments.ExperimentsPanel;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * This class implements a listener for the Experiments Panel's Run button.
 * 
 * @author Eddy Bertoluzzo
 *
 */
public class ExperimentsRunButtonListener implements ActionListener {

	private ExperimentsPanel experimentsPanel;
	
	public ExperimentsRunButtonListener(ExperimentsPanel experimentsPanel) {
		this.experimentsPanel = experimentsPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		PerformTaskThread performTaskThread = new PerformTaskThread();
		performTaskThread.start();
	}
	
	/**
	 * Private Thread class for the execution of the specified task. 
	 */
	private class PerformTaskThread extends Thread {
		
		public void run() {
			
			SystemController gc = SystemController.getInstance();
			
			try {
				if(gc.setExperimentOptions()){
					
					String task = (String)experimentsPanel.getTaskComboBox().getSelectedItem();
					
					experimentsPanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					experimentsPanel.getRunButton().setEnabled(false);
					experimentsPanel.getProgressBar().setVisible(true);
					experimentsPanel.getProgressBar().setIndeterminate(true);
					experimentsPanel.getProgressBar().setString("Collecting Users");
					
					Utils.debug("Beginning tests with the following options:\n" + Options.getInstance().optionsSummary());
					Utils.setDebugMode(false);
					
					Results results = gc.performTestTask(task);
					
					experimentsPanel.getParent().setCursor(null);
					experimentsPanel.getRunButton().setEnabled(true);
					experimentsPanel.getProgressBar().setString("Task Completed Succesfully");
					experimentsPanel.getProgressBar().setIndeterminate(false);
					
					JOptionPane.showMessageDialog(experimentsPanel.getParent(), results.toString(), "Results", JOptionPane.INFORMATION_MESSAGE);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				SystemController.getInstance().sendAbortMessageToGui("Not all fields are filled or User Path is not valid.", false);
				return;
			}
		}
	}
	
}
