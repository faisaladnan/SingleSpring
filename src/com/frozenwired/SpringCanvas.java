package com.frozenwired;

import java.util.Vector;

import net.rim.device.api.system.KeypadListener;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.TouchGesture;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class SpringCanvas extends Field implements Canvas,FieldChangeListener {
	private Vector listeners = new Vector();
	private int xa = 100;
	private int ya = 50;
	private int drawAreaWidth = 100;
	private double verticalScaleConstant = 40;
	private Spring spring;
	private boolean isClicked = false;
	public SpringCanvas(Spring spring)
	{
		this.spring = spring;
		setChangeListener(this);
	}
	protected void layout(int width, int height) {
		setExtent(width, 200);
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(0, 0, 300, 200);
		
		// draw initial line
		g.drawLine( xa+drawAreaWidth/2, 
					ya-25, 			
					xa+drawAreaWidth/2,
					ya 
					);
		// draw line from point 0 to 1
		g.drawLine( xa+drawAreaWidth/2, // x0 
					ya, 				// y0
					xa+drawAreaWidth,   // x1
					(int)(spring.getLen()*verticalScaleConstant)/6 + ya // y1
					);
		// draw line from point 1 to 2
		g.drawLine( xa+drawAreaWidth, // x1 
					(int)(spring.getLen()*verticalScaleConstant)/6 + ya, // y1
					xa,   // x2
					(int)(spring.getLen()*verticalScaleConstant)/2 + ya // y2
					);
		// draw line from point 2 to 3
		g.drawLine( xa,   // x2
					(int)(spring.getLen()*verticalScaleConstant)/2 + ya, // y2
					xa+drawAreaWidth,   // x3
					(int)(spring.getLen()*verticalScaleConstant)*5/6 + ya // y3
					);		
		// draw line from point 3 to 4
		g.drawLine( xa+drawAreaWidth,   // x3
					(int)(spring.getLen()*verticalScaleConstant)*5/6 + ya, // y3
					xa+drawAreaWidth/2, // x4
					(int)(spring.getLen()*verticalScaleConstant) + ya // y4
					);				
		
		// draw mass
		if (isFocus())
		{
			g.setColor(Color.BLUE);
			g.drawRect(xa, 
					(int)(spring.getLen()*verticalScaleConstant) + ya, // y4 
					100, 25);
		} else
		{
			g.setColor(Color.BLACK);
			g.drawRect(xa, 
					(int)(spring.getLen()*verticalScaleConstant) + ya, // y4 
					100, 25);			
		}
		
	}
	
	public void repaint()
	{
		synchronized (UiApplication.getUiApplication().getEventLock()) {
			invalidate();
		}
	}
	
	public boolean isFocusable()
	{
		return true;
	}
	
	protected void drawFocus(Graphics graphics, boolean on)
	{
		
	}
	protected void onFocus(int direction)
	{
		super.onFocus(direction);
		invalidate();
	}
	
	protected void onUnFocus() 
	{
		super.onUnfocus();
		invalidate();
	}
	
	protected boolean navigationClick(int status, int time)
	{
		System.out.println("navigationClick!");	
		if (this.isClicked)
			this.isClicked = false;
		else
			this.isClicked = true;
		fieldChangeNotify(0);
		for (int i=0;i<listeners.size();i++)
		{
			Object obj = listeners.elementAt(i);
			if (obj instanceof CanvasListener)
			{
				CanvasListener listener = (CanvasListener)obj;
				listener.onCanvasClicked(this);
			}
		}
		return true;
	}
	protected boolean navigationMovement(int dx, int  dy, int status, int time)
	{
		System.out.println("navigationMovement!");				
		fieldChangeNotify(0);
		if (this.isClicked)
		{
			for (int i=0;i<listeners.size();i++)
			{
				Object obj = listeners.elementAt(i);
				if (obj instanceof CanvasListener)
				{
					CanvasListener listener = (CanvasListener)obj;
					listener.onCanvasMoved(this, dy);
				}
			}			
			return true;
		} else
			return false;
	}
	protected boolean navigationUnclick(int status, int time)
	{
		System.out.println("navigationUnclick!");						
		fieldChangeNotify(0);
		for (int i=0;i<listeners.size();i++)
		{
			Object obj = listeners.elementAt(i);
			if (obj instanceof CanvasListener)
			{
				CanvasListener listener = (CanvasListener)obj;
				listener.onCanvasReleased(this);
			}
		}
		return true;
	}
	public void addCanvasListener(CanvasListener canvasListener) {
		listeners.addElement(canvasListener);		
	}
	public void removeCanvasListener(CanvasListener canvasListener) {
		listeners.removeElement(canvasListener);
	}
	public void fieldChanged(Field field, int context) {
		System.out.println("fieldChanged!");		
	}
	
	public double getVerticalScaleConstant()
	{
		return this.verticalScaleConstant;
	}
}
