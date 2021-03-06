package br.UFSC.GRIMA.IO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.application.entities.streams.WarningType;
import br.UFSC.GRIMA.dataStructure.Variable;
import br.UFSC.GRIMA.visual.MainInterface;


public class SaveExecution implements Runnable {
	private IOControl ioControl;
	private BufControl bufControl;
	private ArrayList<Variable> saveList;
	private boolean connected;
	private boolean overloaded;
	//parametros de controle de dados a serem escritos no banco de dados
	private ArrayList<ArrayList<String>>buffer;
	private Thread thread;
	private Long loopTime = System.currentTimeMillis();
	private int minSize = 1;
	private int maxSize = 10000;
	// Dados de conexao com o Banco de dados
	private Conexao connection;
	private java.sql.Statement statement;
	private String tableSerieNumber;
	private String dataBaseIP = "150.162.105.1";
	private String dataBase = "MT-Connect";
	private String user = "webcad";
	private String senha = "julio123";
	// Status de conexao
	private long databaseSlowLimit = 1000;
	private int status = ONLINE;
	public static int ONLINE = 0;
	public static int OFFLINE = 1;
	public static int SLOW = 2;

////////////////////////////////////Constructor/////////////////////////////////////////////////////////////
	public SaveExecution (IOControl ioControl) {
		setIoControl(ioControl);
		setBufControl(new BufControl(this));
		setThread(new Thread(this, "Save Execution"));
		setBuffer(new ArrayList<ArrayList<String>>());
		setSaveList(new ArrayList<Variable>());
	}
///////////////////////////////Methods///////////////////////////////////////////////////////////////////////////////
	public void start() {
		boolean connect = connectToDB();
		setConnected(connect);
		if(connected){
			setStatus(ONLINE);
		}
		if(connected)
			thread.start();
		else
			JOptionPane.showMessageDialog(null, "Cannot connect to the DataBase service.", "Erro", JOptionPane.ERROR_MESSAGE);
	}
	public boolean connectToDB() {
		setConnection(new Conexao());
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection.setConn(dataBaseIP, dataBase, user, senha);
			statement = getConnection().getConn().createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "Error at connect to the server " + dataBaseIP;
			ioControl.getController().getMainInterface().updateHistory("Database", msg);
			return false;
		}
		return true;
	}
////////////////////////Run Tasks/////////////////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		while (true) {
			if (buffer.size() > minSize) {
				// pega os registros a serem escritos
				ArrayList<ArrayList<String>> registersToSave = new ArrayList<ArrayList<String>>();
				for (int i = 0; (i < maxSize) && !buffer.isEmpty(); i++) {
					registersToSave.add(buffer.remove(0));
				}
				// constroi a msg
				String query = "INSERT INTO " + tableSerieNumber + "(agent,deviceStream,componentStream,variable,value,timestamp) VALUES ";
				for (int i = 0; i < registersToSave.size(); i++) {
					ArrayList<String> arg = registersToSave.get(i);
					query = query + "('" + arg.get(0) + "', '" + arg.get(1)
							+ "', '" + arg.get(2) + "', '" + arg.get(3)
							+ "', '" + arg.get(4) + "', '" + arg.get(5) + "')";
					if (i < registersToSave.size() - 1)
						query = query + ", ";
				}
				long time = System.currentTimeMillis();
				query = query + ";";
				try {
					statement.executeUpdate(query);
					setConnected(true);
					if((System.currentTimeMillis() - time)>databaseSlowLimit)
						setStatus(SLOW);
					else
						setStatus(ONLINE);
				} catch (Exception e) {
					e.printStackTrace();
					connectToDB();
					for (int i = registersToSave.size() - 1; i <= 0; i--)
						buffer.add(0, registersToSave.remove(i));
					if (connected) {
						setConnected(false);
						setStatus(OFFLINE);
					}
				}
			}
			long loop = System.currentTimeMillis() - getLoopTime();
			ioControl.getController().getMainInterface().setSaveExPing(loop);
			if (loop < 200) {
				try {
					Thread.sleep(200 - loop);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setLoopTime(System.currentTimeMillis());
		}
	}
///////////////////////////////Getters and Setters////////////////////////////////////////////////////////////////////
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public java.sql.Statement getStatement() {
		return statement;
	}
	public void setStatement(java.sql.Statement statement) {
		this.statement = statement;
	}
	public Conexao getConnection() {
		return connection;
	}
	public void setConnection(Conexao connection) {
		this.connection = connection;
	}
	public IOControl getIoControl() {
		return ioControl;
	}
	public void setIoControl(IOControl ioControl) {
		this.ioControl = ioControl;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	public ArrayList<ArrayList<String>> getBuffer() {
		return buffer;
	}
	public void setBuffer(ArrayList<ArrayList<String>> buffer) {
		this.buffer = buffer;
	}
	public ArrayList<Variable> getSaveList() {
		return saveList;
	}
	public void setSaveList(ArrayList<Variable> saveList) {
		this.saveList = saveList;
	}
	public String getTableSerieNumber() {
		return tableSerieNumber;
	}
	public void setTableSerieNumber(String tableSerieNumber) {
		this.tableSerieNumber = tableSerieNumber;
	}
	public int getMinSize() {
		return minSize;
	}
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public boolean isOverloaded() {
		return overloaded;
	}
	public void setOverloaded(boolean overloaded) {
		this.overloaded = overloaded;
	}
	public Long getLoopTime() {
		return loopTime;
	}
	public void setLoopTime(Long loopTime) {
		this.loopTime = loopTime;
	}
	public BufControl getBufControl() {
		return bufControl;
	}
	public void setBufControl(BufControl bufControl) {
		this.bufControl = bufControl;
	}
	public String getDataBaseIP() {
		return dataBaseIP;
	}
	public void setDataBaseIP(String dataBaseIP) {
		this.dataBaseIP = dataBaseIP;
	}
	public String getDataBase() {
		return dataBase;
	}
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		if(status == ONLINE)
			ioControl.getController().getMainInterface().setDatabaseStatus("Online", MainInterface.ONLINE);
		if(status == OFFLINE)
			ioControl.getController().getMainInterface().setDatabaseStatus("Connection Lost", MainInterface.OFFLINE);
		if(status == SLOW)
			ioControl.getController().getMainInterface().setDatabaseStatus("Connection Slow", MainInterface.WARNING);
		this.status = status;
	}
	public long getDatabaseSlowLimit() {
		return databaseSlowLimit;
	}
	public void setDatabaseSlowLimit(long databaseSlowLimit) {
		this.databaseSlowLimit = databaseSlowLimit;
	}

}
