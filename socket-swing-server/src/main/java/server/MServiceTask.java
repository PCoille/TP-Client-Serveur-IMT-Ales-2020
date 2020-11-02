package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.IConstants;
import api.IServicetask;
import api.IThreadedServer;
import api.IServerController;



/**
 * 
 * @author pfister - connecthive.com
 * initial api and implementation
 *
 */
public class MServiceTask extends Thread implements IServicetask {

	int idno;
	int tic;
	boolean interrupted;
	private boolean ended;
	private Socket socketService;
	private MServiceTask thistask = this;
	private IThreadedServer server;
	private SocketAddress remoteAddress;
	private PrintStream output;
	private int[] ident;
	private List<String> buffer = new ArrayList<String>();
	private boolean globalEnd;
	
	private synchronized PrintStream getOutput() {
		return output;
	}

    @Override
	public int getIdNo() {
		return idno;
	}

	public MServiceTask(int id, IThreadedServer threadedServer) {
		this.idno = id;
		this.server = threadedServer;
	}

	@Override
	public void interrupt() {
		if (!isInterrupted()) {
			interrupted = true;
			//clog(this, "task " + id + " is interrupted");
			super.interrupt();
		}
	}

	@Override
	public boolean isInterrupted() {
		boolean result = super.isInterrupted();
		result = result || interrupted;
		return result;
	}

	// KW_CIRCLE,KW_LINE,KW_RECTANGLE,KW_BOMB,KW_SHIP

	private boolean parseFigures(String[] req, String request) {
		boolean result = true;
		if (req[0].equals(IConstants.KW_POLYCIRCLE)) {
			try {
				String figure = circle_v1(req);
				figure = req[0] + " " + figure;
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request + " format attendu:" + "x y radius");
			}
		} else if (req[0].equals(IConstants.KW_CIRCLE)) {
			try {
				String figure = req[0] + " ";
				figure += req[1] + " " + req[2] + " " + req[3];
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request + " format attendu:" + "x y radius");
			}
		}  if (req[0].equals(IConstants.KW_OVAL)) {
			try {
				String figure = req[0] + " ";
				figure += req[1] + " " + req[2] + " " + req[3]+ " " + req[4];
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request + " format attendu:" + "x y w h");
			}
		} else if (req[0].equals(IConstants.KW_BOMB)) {
			try {
				String figure = req[0] + " ";
				figure += req[1] + " " + req[2] + " " + req[3];
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request + " format attendu:" + "x y radius");
			}
		} else if (req[0].equals(IConstants.KW_LINE)) {
			try {
				String figure = req[0] + " ";
				figure += req[1] + " " + req[2] + " " + req[3] + " " + req[4];
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request);
			}
		} else if (req[0].equals(IConstants.KW_RECTANGLE)) {
			try {
				String figure = req[0] + " ";
				figure += req[1] + " " + req[2] + " " + req[3] + " " + req[4];
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request);
			}
		} else if (req[0].equals(IConstants.KW_SHIP_)) {
			try {
				String figure = req[0] + " ";
				figure += req[IConstants.ID0] + " " + req[IConstants.ID1] + " " + req[IConstants.ID2] + " ";
				figure += req[4] + " " + req[5] + " " + req[6] + " " + req[7];
				server.addFigure(figure);
				server.broadcast(this, figure);
				server.log(figure);
				getOutput().println(figure);
			} catch (Exception e) {
				getOutput().println("erreur dans la commande " + request);
			}
		} else
			result = false;

		return result;

	}

	private String circle_v1(String[] req) {
		// TODO Auto-generated method stub
		return null;
	}

	private void parseIdentification(String[] req, String request) {
		ident = new int[3];
		ident[0] = Integer.parseInt(req[IConstants.ID0]);
		ident[1] = Integer.parseInt(req[IConstants.ID1]);
		ident[2] = Integer.parseInt(req[IConstants.ID2]);
		server.log("Welcome " + ident[0] + "." + ident[1] + "." + ident[2]);
		if (server.registerCLient(this, ident)) {

		}
	}

	boolean action(String request) {
		server.logRequest(idno, request);
		if (request.toLowerCase().contains("quit")) {
			getOutput().println("stopping session");
			return false;
		} else {
			String[] req = request.split(" ");
			if (req[0].toLowerCase().equals("stop")) {
				getOutput().println("the srver will shutdown in 5 seconds");
				new Thread(new Runnable() {
				
					//@Override
					public void run() {
						delay(thistask, 5000);
						globalEnd = true;
					}
				}).start();
			}
			if (req[0].toLowerCase().equals("date")) {
				Date now = new Date();
				String response = "il est " + now.toString();
				getOutput().println(response);
				server.broadcast(this, response);
			} else if (req[0].equals("Helo")) {
				parseIdentification(req, request);
			} else if (req[0].toLowerCase().startsWith("add") && req.length >= 2) {
				String element = req[1];
				try {
					Util_addElement(this, element);
					String response = "element added ";
					getOutput().println(response);
				} catch (Exception e) {
					interrupted = true;
					return false;
				}
			} else if (req[0].toLowerCase().startsWith("remove")) {
				try {
					String element = Util_removeElement(this);
					String response = "element removed: " + element;
					getOutput().println(response);
				} catch (Exception e) {
					interrupted = true;
					return false;
				}
			} else if (!parseFigures(req, request)) {
				String response = "commande inconnue ";
				/*
				 * response += "'Je connais les commandes suivantes:"; response
				 * += "'date => je retourne la date"; response +=
				 * "'circle x y radius => je calcule un cercle de rayon radius à la coordonnée x y"
				 * ; response += "'quit => fin de la session"; response +=
				 * "'add [element name]"; response += "'remove";
				 */
				server.broadcast(this, response);
				getOutput().println(response);
			}

		}
		return true;
	}

	private String Util_removeElement(MServiceTask serviceTask) {
		// TODO Auto-generated method stub
		return null;
	}

	private void Util_addElement(MServiceTask serviceTask, String element) {
		// TODO Auto-generated method stub
		
	}

	protected void delay(MServiceTask thistask2, int i){

			try {
				Thread.sleep(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}

	public String toString() {
		String result = "";
		result += idno;
		result += " ";
		result += ident[0];
		result += ".";
		result += ident[1];
		result += ".";
		result += ident[2];
		result += remoteAddress;
		return result;
	}
	
	public String toStri_ng() {
		String result = "";
		result += idno;
		result += " ";

		result += ".";

		result += ".";

		result += remoteAddress;
		return result;
	}

	public void send(String data) {
		if (!socketService.isClosed())
			getOutput().println(data);
	}

	public void prepare(String figure) {
		buffer.add(figure);
	}

	public void flush() {
		if (!socketService.isClosed())
			for (String figure : buffer) {
				getOutput().println(figure);
			}
		buffer.clear();
	}



	@Override
	public void run() {
		Util_log(this, "task " + idno + " starts");
		try {
			this.remoteAddress = socketService.getRemoteSocketAddress();
			String status = "le client " + idno;
			status += " " + remoteAddress + " s'est connecté";
			Util_log(this, status);
			server.logStatus(this, idno, status);
			// server.notify(this);
			output = new PrintStream(socketService.getOutputStream(), true); // autoflush
			BufferedReader networkIn = new BufferedReader(new InputStreamReader(socketService.getInputStream()));
			server.notify(this);
			while (!globalEnd && !isInterrupted()) {
				String requeteclient = networkIn.readLine();
				if (requeteclient == null) {
					status = "le client " + idno + " " + remoteAddress + " s'est déconnecté, fin de la session";
					Util_log(this, status);
					server.logStatus(this, idno, status);
					break;
				} else {
					Util_log(this, "le client demande: " + (requeteclient == null ? "null" : requeteclient));
					if (!action(requeteclient))
						break;
				}
			}
		} catch (IOException e) {
			Util_log(this, "IO error in ServiceTask " + e.toString());
		} finally {
			try {
				socketService.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Util_log(this, "arrêt de la session");
			server.logStatus(this, idno, "session end");
		}
		ended = true;
		server.endSession(this);
		Util_log(this, "session " + idno + " ends");
	}

	private void Util_log(MServiceTask serviceTask, String mesg) {
	    System.out.println(serviceTask.getId()+"   "+mesg);	
	}

	public void setSocketService(Socket socketService) {
		this.socketService = socketService;
	}

	public void closeSocket() {
		try {
			if (socketService != null)
				socketService.close();
		} catch (IOException e) {

		}
	}

}