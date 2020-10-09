package com.connecthive.swingui.draw2d;

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


public class ControlPanel extends JPanel implements IControlPanel{

	public final JComboBox<String> shapes;
	private final JButton foreground, background;
	private final JCheckBox gradient, filled_, dashed;
	private final JTextField lineWidth, dashLength;
	private final JLabel width, dash;
	private JPanel panel;
	private IDrawPanel drawPanel_;
	private JButton clear, undo;

	public ControlPanel() {
		//this.drawPanel_ = panel_;
		shapes = new JComboBox<>();//(new String[] { "Rectangle", "Oval", "Line" });
		shapes.addItem("Rectangle");
		shapes.addItem("Oval");
		shapes.addItem("Line");
		foreground = new JButton("1st Color");
		foreground.setBackground(Color.BLACK);
		foreground.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// color1 = JColorChooser.showDialog(null, "Pick your
				// color", Color.BLACK);
				foreground.setBackground(JColorChooser.showDialog(null, "Pick your color", Color.BLACK));
			}
		});

		background = new JButton("2nd Color");
		background.setBackground(Color.WHITE);
		background.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				/*
				 * color2 = JColorChooser.showDialog(null,
				 * "Pick your color.", color2); if (color2 == null) color2 =
				 * Color.WHITE;
				 */
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
	
		//setupListeners();

	}
	
	
	
	public void setupListeners(IDrawPanel panel_) {
		this.drawPanel_ = panel_;
		MyMouseHandler mouseHandler = new MyMouseHandler();
		((JPanel) drawPanel_).addMouseListener(mouseHandler);
		((JPanel) drawPanel_).addMouseMotionListener(mouseHandler);

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				    drawPanel_.clear();
			}
		});

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 drawPanel_.undo();
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

	protected Drawable createDrawable() {
		Drawable drawable = null;
		State state = new State(foreground.getBackground(), background.getBackground(), gradient.isSelected(),
				filled_.isSelected(), dashed.isSelected(), getLine(), getDash());
		String selected = (String) shapes.getSelectedItem();
		if ("rectangle".equalsIgnoreCase(selected)) {
			drawable = new MyRectangle(state);
		} else if ("oval".equalsIgnoreCase(selected)) {
			drawable = new MyOval(state);
		} else if ("Line".equalsIgnoreCase(selected)) {
			drawable = new MyLine(state);
		}
		return drawable;
	}

	public class MyMouseHandler extends MouseAdapter {
		private Drawable drawable;
		private Point clickPoint;

		public void mousePressed(MouseEvent e) {
			drawable = createDrawable();
			drawable.setLocation(e.getPoint());
			drawPanel_.addDrawable(drawable);
			clickPoint = e.getPoint();
			String position = "(" + e.getX() + "," + e.getY() + ")";
			drawPanel_.setMousePos(position);
			//	app.mousePos.setText(position);
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
				drawable.setLocation(start);
				drawable.setSize(new Dimension(drag.x - start.x, drag.y - start.y));
			} else {
				drawable.setLocation(new Point(minX, minY));
				drawable.setSize(new Dimension(width, height));
			}

			// String position = "(" + e.getX() + "," + e.getY() + ")";
			// String position = "(" + minX + "," + minY + ")" + " W=" +
			// width + ", H=" + height;
			String position = "(" + start.x + "," + start.y + ") - (" + drag.x + "," + drag.y + ")";

			//mousePos.setText(position);
			drawPanel_.setMousePos(position);
			((JPanel) drawPanel_).repaint();
		}
		
		public void mouseMoved(MouseEvent e) {
			String position = "(" + e.getPoint().x + "," + e.getPoint().y + ")";
			//mousePos.setText(position);
			drawPanel_.setMousePos(position);
		}
	}
}