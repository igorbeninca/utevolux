/*
 * Created by JFormDesigner on Wed Jan 27 13:34:15 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class MainWindow extends JFrame {
	public MainWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuPrint = new JMenuItem();
		menuExit = new JMenuItem();
		menu2 = new JMenu();
		menuPreferences = new JMenuItem();
		menuDeviceConfigure = new JMenuItem();
		menuDatabase = new JMenuItem();
		menu3 = new JMenu();
		menuAgent = new JMenuItem();
		menuAddAgent = new JMenuItem();
		menu4 = new JMenu();
		menuDeviceInfo = new JMenuItem();
		menuDevices = new JMenu();
		menu6 = new JMenu();
		menuCameraInfo = new JMenuItem();
		menuAddCamera = new JMenuItem();
		menuView = new JMenu();
		menu8 = new JMenu();
		menuPanels = new JMenu();
		menuViewPanels = new JMenuItem();
		menuAddPanel = new JMenuItem();
		menu5 = new JMenu();
		menuAbout = new JMenuItem();
		panel7 = new JPanel();
		panel2 = new JPanel();
		panel4 = new JPanel();
		panel11 = new JPanel();
		label5 = new JLabel();
		panel1 = new JPanel();
		panel3 = new JPanel();
		panel9 = new JPanel();
		label2 = new JLabel();
		agentStatusField = new JLabel();
		label1 = new JLabel();
		loadExPing = new JTextField();
		label4 = new JLabel();
		currentTimeField = new JTextField();
		panel10 = new JPanel();
		label3 = new JLabel();
		dataBaseStatusField = new JLabel();
		label6 = new JLabel();
		saveExPing = new JTextField();
		panel5 = new JPanel();
		deviceInfoButton = new JToggleButton();
		deviceMonitoringButton = new JToggleButton();
		panelMonitoringButton = new JToggleButton();
		splitPane1 = new JSplitPane();
		scrollPane2 = new JScrollPane();
		workSpace = new JPanel();
		scrollPane1 = new JScrollPane();
		historyTextPane = new JTextPane();

		//======== this ========
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		setTitle("Client Application");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");
				menu1.setFont(new Font("Verdana", Font.PLAIN, 12));

				//---- menuPrint ----
				menuPrint.setText("Print History");
				menu1.add(menuPrint);

				//---- menuExit ----
				menuExit.setText("exit");
				menuExit.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/process-stop.png")));
				menuExit.setFont(new Font("Verdana", Font.PLAIN, 12));
				menu1.add(menuExit);
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("Configure");
				menu2.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuPreferences ----
				menuPreferences.setText("Preferences");
				menuPreferences.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
				menuPreferences.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu2.add(menuPreferences);

				//---- menuDeviceConfigure ----
				menuDeviceConfigure.setText("Configure Imput Data");
				menuDeviceConfigure.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/machineIcon.png")));
				menuDeviceConfigure.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu2.add(menuDeviceConfigure);

				//---- menuDatabase ----
				menuDatabase.setText("Database Info");
				menuDatabase.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/downloadIcon.png")));
				menuDatabase.setFont(new Font("Dialog", Font.PLAIN, 12));
				menuDatabase.setActionCommand("Database Info");
				menu2.add(menuDatabase);
			}
			menuBar1.add(menu2);

			//======== menu3 ========
			{
				menu3.setText("Agent");
				menu3.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuAgent ----
				menuAgent.setText("Agent Info");
				menuAgent.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/agentIcon.png")));
				menu3.add(menuAgent);

				//---- menuAddAgent ----
				menuAddAgent.setText("Add Agent");
				menuAddAgent.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/plusIcon.png")));
				menuAddAgent.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu3.add(menuAddAgent);
			}
			menuBar1.add(menu3);

			//======== menu4 ========
			{
				menu4.setText("Device");
				menu4.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuDeviceInfo ----
				menuDeviceInfo.setText("Devices info");
				menuDeviceInfo.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/machineIcon.png")));
				menuDeviceInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu4.add(menuDeviceInfo);

				//======== menuDevices ========
				{
					menuDevices.setText("Monitor");
					menuDevices.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/viewIcon.png")));
					menuDevices.setFont(new Font("Dialog", Font.PLAIN, 12));
				}
				menu4.add(menuDevices);
			}
			menuBar1.add(menu4);

			//======== menu6 ========
			{
				menu6.setText("Webcam");
				menu6.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuCameraInfo ----
				menuCameraInfo.setText("Camera Info");
				menuCameraInfo.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/cameraIcon.png")));
				menuCameraInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu6.add(menuCameraInfo);

				//---- menuAddCamera ----
				menuAddCamera.setText("Add Camera");
				menuAddCamera.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/plusIcon.png")));
				menuAddCamera.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu6.add(menuAddCamera);

				//======== menuView ========
				{
					menuView.setText("View");
					menuView.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/viewIcon.png")));
					menuView.setFont(new Font("Dialog", Font.PLAIN, 12));
				}
				menu6.add(menuView);
			}
			menuBar1.add(menu6);

			//======== menu8 ========
			{
				menu8.setText("Panels");
				menu8.setFont(new Font("Dialog", Font.PLAIN, 12));

				//======== menuPanels ========
				{
					menuPanels.setText("Configure");
					menuPanels.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/Gears-icon.png")));
					menuPanels.setFont(new Font("Dialog", Font.PLAIN, 12));
				}
				menu8.add(menuPanels);

				//---- menuViewPanels ----
				menuViewPanels.setText("View Panels");
				menuViewPanels.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/monitoringIcon.png")));
				menuViewPanels.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu8.add(menuViewPanels);

				//---- menuAddPanel ----
				menuAddPanel.setText("Add Panel");
				menuAddPanel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/plusIcon.png")));
				menuAddPanel.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu8.add(menuAddPanel);
			}
			menuBar1.add(menu8);

			//======== menu5 ========
			{
				menu5.setText("Help");
				menu5.setFont(new Font("Dialog", Font.PLAIN, 12));

				//---- menuAbout ----
				menuAbout.setText("About");
				menuAbout.setActionCommand("About");
				menuAbout.setFont(new Font("Dialog", Font.PLAIN, 12));
				menu5.add(menuAbout);
			}
			menuBar1.add(menu5);
		}
		setJMenuBar(menuBar1);

		//======== panel7 ========
		{
			panel7.setLayout(new GridBagLayout());
			((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

			//======== panel2 ========
			{
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//======== panel4 ========
				{
					panel4.setLayout(new GridBagLayout());
					((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== panel11 ========
					{
						panel11.setLayout(new GridBagLayout());
						((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
						((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

						//---- label5 ----
						label5.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/logofinal.png")));
						panel11.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel4.add(panel11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== panel1 ========
					{
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

						//======== panel3 ========
						{
							panel3.setBorder(new TitledBorder(""));
							panel3.setLayout(new GridBagLayout());
							((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
							((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
							((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//======== panel9 ========
							{
								panel9.setBorder(new TitledBorder(""));
								panel9.setLayout(new GridBagLayout());
								((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 1.0, 0.0, 1.0E-4};

								//---- label2 ----
								label2.setText("Agent Communication:");
								label2.setHorizontalAlignment(SwingConstants.RIGHT);
								panel9.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- agentStatusField ----
								agentStatusField.setBorder(new EtchedBorder(EtchedBorder.RAISED));
								panel9.add(agentStatusField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label1 ----
								label1.setText("Ping:");
								label1.setHorizontalAlignment(SwingConstants.RIGHT);
								panel9.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- loadExPing ----
								loadExPing.setEditable(false);
								panel9.add(loadExPing, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label4 ----
								label4.setText("Reference Time:");
								label4.setHorizontalAlignment(SwingConstants.RIGHT);
								panel9.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- currentTimeField ----
								currentTimeField.setEditable(false);
								panel9.add(currentTimeField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							panel3.add(panel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//======== panel10 ========
							{
								panel10.setBorder(new TitledBorder(""));
								panel10.setLayout(new GridBagLayout());
								((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0};
								((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
								((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
								((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

								//---- label3 ----
								label3.setText("DataBase Communication:");
								panel10.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- dataBaseStatusField ----
								dataBaseStatusField.setBorder(new EtchedBorder(EtchedBorder.RAISED));
								panel10.add(dataBaseStatusField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));

								//---- label6 ----
								label6.setText("Ping:");
								label6.setHorizontalAlignment(SwingConstants.RIGHT);
								panel10.add(label6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 5), 0, 0));

								//---- saveExPing ----
								saveExPing.setEditable(false);
								panel10.add(saveExPing, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 5, 0), 0, 0));
							}
							panel3.add(panel10, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== panel5 ========
						{
							panel5.setLayout(new GridBagLayout());
							((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
							((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
							((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

							//---- deviceInfoButton ----
							deviceInfoButton.setText("Device Information");
							panel5.add(deviceInfoButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- deviceMonitoringButton ----
							deviceMonitoringButton.setText("Device Monitoring");
							panel5.add(deviceMonitoringButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 5), 0, 0));

							//---- panelMonitoringButton ----
							panelMonitoringButton.setText("Panel Monitoring");
							panel5.add(panelMonitoringButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						panel1.add(panel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel4.add(panel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				panel2.add(panel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			panel7.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== splitPane1 ========
		{
			splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane1.setResizeWeight(0.8);
			splitPane1.setOneTouchExpandable(true);
			splitPane1.setBorder(new EtchedBorder());

			//======== scrollPane2 ========
			{

				//======== workSpace ========
				{
					workSpace.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					workSpace.setLayout(new GridBagLayout());
					((GridBagLayout)workSpace.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)workSpace.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)workSpace.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)workSpace.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
				}
				scrollPane2.setViewportView(workSpace);
			}
			splitPane1.setTopComponent(scrollPane2);

			//======== scrollPane1 ========
			{

				//---- historyTextPane ----
				historyTextPane.setEditable(false);
				historyTextPane.setFont(new Font("Verdana", Font.PLAIN, 12));
				scrollPane1.setViewportView(historyTextPane);
			}
			splitPane1.setBottomComponent(scrollPane1);
		}
		contentPane.add(splitPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		setSize(815, 455);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	protected JMenuItem menuPrint;
	protected JMenuItem menuExit;
	private JMenu menu2;
	protected JMenuItem menuPreferences;
	protected JMenuItem menuDeviceConfigure;
	protected JMenuItem menuDatabase;
	private JMenu menu3;
	protected JMenuItem menuAgent;
	protected JMenuItem menuAddAgent;
	private JMenu menu4;
	protected JMenuItem menuDeviceInfo;
	protected JMenu menuDevices;
	private JMenu menu6;
	protected JMenuItem menuCameraInfo;
	protected JMenuItem menuAddCamera;
	protected JMenu menuView;
	private JMenu menu8;
	protected JMenu menuPanels;
	protected JMenuItem menuViewPanels;
	protected JMenuItem menuAddPanel;
	private JMenu menu5;
	protected JMenuItem menuAbout;
	private JPanel panel7;
	private JPanel panel2;
	private JPanel panel4;
	private JPanel panel11;
	private JLabel label5;
	private JPanel panel1;
	private JPanel panel3;
	private JPanel panel9;
	private JLabel label2;
	protected JLabel agentStatusField;
	private JLabel label1;
	protected JTextField loadExPing;
	private JLabel label4;
	protected JTextField currentTimeField;
	private JPanel panel10;
	private JLabel label3;
	protected JLabel dataBaseStatusField;
	private JLabel label6;
	protected JTextField saveExPing;
	private JPanel panel5;
	protected JToggleButton deviceInfoButton;
	public JToggleButton deviceMonitoringButton;
	protected JToggleButton panelMonitoringButton;
	private JSplitPane splitPane1;
	private JScrollPane scrollPane2;
	protected JPanel workSpace;
	private JScrollPane scrollPane1;
	protected JTextPane historyTextPane;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
