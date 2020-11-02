package com.connecthive.swingui.draw2d.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.connecthive.swingui.draw2dapi.ILogPanel;

public class LogPanel extends JPanel implements ILogPanel {

	private static final int MAX_LOG_LINES = 35;
	private JTextArea logtxt;
	private JButton btnClear;
	private int lign;
	private String log="";

	private void logm(String message) { // send
		lign++;
		if (lign == MAX_LOG_LINES) {
			lign = 0;
			log = "";
		}
		log += message + "\n";
		logtxt.setText(log);
	}

	private void initialize() {
		setForeground(Color.CYAN);
		setBackground(Color.ORANGE);
		logtxt = new JTextArea();
		logtxt.setRows(35);
		logtxt.setText("");//azezae\nhfghf");
		logtxt.setFont(new Font("Arial", Font.PLAIN, 9));
		logtxt.setSize(340, 300);
		logtxt.setLineWrap(true);
		logtxt.setEditable(false);

		add(logtxt);
		btnClear = new JButton("clear");
		btnClear.setHorizontalAlignment(SwingConstants.LEFT);
		add(btnClear);

	}

	public LogPanel() {
		initialize();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(350, 500);
	}

	@Override
	public void log(String mesg) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				logm(mesg);
			}
		});

	}

	@Override
	public void clearLog() {
		lign = 0;
		;
		log = "";
		logtxt.setText(log);
	}

}