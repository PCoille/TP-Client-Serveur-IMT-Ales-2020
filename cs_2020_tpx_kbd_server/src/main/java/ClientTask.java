

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author pfister - connecthive.com
 *
 */
public class ClientTask extends Thread {

	int id;
	int tic;
	boolean interrupted;
	private boolean ended;
	private Socket socketService;
	ClientTask thistask = this;

	private PrintStream output;
	private ThreadedServer server;
	
	
	public ClientTask(ThreadedServer server, int id) {
		this.server = server;
		this.id = id;
	}
	
	private synchronized PrintStream getOutput_() {
		return output;
	}

	@Override
	public void interrupt() {
		if (!isInterrupted()) {
			interrupted = true;
			Util.log(this, "task " + id + " is interrupted");
			super.interrupt();
		}
	}

	@Override
	public boolean isInterrupted() {
		boolean result = super.isInterrupted();
		result = result || interrupted;
		return result;
	}

	boolean action(String request) {
		if (request.toLowerCase().contains("quit")) {
			getOutput_().println("stopping session");
			if (ThreadedServer.BROADCAST)
				   server.broadcast(this, "stopping session "+getId());
			return false;
		} else {
			String[] req = request.split(" ");
			if (req[0].toLowerCase().equals("stop")) {
				String msg = "the server will shutdown in 5 seconds";
				getOutput_().println(msg);
				if (ThreadedServer.BROADCAST)
				   server.broadcast(this, msg);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Util.delay(thistask, 5000);
						Util.globalEnd = true;
					}
				}).start();
			} if (req[0].toLowerCase().equals("date")) {
				Date now = new Date();
				String msg = "il est " + now.toString();
				getOutput_().println(msg);
				if (ThreadedServer.BROADCAST)
					   server.broadcast(this, msg);
			} else if (req[0].toLowerCase().equals("circle")) {
				try {
					String msg = Util.circle(req);
					getOutput_().println(msg);
					if (ThreadedServer.BROADCAST)
						   server.broadcast(this, msg);
				} catch (Exception e) {
					getOutput_().println("erreur dans la commande " + request + " format attendu:" + "x y radius");
				}
			} else if (req[0].toLowerCase().startsWith("add") && req.length >= 2) {
				String element = req[1];
				try {
					Util.addElement(this, element);
					String response = "element added ";
					getOutput_().println(response);
					if (ThreadedServer.BROADCAST)
						   server.broadcast(this, response);
				} catch (InterruptedException e) {
					interrupted = true;
					return false;
				}
			} else if (req[0].toLowerCase().startsWith("remove")) {
				try {
					String element = Util.removeElement(this);
					String response = "element removed: " + element;
					getOutput_().println(response);
					if (ThreadedServer.BROADCAST)
						   server.broadcast(this, response);
				} catch (InterruptedException e) {
					interrupted = true;
					return false;
				}
			} else {
				String response = "commande inconnue \n";
				response += "'Je connais les commandes suivantes: \n";
				response += "'date => je retourne la date\n";
				response += "'circle x y radius => je calcule un cercle de rayon radius à la coordonnée x y\n";
				response += "'quit => fin de la session\n";
				response += "'add [element name]";
				response += "'remove\n";
				getOutput_().println(response);
				
			}

		}
		return true;
	}

	@Override
	public void run() {
		Util.log(this, "task " + id + " starts");
		try {
			Util.log(this, "client " + id);
			Util.log(this, "le client " + socketService.getRemoteSocketAddress() + " s'est connecté");
			output = new PrintStream(socketService.getOutputStream(), true); // autoflush
			BufferedReader networkIn = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
			while (!Util.globalEnd && !isInterrupted()) {
				String requeteclient = networkIn.readLine();
				if (requeteclient == null) {
					Util.log(this, "le client s'est déconnecté, fin de la session");
					break;
				}
				Util.log(this, "le client demande: " + (requeteclient == null ? "null" : requeteclient));
				if (!action(requeteclient))
					break;
			}
		} catch (IOException e) {
		} finally {
			try {
				socketService.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Util.log(this, "arrêt de la session");
		}
		ended = true;
		Util.log(this, "session " + id + " ends");
		
		server.remove(this);
	}

	public void setSocketService(Socket socketService) {
		this.socketService = socketService;
	}

	public void send(String data) {
		if (!socketService.isClosed())
			getOutput_().println(data);
		
	}


}