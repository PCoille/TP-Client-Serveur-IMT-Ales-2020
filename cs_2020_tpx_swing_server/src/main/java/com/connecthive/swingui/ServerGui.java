package com.connecthive.swingui;

//Java program to implement 
//a Simple Registration Form 
//using Java Swing 

import javax.swing.*;


import api.IServerController;
import api.IWsGui;

import java.awt.*; 
import java.awt.event.*; 

public class ServerGui extends JFrame implements ActionListener, IWsGui {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5324121173573288824L;
	// Components of the Form 
	private Container c; 
	private JLabel title; 
	private ButtonGroup gengp; 
	private JButton echo; 
	private JButton disconnect; 
	private JTextArea logTxt; 
	private JLabel res; 

	
	private IServerController server;
	private String log;
	private IServerController controler; 

	public void setControler(Object ctrl) {
     	this.controler = (IServerController) ctrl;
     	this.controler.setGui(this);
	}


	public void initialize() {
		setTitle("Server Socket"); 
		setBounds(200, 90, 464, 605); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setResizable(false); 

		c = getContentPane(); 
		c.setLayout(null); 

		title = new JLabel("Socket Server"); 
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		title.setSize(300, 30); 
		title.setLocation(10, 11); 
		c.add(title);

		gengp = new ButtonGroup();

		echo = new JButton("Echo"); 
		echo.setFont(new Font("Arial", Font.PLAIN, 15)); 
		echo.setSize(100, 20); 
		echo.setLocation(10, 525); 
		echo.addActionListener(this); 
		c.add(echo); 

		disconnect = new JButton("Disconnect"); 
		disconnect.setFont(new Font("Arial", Font.PLAIN, 15)); 
		disconnect.setSize(131, 20); 
		disconnect.setLocation(136, 525); 
		disconnect.addActionListener(this); 
		c.add(disconnect); 

		logTxt = new JTextArea(); 
		logTxt.setFont(new Font("Arial", Font.PLAIN, 15)); 
		logTxt.setSize(426, 434); 
		logTxt.setLocation(10, 62); 
		logTxt.setLineWrap(true); 
		logTxt.setEditable(false); 
		c.add(logTxt); 

		res = new JLabel(""); 
		res.setFont(new Font("Arial", Font.PLAIN, 20)); 
		res.setSize(500, 25); 
		res.setLocation(100, 500); 
		c.add(res);

		setVisible(true); 
	}

	public ServerGui() { 
		initialize();
	} 


	public void handleMessage(String message) { //receive
		if (!message.isEmpty()) {
			log += "<" + message + "\r\n";
			logTxt.setText(log);
		}
	}
	
	private void clearMessage() {
		log = "";
		logTxt.setText(log);
		server.jDisconnect();
	}

	public void actionPerformed(ActionEvent e) {
		/*if (e.getSource() == echo) {
			addMessage();
		} else*/
			
			if (e.getSource() == disconnect) {
			clearMessage();
		}
	}



	public void setServer(IServerController server) {
		this.server = server;
		server.setGui((IWsGui)this);
		// TODO Auto-generated method stub	
	}

	

	public void notifySocketClosed() {
		log("socket closed");	
	}

	
	public void log(String message) {
		if (!message.isEmpty()) {
			log += "(" + message + ")\r\n";
			logTxt.setText(log);
		}
	}

	public void clearView() {
		log = "";
		logTxt.setText(log);
	}

	public void refreshView() {
		log("refresh view");
		
	}



	public void refresh(Rectangle ovni) {
		log("refresh ovni");
		
	}

	public void refreshCanvas() {
		log("refresh canvas");
		
	}

	public void notifySocketError() {
		log("socket error");
		
	}

	public void notify(Object changed) {
		log("changed "+changed.toString());
		
	}

	public void notifyId(String id) {
		log("notify id="+id);
	}


	public static void ma_in(String[] args) throws Exception 
	{ 
		ServerGui f = new ServerGui(); 
	}


	@Override
	public void setId(Object identifier) {
		// NOTHING
		
	}


	
	
} 

