package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.UFSC.GRIMA.IO.Conexao;

public class LoginEvent extends LoginWindow implements ActionListener {
	private int[] timeRange;
	private JFrame ower;
	private Conexao connection;
	private java.sql.Statement statement;
	private String dataBaseIP = "150.162.105.1";
	private String dataBase = "MT-Connect";
	private String user = "webcad";
	private String senha = "julio123";
	public LoginEvent(JFrame ower, int[] timeRange) {
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(cancelButton) ) {
			this.dispose();
		}
		if (e.getSource().equals(okButton)) {
			setConnection(new Conexao());
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection.setConn(dataBaseIP, dataBase, user, senha);
				setStatement(getConnection().getConn().createStatement());
			} catch (Exception x) {
				x.printStackTrace();
				JOptionPane.showMessageDialog(this, "Cannot connect to hte database.", "Coneection Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(pass.getText().getBytes());
				byte[] hashMd5 = md.digest();
				String pass1 = hashMd5.toString();
				statement.executeQuery("SELECT id,user FROM Users WHERE user='" + userField.getText() + "' AND password='" + pass1 + "';");
				
			}catch (Exception x) {
				x.printStackTrace();
				JOptionPane.showMessageDialog(this, "UserName or password invalid, please try again.", "Coneection Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
		}
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
