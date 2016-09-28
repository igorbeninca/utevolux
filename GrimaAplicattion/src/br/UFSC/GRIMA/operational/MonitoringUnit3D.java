package br.UFSC.GRIMA.operational;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.graphics3d.swing.Panel3D;
import com.orsoncharts.plot.XYZPlot;

import br.UFSC.GRIMA.dataStructure.Variable;

public class MonitoringUnit3D extends MonitoringUnit implements SeriesChangeListener, Runnable {
	private ArrayList<VariableRegister> variableRegisters;
	private ArrayList<ArrayList<RegularTimePeriod>> timeRegister;
	private NumericVariableBuffer xAxis;
	private NumericVariableBuffer yAxis;
	private NumericVariableBuffer zAxis;
	private Variable xSelected;
	private Variable ySelected;
	private Variable zSelected;
	private Double xLoad;
	private Double yLoad;
	private Double zLoad;
	private ArrayList<XYZSeries>series;//para inserir descontinuidades
	private XYZSeriesCollection dataset;
	private Chart3D chart;
	private Chart3DPanel panel;
	//////////////layout Components//////////////////
	private JComboBox<String> xComboBox;
	private JComboBox<String> yComboBox;
	private JComboBox<String> zComboBox;
/////////////////////////////////////////////////////constructor/////////////////////////////////////////////////////////////////////////////////
	public MonitoringUnit3D(String name,PanelMonitoringSystem panelMonitoringSystem, int[] timeRange,String chartType, ArrayList<Variable> variables, char panelType, Variable xAxis, Variable yAxis, Variable zAxis) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
		setxSelected(xAxis);
		setySelected(yAxis);
		setzSelected(zAxis);
	}
	
/////////////////////////////////////////////////Methods//////////////////////////////////////////////////////////////////////////////////////////
	public void breakSerie() {
		if(!(series.get(series.size() - 1).getItemCount() == 0)) {
			series.add(new XYZSeries(Integer.parseInt(series.get(series.size() - 1).getKey().toString()) + 1));
			dataset.add(series.get(series.size() - 1));
			timeRegister.add(new ArrayList<RegularTimePeriod>());
		}
	}
	public int getSize() {
		int size = series.size() - 1;
		for(int i = 0; i < series.size(); i++) {
			size += series.get(i).getItemCount();
		}
		return size;
	}
	public int getSizeSinceBreak() {
		int size = series.get(series.size() - 1).getItemCount();
		return size;
	}
	public void remove(ArrayList<ArrayList<Integer>>indexes) {
		ArrayList<XYZSeries>sList = new ArrayList<XYZSeries>();
		ArrayList<ArrayList<RegularTimePeriod>>tList = new ArrayList<ArrayList<RegularTimePeriod>>();
		for(int i = 0; i < series.size(); i++) {
			XYZSeries s = new XYZSeries(series.get(i).getKey());
			ArrayList<RegularTimePeriod>t = new ArrayList<RegularTimePeriod>();
			for(int j = 0; j < series.get(i).getItemCount(); j++) {
				if(!contains(indexes, i, j)) {
					s.add(series.get(i).getXValue(j), series.get(i).getYValue(j), series.get(i).getZValue(j));
					t.add(timeRegister.get(i).get(j));
				}
			}
			if(!t.isEmpty()) {
				sList.add(s);
				tList.add(t);
			}
		}
		if(sList.isEmpty()) {
			sList.add(new XYZSeries(0 + ""));
			tList.add(new ArrayList<RegularTimePeriod>());
		}
		setSeries(sList);
		setTimeRegister(tList);
		setDataset(new XYZSeriesCollection());
		 ViewPoint3D vp = chart.getViewPoint();
		for(int i = 0; i < series.size(); i++) {
			dataset.add(series.get(i));
		}
		refreshChart();
		chart.setViewPoint(vp);
	}
	public boolean contains(ArrayList<ArrayList<Integer>>indexes, int serie, int ind) {
		for(int i = 0; i < indexes.size(); i++) {
			if(indexes.get(i).get(0).equals(serie) && indexes.get(i).get(1).equals(ind))
				return true;
		}
		return false;
	}
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(xAxis.getDataSerie())) {
			if(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1) == null) {
				breakSerie();
				xLoad = null;
				yLoad = null;
				zLoad = null;
			}
			else if((yLoad != null)&&(zLoad != null)) {
				timeRegister.get(timeRegister.size() - 1).add(xAxis.getDataSerie().getTimePeriod((xAxis.getDataSerie().getItemCount() - 1)));
				series.get(series.size() - 1).add(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue(), yLoad, zLoad);
				yLoad = null;
				zLoad = null;
			}
			else 
				xLoad = xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		else if(e.getSource().equals(yAxis.getDataSerie())) {
			if(yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1) == null) {
				breakSerie();
				xLoad = null;
				yLoad = null;
				zLoad = null;
			}
			else if((xLoad != null)&&(zLoad != null)) {
				timeRegister.get(timeRegister.size() - 1).add(yAxis.getDataSerie().getTimePeriod((yAxis.getDataSerie().getItemCount() - 1)));
				series.get(series.size() - 1).add(xLoad, yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1).doubleValue(), zLoad);
				xLoad = null;
				zLoad = null;
			}
			else 
				yLoad = yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		else if(e.getSource().equals(zAxis.getDataSerie())) {
			if(zAxis.getDataSerie().getValue(zAxis.getDataSerie().getItemCount() - 1) == null) {
				breakSerie();
				xLoad = null;
				yLoad = null;
				zLoad = null;
			}
			else if((xLoad != null)&&(yLoad != null)) {
				timeRegister.get(timeRegister.size() - 1).add(zAxis.getDataSerie().getTimePeriod((zAxis.getDataSerie().getItemCount() - 1)));
				series.get(series.size() - 1).add(xLoad, yLoad, zAxis.getDataSerie().getValue(zAxis.getDataSerie().getItemCount() - 1).doubleValue());
				xLoad = null;
				yLoad = null;
			}
			else 
				zLoad = zAxis.getDataSerie().getValue(zAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		ArrayList<ArrayList<Integer>>toDiscart = new ArrayList<ArrayList<Integer>>();
		///////////////////////descarta valores intermediários//////////////////////////////////////////////
		if (getSizeSinceBreak() > 1) {
			XYZSeries serie = series.get(series.size() - 1);
			double x1 = serie.getXValue(serie.getItemCount() - 1);
			double y1 = serie.getYValue(serie.getItemCount() - 1);
			double z1 = serie.getZValue(serie.getItemCount() - 1);
			double x2 = serie.getXValue(serie.getItemCount() - 2);
			double y2 = serie.getYValue(serie.getItemCount() - 2);
			double z2 = serie.getZValue(serie.getItemCount() - 2);
			if ((x1 == x2)&&(y1 == y2)&&(z1 == z2)) {
				ArrayList<Integer>target = new ArrayList<Integer>();
				target.add(series.size() - 1);
				target.add(serie.getItemCount() - 2);
				toDiscart.add(target);
			}
		}
		/////////////////////////discart old values//////////////////////////////////////////////////////////////////
		if ((xSelected.getType() == '1') && (ySelected.getType() == '1') && (zSelected.getType() == '1') && (getSizeSinceBreak() > 1)) {
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
			for(int i = 0; i < series.size(); i++) {
				boolean noCont = false;
				for(int j = 0; j < series.get(i).getItemCount(); j++) {
					if(inicialTime.compareTo(timeRegister.get(i).get(j)) <= 0) {
						noCont = true;
						break;
					}
					else {
						ArrayList<Integer> index = new ArrayList<Integer>();
						index.add(i);index.add(j);
						toDiscart.add(index);
					}
				}
				if(noCont)
					break;
			}
			System.out.println("                              discart Elements: " + toDiscart.size());
			if(!toDiscart.isEmpty())
				remove(toDiscart);
		}
		SwingUtilities.invokeLater(this);
		System.out.println(getSize());
	}
	@Override
	public void actionPerformed2(ActionEvent e) {
		// TODO Auto-generated method stub
		if(xSelected.equals(getVariableByName((String)xComboBox.getSelectedItem())) && ySelected.equals(getVariableByName((String)yComboBox.getSelectedItem())) && zSelected.equals(getVariableByName((String)zComboBox.getSelectedItem())))
			return;
		else if(xSelected.equals(getVariableByName((String)yComboBox.getSelectedItem())) || ySelected.equals(getVariableByName((String)xComboBox.getSelectedItem()))) {
			Variable newX = ySelected;
			setySelected(xSelected);
			setxSelected(newX);
		}
		else if(xSelected.equals(getVariableByName((String)zComboBox.getSelectedItem())) || zSelected.equals(getVariableByName((String)xComboBox.getSelectedItem()))) {
			Variable newX = zSelected;
			setzSelected(xSelected);
			setxSelected(newX);
		}
		else if(zSelected.equals(getVariableByName((String)yComboBox.getSelectedItem())) || ySelected.equals(getVariableByName((String)zComboBox.getSelectedItem()))) {
			Variable newZ = ySelected;
			setySelected(zSelected);
			setzSelected(newZ);
		}
		else if(e.getSource().equals(xComboBox))
			setxSelected(getVariableByName((String)xComboBox.getSelectedItem()));
		else if(e.getSource().equals(yComboBox))
			setySelected(getVariableByName((String)yComboBox.getSelectedItem()));
		else if(e.getSource().equals(zComboBox))
			setzSelected(getVariableByName((String)zComboBox.getSelectedItem()));
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
	public void addVariableControl(Variable variable, JPanel container,	int line, JLabel typeLabel, JTextField valueField) {
		// TODO Auto-generated method stub
		JLabel displayLabel = new JLabel();
		container.add(displayLabel, new GridBagConstraints(5, line, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		if(variable.equals(xSelected) || variable.equals(ySelected) || variable.equals(zSelected)) {
			NumericVariableBuffer numericvariableBuffer = new NumericVariableBuffer(variable, this);
			if (variable.equals(xSelected)) {
				setxAxis(numericvariableBuffer);
				displayLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/xAxisIcon.png")));
			}
			else if (variable.equals(ySelected)) {
				setyAxis(numericvariableBuffer);
				displayLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/yAxisIcon.png")));
			}
			else if (variable.equals(zSelected)) {
				setzAxis(numericvariableBuffer);
				displayLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/zAxisIcon.png")));
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
		setDataset(new XYZSeriesCollection());
		init3DSerie();
		JPanel variablesPanel = new JPanel();
		variablesPanel.setLayout(new GridBagLayout());
		((GridBagLayout)variablesPanel.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)variablesPanel.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)variablesPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0};
		((GridBagLayout)variablesPanel.getLayout()).rowWeights = new double[] {1.0, 1.0};
		variablesPanel.setBorder(new TitledBorder("Chart Variables"));
		JLabel xlab = new JLabel("X Axis:");
		JLabel ylab = new JLabel("Y Axis:");
		JLabel zlab = new JLabel("Z Axis:");
		setxComboBox(new JComboBox<String>());
		setyComboBox(new JComboBox<String>());
		setzComboBox(new JComboBox<String>());
		for(int i = 0; i < getVariables().size(); i++) {
			xComboBox.addItem(getVariables().get(i).getValidName());
			yComboBox.addItem(getVariables().get(i).getValidName());
			zComboBox.addItem(getVariables().get(i).getValidName());
		}
		xComboBox.setSelectedItem(xSelected.getValidName());
		yComboBox.setSelectedItem(ySelected.getValidName());
		zComboBox.setSelectedItem(zSelected.getValidName());
		variablesPanel.add(xlab, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(ylab, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(zlab, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(xComboBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(yComboBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(zComboBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		getMonitoringPanel().add(variablesPanel, new GridBagConstraints(0, 4, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		xComboBox.addActionListener(this);
		yComboBox.addActionListener(this);
		zComboBox.addActionListener(this);
	}
	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		setChart(Chart3DFactory.createXYZLineChart("", "", dataset, xSelected.getValidName(), ySelected.getValidName(), zSelected.getValidName()));
		chart.setLegendBuilder(null);
		((XYZPlot)chart.getPlot()).getRenderer().setColors(Color.BLACK);
		chart.setNotify(true);
		((GridBagLayout)getMonitoringPanel().getLayout()).rowHeights[2] = getMinimumHeight();
		getMonitoringPanel().setPreferredSize(new Dimension(getMinimumWhidth(),  (int) Math.round(getMonitoringPanel().getPreferredSize().getHeight())));
		setVisible(isVisible());
		getMonitoringPanel().setVisible(isVisible());
		if (panel != null) 
			getMonitoringPanel().remove(panel);
		setPanel(new Chart3DPanel(chart));
		getMonitoringPanel().add(panel, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		getMonitoringPanel().setPreferredSize(getMonitoringPanel().getPreferredSize());
		SwingUtilities.invokeLater(this);
	}
	@Override
	public void destroyPanelInstance() {
		// TODO Auto-generated method stub
		try {
			xSelected.getDataSerie().removeChangeListener(xAxis);
			ySelected.getDataSerie().removeChangeListener(yAxis);
			zSelected.getDataSerie().removeChangeListener(zAxis);
			xAxis.getDataSerie().removeChangeListener(this);
			yAxis.getDataSerie().removeChangeListener(this);
			zAxis.getDataSerie().removeChangeListener(this);
			for(int i = 0; i < variableRegisters.size(); i++) 
				variableRegisters.get(i).getVariable().getDataSerie().removeChangeListener(variableRegisters.get(i));
		}catch(Exception e){}
		setTimeRegister(null);
		setSeries(null);
		setVariableRegisters(null);
		setPanel(null);
		setChart(null);
		setDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
		setxAxis(null);
		setyAxis(null);
		setzAxis(null);
		setxComboBox(null);
		setyComboBox(null);
		setzComboBox(null);
		xLoad = null;
		yLoad = null;
		zLoad = null;
	}
	public void init3DSerie() {
		setSeries(new ArrayList<XYZSeries>());
		setTimeRegister(new ArrayList<ArrayList<RegularTimePeriod>>());
		series.add(new XYZSeries(0 + ""));
		dataset.add(series.get(0));
		timeRegister.add(new ArrayList<RegularTimePeriod>());
		//////////////////////finding first closer pair of the serie///////////////////////////////////////////////
		if(!xSelected.getDataSerie().isEmpty() && !ySelected.getDataSerie().isEmpty() && !zSelected.getDataSerie().isEmpty()) {
			int xIndex = 0;
			int yIndex = 0;
			int zIndex = 0;
			if ((xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() >= ySelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond()) && (xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() >= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond())) {
				yIndex = findItemLeftTo((Millisecond)xSelected.getDataSerie().getTimePeriod(xIndex), ySelected.getDataSerie());
				zIndex = findItemLeftTo((Millisecond)xSelected.getDataSerie().getTimePeriod(xIndex), zSelected.getDataSerie());
				timeRegister.get(0).add(xSelected.getDataSerie().getTimePeriod(xIndex));
			}
			else if ((ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() >= xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond()) && (ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() >= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond())) {
				xIndex = findItemLeftTo((Millisecond)ySelected.getDataSerie().getTimePeriod(yIndex), xSelected.getDataSerie());
				zIndex = findItemLeftTo((Millisecond)ySelected.getDataSerie().getTimePeriod(yIndex), zSelected.getDataSerie());
				timeRegister.get(0).add(ySelected.getDataSerie().getTimePeriod(yIndex));
			}
			else {
				xIndex = findItemLeftTo((Millisecond)zSelected.getDataSerie().getTimePeriod(zIndex), xSelected.getDataSerie());
				yIndex = findItemLeftTo((Millisecond)zSelected.getDataSerie().getTimePeriod(zIndex), ySelected.getDataSerie());
				timeRegister.get(0).add(zSelected.getDataSerie().getTimePeriod(zIndex));
			}
			while(!((xIndex == xSelected.getDataSerie().getItemCount() - 1)&&(yIndex == ySelected.getDataSerie().getItemCount() - 1)&&(zIndex == zSelected.getDataSerie().getItemCount() - 1))) {
				if((xSelected.getDataSerie().getValue(xIndex) == null) || (ySelected.getDataSerie().getValue(yIndex) == null) || (zSelected.getDataSerie().getValue(zIndex) == null)) {
					breakSerie();
				}
				else {
					series.get(series.size() - 1).add(xSelected.getDataSerie().getValue(xIndex).doubleValue(), ySelected.getDataSerie().getValue(yIndex).doubleValue(), zSelected.getDataSerie().getValue(zIndex).doubleValue());
					if((xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() >= ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond())&&(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() >= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()))
						timeRegister.get(timeRegister.size() - 1).add(xSelected.getDataSerie().getTimePeriod(xIndex));
					else if((ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() >= xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond())&&(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() >= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()))
						timeRegister.get(timeRegister.size() - 1).add(ySelected.getDataSerie().getTimePeriod(yIndex));
					else
						timeRegister.get(timeRegister.size() - 1).add(zSelected.getDataSerie().getTimePeriod(zIndex));
				}
//				System.out.println("times: " + timeRegister.get(timeRegister.size() - 1).get(timeRegister.get(timeRegister.size() - 1).size() - 1).getFirstMillisecond() + "->" + xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() + ", " + ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() + ", " + zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond());
				if(xIndex < xSelected.getDataSerie().getItemCount() - 1)
					xIndex++;
				if(yIndex < ySelected.getDataSerie().getItemCount() - 1)
					yIndex++;
				if(zIndex < zSelected.getDataSerie().getItemCount() - 1)
					zIndex++;
				boolean xFirst = false;
				boolean yFirst = false;
				boolean zFirst = false;
				if((xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() <= ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond())&&(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() <= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()))
					xFirst = true;
				else if((ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() <= xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond())&&(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() <= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()))
					yFirst = true;
				else
					zFirst = true;
				boolean xDec = false;
				boolean yDec = false;
				boolean zDec = false;
				if(!xFirst) {
					if(yIndex < ySelected.getDataSerie().getItemCount() - 2) {
						if(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(yIndex + 1).getFirstMillisecond())
							xDec = true;
					}
					if(zIndex < zSelected.getDataSerie().getItemCount() - 2) {
						if(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() > zSelected.getDataSerie().getTimePeriod(zIndex + 1).getFirstMillisecond())
							xDec = true;
					}
				}
				if(!yFirst) {
					if(xIndex < xSelected.getDataSerie().getItemCount() - 2) {
						if(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() > xSelected.getDataSerie().getTimePeriod(xIndex + 1).getFirstMillisecond())
							yDec = true;
					}
					if(zIndex < zSelected.getDataSerie().getItemCount() - 2) {
						if(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() > zSelected.getDataSerie().getTimePeriod(zIndex + 1).getFirstMillisecond())
							yDec = true;
					}
				}
				if(!zFirst) {
					if(xIndex < xSelected.getDataSerie().getItemCount() - 2) {
						if(zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond() > xSelected.getDataSerie().getTimePeriod(xIndex + 1).getFirstMillisecond())
							zDec = true;
					}
					if(yIndex < ySelected.getDataSerie().getItemCount() - 2) {
						if(zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond() > ySelected.getDataSerie().getTimePeriod(yIndex + 1).getFirstMillisecond())
							zDec = true;
					}
				}
				if(xDec)
					xIndex--;
				if(yDec)
					yIndex--;
				if(zDec)
					zIndex--;
			}
			if((xSelected.getDataSerie().getValue(xIndex) == null) || (ySelected.getDataSerie().getValue(yIndex) == null) || (zSelected.getDataSerie().getValue(zIndex) == null)) {
				breakSerie();
			}
			else {
				series.get(series.size() - 1).add(xSelected.getDataSerie().getValue(xIndex).doubleValue(), ySelected.getDataSerie().getValue(yIndex).doubleValue(), zSelected.getDataSerie().getValue(zIndex).doubleValue());
				if((xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() >= ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond())&&(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond() >= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()))
					timeRegister.get(timeRegister.size() - 1).add(xSelected.getDataSerie().getTimePeriod(xIndex));
				else if((ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() >= xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond())&&(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond() >= zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()))
					timeRegister.get(timeRegister.size() - 1).add(ySelected.getDataSerie().getTimePeriod(yIndex));
				else
					timeRegister.get(timeRegister.size() - 1).add(zSelected.getDataSerie().getTimePeriod(zIndex));
			}
		}
		xAxis.getDataSerie().addChangeListener(this);
		yAxis.getDataSerie().addChangeListener(this);
		zAxis.getDataSerie().addChangeListener(this);
	}
	public int findItemLeftTo(Millisecond param, TimeSeries list) {
		int closer;
		for(closer = 0; closer < list.getItemCount() - 1; closer++) {
			if((param.getFirstMillisecond() >= list.getTimePeriod(closer).getFirstMillisecond())&&(param.getFirstMillisecond() <= list.getTimePeriod(closer + 1).getFirstMillisecond()))
				break;
		}
		return closer;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		dataset.setNotify(true);
		dataset.setNotify(false);
	}
/////////////////////////////////////////////////////Getters and Setters/////////////////////////////////////////////////////////////////////////
	public NumericVariableBuffer getyAxis() {
		return yAxis;
	}

	public void setyAxis(NumericVariableBuffer yAxis) {
		this.yAxis = yAxis;
	}

	public NumericVariableBuffer getzAxis() {
		return zAxis;
	}

	public void setzAxis(NumericVariableBuffer zAxis) {
		this.zAxis = zAxis;
	}

	public Variable getxSelected() {
		return xSelected;
	}

	public void setxSelected(Variable xSelected) {
		this.xSelected = xSelected;
	}

	public NumericVariableBuffer getxAxis() {
		return xAxis;
	}

	public void setxAxis(NumericVariableBuffer xAxis) {
		this.xAxis = xAxis;
	}

	public Variable getySelected() {
		return ySelected;
	}

	public void setySelected(Variable ySelected) {
		this.ySelected = ySelected;
	}

	public Variable getzSelected() {
		return zSelected;
	}

	public void setzSelected(Variable zSelected) {
		this.zSelected = zSelected;
	}

	public ArrayList<VariableRegister> getVariableRegisters() {
		return variableRegisters;
	}

	public void setVariableRegisters(ArrayList<VariableRegister> variableRegisters) {
		this.variableRegisters = variableRegisters;
	}

	public Double getxLoad() {
		return xLoad;
	}

	public void setxLoad(Double xLoad) {
		this.xLoad = xLoad;
	}

	public Double getyLoad() {
		return yLoad;
	}

	public void setyLoad(Double yLoad) {
		this.yLoad = yLoad;
	}

	public Double getzLoad() {
		return zLoad;
	}

	public void setzLoad(Double zLoad) {
		this.zLoad = zLoad;
	}

	public JComboBox<String> getxComboBox() {
		return xComboBox;
	}

	public void setxComboBox(JComboBox<String> xComboBox) {
		this.xComboBox = xComboBox;
	}

	public JComboBox<String> getyComboBox() {
		return yComboBox;
	}

	public void setyComboBox(JComboBox<String> yComboBox) {
		this.yComboBox = yComboBox;
	}

	public JComboBox<String> getzComboBox() {
		return zComboBox;
	}

	public void setzComboBox(JComboBox<String> zComboBox) {
		this.zComboBox = zComboBox;
	}


	public XYZSeriesCollection getDataset() {
		return dataset;
	}

	public void setDataset(XYZSeriesCollection dataset) {
		this.dataset = dataset;
	}

	public Chart3D getChart() {
		return chart;
	}

	public void setChart(Chart3D chart) {
		this.chart = chart;
	}

	public Chart3DPanel getPanel() {
		return panel;
	}

	public void setPanel(Chart3DPanel panel) {
		this.panel = panel;
	}

	public ArrayList<XYZSeries> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<XYZSeries> series) {
		this.series = series;
	}
	public ArrayList<ArrayList<RegularTimePeriod>> getTimeRegister() {
		return timeRegister;
	}

	public void setTimeRegister(ArrayList<ArrayList<RegularTimePeriod>> timeRegister) {
		this.timeRegister = timeRegister;
	}
}