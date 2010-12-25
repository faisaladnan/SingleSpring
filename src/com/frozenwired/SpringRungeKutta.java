package com.frozenwired;

public class SpringRungeKutta extends RungeKutta {
	RoundMass roundMass;
	Spring spring;
	class RoundMass
	{
	  double mass = 0.5;
	  double damping = 0.2;
	}	
	public SpringRungeKutta(Spring spring)
	{
		this.spring = spring;
		roundMass = new RoundMass();
		this.setNumVars(2);
		init();
	}
	public double diffeq0(double t, double[] x)   // t = time, x = array of variables
	{
		return x[1];  // x' = v
	}

	public double diffeq1(double t, double[] x)   // t = time, x = array of variables
	{
		// v' = -(k/m)(x - R) - (b/m) v
		double r = -spring.getSpringConstant()*(x[0] - spring.getOrigin() - spring.getRestLen())
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
	
	public double getNextSpringLength()
	{
		return vars[0];
	}
	
	public double getNextSpringSpeed()
	{
		return vars[1];
	}
	public void init()
	{
		this.simulationTime = 0;
		this.vars[0] = spring.getInitialLen();
		this.vars[1] = 0;
	}
	public void setNumVars(int numVars) {
		this.numVars = numVars;
	}
}
