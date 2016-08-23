package br.UFSC.GRIMA.IO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;


public class BufControl implements Runnable {
	private SaveExecution SaveExecution;
	private Thread thread;
	private ArrayList<Long>loopEstim;
	private long currentTime;
	private Random randomSource;
	private boolean sent;
	// parametros do controlador
	public static double T5Est = 1.5;
	private double kc = 0;
	private double z0 = 0;
	private long Td = 0;
	private int minFaixa = 15000;
	private int memSetPoint = 20000;
	
	//variaveis do controle
	private double yk = 0;
	private double ek = 0;
	private double ek1 = 0;
	private double uk = 0;
	private double uk1 = 0;
	//mostra valores para analise
//	TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
//	TimeSeries y = new TimeSeries("Y");
//	TimeSeries u = new TimeSeries("U");
//	TimeSeries er = new TimeSeries("E");
	
///////////////////////////////Constructor/////////////////////////////////////////////////////////
	public BufControl(SaveExecution saveExecution) {
//		timeSeriesCollection.addSeries(y);
//		timeSeriesCollection.addSeries(u);
//		timeSeriesCollection.addSeries(er);
//		JFreeChart chart = ChartFactory.createTimeSeriesChart("Buffer Control", "time", "", timeSeriesCollection);
//		ChartPanel panel = new ChartPanel(chart);
//		JFrame frame = new JFrame("Buffer Control");
		loopEstim = new ArrayList<Long>();
//		frame.add(panel);
//		frame.pack();
//		frame.setVisible(true);
		setSaveExecution(saveExecution);
		randomSource = new Random();
		setThread(new Thread(this));
	}
///////////////////////////////Methods////////////////////////////////////////////////////////////
	public void start() {
		thread.start();
	}
	public void setControlValues() {
		long sum = 0;
		for(int i = 0; i < 5; i++) 
			sum = sum + loopEstim.get(i);
		double tmed = ((double)sum)/5000;//tempo de loop estimado em seg
		Td = (long)(tmed*T5Est*1000);
		z0 = 0.893;
		kc = 0.5/tmed*(-1);
		System.out.println("Valores do controlador calculados: Td = " + Td + "; kc = " + kc + "; z0 = " + z0);
	}
///////////////////////////////Run Tasks/////////////////////////////////////////////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (kc == 0) {
				if(SaveExecution.getIoControl().getLoadExecution().getLoopTime() != 0) {
					if(loopEstim.isEmpty())
						loopEstim.add(SaveExecution.getIoControl().getLoadExecution().getLoopTime());
					else if(SaveExecution.getIoControl().getLoadExecution().getLoopTime() != loopEstim.get(loopEstim.size() - 1)) 
						loopEstim.add(SaveExecution.getIoControl().getLoadExecution().getLoopTime());
				}
				if(loopEstim.size() == 5)
					setControlValues();
				try {
					thread.sleep(50);
				} catch(Exception e) {
					e.printStackTrace();
				}
				currentTime = System.currentTimeMillis();
			} 
			else {
				//leitura
				yk = SaveExecution.getBuffer().size();
				//calculo do controle
				ek = memSetPoint - yk;
				uk = uk1 + kc*(ek - z0*ek1);
				//anti-windup e limitador de faixa de operacao
				if((uk < 0) || (yk < minFaixa))
					uk = 0;
				//aplica controle
				int valuesToDiscard = (int)uk;
				for(int i = 0; i < valuesToDiscard; i++) {
					int index = randomSource.nextInt((int)yk - i); //remove n valores aleatoriamente na lista
					SaveExecution.getBuffer().remove(index);
				}
				//add valores para monitoramento
//				y.add(new TimeSeriesDataItem(new Millisecond(), yk));
//				u.add(new TimeSeriesDataItem(new Millisecond(), uk));
//				er.add(new TimeSeriesDataItem(new Millisecond(), ek));
				//atualiza variaveis
				ek1 = ek;
				uk1 = uk;
				if((yk > minFaixa) && !sent) {
					String msg = "Warning: The Array of values to save in database is currently overloaded.";
					SaveExecution.getIoControl().getController().getMainInterface().updateHistory("Database", msg);
					JOptionPane.showMessageDialog(null,"Bad communication with the database. The system is overloaded and will begin to discard values.",	"Erro", JOptionPane.ERROR_MESSAGE);
					sent = true;
				}
				long loop = System.currentTimeMillis() - currentTime;
				if (loop < Td) { // espera Td milisegundos
					try {
						Thread.sleep(Td - loop);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setCurrentTime(System.currentTimeMillis());
			}
		}
	}
//////////////////////////////////Getters and Setters///////////////////////////////////////////////////
	public SaveExecution getSaveExecution() {
		return SaveExecution;
	}
	public void setSaveExecution(SaveExecution saveExecution) {
		SaveExecution = saveExecution;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public ArrayList<Long> getLoopEstim() {
		return loopEstim;
	}
	public void setLoopEstim(ArrayList<Long> loopEstim) {
		this.loopEstim = loopEstim;
	}
	public long getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	public double getKc() {
		return kc;
	}
	public void setKc(double kc) {
		this.kc = kc;
	}
	public double getZ0() {
		return z0;
	}
	public void setZ0(double z0) {
		this.z0 = z0;
	}
	public long getTd() {
		return Td;
	}
	public void setTd(long td) {
		Td = td;
	}
	public int getMinFaixa() {
		return minFaixa;
	}
	public void setMinFaixa(int minFaixa) {
		this.minFaixa = minFaixa;
	}
	public int getMemSetPoint() {
		return memSetPoint;
	}
	public void setMemSetPoint(int memSetPoint) {
		this.memSetPoint = memSetPoint;
	}
}
