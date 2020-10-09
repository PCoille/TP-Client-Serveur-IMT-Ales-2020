package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/*
 
 
cmd pour lancer eclipse sur voter workspace [votre dossier]\eclipse.exe -data . -vm [votrejdk]\bin\javaw.exe
echo %JAVA_HOME%
PATH=%PATH%;%JAVA_HOME%\bin\
si JAVA_HOME existe et si ok pour eclipse enlever -vm [votrejdk]\bin\javaw.exe
attention, dans .gitignore ne pas laisser *.xml car cela supprime pom.xml => plus de maven
 
 
 */


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
		  clog("un client s'est connecté "+socketService.getRemoteSocketAddress());
		BufferedReader entree = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
		PrintStream sortie = new PrintStream(socketService.getOutputStream());
		sortie.println("votre question ?");
		String requeteClient = entree.readLine();
		String reponse;
		clog("le client demande "+requeteClient);
		if (requeteClient.endsWith("date"))
			reponse = new Date().toString();
		else
			 reponse = "requete inconnue";
		clog("Réponse = "+reponse);
		String mesg = String.format("Bonjour, ici le serveur, vous avez demandé [%s], voici la réponse:[%s]",requeteClient,reponse);
		sortie.println(mesg);
	    socketService.close();
	    socketEcoute.close();
		if (LOG)
			clog("terminé");
	}

}

//added maven wrapper ok
//demo pull git
//demo