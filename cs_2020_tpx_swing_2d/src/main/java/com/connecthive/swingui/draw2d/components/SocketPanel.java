package com.connecthive.swingui.draw2d.components;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.connecthive.swingui.draw2dapi.IDrawPanel;
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
	
	
	private IDrawPanel drawPanel_;
	private JTextField tip;
	private JButton echo;
	private JButton connect;
	private JTextArea logTxt;
	private JLabel res;
	private static final int MAX_LOG_LINES = 18;
	private String log;
	private int lign_ = 0;

	private IClientControler controler;
	private JTextField messageTextField;


	private IDrawingApp drawingApplication;


	public void notifyId(String m) {
		log(m);
	}
	
	
	@Override
	public void setControler(IClientControler controler) {
		this.controler = controler;
	}



	public void initialize() {
	
		setBounds(560, 90, 729, 111);
		setLayout(null);

		tip = new JTextField();
		tip.setText("192.168.1.50");
		tip.setFont(new Font("Arial", Font.PLAIN, 15));
		tip.setSize(132, 20);
		tip.setLocation(20, 15);
		add(tip);

		//gengp = new ButtonGroup();

		echo = new JButton("Echo");
		echo.setFont(new Font("Arial", Font.PLAIN, 15));
		echo.setSize(75, 20);
		echo.setLocation(538, 15);
		echo.addActionListener(this);
		add(echo);

		connect = new JButton("Connect");
		connect.setFont(new Font("Arial", Font.PLAIN, 15));
		connect.setSize(98, 20);
		connect.setLocation(162, 15);
		connect.addActionListener(this);
		add(connect);

		logTxt = new JTextArea();
		logTxt.setFont(new Font("Arial", Font.PLAIN, 15));
		logTxt.setSize(699, 45);
		logTxt.setLocation(20, 46);
		logTxt.setLineWrap(true);
		logTxt.setEditable(false);
		add(logTxt);

		res = new JLabel("");
		res.setFont(new Font("Arial", Font.PLAIN, 20));
		res.setSize(500, 25);
		res.setLocation(100, 500);
		add(res);
		
		messageTextField = new JTextField();
		messageTextField.setBounds(270, 16, 258, 20);
		add(messageTextField);
		messageTextField.setColumns(10);

		setVisible(true);
	}	
	public SocketPanel() {
		initialize();
	}
	
	private void logm(String message) { // send
        lign_ ++;
        if (lign_ == MAX_LOG_LINES) {
        	lign_ = 0;
        	log = "";
        }
		log += message + "\r\n";
		logTxt.setText(log);
	}

	private void addMessage() { // send
		String message = messageTextField.getText();
		if (message.isEmpty())
			message = "null";
		logm(">"+message);
	//	resadd.append(message+"\r\n");
		if (controler !=null)
		  controler.sendMessage(message);
	}

	private void connect() {
		String ip = tip.getText();
		if (!ip.isEmpty()) {
			logm("connect:" + ip);// + "\r\n";
			if (controler !=null)
				  controler.connect(ip, IConstants.DEFAULT_PORT);
		}
	}

	private void disconnect() {
		if (controler !=null)
			  controler.disconnect();
	}

	public void handleMessage(String message) { // receive
		if (!message.isEmpty()) 
			logm(message);
	}

	private void disconnect_() {
		clearView();
		if (controler != null)
			controler.jDisconnect();
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

	public IModel getModel() {
		if (controler !=null)
		
		return controler.getModel();
		else
			return null;
	}

	public void setControler(Object controler) {
		this.controler = (IClientControler) controler;
		if (controler !=null)
		this.controler.setGui((IWsGui) this);
		// TODO Auto-generated method stub
	}

	public void notifySocketClosed() {
		logm( "socket closed");
	}

	public void log(String mesg) {
  
		logm(mesg);

	}

	public void clearView() {
		log = "";
		lign_ = 0;
		logTxt.setText(log);

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