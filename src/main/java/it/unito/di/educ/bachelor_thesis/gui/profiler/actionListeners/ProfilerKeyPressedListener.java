package it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners;

import it.unito.di.educ.bachelor_thesis.Main.SystemController;
import it.unito.di.educ.bachelor_thesis.data_collection.Keystroke;
import it.unito.di.educ.bachelor_thesis.gui.profiler.ProfilerPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * This class implements a listener for the Profiler Input Area.
 * 
 * @author Eddy Bertoluzzo
 */
public class ProfilerKeyPressedListener implements KeyListener {
	
	private ProfilerPanel profilerPanel;
	
	public ProfilerKeyPressedListener(ProfilerPanel profilerPanel) {
		this.profilerPanel = profilerPanel;
	}
	
	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent arg0) {
		SystemController gc = SystemController.getInstance();
		profilerPanel.getProfilerCharacterCounterArea().setText("Visible: " + gc.getNumberOfVisibleKeystrokes() + "  Total: " + gc.getNumberOfKeystrokes());
	}

	public void keyTyped(KeyEvent e) {
		SystemController.getInstance().addKeystroke(new Keystroke(e.getKeyChar(), e.getWhen()));
	}

}
