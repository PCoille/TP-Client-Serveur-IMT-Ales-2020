package com.connecthive.swingui.draw2d.figures;

import java.awt.Rectangle;
import java.awt.Shape;

import com.connecthive.swingui.draw2dapi.AbstractFigure;
import com.connecthive.swingui.draw2dapi.Attributes;

public class RectangleFigure extends AbstractFigure {
	public RectangleFigure(Attributes state) {
		super(state);
	}
	@Override
	public Shape getShape() {
		return getBounds();
	}
	
	
	@Override
	public String toCode() {
	  	Rectangle bounds = getBounds();
	    return  "rectangle " + bounds.x + " " + bounds.y + " " +bounds. width + " " + bounds.height;
	}	
	
	
}
