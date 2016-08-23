/*
 * Created by JFormDesigner on Wed Feb 17 15:39:22 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class PreferencesWindow extends JFrame {
	public PreferencesWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel13 = new JPanel();
		scrollPane1 = new JScrollPane();
		panel3 = new JPanel();
		panel4 = new JPanel();
		monitorAll = new JCheckBox();
		panel5 = new JPanel();
		panel6 = new JPanel();
		label5 = new JLabel();
		timeRegister = new JTextField();
		label6 = new JLabel();
		panel8 = new JPanel();
		loopTime = new JTextField();
		label11 = new JLabel();
		panel9 = new JPanel();
		radioGeneral = new JRadioButton();
		radioSplitted = new JRadioButton();
		generalTimeRangePanel = new JPanel();
		label7 = new JLabel();
		panel7 = new JPanel();
		generalHour = new JSpinner();
		label8 = new JLabel();
		generalMinute = new JSpinner();
		label9 = new JLabel();
		generalSecond = new JSpinner();
		label10 = new JLabel();
		SplittedTimeRangePanel = new JPanel();
		NumericRangePane = new JPanel();
		label20 = new JLabel();
		panel10 = new JPanel();
		numericHour = new JSpinner();
		label21 = new JLabel();
		numericMinute = new JSpinner();
		label22 = new JLabel();
		numericSecond = new JSpinner();
		label23 = new JLabel();
		categoryRangePane = new JPanel();
		label24 = new JLabel();
		panel11 = new JPanel();
		categoryHour = new JSpinner();
		label25 = new JLabel();
		categoryMinute = new JSpinner();
		label26 = new JLabel();
		categorySecond = new JSpinner();
		label27 = new JLabel();
		resetVariablesBox = new JCheckBox();
		resetRangeBox = new JCheckBox();
		panel1 = new JPanel();
		panel2 = new JPanel();
		databaseInfoButton = new JButton();
		autoSaveBox = new JCheckBox();
		panel14 = new JPanel();
		panel15 = new JPanel();
		label1 = new JLabel();
		beginField = new JSpinner();
		label2 = new JLabel();
		memSetPointField = new JSpinner();
		label3 = new JLabel();
		panel12 = new JPanel();
		samplingField = new JSpinner();
		calculateButton = new JButton();
		label4 = new JLabel();
		kcField = new JSpinner();
		label12 = new JLabel();
		z0Field = new JSpinner();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		setTitle("Set Preferences");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== panel13 ========
				{
					panel13.setLayout(new GridBagLayout());
					((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

					//======== scrollPane1 ========
					{

						//======== panel3 ========
						{
							panel3.setLayout(new GridBagLayout());
							((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
							((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
							((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
							((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

							//======== panel4 ========
							{
								panel4.setBorder(new TitledBorder("General Info"));
								panel4.setLayout(new GridBagLayout());
								((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//---- monitorAll ----
								monitorAll.setText("Monitor All Variables");
								monitorAll.setToolTipText("When this option is selected, all variables of an agent are automatically added to the monitoring list. Be careful, adding all variables to monitoring can let the system overloaded.");
								panel4.add(monitorAll, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel3.add(panel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel5 ========
							{
								panel5.setBorder(new TitledBorder("Time Information"));
								panel5.setLayout(new GridBagLayout());
								((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
								((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

								//======== panel6 ========
								{
									panel6.setLayout(new GridBagLayout());
									((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
									((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

									//---- label5 ----
									label5.setText("Last Time Registered:");
									panel6.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- timeRegister ----
									timeRegister.setEditable(false);
									panel6.add(timeRegister, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label6 ----
									label6.setText("Last Loop Time:");
									panel6.add(label6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//======== panel8 ========
									{
										panel8.setLayout(new GridBagLayout());
										((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
										((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- loopTime ----
										loopTime.setEditable(false);
										panel8.add(loopTime, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label11 ----
										label11.setText("Milliseconds");
										panel8.add(label11, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
									}
									panel6.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel5.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== panel9 ========
								{
									panel9.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
									panel9.setLayout(new GridBagLayout());
									((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
									((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- radioGeneral ----
									radioGeneral.setText("General Time Range");
									radioGeneral.setSelected(true);
									panel9.add(radioGeneral, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- radioSplitted ----
									radioSplitted.setText("Splitted Time Range");
									panel9.add(radioSplitted, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel5.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== generalTimeRangePanel ========
								{
									generalTimeRangePanel.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
									generalTimeRangePanel.setLayout(new GridBagLayout());
									((GridBagLayout)generalTimeRangePanel.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)generalTimeRangePanel.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)generalTimeRangePanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
									((GridBagLayout)generalTimeRangePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- label7 ----
									label7.setText("General Default Time Range: ");
									generalTimeRangePanel.add(label7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//======== panel7 ========
									{
										panel7.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
										panel7.setLayout(new GridBagLayout());
										((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
										((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
										((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- generalHour ----
										generalHour.setMinimumSize(new Dimension(20, 20));
										generalHour.setPreferredSize(new Dimension(20, 20));
										generalHour.setModel(new SpinnerNumberModel(0, 0, null, 1));
										panel7.add(generalHour, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label8 ----
										label8.setText("h:  ");
										panel7.add(label8, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- generalMinute ----
										generalMinute.setMinimumSize(new Dimension(20, 20));
										generalMinute.setPreferredSize(new Dimension(20, 20));
										generalMinute.setModel(new SpinnerNumberModel(0, 0, 59, 5));
										panel7.add(generalMinute, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label9 ----
										label9.setText("min:   ");
										panel7.add(label9, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- generalSecond ----
										generalSecond.setMinimumSize(new Dimension(20, 20));
										generalSecond.setPreferredSize(new Dimension(20, 20));
										generalSecond.setModel(new SpinnerNumberModel(0, 0, 59, 5));
										panel7.add(generalSecond, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//---- label10 ----
										label10.setText("seg ");
										panel7.add(label10, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));
									}
									generalTimeRangePanel.add(panel7, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel5.add(generalTimeRangePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//======== SplittedTimeRangePanel ========
								{
									SplittedTimeRangePanel.setLayout(new GridBagLayout());
									((GridBagLayout)SplittedTimeRangePanel.getLayout()).columnWidths = new int[] {0, 0};
									((GridBagLayout)SplittedTimeRangePanel.getLayout()).rowHeights = new int[] {0, 0, 0};
									((GridBagLayout)SplittedTimeRangePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
									((GridBagLayout)SplittedTimeRangePanel.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

									//======== NumericRangePane ========
									{
										NumericRangePane.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
										NumericRangePane.setLayout(new GridBagLayout());
										((GridBagLayout)NumericRangePane.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)NumericRangePane.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)NumericRangePane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
										((GridBagLayout)NumericRangePane.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- label20 ----
										label20.setText("Numeric Default Time Range:   ");
										NumericRangePane.add(label20, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//======== panel10 ========
										{
											panel10.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
											panel10.setLayout(new GridBagLayout());
											((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
											((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0};
											((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
											((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

											//---- numericHour ----
											numericHour.setMinimumSize(new Dimension(20, 20));
											numericHour.setPreferredSize(new Dimension(20, 20));
											numericHour.setModel(new SpinnerNumberModel(0, 0, null, 1));
											panel10.add(numericHour, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- label21 ----
											label21.setText("h:  ");
											panel10.add(label21, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- numericMinute ----
											numericMinute.setMinimumSize(new Dimension(20, 20));
											numericMinute.setPreferredSize(new Dimension(20, 20));
											numericMinute.setModel(new SpinnerNumberModel(0, 0, 59, 5));
											panel10.add(numericMinute, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- label22 ----
											label22.setText("min:   ");
											panel10.add(label22, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- numericSecond ----
											numericSecond.setMinimumSize(new Dimension(20, 20));
											numericSecond.setPreferredSize(new Dimension(20, 20));
											numericSecond.setModel(new SpinnerNumberModel(0, 0, 59, 5));
											panel10.add(numericSecond, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- label23 ----
											label23.setText("seg ");
											panel10.add(label23, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));
										}
										NumericRangePane.add(panel10, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									SplittedTimeRangePanel.add(NumericRangePane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));

									//======== categoryRangePane ========
									{
										categoryRangePane.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
										categoryRangePane.setLayout(new GridBagLayout());
										((GridBagLayout)categoryRangePane.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)categoryRangePane.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)categoryRangePane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
										((GridBagLayout)categoryRangePane.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- label24 ----
										label24.setText("Category Default Time Range: ");
										categoryRangePane.add(label24, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 5), 0, 0));

										//======== panel11 ========
										{
											panel11.setToolTipText("Changing this value, the system will load and mantain registers of all variables during the given time range.");
											panel11.setLayout(new GridBagLayout());
											((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
											((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
											((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
											((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

											//---- categoryHour ----
											categoryHour.setMinimumSize(new Dimension(20, 20));
											categoryHour.setPreferredSize(new Dimension(20, 20));
											categoryHour.setModel(new SpinnerNumberModel(0, 0, null, 1));
											panel11.add(categoryHour, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- label25 ----
											label25.setText("h:  ");
											panel11.add(label25, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- categoryMinute ----
											categoryMinute.setMinimumSize(new Dimension(20, 20));
											categoryMinute.setPreferredSize(new Dimension(20, 20));
											categoryMinute.setModel(new SpinnerNumberModel(0, 0, 59, 5));
											panel11.add(categoryMinute, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- label26 ----
											label26.setText("min:   ");
											panel11.add(label26, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- categorySecond ----
											categorySecond.setMinimumSize(new Dimension(20, 20));
											categorySecond.setPreferredSize(new Dimension(20, 20));
											categorySecond.setModel(new SpinnerNumberModel(0, 0, 59, 5));
											panel11.add(categorySecond, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));

											//---- label27 ----
											label27.setText("seg ");
											panel11.add(label27, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
												GridBagConstraints.CENTER, GridBagConstraints.BOTH,
												new Insets(0, 0, 0, 5), 0, 0));
										}
										categoryRangePane.add(panel11, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									SplittedTimeRangePanel.add(categoryRangePane, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel5.add(SplittedTimeRangePanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- resetVariablesBox ----
								resetVariablesBox.setText("Reset Variables Time Range");
								resetVariablesBox.setToolTipText("Set the Time Range of all variables loaded in the given Time Range.");
								resetVariablesBox.setActionCommand("Reset Variables Time Range");
								resetVariablesBox.setSelected(true);
								panel5.add(resetVariablesBox, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- resetRangeBox ----
								resetRangeBox.setText("Reset Panels Time Range");
								resetRangeBox.setToolTipText("Set the Time Range of all monitoring Panels the given Time Range.");
								panel5.add(resetRangeBox, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel3.add(panel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel1 ========
							{
								panel1.setBorder(new TitledBorder("Database Info"));
								panel1.setLayout(new GridBagLayout());
								((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

								//======== panel2 ========
								{
									panel2.setLayout(new GridBagLayout());
									((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
									((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
									((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

									//---- databaseInfoButton ----
									databaseInfoButton.setText("Database Info");
									databaseInfoButton.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/downloadIcon.png")));
									panel2.add(databaseInfoButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));
								}
								panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- autoSaveBox ----
								autoSaveBox.setText("Autosave Monitored Variables");
								autoSaveBox.setSelected(true);
								panel1.add(autoSaveBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel3.add(panel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));

							//======== panel14 ========
							{
								panel14.setBorder(new TitledBorder("Overflow Control Info"));
								panel14.setToolTipText("We recommend not changing these values unless you have clear knowledge of its function. A wrong change could cause a serious failure in the process that saves the variable's values in the Database.");
								panel14.setLayout(new GridBagLayout());
								((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {0, 0};
								((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
								((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//======== panel15 ========
								{
									panel15.setLayout(new GridBagLayout());
									((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {0, 0, 0};
									((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
									((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
									((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

									//---- label1 ----
									label1.setText("Begin Of Overload Size: ");
									panel15.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- beginField ----
									beginField.setModel(new SpinnerNumberModel(0, 0, null, 0));
									panel15.add(beginField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label2 ----
									label2.setText("Memory Size SetPoint: ");
									panel15.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- memSetPointField ----
									memSetPointField.setModel(new SpinnerNumberModel(0, 0, null, 0));
									panel15.add(memSetPointField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label3 ----
									label3.setText("Sampling Time(milliseconds): ");
									panel15.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//======== panel12 ========
									{
										panel12.setLayout(new GridBagLayout());
										((GridBagLayout)panel12.getLayout()).columnWidths = new int[] {0, 0, 0};
										((GridBagLayout)panel12.getLayout()).rowHeights = new int[] {0, 0};
										((GridBagLayout)panel12.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
										((GridBagLayout)panel12.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

										//---- samplingField ----
										samplingField.setModel(new SpinnerNumberModel(200L, 1L, null, 100L));
										panel12.add(samplingField, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));

										//---- calculateButton ----
										calculateButton.setText("Calculate");
										panel12.add(calculateButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
											GridBagConstraints.CENTER, GridBagConstraints.BOTH,
											new Insets(0, 0, 0, 0), 0, 0));
									}
									panel15.add(panel12, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label4 ----
									label4.setText("Kc: ");
									panel15.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 5), 0, 0));

									//---- kcField ----
									kcField.setModel(new SpinnerNumberModel(0.0, null, null, 0.5));
									panel15.add(kcField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 5, 0), 0, 0));

									//---- label12 ----
									label12.setText("Z0:");
									panel15.add(label12, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 5), 0, 0));

									//---- z0Field ----
									z0Field.setModel(new SpinnerNumberModel(0.0, null, null, 0.5));
									panel15.add(z0Field, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								}
								panel14.add(panel15, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel3.add(panel14, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 0), 0, 0));
						}
						scrollPane1.setViewportView(panel3);
					}
					panel13.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== buttonBar ========
					{
						buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
						buttonBar.setLayout(new GridBagLayout());
						((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
						((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

						//---- okButton ----
						okButton.setText("OK");
						buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- cancelButton ----
						cancelButton.setText("Cancel");
						buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel13.add(buttonBar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel13, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(505, 645);
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioGeneral);
		buttonGroup1.add(radioSplitted);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel13;
	private JScrollPane scrollPane1;
	private JPanel panel3;
	private JPanel panel4;
	protected JCheckBox monitorAll;
	private JPanel panel5;
	private JPanel panel6;
	private JLabel label5;
	protected JTextField timeRegister;
	private JLabel label6;
	private JPanel panel8;
	protected JTextField loopTime;
	private JLabel label11;
	private JPanel panel9;
	protected JRadioButton radioGeneral;
	protected JRadioButton radioSplitted;
	protected JPanel generalTimeRangePanel;
	private JLabel label7;
	private JPanel panel7;
	protected JSpinner generalHour;
	private JLabel label8;
	protected JSpinner generalMinute;
	private JLabel label9;
	protected JSpinner generalSecond;
	private JLabel label10;
	protected JPanel SplittedTimeRangePanel;
	protected JPanel NumericRangePane;
	private JLabel label20;
	private JPanel panel10;
	protected JSpinner numericHour;
	private JLabel label21;
	protected JSpinner numericMinute;
	private JLabel label22;
	protected JSpinner numericSecond;
	private JLabel label23;
	protected JPanel categoryRangePane;
	private JLabel label24;
	private JPanel panel11;
	protected JSpinner categoryHour;
	private JLabel label25;
	protected JSpinner categoryMinute;
	private JLabel label26;
	protected JSpinner categorySecond;
	private JLabel label27;
	protected JCheckBox resetVariablesBox;
	protected JCheckBox resetRangeBox;
	private JPanel panel1;
	private JPanel panel2;
	protected JButton databaseInfoButton;
	protected JCheckBox autoSaveBox;
	private JPanel panel14;
	private JPanel panel15;
	private JLabel label1;
	protected JSpinner beginField;
	private JLabel label2;
	protected JSpinner memSetPointField;
	private JLabel label3;
	private JPanel panel12;
	protected JSpinner samplingField;
	protected JButton calculateButton;
	private JLabel label4;
	protected JSpinner kcField;
	private JLabel label12;
	protected JSpinner z0Field;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
