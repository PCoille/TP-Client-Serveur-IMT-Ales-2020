package api;

public interface IBSControler {

	void setViewSize(Object obj);

	void runClock();

	void setModel(IModel model);

	IModel getModel();

	void addFigure(String figure);

	void stopClock();

	void log(Object clienttask, String message);

	void dispose();

	void notify(Object changed);

	String toShipCmd(int x, int y, int w, int h);

}
