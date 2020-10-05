package test1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class OneshotServer {

	static int PORT = 1025;
	static final boolean LOG = true;

	private static void clog(String message) {
		if (LOG)
			System.out.println(message);
	}

	public static void main(String[] args) throws IOException {
		if (LOG)
			clog("je suis le serveur, j'écoute sur le port " + PORT);
		ServerSocket socketEcoute = new ServerSocket(PORT);
		Socket socketService = socketEcoute.accept();
		if (socketService!=null)
		  clog("un client s'est connecté");
		

		if (LOG)
			clog("terminé");
	}

}
