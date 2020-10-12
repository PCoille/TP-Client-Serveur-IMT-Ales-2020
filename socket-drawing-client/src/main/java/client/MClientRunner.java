package client;



import api.IModel;
import api.ISessionClient;

import com.connecthive.swingui.draw2d.DrawApp;

import api.IClientControler;
import api.IWsGui;
import common.Model;

public class MClientRunner {

	private static IClientControler controler;
	private static IWsGui gui;
	private static ISessionClient sessionClient_;
	
	public static void main(String[] args) {
	    IModel model = Model.getInstance();
	    controler = new MClientControler();
	    sessionClient_ = new MClientSocket();//new ClientSocket(uri);
		controler.setModel(model);
		controler.setSessionClient(sessionClient_);
	    gui = new DrawApp();//ClientGui();

		gui.setup();
	    gui.setControler(controler);
	   
	    controler.setGui(gui);

		//wClient.sendMessage("{'room':'1','occupation':'12'}");
		controler.start();
		while (controler.isRunning()) {
			try {
				Thread.sleep(100);
			//	readKeyboard();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	



}
