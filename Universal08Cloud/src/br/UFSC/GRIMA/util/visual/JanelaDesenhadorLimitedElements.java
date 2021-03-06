package br.UFSC.GRIMA.util.visual;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class JanelaDesenhadorLimitedElements extends Frame {
	public JanelaDesenhadorLimitedElements() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel2 = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		spinnerZoom = new JSpinner();
		checkBox1 = new JCheckBox();
		checkBox2 = new JCheckBox();
		label2 = new JLabel();
		spinnerDecimais = new JSpinner();
		scrollPane1 = new JScrollPane();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Desenhador de Elementos");
		setIconImage(new ImageIcon(getClass().getResource("/images/Acceptchanges.png")).getImage());
		setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridBagLayout());
				((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
				((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

				//======== panel2 ========
				{
					panel2.setLayout(new GridBagLayout());
					((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
					((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

					//======== panel1 ========
					{
						panel1.setLayout(new GridBagLayout());
						((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 65, 0, 0, 0, 0, 0, 0, 40, 0};
						((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label1 ----
						label1.setText("zoom");
						panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- spinnerZoom ----
						spinnerZoom.setModel(new SpinnerNumberModel(100.0, 0.0, 2000.0, 100.0));
						panel1.add(spinnerZoom, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- checkBox1 ----
						checkBox1.setText("show coordinates");
						panel1.add(checkBox1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- checkBox2 ----
						checkBox2.setText("show intersections");
						panel1.add(checkBox2, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- label2 ----
						label2.setText("set N decimals");
						panel1.add(label2, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- spinnerDecimais ----
						spinnerDecimais.setModel(new SpinnerNumberModel(0, 0, 16, 1));
						panel1.add(spinnerDecimais, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					panel2.add(panel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
				}
				contentPanel.add(panel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));
				contentPanel.add(scrollPane1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

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
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel2;
	private JPanel panel1;
	private JLabel label1;
	public JSpinner spinnerZoom;
	public JCheckBox checkBox1;
	public JCheckBox checkBox2;
	private JLabel label2;
	public JSpinner spinnerDecimais;
	public JScrollPane scrollPane1;
	private JPanel buttonBar;
	public JButton okButton;
	public JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
