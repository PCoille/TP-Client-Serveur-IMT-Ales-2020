package api;



public interface IClientControler extends ISocketApi{

	void connect(String ip, int i);
	void notifySocketClosed();
	void clearView();
	void refreshView();
	void handleMessage(String message);
	void sendMessage(String message);
	void jAddMessage(String message);
	void setSessionClient(ISessionClient sessionClient);
	void noServerAvailable();
	int[] getIdentifiers();
	
	
}
