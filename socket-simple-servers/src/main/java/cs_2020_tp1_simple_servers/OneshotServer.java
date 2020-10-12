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
 * Ce serveur ne peut servir qu'une seule requ�te � un seul client
 * Et ensuite il s'arr�te
 *
 */
public class OneshotServer {
	private static final int DEFAULT_PORT = 8092;
	static int port = DEFAULT_PORT;

	public static void main(String[] args) throws IOException {
		System.out.println("Je suis le serveur, j'�coute sur le port " + port);
		ServerSocket socketEcoute = new ServerSocket(port);
		System.out.println("En attente d'une connexion");
		Socket socketService = socketEcoute.accept();
		System.out.println("Une connexion est accept�e (" 
		     + socketService.getRemoteSocketAddress() + ")");
		BufferedReader entree = new BufferedReader(new InputStreamReader(
				                             socketService.getInputStream()));
		PrintStream sortie = new PrintStream(socketService.getOutputStream());
		String requeteClient = entree.readLine();
		System.out.println("le client demande: " + requeteClient);
		String reponse = "commande inconnue";
		if (requeteClient.endsWith("Quelle heure est-il ?") 
				                        || requeteClient.endsWith("date")) {
			Date d = new Date();
			reponse = d.toString();
		}
		System.out.println("r�ponse=" + reponse);
		sortie.println("Bonjour, ici le serveur, vous avez demand�: " 
					+ requeteClient + " ,voici la r�ponse [" + reponse + "]");
		socketService.close();
		socketEcoute.close();
		System.out.println("Termin� !");
	}
}
