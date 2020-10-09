package com.connecthive.swingui.draw2d;

import java.util.List;

public interface IDrawingApp {

	List<Drawable> getModel();

	void setMousePos(String position);

}
