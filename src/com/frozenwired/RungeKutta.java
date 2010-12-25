package com.frozenwired;

import java.util.Vector;

public abstract class RungeKutta {
	private Vector listeners = new Vector();
	protected double time = -9999;
	protected double simulationTime = 0;
	private final int MAX_VARS = 40;
	protected double[] vars = new double[MAX_VARS];
	private double now;
	private double h;
	protected int numVars;	

	// A version of Runge-Kutta method using arrays
	// Calculates the values of the variables at time t+h
	// t = last time value
	// h = time increment
	// vars = array of variables
	// N = number of variables in x array
	protected void solve(double t, double h, int numVars)
	{
		int N = numVars;
		int i;
		double[] inp = new double[N];
		double[] k1 = new double[N];
		double[] k2 = new double[N];
		double[] k3 = new double[N];
		double[] k4 = new double[N];
		for (i=0; i<N; i++)
			k1[i] = evaluate(i,t,vars);     // evaluate at time t
		for (i=0; i<N; i++)
			inp[i] = vars[i]+k1[i]*h/2; // set up input to diffeqs
		for (i=0; i<N; i++)
			k2[i] = evaluate(i,t+h/2,inp);  // evaluate at time t+h/2
		for (i=0; i<N; i++)
			inp[i] = vars[i]+k2[i]*h/2; // set up input to diffeqs
		for (i=0; i<N; i++)
			k3[i] = evaluate(i,t+h/2,inp);  // evaluate at time t+h/2
		for (i=0; i<N; i++)
			inp[i] = vars[i]+k3[i]*h; // set up input to diffeqs
		for (i=0; i<N; i++)
			k4[i] = evaluate(i,t+h,inp);    // evaluate at time t+h
		for (i=0; i<N; i++)
			vars[i] = vars[i]+(k1[i]+2*k2[i]+2*k3[i]+k4[i])*h/6;
	}
	
	// executes the i-th diffeq
	// i = which diffeq,  t=time,  x= array of variables
	protected double evaluate(int i, double t, double[] x)
	{
		switch (i)
		{ case 0:  return diffeq0(t, x);
		case 1:  return diffeq1(t, x);
		case 2:  return diffeq2(t, x);
		case 3:  return diffeq3(t, x);
		case 4:  return diffeq4(t, x);
		case 5:  return diffeq5(t, x);
		case 6:  return diffeq6(t, x);
		case 7:  return diffeq7(t, x);
		default:
			System.out.println("throw?  problem in evaluate");
			return 0;
		}
	}

	/* Explanation of how to code up differential equations:
    The variables are stored in the vars[] array.
    Let y = var[0], w = var[1], z = var[2], etc.
    The differential equations must all be first order, in the form:
      y' = f(t, y, w, z, ...)
      w' = g(t, y, w, z, ...)
      z' = h(t, y, w, z, ...)
      ...
    You will have as many equations as there are variables

    diffeq0 returns the right hand side of the first equation
      y' = f(t, y, w, z, ...)
    diffeq1 returns the right hand side of the second equation
      w' = g(t, y, w, z, ...)
	 */

	public abstract double diffeq0(double t, double[] x);
	public abstract double diffeq1(double t, double[] x);
	public abstract double diffeq2(double t, double[] x);
	public abstract double diffeq3(double t, double[] x);
	public abstract double diffeq4(double t, double[] x);	
	public abstract double diffeq5(double t, double[] x);
	public abstract double diffeq6(double t, double[] x);
	public abstract double diffeq7(double t, double[] x);

	public void run() {
		// TODO Auto-generated method stub
		while (true)
		{
			calculationLoop();
//			canvas.getSpring().move(vars[0]);
//			canvas.repaint();			
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void outputMessage()
	{
//		mainScreen.updateText("t: " + double2str(simulationTime) + 
//				" x: " + double2str(vars[0]) +
//				" v: " + double2str(vars[1]) 
//				);
	}
	private void calculationLoop()
	{
		outputMessage();
		double now = (double)System.currentTimeMillis()/1000;
		double h;
		if (time < 0)
		{
			simulationTime = 0;
			h = 0.05;
		} else
		{
			h = now - time;
			if (h==0) return;
			if (h > 0.25) h = 0.25;
		}
		if (h<0 || h>3)
		{
			h = 0.1;
		}
		time = now;
		System.out.println("now " + now + ", h " + h + ", sim_time " + simulationTime);
		solve(simulationTime, h, 2);
		simulationTime += h;
	}

	public abstract void setNumVars(int numVars);
	public abstract void init();
	
	public void calculate()
	{
		fireOnCalculate();
		now = (double)System.currentTimeMillis()/1000;		
		if (time < 0)
		{
			simulationTime = 0;
			h = 0.05;
		} else
		{
			h = now - time;
			if (h==0) return;
			if (h > 0.25) h = 0.25;
		}
		if (h<0 || h>3)
		{
			h = 0.1;
		}
		time = now;
		solve(simulationTime, h, numVars);
		simulationTime += h;
	}	
	
	public void addRungeKuttaEventListener(RungeKuttaEventListener listener)
	{
		listeners.addElement(listener);
	}
	public void removeRungeKuttaEventListener(RungeKuttaEventListener listener)
	{
		listeners.removeElement(listener);
	}
	private void fireOnCalculate()
	{
		for (int i=0;i<listeners.size();i++)
		{
			Object obj = listeners.elementAt(i);
			if (obj instanceof RungeKuttaEventListener)
			{
				RungeKuttaEventListener listener = (RungeKuttaEventListener)obj;
				listener.onCalculate(simulationTime, vars);
			}			
		}
	}
}
