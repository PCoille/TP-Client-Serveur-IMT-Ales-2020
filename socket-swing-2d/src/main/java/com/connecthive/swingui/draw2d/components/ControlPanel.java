package com.connecthive.swingui.draw2d.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.connecthive.swingui.draw2d.utils.Utils;
import com.connecthive.swingui.draw2dapi.Attributes;
import com.connecthive.swingui.draw2dapi.IControlPanel;
import com.connecthive.swingui.draw2dapi.IDrawPanel;
import com.connecthive.swingui.draw2dapi.IDrawingApp;


public class ControlPanel extends JPanel implements IControlPanel,ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303940403563376110L;
	
	

	private IDrawingApp drawingApplication;
	

	
	public JComboBox<String> shapes;
	private JButton foreground, background;
	private JCheckBox gradient, filled, dashed;
	private JTextField lineWidthText, dashLengthText;
	private JLabel width, dash;
	private IDrawPanel drawPanel_;
	private JButton clear, undo;



	private int dashLength = 10;



	private int lineWidth;
	
	
	
	public int getLineWidth() {
		return lineWidth;
	}

	public int getDashLength() {
		return dashLength;
	}

	public void initialize() {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEADING);
		this.setLayout(layout);
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
		filled = new JCheckBox("Filled");
		dashed = new JCheckBox("Dashed");
		dashLengthText = new JTextField("10");
		lineWidthText = new JTextField("2");
		dash = new JLabel("Dash Length:");
		width = new JLabel("Line Width:");
		clear = new JButton("Clear");
		undo = new JButton("Undo");
		JPanel cbpanel = new JPanel();
		cbpanel.add(foreground);
		cbpanel.add(background);
		cbpanel.add(filled);
		
		
		add(clear);
		add(undo);
		add(new JLabel("Shape: "));
		add(shapes);
		add(cbpanel);
		add(gradient);
		add(dashed);
		add(dash);
		add(dashLengthText);
		add(width);
		add(lineWidthText);
		Utils.addChangeListener(dashLengthText,this);
	
	}
	
	public ControlPanel() {
		initialize();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource()==dashLengthText) {
			try {
				dashLength = Integer.parseInt(dashLengthText.getText());
				//drawingApplication.log("dash="+dashLengthText.getText());
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			
		} else 	if (e.getSource()==lineWidthText) {
			try {
				lineWidth = Integer.parseInt(lineWidthText.getText());
				//drawingApplication.log("lineWidth="+lineWidthText.getText());
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			
		}
		
	}


	
	private void setupListeners() {
	
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				drawingApplication.clearDrawing();
			}
		});

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingApplication.undoDrawing();
			}
		});		
	}
	


	public int getDash() {
		String length = dashLengthText.getText();
		int dash = Integer.parseInt(length);
		return dash;
	}

	public int getLineWidth_() {
		String width = lineWidthText.getText();
		int w = Integer.parseInt(width);
		return w;
	}

	

	@Override
	public void setOwner(IDrawingApp drawingApplication) {
		this.drawingApplication = drawingApplication;
		setupListeners();
		
	}

	@Override
	public Attributes getAttributes() {
		return new Attributes(foreground.getBackground(), background.getBackground(), gradient.isSelected(),
				filled.isSelected(), dashed.isSelected(), getLineWidth(), getDashLength());
	}

	@Override
	public String getSelectedFigure() {
		return (String) shapes.getSelectedItem();
	}




}