package com.frozenwired;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;

public class RectMass extends Mass {
	private int width = 20;
	private int height = 20;
	public RectMass(double mass, int width, int height) {
		super(mass);
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g, CanvasCoordinate coordinateOrigin, boolean isFocus) {
		// draw initial line
		g.drawLine(coordinateOrigin.getX(), coordinateOrigin.getY(), 
				coordinateOrigin.getX(), coordinateOrigin.getY()+10);
		if (isFocus)
		{
			g.setColor(Color.BLUE);
		} else
		{
			g.setColor(Color.BLACK);
		}	
		g.drawRect(coordinateOrigin.getX()-width/2, 
				coordinateOrigin.getY()+10, 
				width, height);		
	}
}
