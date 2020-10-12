package com.connecthive.swingui.draw2d.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import com.connecthive.swingui.draw2d.figures.LineFigure;
import com.connecthive.swingui.draw2d.figures.OvalFigure;
import com.connecthive.swingui.draw2d.figures.RectangleFigure;
import com.connecthive.swingui.draw2dapi.IDrawPanel;
import com.connecthive.swingui.draw2dapi.IFigure;
import com.connecthive.swingui.draw2dapi.IDrawingApp;
import com.connecthive.swingui.draw2dapi.Attributes;

public class DrawPanel extends JPanel implements IDrawPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4607498237817300481L;

	private IDrawingApp owner;

	public DrawPanel() {
		setForeground(Color.GREEN);
		setBackground(Color.PINK);
	}

	public void setOwner(IDrawingApp owner) {
		this.owner = owner;
		setupListeners();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (owner != null && !owner.getModel().isEmpty()) {
			Graphics2D g2d = (Graphics2D) g.create();
			if (owner != null)
				for (IFigure d : owner.getModel())
					d.paint(this, g2d);
			g2d.dispose();
		}
	}


	@Override
	public void setMousePos(String position) {
		owner.setMousePos(position);
	}



	private void setupListeners() {
		MouseHandler mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}

	public class MouseHandler extends MouseAdapter {
		private IFigure figure;
		private Point clickPoint;

		@Override
		public void mouseReleased(MouseEvent e) {
			owner.setFigure(figure);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			figure = owner.createFigure();
			figure.setLocation(e.getPoint());
			owner.addFigure(figure);
			clickPoint = e.getPoint();
			String position = "(" + e.getX() + "," + e.getY() + ")";
			setMousePos(position);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point drag = e.getPoint();
			Point start = clickPoint;

			int maxX = Math.max(drag.x, start.x);
			int maxY = Math.max(drag.y, start.y);
			int minX = Math.min(drag.x, start.x);
			int minY = Math.min(drag.y, start.y);
			int width = maxX - minX;
			int height = maxY - minY;

			if (owner.getSelectedFigure().equalsIgnoreCase("line")) {
				figure.setLocation(start);
				figure.setSize(new Dimension(drag.x - start.x, drag.y - start.y));
			} else {
				figure.setLocation(new Point(minX, minY));
				figure.setSize(new Dimension(width, height));
			}
			String position = "(" + start.x + "," + start.y + ") - (" + drag.x + "," + drag.y + ")";
			setMousePos(position);
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			String position = "(" + e.getPoint().x + "," + e.getPoint().y + ")";
			// mousePos.setText(position);
			setMousePos(position);
		}
	}

}