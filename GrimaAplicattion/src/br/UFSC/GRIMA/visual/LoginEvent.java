package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.UFSC.GRIMA.IO.Conexao;
import br.UFSC.GRIMA.IO.MainExecution;

public class LoginEvent extends LoginWindow implements ActionListener {
	private int[] timeRange;
	private JFrame ower;
	private Conexao connection;
	private java.sql.Statement statement;
	private String dataBaseIP = "150.162.105.1";
	private String dataBase = "MT-Connect";
	private String user = "webcad";
	private String senha = "julio123";
	public LoginEvent() {
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.getRootPane().setDefaultButton(okButton);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
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
				JOptionPane.showMessageDialog(this, "Cannot connect to the database.", "Coneection Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				MessageDigest m=MessageDigest.getInstance("MD5");
				m.update(pass.getText().getBytes(),0,pass.getText().length());
				String str = (new BigInteger(1,m.digest())).toString(16);
				ResultSet rs = statement.executeQuery("SELECT id,user FROM Users WHERE user='" + userField.getText() + "' AND password='" + str + "';");
				if(rs.next()) {
					MainExecution main = new MainExecution();
					main.getMainInterface().setEnabled(false);
					try {
						main.setUserPHP(rs.getString("user"));
						main.setUserIdPHP(rs.getInt("id"));
					}
					catch(Exception x) {
						x.printStackTrace();
					}
					new SetNameEvents(main.getMainInterface());
					this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(this, "UserName or password invalid, please try again.", "Coneection Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
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
