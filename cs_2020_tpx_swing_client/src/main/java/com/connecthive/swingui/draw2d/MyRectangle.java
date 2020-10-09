package com.connecthive.swingui.draw2d;

import java.awt.Shape;

public class MyRectangle extends AbstractDrawable {
	public MyRectangle(State state) {
		super(state);
	}
	@Override
	public Shape getShape() {
		return getBounds();
	}
}
