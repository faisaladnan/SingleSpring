package com.frozenwired;

import java.util.Vector;

import net.rim.device.api.ui.UiApplication;

public class SpringAnimation implements Runnable, RungeKuttaEventListener {
	private Vector listeners = new Vector();
	private Canvas canvas;
	private Spring spring;
	private SpringRungeKutta springRungeKutta;
	private boolean isAnimating = false;
	public SpringAnimation(Spring spring, Canvas canvas)
	{
		this.spring = spring;
		this.canvas = canvas;
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
		Thread thread = new Thread(this);
		springRungeKutta.init();
		thread.start();
		return thread;
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
}
