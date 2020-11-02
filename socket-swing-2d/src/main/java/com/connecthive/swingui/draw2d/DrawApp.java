package com.connecthive.swingui.draw2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.connecthive.swingui.draw2d.components.ControlPanel;
import com.connecthive.swingui.draw2d.components.DrawPanel;
import com.connecthive.swingui.draw2d.components.LogPanel;
import com.connecthive.swingui.draw2d.components.SocketPanel;
import com.connecthive.swingui.draw2d.figures.LineFigure;
import com.connecthive.swingui.draw2d.figures.OvalFigure;
import com.connecthive.swingui.draw2d.figures.RectangleFigure;
import com.connecthive.swingui.draw2dapi.IControlPanel;
import com.connecthive.swingui.draw2dapi.IDrawingApp;
import com.connecthive.swingui.draw2dapi.IFigure;
import com.connecthive.swingui.draw2dapi.ILogPanel;
import com.connecthive.swingui.draw2dapi.Attributes;
import com.connecthive.swingui.draw2dapi.IDrawPanel;
import com.connecthive.swingui.draw2dapi.ISocketPanel;

import api.IClientControler;
import api.IConstants;
import api.IWsGui;

// see https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html
// see https://docs.oracle.com/javase/tutorial/uiswing/layout/index.html



public class DrawApp extends JFrame implements IDrawingApp,IWsGui {

	//private JButton btnA;
	private JTextField txtFoobar;

	private JPanel canvas;
	private JPanel header;
	private JPanel center;
	private JPanel status;
	private JPanel logp;
	private JPanel controlp;
    private JPanel socketp;
    
 
	private List<IFigure> model = new ArrayList<IFigure>();


	public DrawApp() {
		initialize();
	}
	
	public void addFigure(IFigure figure) {
		model.add(figure);
		canvas.repaint();
	}
	
	

	@Override
	public void setFigure(IFigure figure) {
		String fig = figure.toCode();
		log (fig);
		controler.sendMessage(fig);
	}



	@Override
	public void clearDrawing() {
		model.clear();
		canvas.repaint();
	}

	@Override
	public void undoDrawing() {
		if (model.size() != 0) {
			model.remove(model.size() - 1);
			canvas.repaint();
		}
	}

	
	
	private void initialize() {
		setTitle("DrawingSocketClient");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		header = new JPanel();
		BorderLayout bl = new BorderLayout();
		bl.setHgap(5);
		bl.setVgap(5);
		header.setLayout(bl);
		cp.add(header, BorderLayout.NORTH);
		controlp = new ControlPanel();
	    socketp = new SocketPanel();
	    header.add(controlp, BorderLayout.CENTER);
	    header.add(socketp, BorderLayout.SOUTH);
		FlowLayout ff = new FlowLayout();
		ff.setAlignment(FlowLayout.LEADING);
		center = new JPanel();
		center.setLayout(ff);
		cp.add(center, BorderLayout.CENTER);
		canvas = new DrawPanel();
		center.add(canvas);
		logp = new LogPanel();
		center.add(logp);
		status = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEADING);
	    status.setLayout(fl);
		cp.add(status, BorderLayout.SOUTH);
		txtFoobar = new JTextField();
		txtFoobar.setText("Foobar");
		status.add(txtFoobar);
		txtFoobar.setColumns(10);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DrawApp app = new DrawApp();
				app.setup();
				//frame.createAndShowGUI();
			}
		});
	}

	@Override
	public void setup() {
		((IControlPanel)controlp).setOwner(this);
		((IDrawPanel)canvas).setOwner(this);
		((ISocketPanel)socketp).setOwner(this);
	}
	

	@Override
	public IFigure createFigure() {
		IFigure figure= null;
		Attributes attributes = ((IControlPanel)controlp).getAttributes();
		String selected = getSelectedFigure();
		if ("rectangle".equalsIgnoreCase(selected)) {
			figure = new RectangleFigure(attributes);
		} else if ("oval".equalsIgnoreCase(selected)) {
			figure = new OvalFigure(attributes);
		} else if ("line".equalsIgnoreCase(selected)) {
			figure = new LineFigure(attributes);
		}
		return figure;
	}


	@Override
	public List<IFigure> getModel() {
		return model;
	}

	@Override
	public String getSelectedFigure() {
		return((IControlPanel)controlp).getSelectedFigure();
	}

	private IClientControler controler;
	
	@Override
	public void setControler(Object controler) {
		this.controler = (IClientControler) controler;
		
	}

	@Override
	public void handleMessage(String message) {
		log("handleMessage "+message);
		
	}

	@Override
	public void notifySocketClosed() {
		log("socket closed");
	}

	@Override
	public void log(final String log) {
		((ILogPanel )logp).log(log);
	}


	@Override
	public void clearLog() {
		((ILogPanel )logp).clearLog();	
	}

	
	@Override
	public void clearView() {
		((ILogPanel )logp).clearLog();	
		
	}

	@Override
	public void refreshFig() {
		canvas.repaint();
		
	}

	@Override
	public void refresh(Rectangle rect) {
		canvas.repaint();
		
	}

	@Override
	public void refreshCanvas() {
		canvas.repaint();
		
	}

	@Override
	public void notifySocketError() {
		log("socket error");
		
	}

	@Override
	public void notify(Object changed) {
		log("notify "+changed.toString());
		
	}

	@Override
	public void notifyId(String id) {
		log("id="+id);
		
	}

	@Override
	public void setId(Object oi) {
		int[] id = (int[]) oi;
		log("id="+id[0]+"."+id[1]+"."+id[2]);
		
	}



	@Override
	public void setMousePos(String position) {
		// TODO Auto-generated method stub
		
	}

	
	
	private void addOval(String message) {
		String[] p = message.split(" ");
		int x = Integer.parseInt(p[1]);
		int y = Integer.parseInt(p[2]);
		int w = Integer.parseInt(p[3]);
		int h = Integer.parseInt(p[4]);

		int fgColor = Integer.parseInt(p[5]);
		int bgColor = Integer.parseInt(p[6]);
		boolean isGradient = Boolean.parseBoolean(p[7]);
		boolean isFilled = Boolean.parseBoolean(p[8]);
		boolean isDashed = Boolean.parseBoolean(p[9]);
		int lineWidth = Integer.parseInt(p[10]);
		int dashLength = Integer.parseInt(p[11]);
		
		Attributes attr = new Attributes(new Color(fgColor), new Color(bgColor), isGradient, isFilled, isDashed, lineWidth, dashLength);
		IFigure f = new OvalFigure(attr);
		Dimension size = new Dimension(w,h);
		Point location = new Point(x,y);
		f.setSize(size);
		f.setLocation(location);
		addFigure(f); //oval 234 141 31 57
	}
	
	
	private void addRectangle(String message) {
		String[] p = message.split(" ");
		int x = Integer.parseInt(p[1]);
		int y = Integer.parseInt(p[2]);
		int w = Integer.parseInt(p[3]);
		int h = Integer.parseInt(p[4]);
		
		int fgColor = Integer.parseInt(p[5]);
		int bgColor = Integer.parseInt(p[6]);
		boolean isGradient = Boolean.parseBoolean(p[7]);
		boolean isFilled = Boolean.parseBoolean(p[8]);
		boolean isDashed = Boolean.parseBoolean(p[9]);
		int lineWidth = Integer.parseInt(p[10]);
		int dashLength = Integer.parseInt(p[11]);
		
		Attributes attr = new Attributes(new Color(fgColor), new Color(bgColor), isGradient, isFilled, isDashed, lineWidth, dashLength);
		IFigure f = new RectangleFigure(attr);
		Dimension size = new Dimension(w,h);
		Point location = new Point(x,y);
		f.setSize(size);
		f.setLocation(location);
		addFigure(f); //oval 234 141 31 57
	}
	
	private void addLine(String message) {
		String[] p = message.split(" ");
		int x = Integer.parseInt(p[1]);
		int y = Integer.parseInt(p[2]);
		int w = Integer.parseInt(p[3]);
		int h = Integer.parseInt(p[4]);
		
		int fgColor = Integer.parseInt(p[5]);
		int bgColor = Integer.parseInt(p[6]);
		boolean isGradient = Boolean.parseBoolean(p[7]);
		boolean isFilled = Boolean.parseBoolean(p[8]);
		boolean isDashed = Boolean.parseBoolean(p[9]);
		int lineWidth = Integer.parseInt(p[10]);
		int dashLength = Integer.parseInt(p[11]);
		
		Attributes attr = new Attributes(new Color(fgColor), new Color(bgColor), isGradient, isFilled, isDashed, lineWidth, dashLength);
		IFigure f = new LineFigure(attr);
		Dimension size = new Dimension(w,h);
		Point location = new Point(x,y);
		f.setSize(size);
		f.setLocation(location);
		addFigure(f); //oval 234 141 31 57
	}
	
	@Override
	public void addCommand(String message) {
		log("cmd="+message);
		if (message.startsWith("oval")) {
			addOval(message);
		} else 	if (message.startsWith("rectangle")) {
			addRectangle(message);
		} else 	if (message.startsWith("line")) {
			addLine(message);
		}
		
		
	}

	@Override
	public void connect(String ip, int port) {
		log("connect ip="+ip+" port="+port);
		controler.connect(ip, port);
		
	}
	


	@Override
	public void disconnect() {
		log("disconnect");
		
	}

	@Override
	public void addFigure(String figure) {
		addCommand(figure);	
	}







}
