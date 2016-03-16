package br.UFSC.GRIMA.visual;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.UFSC.GRIMA.dataStructure.Component;
import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.operational.CategoryMonitoringUnit;
import br.UFSC.GRIMA.operational.MonitoringUnit;
import br.UFSC.GRIMA.operational.NumericMonitoringUnit;
import br.UFSC.GRIMA.operational.ThreeDMonitoringUnit;
import br.UFSC.GRIMA.operational.TwoDMonitoringUnit;

public class ConfigurePanelEvents extends ConfigurePanelWindow implements ActionListener {
	private MainInterface mainInterface;
	private ArrayList<Variable> variables;
	private Device currentDevice;
	private Component currentComponent;
	private Variable currentVariable;
	private MonitoringUnit monitoringUnit;
	private Variable currentX;
	private Variable currentY;
	private Variable currentZ;
/////////////////constructor/////////////////////////////////////////////////////////////
	public ConfigurePanelEvents(MainInterface mainInterface, ArrayList<Variable> variables) {
		// TODO Auto-generated constructor stub
		setMainInterface(mainInterface);
		this.setTitle("Add New Panel ");
		if (variables != null) 
			setVariables(variables);
		else
			setVariables(new ArrayList<Variable>());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				windowClosing(windowEvent);
			}
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (variables != null) {
		    		for (int i = 0; i < variables.size(); i++) 
		    			variables.get(i).setCloseButton(null);
		    	}
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				if (mainInterface.deviceInfoButton.isSelected()) {
					mainInterface.destroyViewDevicesPanel();
					mainInterface.setViewDevicesPanel();
					mainInterface.deviceInfoButton.setSelected(true);
				}
				if (mainInterface.deviceMonitoringButton.isSelected()) {
					mainInterface.destroyDeviceMonitoringPanel();
					mainInterface.setDeviceMonitoringPanel();
					mainInterface.deviceMonitoringButton.setSelected(true);
				}
				if (mainInterface.panelMonitoringButton.isSelected()) {
					mainInterface.destroyPanelMonitoringLayout();
					mainInterface.setPanelMonitoringLayout();
					mainInterface.panelMonitoringButton.setSelected(true);
				}
			}
		});
		String defaultName = makeDefaultName("Panel");
		nameField.setText(defaultName);
		hourField.setValue(mainInterface.getMainExecution().getDefaultTimeRange()[0]);
		minuteField.setValue(mainInterface.getMainExecution().getDefaultTimeRange()[1]);
		secondField.setValue(mainInterface.getMainExecution().getDefaultTimeRange()[2]);
		whidthField.setValue(300);
		heightField.setValue(300);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		////////////////types of chart/////////////////////////////////
		model.addElement("LineChart");
		model.addElement("StepLineChart");
		model.addElement("AreaChart");
		model.addElement("2DLineChart");
		model.addElement("3DLineChart");
		chartTypeCombobox.setModel(model);
		setVariablesCombobox();
		configureWorkspace();
		optionPanel.setVisible(false);
		axisSelectPanel.setVisible(false);
		chartTypeCombobox.addActionListener(this);
		addVariableButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
	public ConfigurePanelEvents(MonitoringUnit monitoringUnit) {
		setMonitoringUnit(monitoringUnit);
		setMainInterface(monitoringUnit.getPanelMonitoringSystem().getController().getMainInterface());
		setVariables((ArrayList<Variable>)monitoringUnit.getVariables().clone());
		this.setTitle("Configure " + monitoringUnit.getName());
		nameField.setText(monitoringUnit.getName());
		hourField.setValue(monitoringUnit.getTimeRange()[0]);
		minuteField.setValue(monitoringUnit.getTimeRange()[1]);
		secondField.setValue(monitoringUnit.getTimeRange()[2]);
		whidthField.setValue(monitoringUnit.getMinimumWhidth());
		heightField.setValue(monitoringUnit.getMinimumHeight());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				windowClosed(windowEvent);
			}
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		    	MainInterface mainInterface = monitoringUnit.getPanelMonitoringSystem().getController().getMainInterface();
				mainInterface.setEnabled(true);
				mainInterface.toFront();
				mainInterface.setViewDevicesEvents(null);
				ArrayList<Device> devices = mainInterface.getMainExecution().getAllDevices();
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
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		////////////////types of chart/////////////////////////////////
		model.addElement("LineChart");
		model.addElement("StepLineChart");
		model.addElement("AreaChart");
		model.addElement("2DLineChart");
		model.addElement("3DLineChart");
		chartTypeCombobox.setModel(model);
		chartTypeCombobox.setSelectedItem(monitoringUnit.getChartType());
		setVariablesCombobox();
		configureWorkspace();
		if(monitoringUnit.getChartType().equals("2DLineChart")) {
			axisSelectPanel.setVisible(true);
			zAxisPanel.setVisible(false);
		}
		else if (monitoringUnit.getChartType().equals("3DLineChart")) {
			axisSelectPanel.setVisible(true);
			zAxisPanel.setVisible(true);
		}
		else
			axisSelectPanel.setVisible(false);
		if(monitoringUnit.getChartType().equals("2DLineChart")) {
			xAxisCombobox.setSelectedItem(((TwoDMonitoringUnit)monitoringUnit).getxSelected().getValidName());
			yAxisCombobox.setSelectedItem(((TwoDMonitoringUnit)monitoringUnit).getySelected().getValidName());
		}
		else if(monitoringUnit.getChartType().equals("3DLineChart")) {
			xAxisCombobox.setSelectedItem(((ThreeDMonitoringUnit)monitoringUnit).getxSelected().getValidName());
			yAxisCombobox.setSelectedItem(((ThreeDMonitoringUnit)monitoringUnit).getySelected().getValidName());
			zAxisCombobox.setSelectedItem(((ThreeDMonitoringUnit)monitoringUnit).getzSelected().getValidName());
		}
		chartTypeCombobox.addActionListener(this);
		addVariableButton.addActionListener(this);
		minimizeButton.addActionListener(this);
		maximizeButton.addActionListener(this);
		removePanelButton.addActionListener(this);
		clonePanelButton.addActionListener(this);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
///////////////////////////////////Methods///////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(chartTypeCombobox)) {
			if(chartTypeCombobox.getSelectedItem().equals("2DLineChart")) {
				axisSelectPanel.setVisible(true);
				zAxisPanel.setVisible(false);
			}
			else if(chartTypeCombobox.getSelectedItem().equals("3DLineChart")) {
				axisSelectPanel.setVisible(true);
				zAxisPanel.setVisible(true);
			}
			else
				axisSelectPanel.setVisible(false);
		}
		else if(e.getSource().equals(deviceCombobox)) {
			Device device = mainInterface.getMainExecution().getAllDevices().get(deviceCombobox.getSelectedIndex());
			setCurrentDevice(device);
			componentCombobox.removeActionListener(this);
			componentCombobox.removeAllItems();
			for (int i = 0; i < device.getComponents().size(); i++) {
				componentCombobox.addItem(device.getComponents().get(i).getComponent() + "-" + device.getComponents().get(i).getName());
			}
			componentCombobox.addActionListener(this);
			componentCombobox.setSelectedItem(0);
			this.actionPerformed(new ActionEvent(componentCombobox, 0, null));
		}
		else if(e.getSource().equals(componentCombobox)) {
			Component component;
			if (componentCombobox.getSelectedIndex() >= 0)
				component = currentDevice.getComponents().get(componentCombobox.getSelectedIndex());
			else
				component = currentDevice.getComponents().get(0);
			setCurrentComponent(component);
			variableCombobox.removeActionListener(this);
			variableCombobox.removeAllItems();
			for (int i = 0; i < component.getVariables().size(); i++) {
				if (component.getVariables().get(i).getName() != null) 
					variableCombobox.addItem(component.getVariables().get(i).getName());
				else
					variableCombobox.addItem(component.getVariables().get(i).getDataItemID());
			}
			variableCombobox.addActionListener(this);
			variableCombobox.setSelectedItem(0);
			this.actionPerformed(new ActionEvent(variableCombobox, 0, null));
		}
		else if(e.getSource().equals(variableCombobox)) {
			if(variableCombobox.getSelectedIndex() >= 0)
				setCurrentVariable(currentComponent.getVariables().get(variableCombobox.getSelectedIndex()));
			else
				setCurrentVariable(currentComponent.getVariables().get(0));
		}
		else if(e.getSource().equals(addVariableButton)) {
			if((deviceCombobox.getSelectedIndex() < 0) || (componentCombobox.getSelectedIndex() < 0) || (variableCombobox.getSelectedIndex() < 0)) {
				JOptionPane.showMessageDialog(this, "One or more fields are not selected, please set all registers to add a new variable.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (variables.contains(currentVariable))
				JOptionPane.showMessageDialog(this, "The selected variable is already in the monitoring variables list.", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				variables.add(currentVariable);
				configureWorkspace();
			}
		}
		else if(e.getSource().equals(cancelButton))
			this.dispose();
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(e.getSource().equals(okButton)) {
			if(monitoringUnit == null) {
				if(checkPanelInformation(true) && isNameOk(nameField.getText(), true)) {
					if(chartTypeCombobox.getSelectedItem().equals("2DLineChart")) {
						Variable xAxis = getVariableByName((String)xAxisCombobox.getSelectedItem());
						Variable yAxis = getVariableByName((String)yAxisCombobox.getSelectedItem());
						if((xAxis == null) || (yAxis == null)) {
							JOptionPane.showMessageDialog(this, "Invalid variables, some of the variables in the axis are incorrect setted.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else if(xAxis.equals(yAxis)) {
							JOptionPane.showMessageDialog(this, "Invalid variables, some of the variables in the axis are incorrect setted.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							addPanel(nameField.getText(), new int[]{(int)hourField.getValue(), (int)minuteField.getValue(), (int)secondField.getValue()}, (String)chartTypeCombobox.getSelectedItem(), variables, xAxis, yAxis, null);
						}
					}
					else if(chartTypeCombobox.getSelectedItem().equals("3DLineChart")) {
						Variable xAxis = getVariableByName((String)xAxisCombobox.getSelectedItem());
						Variable yAxis = getVariableByName((String)yAxisCombobox.getSelectedItem());
						Variable zAxis = getVariableByName((String)zAxisCombobox.getSelectedItem());
						if((xAxis == null) || (yAxis == null) || (zAxis == null)) {
							JOptionPane.showMessageDialog(this, "Invalid variables, some of the variables in the axis are incorrect setted.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else if(xAxis.equals(yAxis) || xAxis.equals(zAxis) || yAxis.equals(zAxis)) {
							JOptionPane.showMessageDialog(this, "Invalid variables, some of the variables in the axis are incorrect setted.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							addPanel(nameField.getText(), new int[]{(int)hourField.getValue(), (int)minuteField.getValue(), (int)secondField.getValue()}, (String)chartTypeCombobox.getSelectedItem(), variables, xAxis, yAxis, zAxis);
						}
					}
					else 
						addPanel(nameField.getText(), new int[]{(int)hourField.getValue(), (int)minuteField.getValue(), (int)secondField.getValue()}, (String)chartTypeCombobox.getSelectedItem(), variables, null, null, null);
				}
			}
			else if(!variableChanged() && chartTypeCombobox.getSelectedItem().equals(monitoringUnit.getChartType())) {
				if(checkPanelInformation(false)) {
					if(!nameField.getText().equals(monitoringUnit.getName())) {
						if(!isNameOk(nameField.getText(), true)) {
							return;
						}
					}
					if(chartTypeCombobox.getSelectedItem().equals("2DLineChart")) {
						if(xAxisCombobox.getSelectedItem().equals(yAxisCombobox.getSelectedItem())) {
							JOptionPane.showMessageDialog(this, "Invalid axis variables.", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						} else {
							((TwoDMonitoringUnit)monitoringUnit).setxSelected(getVariableByName((String)xAxisCombobox.getSelectedItem()));
							((TwoDMonitoringUnit)monitoringUnit).setySelected(getVariableByName((String)yAxisCombobox.getSelectedItem()));
						}
					}
					else if(chartTypeCombobox.getSelectedItem().equals("3DLineChart")) {
						if(xAxisCombobox.getSelectedItem().equals(yAxisCombobox.getSelectedItem()) || xAxisCombobox.getSelectedItem().equals(zAxisCombobox.getSelectedItem()) || yAxisCombobox.getSelectedItem().equals(zAxisCombobox.getSelectedItem())) {
							JOptionPane.showMessageDialog(this, "Invalid axis variables.", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						} else {
							((ThreeDMonitoringUnit)monitoringUnit).setxSelected(getVariableByName((String)xAxisCombobox.getSelectedItem()));
							((ThreeDMonitoringUnit)monitoringUnit).setySelected(getVariableByName((String)yAxisCombobox.getSelectedItem()));
							((ThreeDMonitoringUnit)monitoringUnit).setzSelected(getVariableByName((String)zAxisCombobox.getSelectedItem()));
						}
					}
					monitoringUnit.setName(nameField.getText());
					monitoringUnit.setTimeRange(new int[]{(int)hourField.getValue(), (int)minuteField.getValue(), (int)secondField.getValue()});
					monitoringUnit.setMinimumWhidth((int)whidthField.getValue());
					monitoringUnit.setMinimumHeight((int)heightField.getValue());
					this.dispose();
				}
			}
			else if(checkPanelInformation(true)) {
				if(!nameField.getText().equals(monitoringUnit.getName())) {
					if(!isNameOk(nameField.getText(), true)) {
						return;
					}
				}
				Variable xAxis = null;
				Variable yAxis = null;
				Variable zAxis = null;
				if(chartTypeCombobox.getSelectedItem().equals("2DLineChart")) {
					if(xAxisCombobox.getSelectedItem().equals(yAxisCombobox.getSelectedItem())) {
						JOptionPane.showMessageDialog(this, "Invalid axis variables.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						xAxis = getVariableByName((String)xAxisCombobox.getSelectedItem());
						yAxis = getVariableByName((String)yAxisCombobox.getSelectedItem());
					}
				}
				else if(chartTypeCombobox.getSelectedItem().equals("3DLineChart")) {
					if(xAxisCombobox.getSelectedItem().equals(yAxisCombobox.getSelectedItem()) || xAxisCombobox.getSelectedItem().equals(zAxisCombobox.getSelectedItem()) || yAxisCombobox.getSelectedItem().equals(zAxisCombobox.getSelectedItem())) {
						JOptionPane.showMessageDialog(this, "Invalid axis variables.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						xAxis = getVariableByName((String)xAxisCombobox.getSelectedItem());
						yAxis = getVariableByName((String)yAxisCombobox.getSelectedItem());
						zAxis = getVariableByName((String)zAxisCombobox.getSelectedItem());
					}
				}
				monitoringUnit.destroyPanelInstance();
				monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().remove(monitoringUnit);
				addPanel(nameField.getText(), new int[]{(int)hourField.getValue(), (int)minuteField.getValue(), (int)secondField.getValue()}, (String)chartTypeCombobox.getSelectedItem(), variables, xAxis, yAxis, zAxis);
				mainInterface.setMenuConfigurePanel();
			}
		}
		if(minimizeButton != null) {
			if (e.getSource().equals(minimizeButton)) {
				monitoringUnit.getPanelButton().setSelected(false);
				if(monitoringUnit.getMonitoringPanel() != null)
					monitoringUnit.setVisible(false);
				monitoringUnit.setVisible(false);
				this.dispose();
			}
			else if(e.getSource().equals(maximizeButton)) {
				for(int i = 0; i < monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().size(); i++) {
					 monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).getPanelButton().setSelected(false);
					if(monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).getMonitoringPanel() != null)
						monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).setVisible(false);
					monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().get(i).setVisible(false);
				}
				monitoringUnit.getPanelButton().doClick();
				this.dispose();
			}
			else if(e.getSource().equals(removePanelButton)) {
				monitoringUnit.getPanelMonitoringSystem().getMonitoringUnits().remove(monitoringUnit);
				mainInterface.setMenuConfigurePanel();
				this.dispose();
			}
			else if(e.getSource().equals(clonePanelButton)) {
				if(monitoringUnit.getChartType() == "2DLineChart")
					addPanel(makeDefaultName("Clone"), monitoringUnit.getTimeRange(), monitoringUnit.getChartType(), monitoringUnit.getVariables(), ((TwoDMonitoringUnit)monitoringUnit).getxSelected(), ((TwoDMonitoringUnit)monitoringUnit).getySelected(), null);
				else if(monitoringUnit.getChartType() == "3DLineChart")
					addPanel(makeDefaultName("Clone"), monitoringUnit.getTimeRange(), monitoringUnit.getChartType(), monitoringUnit.getVariables(), ((ThreeDMonitoringUnit)monitoringUnit).getxSelected(), ((ThreeDMonitoringUnit)monitoringUnit).getySelected(), ((ThreeDMonitoringUnit)monitoringUnit).getzSelected());
				else
					addPanel(makeDefaultName("Clone"), monitoringUnit.getTimeRange(), monitoringUnit.getChartType(), monitoringUnit.getVariables(), null, null, null);
			}
		}
		for (int i = 0; i < variables.size(); i++) {
			if (e.getSource().equals(variables.get(i).getCloseButton())) {
				variables.get(i).setCloseButton(null);
				variables.remove(variables.get(i));
				configureWorkspace();
				break;
			}
		}
		revalidate();
		repaint();
	}
	public boolean variableChanged() {
		if(variables.size() != monitoringUnit.getVariables().size())
			return true;
		for(int i = 0; i < variables.size(); i++) {
			if(!monitoringUnit.getVariables().contains(variables.get(i)))
				return true;
		}
		return false;
	}
	public boolean isNameOk(String s, boolean notify) {
		for(int i = 0; i < mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().size(); i++) {
			if (s.equals(mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().get(i).getName())) {
				if(notify)
					JOptionPane.showMessageDialog(this, "There is another panel with this name, please change it and try again.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	public Variable getVariableByName(String name) {
		for(int i = 0; i < variables.size(); i++) {
			if(variables.get(i).getName() != null) {
				if(variables.get(i).getName().equals(name)) 
					return variables.get(i);
			}
			else {
				if(variables.get(i).getDataItemID().equals(name)) {
					return variables.get(i);
				}
			}
		}
		return null;
	}
	public String makeDefaultName(String begin) {
		String defaultName = null;
		int i = 0;
		while(defaultName == null) {
			boolean create = true;
			for (int j = 0; j < mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().size(); j++) {
				if(mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().get(j).getName().equals(begin + i)) {
					create = false;
					break;
				}
			}
			if (create) {
				defaultName = (begin + i);
			}
			i++;
		}
		return defaultName;
	}
	public void addPanel(String name, int[] timeRange, String chartType, ArrayList<Variable>variables, Variable xAxis, Variable yAxis, Variable zAxis) {
		if(chartType.equals("LineChart") || chartType.equals("AreaChart"))
			mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().add(new NumericMonitoringUnit(name, mainInterface.getMainExecution().getPanelMonitoringSystem(), timeRange, chartType, variables, variables.get(0).getType()));
		else if(chartType.equals("StepLineChart"))
			mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().add(new CategoryMonitoringUnit(name, mainInterface.getMainExecution().getPanelMonitoringSystem(), timeRange, chartType, variables, variables.get(0).getType()));
		else if (chartType.equals("2DLineChart"))
			mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().add(new TwoDMonitoringUnit(name, mainInterface.getMainExecution().getPanelMonitoringSystem(), timeRange, chartType, variables, variables.get(0).getType(), xAxis, yAxis));
		else if (chartType.equals("3DLineChart"))
			mainInterface.getMainExecution().getPanelMonitoringSystem().getMonitoringUnits().add(new ThreeDMonitoringUnit(name, mainInterface.getMainExecution().getPanelMonitoringSystem(), timeRange, chartType, variables, variables.get(0).getType(), xAxis, yAxis, zAxis));
		mainInterface.setMenuConfigurePanel();
		this.dispose();
	}
	public boolean checkPanelInformation(boolean checkVariables) {
		if(checkVariables) {
			if(variables.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Select at least one variable to create the panel.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			for(int i = 0; i < variables.size(); i++) {
				if(variables.get(i).getType() != variables.get(0).getType()) {
					JOptionPane.showMessageDialog(this, "The type of the variables doesn't mismatch, the panel can only be created from same type variables.", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			if ((variables.get(0).getType() == 'i') || (variables.get(0).getType() == 'n')) {
				JOptionPane.showMessageDialog(this, "The panel can only be created from categoric or numeric variables, any other type is not allowed.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if (nameField.equals("")) {
			JOptionPane.showMessageDialog(this, "You cannot create a new panel without a name.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if ((chartTypeCombobox.getSelectedItem().equals("LineChart")) || (chartTypeCombobox.getSelectedItem().equals("AreaChart")) || (chartTypeCombobox.getSelectedItem().equals("2DLineChart")) || (chartTypeCombobox.getSelectedItem().equals("3DLineChart"))) {
			if(variables.get(0).getType() == 'c') {
				JOptionPane.showMessageDialog(this, "You cannot use this type of chart for categoric variables, please select the StepLineChart chartType.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else if (chartTypeCombobox.getSelectedItem().equals("StepLineChart")) {
			if(variables.get(0).getType() == '1') {
				JOptionPane.showMessageDialog(this, "You cannot use this type of chart for numeric variables, please select the LineChart or AreaChart chartType.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "ChartType cannot be identified by the application.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if ((secondField.getValue().equals(0))&&(minuteField.getValue().equals(0))&&(hourField.getValue().equals(0))) {
			JOptionPane.showMessageDialog(this, "Invalid Time Range, it cannot be zero.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (chartTypeCombobox.getSelectedItem().equals("2DLineChart")) {
			if((xAxisCombobox.getSelectedIndex() < 0) || (yAxisCombobox.getSelectedIndex() < 0) || (xAxisCombobox.getSelectedIndex() == yAxisCombobox.getSelectedIndex())) {
				JOptionPane.showMessageDialog(this, "Invalid variable axis.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if (chartTypeCombobox.getSelectedItem().equals("3DLineChart")) {
			if((xAxisCombobox.getSelectedIndex() < 0) || (yAxisCombobox.getSelectedIndex() < 0) || (zAxisCombobox.getSelectedIndex() < 0) || (xAxisCombobox.getSelectedIndex() == yAxisCombobox.getSelectedIndex()) || (xAxisCombobox.getSelectedIndex() == zAxisCombobox.getSelectedIndex()) || (yAxisCombobox.getSelectedIndex() == zAxisCombobox.getSelectedIndex())) {
				JOptionPane.showMessageDialog(this, "Invalid variable axis.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	public void setVariablesCombobox() {
		DefaultComboBoxModel<String> deviceModel = new DefaultComboBoxModel<String>();
		ArrayList<Device>devices = mainInterface.getMainExecution().getAllDevices();
		componentCombobox.setModel(new DefaultComboBoxModel<String>());
		variableCombobox.setModel(new DefaultComboBoxModel<String>());
		for (int j = 0; j < devices.size(); j++) {
			deviceModel.addElement(devices.get(j).getName());
			if (j == 0) {
				deviceModel.setSelectedItem(devices.get(j).getName());
				for (int k = 0; k < devices.get(0).getComponents().size(); k++) {
					componentCombobox.addItem(devices.get(0).getComponents().get(k).getComponent() + "-" + devices.get(0).getComponents().get(k).getName());
					if (k == 0) {
						for (int l = 0; l < devices.get(0).getComponents().get(0).getVariables().size(); l++) {
							if (devices.get(0).getComponents().get(0).getVariables().get(l).getName() != null) 
								variableCombobox.addItem(devices.get(0).getComponents().get(0).getVariables().get(l).getName());
							else
								variableCombobox.addItem(devices.get(0).getComponents().get(0).getVariables().get(l).getDataItemID());
							if (l == 0) {
								setCurrentVariable(devices.get(0).getComponents().get(0).getVariables().get(0));
								setCurrentComponent(devices.get(0).getComponents().get(0));
								setCurrentDevice(devices.get(0));
							}
						}
					}
				}
			}
		}
		deviceCombobox.setModel(deviceModel);
		deviceCombobox.addActionListener(this);
		componentCombobox.addActionListener(this);
		variableCombobox.addActionListener(this);
	}
	public void addVariable(Variable variable) {
		JLabel type = new JLabel();
		if(variable.getType() == '1') {
			type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/numberTypeIcon.png")));
			type.setToolTipText("Numeric Variable Type: this variable shows its information through number values.");
		}
		else if(variable.getType() == 'c') { 
			type = new JLabel((new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/categoryTypeIcon.png"))));
			type.setToolTipText("Category Variable Type: this variable shows its information through categories that describe some kind of information.");
		}
		else if(variable.getType() == 'i') {
			type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
			type.setToolTipText("Irregular Variable Type: this variable shows values that is neither numeric nor category variable Type.");
		}
		else {
			type = new JLabel(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/nullTypeIcon.png")));
			type.setToolTipText("Null Variable Type: this variable didn't show any register yet, so the application cannot identify its type.");
		}
		JTextField var = new JTextField(variable.getValidName());;
		xAxisCombobox.addItem(variable.getValidName());
		yAxisCombobox.addItem(variable.getValidName());
		zAxisCombobox.addItem(variable.getValidName());
		JLabel twoPoint = new JLabel(":");
		JTextField comp = new JTextField(variable.getComponent().getComponent() + "-" + variable.getComponent().getName());
		JLabel twoPoint1 = new JLabel(":");
		JTextField device = new JTextField(variable.getComponent().getDevice().getName());
		var.setEditable(false);
		comp.setEditable(false);
		device.setEditable(false);
		JButton close = new JButton("X");
		workspace.add(type, new GridBagConstraints(0, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(var, new GridBagConstraints(1, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(twoPoint, new GridBagConstraints(2, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(comp, new GridBagConstraints(3, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(twoPoint1, new GridBagConstraints(4, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(device, new GridBagConstraints(5, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(close, new GridBagConstraints(6, variables.indexOf(variable) + 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		close.addActionListener(this);
		variable.setCloseButton(close);
		revalidate();
		repaint();
	}
	public void removeVariable(Variable variable) {
		variables.remove(variable);
		configureWorkspace();
	}
	public void configureWorkspace() {
		workspace.removeAll();
		xAxisCombobox.removeAllItems();
		yAxisCombobox.removeAllItems();
		zAxisCombobox.removeAllItems();
		JLabel type = new JLabel("Type");
		JLabel variable = new JLabel("Variable");
		JLabel component = new JLabel("Component");
		JLabel device = new JLabel("Device");
		workspace.add(type, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(variable, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(component, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		workspace.add(device, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
		//////////////////cria painel das Variaveis////////////////////////
		double[] growList = new double[variables.size() + 2];
		growList[growList.length - 1] = 1.0;
		((GridBagLayout)workspace.getLayout()).rowWeights = growList;
		for(int j = 0; j < variables.size(); j++) 
			addVariable(variables.get(j));
		if(!variables.isEmpty()) {
			xAxisCombobox.setSelectedIndex(0);
			yAxisCombobox.setSelectedIndex(0);
			zAxisCombobox.setSelectedIndex(0);
		}
		revalidate();
		repaint();
	}
///////////////////////////////Getters and Setters//////////////////////////////////////////
	public MonitoringUnit getMonitoringUnit() {
		return monitoringUnit;
	}
	public void setMonitoringUnit(MonitoringUnit monitoringUnit) {
		this.monitoringUnit = monitoringUnit;
	}
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	public Device getCurrentDevice() {
		return currentDevice;
	}
	public void setCurrentDevice(Device currentDevice) {
		this.currentDevice = currentDevice;
	}
	public Component getCurrentComponent() {
		return currentComponent;
	}
	public void setCurrentComponent(Component currentComponent) {
		this.currentComponent = currentComponent;
	}
	public Variable getCurrentVariable() {
		return currentVariable;
	}
	public void setCurrentVariable(Variable currentVariable) {
		this.currentVariable = currentVariable;
	}
	public Variable getCurrentX() {
		return currentX;
	}
	public void setCurrentX(Variable currentX) {
		this.currentX = currentX;
	}
	public Variable getCurrentZ() {
		return currentZ;
	}
	public void setCurrentZ(Variable currentZ) {
		this.currentZ = currentZ;
	}
	public Variable getCurrentY() {
		return currentY;
	}
	public void setCurrentY(Variable currentY) {
		this.currentY = currentY;
	}
}
