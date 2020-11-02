


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author pfister - connecthive.com initial api and implementation
 *
 */
public class ClientSocket {

	private static final boolean LOG = true;

	private static final int DEFAULT_PORT = 8009;
	private static final String DEFAULT_HOST = "164.132.226.205";//tranquil-shelf-73723.herokuapp.com";

	// private IModel model;
	private String serverName = DEFAULT_HOST;
	private int serverPort = DEFAULT_PORT;
	private String response;
	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket_;
	private SessionThread sessionTask;
	private String host;
	private boolean pending;

	private static Random rand = new Random();

//	private IClientControler controler;
	// private IWsGui gui;
	private boolean keepConnection_ = true; // by default

	private List<String> keywords = new ArrayList<String>();

	private BufferedReader userIn;

	private KeyboardThread keyboardTask;

	public ClientSocket() {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		// this.host_ = uri;

	}

	private void setHost(String uri, int port) {
		this.serverName = uri;
		this.serverPort = port;
	}


	private boolean checkConnection() {
		boolean result = isConnected();
		if (!result)
			viewClearConnexion();// error("plus de connexion");
		return result;
	}

	private void openSocket() throws UnknownHostException, IOException {
		if (isConnected()) {
			info("déjà connecté");
			return;
		}
		if (serverPort == -1)
			throw new IOException("port error");

		if (serverName == null || serverName.equals("-1"))
			throw new IOException("host error");

		try {
			clientSocket_ = new Socket(serverName, serverPort);
			if (LOG)
				clog("success for " + serverName + "." + serverPort);
			// pendingSessions.remove(this);
			pending = false;
			host = clientSocket_.getLocalAddress().getHostAddress();
			viewSetLocal(host, serverPort);// port);

		} catch (ConnectException e) {
			if (pending) { // FP170325
				error("No connection " + e.toString());// + e.toString());
				error("No server: " + serverName + "-" + serverPort);
				noServerAvailable();
				if (LOG)
					clog("No server: " + serverName + "-" + serverPort);
				in = null;
				out = null;
				removePendingSession();
				sessionTask.interrupt();
				throw new IOException("connection error");
			}
		}
		in = new BufferedReader(new InputStreamReader(clientSocket_.getInputStream()));
		out = new PrintWriter(clientSocket_.getOutputStream(), true); // autoflush

		sendIdentification();
		info("Connected to the server");
	}

	private void sendIdentification() {
		String ids = "";
		int[] id = getIdentifiers();
		for (int i : id) {
			ids += Integer.toString(i);
			ids += " ";
		}
		sendCommand("Helo " + ids.trim());
	}

	private void removePendingSession() {
		pending = false;
		/*
		 * try { SessionThread pending = pendingSessions.get(0);
		 * pending.abortConnection(); pending.interrupt(); pending = null; // FP170325 }
		 * catch (Exception e) {
		 * 
		 * } pendingSessions.clear();
		 */
	}

	private void closeSocket(boolean verbose) {
		if (!isConnected()) {
			if (verbose)
				info("session allready closed");
			return;
		}
		info("closing socket");

		if (clientSocket_ != null)
			try {
				clientSocket_.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (in != null)
			try {
				in.close();
				in = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (out != null) {
			out.close();
			out = null;
		}
		notifySocketClosed();

	}

	private boolean doAddFigure() {
		for (String kw : keywords) {
			if (response.startsWith(kw)) {
				addFigure();
				return true;
			}
		}
		return false;
	}
	

	private void handleServerResponse() {
		if (response == null) {
			info("session ended");
			closeSocket(true);
			return;
		} else {
			response = response.trim();
			if (response.contains("STOP-SERVER OK"))
				info("server will shut down");
			else if (!doAddFigure())
				doAddCmd();
				//clean();
		}
		viewRefreshModel();
	}




	private void addFigure() {
		if (LOG)
			clog("figure: [" + response + "]");
		addtoNetCommands(response);
		viewRefresh();
	}
	
	private void doAddCmd() {
		if (LOG)
			clog("cmd: [" + response + "]");
	}


	private void readServer() {
		response = null;
		try {
			response = in.readLine(); // bloquant - attendre la réponse du
										// serveur
			if (LOG)
				clog("received " + response);

		} catch (IOException e) {
			info("socket closed");
			closeSocket(false);
			return;
		} catch (Exception e) {
			error("(2) while read response (" + e.toString() + ")");
			closeSocket(false);
			return;
		}
		if (response == null) {
			info("connection end");
			closeSocket(false);
			return;
		}
	}

	private static void delay(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getResponse() {
		return response;
	}

	private void disconnect(boolean verbose) {
		if (isConnected()) {
			closeSocket(verbose);
		} else if (verbose)
			clog("allready disconnected !");
		if (sessionTask != null && !sessionTask.isInterrupted())
			sessionTask.interrupt();
	}

	private boolean connect(String host, int port) {
		setHost(host,port);
		if (isConnected()) {
			if (LOG)
				clog("allready connected !");
			return true;
		}
		if (pending)
			pending = false;
		sessionTask = new SessionThread(host, port);
		sessionTask.start();
		
		keyboardTask = new KeyboardThread();
		keyboardTask.start();
		
		
		// pendingSessions.add(session);
		pending = true;
		delay(100);
		return isConnected();
	}

	private boolean isConnected() {
		boolean result = clientSocket_ != null && !clientSocket_.isClosed() && clientSocket_.isConnected();
		if (!result) {
			if (LOG) {
				if (clientSocket_ == null)
					clog("clientSocket == null");
				if (clientSocket_ != null && clientSocket_.isClosed())
					clog("clientSocket.isClosed()");
				if (clientSocket_ != null && !clientSocket_.isConnected())
					clog("!clientSocket.isConnected()");
			}
		}
		if (LOG && result)
			clog("connected ");
		return result;
	}

	private boolean openConnection() {
		try {
			openSocket();
			return true;
		} catch (UnknownHostException e) {
			error("Unknown server: " + e.getMessage());
		} catch (IOException e) {
			// error("Connection error");
		} catch (Exception e) {
			error("Unknown error) " + e.toString());
		}
		return false;
	}

	private boolean sendCommand(String cmd) {
		if (cmd != null && !cmd.isEmpty()) {
			// info("send " + serverName + ":" + serverPort + "[" + cmd + "]");
			if (!isConnected())
				connect(serverName, serverPort);
			if (isConnected()) {
				out.println(cmd);
				if (!keepConnection_) {
					soonCloseConnection();
				}
				return true;
			} else
				error("no session");
		} else
			clog("command is empty !");
		return false;
	}

	private void soonCloseConnection() {
		clog("will close connection");
		new Thread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					return;
				}
				clog("closing connection after 5s");
				disconnect(true);
			}
		}).start();
	}

	private void readKeyboard() {
		String theLine;
		try {
			theLine = userIn.readLine().trim();
			out.println(theLine);
			if (theLine.equals("quit")) {
				System.out.println("le serveur va terminer ma session");
				// endSession = true;
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	class SessionThread extends Thread {

		private boolean interrupted;

		public SessionThread(String host, int port) {
			if (LOG)
				clog("session thread, try to connect " + host + "." + port);
			if (isConnected()) {
				clog("should not happen, allready connected !");
				disconnect(true);
			}
			serverName = host;
			serverPort = port;
		}

		@Override
		public void interrupt() {
			if (!isInterrupted()) {
				interrupted = true;
				// clog("connection " + serverName + " " + serverPort + " is interrupted");
				super.interrupt();
			}
		}

		@Override
		public boolean isInterrupted() {
			boolean result = super.isInterrupted();
			result = result || interrupted;
			return result;
		}


		public void run() {
			if (openConnection()) {
				while (!isInterrupted() && checkConnection()) {
					// readKeyboard();
					readServer();
					handleServerResponse();
				}
				disconnect(false);
			}
		}

	}

	class KeyboardThread extends Thread {

		private boolean interrupted;

		public KeyboardThread() {
			if (LOG)
				clog("KeyboardThread thread");

		}

		@Override
		public void interrupt() {
			if (!isInterrupted()) {
				interrupted = true;
				// clog("connection " + serverName + " " + serverPort + " is interrupted");
				super.interrupt();
			}
		}

		@Override
		public boolean isInterrupted() {
			boolean result = super.isInterrupted();
			result = result || interrupted;
			return result;
		}

		public void run() {
			while(true)
			   readKeyboard();
		}

	}

	private void addtoNetCommands(String cmd) { // kk300
		//clog("cmd=" + cmd);
		/*
		 * IModel model = getModel(); if (model == null) return; synchronized
		 * (model) { model.addFigure(cmd); }
		 */
	}

	private void addCommand(String cmd) { // kk300
		if (cmd != null)
			addtoNetCommands(cmd);
	}


	private void info(String info) {
		clog("[i]" + info);
	}

	private void clog(String mesg) {
		System.out.println(mesg);
	}

	private void error(String err) {
		clog("[err]" + err);
	}

	private void viewSetLocal(String host, int port) {
		clog("viewSetLocal " + host + "  " + port);
	}

	private void viewRefreshModel() {
		clog("refreshmodel");
	}

	private void clean() {
		clog("clean");
	}

	private void viewClearConnexion() {
		clog("ClearConnexion");
	}

	private void viewClear() {
		info("viewClear");
		clearView();
	}

	private void runClient(String host, int port) {
		connect(host, port);
	}

	private boolean isConnected(String[] ip) {
		if (clientSocket_ == null)
			return false;
		if (sessionTask == null || sessionTask.isInterrupted())
			return false;
		boolean idem = ip[0].equals(serverName) && ip[1].equals(Integer.toString(serverPort));
		if (idem) {
			if (clientSocket_.isConnected())
				return true;
		} else {
			disconnect(true);
			connect(ip[0], Integer.parseInt(ip[1]));
			return true;
		}
		return false;
	}

	private void setKeepConnection(boolean keep) {
		this.keepConnection_ = keep;
	}


	private void viewRefresh() {
		refreshView();
	}

	
	/*---------*/

	private void handleMessage(String message) {
		clog("handleMessage " + message);

	}

	private void clearView() {
		clog("clearView ");

	}


	private void refreshView() {
		clog("refreshView ");

	}

	private void notifySocketClosed() {
		clog("socketClosed ");

	}

	private void noServerAvailable() {
		clog("no server available");

	}

	private int[] getIdentifiers() {
		int maxc = 180;
		int id1 = rand.nextInt(maxc);
		int id2 = rand.nextInt(maxc);
		int id3 = rand.nextInt(maxc);
		int[] identifiers = new int[3];
		identifiers[0] = id1;
		identifiers[1] = id2;
		identifiers[2] = id3;
		return identifiers;
	}

	public static void main(String[] args) {
		ClientSocket s = new ClientSocket();
		s.runClient(DEFAULT_HOST, DEFAULT_PORT);
	}

}
