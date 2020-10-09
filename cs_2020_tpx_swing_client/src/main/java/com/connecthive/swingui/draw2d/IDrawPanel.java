package com.connecthive.swingui.draw2d;

public interface IDrawPanel {
	 void addDrawable(Drawable drawable);

	void setMousePos(String position);

	void clear();

	void undo();

}
