/*
 * Created by JFormDesigner on Fri Nov 04 13:00:03 BRST 2016
 */

package br.UFSC.GRIMA.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jc
 */
public class LoginWindow extends JFrame {
	public LoginWindow() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		dialogPane2 = new JPanel();
		contentPanel = new JPanel();
		label4 = new JLabel();
		panel1 = new JPanel();
		label1 = new JLabel();
		userField = new JTextField();
		label3 = new JLabel();
		pass = new JPasswordField();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Login:");
		setIconImage(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/iconeLogo.png")).getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== dialogPane2 ========
			{
				dialogPane2.setBorder(new EmptyBorder(12, 12, 12, 12));
				dialogPane2.setLayout(new BorderLayout());

				//======== contentPanel ========
				{
					contentPanel.setBorder(new EtchedBorder());
					contentPanel.setLayout(new GridBagLayout());
					((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {100, 0};
					((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

					//---- label4 ----
					label4.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/logofinal.png")));
					contentPanel.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== panel1 ========
					{
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {45, 0, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0, 0.0, 1.0, 1.0E-4};

						//---- label1 ----
						label1.setText("Username");
						label1.setFont(new Font("Verdana", Font.PLAIN, 12));
						label1.setHorizontalAlignment(SwingConstants.RIGHT);
						panel1.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- userField ----
						userField.setFont(new Font("Verdana", Font.PLAIN, 12));
						userField.setEditable(true);
						userField.setText("Enter your user name");
						userField.setForeground(new Color(204, 204, 204));
						panel1.add(userField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- label3 ----
						label3.setText("Password:");
						label3.setFont(new Font("Verdana", Font.PLAIN, 12));
						panel1.add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
						panel1.add(pass, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					contentPanel.add(panel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				dialogPane2.add(contentPanel, BorderLayout.CENTER);

				//======== buttonBar ========
				{
					buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
					buttonBar.setLayout(new GridBagLayout());
					((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
					((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

					//---- okButton ----
					okButton.setText("OK");
					okButton.setFont(new Font("Verdana", Font.PLAIN, 12));
					buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//---- cancelButton ----
					cancelButton.setText("Cancel");
					cancelButton.setFont(new Font("Verdana", Font.PLAIN, 12));
					buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				dialogPane2.add(buttonBar, BorderLayout.SOUTH);
			}
			dialogPane.add(dialogPane2, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(525, 360);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel dialogPane2;
	private JPanel contentPanel;
	private JLabel label4;
	private JPanel panel1;
	private JLabel label1;
	protected JTextField userField;
	private JLabel label3;
	protected JPasswordField pass;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
