package br.UFSC.GRIMA.operational;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.chart.ChartPanel;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.swing.JPlot;
import com.panayotis.gnuplot.utils.Debug;

import br.UFSC.GRIMA.dataStructure.Variable;

public class ThreeDMonitoringUnit extends MonitoringUnit implements SeriesChangeListener, ActionListener, Runnable {
	private ArrayList<VariableRegister> variableRegisters;
	private NumericVariableBuffer xAxis;
	private NumericVariableBuffer yAxis;
	private NumericVariableBuffer zAxis;
	private Variable xSelected;
	private Variable ySelected;
	private Variable zSelected;
	private Double xLoad;
	private Double yLoad;
	private Double zLoad;
	private ArrayList<Timed3DSerie>registers;
	private Thread painter;
	private JPlot jplot;
	private boolean serieChanged;
	private boolean notify;
	//////////////layout Components//////////////////
	private JComboBox<String> xCombobox;
	private JComboBox<String> yComboBox;
	private JComboBox<String> zComboBox;
		
//////////////////////////////////Comstructor//////////////////////////////////////////
	public ThreeDMonitoringUnit(String name, PanelMonitoringSystem panelMonitoringSystem, int[] timeRange, String chartType, ArrayList<Variable> variables, char panelType, Variable xAxis, Variable yAxis, Variable zAxis) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
		setxSelected(xAxis);
		setySelected(yAxis);
		setzSelected(zAxis);
	}
////////////////////////////////////Methods///////////////////////////////////////////////
	@Override
	public void actionPerformed2(ActionEvent e) {
		// TODO Auto-generated method stub
		if(xSelected.equals(getVariableByName((String)xCombobox.getSelectedItem())) && ySelected.equals(getVariableByName((String)yComboBox.getSelectedItem())) && zSelected.equals(getVariableByName((String)zComboBox.getSelectedItem())))
			return;
		
		if(xSelected.equals(getVariableByName((String)yComboBox.getSelectedItem())) || ySelected.equals(getVariableByName((String)xCombobox.getSelectedItem())))
			changeVariables(xSelected, ySelected);
		else if(xSelected.equals(getVariableByName((String)zComboBox.getSelectedItem())) || zSelected.equals(getVariableByName((String)xCombobox.getSelectedItem()))) 
			changeVariables(xSelected, zSelected);
		else if(ySelected.equals(getVariableByName((String)zComboBox.getSelectedItem())) || zSelected.equals(getVariableByName((String)yComboBox.getSelectedItem()))) 
			changeVariables(ySelected, zSelected);
		else if(e.getSource().equals(xCombobox))
			setxSelected(getVariableByName((String)xCombobox.getSelectedItem()));
		else if(e.getSource().equals(yComboBox))
			setySelected(getVariableByName((String)yComboBox.getSelectedItem()));
		else
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
	public void changeVariables(Variable a, Variable b) {
		Variable newA = b;
		b = a;
		a = newA;
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
	public void seriesChanged(SeriesChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(xAxis.getDataSerie())) {
			if(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1) == null) {
				registers.add(new Timed3DSerie(0, 0, 0, xAxis.getDataSerie().getTimePeriod((xAxis.getDataSerie().getItemCount() - 1))));
				registers.get(registers.size() - 1).setNullRegister(true);
				xLoad = null;
				yLoad = null;
				zLoad = null;
			}
			else if((yLoad != null) && (zLoad != null)) {
				registers.add(new Timed3DSerie(xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue(), yLoad, zLoad, xAxis.getDataSerie().getTimePeriod(xAxis.getDataSerie().getItemCount() - 1)));
				yLoad = null;
				zLoad = null;
			}
			else 
				xLoad = xAxis.getDataSerie().getValue(xAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		else if(e.getSource().equals(yAxis.getDataSerie())) {
			if(yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1) == null) {
				registers.add(new Timed3DSerie(0, 0, 0, yAxis.getDataSerie().getTimePeriod((yAxis.getDataSerie().getItemCount() - 1))));
				registers.get(registers.size() - 1).setNullRegister(true);
				xLoad = null;
				yLoad = null;
				zLoad = null;
			}
			else if((xLoad != null) && (zLoad != null)) {
				registers.add(new Timed3DSerie(xLoad, yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1).doubleValue(), zLoad, xAxis.getDataSerie().getTimePeriod(xAxis.getDataSerie().getItemCount() - 1)));
				xLoad = null;
				zLoad = null;
			}
			else 
				yLoad = yAxis.getDataSerie().getValue(yAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		else if(e.getSource().equals(zAxis.getDataSerie())) {
			if(zAxis.getDataSerie().getValue(zAxis.getDataSerie().getItemCount() - 1) == null) {
				registers.add(new Timed3DSerie(0, 0, 0, zAxis.getDataSerie().getTimePeriod((zAxis.getDataSerie().getItemCount() - 1))));
				registers.get(registers.size() - 1).setNullRegister(true);
				xLoad = null;
				yLoad = null;
				zLoad = null;
			}
			else if((xLoad != null) && (yLoad != null)) {
				registers.add(new Timed3DSerie(xLoad, yLoad, zAxis.getDataSerie().getValue(zAxis.getDataSerie().getItemCount() - 1).doubleValue(), zAxis.getDataSerie().getTimePeriod(zAxis.getDataSerie().getItemCount() - 1)));
				xLoad = null;
				zLoad = null;
			}
			else 
				zLoad = zAxis.getDataSerie().getValue(zAxis.getDataSerie().getItemCount() - 1).doubleValue();
		}
		///////////////////////descarta valores intermediários//////////////////////////////////////////////
		if (registers.size() > 1) {
			if(registers.get(registers.size() - 1).isNullRegister()) {
				if (registers.get(registers.size() - 2).isNullRegister()) 
					registers.remove(registers.size() - 2);
			}
			else if(registers.get(registers.size() - 1).equals(registers.get(registers.size() - 2))) {
				registers.remove(registers.size() - 2);
			}
		}
		/////////////////////////discart old values//////////////////////////////////////////////////////////////////
		
		if (((xSelected.getType() == '1') && (ySelected.getType() == '1')&&(zSelected.getType() == '1')) && (registers.size() > 1)) {
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
			while(registers.size() > 2) {
				if(inicialTime.compareTo(registers.get(0).getTime()) <= 0)
					break;
				if(inicialTime.compareTo(registers.get(0).getTime()) > 0) {
					registers.remove(0);
					break;
				}
			}
		}
		setSerieChanged(true);
	}

	@Override
	public void freezeChart(boolean freeze) {
		// TODO Auto-generated method stub
		setNotify(freeze);
	}

	@Override
	public void addVariableControl(Variable variable, JPanel container, int line, JLabel typeLabel, JTextField valueField) {
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
			else if(variable.equals(ySelected)){
				setyAxis(numericvariableBuffer);
				displayLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/yAxisIcon.png")));
			}
			else {
				setzAxis(numericvariableBuffer);
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
		setRegisters(new ArrayList<Timed3DSerie>());
		init3DSerie();
		setJplot(new JPlot());
		jplot.setLayout(new GridBagLayout());
		((GridBagLayout)jplot.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)jplot.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)jplot.getLayout()).columnWeights = new double[] {1.0, 1.0e-4};
		((GridBagLayout)jplot.getLayout()).rowWeights = new double[] {1.0, 1.0e-4};
		JavaPlot.getDebugger().setLevel(Debug.VERBOSE);
		setNotify(getPlayPause().isSelected());
		setSerieChanged(true);
		setPainter(new Thread(this));
		painter.start();
		JPanel variablesPanel = new JPanel();
		variablesPanel.setLayout(new GridBagLayout());
		((GridBagLayout)variablesPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
		((GridBagLayout)variablesPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)variablesPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0};
		((GridBagLayout)variablesPanel.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0};
		variablesPanel.setBorder(new TitledBorder("Chart Variables"));
		JLabel xlab = new JLabel("X Axis:");
		JLabel ylab = new JLabel("Y Axis:");
		JLabel zLab = new JLabel("Z Axis:");
		setxCombobox(new JComboBox<String>());
		setyComboBox(new JComboBox<String>());
		setzComboBox(new JComboBox<String>());
		for(int i = 0; i < getVariables().size(); i++) {
			if(getVariables().get(i).getName() != null) {
				xCombobox.addItem(getVariables().get(i).getName());
				yComboBox.addItem(getVariables().get(i).getName());
				zComboBox.addItem(getVariables().get(i).getName());
			} else {
				xCombobox.addItem(getVariables().get(i).getDataItemID());
				yComboBox.addItem(getVariables().get(i).getDataItemID());
				zComboBox.addItem(getVariables().get(i).getName());
			}
		}
		if(xSelected.getName() != null)
			xCombobox.setSelectedItem(xSelected.getName());
		else
			xCombobox.setSelectedItem(xSelected.getDataItemID());
		if(ySelected.getName() != null)
			yComboBox.setSelectedItem(ySelected.getName());
		else
			yComboBox.setSelectedItem(ySelected.getDataItemID());
		if(zSelected.getName() != null)
			zComboBox.setSelectedItem(zSelected.getName());
		else
			zComboBox.setSelectedItem(zSelected.getDataItemID());
		variablesPanel.add(xlab, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(ylab, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(zLab, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		variablesPanel.add(xCombobox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
		xCombobox.addActionListener(this);
		yComboBox.addActionListener(this);
		zComboBox.addActionListener(this);
	}
	public void init3DSerie() {
		/////////////////finding first closer pair of the serie///////////////////////////////////////////////
		if(!xSelected.getDataSerie().isEmpty() && !ySelected.getDataSerie().isEmpty()) {
			int xIndex = 0;
			int yIndex = 0;
			int zIndex = 0;
			RegularTimePeriod time = null;
			long max = Math.max(xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond(), Math.max(ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond(), zSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond()));
			if (max == xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond()) {
				yIndex = findItemClosedTo((Millisecond)xSelected.getDataSerie().getTimePeriod(xIndex), ySelected.getDataSerie());
				zIndex = findItemClosedTo((Millisecond)xSelected.getDataSerie().getTimePeriod(xIndex), zSelected.getDataSerie());
				time = xSelected.getDataSerie().getTimePeriod(xIndex);
			}
			else if (max == ySelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond()) {
				xIndex = findItemClosedTo((Millisecond)ySelected.getDataSerie().getTimePeriod(yIndex), xSelected.getDataSerie());
				zIndex = findItemClosedTo((Millisecond)ySelected.getDataSerie().getTimePeriod(yIndex), zSelected.getDataSerie());
				time = ySelected.getDataSerie().getTimePeriod(xIndex);
			}
			else {
				xIndex = findItemClosedTo((Millisecond)zSelected.getDataSerie().getTimePeriod(zIndex), xSelected.getDataSerie());
				yIndex = findItemClosedTo((Millisecond)zSelected.getDataSerie().getTimePeriod(zIndex), ySelected.getDataSerie());
				time = zSelected.getDataSerie().getTimePeriod(xIndex);
			}
			registers.add(new Timed3DSerie(xSelected.getDataSerie().getValue(xIndex).doubleValue(), ySelected.getDataSerie().getValue(yIndex).doubleValue(), zSelected.getDataSerie().getValue(zIndex).doubleValue(), time));
			while((xIndex < xSelected.getDataSerie().getItemCount()) && (yIndex < ySelected.getDataSerie().getItemCount())) {
				long xtime = xSelected.getDataSerie().getTimePeriod(xIndex).getFirstMillisecond();
				long ytime = xSelected.getDataSerie().getTimePeriod(yIndex).getFirstMillisecond();
				long ztime = xSelected.getDataSerie().getTimePeriod(zIndex).getFirstMillisecond();
				long min = Math.min(xtime, Math.min(ytime, ztime));
				int xdec = 1;
				int ydec = 1;
				int zdec = 1;
				RegularTimePeriod instant = null;
				if(min == xtime) {
					xdec = 0;
					instant = xSelected.getDataSerie().getTimePeriod(xIndex);
				}
				if(min == ytime) {
					ydec = 0;
					instant = ySelected.getDataSerie().getTimePeriod(yIndex);
				}
				if(min == ztime) {
					zdec = 0;
					instant = zSelected.getDataSerie().getTimePeriod(zIndex);
				}
				registers.add(new Timed3DSerie(xSelected.getDataSerie().getValue(xIndex - xdec).doubleValue(), ySelected.getDataSerie().getValue(yIndex - ydec).doubleValue(), zSelected.getDataSerie().getValue(zIndex - zdec).doubleValue(), instant));
				if(xIndex >= xSelected.getDataSerie().getItemCount()) {
					if(ytime < ztime)
						ydec = 0;
					else if(ztime < ytime)
						zdec = 0;
					else {
						ydec = 0;
						zdec = 0;
					}
				}
				if(yIndex >= ySelected.getDataSerie().getItemCount()) {
					if(xtime < ztime)
						xdec = 0;
					else if(ztime < xtime)
						zdec = 0;
					else {
						xdec = 0;
						zdec = 0;
					}
				}
				if(zIndex >= zSelected.getDataSerie().getItemCount()) {
					if(xtime < ytime)
						xdec = 0;
					else if(ytime < xtime)
						ydec = 0;
					else {
						xdec = 0;
						ydec = 0;
					}
				}
				if(xIndex < xSelected.getDataSerie().getItemCount()) 
					xIndex = xIndex - xdec + 1;
				if(yIndex < ySelected.getDataSerie().getItemCount()) 
					yIndex = yIndex - ydec + 1;
				if(zIndex < zSelected.getDataSerie().getItemCount()) 
					zIndex = zIndex - zdec + 1;
				
			}
		}
		xAxis.getDataSerie().addChangeListener(this);
		yAxis.getDataSerie().addChangeListener(this);
		zAxis.getDataSerie().addChangeListener(this);
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
	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		((GridBagLayout)getMonitoringPanel().getLayout()).rowHeights[2] = getMinimumHeight();
		getMonitoringPanel().setPreferredSize(new Dimension(getMinimumWhidth(),  (int) Math.round(getMonitoringPanel().getPreferredSize().getHeight())));
		setVisible(isVisible());
		getMonitoringPanel().setVisible(isVisible());
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
		if(painter != null)
			painter.interrupt();
		setPainter(null);
		if(jplot != null)
			getMonitoringPanel().remove(jplot);
		setJplot(null);
		setRegisters(null);
		setVariableRegisters(null);
		setMonitoringPanel(null);
		setPlayPause(null);
		setxAxis(null);
		setyAxis(null);
		setzAxis(null);
		setxCombobox(null);
		setyComboBox(null);
		xLoad = null;
		yLoad = null;
	}
	public double[][] getCoordinateArray() {
		double[][] array = new double[registers.size()][3];
		for(int i = 0; i < registers.size(); i++) {
			array[i][0] = registers.get(i).getX();
			array[i][1] = registers.get(i).getY();
			array[i][2] = registers.get(i).getZ();
		}
		return array;
	}
 	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(registers != null) {
				if(notify && serieChanged && !registers.isEmpty()) {
					try {
						double[][] array = getCoordinateArray();
						setSerieChanged(false);
						DataSetPlot plot = new DataSetPlot(array);
						plot.setTitle("");
						JPlot jplot = new JPlot();
						jplot.getJavaPlot().newGraph3D();
						jplot.getJavaPlot().getAxis("x").setLabel(xSelected.getValidName());
						jplot.getJavaPlot().getAxis("y").setLabel(ySelected.getValidName());
						jplot.getJavaPlot().getAxis("z").setLabel(zSelected.getValidName());
						jplot.getJavaPlot().addPlot(plot);
						PlotStyle stl2 = ((AbstractPlot) jplot.getJavaPlot().getPlots().get(0)).getPlotStyle();
					    stl2.setStyle(Style.LINES);
					    stl2.setLineType(NamedPlotColor.RED);
					    jplot.getJavaPlot().plot();
					    jplot.setBorder(new TitledBorder(""));
					    getMonitoringPanel().remove(this.jplot);
						setJplot(jplot);
						jplot.setBackground(getMonitoringPanel().getBackground());
					    getMonitoringPanel().add(jplot, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
					    getMonitoringPanel().revalidate();
					    getMonitoringPanel().repaint();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				else {
					try {
						painter.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
///////////////////////////////////////Getters and Setters//////////////////////////////////////////
	public ArrayList<VariableRegister> getVariableRegisters() {
		return variableRegisters;
	}
	public void setVariableRegisters(ArrayList<VariableRegister> variableRegisters) {
		this.variableRegisters = variableRegisters;
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
	public ArrayList<Timed3DSerie> getRegisters() {
		return registers;
	}
	public void setRegisters(ArrayList<Timed3DSerie> registers) {
		this.registers = registers;
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
	public JComboBox<String> getzComboBox() {
		return zComboBox;
	}
	public void setzComboBox(JComboBox<String> zComboBox) {
		this.zComboBox = zComboBox;
	}
	public boolean isNotify() {
		return notify;
	}
	public void setNotify(boolean notify) {
		this.notify = notify;
	}
	public Thread getPainter() {
		return painter;
	}
	public void setPainter(Thread painter) {
		this.painter = painter;
	}
	public boolean isSerieChanged() {
		return serieChanged;
	}
	public void setSerieChanged(boolean serieChanged) {
		this.serieChanged = serieChanged;
	}
	public JPlot getJplot() {
		return jplot;
	}
	public void setJplot(JPlot jplot) {
		this.jplot = jplot;
	}
	

}
