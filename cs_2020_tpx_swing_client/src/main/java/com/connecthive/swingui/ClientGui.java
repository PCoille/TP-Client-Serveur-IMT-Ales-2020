package com.connecthive.swingui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java program to implement 
//a Simple Registration Form 
//using Java Swing 
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import api.IClientControler;
import api.IConstants;
import api.IModel;
import api.IWsGui;

public class ClientGui extends JFrame implements ActionListener, IWsGui {


	// Components of the Form
	private Container contentpane;
	private JLabel title;
	private JLabel name;
	private JTextField tip;
	private JLabel mno;
	private JTextField tport;
	private ButtonGroup gengp;
	private JButton echo;
	private JButton connect_;
	private JButton disconnect;
	private JTextArea logTxt_;
	private JLabel res;


	private static final int MAX_LOG_LINES = 18;
	private String log="";
	private int lign_ = 0;

	private IClientControler controler;
	private JTextField messageTextField;


	public void notifyId(String m) {
		log(m);
	}
	
	
	

	public void initialize() {
		setTitle("Client Socket");
		setBounds(560, 90, 449, 601);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		contentpane = getContentPane();
		contentpane.setLayout(null);

		title = new JLabel("Socket Client");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setSize(300, 30);
		title.setLocation(10, 11);
		contentpane.add(title);

		name = new JLabel("Server");
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		name.setSize(58, 24);
		name.setLocation(42, 52);
		contentpane.add(name);

		tip = new JTextField();
		tip.setText("192.168.1.50");
		tip.setFont(new Font("Arial", Font.PLAIN, 15));
		tip.setSize(190, 20);
		tip.setLocation(110, 54);
		contentpane.add(tip);

		mno = new JLabel("SEND");
		mno.setFont(new Font("Arial", Font.PLAIN, 20));
		mno.setSize(53, 24);
		mno.setLocation(47, 115);
		contentpane.add(mno);

		tport = new JTextField();
		tport.setFont(new Font("Arial", Font.PLAIN, 15));
		tport.setSize(150, 20);
		tport.setLocation(110, 82);
		contentpane.add(tport);

		gengp = new ButtonGroup();

		echo = new JButton("Echo");
		echo.setFont(new Font("Arial", Font.PLAIN, 15));
		echo.setSize(75, 20);
		echo.setLocation(347, 119);
		echo.addActionListener(this);
		contentpane.add(echo);

		disconnect = new JButton("Disconnect");
		disconnect.setFont(new Font("Arial", Font.PLAIN, 15));
		disconnect.setSize(131, 20);
		disconnect.setLocation(291, 538);
		disconnect.addActionListener(this);
		contentpane.add(disconnect);

		connect_ = new JButton("Connect");
		connect_.setFont(new Font("Arial", Font.PLAIN, 15));
		connect_.setSize(131, 20);
		connect_.setLocation(291, 507);
		connect_.addActionListener(this);
		contentpane.add(connect_);

		logTxt_ = new JTextArea();
		logTxt_.setFont(new Font("Arial", Font.PLAIN, 15));
		logTxt_.setSize(423, 350);
		logTxt_.setLocation(10, 146);
		logTxt_.setLineWrap(true);
		logTxt_.setEditable(false);
		contentpane.add(logTxt_);

		res = new JLabel("");
		res.setFont(new Font("Arial", Font.PLAIN, 20));
		res.setSize(500, 25);
		res.setLocation(100, 500);
		contentpane.add(res);
		
		messageTextField = new JTextField();
		messageTextField.setBounds(110, 117, 227, 20);
		contentpane.add(messageTextField);
		messageTextField.setColumns(10);
		messageTextField.setText("oval 200 100 40 40");
		
		JLabel lblMsg = new JLabel("Port");
		lblMsg.setFont(new Font("Arial", Font.PLAIN, 20));
		lblMsg.setBounds(63, 80, 37, 24);
		contentpane.add(lblMsg);

		setVisible(true);
	}

	// constructor, to initialize the components
	// with default values.

	public ClientGui() {
		initialize();
	}


	private void logm(String message) { // send
        lign_ ++;
        if (lign_ == MAX_LOG_LINES) {
        	lign_ = 0;
        	log = "";
        }
		log += message + "\r\n";
		logTxt_.setText(log);
	}

	private void addMessage() { // send
		String message = messageTextField.getText();
		if (message.isEmpty())
			message = "null";
		logm(">"+message);
	//	resadd.append(message+"\r\n");
		controler.sendMessage(message);
	}

	private void connect() {
		String ip = tip.getText();
		if (!ip.isEmpty()) {
			logm("connect:" + ip);// + "\r\n";
			controler.connect(ip, IConstants.DEFAULT_PORT);
		}
	}

	private void disconnect() {
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
		return controler.getModel();
	}

	public void setControler(Object controler_) {
		this.controler = (IClientControler) controler_;
		controler.setGui((IWsGui) this);
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
		logTxt_.setText(log);

	}

	@Override
	public void refreshFig() {
		log("refreshFig");

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
		ClientGui f = new ClientGui();
	}

	@Override
	public void setId(Object oi) {
		int[] id = (int[]) oi;
		log("id="+id[0]+"."+id[1]+"."+id[2]);
		
	}




	@Override
	public void setup() {
		boolean tb = true;
		
	}




	@Override
	public void addFigure(String figure) {
		//NOTHING
		
	}
}
//before remove clientgui
