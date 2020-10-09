package com.connecthive.swingui.draw2dapi;

import java.awt.Color;

public class State {

	private final Color foreground, background;
	private final boolean gradient, filled, dashed;
	final int lineWidth;
	final int dashLength;

	public State(Color foreground, Color background, boolean gradient, boolean filled, boolean dashed,
			int lineWidth, int dashLength) {
		this.foreground = foreground;
		this.background = background;
		this.gradient = gradient;
		this.filled = filled;
		this.dashed = dashed;
		this.lineWidth = lineWidth;
		this.dashLength = dashLength;
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