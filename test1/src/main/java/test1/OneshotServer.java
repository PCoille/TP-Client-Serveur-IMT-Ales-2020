package test1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
		  clog("un client s'est connecté");
		
		
		
		
		
		if (LOG)
			clog("terminé");
	}

}
