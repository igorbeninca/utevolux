package br.UFSC.GRIMA.IO;

import javax.swing.JApplet;

import br.UFSC.GRIMA.visual.MainInterface;

public class ClientApplet extends JApplet
{
	public void init()
	{
		MainExecution main = new MainExecution();
		try {
			main.setUserIdPHP(Integer.parseInt(getParameter("userID")));
			main.setUserPHP(getParameter("userName"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		main.getMainInterface().setEnabled(false);
		main.getIoControl().start();
		main.getMainInterface().setEnabled(true);
	}
}
