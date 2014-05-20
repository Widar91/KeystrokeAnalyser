package it.unito.di.educ.bachelor_thesis.gui.profiler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 * This class implements the GUI's Profiler Panel.
 * 
 * @author Eddy Bertoluzzo
 *
 */
public class ProfilerPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel userNameLabel;
	private JLabel charCounterLabel;
	private JLabel inputAreaLabel;
	private JLabel statusLabel;
	private JTextField userNameTextField;
	private JTextField charCounterTextField;
	private JComboBox taskComboBox;
	private JTextArea inputArea;
	private JButton btProfilerClearBut;
	private JButton btProfilerOKBut;
	
	private JPanel profiler_directory_Panel;
	private JLabel profilerUserPathLabel;
	private JPanel profiler_user_Folder_Panel;
	private JTextField profilerUserPathTextField;
	private JButton profilerChooseUserPathBut;

	private JLabel lbDistancerP1;
	private JLabel lbDistancerP2;
	private JLabel lbDistancerP4;
	private JLabel lbDistancerP3;
	private JLabel lbDistancerP5;
	private JLabel lbDistancerP6;
	private JLabel lbDistancerP7;
	

	public ProfilerPanel() {
		initialize();
	}
	
	public JButton getProfilerClearButton() {
		return btProfilerClearBut;
	}
	
	public JButton getProfilerOKButton() {
		return btProfilerOKBut;
	}
	
	public JComboBox getProfilerTaskComboBox() {
		return taskComboBox;
	}
	
	public JTextArea getProfilerInputArea() {
		return inputArea;
	}
	
	public JTextField getProfilerCharacterCounterArea() {
		return charCounterTextField;
	}
	
	public JTextField getProfilerUserName() {
		return userNameTextField;
	}

	public JTextField getProfilerUserPathTextField() {
		return profilerUserPathTextField;
	}
	
	public JButton getProfilerChooseUserPathButton() {
		return profilerChooseUserPathBut;
	}
	
	private void initialize() {
		
		GridBagLayout gb_profiler_Panel = new GridBagLayout();
		GridBagConstraints gbc_profiler_Panel = new GridBagConstraints();
		setLayout( gb_profiler_Panel );
		
		statusLabel = new JLabel( "Task"  );
		gbc_profiler_Panel.gridx = 8;
		gbc_profiler_Panel.gridy = 1;
		gbc_profiler_Panel.gridwidth = 2;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( statusLabel, gbc_profiler_Panel );
		add( statusLabel );
		
		String [] profilerTasks = {"PROFILER", "CLASSIFICATION", "AUTHENTICATION", "IDENTIFICATION"};
		taskComboBox = new JComboBox(profilerTasks);
		taskComboBox.setSelectedItem(0);
		gbc_profiler_Panel.gridx = 8;
		gbc_profiler_Panel.gridy = 2;
		gbc_profiler_Panel.gridwidth = 6;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 0;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( taskComboBox , gbc_profiler_Panel );
		add( taskComboBox  );

		userNameLabel = new JLabel( "User Name"  );
		gbc_profiler_Panel.gridx = 1;
		gbc_profiler_Panel.gridy = 1;
		gbc_profiler_Panel.gridwidth = 4;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( userNameLabel, gbc_profiler_Panel );
		add( userNameLabel );

		userNameTextField = new JTextField(5);
		userNameTextField.setPreferredSize(taskComboBox.getMinimumSize());
		userNameTextField.setName( "null" );
		gbc_profiler_Panel.gridx = 1;
		gbc_profiler_Panel.gridy = 2;
		gbc_profiler_Panel.gridwidth = 3;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 0;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( userNameTextField, gbc_profiler_Panel );
		add( userNameTextField );
		
		charCounterLabel = new JLabel( "Keystroke Counter"  );
		gbc_profiler_Panel.gridx = 24;
		gbc_profiler_Panel.gridy = 1;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( charCounterLabel, gbc_profiler_Panel );
		add( charCounterLabel );

		charCounterTextField = new JTextField(5);
		charCounterTextField.setBackground( new Color( 238,238,238 ) );
		charCounterTextField.setPreferredSize(taskComboBox.getMinimumSize());
		charCounterTextField.setEditable( false );
		gbc_profiler_Panel.gridx = 23;
		gbc_profiler_Panel.gridy = 2;
		gbc_profiler_Panel.gridwidth = 2;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 0;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( charCounterTextField, gbc_profiler_Panel );
		add( charCounterTextField );
		
		profiler_directory_Panel = new JPanel();
		GridBagLayout gbl_profiler_directory_Panel = new GridBagLayout();
		GridBagConstraints gbc_profiler_directory_Panel = new GridBagConstraints();
		profiler_directory_Panel.setLayout( gbl_profiler_directory_Panel );
		
		profilerUserPathLabel = new JLabel("Choose User's Path: ");
		gbc_profiler_directory_Panel.gridx = 0;
		gbc_profiler_directory_Panel.gridy = 0;
		gbc_profiler_directory_Panel.gridwidth = 1;
		gbc_profiler_directory_Panel.gridheight = 1;
		gbc_profiler_directory_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_directory_Panel.weightx = 1;
		gbc_profiler_directory_Panel.weighty = 0;
		gbc_profiler_directory_Panel.anchor = GridBagConstraints.WEST;
		gbl_profiler_directory_Panel.setConstraints( profilerUserPathLabel, gbc_profiler_directory_Panel );
		profiler_directory_Panel.add( profilerUserPathLabel );
		
		profiler_user_Folder_Panel = new JPanel();
		profiler_user_Folder_Panel.setLayout(new BorderLayout());
		
		profilerUserPathTextField = new JTextField(40);
		profilerUserPathTextField.setHorizontalAlignment(SwingConstants.LEADING);
		profilerUserPathTextField.setEditable(false);
		profilerUserPathTextField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		profilerChooseUserPathBut = new JButton( "Browse..."  );
		profilerChooseUserPathBut.setActionCommand("ProfilerUserPath");
		profilerChooseUserPathBut.setPreferredSize(new Dimension(110,18));
		profilerChooseUserPathBut.setHorizontalAlignment(SwingConstants.RIGHT);
		profilerChooseUserPathBut.setToolTipText("<html>For a correct collection of samples you MUST <br>choose the folder containing all the Users, <br>for example folder \"USERS\" containing <br>folders \"user1\", \"user2\", etc.</html>");

		profiler_user_Folder_Panel.add(profilerUserPathTextField, BorderLayout.CENTER);
		profiler_user_Folder_Panel.add(profilerChooseUserPathBut, BorderLayout.EAST);
		
		gbc_profiler_directory_Panel.gridx = 3;
		gbc_profiler_directory_Panel.gridy = 0;
		gbc_profiler_directory_Panel.gridwidth = 1;
		gbc_profiler_directory_Panel.gridheight = 1;
		gbc_profiler_directory_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_directory_Panel.weightx = 1;
		gbc_profiler_directory_Panel.weighty = 0;
		gbc_profiler_directory_Panel.anchor = GridBagConstraints.WEST;
		gbl_profiler_directory_Panel.setConstraints( profiler_user_Folder_Panel, gbc_profiler_directory_Panel );
		profiler_directory_Panel.add( profiler_user_Folder_Panel );
		
		gbc_profiler_Panel.gridx = 1;
		gbc_profiler_Panel.gridy = 3;
		gbc_profiler_Panel.gridwidth = 24;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( profiler_directory_Panel, gbc_profiler_Panel );
		add( profiler_directory_Panel );
		
		inputAreaLabel = new JLabel( "Type the sample here"  );
		gbc_profiler_Panel.gridx = 1;
		gbc_profiler_Panel.gridy = 4;
		gbc_profiler_Panel.gridwidth = 17;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( inputAreaLabel, gbc_profiler_Panel );
		add( inputAreaLabel );

		inputArea = new JTextArea(15,50);
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		JScrollPane scpProfilerInputArea = new JScrollPane( inputArea );
		scpProfilerInputArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbc_profiler_Panel.gridx = 1;
		gbc_profiler_Panel.gridy = 5;
		gbc_profiler_Panel.gridwidth = 24;
		gbc_profiler_Panel.gridheight = 6;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( scpProfilerInputArea, gbc_profiler_Panel );
		add( scpProfilerInputArea );

		btProfilerClearBut = new JButton( "Clear"  );
		gbc_profiler_Panel.gridx = 11;
		gbc_profiler_Panel.gridy = 12;
		gbc_profiler_Panel.gridwidth = 3;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 0;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( btProfilerClearBut, gbc_profiler_Panel );
		add( btProfilerClearBut );

		btProfilerOKBut = new JButton( "Save the sample"  );
		gbc_profiler_Panel.gridx = 6;
		gbc_profiler_Panel.gridy = 12;
		gbc_profiler_Panel.gridwidth = 3;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 0;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( btProfilerOKBut, gbc_profiler_Panel );
		add( btProfilerOKBut );

		lbDistancerP5 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 25;
		gbc_profiler_Panel.gridy = 3;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP5, gbc_profiler_Panel );
		add( lbDistancerP5 );

		lbDistancerP6 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 25;
		gbc_profiler_Panel.gridy = 11;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP6, gbc_profiler_Panel );
		add( lbDistancerP6 );

		lbDistancerP2 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 25;
		gbc_profiler_Panel.gridy = 0;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP2, gbc_profiler_Panel );
		add( lbDistancerP2 );

		lbDistancerP1 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 0;
		gbc_profiler_Panel.gridy = 0;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP1, gbc_profiler_Panel );
		add( lbDistancerP1 );

		lbDistancerP7 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 25;
		gbc_profiler_Panel.gridy = 13;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP7, gbc_profiler_Panel );
		add( lbDistancerP7 );

		lbDistancerP4 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 17;
		gbc_profiler_Panel.gridy = 2;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP4, gbc_profiler_Panel );
		add( lbDistancerP4 );

		lbDistancerP3 = new JLabel( " "  );
		gbc_profiler_Panel.gridx = 5;
		gbc_profiler_Panel.gridy = 2;
		gbc_profiler_Panel.gridwidth = 1;
		gbc_profiler_Panel.gridheight = 1;
		gbc_profiler_Panel.fill = GridBagConstraints.BOTH;
		gbc_profiler_Panel.weightx = 1;
		gbc_profiler_Panel.weighty = 1;
		gbc_profiler_Panel.anchor = GridBagConstraints.NORTH;
		gb_profiler_Panel.setConstraints( lbDistancerP3, gbc_profiler_Panel );
		add( lbDistancerP3 );
		
	}

}
