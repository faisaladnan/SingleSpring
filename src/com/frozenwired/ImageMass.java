package com.frozenwired;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;

public class ImageMass extends Mass {
	private EncodedImage image;
	public ImageMass(double mass, EncodedImage image) {
		super(mass);
		this.image = image;
		// TODO Auto-generated constructor stub
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
		g.drawImage(coordinateOrigin.getX()-image.getWidth()/2, 
				coordinateOrigin.getY()+10, 
				image.getWidth(), image.getHeight(), 
				image, 0, 0, 0);
	}
}
