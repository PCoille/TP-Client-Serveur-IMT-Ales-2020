package api;

import java.util.List;



/**
 * 
 * @author pfister - connecthive.com
 * initial api and implementation
 *
 */
public interface IServerController extends ISocketApi {

	void runServer() throws Exception;
	void logRequest(int id, String request);
	void logStatus(IServicetask serviceTask, int id, String status);
	List getClientTasks();
	void endSession(IServicetask serviceTask);
	void broadcast(IServicetask serviceTask, String resp);
	void notify(IServicetask serviceTask);
	List<int[]> getClientIds();
	boolean registerCLient(IServicetask serviceTask, int[] id);
	void connect();
	void handleMessage(String message);
	void setServer(IThreadedServer threadedServer);

}
