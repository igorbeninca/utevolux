/*
 * Created by JFormDesigner on Wed Aug 24 13:35:59 BRT 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class AgentInfoWindow extends JDialog {
	public AgentInfoWindow(Frame owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		workspace = new JPanel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label5 = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		setTitle("Agent Info");
		setModal(true);
		setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
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
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

				//======== panel1 ========
				{
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

					//---- label1 ----
					label1.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/logofinal.png")));
					label1.setHorizontalAlignment(SwingConstants.CENTER);
					panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== scrollPane1 ========
				{

					//======== workspace ========
					{
						workspace.setBorder(new TitledBorder(""));
						workspace.setLayout(new GridBagLayout());
						((GridBagLayout)workspace.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
						((GridBagLayout)workspace.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)workspace.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0E-4};
						((GridBagLayout)workspace.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

						//---- label2 ----
						label2.setText("Agent Name");
						workspace.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- label3 ----
						label3.setText("Agent Ip");
						workspace.add(label3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- label4 ----
						label4.setText("Buffer Size");
						workspace.add(label4, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- label6 ----
						label6.setText("Devices-Cameras");
						workspace.add(label6, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- label7 ----
						label7.setText("Status");
						workspace.add(label7, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- label5 ----
						label5.setText("Ping");
						workspace.add(label5, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					scrollPane1.setViewportView(workspace);
				}
				contentPanel.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(595, 370);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	private JLabel label1;
	private JScrollPane scrollPane1;
	protected JPanel workspace;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label6;
	private JLabel label7;
	private JLabel label5;
	private JPanel buttonBar;
	protected JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
