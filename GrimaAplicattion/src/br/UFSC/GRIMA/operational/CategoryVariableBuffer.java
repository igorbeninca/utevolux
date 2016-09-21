package br.UFSC.GRIMA.operational;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;

import br.UFSC.GRIMA.dataStructure.Variable;

public class CategoryVariableBuffer implements SeriesChangeListener, ActionListener {
	private Variable variable;
	private CategoryMonitoringUnit categoryMonitoringUnit;
	private FrequencyMonitoingUnit frequencyMonitoingUnit;
	private TimeSeries dataSerie;
	private int categoriesInVariable;
	//////////panelComponents
	private JLabel typeLabel;
	private JTextField valueTextField;
	private JToggleButton displayButton;
/////////////////////////////////////constructor///////////////////////////////////////////////////////////////////
	public CategoryVariableBuffer(Variable variable, CategoryMonitoringUnit categoryMonitoringUnit) {
		setVariable(variable);
		setCategoryMonitoringUnit(categoryMonitoringUnit);
		categoryMonitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		if (variable.getName() != null) 
			setDataSerie(new TimeSeries(variable.getName()));
		else
			setDataSerie(new TimeSeries(variable.getDataItemID()));
		dataSerie.setNotify(false);
		for(int i = 0; i < variable.getDataSerie().getItemCount(); i++) 
			dataSerie.addOrUpdate(variable.getDataSerie().getDataItem(i));
		for (int i = 0; i < dataSerie.getItemCount(); i++) {
			if(dataSerie.getValue(i) != null)
				dataSerie.update(i, (double)categoryMonitoringUnit.getCategoryStrings().indexOf(variable.getCategoryStrings().get(dataSerie.getValue(i).intValue())));
		}
		variable.getDataSerie().addChangeListener(this);
	}
	public CategoryVariableBuffer(Variable variable, FrequencyMonitoingUnit frequencyMonitoringUnit) {
		setVariable(variable);
		setFrequencyMonitoingUnit(frequencyMonitoringUnit);
		frequencyMonitoringUnit.getPanelMonitoringSystem().getController().getIoControl().getLoadExecution().addToVariableList(variable);
		if (variable.getName() != null) 
			setDataSerie(new TimeSeries(variable.getName()));
		else
			setDataSerie(new TimeSeries(variable.getDataItemID()));
		dataSerie.setNotify(false);
		for(int i = 0; i < variable.getDataSerie().getItemCount(); i++) 
			dataSerie.addOrUpdate(variable.getDataSerie().getDataItem(i));
		for (int i = 0; i < dataSerie.getItemCount(); i++) {
			if(dataSerie.getValue(i) != null)
				dataSerie.update(i, (double)frequencyMonitoringUnit.getCategoryStrings().indexOf(variable.getCategoryStrings().get(dataSerie.getValue(i).intValue())));
		}
		variable.getDataSerie().addChangeListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(displayButton)) {
			if(categoryMonitoringUnit != null) {
				if (variable.getType() == 'i')
					displayButton.setSelected(false);
				else if (displayButton.isSelected())
					categoryMonitoringUnit.getChartDataset().addSeries(dataSerie);
				else
					categoryMonitoringUnit.getChartDataset().removeSeries(dataSerie);
			}
			else {
				if (variable.getType() == 'i')
					displayButton.setSelected(false);
			}
		}
	}
	@Override
	public void seriesChanged(SeriesChangeEvent e) {
		if ((variable.getType() == 'i')&&(dataSerie != null)) 
			setVariableToIrregular();
		if (!variable.getDataSerie().isEmpty()) {
			if (variable.getType() != 'i') 
				addToSerie(variable.getDataSerie().getDataItem(variable.getDataSerie().getItemCount() - 1));
			if(categoryMonitoringUnit != null) {
				if (categoryMonitoringUnit.getPlayPause() != null) {
					if(categoryMonitoringUnit.getPlayPause().isSelected())
						valueTextField.setText(variable.getLastValue());
				}
			}
			else {
				if (frequencyMonitoingUnit.getPlayPause() != null) {
					if(frequencyMonitoingUnit.getPlayPause().isSelected())
						valueTextField.setText(variable.getLastValue());
				}
			}
		}
	}
	public void addToSerie(TimeSeriesDataItem item) {
		try {
			TimeSeriesDataItem nItem = (TimeSeriesDataItem)item.clone();
			if(nItem.getValue() != null)
				dataSerie.addOrUpdate(nItem.getPeriod(), getCategoryPosition(variable.getCategoryStrings().get(nItem.getValue().intValue())));
			else
				dataSerie.addOrUpdate(nItem.getPeriod(), null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		///////////////Discart medium value/////////////////
		TimeSeries serie = dataSerie;
		if (serie.getItemCount() > 3) {
			if((serie.getValue(serie.getItemCount() - 1) == null)&&(serie.getValue(serie.getItemCount() - 2) == null)&&(serie.getValue(serie.getItemCount() - 3) == null) && (serie.getValue(serie.getItemCount() - 4) == null))
				serie.delete(serie.getItemCount()-3, serie.getItemCount()-3); /// deleta o pen�ltimo registro
			else if (serie.getValue(serie.getItemCount() - 1) != null) {
				if (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 2)) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 3))) && (serie.getValue(serie.getItemCount() - 1).equals(serie.getValue(serie.getItemCount() - 4))))
					serie.delete(serie.getItemCount()-3, serie.getItemCount()-3); /// deleta o pen�ltimo registro
			}
		}
		//////////////discart old values////////////////////////
		if (((variable.getType() == 'c')) && (serie.getItemCount() > 1)) {
			XMLGregorianCalendar iniTime =(XMLGregorianCalendar) variable.getComponent().getDevice().getAgent().getCreationTime().clone();
			int second = 0;
			int minute = 0;
			int hour = 0;
			if(categoryMonitoringUnit != null) {
				second = iniTime.getSecond() - categoryMonitoringUnit.getTimeRange()[2];
				minute = iniTime.getMinute() - categoryMonitoringUnit.getTimeRange()[1];
				hour = iniTime.getHour() - categoryMonitoringUnit.getTimeRange()[0];
			}
			else {
				second = iniTime.getSecond() - frequencyMonitoingUnit.getTimeRange()[2];
				minute = iniTime.getMinute() - frequencyMonitoingUnit.getTimeRange()[1];
				hour = iniTime.getHour() - frequencyMonitoingUnit.getTimeRange()[0];
			}
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
			iniTime.setTime(hour, minute, second);
			iniTime.setDay(day);
			iniTime.setMonth(month);
			iniTime.setYear(year);
			Millisecond inicialTime = new Millisecond(iniTime.toGregorianCalendar().getTime());
			while(serie.getItemCount() > 2) {
				if(inicialTime.compareTo(serie.getTimePeriod(0)) <= 0)
					break;
				if(inicialTime.compareTo(serie.getTimePeriod(0)) > 0) {
					if (inicialTime.compareTo(serie.getTimePeriod(1)) < 0) {
						if ((variable.getType() == '1') && (serie.getValue(0) != null) && (serie.getValue(1) != null)) {
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
		}
		ArrayList<Number> categoriesInVariable = new ArrayList<Number>();
		for(int i = 0; i < dataSerie.getItemCount(); i++) {
			if(!categoriesInVariable.contains(dataSerie.getValue(i)))
				categoriesInVariable.add(dataSerie.getValue(i));
		}
		if(categoriesInVariable.size() < getCategoriesInVariable()) {
			if(categoryMonitoringUnit != null)
				categoryMonitoringUnit.categoryRemove();
			else
				frequencyMonitoingUnit.categoryRemove();
		}
		setCategoriesInVariable(categoriesInVariable.size());
		dataSerie.setNotify(true);
		dataSerie.setNotify(false);
	}
	public int getCategoryPosition(String s) {
		if(categoryMonitoringUnit != null) {
			if (!categoryMonitoringUnit.getCategoryStrings().contains(s))
				categoryMonitoringUnit.categoryAdd(s);
			return categoryMonitoringUnit.getCategoryStrings().indexOf(s);
		}
		else {
			if (!frequencyMonitoingUnit.getCategoryStrings().contains(s))
				frequencyMonitoingUnit.categoryAdd(s);
			return frequencyMonitoingUnit.getCategoryStrings().indexOf(s);
		}
	}
	public void setNewCategoryData(int[] correction) {
		if(variable.getType() != 'i') {
			try {
				for(int i = 0; i < dataSerie.getItemCount(); i++) {
					if(dataSerie.getValue(i) != null)
						dataSerie.update(i, (double) (dataSerie.getValue(i).intValue() - correction[dataSerie.getValue(i).intValue()]));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void setVariableToIrregular() {
		if(categoryMonitoringUnit != null)
			categoryMonitoringUnit.getChartDataset().removeSeries(dataSerie);
		dataSerie = null;
		typeLabel.setIcon(new ImageIcon(getClass().getResource("/br/UFSC/GRIMA/images/irregularTypeIcon.gif")));
		typeLabel.setToolTipText("Irregular Variable Type: this variable show values that is neither numeric nor category variable Type.");
		displayButton.setSelected(false);
		if(categoryMonitoringUnit != null) {
			if(categoryMonitoringUnit.getPanelType() == 'c')
				categoryMonitoringUnit.categoryRemove();
		}
		else {
			if(frequencyMonitoingUnit.getPanelType() == 'c')
				frequencyMonitoingUnit.categoryRemove();
		}
	}
//////////////////////////////////////Getters and Setters////////////////////////////////////////////////////////
	public TimeSeries getDataSerie() {
		return dataSerie;
	}

	public void setDataSerie(TimeSeries dataSerie) {
		this.dataSerie = dataSerie;
	}
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	public CategoryMonitoringUnit getCategoryMonitoringUnit() {
		return categoryMonitoringUnit;
	}
	public void setCategoryMonitoringUnit(CategoryMonitoringUnit categoryMonitoringUnit) {
		this.categoryMonitoringUnit = categoryMonitoringUnit;
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

	public JToggleButton getDisplayButton() {
		return displayButton;
	}

	public void setDisplayButton(JToggleButton displayButton) {
		this.displayButton = displayButton;
	}
	public int getCategoriesInVariable() {
		return categoriesInVariable;
	}
	public void setCategoriesInVariable(int categoriesInVariable) {
		this.categoriesInVariable = categoriesInVariable;
	}
	public FrequencyMonitoingUnit getFrequencyMonitoingUnit() {
		return frequencyMonitoingUnit;
	}
	public void setFrequencyMonitoingUnit(FrequencyMonitoingUnit frequencyMonitoingUnit) {
		this.frequencyMonitoingUnit = frequencyMonitoingUnit;
	}
}
