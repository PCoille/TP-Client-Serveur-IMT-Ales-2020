package server;

import java.io.BufferedReader;

import com.connecthive.swingui.ServerGui;

import api.IWsGui;
import common.Model;
import api.IModel;
import api.IServerController;
import api.IThreadedServer;

public class MServerRunner {
	private static BufferedReader userIn = null;
	private static IServerController servercontroler;
	private static IWsGui gui;

	private IThreadedServer threadedServer;

	public static void main(String[] args) {
		IModel model = Model.getInstance();
		servercontroler = new MServerControler();// new MServerSocket();
		IThreadedServer threadedServer = new MServerSocket();
		servercontroler.setServer(threadedServer);
		servercontroler.setModel(model);
		gui = new ServerGui();
		gui.setControler(servercontroler);


		// wClient.sendMessage("{'room':'1','occupation':'12'}");
		servercontroler.start();
		while (servercontroler.isRunning()) {
			try {
				Thread.sleep(100);
				// readKeyboard();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
