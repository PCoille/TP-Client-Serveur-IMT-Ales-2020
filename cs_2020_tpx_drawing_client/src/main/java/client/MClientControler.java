package client;

import java.util.Random;

import api.IBSControler;

import api.IModel;
import api.ISessionClient;
import api.IWsGui;
import api.IClientControler;
import api.IConstants;

/**
 * 
 * @author pfister - connecthive.com
 * 
 *
 * 
 */
public class MClientControler extends Thread implements IClientControler {


	private ISessionClient sessionClient;
	private IWsGui gui;

	private static Random rand = new Random();

	private boolean endSession;
	private IModel model;
	private IBSControler bscontroler_nu;
	private int[] identifiers;

	// @Override
	public void setBsControler(IBSControler bscontroler) {
		this.bscontroler_nu = bscontroler;
	}

	private void clog(String m) {
		gui.log(m);
	}

	private void runClient(String uri) {
		gui.log("Socket client: open socket client at " + uri);
		sessionClient.setHost(uri, IConstants.DEFAULT_PORT);
		sessionClient.runClient(uri, IConstants.DEFAULT_PORT);
	}

	@Override
	public void setSessionClient(ISessionClient sc) {
		this.sessionClient = sc;
		sessionClient.setControler(this);
	}

	private boolean openConnection(String uri, int port) {
		try {
			// URI u = new URI(webSocketAddress);// + "/"+clientId);
			runClient(uri);
			return true;
		} catch (Exception e) {
			gui.log("unable to open socket");
			return false;
		}
	}

	@Override
	public void connect(String ip, int port) {
		openConnection(ip, port);
	}

	@Override
	public void sendMessage(String message) {
		sessionClient.sendMessage(message);
	}

	@Override
	public void run() {
		while (!isInterrupted() && checkConnection()) {
			// readKeyboard();
			// send();
		}
		disconnect(false);
	}

	@Override
	public boolean isRunning() {
		return (!isInterrupted() && checkConnection());
	}

	private void disconnect(boolean b) {
		gui.log("disconnect 2");
	}

	private boolean checkConnection() {
		return endSession == false;
	}

	@Override
	public void handleMessage(String message) {
		gui.log("messagehandler on client side - received " + message);
		gui.handleMessage(message);
	}

	@Override
	public void setGui(IWsGui gui) {
		this.gui = gui;
		int maxc = 180;
		int id1 = rand.nextInt(maxc);
		int id2 = rand.nextInt(maxc);
		int id3 = rand.nextInt(maxc);
		identifiers = new int[3];
		identifiers[0] = id1;
		identifiers[1] = id2;
		identifiers[2] = id3;
		gui.setId(identifiers);
	}

	@Override
	public void jAddMessage(String message) {
		clog("jAddMessage " + message);
		if (sessionClient != null && message != null) {
			try {
				Integer n = Integer.parseInt(message);
				String m = "{'foo':'1','bar':'" + n + "'}";
				sessionClient.sendMessage(message);
			} catch (Exception e) {
				clog("not an integer value");
			}
		}
	}

	@Override
	public void jDisconnect() {
		clog("jDisconnect ");
		sessionClient.disconnect();
	}

	@Override
	public void setModel(IModel model) {
		this.model = model;
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void disconnect() {
		gui.log("disconnect");
	}

	@Override
	public void log(String log) {
		gui.log(log);
	}

	@Override
	public void addFigure(String figure) {
		gui.log("addFigure " + figure);
	}

	@Override
	public void dispose() {
		gui.log("dispose");
	}

	@Override
	public void notifySocketClosed() {
		gui.log("SocketClosed");
	}

	@Override
	public void clearView() {
		gui.log("clearview");
	}

	@Override
	public void refreshFigure(String figure) {
	//	gui.log("refreshFigure "+figure);
		gui.addFigure(figure);
	}

	@Override
	public void noServerAvailable() {
		gui.log("noServerAvailable");
	}

	@Override
	public int[] getIdentifiers() {
		return identifiers;
	}
/*
	@Override
	public void refreshModel(String figure) {
		gui.refreshModel(figure);
		
	}*/

}
