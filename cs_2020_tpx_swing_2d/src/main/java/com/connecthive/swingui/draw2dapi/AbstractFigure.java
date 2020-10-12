package com.connecthive.swingui.draw2dapi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JComponent;



public abstract class AbstractFigure implements IFigure {
	private Rectangle bounds;
	private Attributes attributes;

	public AbstractFigure(Attributes attr) {
		bounds = new Rectangle();
		this.attributes = attr;
	}

	@Override
	public Attributes getAttributes() {
		return attributes;
	}

	public abstract Shape getShape();

	@Override
	public void setLocation(Point location) {
		bounds.setLocation(location);
	}

	@Override
	public void setSize(Dimension size) {
		bounds.setSize(size);
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}
	
	public String toString() {
	     return  this.getClass().getSimpleName()+" [x=" + bounds.x + ",y=" + bounds.y + ",width=" +bounds. width + ",height=" + bounds.height + "]";
	}

	public abstract String toCode();
	

	@Override
	public void paint(JComponent parent, Graphics2D g2d) {
		Shape shape = getShape();
		Attributes state = getAttributes();
		Rectangle bounds = getBounds();
		final BasicStroke dashed = new BasicStroke(state.lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { state.dashLength }, 0);
		final BasicStroke solid = new BasicStroke(state.lineWidth);

		g2d.setPaint(state.getForeground());
		g2d.setStroke(solid);

		if (state.isGradient() && bounds.width > 0 && bounds.height > 0) {
			Point2D startPoint = new Point2D.Double(bounds.x, bounds.y);
			Point2D endPoint = new Point2D.Double(bounds.x + bounds.width, bounds.y + bounds.height);
			LinearGradientPaint gp = new LinearGradientPaint(startPoint, endPoint, new float[] { 0f, 1f },
					new Color[] { state.getForeground(), state.getBackground() });
			g2d.setPaint(gp);
		}

		if (state.isFilled()) {
			g2d.fill(shape);
		}

		if (state.isDashed()) {
			g2d.setStroke(dashed);
		}

		g2d.draw(shape);
	}
}