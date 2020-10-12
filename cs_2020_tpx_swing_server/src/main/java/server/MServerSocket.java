package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import api.IConstants;
import api.IModel;
import api.IServerController;
import api.IServicetask;
import api.IThreadedServer;


//https://github.com/ggrandes/httpd/blob/master/src/main/java/org/javastack/httpd/HttpServer.java

/**
 * 
 * @author pfister - connecthive.com
 * 
 *
 */
public class MServerSocket implements IThreadedServer {

	// private IServerControler controler;
	private ServerThread serverThread;

	private ServerSocket socketEcoute;
	private List<MServiceTask> clientServices_ = new ArrayList<MServiceTask>();
	// a client has a triplet identifier (color) i order to handle
	// reconnections where the remote port changes while the identifier remains
	private List<int[]> clientIdentifiers = new ArrayList<int[]>();
	private boolean globalEnd;

	//private IWsGui gui;
	private IServerController controler;

	private IModel model;

	// private IBSControler bscontroler_;

	@Override
	public boolean registerCLient(IServicetask serviceTask, int[] id) {
		if (!registered(id)) {
			clientIdentifiers.add(id);
			// controler.consolideNewClient(serviceTask);
			return true;
		} else
			return false;
	}

	private boolean registered(int[] pid) {
		for (int[] id : clientIdentifiers)
			if (id[0] == pid[0] && id[1] == pid[1] && id[2] == pid[2])
				return true;
		return false;
	}

	@Override
	public void broadcast(IServicetask serviceTask, String data) {
		//MServiceTask current = (MServiceTask) serviceTask;
		for (MServiceTask client : clientServices_) {
			if (client != serviceTask)
				client.send(data);
		}
	}

	public void runServer() throws IOException {
		int port = IConstants.DEFAULT_PORT;// controler.getDefaultPort();
		socketEcoute = new ServerSocket(port);
		// NetIdentity ni = new NetIdentity();
		String host = socketEcoute.getInetAddress().toString();
		// String mac = ni.getMacAddress();
		// controler.setHost(mac, host, port);
		String log = "[serveur multiclient multisession] démarré sur :" + host + ":" + port;
		clog(log);
		int numClient = 0;
		while (!globalEnd) {
			clog("en attente d'une connexion");
			Socket socketService = socketEcoute.accept(); // bloquant ici
			MServiceTask clientTask = new MServiceTask(numClient++, this);
			clientTask.setSocketService(socketService);
			clientServices_.add(clientTask);
			clientTask.start();
		}
		socketEcoute.close();
		// Util.log(null, "arrÃªt du serveur");
	}

	public static void mai_n(String[] args) {
		MServerSocket threadedServer = new MServerSocket();
		try {
			threadedServer.runServer();
		} catch (Exception e) {
			// Util.log(e.toString());
		}
	}

	public void start() {
		try {
			runServer();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void endSession(IServicetask clientTask) {
		// ClientTask ct = (ClientTask) clientTask;
		clientServices_.remove(clientTask);
		// controler.notify(clientTask);
	}

	class ServerThread extends Thread {
		private boolean interrupted;

		@Override
		public void interrupt() {
			if (!isInterrupted()) {
				interrupted = true;
				// controler.log("server " + " will shut down");
				super.interrupt();
			}
		}

		@Override
		public boolean isInterrupted() {
			boolean result = super.isInterrupted();
			result = result || interrupted;
			return result;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(500);
				runServer();
			} catch (InterruptedException e) {
				// System.out.println("interrupted");
				return;
			} catch (SocketException e) {
				// e.printStackTrace();
				// controler.log("server is down");
				System.out.println("server is down");
				// controler.setServerDown();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				// controler.log("2 unable to start server");
				System.out.println("2 unable to start server");
				// controler.setSocketError();
				return;
			}
		}
	}

	@Override
	public void dispose() {
		disconnect();
	}

	@Override
	public void connect() {
		serverThread = new ServerThread();
		serverThread.start();
	}

	@Override
	public void disconnect() {
		for (MServiceTask clientTask : clientServices_)
			clientTask.closeSocket();
		for (MServiceTask clientTask : clientServices_)
			clientTask.interrupt();
		try {
			if (socketEcoute != null && socketEcoute.isBound()) {
				socketEcoute.close();
			}
		} catch (IOException e) {

		}
		if (serverThread != null)
			serverThread.interrupt();
	}

	@Override
	public void log(String log) {
		if (controler!=null)
		 controler.log(log);
		// System.out.println(log);
	}

	private void clog(String log) {
		// System.out.println(log);
		log(log);
	}

	@Override
	public void addFigure(String figure) {
		log("addfigure=" + figure);
	}

	@Override
	public void notify(IServicetask serviceTask) {
		log("notify servicetask=" + serviceTask.getIdNo());
	}

	@Override
	public List<int[]> getClientIds() {
		return clientIdentifiers;
	}

	public void logRequest(int id, String request) {
		log("request=" + request);
	}

	public void logStatus(Object serviceTask, int id, String status) {
		MServiceTask t = (MServiceTask) serviceTask;
		log("task status=" + t.getId() + "  " + status);
	}

	public List getClientTasks() {
		return clientServices_;
	}

	/*
	 * public void setBsControler(IBSControler bscontroler) { this.bscontroler_ =
	 * bscontroler; }
	 */

	public boolean isRunning() {
		return !globalEnd;
	}

	public void jAddMessage(String message) {
		controler.handleMessage(message);
	}
/*
	public void setGui(IWsGui iWsGui) {
		this.gui = iWsGui;
	}
*/
	public void jDisconnect() {
		log("disconnect");
	}

	public void setModel(IModel model) {
		this.model = model;
	}

	public IModel getModel() {
		return model;
	}


	@Override
	public void logStatus(IServicetask ServiceTask, int id, String status) {
		controler.logStatus(ServiceTask, id, status);
		
	}

	@Override
	public void setControler(IServerController serverControler) {
	    this.controler = serverControler;
		
	}

}
