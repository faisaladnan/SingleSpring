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

public class SingleSpringMainScreen extends MainScreen implements AnimationListener {
	TextField textField;
	Spring spring = new Spring(2.5, 3, 0, 4);
	SpringCanvas canvas = new SpringCanvas(spring);
	SpringAnimation springAnimation = new SpringAnimation(spring, canvas);	
	BasicEditField lenField = new BasicEditField("Len: ","0.000");	
	public SingleSpringMainScreen()
	{
		setTitle("Single Spring");
		textField = new TextField();
		add(new LabelField("Simulation Parameters:"));
		add(textField);
		add(new SeparatorField());
		ButtonField lenButton = new ButtonField("Change", ButtonField.CONSUME_CLICK);
		FlowFieldManager lenManager = new FlowFieldManager();
		lenManager.add(lenField);
		lenManager.add(lenButton);
		add(canvas);
		add(new SeparatorField());
		add(lenManager);
		springAnimation.addAnimationListener(this);
		springAnimation.startAnimation();
	}

	protected void makeMenu(Menu menu, int instance)
	{
		super.makeMenu(menu, instance);
		menu.add(new MenuItem("Restart Animation", 10, 10) {			
			public void run() {
				springAnimation.pauseAnimation();
				String len = lenField.getText();				
				spring.setInitialLen(Double.parseDouble(len));
				springAnimation.startAnimation();
			}
		});
		menu.add(new MenuItem("Start Animation", 20, 10) {			
			public void run() {
				springAnimation.resumeAnimation();
			}
		});		
		menu.add(new MenuItem("Pause Animation", 30, 10) {			
			public void run() {
				springAnimation.pauseAnimation();
			}
		});				
	}

	public void onAnimationChanged(String message) {
		synchronized (UiApplication.getUiApplication().getEventLock()) {
			textField.setText(message);
		}				
	}	
}
