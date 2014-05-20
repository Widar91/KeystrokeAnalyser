package it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners;


import it.unito.di.educ.bachelor_thesis.Main.SystemController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements a listener for the Profiler's Clear button.
 * 
 * @author Eddy Bertoluzzo
 */
public class ProfilerClearButtonListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		SystemController.getInstance().resetProfiler();
	}
}
