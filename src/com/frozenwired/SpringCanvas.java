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
	private int xa = 0;
	private int ya = 10;
//	private int maxNoOfAngle = 3;
	private int drawAreaWidth = 50;
	private double verticalScaleConstant = 40; // default value
	private Spring spring;
	private Mass mass;
	private boolean isClicked = false;
	private int canvasWidth = 50;
	private int canvasHeight = -1;
	private Animation animation;
//	public SpringCanvas(Spring spring)
//	{
//		super(ButtonField.CONSUME_CLICK);
//		verticalScaleConstant = (getPreferredHeight()/2)/spring.getRestLen();
//		this.spring = spring;
//		this.spring.setVerticalScaleConstant(verticalScaleConstant);
//		this.spring.setCanvasWidth(getPreferredWidth());
//		this.spring.setXa(xa);
//		this.spring.setYa(ya);
////		maxNoOfAngle = (int)spring.getSpringConstant()*2;
////		if (maxNoOfAngle%2==0)
////			maxNoOfAngle = maxNoOfAngle + 1;
//			
//		setChangeListener(this);
//	}
	public SpringCanvas(Spring spring, Mass mass, int canvasWidth, int canvasHeight)
	{
		super(ButtonField.CONSUME_CLICK);
		verticalScaleConstant = (canvasHeight/2)/spring.getRestLen();
		this.spring = spring;
		this.spring.setVerticalScaleConstant(verticalScaleConstant);
		this.spring.setCanvasWidth(canvasWidth);
		this.spring.setXa(xa);
		this.spring.setYa(ya);
//		maxNoOfAngle = (int)spring.getSpringConstant()*2;
//		if (maxNoOfAngle%2==0)
//			maxNoOfAngle = maxNoOfAngle + 1;
			
		setChangeListener(this);

		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.drawAreaWidth = canvasWidth;
		this.mass = mass;
	}
	protected void layout(int width, int height) {
		if (width >= getPreferredWidth()) {
            width = getPreferredWidth();
        }
        if (height >= getPreferredHeight()) {
            height = getPreferredHeight();
        }
        setExtent(width, height);
	}
	public int getPreferredHeight()
	{
		if (canvasHeight < 0)
		{
			return getScreen().getVisibleHeight() - getTop();
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
//		g.drawRect(0, 0, getPreferredWidth(), getPreferredHeight());

		// draw spring
		spring.draw(g);
		
		// draw mass
		CanvasCoordinate coordinateOrigin = new CanvasCoordinate();
		coordinateOrigin.setX(xa + drawAreaWidth/2);
		coordinateOrigin.setY(ya + (int)(spring.getLen()*verticalScaleConstant));
		mass.draw(g, coordinateOrigin, isFocus());
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
		for (int i=0;i<listeners.size();i++)
		{
			Object obj = listeners.elementAt(i);
			if (obj instanceof CanvasListener)
			{
				CanvasListener listener = (CanvasListener)obj;
				listener.onCanvasFocus(this);
			}
		}		
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
	public Mass getMass() {
		return this.mass;
	}
	public Spring getSpring() {
		return this.spring;
	}
	public Animation getAnimation() {
		return animation;
	}
	public void setAnimation(Animation animation) {
		// TODO Auto-generated method stub
		this.animation = animation;
	}

}
