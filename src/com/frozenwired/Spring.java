package com.frozenwired;

import net.rim.device.api.ui.Graphics;

public class Spring implements GaugeListener {
	private double restLen;
	private double springConstant;
	private double damping;
	private double len;
	private double origin;
	private double initialLen;
	
	// variables needed to draw spring
	private int maxNoOfAngle;
	private double verticalScaleConstant;
	private int canvasWidth;
	private int xa;
	private int ya;
	public Spring(double restLen, double springConstant, double damping, double origin, double initialLen)
	{
		this.restLen = restLen;
		this.springConstant = springConstant;
		this.setDamping(damping);
		this.origin = origin;
		this.len = origin;
		this.initialLen = initialLen;
		maxNoOfAngle = (int)this.springConstant*2;
		if (maxNoOfAngle%2==0)
			maxNoOfAngle = maxNoOfAngle + 1;
		
	}
	
	public void setLen(double len)
	{
		this.len = len;
	}
	public double getLen()
	{
		return this.len;
	}
	public double getRestLen()
	{
		return this.restLen;
	}
	public double getSpringConstant()
	{
		return this.springConstant;
	}
	public double getOrigin()
	{
		return this.origin;
	}
	public double getInitialLen()
	{
		return this.initialLen;
	}
	public void setInitialLen(double initialLen)
	{
		this.initialLen = initialLen;
	}
	public void draw(Graphics g)
	{
		// draw initial line
		g.drawLine( this.xa+this.canvasWidth/2, 
					0, 			
					this.xa+this.canvasWidth/2,
					this.ya 
					);
		// draw spring
		for (int i=1;i<=maxNoOfAngle+1;i++)
		{
			CanvasCoordinate p1 = getSpringCoordinate(i-1, this.len);
			CanvasCoordinate p2 = getSpringCoordinate(i, this.len);
			g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
		
	}
	private CanvasCoordinate getSpringCoordinate(int index, double len)
	{
		CanvasCoordinate p = new CanvasCoordinate();
		if (index == 0)
		{
			p.setX(xa + this.canvasWidth/2);
			p.setY(ya);
			return p;
		}
		if (index == this.maxNoOfAngle+1)
		{
			p.setX(xa + this.canvasWidth/2);
			p.setY(ya + (int)(len*this.verticalScaleConstant));
			return p;
		}
		if (index%2==0)
		{
			p.setX(xa);
		} else
		{
			p.setX(xa + this.canvasWidth);
		}
		int noOfSegment = (maxNoOfAngle-1)*2 + 2;
		p.setY(ya + (int)(len*this.verticalScaleConstant)*(2*index-1)/noOfSegment);
		return p;
	}

	public void setVerticalScaleConstant(double verticalScaleConstant) {
		this.verticalScaleConstant = verticalScaleConstant;
	}

	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	public void setXa(int xa) {
		this.xa = xa;
	}

	public void setYa(int ya) {
		this.ya = ya;
	}

	public void onValueChange(int oldValue, int newValue, int context) {
		// TODO Auto-generated method stub
		if (context == 1) // constant value changed
		{
			this.springConstant = newValue;
			maxNoOfAngle = (int)this.springConstant*2;
			if (maxNoOfAngle%2==0)
				maxNoOfAngle = maxNoOfAngle + 1;			
		} else if (context == 2)
		{
			this.damping = newValue;
		}
	}

	public void onValueChange(double oldValue, double newValue, int context) {
		// TODO Auto-generated method stub
		if (context == 1) // constant value changed
		{
			this.springConstant = newValue;
			maxNoOfAngle = (int)this.springConstant*2;
			if (maxNoOfAngle%2==0)
				maxNoOfAngle = maxNoOfAngle + 1;			
		} else if (context == 2)
		{
			this.damping = newValue;
		}
	}	
	
	public void setDamping(double damping) {
		this.damping = damping;
	}

	public double getDamping() {
		return damping;
	}
}
