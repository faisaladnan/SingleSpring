package com.frozenwired;

import java.util.Vector;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class SpringAnimation implements Runnable, RungeKuttaEventListener, CanvasListener {
	private Vector listeners = new Vector();
	private Canvas canvas;
	private Spring spring;
	private SpringRungeKutta springRungeKutta;
	private boolean isAnimating = false;
	private boolean isMoved = false;
	Thread animationThread;
	public SpringAnimation(Spring spring, Canvas canvas)
	{
		this.spring = spring;
		this.canvas = canvas;
		this.canvas.addCanvasListener(this);
		this.springRungeKutta = new SpringRungeKutta(spring);
		this.springRungeKutta.addRungeKuttaEventListener(this);
		isAnimating = true;
	}
	public void run() {
		while (true)
		{
			if (isAnimating) 
			{
				springRungeKutta.calculate();
				spring.setLen(springRungeKutta.getNextSpringLength());
				canvas.repaint();
			}
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void pauseAnimation()
	{
		this.isAnimating = false;
	}

	public void resumeAnimation()
	{
		this.isAnimating = true;
	}	
	public Thread startAnimation()
	{
		this.isAnimating = true;
		if (animationThread != null)
			animationThread = null;
		animationThread = new Thread(this);
		springRungeKutta.init();
		animationThread.start();
		return animationThread;
	}
	public void onCalculate(double simulationTime, double[] vars) {
		String msg = "t: " + double2str(simulationTime) + 
		" x: " + double2str(vars[0]) +
		" v: " + double2str(vars[1]);
		fireOnAnimationChanged(msg);
	}
	
	private String double2str(double d)
	{
		String str = "";
		Double obj = new Double(d);
		String ds = obj.toString();
		if (d == 0)
			ds = "0.000";
		if (ds.length() < 5)
			ds = ds + generateZeros(5-ds.length()); 
		int delimiterIndex = ds.indexOf(".");
		int decLen = ds.substring(delimiterIndex, ds.length()).length()-1; 
		if (decLen < 3)
			ds = ds + generateZeros(3-decLen);
		str = ds.substring(0, delimiterIndex) + ds.substring(delimiterIndex, delimiterIndex+4);
		return str;
	}
	private String generateZeros(int n)
	{
		String res = "";
		for (int i=0;i<n;i++)
		{
			res = res + "0";
		}
		return res;
	}
	public void addAnimationListener(AnimationListener listener)
	{
		listeners.addElement(listener);
	}
	public void removeAnimationListener(AnimationListener listener)
	{
		listeners.removeElement(listener);
	}
	private void fireOnAnimationChanged(String message)
	{
		for (int i=0;i<listeners.size();i++)
		{
			Object obj = listeners.elementAt(i);
			if (obj instanceof AnimationListener)
			{
				AnimationListener listener = (AnimationListener)obj;
				listener.onAnimationChanged(message);
			}			
		}
	}
	public void onCanvasClicked(Canvas canvas) {
		System.out.println("clicked");
		if (isAnimating)
			pauseAnimation();
		else {
			if (this.isMoved)
			{
				startAnimation();
			} else
			{
				resumeAnimation();
			}
			this.isMoved = false;
		}
	}
	public void onCanvasMoved(Canvas canvas, int delta) {
		System.out.println("delta: " + delta);
		this.isMoved = true;
		spring.setInitialLen(delta/canvas.getVerticalScaleConstant() + spring.getLen());
		spring.setLen(delta/canvas.getVerticalScaleConstant() + spring.getLen());
		canvas.repaint();		
	}
	public void onCanvasReleased(Canvas canvas) {
//		startAnimation();
		System.out.println("released");
	}
	public void onCanvasTouchDown(Canvas canvas) {
		System.out.println("onCanvasTap " + isAnimating);
		if (isAnimating)
			pauseAnimation();
		
	}
	public void onCanvasTouchUp(Canvas canvas) {
		// TODO Auto-generated method stub
		if (this.isMoved)
		{
			startAnimation();
		} else
		{
			resumeAnimation();
		}
		this.isMoved = false;
	}
}
