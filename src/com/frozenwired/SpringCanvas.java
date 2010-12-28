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
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;

public class SpringCanvas extends Field implements Canvas,FieldChangeListener {
	private Vector listeners = new Vector();
	private int xa = 1;
	private int ya = 20;
	private int drawAreaWidth = 50;
	private double verticalScaleConstant = 40;
	private Spring spring;
	private boolean isClicked = false;
	private int canvasWidth = -1;
	private int canvasHeight = -1;
	public SpringCanvas(Spring spring)
	{
		super(ButtonField.CONSUME_CLICK);
		this.spring = spring;
		setChangeListener(this);
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(), getPreferredHeight());
	}
	public int getPreferredHeight()
	{
		if (canvasHeight < 0)
		{
			return getScreen().getVisibleHeight();
		} else
			return canvasHeight;
		
	}
	public int getPreferredWidth()
	{
		if (canvasWidth < 0)
		{
			return getScreen().getVisibleWidth();
		} else
			return canvasWidth;
	}
	public void setCanvasWidth(int canvasWidth)
	{
		this.canvasWidth = canvasWidth;
	}
	public int getCanvasWidth()
	{
		return this.canvasWidth;
	}	
	public void setCanvasHeight(int canvasHeight)
	{
		this.canvasHeight = canvasHeight;
	}
	public int setCanvasWidth()
	{
		return this.canvasHeight;
	}		
	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
//		g.drawRect(0, 0, 300, 200);
		
		// draw initial line
		g.drawLine( xa+drawAreaWidth/2, 
					0, 			
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
					drawAreaWidth, 25);
		} else
		{
			g.setColor(Color.BLACK);
			g.drawRect(xa, 
					(int)(spring.getLen()*verticalScaleConstant) + ya, // y4 
					drawAreaWidth, 25);			
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
		if ((status & KeypadListener.STATUS_FOUR_WAY) == KeypadListener.STATUS_FOUR_WAY)
		{
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
		} else
		{
			fieldChangeNotify(0);
			return true;
		}
	}
	protected boolean navigationMovement(int dx, int  dy, int status, int time)
	{
		System.out.println("navigationMovement!");	
		if ((status & KeypadListener.STATUS_FOUR_WAY) == KeypadListener.STATUS_FOUR_WAY)
		{
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
		} else
		{
			return super.navigationMovement(dx, dy, status, time);
		}
	}
	protected boolean navigationUnclick(int status, int time)
	{
		System.out.println("navigationUnclick!");		
		if ((status & KeypadListener.STATUS_FOUR_WAY) == KeypadListener.STATUS_FOUR_WAY)
		{
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
		} else
		{
			return super.navigationUnclick(status, time);
		}
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
	protected boolean touchEvent(TouchEvent message) 
	{
		int touchX = message.getX(1);
		int touchY = message.getY(1);
		int y4 = (int)(spring.getLen()*verticalScaleConstant) + ya;
		
		// If click position is in the rectangle mass
		if ((touchX >= xa) && (touchX <= xa+drawAreaWidth)
				&& (touchY >= y4) && (touchY <= y4+25))
		{
			switch(message.getEvent()) {
			case TouchEvent.DOWN:
				this.isClicked = true;
				System.out.println("TouchEvent.DOWN globalY " + message.getGlobalY(1));
				for (int i=0;i<listeners.size();i++)
				{
					Object obj = listeners.elementAt(i);
					if (obj instanceof CanvasListener)
					{
						CanvasListener listener = (CanvasListener)obj;
						listener.onCanvasTouchDown(this);
					}
				}				
				return true;
			}
		}
		if (this.isClicked)
		{
			switch(message.getEvent())
			{
			case TouchEvent.MOVE:
				System.out.println("A MOVE action occurred touchY:" + touchY + ", y4:" + y4);
				for (int i=0;i<listeners.size();i++)
				{
					Object obj = listeners.elementAt(i);
					if (obj instanceof CanvasListener)
					{
						CanvasListener listener = (CanvasListener)obj;
						listener.onCanvasMoved(this, touchY-y4);
					}
				}
		        return true;
			case TouchEvent.UP:	
				this.isClicked = false;
				System.out.println("A UP action occurred");
				for (int i=0;i<listeners.size();i++)
				{
					Object obj = listeners.elementAt(i);
					if (obj instanceof CanvasListener)
					{
						CanvasListener listener = (CanvasListener)obj;
						listener.onCanvasTouchUp(this);
					}
				}				
				return true;		        
			}			
		}
		return false;
	}	
}
