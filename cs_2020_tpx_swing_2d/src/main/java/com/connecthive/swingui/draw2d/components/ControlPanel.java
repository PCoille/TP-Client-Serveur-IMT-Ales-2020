package com.connecthive.swingui.draw2d.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.connecthive.swingui.draw2d.figures.LineFigure;
import com.connecthive.swingui.draw2d.figures.OvalFigure;
import com.connecthive.swingui.draw2d.figures.RectangleFigure;
import com.connecthive.swingui.draw2dapi.IControlPanel;
import com.connecthive.swingui.draw2dapi.IDrawPanel;
import com.connecthive.swingui.draw2dapi.IDrawable;
import com.connecthive.swingui.draw2dapi.IDrawingApp;
import com.connecthive.swingui.draw2dapi.State;


public class ControlPanel extends JPanel implements IControlPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303940403563376110L;
	
	
	public final JComboBox<String> shapes;
	private final JButton foreground, background;
	private final JCheckBox gradient, filled_, dashed;
	private final JTextField lineWidth, dashLength;
	private final JLabel width, dash;
	private JPanel panel;
	private IDrawPanel drawPanel;
	private JButton clear, undo;


	private IDrawingApp drawingApplication;

	public ControlPanel() {
		shapes = new JComboBox<>();
		shapes.addItem("Rectangle");
		shapes.addItem("Oval");
		shapes.addItem("Line");
		foreground = new JButton("1st Color");
		foreground.setBackground(Color.BLACK);
		foreground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				foreground.setBackground(JColorChooser.showDialog(null, "Pick your color", Color.BLACK));
			}
		});

		background = new JButton("2nd Color");
		background.setBackground(Color.WHITE);
		background.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				background.setBackground(JColorChooser.showDialog(null, "Pick your color", Color.BLACK));
			}
		});

		gradient = new JCheckBox("Use Gradient");
		filled_ = new JCheckBox("Filled");
		dashed = new JCheckBox("Dashed");
		dashLength = new JTextField("10");
		lineWidth = new JTextField("2");
		dash = new JLabel("Dash Length:");
		width = new JLabel("Line Width:");
		clear = new JButton("Clear");
		undo = new JButton("Undo");
		JPanel cbpanel = new JPanel();
		cbpanel.add(foreground);
		cbpanel.add(background);
		cbpanel.add(filled_);
		setLayout(new FlowLayout());
		
		
		
		GridBagConstraints gbcb = new GridBagConstraints();
		gbcb.gridwidth = GridBagConstraints.REMAINDER;
		gbcb.fill = GridBagConstraints.HORIZONTAL;
		gbcb.weightx = 1;
		gbcb.weighty = 1;
		gbcb.anchor = GridBagConstraints.NORTH;
		add(clear, gbcb);
		
		
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.gridwidth = GridBagConstraints.REMAINDER;
		gbca.fill = GridBagConstraints.HORIZONTAL;
		gbca.weightx = 1;
		gbca.weighty = 1;
		gbca.anchor = GridBagConstraints.NORTH;
		add(undo, gbca);
		
		GridBagConstraints gbc0 = new GridBagConstraints();
		gbc0.gridwidth = GridBagConstraints.REMAINDER;
		gbc0.fill = GridBagConstraints.HORIZONTAL;
		gbc0.weightx = 1;
		gbc0.weighty = 1;
		gbc0.anchor = GridBagConstraints.NORTH;
		add(new JLabel("Shape: "));
		add(shapes, gbc0);
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridwidth = GridBagConstraints.REMAINDER;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.weightx = 1;
		gbc1.weighty = 1;
		gbc1.anchor = GridBagConstraints.NORTH;
		add(cbpanel, gbc1);
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1;
		gbc2.weighty = 1;
		gbc2.anchor = GridBagConstraints.NORTH;
		add(gradient, gbc2);
		
		
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridwidth = GridBagConstraints.REMAINDER;
		gbc4.fill = GridBagConstraints.HORIZONTAL;
		gbc4.weightx = 1;
		gbc4.weighty = 1;
		gbc4.anchor = GridBagConstraints.NORTH;
		add(dashed, gbc4);
		
	
		add(dash);
		add(dashLength);
		add(width);
		add(lineWidth);
	}
	
	
	
	public void setupListeners(IDrawPanel panel_) {
		this.drawPanel = panel_;
		MyMouseHandler mouseHandler = new MyMouseHandler();
		((JPanel) drawPanel).addMouseListener(mouseHandler);
		((JPanel) drawPanel).addMouseMotionListener(mouseHandler);

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				    drawPanel.clear();
			}
		});

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 drawPanel.undo();
			}
		});		
	}

	public int getDash() {
		String length = dashLength.getText();
		int dash = Integer.parseInt(length);
		return dash;
	}

	public int getLine() {
		String width = lineWidth.getText();
		int line = Integer.parseInt(width);
		return line;
	}

	protected IDrawable createFigure() {
		IDrawable figure= null;
		State state = new State(foreground.getBackground(), background.getBackground(), gradient.isSelected(),
				filled_.isSelected(), dashed.isSelected(), getLine(), getDash());
		String selected = (String) shapes.getSelectedItem();
		if ("rectangle".equalsIgnoreCase(selected)) {
			figure = new RectangleFigure(state);
		} else if ("oval".equalsIgnoreCase(selected)) {
			figure = new OvalFigure(state);
		} else if ("Line".equalsIgnoreCase(selected)) {
			figure = new LineFigure(state);
		}
		return figure;
	}

	public class MyMouseHandler extends MouseAdapter {
		private IDrawable figure;
		private Point clickPoint;

		public void mousePressed(MouseEvent e) {
			figure = createFigure();
			figure.setLocation(e.getPoint());
			drawPanel.addDrawable(figure);
			clickPoint = e.getPoint();
			String position = "(" + e.getX() + "," + e.getY() + ")";
			drawPanel.setMousePos(position);
		}

		public void mouseDragged(MouseEvent e) {
			Point drag = e.getPoint();
			Point start = clickPoint;

			int maxX = Math.max(drag.x, start.x);
			int maxY = Math.max(drag.y, start.y);
			int minX = Math.min(drag.x, start.x);
			int minY = Math.min(drag.y, start.y);
			int width = maxX - minX;
			int height = maxY - minY;

			if (shapes.getSelectedItem().equals("Line")) {
				figure.setLocation(start);
				figure.setSize(new Dimension(drag.x - start.x, drag.y - start.y));
			} else {
				figure.setLocation(new Point(minX, minY));
				figure.setSize(new Dimension(width, height));
			}
			String position = "(" + start.x + "," + start.y + ") - (" + drag.x + "," + drag.y + ")";
			drawPanel.setMousePos(position);
			((JPanel) drawPanel).repaint();
		}
		
		public void mouseMoved(MouseEvent e) {
			String position = "(" + e.getPoint().x + "," + e.getPoint().y + ")";
			//mousePos.setText(position);
			drawPanel.setMousePos(position);
		}
	}

	@Override
	public void setOwner(IDrawingApp drawingApplication) {
		this.drawingApplication = drawingApplication;
		
	}
}