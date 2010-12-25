package com.frozenwired;

public interface Canvas {
	public void repaint();
	public void addCanvasListener(CanvasListener canvasListener);
	public void removeCanvasListener(CanvasListener canvasListener);
}
