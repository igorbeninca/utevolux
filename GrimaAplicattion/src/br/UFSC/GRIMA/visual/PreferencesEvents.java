package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import br.UFSC.GRIMA.IO.BufControl;
import br.UFSC.GRIMA.IO.MainExecution;
import br.UFSC.GRIMA.dataStructure.Device;

public class PreferencesEvents extends PreferencesWindow implements ActionListener {
	private MainInterface mainInterface;
//////////////////////////////Constructor///////////////////////////////////////////////////
	public PreferencesEvents(MainInterface mainInterface) {
		this.setMainInterface(mainInterface);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
				mainInterface.setPreferenceEvents(null);
				for (int i = 0; i < devices.size(); i++) {
					devices.get(i).setChangeNameButton(null);
					devices.get(i).setNameTextField(null);
				}
				if (mainInterface.deviceInfoButton.isSelected()) {
					mainInterface.destroyViewDevicesPanel();
					mainInterface.setViewDevicesPanel();
					mainInterface.deviceInfoButton.setSelected(true);
				}
				else if (mainInterface.deviceMonitoringButton.isSelected()) {
					mainInterface.destroyDeviceMonitoringPanel();
					mainInterface.setDeviceMonitoringPanel();
					mainInterface.deviceMonitoringButton.setSelected(true);
				}
				else if (mainInterface.panelMonitoringButton.isSelected()) {
					mainInterface.destroyPanelMonitoringLayout();
					mainInterface.setPanelMonitoringLayout();
					mainInterface.panelMonitoringButton.setSelected(true);
				}
			}
		});
		timeRegister.setText(mainInterface.currentTimeField.getText());
		loopTime.setText(mainInterface.loadExPing.getText());
		monitorAll.setSelected(mainInterface.getMainExecution().isMonitorAllVariables());
		autoSaveBox.setSelected(mainInterface.getMainExecution().isAutoSaveVariables());
		if(mainInterface.getMainExecution().getDefaultTimeOption() == mainInterface.getMainExecution().GENERAL) {
			generalTimeRangePanel.setVisible(true);
			SplittedTimeRangePanel.setVisible(false);
		}
		else {
			SplittedTimeRangePanel.setVisible(true);
			generalTimeRangePanel.setVisible(false);
		}
		radioGeneral.addActionListener(this);
		radioSplitted.addActionListener(this);
		//set Default Time ranges
		generalHour.setValue(mainInterface.getMainExecution().getDefaultTimeRange()[0]);
		generalMinute.setValue(mainInterface.getMainExecution().getDefaultTimeRange()[1]);
		generalSecond.setValue(mainInterface.getMainExecution().getDefaultTimeRange()[2]);
		numericHour.setValue(mainInterface.getMainExecution().getNumericDefaultTimeRange()[0]);
		numericMinute.setValue(mainInterface.getMainExecution().getNumericDefaultTimeRange()[1]);
		numericSecond.setValue(mainInterface.getMainExecution().getNumericDefaultTimeRange()[2]);
		categoryHour.setValue(mainInterface.getMainExecution().getCategoryDefaultTimeRange()[0]);
		categoryMinute.setValue(mainInterface.getMainExecution().getCategoryDefaultTimeRange()[1]);
		categorySecond.setValue(mainInterface.getMainExecution().getCategoryDefaultTimeRange()[2]);
		BufControl bufControl = mainInterface.getMainExecution().getIoControl().getSaveExecution().getBufControl();
		beginField.setValue(bufControl.getMinFaixa());
		memSetPointField.setValue(bufControl.getMemSetPoint());
		kcField.setValue(bufControl.getKc());
		samplingField.setValue(bufControl.getTd());
		z0Field.setValue(bufControl.getZ0());
		agentSlowField.setValue(mainInterface.getMainExecution().getIoControl().getLoadExecution().getAgentSlowLimit());
		dataBaseSlowField.setValue(mainInterface.getMainExecution().getIoControl().getSaveExecution().getDatabaseSlowLimit());
		calculateButton.addActionListener(this);
		databaseInfoButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
//////////////////////////////Methods///////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(mainInterface.currentTimeField)) {
			timeRegister.setText(mainInterface.currentTimeField.getText());
		}
		else if(e.getSource().equals(mainInterface.loadExPing)) {
			loopTime.setText(mainInterface.loadExPing.getText());
		}
		else if(e.getSource().equals(radioGeneral)||e.getSource().equals(radioSplitted)) {
			if(radioGeneral.isSelected()) {
				generalTimeRangePanel.setVisible(true);
				SplittedTimeRangePanel.setVisible(false);
			}
			else {
				SplittedTimeRangePanel.setVisible(true);
				generalTimeRangePanel.setVisible(false);
			}
		}
		else if(e.getSource().equals(databaseInfoButton)) {
			new DataBaseInfoEvents(mainInterface);
		}
		else if(e.getSource().equals(calculateButton)) {
			z0Field.setValue(0.893);
			kcField.setValue(0.5*(-1)*1000/((long)samplingField.getValue())*mainInterface.getMainExecution().getIoControl().getSaveExecution().getBufControl().T5Est);
		}
		else if(e.getSource().equals(cancelButton)) {
			this.dispose();
		}
		else if(e.getSource().equals(okButton)) {
			MainExecution mm = mainInterface.getMainExecution();
			mm.setAutoSaveVariables(autoSaveBox.isSelected());
			if(monitorAll.isSelected())
				mm.setAllVariablesMonitored();
			else 
				mm.setMonitorAllVariables(false);
			if(radioGeneral.isSelected()) {
				mm.setDefaultTimeOption(mm.GENERAL);
				int[] range = {(int)generalHour.getValue(),(int)generalMinute.getValue(),(int)generalSecond.getValue()};
				mm.setDefaultTimeRange(range);
			}
			else {
				mm.setDefaultTimeOption(mm.SPLITTED);
				int[] range = {(int)categoryHour.getValue(),(int)categoryMinute.getValue(),(int)categorySecond.getValue()};
				mm.setCategoryDefaultTimeRange(range);
				int[] range2 = {(int)numericHour.getValue(),(int)numericMinute.getValue(),(int)numericSecond.getValue()};
				mm.setNumericDefaultTimeRange(range2);
			}
			if(resetVariablesBox.isSelected())
				mainInterface.getMainExecution().resetVariablesRange();
			if(resetRangeBox.isSelected())
				mainInterface.getMainExecution().resetPanelsRange();
			BufControl bufControl = mainInterface.getMainExecution().getIoControl().getSaveExecution().getBufControl();
			bufControl.setMinFaixa((int)beginField.getValue());
			bufControl.setMemSetPoint((int)memSetPointField.getValue());
			bufControl.setKc((double)kcField.getValue());
			bufControl.setZ0((double)z0Field.getValue());
			bufControl.setTd((long)samplingField.getValue());
			mainInterface.getMainExecution().getIoControl().getLoadExecution().setAgentSlowLimit((long)agentSlowField.getValue());
			mainInterface.getMainExecution().getIoControl().getSaveExecution().setDatabaseSlowLimit((long)dataBaseSlowField.getValue());
			this.dispose();
		}
	}
////////////////////////////////Getters and Setters///////////////////////////////////////////
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}

}
