package com.frozenwired;

import net.rim.device.api.ui.Graphics;

public abstract class Mass implements GaugeListener {
	private double mass;
	public Mass(double mass)
	{
		this.mass = mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public double getMass() {
		return mass;
	}
	public abstract void draw(Graphics g, CanvasCoordinate coordinateOrigin, boolean isFocus);
	
	public void onValueChange(int oldValue, int newValue, int context) {
		this.mass = newValue;		
	}
	public void onValueChange(double oldValue, double newValue, int context) {
		this.mass = newValue;		
	}		
}
