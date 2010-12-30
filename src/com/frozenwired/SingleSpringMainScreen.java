package com.frozenwired;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class SingleSpringMainScreen extends MainScreen implements AnimationListener, CanvasListener {
	TextField textField;
	// spring 1 animation 
	Spring spring = new Spring(2.5, 5, 0.2, 0, 0);
	Mass mass = new RectMass(0.5, 50, 50);
	SpringCanvas canvas = new SpringCanvas(spring, mass, 100, 200);
	SpringAnimation springAnimation = new SpringAnimation(spring, canvas, mass);
	
	// spring 2 animation 
	Spring spring2 = new Spring(2.5, 15, 0.0, 0, 4);
	Mass mass2 = new RoundMass(1.5, 50);
	SpringCanvas canvas2 = new SpringCanvas(spring2, mass2, 100, 400);
	SpringAnimation springAnimation2 = new SpringAnimation(spring2, canvas2, mass2);		
	
	// spring 3 animation
	EncodedImage loveImage = EncodedImage.getEncodedImageResource("res/love.png");
	Spring spring3 = new Spring(2.5, 5, 0.0, 0, 4);
	Mass mass3 = new ImageMass(1.5, loveImage);
	SpringCanvas canvas3 = new SpringCanvas(spring3, mass3, 100, 200);
	SpringAnimation springAnimation3 = new SpringAnimation(spring3, canvas3, mass3);		
	
	CustomGaugeField springConstantField = new CustomGaugeField("Constant",1,30,10,Field.EDITABLE | Field.FOCUSABLE, SpringRungeKutta.SPRING_CONSTANT_CTX);
	CustomGaugeField springDampingField = new CustomGaugeField("",0,5,2.5,Field.EDITABLE | Field.FOCUSABLE, SpringRungeKutta.SPRING_DAMPING_CTX);
	CustomGaugeField massField = new CustomGaugeField("",0.1,30,10,Field.EDITABLE | Field.FOCUSABLE, SpringRungeKutta.SPRING_MASS_CTX);
	CustomCheckboxField gravitationField = new CustomCheckboxField("", true);
//	CustomGaugeField customGaugeField = new CustomGaugeField("Label:", 0, 100, 10, Field.FOCUSABLE, 1);
//	BasicEditField lenField = new BasicEditField("Len: ","0.000");	
	CustomLabelField dampingratioField = new CustomLabelField("aa",LabelField.USE_ALL_HEIGHT | DrawStyle.VCENTER | Field.FIELD_VALIGN_MASK );

	
	public SingleSpringMainScreen()
	{
		setTitle("Spring Simulation");
//		springConstantField.addGaugeListener(spring);
		springConstantField.setShowLabel(false);
		springConstantField.setStep(0.1f);
		springDampingField.setStep(0.1f);
		massField.setStep(0.1f);
		textField = new TextField();
//		add(new LabelField("Simulation Parameters:"));
//		add(textField);
		add(new SeparatorField());
//		ButtonField lenButton = new ButtonField("Change", ButtonField.CONSUME_CLICK);
		FlowFieldManager canvasManager = new FlowFieldManager();
		canvas.addCanvasListener(this);
		canvas2.addCanvasListener(this);
		canvas3.addCanvasListener(this);		
		canvasManager.add(canvas);
//		canvasManager.add(canvas2);
//		canvasManager.add(canvas3);	
		VerticalFieldManager panelManager = new VerticalFieldManager();
		
		CustomGridFieldManager gridManager = new CustomGridFieldManager(2, 0);
		EncodedImage dampingImage = EncodedImage.getEncodedImageResource("res/damping.png");
		gridManager.add(new BitmapField(dampingImage.getBitmap(), Field.FIELD_LEFT | Field.FIELD_VCENTER));		
		gridManager.add(springDampingField);	
		EncodedImage constantImage = EncodedImage.getEncodedImageResource("res/constant.png");
		gridManager.add(new BitmapField(constantImage.getBitmap(), Field.FIELD_LEFT | Field.FIELD_VCENTER));		
		gridManager.add(springConstantField);		
		EncodedImage massImage = EncodedImage.getEncodedImageResource("res/mass.png");
		gridManager.add(new BitmapField(massImage.getBitmap(), Field.FIELD_LEFT | Field.FIELD_VCENTER));		
		gridManager.add(massField);		
		EncodedImage dampingratioImage = EncodedImage.getEncodedImageResource("res/dampingratio.png");
		gridManager.add(new BitmapField(dampingratioImage.getBitmap(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
		dampingratioField.setFont(massField.getFont());
		gridManager.add(dampingratioField);
		EncodedImage gravitationImage = EncodedImage.getEncodedImageResource("res/gravitation.png");
		gridManager.add(new BitmapField(gravitationImage.getBitmap(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
		gravitationField.setFont(massField.getFont());
		gravitationField.setChangeListener(springAnimation);
		gridManager.add(gravitationField);
		panelManager.add(gridManager);
		canvasManager.add(panelManager);
		add(canvasManager);
		
		add(new SeparatorField());
		springAnimation.addAnimationListener(this);
		springAnimation.startAnimation();
//		springAnimation2.addAnimationListener(this);
//		springAnimation2.startAnimation();
//		springAnimation3.addAnimationListener(this);
//		springAnimation3.startAnimation();		
	}

	protected void makeMenu(Menu menu, int instance)
	{
		super.makeMenu(menu, instance);
//		menu.add(new MenuItem("Restart Animation", 10, 10) {			
//			public void run() {
//				springAnimation.pauseAnimation();
//				String len = lenField.getText();				
//				spring.setInitialLen(Double.parseDouble(len));
//				springAnimation.startAnimation();
//			}
//		});
		menu.add(new MenuItem("Resume Animation", 20, 10) {			
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
			dampingratioField.setText(message);
		}				
	}

//	public void onAnimationSelected(SpringAnimation animation) {
//		springConstantField.addGaugeListener(animation.getSpring());
//		springConstantField.addGaugeListener(animation);
//		springConstantField.setValue(animation.getSpring().getSpringConstant());
//		springDampingField.addGaugeListener(animation.getSpring());
//		springDampingField.addGaugeListener(animation);
//		springDampingField.setValue(animation.getSpring().getDamping());
//		massField.addGaugeListener(animation.getMass());
//		massField.addGaugeListener(animation);
//		massField.setValue(animation.getMass().getMass());
//	}

	public void onCanvasClicked(Canvas canvas) {
	}

	public void onCanvasFocus(Canvas canvas) {
		springConstantField.removeAllGaugeListeners();
		springConstantField.addGaugeListener(canvas.getSpring());
		springConstantField.addGaugeListener(canvas.getAnimation());
		springConstantField.setValue(canvas.getSpring().getSpringConstant());
		springDampingField.removeAllGaugeListeners();
		springDampingField.addGaugeListener(canvas.getSpring());
		springDampingField.addGaugeListener(canvas.getAnimation());
		springDampingField.setValue(canvas.getSpring().getDamping());
		massField.removeAllGaugeListeners();
		massField.addGaugeListener(canvas.getMass());
		massField.addGaugeListener(canvas.getAnimation());	
		massField.setValue(canvas.getMass().getMass());
	}

	public void onCanvasMoved(Canvas canvas, int delta) {
	}

	public void onCanvasReleased(Canvas canvas) {
	}

	public void onCanvasTouchDown(Canvas canvas) {
	}

	public void onCanvasTouchUp(Canvas canvas) {
	}	
}
