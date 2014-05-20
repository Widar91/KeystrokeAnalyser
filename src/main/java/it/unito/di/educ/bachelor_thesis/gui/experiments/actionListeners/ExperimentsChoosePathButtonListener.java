package it.unito.di.educ.bachelor_thesis.gui.experiments.actionListeners;

import it.unito.di.educ.bachelor_thesis.Utils.Options;
import it.unito.di.educ.bachelor_thesis.Utils.Utils;
import it.unito.di.educ.bachelor_thesis.gui.experiments.ExperimentsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * This class implements a listener for the Experiments Panel's Users and Impostors buttons.
 * 
 * @author Eddy Bertoluzzo
 *
 */
public class ExperimentsChoosePathButtonListener implements ActionListener{

	private ExperimentsPanel experimentsPanel;
	
	public ExperimentsChoosePathButtonListener(ExperimentsPanel experimentsPanel) {
		this.experimentsPanel = experimentsPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser;
		File selectedFile;
		Options options = Options.getInstance();
		
		if(e.getActionCommand().equalsIgnoreCase("UserPath"))
			chooser = new JFileChooser(options.getUserFolderPath());
		else 
			chooser = new JFileChooser(options.getImpostorsFolderPath());

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(chooser.showDialog(null, "Choose Directory") == JFileChooser.APPROVE_OPTION) {
			selectedFile = chooser.getSelectedFile();
			
			if(e.getActionCommand().equalsIgnoreCase("UserPath")) {
				
				options.setUserFolder(selectedFile.getPath());
				experimentsPanel.getUserPathTextField().setText(selectedFile.getPath());
				Utils.debug("Changed users' path directory to: " + selectedFile.getPath());
				
			} else {
				
				options.setImpostorsFolder(selectedFile.getPath());
				experimentsPanel.getImpostorsPathTextField().setText(selectedFile.getPath());
				Utils.debug("Changed impostors' path directory to: " + selectedFile.getPath());
				
			}
				
		}
		
	}
}
