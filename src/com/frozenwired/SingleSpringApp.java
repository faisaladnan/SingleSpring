package com.frozenwired;

import net.rim.device.api.ui.UiApplication;

public class SingleSpringApp extends UiApplication {
	public static void main(String[] args)
	{
		SingleSpringApp app = new SingleSpringApp();
		app.enterEventDispatcher();
	}
	public SingleSpringApp()
	{
		SingleSpringMainScreen mainScreen = new SingleSpringMainScreen();
		pushScreen(mainScreen);
	}
}
