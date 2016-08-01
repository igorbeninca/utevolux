package br.UFSC.GRIMA.IO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.application.entities.streams.WarningType;
import br.UFSC.GRIMA.dataStructure.Variable;


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
	private int overloadRegion = 60000;
	// Dados de conexao com o Banco de dados
	private Conexao connection;
	private java.sql.Statement statement;
	private int userId = 1;
	private String tableSerieNumber;

////////////////////////////////////Constructor/////////////////////////////////////////////////////////////
	public SaveExecution (IOControl ioControl) {
		setIoControl(ioControl);
		setBufControl(new BufControl(this));
		setThread(new Thread(this, "Save Execution"));
		setBuffer(new ArrayList<ArrayList<String>>());
		setSaveList(new ArrayList<Variable>());
		boolean connect = true;
		connect = connect && connectToDB();
		if(connect)
			connect = connect && createMonitoringTable();
		setConnected(connect);
	}
///////////////////////////////Methods///////////////////////////////////////////////////////////////////////////////
	public void start() {
//		if(connected)
//		//	thread.start();
//		else
//			JOptionPane.showMessageDialog(null, "Cannot connect to the DataBase service.", "Erro", JOptionPane.ERROR_MESSAGE);
	}
	public boolean connectToDB() {
		setConnection(new Conexao());
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection.setConn("150.162.105.1", "mtcTest", "webcad", "julio123");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean createMonitoringTable() {
		try {
			// encontrar ip de usuario
			statement = getConnection().getConn().createStatement();
			ResultSet rs = statement.executeQuery("select id from usuarios where usuario='Marcio' AND senha = '36250243';");
			rs.next();
			String id1 = rs.getString("id");
			//cria referencia da tabela de monitoramento no Header
			String time = new Date().toString();
			statement.executeUpdate("INSERT INTO MonitoringHeader (user, Timestamp, Observation) VALUES(" + id1 + ",'" + time + "', 'ESTOU COM FOME');");
			//encontra numero de serie da tabela criada
			rs = statement.executeQuery("select SerieNumber from MonitoringHeader where user= " + id1 + " AND Timestamp = '" + time + "';");
			rs.next();
			setTableSerieNumber("Z" + rs.getInt("SerieNumber"));
			//cria tabela de monitoramento
			statement.executeUpdate("CREATE TABLE " + tableSerieNumber +"(agent varchar(255), deviceStream varchar(255), componentStream varchar(255), variable varchar(255), value varchar(255), timestamp varchar(255));");
		} catch (Exception e) {
			e.printStackTrace();
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
				String query = "INSERT INTO " + tableSerieNumber + " VALUES ";
				for (int i = 0; i < registersToSave.size(); i++) {
					ArrayList<String> arg = registersToSave.get(i);
					query = query + "('" + arg.get(0) + "', '" + arg.get(1)
							+ "', '" + arg.get(2) + "', '" + arg.get(3)
							+ "', '" + arg.get(4) + "', '" + arg.get(5) + "')";
					if (i < registersToSave.size() - 1)
						query = query + ", ";
				}
				query = query + ";";
				try {
					statement.executeUpdate(query);
					setConnected(true);
				} catch (Exception e) {
					e.printStackTrace();
					for (int i = registersToSave.size() - 1; i <= 0; i--)
						buffer.add(0, registersToSave.remove(i));
					if (connected) {
						JOptionPane.showMessageDialog(null,
								"Connection with DataBase was interrupted.",
								"Erro", JOptionPane.ERROR_MESSAGE);
						setConnected(false);
					}
				}
			}
			long loop = System.currentTimeMillis() - getLoopTime();
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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

}
