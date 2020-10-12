package api;



public interface IClientControler extends ISocketApi{

	void connect(String ip, int i);
	void notifySocketClosed();
	void clearView();
	void refreshFigure(String figure);
	void handleMessage(String message);
	void sendMessage(String message);
	void jAddMessage(String message);
	void setSessionClient(ISessionClient sessionClient);
	void noServerAvailable();
	int[] getIdentifiers();
	//void refreshModel(String figure);
	
	
}
