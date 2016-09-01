package br.UFSC.GRIMA.IO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;

import br.UFSC.GRIMA.application.entities.devices.MTConnectDevicesType;
import br.UFSC.GRIMA.application.entities.streams.MTConnectStreamsType;
import br.UFSC.GRIMA.dataStructure.*;
import br.UFSC.GRIMA.visual.MainInterface;

public class LoadExecution implements Runnable {
	private IOControl ioControl;
	private ArrayList<Variable> variableList;
	private ArrayList<Agent> agentList;
	private SaveExecution saveExecution;
	private Thread thread;
	private long loopTime;
	private long currentTime;
	private long AgentSlowLimit = 1000;
	private long abortCommunication = 15000;
	private String lastError;
	private boolean connected = true;
////////////////////////////////////Constructor/////////////////////////////////////////////////////////////
	public LoadExecution(IOControl ioControl) {
		setIoControl(ioControl);
		setSaveExecution(ioControl.getSaveExecution());
		setVariableList(new ArrayList<Variable>());
		setAgentList(new ArrayList<Agent>());
		setThread(new Thread(this, "Load Execution"));
		setCurrentTime(System.currentTimeMillis());
	}
/////////////////////////Methods//////////////////////////////////////////////////////////////////////////
	public void start() {
		thread.start();
	}
	private JAXBElement<MTConnectDevicesType> extracted(Unmarshaller u, URL url) throws JAXBException 
	{
		return (JAXBElement<MTConnectDevicesType>)u.unmarshal(url);
	}
	public MTConnectDevicesType probeRequest(String ip) {
		try {
			JAXBContext jc = JAXBContext.newInstance(MTConnectDevicesType.class);
			Unmarshaller u = jc.createUnmarshaller();
			URL url = new URL(ip + "/probe");
			JAXBElement<MTConnectDevicesType> element = extracted(u, url);
			return element.getValue();
		}
		catch (Exception e) {
			setLastError(new String(e.toString()));
			return null;
		}
	}
	public MTConnectStreamsType currentRequest(String ip) {
		try	{
			JAXBContext jc = JAXBContext.newInstance(MTConnectStreamsType.class);
			Unmarshaller u = jc.createUnmarshaller();
			URL url = new URL(ip + "/current" );
			JAXBElement<MTConnectStreamsType> element =(JAXBElement<MTConnectStreamsType>)u.unmarshal(url);
			return element.getValue();
		}
		catch (Exception e) {
			setLastError(new String(e.toString()));
			return null;
		}
	}
	public void addToVariableList(Variable variable) {
		if (!agentList.contains(variable.getComponent().getDevice().getAgent())) 
			agentList.add(variable.getComponent().getDevice().getAgent());
		if (!variableList.contains(variable)) {
			if (variable.getDataSerie() == null) {
				if(variable.getName() != null) 
					variable.setDataSerie(new TimeSeries(variable.getName()));
				else
					variable.setDataSerie(new TimeSeries(variable.getDataItemID()));
			}
			variable.getDataSerie().addChangeListener(variable);
			variable.getDataSerie().setNotify(false);
			variableList.add(variable);
		}
		if(ioControl.getController().isAutoSaveVariables())
			addToSaveList(variable);
		if(variable.getVarMonitored() != null)
			variable.getVarMonitored().setSelected(true);
	}
	public boolean removeFromVariableList(Variable variable, boolean notify) { ///////////////////////////lembrar de adicionar o valor nulo no final da lista
		if (ioControl.getController().getPanelMonitoringSystem().isVariableMonitored(variable)) {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The variable is currently used in one of the created Monitoring Panels, to remove from the monitoring you need to destroy the panels.", "Remove Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (ioControl.getController().getDeviceMonitoringSystem().getSelectedVariables().contains(variable)) {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The variable is currently added to the Device Monitoring Segment, please remove it from this segment first.", "Remove Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (saveExecution.getSaveList().contains(variable)) {
			if(notify) 
				JOptionPane.showMessageDialog(ioControl.getController().getMainInterface(), "The system is saving this variable in the current DataBase, please remove the variable from that list first.", "Remove Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			variableList.remove(variable);
			boolean agentUsedInOtherVariable = false;
			for(int i = 0; i < variableList.size(); i++) {
				if(variableList.get(i).getComponent().getDevice().getAgent().equals(variable.getComponent().getDevice().getAgent())) {
					agentUsedInOtherVariable = true;
				}
			}
			if(!agentUsedInOtherVariable) 
				agentList.remove(variable.getComponent().getDevice().getAgent());
			if(variable.getVarMonitored() != null)
				variable.getVarMonitored().setSelected(false);
			return true;
		}
	}
	public void addToSaveList(Variable variable) {
		if(!saveExecution.getSaveList().contains(variable))
			saveExecution.getSaveList().add(variable);
		if(variable.getVarSaving() != null)
			variable.getVarSaving().setSelected(true);
		if(!variableList.contains(variable))
			addToVariableList(variable);
	}
	public void removeFromSaveList(Variable variable) {
		saveExecution.getSaveList().remove(variable);
		if(variable.getVarSaving() != null)
			variable.getVarSaving().setSelected(false);
	}
///////////////////////Getters and Setters///////////////////////////////////////////////////////////////
	public IOControl getIoControl() {
		return ioControl;
	}
	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}
	public ArrayList<Variable> getVariableList() {
		return variableList;
	}
	public void setVariableList(ArrayList<Variable> variableList) {
		this.variableList = variableList;
	}
	public ArrayList<Agent> getAgentList() {
		return agentList;
	}
	public void setAgentList(ArrayList<Agent> agentList) {
		this.agentList = agentList;
	}
	public SaveExecution getSaveExecution() {
		return saveExecution;
	}
	public void setSaveExecution(SaveExecution saveExecution) {
		this.saveExecution = saveExecution;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public long getLoopTime() {
		return loopTime;
	}
	public void setLoopTime(long loopTime) {
		this.loopTime = loopTime;
	}
	public String getLastError() {
		return lastError;
	}
	public void setLastError(String lastError) {
		this.lastError = lastError;
	}
////////////////////////Run Tasks/////////////////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			boolean offline = false;
			if (!variableList.isEmpty()) {
				ArrayList<CurrentThread> threads = new ArrayList<CurrentThread>();
				for (int i = 0; i < agentList.size(); i++) {
					threads.add(new CurrentThread(agentList.get(i), this));
				}
				boolean terminated = false;
				long timeL = System.currentTimeMillis();
				while (!terminated) {
					terminated = true;
					for(int i = 0; i < threads.size(); i++) 
						terminated = terminated && threads.get(i).isTerminated();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {}
					if((System.currentTimeMillis() - timeL) > abortCommunication) {//abort communication procedure 
						for(int i = 0; i < threads.size(); i++) {
							if(!threads.get(i).isTerminated()) {
								threads.get(i).abort();
							}
						}
					}
				}
				try {
					Millisecond referenceTime = new Millisecond(threads.get(0).getResult().getHeader().getCreationTime().toGregorianCalendar().getTime());
					ioControl.getController().getMainInterface().setCurrentTime(referenceTime.toString());
				}catch(Exception e){}
				//gerenciamento de status
				int slowAgents = 0;
				int offlineAgents = 0;
				for(int i = 0; i < threads.size(); i++) {
					if(threads.get(i).getResult() != null)
						threads.get(i).getTarget().setCreationTime(threads.get(i).getResult().getHeader().getCreationTime());
					if(threads.get(i).getTarget().getStatus()== Agent.OFFLINE)
						offlineAgents++;
					else if(threads.get(i).getTarget().getStatus()== Agent.SLOW)
						slowAgents++;
				}
				if(offlineAgents == agentList.size()) {
					ioControl.getController().getMainInterface().setAgentStatus("Error: Connection Lost.", MainInterface.OFFLINE);
					offline = true;
				}
				else if(slowAgents == agentList.size()) {
					ioControl.getController().getMainInterface().setAgentStatus("Warning: All agents are Slow.", MainInterface.WARNING);
				}
				else if((slowAgents == 0) && (offlineAgents == 0)) {
					if(!agentList.isEmpty())
						ioControl.getController().getMainInterface().setAgentStatus("Online", MainInterface.ONLINE);
				}
				else {
					String msg = "Warning: ";
					if(slowAgents != 0) {
						msg = msg + slowAgents + " of " + agentList.size() + "are slow;"; 
					}
					if(offlineAgents != 0) {
						msg = msg + offlineAgents + " of " + agentList.size() + "are offline"; 
					}
					ioControl.getController().getMainInterface().setAgentStatus(msg, MainInterface.WARNING);
				}
				for(int i = 0; i < variableList.size(); i++) {
					MTConnectStreamsType currentObject = null;
					for (int j = 0; j < threads.size(); j++) {
						if (variableList.get(i).getComponent().getDevice().getAgent().equals(threads.get(j).getTarget())) {
							currentObject = threads.get(j).getResult();
							break;
						}
					}
					if (currentObject != null) {
						int devPosition = variableList.get(i).getComponent().getDevice().getAgentPosition();
						int comPosition = variableList.get(i).getComponent().getAgentPosition();
						int varPosition = variableList.get(i).getAgentPosition();
						String value = null;
						XMLGregorianCalendar time = null;
						if (variableList.get(i).getDivision() == 'S') {
							value = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getSamples().getSample().get(varPosition).getValue().getValue();
							time  = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getSamples().getSample().get(varPosition).getValue().getTimestamp();
						}
						else if (variableList.get(i).getDivision() == 'E') {
							value = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getEvents().getEvent().get(varPosition).getValue().getValue();
							time = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getEvents().getEvent().get(varPosition).getValue().getTimestamp();
						}
						else {
							value = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getCondition().getCondition().get(varPosition).getName().getLocalPart();
							time = currentObject.getStreams().getDeviceStream().get(devPosition).getComponentStream().get(comPosition).getCondition().getCondition().get(varPosition).getValue().getTimestamp();
						}
						
						//escrevendo no buffer do banco de dados
						if(!variableList.get(i).getXMLValue().equals(value) && saveExecution.getSaveList().contains(variableList.get(i))) {
							//escrevendo no history
							if(variableList.get(i).getType() == 'c') {
								ioControl.getController().getMainInterface().updateHistory("Device", "Value changed: " + variableList.get(i).getComponent().getDevice().getAgent().getAgentName() + "/" +
																														 variableList.get(i).getComponent().getDevice().getName() + "/" +
																														 variableList.get(i).getComponent().getComponent() + "-" + variableList.get(i).getComponent().getComponentID() + "/" +
																														 variableList.get(i).getValidName() + ": " + value.toUpperCase(), time.toXMLFormat());
							}
							ArrayList<String> register = new ArrayList<String>();
							variableList.get(i).setXMLValue(value);
							register.add(currentObject.getHeader().getSender());
							register.add(currentObject.getStreams().getDeviceStream().get(devPosition).getName());
							register.add(variableList.get(i).getComponent().getName());
							register.add(variableList.get(i).getValidName());
							register.add(value);
							register.add(time.toXMLFormat());
							ioControl.getSaveExecution().getBuffer().add(register);
						}
						//carregando informacoes no cliente
						time.setTimezone(currentObject.getHeader().getCreationTime().getTimezone());
						XMLGregorianCalendar iniTime = (XMLGregorianCalendar) currentObject.getHeader().getCreationTime().clone();
						int second = iniTime.getSecond() - variableList.get(i).getTimeRange()[2];
						int minute = iniTime.getMinute() - variableList.get(i).getTimeRange()[1];
						int hour = iniTime.getHour() - variableList.get(i).getTimeRange()[0];
						int day = iniTime.getDay();
						int month = iniTime.getMonth();
						int year = iniTime.getYear();
						if (second < 0) {
							second = second + 60;
							minute--;
						}
						if (minute < 0) {
							minute = minute + 60;
							hour--;
						}
						if (hour < 0) {
							hour = hour + 24;
							day--;
						}
						if (day < 1) {
							XMLGregorianCalendar correction = iniTime;
							try {
								correction.setMonth(iniTime.getMonth() - 1);
							}
							catch (IllegalArgumentException e) {
								correction.setYear(iniTime.getYear() - 1);
								correction.setMonth(1);
							}
							day = day + correction.toGregorianCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
							month--;
						}
						if (month < 1) {
							month = month + 12;
							year--;
						}
						Millisecond creationTime = new Millisecond(iniTime.toGregorianCalendar().getTime());    ////////////////////creationTime
						iniTime.setTime(hour, minute, second);
						iniTime.setDay(day);
						iniTime.setMonth(month);
						iniTime.setYear(year);
						Millisecond inicialTime = new Millisecond(iniTime.toGregorianCalendar().getTime());     /////////////////////timeStamp init time value
						Millisecond timestamp = new Millisecond(time.toGregorianCalendar().getTime());			/////////////////////register time value
						boolean tolerable = (Math.abs(timestamp.getFirstMillisecond() - creationTime.getFirstMillisecond()) <= 1000);
						if(variableList.get(i).getType() == 'n') {
							if(!value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).typeIdentification(value);
							}
						}
						if(variableList.get(i).getType() == '1') {
							if(value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).getDataSerie().addOrUpdate(creationTime, null);
							}
							else {
								try {
									double numValue = ((Double)(Double.parseDouble(value.replace(',', '.')))).doubleValue();
									if(variableList.get(i).getDataSerie().isEmpty()) {
										if((timestamp.compareTo(inicialTime) > 0) && (timestamp.compareTo(creationTime) < 0))
											variableList.get(i).getDataSerie().addOrUpdate(timestamp, numValue);
										else if(timestamp.compareTo(inicialTime) < 0) {
											variableList.get(i).getDataSerie().addOrUpdate(inicialTime, numValue);
										}
										variableList.get(i).getDataSerie().setNotify(true);
										variableList.get(i).getDataSerie().setNotify(false);
									}
									if(tolerable) 
										variableList.get(i).getDataSerie().addOrUpdate(timestamp, numValue);
									else
										variableList.get(i).getDataSerie().addOrUpdate(creationTime, numValue);
								}
								catch (Exception e) {
									variableList.get(i).setVariableToIrregular();
									e.printStackTrace();
								}
							}
						}
						else if(variableList.get(i).getType() == 'c') {
							if(value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).getDataSerie().addOrUpdate(creationTime, null);
							}
							else  {
								if(variableList.get(i).getDataSerie().isEmpty()) {
									if((timestamp.compareTo(inicialTime) > 0) && (timestamp.compareTo(creationTime) < 0))
										variableList.get(i).getDataSerie().addOrUpdate(timestamp, variableList.get(i).getCategoryPosition(value));
									else if(timestamp.compareTo(inicialTime) < 0) {
										variableList.get(i).getDataSerie().addOrUpdate(inicialTime, variableList.get(i).getCategoryPosition(value));
									}
									variableList.get(i).getDataSerie().setNotify(true);
									variableList.get(i).getDataSerie().setNotify(false);
								}
								if(tolerable)
									variableList.get(i).getDataSerie().addOrUpdate(timestamp, variableList.get(i).getCategoryPosition(value));
								else
									variableList.get(i).getDataSerie().addOrUpdate(creationTime, variableList.get(i).getCategoryPosition(value));
								if (variableList.get(i).getCategoryStrings().size() > 10) {
									variableList.get(i).setVariableToIrregular();
								}
							}
						}
						else if(variableList.get(i).getType() == 'i') {
							if(value.toUpperCase().equals("UNAVAILABLE")) {
								variableList.get(i).getDataSerie().clear();
								variableList.get(i).getDataSerie().add(creationTime, 0);
								variableList.get(i).getCategoryStrings().clear();
								variableList.get(i).getCategoryStrings().add(null);
							}
							else {
								variableList.get(i).getDataSerie().clear();
								variableList.get(i).getDataSerie().add(creationTime, 0);
								variableList.get(i).getCategoryStrings().clear();
								variableList.get(i).getCategoryStrings().add(value);
							}
						}
						///////////////Discart medium value/////////////////
						TimeSeries serie = variableList.get(i).getDataSerie();
						if (serie.getItemCount() > 3) {
							if((serie.getValue(serie.getItemCount() - 1) == null)&&(serie.getValue(serie.getItemCount() - 2) == null)&&(serie.getValue(serie.getItemCount() - 3) == null) && (serie.getValue(serie.getItemCount() - 4) == null))
								serie.delete(serie.getItemCount()-3, serie.getItemCount()-3); /// deleta o penúltimo registro
							else if (serie.getValue(serie.getItemCount() - 1) != null) {
								if (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 2)) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 3))) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 4))))
									serie.delete(serie.getItemCount()-3, serie.getItemCount()-3); /// deleta o penúltimo registro
							}
						}
						//////////////discart old values////////////////////////
						if (((variableList.get(i).getType() == '1') || (variableList.get(i).getType() == 'c'))&& (serie.getItemCount() > 1)) {
							while(serie.getItemCount() > 2) {
								if(inicialTime.compareTo(serie.getTimePeriod(0)) <= 0)
									break;
								if(inicialTime.compareTo(serie.getTimePeriod(0)) > 0) {
									if (inicialTime.compareTo(serie.getTimePeriod(1)) < 0) {
										if ((variableList.get(i).getType() == '1') && (serie.getValue(0) != null) && (serie.getValue(1) != null)) {
											//faz uma aproximacao linear em t0
											double y1 = serie.getValue(0).doubleValue();
											double y2 = serie.getValue(1).doubleValue();
											long x0 = inicialTime.getLastMillisecond();
											long x1 = serie.getTimePeriod(0).getLastMillisecond();
											long x2 = serie.getTimePeriod(1).getLastMillisecond();
											double a =  (double) ((y2 - y1 )/(x2 - x1));
											double b = (double) (y1 - a*x1);
											double yn = a*x0 + b;
											serie.addOrUpdate(inicialTime, yn);
										}
										else {
											serie.addOrUpdate(inicialTime, serie.getValue(0));
										}
									}
									serie.delete(0, 0);
								}
							}
							//////////////////////ajusta valores na categoryStrings
							if (variableList.get(i).getType() == 'c') {
								boolean[] existence = new boolean[variableList.get(i).getCategoryStrings().size()];
								for (int j = 0; j < variableList.get(i).getDataSerie().getItemCount(); j++) {
									if(variableList.get(i).getDataSerie().getValue(j)!= null)
										existence[variableList.get(i).getDataSerie().getValue(j).intValue()] = true;
								}
								boolean noChange = true;
								for (int j = 0; j < existence.length; j++) {
									noChange = noChange && existence[j];
								}
								if (!noChange) {
									int[] correction = new int[existence.length];
									ArrayList<String>newCategoryStrings = new ArrayList<String>();
									correction[0] = 0;
									for(int j = 0; j < existence.length; j++) {
										if (j != 0)
											correction[j] = correction[j - 1];
										if(existence[j] == true) 
											newCategoryStrings.add(variableList.get(i).getCategoryStrings().get(j));
										else {
											correction[j]++;
										}
											
									}
									variableList.get(i).setCategoryStrings(newCategoryStrings);
									for(int j = 0; j < variableList.get(i).getDataSerie().getItemCount(); j++) {
										if(variableList.get(i).getDataSerie().getValue(j) != null)
											variableList.get(i).getDataSerie().update(j, variableList.get(i).getDataSerie().getValue(j).intValue() - correction[variableList.get(i).getDataSerie().getValue(j).intValue()]);
									}
								}
							}
						}
					}
					else {
						if(!variableList.get(i).getDataSerie().isEmpty())
							variableList.get(i).getDataSerie().addOrUpdate(variableList.get(i).getDataSerie().getTimePeriod(variableList.get(i).getDataSerie().getItemCount() - 1).next(), null);
					}
					variableList.get(i).getDataSerie().setNotify(true);
					variableList.get(i).getDataSerie().setNotify(false);
				}
			}
			long loop = System.currentTimeMillis() - getCurrentTime();
			if(offline)
				ioControl.getController().getMainInterface().setLoadExPing("inf");
			else
				ioControl.getController().getMainInterface().setLoadExPing(loop + "");
			setLoopTime(loop);
			if (loop < 200) {
				try {
					Thread.sleep(200 - loop);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setCurrentTime(System.currentTimeMillis());
		}
	}
	public long getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	public long getAgentSlowLimit() {
		return AgentSlowLimit;
	}
	public void setAgentSlowLimit(long agentSlowLimit) {
		AgentSlowLimit = agentSlowLimit;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		if((connected == false)&&(this.connected == true)) {
			ioControl.getController().getMainInterface().updateHistory("Agent", "Connection lost with all Agents.");
		}
		if((connected == true)&&(this.connected == false)) {
			ioControl.getController().getMainInterface().updateHistory("Agent", "Connection re-established with all Agents.");
		}
		this.connected = connected;
	}
	
	
}
