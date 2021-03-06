package br.UFSC.GRIMA.operational;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.UFSC.GRIMA.dataStructure.Variable;

public class MonitoringUnit2D extends MonitoringUnit implements SeriesChangeListener {
	private ArrayList<VariableRegister> variableRegisters;
	private ArrayList<RegularTimePeriod> timeRegister;
	private NumericVariableBuffer xAxis;
	private NumericVariableBuffer yAxis;
	private XYSeries valueRegister;
	private XYSeriesCollection chartDataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private Variable xSelected;
	private Variable ySelected;
	private Double xLoad;
	private Double yLoad;
	//////////////layout Components//////////////////
	private JComboBox<String> xCombobox;
	private JComboBox<String> yComboBox;
//////////////////////////////////////////////////Constructor///////////////////////////////////////////////////////////////////////////////////
	public MonitoringUnit2D(String name,PanelMonitoringSystem panelMonitoringSystem, int[] timeRange, String chartType, ArrayList<Variable> variables, char panelType, Variable xAxis, Variable yAxis) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
		setxSelected(xAxis);
		setySelected(yAxis);
	}
////////////////////////////////////////////////////Methods/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(xAxis.getDataSerie())) {
			if(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1) == null) {
				timeRegister.add(xAxis.getDataSerie().getTimePeriod((xAxis.getDataSerie().getItemCount() - 1)));
				valueRegister.add(0, null);
				xLoad = null;
				yLoad = null;
			}
			else if(yLoad != null) {
				timeRegister.add(xAxis.getDataSerie().getTimePeriod((xAxis.getDataSerie().getItemCount() - 1)));
				valueRegister.add(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1), yLoad);
				yLoad = null;
			}
			else 
				xLoad = xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		else if(e.getSource().equals(yAxis.getDataSerie())) {
			if(yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1) == null) {
				timeRegister.add(yAxis.getDataSerie().getTimePeriod((yAxis.getDataSerie().getItemCount() - 1)));
				valueRegister.add(0, null);
				xLoad = null;
				yLoad = null;
			}
			else if(xLoad != null) {
				timeRegister.add(yAxis.getDataSerie().getTimePeriod((yAxis.getDataSerie().getItemCount() - 1)));
				valueRegister.add(xLoad, yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1));
				xLoad = null;
			}
			else 
				yLoad = yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		///////////////////////descarta valores intermediários//////////////////////////////////////////////
		if (valueRegister.getItemCount() > 1) {
			if((valueRegister.getDataItem(valueRegister.getItemCount() - 1) == null)) {
				if (valueRegister.getDataItem(valueRegister.getItemCount() - 2) == null) {
					valueRegister.remove(valueRegister.getItemCount() - 2);
					timeRegister.remove(timeRegister.size() - 2);
				}
			}
			else if(valueRegister.getDataItem(valueRegister.getItemCount() - 1).equals(valueRegister.getDataItem(valueRegister.getItemCount() - 2))) {
				valueRegister.remove(valueRegister.getItemCount() - 2);
				timeRegister.remove(timeRegister.size() - 2);
			}
		}
		/////////////////////////discart old values//////////////////////////////////////////////////////////////////
		
		if (((xSelected.getType() == '1') && (ySelected.getType() == '1')) && (valueRegister.getItemCount() > 1)) {
			XMLGregorianCalendar iniTime =(XMLGregorianCalendar) xSelected.getComponent().getDevice().getAgent().getCreationTime().clone();
			int second;
			int minute;
			int hour;
			second = iniTime.getSecond() - getTimeRange()[2];
			minute = iniTime.getMinute() - getTimeRange()[1];
			hour = iniTime.getHour() - getTimeRange()[0];
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
				catch (IllegalArgumentException ex) {
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
			iniTime.setTime(hour, minute, second);
			iniTime.setDay(day);
			iniTime.setMonth(month);
			iniTime.setYear(year);
			Millisecond inicialTime = new Millisecond(iniTime.toGregorianCalendar().getTime());
			while(timeRegister.size() > 2) {
				if(inicialTime.compareTo(timeRegister.get(0)) <= 0)
					break;
				if(inicialTime.compareTo(timeRegister.get(0)) > 0) {
					timeRegister.remove(0);
					valueRegister.remove(0);
				}
			}
		}
	}

	@Override
	public void actionPerformed2(ActionEvent e) {
		// TODO Auto-generated method stub
		if(xSelected.equals(getVariableByName((String)xCombobox.getSelectedItem())) && ySelected.equals(getVariableByName((String)yComboBox.getSelectedItem())))
			return;
		else if(xSelected.equals(getVariableByName((String)yComboBox.getSelectedItem())) || ySelected.equals(getVariableByName((String)xCombobox.getSelectedItem()))) {
			Variable newX = ySelected;
			setySelected(xSelected);
			setxSelected(newX);
		}
		else if(e.getSource().equals(xCombobox))
			setxSelected(getVariableByName((String)xCombobox.getSelectedItem()));
		else
			setySelected(getVariableByName((String)yComboBox.getSelectedItem()));
		JPanel monitoringPanel = getMonitoringPanel();
		JToggleButton panelButton = getPanelButton();
		monitoringPanel.removeAll();
		
		destroyPanelInstance();
		initPanel(monitoringPanel, panelButton);
		refreshChart();
		getPanelMonitoringSystem().getController().getMainInterface().revalidate();
		getPanelMonitoringSystem().getController().getMainInterface().repaint();
	}
	public Variable getVariableByName(String name) {
		for(int i = 0; i < getVariables().size(); i++) {
			if(getVariables().get(i).getName() != null) {
				if(getVariables().get(i).getName().equals(name)) 
					return getVariables().get(i);
			}
			else {
				if(getVariables().get(i).getDataItemID().equals(name)) {
					return getVariables().get(i);
				}
			}
		}
		return null;
	}

	@Override
	public void doFreeze(boolean freeze) {
		// TODO Auto-generated method stub
		chart.setNotify(freeze);
	}

	@Override
	public void addVariableControl(Variable variable, JPanel container, int line, JLabel typeLabel, JTextField valueField) {
		// TODO Auto-generated method stub
		JLabel displayLabel = new JLabel();
		container.add(displayLabel, new GridBagConstraints(5, line, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		if(variable.equals(xSelected) || variable.equals(ySelected)) {
			NumericVariableBuffer numericvariableBuffer = new NumericVariableBuffer(variable, this);
			if (variable.equals(xSelected)) {
				setxAxis(numericvariableBuffer);
				displayLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/xAxisIcon.png")));
			}
			else {
				setyAxis(numericvariableBuffer);
				displayLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/yAxisIcon.png")));
			}
			numericvariableBuffer.setTypeLabel(typeLabel);
			numericvariableBuffer.setValueTextField(valueField);
			numericvariableBuffer.setDisplayLabel(displayLabel);
		}
		else {
			if(variableRegisters == null)
				setVariableRegisters(new ArrayList<VariableRegister>());
			variableRegisters.add(new VariableRegister(variable, this, typeLabel, valueField, displayLabel));
		}
		
	}

	@Override
	public void initAditionalPanelElements() {
		// TODO Auto-generated method stub
		init2DSerie();
		setChartDataset(new XYSeriesCollection(valueRegister));
		JPanel variablesPanel = new JPanel();
		variablesPanel.setLayout(new GridBagLayout());
		((GridBagLayout)variablesPanel.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)variablesPanel.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)variablesPanel.getLayout()).columnWeights = new double[] {1.0, 1.0};
		((GridBagLayout)variablesPanel.getLayout()).rowWeights = new double[] {1.0, 1.0};
		variablesPanel.setBorder(new TitledBorder("Chart Variables"));
		JLabel xlab = new JLabel("X Axis:");
		JLabel ylab = new JLabel("Y Axis:");
		setxCombobox(new JComboBox<String>());
		setyComboBox(new JComboBox<String>());
		for(int i = 0; i < getVariables().size(); i++) {
			xCombobox.addItem(getVariables().get(i).getValidName());
			yComboBox.addItem(getVariables().get(i).getValidName());
		}
		xCombobox.setSelectedItem(xSelected.getValidName());
		yComboBox.setSelectedItem(ySelected.getValidName());
		variablesPanel.add(xlab, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(ylab, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(xCombobox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(yComboBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		getMonitoringPanel().add(variablesPanel, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		xCombobox.addActionListener(this);
		yComboBox.addActionListener(this);
		
	}

	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		setChart(ChartFactory.createXYLineChart("", xSelected.getValidName(), ySelected.getValidName(), chartDataset));
		((GridBagLayout)getMonitoringPanel().getLayout()).rowHeights[2] = getMinimumHeight();
		getMonitoringPanel().setPreferredSize(new Dimension(getMinimumWhidth(),  (int) Math.round(getMonitoringPanel().getPreferredSize().getHeight())));
		setVisible(isVisible());
		getMonitoringPanel().setVisible(isVisible());
		if (chartPanel != null) 
			getMonitoringPanel().remove(chartPanel);
		setChartPanel(new ChartPanel(chart));
		getMonitoringPanel().add(chartPanel, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
	}
	@Override
	public void destroyPanelInstance() {
		// TODO Auto-generated method stub
		try {
			xSelected.getDataSerie().removeChangeListener(xAxis);
			ySelected.getDataSerie().removeChangeListener(yAxis);
			xAxis.getDataSerie().removeChangeListener(this);
			yAxis.getDataSerie().removeChangeListener(this);
			for(int i = 0; i < variableRegisters.size(); i++) 
				variableRegisters.get(i).getVariable().getDataSerie().removeChangeListener(variableRegisters.get(i));
		}catch(Exception e){}
		setTimeRegister(null);
		setValueRegister(null);
		setVariableRegisters(null);
		setChartPanel(null);
		setChart(null);
		setChartDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
		setxAxis(null);
		setyAxis(null);
		setxCombobox(null);
		setyComboBox(null);
		xLoad = null;
		yLoad = null;
	}
	
	public void init2DSerie() {
		setValueRegister(new XYSeries("", false, true));
		setTimeRegister(new ArrayList<RegularTimePeriod>());
		/////////////////finding first closer pair of the serie///////////////////////////////////////////////
		if(!xSelected.getDataSerie().isEmpty() && !ySelected.getDataSerie().isEmpty()) {
			int xIndex = 0;
			int yIndex = 0;
			if (xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond()) {
				yIndex = findItemClosedTo((Millisecond)xSelected.getDataSerie().getTimePeriod(xIndex), ySelected.getDataSerie());
				timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
			}
			else {
				xIndex = findItemClosedTo((Millisecond)ySelected.getDataSerie().getTimePeriod(yIndex), xSelected.getDataSerie());
				timeRegister.add(ySelected.getDataSerie().getTimePeriod(yIndex));
			}
			while(!((xIndex == xSelected.getDataSerie().getItemCount() - 1)&&(yIndex == ySelected.getDataSerie().getItemCount() - 1))) {
				if((xSelected.getDataSerie().getValue(xIndex) == null) || (ySelected.getDataSerie().getValue(yIndex) == null)) 
					valueRegister.add(0, null);
				else
					valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex));
				if(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond())
					timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
				else
					timeRegister.add(ySelected.getDataSerie().getTimePeriod(yIndex));
				if(xIndex < xSelected.getDataSerie().getItemCount() - 1)
					xIndex++;
				if(yIndex < ySelected.getDataSerie().getItemCount() - 1)
					yIndex++;
				if(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond()) {
					if(yIndex < ySelected.getDataSerie().getItemCount() - 2) {
						if(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(yIndex + 1).getFirstMillisecond())
							xIndex--;
					}
				}
				else if(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() < ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond()){
					if(xIndex < xSelected.getDataSerie().getItemCount() - 2) {
						if(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() > xSelected.getDataSerie().getTimePeriod(xIndex + 1).getFirstMillisecond())
							yIndex--;
					}
				}
			}
			valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex));
//			while((xIndex < xSelected.getDataSerie().getItemCount()) && (yIndex < ySelected.getDataSerie().getItemCount())) {
//				int comp = ((Millisecond)(xSelected.getDataSerie().getTimePeriod(xIndex))).compareTo(ySelected.getDataSerie().getTimePeriod(yIndex));
//				if(comp == 0) {
//					timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
//					if((xSelected.getDataSerie().getValue(xIndex) == null) || (ySelected.getDataSerie().getValue(yIndex) == null)) {
//						valueRegister.add(0, null);
//					}
//					else
//						valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex));
//					if(xIndex < xSelected.getDataSerie().getItemCount())
//						xIndex++;
//					if(yIndex <  ySelected.getDataSerie().getItemCount())
//						yIndex++;
//					continue;
//				}
//				if (comp < 0) {
//					timeRegister.add(ySelected.getDataSerie().getTimePeriod(yIndex));
//					valueRegister.add(xSelected.getDataSerie().getValue(xIndex), ySelected.getDataSerie().getValue(yIndex - 1));
//					if(xIndex <  xSelected.getDataSerie().getItemCount())
//						xIndex++;
//					else
//						yIndex++;
//					continue;
//				}
//				//else
//				timeRegister.add(xSelected.getDataSerie().getTimePeriod(xIndex));
//				valueRegister.add(xSelected.getDataSerie().getValue(xIndex - 1), ySelected.getDataSerie().getValue(yIndex));
//				if(yIndex <  ySelected.getDataSerie().getItemCount())
//					yIndex++;
//				else
//					xIndex++;
//			}
		}
		xAxis.getDataSerie().addChangeListener(this);
		yAxis.getDataSerie().addChangeListener(this);
	}
	public int findItemClosedTo(Millisecond param, TimeSeries list) {
		int closer;
		for(closer = 0; closer < list.getItemCount() - 1; closer++) {
			long distDown = Math.abs(list.getTimePeriod(closer).getFirstMillisecond() - param.getFirstMillisecond());
			long distUp = Math.abs(list.getTimePeriod(closer + 1).getFirstMillisecond() - param.getFirstMillisecond());
			if(distDown <= distUp)
				break;
		}
		return closer;
	}
	
//////////////////////////////////////////////////////////Getters and Setters/////////////////////////////////////////////////////////////////////////////
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public ChartPanel getChartPanel() {
		return chartPanel;
	}
	public void setChartPanel(ChartPanel chartPanel) {
		this.chartPanel = chartPanel;
	}
	public NumericVariableBuffer getxAxis() {
		return xAxis;
	}
	public void setxAxis(NumericVariableBuffer xAxis) {
		this.xAxis = xAxis;
	}
	public NumericVariableBuffer getyAxis() {
		return yAxis;
	}
	public void setyAxis(NumericVariableBuffer yAxys) {
		this.yAxis = yAxys;
	}
	public ArrayList<RegularTimePeriod> getTimeRegister() {
		return timeRegister;
	}
	public void setTimeRegister(ArrayList<RegularTimePeriod> timeRegister) {
		this.timeRegister = timeRegister;
	}
	public XYSeries getValueRegister() {
		return valueRegister;
	}
	public void setValueRegister(XYSeries valueRegister) {
		this.valueRegister = valueRegister;
	}
	public Variable getxSelected() {
		return xSelected;
	}
	public void setxSelected(Variable xSelected) {
		this.xSelected = xSelected;
	}
	public Variable getySelected() {
		return ySelected;
	}
	public void setySelected(Variable ySelected) {
		this.ySelected = ySelected;
	}
	public JComboBox<String> getxCombobox() {
		return xCombobox;
	}
	public void setxCombobox(JComboBox<String> xCombobox) {
		this.xCombobox = xCombobox;
	}
	public JComboBox<String> getyComboBox() {
		return yComboBox;
	}
	public void setyComboBox(JComboBox<String> yComboBox) {
		this.yComboBox = yComboBox;
	}
	public XYSeriesCollection getChartDataset() {
		return chartDataset;
	}
	public void setChartDataset(XYSeriesCollection chartDataset) {
		this.chartDataset = chartDataset;
	}
	public ArrayList<VariableRegister> getVariableRegisters() {
		return variableRegisters;
	}
	public void setVariableRegisters(ArrayList<VariableRegister> variableRegisters) {
		this.variableRegisters = variableRegisters;
	}
	
}
