package com.connecthive.swingui.draw2dapi;



public interface IDrawPanel {
	 void addDrawable(IDrawable drawable);

	void setMousePos(String position);

	void clear();

	void undo();

	void setOwner(IDrawingApp drawingApplication);

}
