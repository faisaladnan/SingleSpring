package com.frozenwired;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;

public class SingleSpringMainScreen extends MainScreen {
	TextField textField;
	public SingleSpringMainScreen()
	{
		setTitle("Single Spring");
		textField = new TextField();
		add(new LabelField("Simulation Parameters:"));
		add(textField);
		add(new SeparatorField());
		RungeKutta rk = new RungeKutta(this);
		(new Thread(rk)).start();
	}
	public void updateText(String msg)
	{
		synchronized (UiApplication.getUiApplication().getEventLock()) {
			textField.setText(msg);
		}
	}
}
