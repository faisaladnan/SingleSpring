package com.frozenwired;

public interface Canvas {
	public void repaint();
	public void addCanvasListener(CanvasListener canvasListener);
	public void removeCanvasListener(CanvasListener canvasListener);
	public double getVerticalScaleConstant();
	public Spring getSpring();
	public Mass getMass();
	public void setAnimation(Animation animation);
	public Animation getAnimation(); 
}
