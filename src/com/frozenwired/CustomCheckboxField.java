package com.frozenwired;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.CheckboxField;

public class CustomCheckboxField extends CheckboxField {
	public CustomCheckboxField(String label, boolean checked, long style)
	{
		super(label, checked, style);
	}
	
	public CustomCheckboxField(String label, boolean checked)
	{
		super(label, checked);
	}
	
	public void drawFocus(Graphics g, boolean on)
	{
		
	}
}