package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class FirstServer {
    static int PORT = 25;
    static final boolean LOG = true;

    private static void clog(String msg) {
        if(LOG)
            System.out.println(msg);
    }

    public static void main(String[] args) {
        if(LOG)
            clog("Creating server...");



        try {
            ServerSocket listeningSocket = new ServerSocket(PORT);
            if(LOG)
                clog("Currently listening on port " + PORT);

            Socket socketService = listeningSocket.accept();

            if (socketService!=null)  {
                clog("Client connected, with address [" + socketService.getRemoteSocketAddress() + "]");

                BufferedReader input = new BufferedReader(new InputStreamReader(socketService.getInputStream()));

                PrintStream output = new PrintStream(socketService.getOutputStream());

                output.println("Awaiting command.");

                String clientRequest = input.readLine();
                clog("The client asked: " + clientRequest);
                String response = "Unknown command";
                if (clientRequest.endsWith("date")) {
                    Date d = new Date();
                    response = d.toString();
                }

                clog("Response: "+ response);

                output.println("You said: " + clientRequest);
                output.println("Response: " + response);

                socketService.close();
                listeningSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(LOG)
            clog("Done.");
    }
}
