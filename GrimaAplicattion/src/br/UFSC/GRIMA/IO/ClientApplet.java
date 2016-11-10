package br.UFSC.GRIMA.IO;

import javax.swing.JApplet;

import br.UFSC.GRIMA.visual.LoginEvent;
import br.UFSC.GRIMA.visual.SetNameEvents;

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
			new SetNameEvents(main.getMainInterface());
		}
		catch(Exception e) {
			new LoginEvent();
		}
	}
	public static void main(String[] args) {
		(new ClientApplet()).init();
	}
}
