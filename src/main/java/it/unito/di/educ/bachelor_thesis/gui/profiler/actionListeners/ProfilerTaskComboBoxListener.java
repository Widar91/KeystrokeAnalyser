package it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners;

import it.unito.di.educ.bachelor_thesis.gui.profiler.ProfilerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * This class implements a listener for the Profiler's Task Combo Box.
 * 
 * @author Eddy Bertoluzzo
 */
public class ProfilerTaskComboBoxListener implements ActionListener {
	
	private ProfilerPanel profilerPanel;
	private boolean firstTaskChoise;	/* used for the showing of options warning in the profiler */
	
	public ProfilerTaskComboBoxListener(ProfilerPanel profilerPanel) {
		this.profilerPanel = profilerPanel;
		this.firstTaskChoise = true;
	}

	public void actionPerformed(ActionEvent e) {
		String choice = ((String)profilerPanel.getProfilerTaskComboBox().getSelectedItem());
		
		if(choice != null) {
			if (choice.trim().equalsIgnoreCase("PROFILER")) 
				profilerPanel.getProfilerOKButton().setText("Save the sample");
			else {
				profilerPanel.getProfilerOKButton().setText("Run");
				if(firstTaskChoise) {
					JOptionPane.showMessageDialog(profilerPanel.getParent(), "Options for this task can be chosen from the \"Experiments\" tab.", "Results", JOptionPane.INFORMATION_MESSAGE);
					firstTaskChoise = false;
				}
			}
		}
	}
	
}
