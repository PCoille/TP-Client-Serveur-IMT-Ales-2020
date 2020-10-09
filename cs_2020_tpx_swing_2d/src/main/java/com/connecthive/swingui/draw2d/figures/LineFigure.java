package com.connecthive.swingui.draw2d.figures;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import com.connecthive.swingui.draw2dapi.AbstractFigure;
import com.connecthive.swingui.draw2dapi.State;

public class LineFigure extends AbstractFigure {
	public LineFigure(State state) {
		super(state);
	}
	@Override
	public Shape getShape() {
		Rectangle bounds = getBounds();
		return new Line2D.Float(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
	}
}
