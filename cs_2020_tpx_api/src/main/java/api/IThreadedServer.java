package api;

import java.util.List;





public interface IThreadedServer {

	boolean registerCLient(IServicetask serviceTask, int[] id);

	void broadcast(IServicetask serviceTask, String data);

	void endSession(IServicetask clientTask);

	void dispose();

	void connect();

	void disconnect();

	void log(String log);

	void addFigure(String figure);

	void notify(IServicetask serviceTask);

	List<int[]> getClientIds();

	void logRequest(int id, String request);

	void logStatus( IServicetask mServiceTask, int id, String status);

	List getClientTasks();

	void start();

	void setModel(IModel model);

	IModel getModel();

	void setControler(IServerController serverControler);

}
