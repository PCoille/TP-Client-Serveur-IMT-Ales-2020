package com.connecthive.swingui.draw2dapi;


import com.connecthive.swingui.draw2dapi.IDrawingApp;

public interface IControlPanel {

	

	void setOwner(IDrawingApp drawingApplication);

	Attributes getAttributes();

	String getSelectedFigure();

}
