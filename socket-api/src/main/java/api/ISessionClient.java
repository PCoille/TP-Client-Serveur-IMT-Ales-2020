package api;



public interface ISessionClient {
	String getResponse();
	void disconnect(boolean verbose);
	boolean connect(String host, int port);
	boolean sendCommand(String cmd);
	void addCommand(String cmd);
	void clearModel();
	void runClient(String host, int port);
	boolean isConnected(String[] ip);
	void setKeepConnection(boolean keep);
	boolean isKeepConnection();

	void sendMessage(String message);
	void disconnect();
	void jAddMessage_(String message);
	void jDisconnect();

	void setHost(String uri, int i);
	void setControler(IClientControler cc);

}
