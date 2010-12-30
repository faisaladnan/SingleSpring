package com.frozenwired;

public class StringUtil {
	public static String double2str(double d, int noOfDecimalDigit)
	{
		String str = "";
		String ds = Double.toString(d);
		if (d == 0)
			ds = "0." + generateZeros(noOfDecimalDigit);
		if (ds.length() < noOfDecimalDigit+2)
			ds = ds + generateZeros(noOfDecimalDigit+2-ds.length()); 
		int delimiterIndex = ds.indexOf(".");
		int decLen = ds.substring(delimiterIndex, ds.length()).length()-1; 
		if (decLen < noOfDecimalDigit+2)
			ds = ds + generateZeros(noOfDecimalDigit+2-decLen);
		str = ds.substring(0, delimiterIndex) + ds.substring(delimiterIndex, delimiterIndex+noOfDecimalDigit+1);
		return str;
	}
	public static String generateZeros(int n)
	{
		String res = "";
		for (int i=0;i<n;i++)
		{
			res = res + "0";
		}
		return res;
	}
}
