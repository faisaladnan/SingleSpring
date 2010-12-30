package com.frozenwired;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;

public class RoundMass extends Mass {
	private int radius;
	public RoundMass(double mass, int radius) {
		super(mass);
		this.radius = radius;
	}

	public void draw(Graphics g, CanvasCoordinate coordinateOrigin,
			boolean isFocus) {
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
		g.drawArc(coordinateOrigin.getX()-radius/2, 
				coordinateOrigin.getY()+10, radius, radius, 0, 360);
		
	}
}
