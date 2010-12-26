package com.frozenwired;

public interface CanvasListener {
	public void onCanvasClicked(Canvas canvas);
	public void onCanvasReleased(Canvas canvas);	
	public void onCanvasMoved(Canvas canvas, int delta);
}
