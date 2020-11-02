package com.connecthive.swingui.draw2d.components;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;

import com.connecthive.swingui.draw2dapi.IDrawingApp;
import com.connecthive.swingui.draw2dapi.ISocketPanel;

import api.IClientControler;
import api.IConstants;
import api.IModel;
import api.IWsGui;


public class SocketPanel extends JPanel implements ActionListener ,ISocketPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303940403563376110L;
	
	private String foo = "Foo";
	private String bar = "Bar";

	private JTextField tip;
	private JTextField portTextField;
	private JButton echo;
	private JButton connect;
	private JLabel res;
	private JTextField messageTextField;


	private IDrawingApp drawingApplication;


	public void notifyId(String m) {
		log(m);
	}
	

	public void initialize() {
	
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEADING);
		this.setLayout(layout);

		tip = new JTextField();
	
		tip.setText(IConstants.DEFAULT_HOST);
		tip.setFont(new Font("Arial", Font.PLAIN, 15));
		tip.setSize(132, 20);
		
		add(tip);

		portTextField = new JTextField();
		
		portTextField.setText(""+IConstants.DEFAULT_PORT);
		portTextField.setFont(new Font("Arial", Font.PLAIN, 15));
		portTextField.setSize(132, 20);
		
		add(portTextField);
		
		//gengp = new ButtonGroup();

		echo = new JButton("Echo");
		echo.setFont(new Font("Arial", Font.PLAIN, 15));
		echo.setSize(75, 20);

		echo.addActionListener(this);
		add(echo);

		connect = new JButton("Connect");
		connect.setFont(new Font("Arial", Font.PLAIN, 15));
		connect.setSize(98, 20);

		connect.addActionListener(this);
		add(connect);

		res = new JLabel("");
		res.setFont(new Font("Arial", Font.PLAIN, 20));
		res.setSize(500, 25);
		add(res);
		
		messageTextField = new JTextField();
		messageTextField.setSize( 258, 20);
		add(messageTextField);
		messageTextField.setColumns(10);
		//messageTextField.setText("circle 10 20 30");
		messageTextField.setText("oval 200 100 40 40");
		

		setVisible(true);
	}	
	public SocketPanel() {
		initialize();
	}
	
	private void logm(String message) { // send
		drawingApplication.log(message);
	}

	private void addMessage() { // send
		String message = messageTextField.getText();
		if (message.isEmpty())
			message = "null";
		logm(">"+message);
		this.drawingApplication.addCommand(message);
	}

	private void connect() {
		String ip = tip.getText();
		int port = Integer.parseInt(portTextField.getText());
		
		if (!ip.isEmpty()) {
			logm("connect:" + ip);
			this.drawingApplication.connect(ip, port);
		}
	}

	private void disconnect() {
		this.drawingApplication.disconnect();
	}

	public void handleMessage(String message) { // receive
		if (!message.isEmpty()) 
			logm(message);
	}

	private void disconnect_() {
		clearView();
		this.drawingApplication.disconnect();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("Connect"))
			connect();
		else if (ac.equals("Disconnect"))
			disconnect();
		else if (ac.equals("Echo"))
			addMessage();
	}


	public void notifySocketClosed() {
		logm( "socket closed");
	}

	public void log(String mesg) {
  
		logm(mesg);

	}

	public void clearView() {
		drawingApplication.clearLog();
	}

	public void refreshView() {
		log("refreshView");

	}

	public void refresh(Rectangle ovni) {
		log("refresh ovni");
	}

	public void refreshCanvas() {
		log("refresh canvas");
	}

	public void notifySocketError() {
		log("SocketError");
	}

	public void notify(Object changed) {
		log("changed " + changed.toString());
	}

	public static void main(String[] args) throws Exception {
		//ClientGui f = new ClientGui();
	}

	@Override
	public void setId(Object oi) {
		int[] id = (int[]) oi;
		log("id="+id[0]+"."+id[1]+"."+id[2]);
	}

	@Override
	public void setOwner(IDrawingApp drawingApplication) {
	   this.drawingApplication =drawingApplication;
	}



}