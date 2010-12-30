package com.frozenwired;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public class CustomLabelField extends LabelField {
	String label;
	public CustomLabelField(Object text, long style)
	{
		super(text, style);
		if (text instanceof String)
			this.label = (String)text;
	}
	protected void paint(Graphics graphics)
	{
		graphics.drawText(label, 0, (getHeight()-getFont().getHeight())/2);
	}
	protected void layout(int width, int height) {
		setExtent(200, 21);
	}	
	public void setText(Object text)
	{
		if (text instanceof String)
			this.label = (String)text;
		invalidate();
	}
}
