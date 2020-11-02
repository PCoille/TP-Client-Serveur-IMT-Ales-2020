package cs_2020_tp1_simple_servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * @author pfister - connecthive.com
 * 
 * ce serveur peut servir n requêtes à n clients
 * Un client peut faire tomber le serveur en émettant la commande "stop"
 * Une connection est maintenue pour chaque client, ce dernier y met fin avec la commande "quit"
 * Pendant ce temps les autres clients sont bloqués -> necessité d'utiliser des threads
 *
 */
public class LoopServer2 {

	private static int DEFAULT_PORT = 8092;
	static int port = DEFAULT_PORT ;

	private static void delai(int millis) {
		if (millis > 0)
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) throws IOException {
		String myadress = "";// HostAdress.getHostAddress(); TODO
		System.out.println("Je suis le serveur " + myadress);
		System.out.println("J'écoute sur le port " + port);
		ServerSocket socketEcoute = new ServerSocket(port);
		boolean endServer = false;
		while (!endServer) {
			System.out.println("En attente d'une connexion");
			Socket socketService = socketEcoute.accept();
			System.out.println("Une connexion est accept�e (" + socketService.getRemoteSocketAddress() + ")");
			BufferedReader entree = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
			PrintStream sortie = new PrintStream(socketService.getOutputStream());
			boolean endSession = false;
			while (!endSession && !endServer) {
				String requeteClient = entree.readLine();
				System.out.println("le client demande: " + requeteClient);
				String reponse = "commande inconnue";
				if (requeteClient.endsWith("Quelle heure est-il ?") || requeteClient.endsWith("date")) {
					Date d = new Date();
					reponse = d.toString();
				} else if (requeteClient.endsWith("quit")) {
					endSession = true;
					reponse = "fin de session";
				}else if (requeteClient.endsWith("stop")) {
					endServer = true;
					reponse = "arrêt du serveur";
				}
				System.out.println("réponse=" + reponse);
				sortie.println("Bonjour, ici le serveur, vous avez demandé: "
						+ requeteClient + " ,voici la réponse [" + reponse +"]");
			}
			socketService.close();
			delai(100);
		}
		socketEcoute.close();
		System.out.println("Terminé !");
	}
}
