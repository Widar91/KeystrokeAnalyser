package it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners;


import it.unito.di.educ.bachelor_thesis.main.SystemController;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class implements a listener for the Profiler's Input Area.
 * 
 * @author Eddy Bertoluzzo
 */
public class ProfilerInputAreaListener implements DocumentListener {

	public void changedUpdate(DocumentEvent arg0) {}

	public void insertUpdate(DocumentEvent arg0) {
		SystemController.getInstance().increaseNumberOfVisibleKeystrokes();
	}

	public void removeUpdate(DocumentEvent arg0) {
		SystemController.getInstance().decreaseNumberOfVisibleKeystrokes();
	}
	
}