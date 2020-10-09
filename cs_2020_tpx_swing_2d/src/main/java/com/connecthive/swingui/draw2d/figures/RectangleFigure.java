package com.connecthive.swingui.draw2d.figures;

import java.awt.Shape;

import com.connecthive.swingui.draw2dapi.AbstractFigure;
import com.connecthive.swingui.draw2dapi.State;

public class RectangleFigure extends AbstractFigure {
	public RectangleFigure(State state) {
		super(state);
	}
	@Override
	public Shape getShape() {
		return getBounds();
	}
}
