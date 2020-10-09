package com.connecthive.swingui.draw2dapi;



import api.IClientControler;

public interface ISocketPanel {

	void setId(Object oi);

	void setControler(IClientControler controler);

	void setOwner(IDrawingApp drawingApplication);

}
