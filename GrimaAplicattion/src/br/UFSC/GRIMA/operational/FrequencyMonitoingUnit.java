package br.UFSC.GRIMA.operational;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xml.PieDatasetHandler;
import org.jfree.date.DateUtilities;
import org.jfree.util.TableOrder;

import br.UFSC.GRIMA.dataStructure.Variable;

public class FrequencyMonitoingUnit extends MonitoringUnit implements Runnable {
	private ArrayList<String> categoryStrings;
	private ArrayList<CategoryVariableBuffer> categoryVariableBuffers;
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;
	private JPanel chartPanel;
	private boolean freeze;
	private Thread thread;
///////////////////////////////////constructor////////////////////////////////////////////
	public FrequencyMonitoingUnit(String name,PanelMonitoringSystem panelMonitoringSystem, int[] timeRange,	String chartType, ArrayList<Variable> variables, char panelType) {
		super(name, panelMonitoringSystem, timeRange, chartType, variables, panelType);
		// TODO Auto-generated constructor stub
	}
/////////////////////////////////////////////////////Methods///////////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed2(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freezeChart(boolean freeze) {
		this.freeze = freeze;
	}

	@Override
	public void addVariableControl(Variable variable, JPanel container,	int line, JLabel typeLabel, JTextField valueField) {
		// TODO Auto-generated method stub
		if(categoryStrings == null) {
			setCategoryStrings(new ArrayList<String>());
			for(int i = 0; i < getVariables().size(); i++) {
				if (getVariables().get(i).getType() != 'i') {
					for(int j = 0; j < getVariables().get(i).getCategoryStrings().size(); j++) {
						if (!this.categoryStrings.contains(getVariables().get(i).getCategoryStrings().get(j)))
							categoryStrings.add(getVariables().get(i).getCategoryStrings().get(j));
					}
				}
			}
		}
		if(categoryVariableBuffers == null)
			setCategoryVariableBuffers(new ArrayList<CategoryVariableBuffer>());
		CategoryVariableBuffer categoryVariableBuffer = new CategoryVariableBuffer(variable, this);
		categoryVariableBuffers.add(categoryVariableBuffer);
		JToggleButton display = new JToggleButton();
		display.setSelected(true);
		container.add(display, new GridBagConstraints(5, line, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
		categoryVariableBuffer.setTypeLabel(typeLabel);
		categoryVariableBuffer.setValueTextField(valueField);
		categoryVariableBuffer.setDisplayButton(display);
		display.addActionListener(categoryVariableBuffer);
	}

	@Override
	public void initAditionalPanelElements() {
		// TODO Auto-generated method stub
		thread = new Thread(this);
		thread.setName("Frequency Panel Runnable");
		thread.start();
	}

	@Override
	public void refreshChart2() {
		// TODO Auto-generated method stub
		String[] s = new String[categoryStrings.size()];
		s= categoryStrings.toArray(s);
		((GridBagLayout)getMonitoringPanel().getLayout()).rowHeights[2] = getMinimumHeight();
		getMonitoringPanel().setPreferredSize(new Dimension(getMinimumWhidth(),  (int) Math.round(getMonitoringPanel().getPreferredSize().getHeight())));
		setVisible(isVisible());
		getMonitoringPanel().setVisible(isVisible());
	}

	@Override
	public void destroyPanelInstance() {
		try {
			thread.interrupt();
		}catch(Exception e){}
		try {
			for(int i = 0; i < categoryVariableBuffers.size(); i++) 
				getVariables().get(i).getDataSerie().removeChangeListener(categoryVariableBuffers.get(i));
		}catch(Exception e){}
		setCategoryVariableBuffers(null);
		setCategoryStrings(null);
		setChartPanel(null);
		setChart(null);
		setDataset(null);
		setMonitoringPanel(null);
		setPlayPause(null);
	}
	public void categoryAdd(String str) {
		categoryStrings.add(str);
		refreshChart();
	}
	public void categoryRemove() {
		boolean[] existence = new boolean[categoryStrings.size()];
		for(int i = 0; i < categoryVariableBuffers.size();i++) {
			if (getVariables().get(i).getType() != 'i') {
				for (int j = 0; j < categoryVariableBuffers.get(i).getDataSerie().getItemCount(); j++) {
					if(categoryVariableBuffers.get(i).getDataSerie().getValue(j) != null)
						existence[categoryVariableBuffers.get(i).getDataSerie().getValue(j).intValue()] = true;
				}
			}
		}
		boolean noChange = true;
		for (int j = 0; j < existence.length; j++) 
			noChange = noChange && existence[j];
		if (!noChange) {
			int[] correction = new int[existence.length];
			ArrayList<String>newCategoryStrings = new ArrayList<String>();
			correction[0] = 0;
			for(int j = 0; j < existence.length; j++) {
				if (j != 0)
					correction[j] = correction[j - 1];
				if(existence[j]) 
					newCategoryStrings.add(categoryStrings.get(j));
				else {
					correction[j]++;
				}
			}
			setCategoryStrings(newCategoryStrings);
			for (int i = 0; i < categoryVariableBuffers.size(); i++)
				categoryVariableBuffers.get(i).setNewCategoryData(correction);
		}
		refreshChart();
	}
	public int getDisplaySize() {
		int cont = 0;
		if(categoryVariableBuffers != null) {
			for(int i = 0; i < categoryVariableBuffers.size(); i++) {
				if(categoryVariableBuffers.get(i).getDisplayButton().isSelected() && (categoryVariableBuffers.get(i).getVariable().getType() != 'i'))
					cont++;
			}
		}
		return cont;
	}
////////////////////////////////////////////////////////////Getters and Setters////////////////////////////////////////////////////////////////////////////////
	public ArrayList<String> getCategoryStrings() {
		return categoryStrings;
	}
	public void setCategoryStrings(ArrayList<String> categoryStrings) {
		this.categoryStrings = categoryStrings;
	}
	public ArrayList<CategoryVariableBuffer> getCategoryVariableBuffers() {
		return categoryVariableBuffers;
	}
	public void setCategoryVariableBuffers(ArrayList<CategoryVariableBuffer> categoryVariableBuffers) {
		this.categoryVariableBuffers = categoryVariableBuffers;
	}
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public JPanel getChartPanel() {
		return chartPanel;
	}
	public void setChartPanel(JPanel chartPanel) {
		this.chartPanel = chartPanel;
	}
	public DefaultCategoryDataset getDataset() {
		return dataset;
	}
	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}
/////////////////////////////////////////////////////////////////////run tasks//////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			long time = System.currentTimeMillis();
			if(!freeze && (getDisplaySize() != 0)) {
				int col = 0;
				int line = 0;
				int limit = (int)Math.ceil((Math.sqrt(getDisplaySize()))); 
				JPanel sub = new JPanel();
				//make cells to grow
				double[] ColWeigths = new double[limit];
				for(int i = 0; i < ColWeigths.length; i++)
					ColWeigths[i] = 1.0;
				double[] rowWeigths = new double[(int)Math.ceil((double)getDisplaySize()/limit)];
				for(int i = 0; i < rowWeigths.length; i++)
					rowWeigths[i] = 1.0;
				
				sub.setLayout(new GridBagLayout());
				((GridBagLayout)sub.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)sub.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)sub.getLayout()).columnWeights = ColWeigths;
				((GridBagLayout)sub.getLayout()).rowWeights = rowWeigths;
				for(int i = 0; i < categoryVariableBuffers.size(); i++) {
					if(categoryVariableBuffers.get(i).getDisplayButton().isSelected() && (categoryVariableBuffers.get(i).getVariable().getType() != 'i')) {
						DefaultPieDataset data = new DefaultPieDataset();
						JFreeChart cht = ChartFactory.createPieChart(categoryVariableBuffers.get(i).getVariable().getValidName(), data, false, true, false);
						for(int j = 0; j < categoryVariableBuffers.get(i).getDataSerie().getItemCount() - 1; j++) {
							if(categoryVariableBuffers.get(i).getDataSerie().getValue(j) == null)
								continue;
							String category = categoryStrings.get((categoryVariableBuffers.get(i).getDataSerie().getValue(j)).intValue());
							double interval = ((double)(categoryVariableBuffers.get(i).getDataSerie().getTimePeriod(j + 1).getFirstMillisecond() - categoryVariableBuffers.get(i).getDataSerie().getTimePeriod(j).getFirstMillisecond())/1000);
							try {
								data.setValue(category, interval + data.getValue(category).doubleValue());
							}catch(Exception e) {
								data.setValue(category, interval);
							}
						}
						ChartPanel pan = new ChartPanel(cht);
						sub.add(pan, new GridBagConstraints(col, line, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5), 0, 0));
						if(col == limit - 1)  {
							col = 0;
							line++;
						}
						else {
							col++;
						}
					}
				}
				if(chartPanel != null)
					getMonitoringPanel().remove(chartPanel);
				getMonitoringPanel().add(sub, new GridBagConstraints(0, 2, 6, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				setChartPanel(sub);
			}
			else {
				if(chartPanel != null)
					getMonitoringPanel().remove(chartPanel);
			}
			long performed = System.currentTimeMillis() - time;
			if(performed < 1000) {
				try {
					thread.sleep(1000 - performed);
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
