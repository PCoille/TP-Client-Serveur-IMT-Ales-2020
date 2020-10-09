package com.connecthive.swingui.draw2dapi;

import java.util.List;

public interface IDrawingApp {

	List<IDrawable> getModel();

	void setMousePos(String position);

}
