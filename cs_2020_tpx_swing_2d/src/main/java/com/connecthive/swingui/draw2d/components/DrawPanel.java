package com.connecthive.swingui.draw2d.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import com.connecthive.swingui.draw2dapi.IDrawPanel;
import com.connecthive.swingui.draw2dapi.IDrawable;
import com.connecthive.swingui.draw2dapi.IDrawingApp;

public class DrawPanel extends JPanel implements IDrawPanel {
	private IDrawingApp owner;

	public DrawPanel() {
	
	}
	
	
	public void setOwner(IDrawingApp owner) {
		this.owner = owner;
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for (IDrawable d : owner.getModel()) {
			d.paint(this, g2d);
		}
		g2d.dispose();
	}

	public void addDrawable(IDrawable drawable) {
		owner.getModel().add(drawable);
		repaint();
	}


	@Override
	public void setMousePos(String position) {
	   owner.setMousePos(position);
	}


	@Override
	public void clear() {
		owner.getModel().clear();
		repaint();
	}


	@Override
	public void undo() {
		List<IDrawable> itemsDrawn = owner.getModel();
		if (itemsDrawn.size() != 0) {
			itemsDrawn.remove(itemsDrawn.size() - 1);
			repaint();
		}
		
	}
}