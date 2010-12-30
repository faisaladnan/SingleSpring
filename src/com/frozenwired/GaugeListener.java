package com.frozenwired;

public interface GaugeListener {
	public void onValueChange(double oldValue, double newValue, int context);	
}
