package com.frozenwired;

import java.util.Vector;

import net.rim.device.api.system.KeypadListener;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;

public class CustomGaugeField extends Field {
	private String label;
	private double min;
	private double max;
	private double start;
	private long style;
	private int context;
	private double value;
	private double step = 1;
	private boolean showLabel;
	Vector listeners = new Vector();	
	public CustomGaugeField(String label, double min, double max, double start, long style, int context)
	{
		super(style);
		this.setLabel(label);
		this.min = min;
		this.max = max;
		this.start = start;
		this.setValue(start);
		this.style = style;
		this.context = context;
		try {
			FontFamily sansSerifFamily = FontFamily.forName("BBSansSerif");
			Font gaugeFont = sansSerifFamily.getFont(Font.ITALIC, 4, Ui.UNITS_pt);
			setFont(gaugeFont);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void layout(int width, int height) {
		setExtent(width, 21);
	}

	protected void paint(Graphics graphics) {
			
		int fieldWidth = getWidth();
		int fieldHeight = getHeight();
		System.out.println("CustomGaugeField paint " + getHeight());
		int barStartX = 0;

		if (isShowLabel())
		{
			graphics.drawText(getLabel(), 0, 0);
			barStartX = getFont().getAdvance(getLabel());
		}
		
		int barWidth = getWidth()-barStartX;
		
		if (isFocus()) 
			graphics.drawRect(barStartX, 0, fieldWidth-barStartX, fieldHeight);

		int filledWidth = (int)(getValue()*barWidth/max);
		graphics.setColor(Color.LIGHTBLUE);
		graphics.fillRect(barStartX+1, 1, filledWidth, getHeight()-2);
		int valueStartX = barStartX + barWidth/2 - getFont().getAdvance(StringUtil.double2str(value,1));
		graphics.setColor(Color.BLACK);
		int valueStartHeight = (getHeight() - getFont().getHeight())/2;
		graphics.drawText(StringUtil.double2str(value,1), valueStartX, valueStartHeight);
	}
	protected void drawFocus(Graphics graphics, boolean on)
	{
		
	}
	public boolean isFocusable()
	{
		return true;
	}
	public int getPreferredWidth()
	{
		return getScreen().getWidth()-getFont().getAdvance(getLabel());
	}
	protected boolean navigationMovement(int dx, int  dy, int status, int time)
	{
		System.out.println("CustomGaugeField navigationMovement!");	
		if ((status & KeypadListener.STATUS_FOUR_WAY) == KeypadListener.STATUS_FOUR_WAY)
		{
			fieldChangeNotify(0);
			if (dx != 0) 
			{
				
				double oldValue = getValue();
				double newValue = oldValue;
				if (dx < 0)
					newValue = getValue() - this.step;
				else 
					newValue = getValue() + this.step;
				if (newValue >= min && newValue <= max)
					setValue(newValue);
				fireOnValueChange(oldValue, newValue);
				return true;
			} else if (dy != 0)
			{
				return false;
			} else
			{
				return false;
			}
		} else
		{
			return super.navigationMovement(dx, dy, status, time);
		}		
	}	
	public void addGaugeListener(GaugeListener listener)
	{
		listeners.addElement(listener);
	}
	public void removeGaugeListener(GaugeListener listener)
	{
		listeners.removeElement(listener);
	}
	public void removeAllGaugeListeners()
	{
		listeners.removeAllElements();
	}
	private void fireOnValueChange(double oldValue, double newValue)
	{
		for (int i=0;i<listeners.size();i++)
		{
			Object obj = listeners.elementAt(i);
			if (obj instanceof GaugeListener)
			{
				GaugeListener listener = (GaugeListener)obj;
				listener.onValueChange(oldValue, newValue, context);
			}
		}		
	}
	public void setValue(double value) {
		this.value = value;
		invalidate();
	}
	public double getValue() {
		return value;
	}
	public void setStep(double step) {
		this.step = step;
	}
	public double getStep() {
		return step;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}
	public boolean isShowLabel() {
		return showLabel;
	}	
}
