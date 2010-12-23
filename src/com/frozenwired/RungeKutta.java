package com.frozenwired;

public class RungeKutta implements Runnable {
	Spring spring;
	RoundMass roundMass;
	SingleSpringMainScreen mainScreen;
	private int numVars = 2;
	private double m_time = -9999;
	private double sim_time = 0;
	private double[] vars;
	private final int MAX_VARS = 40;
	class Spring
	{
	  double springConst = 3;
	  double restLength = 2.5;
	  double x1 = 0;  // left end of spring
	}

	class RoundMass
	{
	  double mass = 0.5;
	  double damping = 0.2;
	}
	public RungeKutta(SingleSpringMainScreen mainScreen)
	{
		spring = new Spring();
		roundMass = new RoundMass();
		this.mainScreen = mainScreen;
		vars = new double[MAX_VARS];		
	}
	// A version of Runge-Kutta method using arrays
	// Calculates the values of the variables at time t+h
	// t = last time value
	// h = time increment
	// vars = array of variables
	// N = number of variables in x array
	public void solve(double t, double h)
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
	public double evaluate(int i, double t, double[] x)
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

	public double diffeq0(double t, double[] x)   // t = time, x = array of variables
	{
		return x[1];  // x' = v
	}

	public double diffeq1(double t, double[] x)   // t = time, x = array of variables
	{
		// v' = -(k/m)(x - R) - (b/m) v
		double r = -spring.springConst*(x[0] - spring.x1 - spring.restLength)
		- roundMass.damping*x[1];
		return r/roundMass.mass;
	}

	public double diffeq2(double t, double[] x)
	{ return 0; }
	public double diffeq3(double t, double[] x)
	{ return 0; }
	public double diffeq4(double t, double[] x)
	{ return 0; }
	public double diffeq5(double t, double[] x)
	{ return 0; }
	public double diffeq6(double t, double[] x)
	{ return 0; }
	public double diffeq7(double t, double[] x)
	{ return 0; }

	public void run() {
		// TODO Auto-generated method stub
		while (true)
		{
			calculationLoop();
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
		String msg = "t = "+(sim_time)+
		"   x = "+(vars[0])+
		"   v = "+(vars[1])+"\n";
		mainScreen.updateText(msg);
	}
	private void calculationLoop()
	{
		outputMessage();
		double now = (double)System.currentTimeMillis()/1000;
		double h;
		if (m_time < 0)
		{
			sim_time = 0;
			h = 0.05;
		} else
		{
			h = now - m_time;
			if (h==0) return;
			if (h > 0.25) h = 0.25;
		}
		if (h<0 || h>3)
		{
			h = 0.1;
		}
		m_time = now;
		System.out.println("now " + now + ", h " + h + ", sim_time " + sim_time);
		solve(sim_time, h);
		sim_time += h;
	}
	
}
