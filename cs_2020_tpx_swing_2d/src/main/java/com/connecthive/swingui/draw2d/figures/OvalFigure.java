package com.connecthive.swingui.draw2d.figures;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import com.connecthive.swingui.draw2dapi.AbstractFigure;
import com.connecthive.swingui.draw2dapi.Attributes;

public class OvalFigure extends AbstractFigure {
	public OvalFigure(Attributes attributes) {
		super(attributes);
	}
	@Override
	public Shape getShape() {
		Rectangle bounds = getBounds();
		return new Ellipse2D.Float(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	@Override
	public String toCode() {
	  	Rectangle bounds = getBounds();
	    return  "oval " + bounds.x + " " + bounds.y + " " +bounds. width + " " + bounds.height;
	}
	
	
	
}
