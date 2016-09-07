package br.UFSC.GRIMA.operational;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;

import br.UFSC.GRIMA.dataStructure.Variable;

public class VariableRegister implements SeriesChangeListener {
	private Variable variable;
	private MonitoringUnit2D twoDMonitoringUnit;
	private MonitoringUnit3D monitoringUnit3D;
	private char varType;
	//////////panelComponents
	private JLabel typeLabel;
	private JTextField valueTextField;
	private JLabel displayLabel;
/////////////////////////////////////////Constructor////////////////////////////////////////////////////
	public VariableRegister(Variable variable, MonitoringUnit2D monitoringUnit, JLabel typeLabel, JTextField valueTextField, JLabel displayLabel) {
		// TODO Auto-generated constructor stub
		setVariable(variable);
		monitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		setTwoDMonitoringUnit(monitoringUnit);
		setTypeLabel(typeLabel);
		setDisplayLabel(displayLabel);
		setValueTextField(valueTextField);
		setVarType(variable.getType());
		variable.getDataSerie().addChangeListener(this);
	}
	public VariableRegister(Variable variable, MonitoringUnit3D monitoringUnit, JLabel typeLabel, JTextField valueTextField, JLabel displayLabel) {
		// TODO Auto-generated constructor stub
		setVariable(variable);
		monitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
//		setThreeDMonitoringUnit(monitoringUnit);
		setTypeLabel(typeLabel);
		setDisplayLabel(displayLabel);
		setValueTextField(valueTextField);
		setVarType(variable.getType());
		variable.getDataSerie().addChangeListener(this);
	}
	
////////////////////////////////////////Methods////////////////////////////////////////////////////////
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		if ((variable.getType() == 'i')&&(varType != 'i')) {
			typeLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
			typeLabel.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
			setVarType('i');
		}
		else if(twoDMonitoringUnit != null) {
			if (twoDMonitoringUnit.getPlayPause() != null) {
				if(twoDMonitoringUnit.getPlayPause().isSelected())
					valueTextField.setText(variable.getLastValue());
			}
		}
		else if(monitoringUnit3D != null) {
			if (monitoringUnit3D.getPlayPause() != null) {
				if(monitoringUnit3D.getPlayPause().isSelected())
					valueTextField.setText(variable.getLastValue());
			}
		}
	}
/////////////////////////////////////////Getters and Setters//////////////////////////////////////////////
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public MonitoringUnit2D getTwoDMonitoringUnit() {
		return twoDMonitoringUnit;
	}
	public void setTwoDMonitoringUnit(MonitoringUnit2D twoDMonitoringUnit) {
		this.twoDMonitoringUnit = twoDMonitoringUnit;
	}
	public JLabel getTypeLabel() {
		return typeLabel;
	}
	public void setTypeLabel(JLabel typeLabel) {
		this.typeLabel = typeLabel;
	}
	public JTextField getValueTextField() {
		return valueTextField;
	}
	public void setValueTextField(JTextField valueTextField) {
		this.valueTextField = valueTextField;
	}
	public char getVarType() {
		return varType;
	}
	public void setVarType(char varType) {
		this.varType = varType;
	}
	public JLabel getDisplayLabel() {
		return displayLabel;
	}
	public void setDisplayLabel(JLabel displayLabel) {
		this.displayLabel = displayLabel;
	}
	public MonitoringUnit3D getMonitoringUnit3D() {
		return monitoringUnit3D;
	}
	public void setMonitoringUnit3D(MonitoringUnit3D monitoringUnit3D) {
		this.monitoringUnit3D = monitoringUnit3D;
	}
	
}
