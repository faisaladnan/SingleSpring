package com.frozenwired;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class SingleSpringMainScreen extends MainScreen {
	TextField textField;
	Spring spring = new Spring(2.5, 3, 0);
	SpringCanvas canvas = new SpringCanvas(spring);
	BasicEditField lenField = new BasicEditField("Len: ","2.500");	
	public SingleSpringMainScreen()
	{
		setTitle("Single Spring");
		textField = new TextField();
		add(new LabelField("Simulation Parameters:"));
		add(textField);
		add(new SeparatorField());
		canvas.getSpring().move(2.5);
		ButtonField lenButton = new ButtonField("Change");
		FlowFieldManager lenManager = new FlowFieldManager();
		lenManager.add(lenField);
		lenManager.add(lenButton);
		add(canvas);
		add(new SeparatorField());
		add(lenManager);
		
		RungeKutta rk = new RungeKutta(this, canvas);
		(new Thread(rk)).start();
	}
	public void updateText(String msg)
	{
		synchronized (UiApplication.getUiApplication().getEventLock()) {
			textField.setText(msg);
		}
	}
	protected void makeMenu(Menu menu, int instance)
	{
		super.makeMenu(menu, instance);
		menu.add(new MenuItem("Change Length", 10, 10) {			
			public void run() {
				// TODO Auto-generated method stub
				String len = lenField.getText();				
				canvas.getSpring().move(Double.parseDouble(len));
				canvas.repaint();
			}
		});
		
	}
}
