package it.unito.di.educ.bachelor_thesis.gui.profiler.actionListeners;

import it.unito.di.educ.bachelor_thesis.gui.profiler.ProfilerPanel;
import it.unito.di.educ.bachelor_thesis.utils.Options;
import it.unito.di.educ.bachelor_thesis.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;


/**
 * This class implements a listener for the Profiler Panel's Users and Impostors buttons.
 * 
 * @author Eddy Bertoluzzo
 */
public class ProfilerChoosePathButtonListener implements ActionListener{
	
	private ProfilerPanel profilerPanel;
	
	public ProfilerChoosePathButtonListener(ProfilerPanel profilerPanel) {
		this.profilerPanel = profilerPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser;
		File selectedFile;
		
		chooser = new JFileChooser(Options.getInstance().getUserFolderPath());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if(chooser.showDialog(null, "Choose Directory") == JFileChooser.APPROVE_OPTION) {
			
			selectedFile = chooser.getSelectedFile();
			profilerPanel.getProfilerUserPathTextField().setText(selectedFile.getPath());
			Utils.debug("Changed profiler user's path directory to: " + selectedFile.getPath());
			
		}
	}
}
