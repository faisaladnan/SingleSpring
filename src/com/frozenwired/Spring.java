package com.frozenwired;

public class Spring {
	private double restLen;
	private double springConstant;
	private double len;
	private double initialLen;
	public Spring(double restLen, double springConstant, double initialLen)
	{
		this.restLen = restLen;
		this.springConstant = springConstant;
		this.initialLen = initialLen;
		this.len = initialLen;
	}
	
	public void move(double len)
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
	public double getInitialLen()
	{
		return this.initialLen;
	}
}
