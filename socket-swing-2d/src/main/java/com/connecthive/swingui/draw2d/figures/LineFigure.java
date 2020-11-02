package com.connecthive.swingui.draw2d.figures;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;

import com.connecthive.swingui.draw2dapi.AbstractFigure;
import com.connecthive.swingui.draw2dapi.Attributes;

public class LineFigure extends AbstractFigure {
	public LineFigure(Attributes state) {
		super(state);
	}
	@Override
	public Shape getShape() {
		Rectangle bounds = getBounds();
		return new Line2D.Float(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
	}
	
	
	@Override
	public String toCode() {
	  	Rectangle bounds = getBounds();
	  	Attributes attr = getAttributes();
	  	
	    return  "line " + bounds.x + " " + bounds.y + " " +bounds. width + " " + bounds.height
	    		+ " " + attr.getForeground().getRGB() + " " + attr.getBackground().getRGB() + " " + attr.isGradient() + " " 
	    		+ attr.isFilled() + " " + attr.isDashed() + " " + attr.getLineWidth() + " " + attr.getDashLength();
	}
	
	
	
	
	
}
