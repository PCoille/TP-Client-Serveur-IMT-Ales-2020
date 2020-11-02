package server;


import java.util.List;

import api.IModel;
import api.IServerController;
import api.IServicetask;
import api.IThreadedServer;
import api.IWsGui;

/**
 * 
 * @author pfister - connecthive.com
 * initial api and implementation
 *
 */
public class MServerControler implements IServerController  { // extends Controler
	
	private IThreadedServer server;

	private IWsGui serverView;
	
	@Override
	public void setServer(IThreadedServer threadedServer) {
		server = threadedServer;
		server.setControler(this);
	}



	public MServerControler() {//IApplication application) {
	//	super(application);
	}
/*
	//@Override
	public void setNetwork(Object server) {
		this.server = (ISocketServer) server;	
	}*/

	//@Override
	public List getClientTasks() {
		return server.getClientTasks();
	}

	//@Override
	public void connect() {
		server.connect();
	}

	//@Override
	public void disconnect() {
		server.disconnect();
	}

//	@Override
	public void setServerView(IWsGui view) {
		this.serverView = view;
	//	this.view = view;
		serverView.setControler(this);
	}


	public void setServerDown() {
		serverView.notifyId("server down");
	}

	
	@Override
	public void addFigure(String figure) {
		server.addFigure(figure);	
	}


	public void setHost(String mac, String host, int port) {
		serverView.log("setHost mac="+mac +" host="+host+" port="+ port);	
	}

	@Override
	public List<int[]> getClientIds() {
		return server.getClientIds();
	}


	protected Object[] getId() {
		//return List.toArray(getClientIds());
		return  getClientIds().toArray();
	}

	//@Override
	public void consolideNewClient(Object serviceTask) {
		MServiceTask st = (MServiceTask) serviceTask;
		List<String> figures = getModel().getFigures();
		for (String figure : figures) 
			st.prepare(figure);
		st.flush();
		
	}

	public void start() {
		server.start();
		
	}
	
	@Override
	public void handleMessage(String message) {
		serverView.handleMessage(message);	
	}
	

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return true;
	}

	public void jAddMessage(String message) {
		serverView.log("jAddMessage "+message);
		
	}

	public void setGui(IWsGui gui) {
		this.serverView = gui;
		
	}

	public void jDisconnect() {
		server.disconnect();
		
	}

	public void setModel(IModel model) {
		server.setModel(model);
		
	}

	public IModel getModel() {
		// TODO Auto-generated method stub
		return server.getModel();
	}

	public void log(String log) {
		serverView.log(log);
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void runServer() throws Exception {
		start();
		
	}


	public void logStatus(IServicetask serviceTask, int id, String status) {
	//	serverView.log("status="+serviceTask.toString()+"  "+id+"  "+status);
		serverView.log("status="+"  "+id+"  "+status);
		
	}



	@Override
	public void logRequest(int id, String request) {
		boolean tb = true;
		
	}



	@Override
	public void endSession(IServicetask serviceTask) {
		boolean tb = true;
		
	}



	@Override
	public void broadcast(IServicetask serviceTask, String resp) {
		boolean tb = true;
		
	}



	@Override
	public void notify(IServicetask serviceTask) {
		boolean tb = true;
		
	}



	@Override
	public boolean registerCLient(IServicetask serviceTask, int[] id) {
		// TODO Auto-generated method stub
		return true;
	}






	
	
	
}
