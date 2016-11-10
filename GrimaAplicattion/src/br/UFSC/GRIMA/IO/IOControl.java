package br.UFSC.GRIMA.IO;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.application.entities.devices.MTConnectDevicesType;
import br.UFSC.GRIMA.application.entities.streams.MTConnectStreamsType;

public class IOControl {
	private MainExecution controller;
	private ArrayList<Agent> agents;
	private ArrayList<ClientCamera> clientCameras;
	private LoadExecution loadExecution;
	private SaveExecution saveExecution;
	private String tableName = "";
	
	public IOControl(MainExecution mainExecution) {
		setController(mainExecution);
		setAgents(new ArrayList<Agent>());
		setClientCameras(new ArrayList<ClientCamera>());
	}
///////////////////////////Methods/////////////////////////////////////////////////////////////
	public void start() {
		setSaveExecution(new SaveExecution(this));
		setLoadExecution(new LoadExecution(this));
		saveExecution.setTableSerieNumber(tableName);
		loadExecution.getThread().setPriority(Thread.MAX_PRIORITY);
		String str = "=====================================================================" + "\n"
				   + "History							       " + "\n"
				   + "User: " + controller.getUserPHP() + "; ID: " + controller.getUserIdPHP() + "\n"
				   + "Creation Time: " + (new Date()).toString() + "\n"
				   + "=====================================================================";
				
		controller.getMainInterface().updateHistory("", str);
		saveExecution.getBufControl().start();
		loadExecution.start();
		saveExecution.start();
	}
	public Agent getAgentByName(String name) {
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).getAgentName().equals(name)) {
				return agents.get(i);
			}
		}
		return null;
	}
	public Agent getAgentByIP(String name) {
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).getAgentIP().equals(name)) {
				return agents.get(i);
			}
		}
		return null;
	}
	public ClientCamera getClientByIP(String ip) {
		for(int i = 0; i < clientCameras.size(); i++) {
			if(clientCameras.get(i).getIp().equals(ip))
				return clientCameras.get(i);
		}
		return null;
	}
	public String addAgent (String ip) {
		MTConnectDevicesType probeObject = loadExecution.probeRequest(ip);
		if (probeObject == null) {
			String msg = "Cannot connect to the Agent " + ip + "\n" + loadExecution.getLastError();
			getController().getMainInterface().updateHistory("Agent", msg);
			return loadExecution.getLastError();
		}
		else {
			MTConnectStreamsType currentObject = loadExecution.currentRequest(ip);
			if (currentObject == null) {
				String msg = "Cannot connect to the Agent " + ip + "\n" + loadExecution.getLastError();
				getController().getMainInterface().updateHistory("Agent", msg);
				return loadExecution.getLastError();
			}
			else {
				Agent agent =  new Agent(currentObject, probeObject, currentObject.getHeader().getSender(), ip, this);
				agents.add(agent);
				String msg = "Sucessfully connected with the Agent " + agent.getAgentName() + ": " + agent.getAgentIP() + "\nDevices: ";
				for(int i = 0; i < agent.getDevices().size();i++) {
					msg = msg + "\n     " + agent.getDevices().get(i).getName();
				}
				getController().getMainInterface().updateHistory("Agent", msg);
			}
		}
		
		return null;
	}
	public boolean addClientCamera(String name) {
		if(getClientByIP(name) != null) {
			JOptionPane.showMessageDialog(null, "This ip is already added in the application.", "Error in IP", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		clientCameras.add(new ClientCamera(name, this));
		if(clientCameras.get(clientCameras.size() - 1).getCameras() == null) {
			clientCameras.remove(clientCameras.size() - 1);
			return false;
		}
		controller.getMainInterface().setMenuViewCameras();
		return true;
	}
/////////////////////////////Getters and Setters////////////////////////////////////////////
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	public MainExecution getController() {
		return controller;
	}
	public void setController(MainExecution controller) {
		this.controller = controller;
	}
	public LoadExecution getLoadExecution() {
		return loadExecution;
	}
	public void setLoadExecution(LoadExecution loadExecution) {
		this.loadExecution = loadExecution;
	}
	public ArrayList<ClientCamera> getClientCameras() {
		return clientCameras;
	}
	public void setClientCameras(ArrayList<ClientCamera> clientCameras) {
		this.clientCameras = clientCameras;
	}
	public SaveExecution getSaveExecution() {
		return saveExecution;
	}
	public void setSaveExecution(SaveExecution saveExecution) {
		this.saveExecution = saveExecution;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
