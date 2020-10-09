package api;

public interface ISocketApi {
	void start();
	boolean isRunning();
	void setGui(IWsGui iWsGui);
	void jDisconnect();
	void setModel(IModel model);
	IModel getModel();
	void disconnect();
	void log(String log);
	void addFigure(String figure);
	void dispose();

}
