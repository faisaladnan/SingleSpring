package com.frozenwired;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;

public class SpringCanvas extends Field implements Canvas {
	private int xa = 100;
	private int ya = 50;
	private int drawAreaWidth = 100;
	private double verticalScaleConstant = 40;
	private Spring spring;
	public SpringCanvas(Spring spring)
	{
		this.spring = spring;
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
		g.drawRect(xa, 
				  (int)(spring.getLen()*verticalScaleConstant) + ya, // y4 
				   100, 25);
		
	}
	
	public void repaint()
	{
		synchronized (UiApplication.getUiApplication().getEventLock()) {
			invalidate();
		}
	}
}
