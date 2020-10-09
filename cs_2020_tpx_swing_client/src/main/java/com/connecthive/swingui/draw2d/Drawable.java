package com.connecthive.swingui.draw2d;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;



public interface Drawable {
	public void paint(JComponent parent, Graphics2D g2d);

	public void setLocation(Point location);

	public void setSize(Dimension size);

	public State getState();

	public Rectangle getBounds();
}