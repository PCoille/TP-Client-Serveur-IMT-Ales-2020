

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author pfister - connecthive.com
 * 
 *
 */
public class ThreadedServer {

	private static final int PORT = 8092;
	public static final boolean BROADCAST = true;
	private List<ClientTask> clientServices = new ArrayList<ClientTask>();

	public void broadcast(ClientTask serviceTask, String data) {
		for (ClientTask client : clientServices) 
			if (client != serviceTask)
				client.send(data);
		
	}

	public void remove(ClientTask clientTask) {
		clientServices.remove(clientTask);	
	}

	public void runServer() throws Exception {
		ServerSocket socketEcoute = new ServerSocket(PORT);
		//String localhost_ = "192.168.1.95"; // TODO recherchez votre adresse avec
											// ipconfig sous windows, ou
											// ifconfig ou encore ip sous linux

		Util.log(null, "[serveur multisession] démarré sur :" +  ":" + socketEcoute.getLocalPort());

		//boolean endServer = false;
		int numClient = 0;
		while (!Util.globalEnd) {
			Util.log(null, "en attente d'une connexion");
			Socket socketService = socketEcoute.accept(); // bloquant ici
			ClientTask clientTask = new ClientTask(this,numClient++);
			clientServices.add(clientTask);
			clientTask.setSocketService(socketService);
			clientTask.start();
		}
		socketEcoute.close();
		Util.log(null, "arrêt du serveur");
	}

	public static void main(String[] args) {
		ThreadedServer threadedServer = new ThreadedServer();
		try {
			threadedServer.runServer();
		} catch (Exception e) {
			Util.log(e.toString());
		}
	}




}
//ok whiteboard ok