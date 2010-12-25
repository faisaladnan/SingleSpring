package com.frozenwired;

public class Spring {
	private double restLen;
	private double springConstant;
	private double len;
	private double origin;
	private double initialLen;
	public Spring(double restLen, double springConstant, double origin, double initialLen)
	{
		this.restLen = restLen;
		this.springConstant = springConstant;
		this.origin = origin;
		this.len = origin;
		this.initialLen = initialLen;
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

}
