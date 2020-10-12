package com.connecthive.swingui.draw2dapi;

import java.util.List;

public interface IDrawingApp {

	List<IFigure> getModel();
	void setMousePos(String position);
	void log(String string);
	IFigure createFigure();
	String getSelectedFigure();
	void clearLog();
	void addFigure(IFigure figure);
	void clearDrawing();
	void undoDrawing();
	void setFigure(IFigure figure);
	void addCommand(String message);
	void connect(String ip, int port);
	void disconnect();

	

}
