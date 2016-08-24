/*
 * Created by JFormDesigner on Mon Aug 22 22:35:13 BRT 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class dataBaseInfoWindow extends JDialog {
	public dataBaseInfoWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		label6 = new JLabel();
		label1 = new JLabel();
		contentPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		panel1 = new JPanel();
		panel10 = new JPanel();
		panel11 = new JPanel();
		label7 = new JLabel();
		userPHP = new JTextField();
		panel2 = new JPanel();
		panel6 = new JPanel();
		label2 = new JLabel();
		userID = new JTextField();
		panel3 = new JPanel();
		panel7 = new JPanel();
		label3 = new JLabel();
		dataIP = new JTextField();
		panel4 = new JPanel();
		panel8 = new JPanel();
		label4 = new JLabel();
		database = new JTextField();
		panel5 = new JPanel();
		panel9 = new JPanel();
		label5 = new JLabel();
		dataUser = new JTextField();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		setTitle("DataBase Information");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new GridBagLayout());
			((GridBagLayout)dialogPane.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)dialogPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)dialogPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)dialogPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};

			//---- label6 ----
			label6.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/logofinal.png")));
			dialogPane.add(label6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
			dialogPane.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

				//======== scrollPane1 ========
				{

					//======== panel1 ========
					{
						panel1.setBorder(new TitledBorder(""));
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};

						//======== panel10 ========
						{
							panel10.setLayout(new GridBagLayout());
							((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panel11 ========
							{
								panel11.setLayout(new GridBagLayout());
								((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {100, 0};
								((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//---- label7 ----
								label7.setText("User: ");
								label7.setHorizontalAlignment(SwingConstants.LEFT);
								label7.setFocusTraversalPolicyProvider(true);
								label7.setHorizontalTextPosition(SwingConstants.CENTER);
								panel11.add(label7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel10.add(panel11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- userPHP ----
							userPHP.setEditable(false);
							panel10.add(userPHP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel10, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel2 ========
						{
							panel2.setLayout(new GridBagLayout());
							((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panel6 ========
							{
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {100, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//---- label2 ----
								label2.setText("User Id: ");
								label2.setHorizontalAlignment(SwingConstants.LEFT);
								label2.setFocusTraversalPolicyProvider(true);
								label2.setHorizontalTextPosition(SwingConstants.CENTER);
								panel6.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel2.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- userID ----
							userID.setEditable(false);
							panel2.add(userID, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel3 ========
						{
							panel3.setLayout(new GridBagLayout());
							((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panel7 ========
							{
								panel7.setLayout(new GridBagLayout());
								((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {100, 0};
								((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//---- label3 ----
								label3.setText("Database IP: ");
								label3.setHorizontalAlignment(SwingConstants.LEFT);
								panel7.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel3.add(panel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- dataIP ----
							dataIP.setEditable(false);
							panel3.add(dataIP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel4 ========
						{
							panel4.setLayout(new GridBagLayout());
							((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panel8 ========
							{
								panel8.setLayout(new GridBagLayout());
								((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {100, 0};
								((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//---- label4 ----
								label4.setText("Database: ");
								label4.setHorizontalAlignment(SwingConstants.LEFT);
								panel8.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel4.add(panel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- database ----
							database.setEditable(false);
							panel4.add(database, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panel9 ========
							{
								panel9.setLayout(new GridBagLayout());
								((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {100, 0};
								((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
								((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

								//---- label5 ----
								label5.setText("Database User: ");
								label5.setHorizontalAlignment(SwingConstants.LEFT);
								panel9.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel5.add(panel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- dataUser ----
							dataUser.setEditable(false);
							panel5.add(dataUser, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					scrollPane1.setViewportView(panel1);
				}
				contentPanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				okButton.setActionCommand("ok");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(490, 370);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JLabel label6;
	protected JLabel label1;
	private JPanel contentPanel;
	private JScrollPane scrollPane1;
	private JPanel panel1;
	private JPanel panel10;
	private JPanel panel11;
	private JLabel label7;
	protected JTextField userPHP;
	private JPanel panel2;
	private JPanel panel6;
	private JLabel label2;
	protected JTextField userID;
	private JPanel panel3;
	private JPanel panel7;
	private JLabel label3;
	protected JTextField dataIP;
	private JPanel panel4;
	private JPanel panel8;
	private JLabel label4;
	protected JTextField database;
	private JPanel panel5;
	private JPanel panel9;
	private JLabel label5;
	protected JTextField dataUser;
	private JPanel buttonBar;
	protected JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
