package com.frozenwired;

public interface RungeKuttaEventListener {
	public void onCalculate(double simulationTime, double[] vars);
}
