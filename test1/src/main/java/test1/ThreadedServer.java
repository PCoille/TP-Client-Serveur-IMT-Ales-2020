package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ThreadedServer {

	static int PORT = 1025;
	static final boolean LOG = true;
	public static boolean endserver;

	static class ClientTask extends Thread {

		private Socket socketService;

		public ClientTask(Socket soc) {
			this.socketService = soc;
		}

		@Override
		public void run() {
			try {
				while (true) {
					if (socketService != null)
						clog("un client s'est connecté " + socketService.getRemoteSocketAddress());
					BufferedReader entree;
					entree = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
					PrintStream sortie = new PrintStream(socketService.getOutputStream());
					boolean end = false;
					while (!end) {
						sortie.println("votre question ?");
						String requeteClient = entree.readLine();
						String reponse;
						clog("le client demande " + requeteClient);
						if (requeteClient.endsWith("date"))
							reponse = new Date().toString();
						else if (requeteClient.endsWith("fin")) {
							reponse = "fin de la session";
							end = true;
						} else if (requeteClient.endsWith("quit")) {
							reponse = "fin du serveur";
							end = true;
							endserver = true;
						} else
							reponse = "requete inconnue";
						clog("Réponse = " + reponse);
						String mesg = String.format(
								"Bonjour, ici le serveur, vous avez demandé [%s], voici la réponse:[%s]", requeteClient,
								reponse);
						sortie.println(mesg);
					}
					socketService.close();
				}
			} catch (IOException e) {
				return;
			}
		}
	}

	private static void clog(String message) {
		if (LOG)
			System.out.println(message);
	}

	public static void main(String[] args) throws IOException {
		if (LOG)
			clog("je suis le serveur multitache, j'écoute sur le port " + PORT);
		ServerSocket socketEcoute = new ServerSocket(PORT);
		while (!endserver) {
			Socket socketService = socketEcoute.accept();
			ClientTask tacheClient = new ClientTask(socketService);
			tacheClient.start();
		}
		socketEcoute.close();
		if (LOG)
			clog("terminé");
	}

}

//added maven wrapper ok
//demo pull git
//demo branch 4