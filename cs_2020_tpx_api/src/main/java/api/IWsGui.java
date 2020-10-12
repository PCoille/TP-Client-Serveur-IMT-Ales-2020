package api;

import java.awt.Rectangle;


public interface IWsGui {
	//void initialize();
	void setControler(Object controler);
	void handleMessage(String message);
	void notifySocketClosed();
	void log(String log);
	void clearView();
	void refreshFig();
	void refresh(Rectangle rect);
	void refreshCanvas();
	void notifySocketError();
	void notify(Object changed);
	void notifyId(String id);
	void setId(Object identifiers);
	void setup();
	void addFigure(String figure);
	
	
}

