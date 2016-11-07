package br.UFSC.GRIMA.IO;

import javax.swing.JApplet;

import br.UFSC.GRIMA.visual.LoginEvent;

public class ClientApplet extends JApplet
{
	public void init()
	{
		String usuario = null;
		int id = -1;
		try {
			id = Integer.parseInt(getParameter("userID"));
			usuario = getParameter("userName");
			MainExecution main = new MainExecution();
			main.setUserPHP(usuario);
			main.setUserIdPHP(id);
			main.getMainInterface().setEnabled(false);
			main.getIoControl().start();
			main.getMainInterface().setEnabled(true);
		}
		catch(Exception e) {
			new LoginEvent();
		}
	}
	public static void main(String[] args) {
		(new ClientApplet()).init();
	}
}
