package com.connecthive.swingui.draw2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import api.IWsGui;

//author https://raw.githubusercontent.com/KoalaChelsea/2D-Drawing-Application/master/DrawingApplication.java

public class DrawingApplication extends JFrame implements IDrawingApp,IWsGui {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3486205043460614466L;
	/**
	 * 
	 */

	List<Drawable> model = new ArrayList<Drawable>();
	private JLabel mousePos;
	private DrawPanel dp;// = new DrawPanel();
	private ControlPanel cp;// = new ControlPanel();
//com.connecthive.swingui.draw2d.DrawingApplication
	//private Point start, end;

	public void clog(String mesg) {
		System.out.println(mesg);
	}

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		// Create the frame.
		// JFrame frame = new JFrame("Java 2D Drawings");
		// Optional: What happens when the frame closes?
		setTitle("Socket Draw2D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create components and put them in the frame.
		dp = new DrawPanel();
		cp = new ControlPanel();
		JLabel coordinates = new JLabel("Mouse Coordinates");
		coordinates.setForeground(Color.BLUE);
		add(coordinates, BorderLayout.SOUTH);
		setLayout(new BorderLayout());

		JPanel panel_ = new JPanel();
		panel_.setLayout(new BorderLayout());
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		JPanel control_ = new JPanel();
		mousePos = new JLabel("( , )");
		bottom.add(mousePos, BorderLayout.WEST);
		bottom.setVisible(true);

		//start = end = null;

	
		
		control_.add(cp);
		panel_.add(control_, BorderLayout.NORTH);
		dp.setLayout(new GridLayout());
		dp.setVisible(true);
		dp.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(0, 0, 20, 30)));
		dp.setBackground(Color.WHITE);
		panel_.add(dp, BorderLayout.CENTER);
		panel_.setVisible(true);
		panel_.add(bottom, BorderLayout.SOUTH);
		add(panel_, BorderLayout.PAGE_START);

		// frame.add(this, BorderLayout.NORTH);

		// Size the frame
		pack();
		// Centers a frame on screen
		setLocationRelativeTo(null);
		// Show it.
		setVisible(true);
	}
	
	
	public DrawingApplication() {
   
	}

	public void setupListeners() {
		dp.setOwner(this);
		cp.setupListeners(dp);
	}

	public static void main(String[] args) {
		DrawingApplication drawingApplication = new DrawingApplication();
		drawingApplication.initialize();
		drawingApplication.setupListeners();

	}

	@Override
	public List<Drawable> getModel() {
		return model;
	}

	@Override
	public void setMousePos(String position) {
		mousePos.setText(position);
	}


	/* ----------------- TODO ---------------------*/
	
	
	@Override
	public void setControler(Object controler) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void handleMessage(String message) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifySocketClosed() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void log(String string) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void clearView() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refresh(Rectangle ovni) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refreshCanvas() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifySocketError() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notify(Object changed) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyId(String string) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setId(Object identifier) {
		// TODO Auto-generated method stub
		
	}
}