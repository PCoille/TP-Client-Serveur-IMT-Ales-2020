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
 * ce serveur peut servir n requ�tes � n clients
 * mais la connexion est ferm�e aussit�t, pas de gestion de session
 * Un client peut faire tomber le serveur en �mettant la commande "stop"
 *
 */
public class LoopServer1 {

	private static final int DEFAULT_PORT = 8092;
	static int port = DEFAULT_PORT;

	private static void delai(int millis) {
		if (millis > 0)
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) throws IOException {
		String myadress = "";//HostAdress.getHostAddress(); TODO
		System.out.println("Je suis le serveur " + myadress);
		System.out.println("J'�coute sur le port " + port);
		ServerSocket socketEcoute = new ServerSocket(port);
		boolean end = false;
		while (!end) {
			System.out.println("En attente d'une connexion");
			Socket socketService = socketEcoute.accept();
			System.out.println("Une connexion est accept�e ("
					+ socketService.getRemoteSocketAddress() + ")");
			BufferedReader entree = new BufferedReader(new InputStreamReader(
					socketService.getInputStream()));
			PrintStream sortie = new PrintStream(
					socketService.getOutputStream());
			String requeteClient = entree.readLine();
			System.out.println("le client demande: " + requeteClient);
			String reponse = "commande inconnue";
			if (requeteClient.endsWith("Quelle heure est-il ?")
					|| requeteClient.endsWith("date")) {
				Date d = new Date();
				reponse = d.toString();
			} else if (requeteClient.endsWith("stop")) {
				end = true;
				reponse = "arr�t demand�";
			}
			System.out.println("r�ponse=" + reponse);
			sortie.println("Bonjour, ici le serveur, vous avez demand�: "
					+ requeteClient + " ,voici la r�ponse [" + reponse +"]");
			socketService.close();
			delai(100);
		}

		socketEcoute.close();
		System.out.println("Termin� !");
	}
}
