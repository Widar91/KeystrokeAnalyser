package it.unito.di.educ.bachelor_thesis.gui.experiments;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 * This class implements the GUI's Experiments Panel.
 * 
 * @author Eddy Bertoluzzo
 *
 */
public class ExperimentsPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private	JPanel options;

	private	JPanel r_Panel;
	private	JCheckBox cbBox0;
	private	JCheckBox cbBox1;
	private	JCheckBox cbBox2;
	private	JCheckBox cbBox3;
	private	JCheckBox cbBox4;

	private	JPanel a_Panel;
	private	JCheckBox cbBox5;
	private	JCheckBox cbBox6;
	private	JCheckBox cbBox7;
	private	JCheckBox cbBox8;
	private	JCheckBox cbBox9;
	
	private JLabel lbDistancerO6;
	private	JLabel lbDistancerO5;
	private	JLabel lbDistancerO4;
	private	JLabel lbDistancerO3;
	private	JLabel lbDistancerO2;
	private	JLabel lbDistancerO1;

	private	JPanel costant_Panel;
	private	JLabel lbTLabel;
	private	JLabel lbKLabel;
	private	JTextField tTextField;
	private	JTextField kTextField;

	private	JPanel other_Panel;
	private JLabel lbMinDurationLabel;
	private	JLabel lbMaxDurationLabel;
	private JLabel lbMinSharedLabel;
	private	JLabel lbMaxSampleLabel;
	private JLabel lbMinSampleLabel;
	private JTextField minDurationTextField;
	private	JTextField maxDurationTextField;
	private JTextField minSharedNGraphsTextField;
	private	JTextField maxSampleTextField;
	private JTextField minSampleTextField;
	private JCheckBox cbLowerCase;
	
	private	JPanel buttons_Panel;
	private JPanel user_Folder_Panel;
	private JPanel impostors_Folder_Panel;
	private	JButton chooseUserPathBut;
	private	JButton chooseImpostorsPathBut;
	private JTextField usersPathTextField;
	private JTextField impostorsPathTextField;
	private JLabel userPathLabel;
	private JLabel impostorsPathLabel;

	private JPanel south_Panel;
	private JComboBox taskComboBox;
	private JButton run_Button;
	private JProgressBar progressBar;
	
	
	public ExperimentsPanel() {
		initialize();
	}
	
	public JButton getChooseUserPathButton() {
		return chooseUserPathBut;
	}
	
	public JButton getChooseImpostorsPathButton() {
		return chooseImpostorsPathBut;
	}

	public JButton getRunButton() {
		return run_Button;
	}
	
	public JTextField getConstantTTextField() {
		return tTextField;
	}

	public JTextField getConstantKTextField() {
		return kTextField;
	}
	
	public JTextField getNGraphMinDurationTextField() {
		return minDurationTextField;
	}

	public JTextField getNGraphMaxDurationTextField() {
		return maxDurationTextField;
	}
	
	public JTextField getMinNumberOfSamplesTextField() {
		return minSampleTextField;
	}

	public JTextField getMaxNumberOfSamplesTextField() {
		return maxSampleTextField;
	}
	
	public JTextField getMinSharedNGraphsTextField() {
		return minSharedNGraphsTextField;
	}
	
	public boolean getLowerCaseChoice() {
		return cbLowerCase.isSelected();
	}
	
	public JTextField getUserPathTextField() {
		return usersPathTextField;
	}
	
	public JTextField getImpostorsPathTextField() {
		return impostorsPathTextField;
	}
	
	public JComboBox getTaskComboBox() {
		return taskComboBox;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	/**
	* This method returns if the specified Box is selected or not.
	* @param index the box index.
	* @return true if the box is selected, false otherwise.
	*/
	public boolean isBoxSelected(int index) {
		switch(index) {
			case 0: return cbBox0.isSelected();
			case 1: return cbBox1.isSelected();
			case 2: return cbBox2.isSelected();
			case 3: return cbBox3.isSelected();
			case 4: return cbBox4.isSelected();
			case 5: return cbBox5.isSelected();
			case 6: return cbBox6.isSelected();
			case 7: return cbBox7.isSelected();
			case 8: return cbBox8.isSelected();
			case 9: return cbBox9.isSelected();
			default: return false;
		}
	}
	
	private void initialize() {
		
		setLayout(new BorderLayout());

		options = new JPanel();
		GridBagLayout gbl_options = new GridBagLayout();
		GridBagConstraints gbc_options = new GridBagConstraints();
		options.setLayout( gbl_options );
		
		//R Panel
		r_Panel = new JPanel();
		r_Panel.setBorder( BorderFactory.createTitledBorder( "'R' Measure" ) );
		GridBagLayout gbl_r_Panel = new GridBagLayout();
		GridBagConstraints gbc_r_Panel = new GridBagConstraints();
		r_Panel.setLayout( gbl_r_Panel );

		cbBox0 = new JCheckBox( "2-Graphs"  );
		cbBox0.setSelected( true );
		gbc_r_Panel.gridx = 0;
		gbc_r_Panel.gridy = 0;
		gbc_r_Panel.gridwidth = 1;
		gbc_r_Panel.gridheight = 1;
		gbc_r_Panel.fill = GridBagConstraints.BOTH;
		gbc_r_Panel.weightx = 1;
		gbc_r_Panel.weighty = 0;
		gbc_r_Panel.anchor = GridBagConstraints.NORTH;
		gbl_r_Panel.setConstraints( cbBox0, gbc_r_Panel );
		r_Panel.add( cbBox0 );

		cbBox1 = new JCheckBox( "3-Graphs"  );
		cbBox1.setSelected( true );
		gbc_r_Panel.gridx = 0;
		gbc_r_Panel.gridy = 1;
		gbc_r_Panel.gridwidth = 1;
		gbc_r_Panel.gridheight = 1;
		gbc_r_Panel.fill = GridBagConstraints.BOTH;
		gbc_r_Panel.weightx = 1;
		gbc_r_Panel.weighty = 0;
		gbc_r_Panel.anchor = GridBagConstraints.NORTH;
		gbl_r_Panel.setConstraints( cbBox1, gbc_r_Panel );
		r_Panel.add( cbBox1 );

		cbBox2 = new JCheckBox( "4-Graphs"  );
		cbBox2.setSelected( false );
		gbc_r_Panel.gridx = 0;
		gbc_r_Panel.gridy = 2;
		gbc_r_Panel.gridwidth = 1;
		gbc_r_Panel.gridheight = 1;
		gbc_r_Panel.fill = GridBagConstraints.BOTH;
		gbc_r_Panel.weightx = 1;
		gbc_r_Panel.weighty = 0;
		gbc_r_Panel.anchor = GridBagConstraints.NORTH;
		gbl_r_Panel.setConstraints( cbBox2, gbc_r_Panel );
		r_Panel.add( cbBox2 );

		cbBox3 = new JCheckBox( "5-Graphs"  );
		cbBox3.setSelected( false );
		gbc_r_Panel.gridx = 0;
		gbc_r_Panel.gridy = 3;
		gbc_r_Panel.gridwidth = 1;
		gbc_r_Panel.gridheight = 1;
		gbc_r_Panel.fill = GridBagConstraints.BOTH;
		gbc_r_Panel.weightx = 1;
		gbc_r_Panel.weighty = 0;
		gbc_r_Panel.anchor = GridBagConstraints.NORTH;
		gbl_r_Panel.setConstraints( cbBox3, gbc_r_Panel );
		r_Panel.add( cbBox3 );

		cbBox4 = new JCheckBox( "Normalize w.r.t. the num of N-Graphs (Old Way) "  );
		cbBox4.setSelected( false );
		gbc_r_Panel.gridx = 0;
		gbc_r_Panel.gridy = 4;
		gbc_r_Panel.gridwidth = 1;
		gbc_r_Panel.gridheight = 1;
		gbc_r_Panel.fill = GridBagConstraints.BOTH;
		gbc_r_Panel.weightx = 1;
		gbc_r_Panel.weighty = 0;
		gbc_r_Panel.anchor = GridBagConstraints.NORTH;
		gbl_r_Panel.setConstraints( cbBox4, gbc_r_Panel );
		r_Panel.add( cbBox4 );
		
		gbc_options.gridx = 1;
		gbc_options.gridy = 1;
		gbc_options.gridwidth = 7;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.VERTICAL;
		gbc_options.weightx = 1;
		gbc_options.weighty = 0;
		gbc_options.anchor = GridBagConstraints.NORTHWEST;
		gbl_options.setConstraints( r_Panel, gbc_options );
		options.add( r_Panel );
		
		//A Panel
		a_Panel = new JPanel();
		a_Panel.setBorder( BorderFactory.createTitledBorder( "'A' Measure" ) );
		GridBagLayout gbl_a_Panel = new GridBagLayout();
		GridBagConstraints gbc_a_Panel = new GridBagConstraints();
		a_Panel.setLayout( gbl_a_Panel );

		cbBox5 = new JCheckBox( "2-Graphs"  );
		cbBox5.setSelected( true );
		gbc_a_Panel.gridx = 0;
		gbc_a_Panel.gridy = 0;
		gbc_a_Panel.gridwidth = 1;
		gbc_a_Panel.gridheight = 1;
		gbc_a_Panel.fill = GridBagConstraints.BOTH;
		gbc_a_Panel.weightx = 1;
		gbc_a_Panel.weighty = 0;
		gbc_a_Panel.anchor = GridBagConstraints.NORTH;
		gbl_a_Panel.setConstraints( cbBox5, gbc_a_Panel );
		a_Panel.add( cbBox5 );

		cbBox6 = new JCheckBox( "3-Graphs"  );
		cbBox6.setSelected( true );
		gbc_a_Panel.gridx = 0;
		gbc_a_Panel.gridy = 1;
		gbc_a_Panel.gridwidth = 1;
		gbc_a_Panel.gridheight = 1;
		gbc_a_Panel.fill = GridBagConstraints.BOTH;
		gbc_a_Panel.weightx = 1;
		gbc_a_Panel.weighty = 0;
		gbc_a_Panel.anchor = GridBagConstraints.NORTH;
		gbl_a_Panel.setConstraints( cbBox6, gbc_a_Panel );
		a_Panel.add( cbBox6 );

		cbBox7 = new JCheckBox( "4-Graphs"  );
		cbBox7.setSelected( false );
		gbc_a_Panel.gridx = 0;
		gbc_a_Panel.gridy = 2;
		gbc_a_Panel.gridwidth = 1;
		gbc_a_Panel.gridheight = 1;
		gbc_a_Panel.fill = GridBagConstraints.BOTH;
		gbc_a_Panel.weightx = 1;
		gbc_a_Panel.weighty = 0;
		gbc_a_Panel.anchor = GridBagConstraints.NORTH;
		gbl_a_Panel.setConstraints( cbBox7, gbc_a_Panel );
		a_Panel.add( cbBox7 );

		cbBox8 = new JCheckBox( "5-Graphs"  );
		cbBox8.setSelected( false );
		gbc_a_Panel.gridx = 0;
		gbc_a_Panel.gridy = 3;
		gbc_a_Panel.gridwidth = 1;
		gbc_a_Panel.gridheight = 1;
		gbc_a_Panel.fill = GridBagConstraints.BOTH;
		gbc_a_Panel.weightx = 1;
		gbc_a_Panel.weighty = 0;
		gbc_a_Panel.anchor = GridBagConstraints.NORTH;
		gbl_a_Panel.setConstraints( cbBox8, gbc_a_Panel );
		a_Panel.add( cbBox8 );

		cbBox9 = new JCheckBox( "Normalize w.r.t. the num of N-Graphs (Old Way) "  );
		cbBox9.setSelected( false );
		gbc_a_Panel.gridx = 0;
		gbc_a_Panel.gridy = 4;
		gbc_a_Panel.gridwidth = 1;
		gbc_a_Panel.gridheight = 1;
		gbc_a_Panel.fill = GridBagConstraints.BOTH;
		gbc_a_Panel.weightx = 1;
		gbc_a_Panel.weighty = 0;
		gbc_a_Panel.anchor = GridBagConstraints.NORTH;
		gbl_a_Panel.setConstraints( cbBox9, gbc_a_Panel );
		a_Panel.add( cbBox9 );
		
		gbc_options.gridx = 10;
		gbc_options.gridy = 1;
		gbc_options.gridwidth = 5;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.VERTICAL;
		gbc_options.weightx = 1;
		gbc_options.weighty = 0;
		gbc_options.anchor = GridBagConstraints.NORTHWEST;
		gbl_options.setConstraints( a_Panel, gbc_options );
		options.add( a_Panel );
		
		//Distances (for layout spacing)
		JLabel lbDistancerO7 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 10;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 2;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO7, gbc_options );
		options.add( lbDistancerO7 );
		
		lbDistancerO6 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 9;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 2;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO6, gbc_options );
		options.add( lbDistancerO6 );
		
		lbDistancerO5 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 1;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO5, gbc_options );
		options.add( lbDistancerO5 );

		lbDistancerO4 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 7;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO4, gbc_options );
		options.add( lbDistancerO4 );

		lbDistancerO3 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 5;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO3, gbc_options );
		options.add( lbDistancerO3 );

		lbDistancerO2 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 2;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO2, gbc_options );
		options.add( lbDistancerO2 );

		lbDistancerO1 = new JLabel( ""  );
		gbc_options.gridx = 0;
		gbc_options.gridy = 0;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 1;
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( lbDistancerO1, gbc_options );
		options.add( lbDistancerO1 );
		
		//Costant Panel
		costant_Panel = new JPanel();
		costant_Panel.setBorder( BorderFactory.createTitledBorder( "Constants" ) );
		GridBagLayout gbl_costant_Panel = new GridBagLayout();
		GridBagConstraints gbcCostantPanel = new GridBagConstraints();
		costant_Panel.setLayout( gbl_costant_Panel );

		tTextField = new JTextField("1.05", 5);
		tTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbcCostantPanel.gridx = 1;
		gbcCostantPanel.gridy = 0;
		gbcCostantPanel.gridwidth = 1;
		gbcCostantPanel.gridheight = 1;
		gbcCostantPanel.fill = GridBagConstraints.BOTH;
		gbcCostantPanel.weightx = 1;
		gbcCostantPanel.weighty = 0;
		gbcCostantPanel.insets = new Insets(3, 0 , 0, 0);
		gbcCostantPanel.anchor = GridBagConstraints.WEST;
		gbl_costant_Panel.setConstraints( tTextField, gbcCostantPanel );
		costant_Panel.add( tTextField );

		kTextField = new JTextField("0.5", 5);
		kTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbcCostantPanel.gridx = 1;
		gbcCostantPanel.gridy = 1;
		gbcCostantPanel.gridwidth = 1;
		gbcCostantPanel.gridheight = 1;
		gbcCostantPanel.fill = GridBagConstraints.BOTH;
		gbcCostantPanel.weightx = 1;
		gbcCostantPanel.weighty = 0;
		gbcCostantPanel.anchor = GridBagConstraints.WEST;
		gbl_costant_Panel.setConstraints( kTextField, gbcCostantPanel );
		costant_Panel.add( kTextField );

		lbTLabel = new JLabel( " T  (t > 1): "  );
		gbcCostantPanel.gridx = 0;
		gbcCostantPanel.gridy = 0;
		gbcCostantPanel.gridwidth = 1;
		gbcCostantPanel.gridheight = 1;
		gbcCostantPanel.fill = GridBagConstraints.VERTICAL;
		gbcCostantPanel.weightx = 1;
		gbcCostantPanel.weighty = 1;
		gbcCostantPanel.anchor = GridBagConstraints.WEST;
		gbl_costant_Panel.setConstraints( lbTLabel, gbcCostantPanel );
		costant_Panel.add( lbTLabel );

		lbKLabel = new JLabel( " K  (0 < k <= 1): "  );
		gbcCostantPanel.gridx = 0;
		gbcCostantPanel.gridy = 1;
		gbcCostantPanel.gridwidth = 1;
		gbcCostantPanel.gridheight = 1;
		gbcCostantPanel.fill = GridBagConstraints.VERTICAL;
		gbcCostantPanel.weightx = 1;
		gbcCostantPanel.weighty = 1;
		gbcCostantPanel.anchor = GridBagConstraints.WEST;
		gbl_costant_Panel.setConstraints( lbKLabel, gbcCostantPanel );
		costant_Panel.add( lbKLabel );
		
		gbc_options.gridx = 1;
		gbc_options.gridy = 3;
		gbc_options.gridwidth = 1;
		gbc_options.gridheight = 1;
		gbc_options.fill = GridBagConstraints.VERTICAL;
		gbc_options.weightx = 1;
		gbc_options.weighty = 0;
		gbc_options.anchor = GridBagConstraints.WEST;
		gbl_options.setConstraints( costant_Panel, gbc_options );
		options.add( costant_Panel );
		
		//Other Options Panel
		other_Panel = new JPanel();
		other_Panel.setBorder( BorderFactory.createTitledBorder( "Other" ) );
		GridBagLayout gbl_other_Panel = new GridBagLayout();
		GridBagConstraints gbc_other_Panel = new GridBagConstraints();
		other_Panel.setLayout( gbl_other_Panel );
		
		minDurationTextField = new JTextField("20", 5);
		minDurationTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_other_Panel.gridx = 3;
		gbc_other_Panel.gridy = 0;
		gbc_other_Panel.gridwidth = 1;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.NONE;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 0;
		gbc_other_Panel.insets = new Insets(3, 0 , 0, 0);
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( minDurationTextField, gbc_other_Panel );
		other_Panel.add( minDurationTextField );

		lbMinDurationLabel = new JLabel( " Min 2-Graph Duration (ms): "  );
		gbc_other_Panel.gridx = 0;
		gbc_other_Panel.gridy = 0;
		gbc_other_Panel.gridwidth = 3;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.BOTH;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 1;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( lbMinDurationLabel, gbc_other_Panel );
		other_Panel.add( lbMinDurationLabel );
		

		maxDurationTextField = new JTextField("1000", 5);
		maxDurationTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_other_Panel.gridx = 3;
		gbc_other_Panel.gridy = 1;
		gbc_other_Panel.gridwidth = 1;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.NONE;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 0;
		gbc_other_Panel.insets = new Insets(3, 0 , 0, 0);
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( maxDurationTextField, gbc_other_Panel );
		other_Panel.add( maxDurationTextField );

		lbMaxDurationLabel = new JLabel( " Max 2-Graph Duration (ms): "  );
		gbc_other_Panel.gridx = 0;
		gbc_other_Panel.gridy = 1;
		gbc_other_Panel.gridwidth = 3;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.BOTH;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 1;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( lbMaxDurationLabel, gbc_other_Panel );
		other_Panel.add( lbMaxDurationLabel );
		
		minSharedNGraphsTextField = new JTextField("10", 5);
		minSharedNGraphsTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_other_Panel.gridx = 3;
		gbc_other_Panel.gridy = 2;
		gbc_other_Panel.gridwidth = 1;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.NONE;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 0;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( minSharedNGraphsTextField, gbc_other_Panel );
		other_Panel.add( minSharedNGraphsTextField );

		lbMinSharedLabel = new JLabel( " Min Num of Shared N-Graphs: "  );
		gbc_other_Panel.gridx = 0;
		gbc_other_Panel.gridy = 2;
		gbc_other_Panel.gridwidth = 3;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.BOTH;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 1;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( lbMinSharedLabel, gbc_other_Panel );
		other_Panel.add( lbMinSharedLabel );

		maxSampleTextField = new JTextField("15", 5);
		maxSampleTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_other_Panel.gridx = 3;
		gbc_other_Panel.gridy = 3;
		gbc_other_Panel.gridwidth = 1;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.NONE;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 0;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( maxSampleTextField, gbc_other_Panel );
		other_Panel.add( maxSampleTextField );

		lbMaxSampleLabel = new JLabel( " Max Num of Samples per User: "  );
		gbc_other_Panel.gridx = 0;
		gbc_other_Panel.gridy = 3;
		gbc_other_Panel.gridwidth = 3;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.BOTH;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 1;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( lbMaxSampleLabel, gbc_other_Panel );
		other_Panel.add( lbMaxSampleLabel );
		
		minSampleTextField = new JTextField("15", 5);
		minSampleTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_other_Panel.gridx = 3;
		gbc_other_Panel.gridy = 4;
		gbc_other_Panel.gridwidth = 1;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.NONE;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 0;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( minSampleTextField, gbc_other_Panel );
		other_Panel.add( minSampleTextField );

		lbMinSampleLabel = new JLabel( " Min Num of Samples per User: "  );
		gbc_other_Panel.gridx = 0;
		gbc_other_Panel.gridy = 4;
		gbc_other_Panel.gridwidth = 3;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.BOTH;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 1;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( lbMinSampleLabel, gbc_other_Panel );
		other_Panel.add( lbMinSampleLabel );
		
		cbLowerCase = new JCheckBox("Convert Keystrokes to Lower Case");
		cbLowerCase.setSelected(true);
		gbc_other_Panel.gridx = 0;
		gbc_other_Panel.gridy = 5;
		gbc_other_Panel.gridwidth = 1;
		gbc_other_Panel.gridheight = 1;
		gbc_other_Panel.fill = GridBagConstraints.NONE;
		gbc_other_Panel.weightx = 1;
		gbc_other_Panel.weighty = 0;
		gbc_other_Panel.anchor = GridBagConstraints.WEST;
		gbl_other_Panel.setConstraints( cbLowerCase, gbc_other_Panel );
		other_Panel.add( cbLowerCase );
		
		gbc_options.gridx = 10;
		gbc_options.gridy = 3;
		gbc_options.gridwidth = 2;
		gbc_options.gridheight = 2;
		gbc_options.fill = GridBagConstraints.VERTICAL;
		gbc_options.weightx = 1;
		gbc_options.weighty = 0;
		gbc_options.anchor = GridBagConstraints.WEST;
		gbl_options.setConstraints( other_Panel, gbc_options );
		options.add( other_Panel );
		
		//Buttons Panel
		buttons_Panel = new JPanel();
		buttons_Panel.setBorder( BorderFactory.createTitledBorder( "Folders" ) );
		GridBagLayout gbl_buttons_Panel = new GridBagLayout();
		GridBagConstraints gbc_buttons_Panel = new GridBagConstraints();
		buttons_Panel.setLayout( gbl_buttons_Panel );
		
		userPathLabel = new JLabel(" Choose Users' Path: ");
		gbc_buttons_Panel.gridx = 0;
		gbc_buttons_Panel.gridy = 0;
		gbc_buttons_Panel.gridwidth = 1;
		gbc_buttons_Panel.gridheight = 1;
		gbc_buttons_Panel.fill = GridBagConstraints.BOTH;
		gbc_buttons_Panel.weightx = 1;
		gbc_buttons_Panel.weighty = 0;
		gbc_buttons_Panel.anchor = GridBagConstraints.WEST;
		gbl_buttons_Panel.setConstraints( userPathLabel, gbc_buttons_Panel );
		buttons_Panel.add( userPathLabel );
		
		user_Folder_Panel = new JPanel();
		user_Folder_Panel.setLayout(new BorderLayout());
		
		usersPathTextField = new JTextField(40);
		usersPathTextField.setHorizontalAlignment(SwingConstants.LEADING);
		usersPathTextField.setEditable(false);
		usersPathTextField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		chooseUserPathBut = new JButton( "Browse..."  );
		chooseUserPathBut.setActionCommand("UserPath");
		chooseUserPathBut.setPreferredSize(new Dimension(110,18));
		chooseUserPathBut.setHorizontalAlignment(SwingConstants.RIGHT);
		chooseUserPathBut.setToolTipText("<html>For a correct collection of samples you MUST <br>choose the folder containing all the Users, <br>for example folder \"USERS\" containing <br>folders \"user1\", \"user2\", etc.</html>");

		user_Folder_Panel.add(usersPathTextField, BorderLayout.CENTER);
		user_Folder_Panel.add(chooseUserPathBut, BorderLayout.EAST);
		
		gbc_buttons_Panel.gridx = 3;
		gbc_buttons_Panel.gridy = 0;
		gbc_buttons_Panel.gridwidth = 1;
		gbc_buttons_Panel.gridheight = 1;
		gbc_buttons_Panel.fill = GridBagConstraints.BOTH;
		gbc_buttons_Panel.weightx = 1;
		gbc_buttons_Panel.weighty = 0;
		gbc_buttons_Panel.anchor = GridBagConstraints.WEST;
		gbl_buttons_Panel.setConstraints( user_Folder_Panel, gbc_buttons_Panel );
		buttons_Panel.add( user_Folder_Panel );
		
		impostorsPathLabel = new JLabel(" Choose Impostors' Path: ");
		gbc_buttons_Panel.gridx = 0;
		gbc_buttons_Panel.gridy = 1;
		gbc_buttons_Panel.gridwidth = 1;
		gbc_buttons_Panel.gridheight = 1;
		gbc_buttons_Panel.fill = GridBagConstraints.BOTH;
		gbc_buttons_Panel.weightx = 1;
		gbc_buttons_Panel.weighty = 0;
		gbc_buttons_Panel.anchor = GridBagConstraints.WEST;
		gbl_buttons_Panel.setConstraints( impostorsPathLabel, gbc_buttons_Panel );
		buttons_Panel.add( impostorsPathLabel );
		
		impostors_Folder_Panel = new JPanel();
		impostors_Folder_Panel.setLayout(new BorderLayout());
		
		impostorsPathTextField = new JTextField(5);
		impostorsPathTextField.setHorizontalAlignment(SwingConstants.LEADING);
		impostorsPathTextField.setEditable(false);
		impostorsPathTextField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		chooseImpostorsPathBut = new JButton( "Browse..."  );
		chooseImpostorsPathBut.setActionCommand("ImpostorsPath");
		chooseImpostorsPathBut.setPreferredSize(new Dimension(110,18));
		chooseImpostorsPathBut.setHorizontalAlignment(SwingConstants.RIGHT);
		chooseImpostorsPathBut.setToolTipText("<html>For a correct collection of samples you MUST <br>choose the folder containing all the Impostors' samples, <br>for example folder \"IMPOSTORS\" containing <br>samples \"I1\", \"I2\", etc.</html>");
		
		impostors_Folder_Panel.add(impostorsPathTextField, BorderLayout.CENTER);
		impostors_Folder_Panel.add(chooseImpostorsPathBut, BorderLayout.EAST);
		
		gbc_buttons_Panel.gridx = 3;
		gbc_buttons_Panel.gridy = 1;
		gbc_buttons_Panel.gridwidth = 1;
		gbc_buttons_Panel.gridheight = 1;
		gbc_buttons_Panel.fill = GridBagConstraints.BOTH;
		gbc_buttons_Panel.weightx = 1;
		gbc_buttons_Panel.weighty = 0;
		gbc_buttons_Panel.anchor = GridBagConstraints.WEST;
		gbl_buttons_Panel.setConstraints( impostors_Folder_Panel, gbc_buttons_Panel );
		buttons_Panel.add( impostors_Folder_Panel );
		
		gbc_options.gridx = 0;
		gbc_options.gridy = 6;
		gbc_options.gridwidth = GridBagConstraints.WEST;
		gbc_options.gridheight = 2;
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.weightx = 1;
		gbc_options.weighty = 0;
		gbc_options.ipadx = 20;
		gbc_options.insets = new Insets(0, 15, 0, 15);
		gbc_options.anchor = GridBagConstraints.NORTH;
		gbl_options.setConstraints( buttons_Panel, gbc_options );
		options.add( buttons_Panel );
		
		//Run Button
		run_Button = new JButton("Run Tests");
		run_Button.setPreferredSize(new Dimension(120, 25));
		run_Button.setVerticalAlignment(SwingConstants.TOP);
		run_Button.setToolTipText("<html>The results will be saved in a folder named \"keystrokeLog\" placed in your HOME folder</html>");

		
		JPanel run_Panel = new JPanel();
		run_Panel.add(run_Button);
		
		//Choice
		String [] tasks = {"Choose Task...","CLASSIFICATION", "AUTHENTICATION", "IDENTIFICATION"};
		taskComboBox = new JComboBox(tasks);
		taskComboBox.setPreferredSize(new Dimension(200, 35)); 
		
		JPanel task_Panel = new JPanel();
		task_Panel.add(taskComboBox);
		
		//Progress Bar
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setString("");
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
		
		
		//South Panel
		south_Panel = new JPanel();
		south_Panel.setLayout(new GridLayout(3,3));
		south_Panel.add(new JLabel(""));
		south_Panel.add(task_Panel);
		south_Panel.add(new JLabel(""));
		south_Panel.add(new JLabel(""));
		south_Panel.add(progressBar);
		south_Panel.add(new JLabel(""));
		south_Panel.add(new JLabel(""));
		south_Panel.add(run_Panel);
		south_Panel.add(new JLabel(""));
		
		//Add "Options Panel" and "Run Button" to the Container
		add(options, BorderLayout.CENTER);
		add(south_Panel, BorderLayout.SOUTH);
	}
}
