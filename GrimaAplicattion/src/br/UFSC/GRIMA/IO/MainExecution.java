package br.UFSC.GRIMA.IO;


import java.util.ArrayList;

import br.UFSC.GRIMA.dataStructure.Device;
import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.operational.DeviceMonitoringSystem;
import br.UFSC.GRIMA.operational.MonitoringUnit;
import br.UFSC.GRIMA.operational.PanelMonitoringSystem;
import br.UFSC.GRIMA.visual.LoginEvent;
import br.UFSC.GRIMA.visual.MainInterface;
import br.UFSC.GRIMA.visual.SetNameEvents;


public class MainExecution {
	
	private MainInterface mainInterface;
	private DeviceMonitoringSystem deviceMonitoringSystem;
	private PanelMonitoringSystem panelMonitoringSystem;
	private IOControl ioControl;
	private int[] defaultTimeRange;
	private int[] numericDefaultTimeRange;
	private int[] categoryDefaultTimeRange;
	private boolean defaultTimeOption; //se o time range esta generico (false) ou separado (true)
	public static boolean SPLITTED = false;
	public static boolean GENERAL = true;
	private ArrayList<Variable> newMonitoringPanelList;
	private boolean autoSaveVariables = true; // com essa opcao ativada, todas as variaveis adicionadas ao monitoramento sao automaticamente selecionadas para serem salvas no banco de dados
	private boolean monitorAllVariables = true; //com essa opcao ativada, ao adicionar um agente todas as variaveis das maquinas contidas nele entram na lista de monitoramento automaticamente
	//login do usuario no php
	private String userPHP = "Void";
	private int userIdPHP = 0;
	private String tableName = "New_project";
	private String Observation = "";
	
///////////////////////////Constructor//////////////////////////////////////////////////////////	
	public MainExecution() {
		int[] range = {0, 2, 0};
		setDefaultTimeOption(GENERAL);
		setDefaultTimeRange(range);
		setNumericDefaultTimeRange(range);
		setCategoryDefaultTimeRange(range);
		setDeviceMonitoringSystem(new DeviceMonitoringSystem(this));
		setPanelMonitoringSystem(new PanelMonitoringSystem(this));
		setMainInterface(new MainInterface(this));
		setIoControl(new IOControl(this));
		setNewMonitoringPanelList(new ArrayList<Variable>());
		mainInterface.setVisible(true);
	}
////////////////////////////////////Methods/////////////////////////////////////////////////////////////
	public String addAgent (String ip) {
		if (ioControl.getAgentByIP(ip) != null) {
			String msg = "Cannot connect to the Agent " + ip + "The ip given is already in use in another Agent in the Application.";
			getMainInterface().updateHistory("Agent", msg);
			return (new String("The ip given is already in use in another Agent in the Application."));
		}
		String  str =  ioControl.addAgent(ip);
		mainInterface.setMenuDeviceMonitor();
		return str;
	}
	public ArrayList<Device> getAllDevices() {
		ArrayList<Device> devices = new ArrayList<Device>();
		for(int i = 0; i < ioControl.getAgents().size(); i++) {
			for (int j = 0; j < ioControl.getAgents().get(i).getDevices().size(); j++) {
				devices.add(ioControl.getAgents().get(i).getDevices().get(j));
			}
		}
		return devices;
	}
	public ArrayList<Camera> getAllCameras() {
		ArrayList<Camera> cameras = new ArrayList<Camera>();
		for(int i = 0; i < ioControl.getClientCameras().size(); i++) {
			for(int j = 0; j < ioControl.getClientCameras().get(i).getCameras().size(); j++) 
				cameras.add(ioControl.getClientCameras().get(i).getCameras().get(j));
		}
		return cameras;
	}
	public ArrayList<Agent> getAllAgents() {
		return ioControl.getAgents();
	}
	public boolean addClientCamera(String ip) {
		return ioControl.addClientCamera(ip);
	}
	public void setAllVariablesMonitored() {
		if(!isMonitorAllVariables()) {
			setMonitorAllVariables(true);
			ioControl.getLoadExecution().setVariableList(new ArrayList<Variable>());
			ArrayList<Device>devices = getAllDevices();
			for(int i = 0; i < devices.size(); i++) {
				for(int j = 0; j < devices.get(i).getComponents().size(); j++) {
					for(int k = 0; k < devices.get(i).getComponents().get(j).getVariables().size(); k++)
						ioControl.getLoadExecution().addToVariableList(devices.get(i).getComponents().get(j).getVariables().get(k));
				}
			}
		}
	}
	public void resetVariablesRange() {
		ArrayList<Variable>variables = ioControl.getLoadExecution().getVariableList();
		for(int i = 0; i < variables.size(); i++) {
			variables.get(i).setTimeRange(getTimeRange(variables.get(i).getType()));
		}
	}
	public int[] getTimeRange(char type) {
		if(getDefaultTimeOption() == GENERAL) {
			return defaultTimeRange;
		}
		else {
			if(type == '1') 
				return numericDefaultTimeRange;
			else
				return categoryDefaultTimeRange;
		}
	}
	public void resetPanelsRange() {
		ArrayList<MonitoringUnit>units = getPanelMonitoringSystem().getMonitoringUnits();
		for(int i = 0; i < units.size(); i++) {
			units.get(i).setTimeRange(getTimeRange(units.get(i).getPanelType()));
		}
	}
	public static void main(String[] args)
	{
		String usuario = null;
		int id = -1;
		try {
			//basicService = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
			id = Integer.parseInt(args[0]);
			usuario = args[1];
			MainExecution main = new MainExecution();
			main.setUserPHP(usuario);
			main.setUserIdPHP(id);
			main.getMainInterface().setEnabled(false);
			new SetNameEvents(main.getMainInterface());
		} catch (Exception e) {
			new LoginEvent();
		}
	}
////////////////////Getters and Setters/////////////////////////////////////////////////////
	public IOControl getIoControl() {
		return ioControl;
	}

	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}

	public MainInterface getMainInterface() {
		return mainInterface;
	}

	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}

	public DeviceMonitoringSystem getDeviceMonitoringSystem() {
		return deviceMonitoringSystem;
	}

	public void setDeviceMonitoringSystem(DeviceMonitoringSystem deviceMonitoringSystem) {
		this.deviceMonitoringSystem = deviceMonitoringSystem;
	}

	public PanelMonitoringSystem getPanelMonitoringSystem() {
		return panelMonitoringSystem;
	}

	public void setPanelMonitoringSystem(PanelMonitoringSystem panelMonitoringSystem) {
		this.panelMonitoringSystem = panelMonitoringSystem;
	}
	
	public ArrayList<Variable> getNewMonitoringPanelList() {
		return newMonitoringPanelList;
	}
	public void setNewMonitoringPanelList(ArrayList<Variable> newMonitoringPanelList) {
		this.newMonitoringPanelList = newMonitoringPanelList;
	}
	public int[] getDefaultTimeRange() {
		return defaultTimeRange;
	}
	public void setDefaultTimeRange(int[] defaultTimeRange) {
		this.defaultTimeRange = defaultTimeRange;
	}
	public int[] getNumericDefaultTimeRange() {
		return numericDefaultTimeRange;
	}
	public void setNumericDefaultTimeRange(int[] numericDefaultTimeRange) {
		this.numericDefaultTimeRange = numericDefaultTimeRange;
	}
	public int[] getCategoryDefaultTimeRange() {
		return categoryDefaultTimeRange;
	}
	public void setCategoryDefaultTimeRange(int[] categoryDefaultTimeRange) {
		this.categoryDefaultTimeRange = categoryDefaultTimeRange;
	}
	public boolean getDefaultTimeOption() {
		return defaultTimeOption;
	}
	public void setDefaultTimeOption(boolean defaultTimeOption) {
		this.defaultTimeOption = defaultTimeOption;
	}
	public boolean isAutoSaveVariables() {
		return autoSaveVariables;
	}
	public void setAutoSaveVariables(boolean autoSaveVariables) {
		this.autoSaveVariables = autoSaveVariables;
	}
	public boolean isMonitorAllVariables() {
		return monitorAllVariables;
	}
	public void setMonitorAllVariables(boolean monitorAllVariables) {
		this.monitorAllVariables = monitorAllVariables;
	}
	public String getUserPHP() {
		return userPHP;
	}
	public void setUserPHP(String userPHP) {
		this.userPHP = userPHP;
	}
	public int getUserIdPHP() {
		return userIdPHP;
	}
	public void setUserIdPHP(int userIdPHP) {
		this.userIdPHP = userIdPHP;
	}
	public String getObservation() {
		return Observation;
	}
	public void setObservation(String observation) {
		Observation = observation;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
