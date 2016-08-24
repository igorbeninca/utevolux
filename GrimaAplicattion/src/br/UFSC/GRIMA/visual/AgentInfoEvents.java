package br.UFSC.GRIMA.visual;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

import br.UFSC.GRIMA.IO.Agent;

public class AgentInfoEvents extends AgentInfoWindow implements ActionListener{
	private MainInterface mainInterface;
//////////////////////////////////////constructor////////////////////////////////////
	public AgentInfoEvents(MainInterface mainInterface) {
		super(mainInterface);
		setMainInterface(mainInterface);
		ArrayList<Agent>agents = mainInterface.getMainExecution().getAllAgents();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				mainInterface.setAgentInfoEvents(null);
				for(int i = 0; i < agents.size(); i++) {
					agents.get(i).setStatusLabel(null);
				}
		    }
		});
		
		okButton.addActionListener(this);
		for(int i = 0; i < agents.size(); i++) {
			JLabel name = new JLabel(agents.get(i).getAgentName());
			JLabel ip = new JLabel(agents.get(i).getAgentIP());
			JLabel buffer = new JLabel(agents.get(i).getBufferSize() + "");
			JLabel dev_cam = new JLabel(agents.get(i).getDevices().size() + "-" + agents.get(i).getCameras().size());
			JLabel status = new JLabel();
			String text = "";
			if(agents.get(i).getStatus()== Agent.ONLINE)
				text = "<html><font color=\"green\">Online</font></html>";
			else if(agents.get(i).getStatus()== Agent.OFFLINE)
				text = "<html><font color=\"red\">Offline</font></html>";
			else if(agents.get(i).getStatus()== Agent.SLOW)
				text = "<html><font color=\"yellow\">Slow</font></html>";
			status.setText(text);
			agents.get(i).setStatusLabel(status);
			workspace.add(name, new GridBagConstraints(0, i+1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			workspace.add(ip, new GridBagConstraints(1, i+1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			workspace.add(buffer, new GridBagConstraints(2, i+1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			workspace.add(dev_cam, new GridBagConstraints(3, i+1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			workspace.add(status, new GridBagConstraints(4, i+1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
		}
		this.setVisible(true);
	}
/////////////////////////////////////methods///////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(okButton)) {
			this.dispose();
		}
	}
////////////////////////////////////getters and Setters///////////////////////////////////////
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}

}
