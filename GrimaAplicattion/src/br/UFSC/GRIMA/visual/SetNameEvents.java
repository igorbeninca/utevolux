package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.UFSC.GRIMA.IO.Conexao;
import br.UFSC.GRIMA.IO.IOControl;
import br.UFSC.GRIMA.IO.MainExecution;

public class SetNameEvents extends SetNameWindow implements ActionListener {
	private MainInterface mainInterface;
	private Conexao connection;
	private java.sql.Statement statement;
	private String dataBaseIP = "150.162.105.1";
	private String dataBase = "MT-Connect";
	private String user = "webcad";
	private String senha = "julio123";
	public SetNameEvents(MainInterface mainInterface) {
		super();
		setConnection(new Conexao());
		setMainInterface(mainInterface);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(okButton)) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection.setConn(dataBaseIP, dataBase, user, senha);
				setStatement(getConnection().getConn().createStatement());
			} catch (Exception x) {
				x.printStackTrace();
				JOptionPane.showMessageDialog(this, "Cannot connect to the database.", "Connection Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			boolean tableCreated = createMonitoringTable();
			if(!tableCreated)
				return;
			else {
				mainInterface.getMainExecution().getIoControl().start();
				mainInterface.setEnabled(true);
				this.dispose();
			}
		}
		else if(e.getSource().equals(cancelButton)) {
			this.dispose();
			mainInterface.dispose();
		}
	}
	public boolean createMonitoringTable() {
		IOControl ioControl = mainInterface.getMainExecution().getIoControl();
		try {
			if(tableName.getText().equals("")) {
				tableName.setText("New_Project");
			}
			int id1 = ioControl.getController().getUserIdPHP();
			//cria referencia da tabela de monitoramento no Header
			String time = new Date().toString();
			mainInterface.getMainExecution().setTableName(tableName.getText());
			mainInterface.getMainExecution().setObservation(observation.getText());
			statement.executeUpdate("INSERT INTO MonitoringHeader (user, Timestamp, projectName,  Observation) VALUES(" + id1 + ",'" + time + "', '" + tableName.getText() + "', '" + observation.getText() + "');");
			//encontra numero de serie da tabela criada
			ResultSet rs = statement.executeQuery("select SerieNumber from MonitoringHeader where user= " + id1 + " AND Timestamp = '" + time + "';");
			rs.next();
			ioControl.setTableName("Z" + rs.getInt("SerieNumber"));
			//cria tabela de monitoramento
			statement.executeUpdate("CREATE TABLE " + ioControl.getTableName() +"(agent varchar(255), deviceStream varchar(255), componentStream varchar(255), variable varchar(255), value varchar(255), timestamp varchar(255));");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Cannot comunicate with the DataBase.","Erro", JOptionPane.ERROR_MESSAGE);
			String msg = "Communication lost with the DataBase " + dataBaseIP;
			ioControl.getController().getMainInterface().updateHistory("Database", msg);
			e.printStackTrace();
			return false;
		}
		String msg = "Successfully connected to the server " + dataBaseIP + "\n Table Created: " + ioControl.getTableName();
		ioControl.getController().getMainInterface().updateHistory("Database", msg);
		return true;
	}
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}
	public Conexao getConnection() {
		return connection;
	}
	public void setConnection(Conexao connection) {
		this.connection = connection;
	}
	public java.sql.Statement getStatement() {
		return statement;
	}
	public void setStatement(java.sql.Statement statement) {
		this.statement = statement;
	}
	
}
