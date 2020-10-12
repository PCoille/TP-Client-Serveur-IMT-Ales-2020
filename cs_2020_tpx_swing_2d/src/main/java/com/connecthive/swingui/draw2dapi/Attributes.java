package com.connecthive.swingui.draw2dapi;

import java.awt.Color;

public class Attributes {

	private Color foreground, background;
	private boolean gradient, filled, dashed;
	public int lineWidth;
	public int dashLength;

	public Attributes(Color foreground, Color background, boolean gradient, boolean filled, boolean dashed,
			int lineWidth, int dashLength) {
		this.foreground = foreground;
		this.background = background;
		this.gradient = gradient;
		this.filled = filled;
		this.dashed = dashed;
		this.lineWidth = lineWidth;
		this.dashLength = dashLength;
	}

	public Attributes() {
		this.foreground = Color.black;
		this.background  = Color.white;
		this.gradient = false;
		this.filled = false;
		this.dashed = false;
		this.lineWidth = 2;
		this.dashLength = 20;
	}

	public Color getForeground() {
		return foreground;
	}

	public Color getBackground() {
		return background;
	}

	public boolean isGradient() {
		return gradient;
	}

	public boolean isFilled() {
		return filled;
	}

	public boolean isDashed() {
		return dashed;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public int getDashLength() {
		return dashLength;
	}
}