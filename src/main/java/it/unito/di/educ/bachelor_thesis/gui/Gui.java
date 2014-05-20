package it.unito.di.educ.bachelor_thesis.gui;

import it.unito.di.educ.bachelor_thesis.gui.experiments.ExperimentsPanel;
import it.unito.di.educ.bachelor_thesis.gui.profiler.ProfilerPanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * This class implements the GUI.
 * 
 * @author Eddy Bertoluzzo
 *
 */
public class Gui {

	private JFrame frame;
	private	JTabbedPane rootPanel;
	
	private ExperimentsPanel experimentsPanel;
	private ProfilerPanel profilerPanel;


	public Gui() {
		initialize();
	}
	
	public JTabbedPane getRootPanel(){
		return rootPanel;
	}

	public ExperimentsPanel getExperimentsPanel() {
		return experimentsPanel;
	}

	public ProfilerPanel getProfilerPanel() {
		return profilerPanel;
	}
	
	/**
	 * Initializes the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("Keystroke Analysis of Free Text - Eddy Bertoluzzo");
		frame.setMinimumSize(new Dimension(950, 700));
		frame.setBounds(175, 80, 950, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setVisible(true);
		
		rootPanel = new JTabbedPane();
		experimentsPanel = new ExperimentsPanel();
		profilerPanel = new ProfilerPanel();
		
		rootPanel.addTab("Experiments", experimentsPanel);
		rootPanel.addTab("Profiler", profilerPanel);
		frame.getContentPane().add(rootPanel);
		
	}

}
