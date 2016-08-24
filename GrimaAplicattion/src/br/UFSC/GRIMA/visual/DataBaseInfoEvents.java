package br.UFSC.GRIMA.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.IO.SaveExecution;

public class DataBaseInfoEvents extends dataBaseInfoWindow implements ActionListener {
	private MainInterface mainInterface;
	public DataBaseInfoEvents(MainInterface mainInterface) {
		setMainInterface(mainInterface);
		//testar
		label1.setText("<html><body>(O banco de dados usado por esta aplicação é fornecido pelo laboratório responsável pelo seu projeto. Todo e qualquer monitoramento realizado pelo programa irá gerar uma tabela de dados que ficará armazenada no servidor indicado abaixo com as informações do usuário coerentes ao login realizado no inicio da aplicação)<br&gtcom HTML!</body></html>");
		okButton.addActionListener(this);
		SaveExecution save = mainInterface.getMainExecution().getIoControl().getSaveExecution();
		userPHP.setText(mainInterface.getMainExecution().getUserPHP() + "");
		userID.setText(mainInterface.getMainExecution().getUserIdPHP() + "");
		dataIP.setText(save.getDataBaseIP());
		database.setText(save.getDataBase());
		dataUser.setText(save.getUser());
		toFront();
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(okButton)) {
			this.dispose();
		}
	}
	public MainInterface getMainInterface() {
		return mainInterface;
	}
	public void setMainInterface(MainInterface mainInterface) {
		this.mainInterface = mainInterface;
	}

}
