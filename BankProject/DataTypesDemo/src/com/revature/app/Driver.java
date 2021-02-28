package com.revature.app;

public class Driver {
	
	public static boolean myStaticBoolean; // false is default value
	public static int myStaticInt; // 0 is default value
	
	public int myInstanceInt;  // 0 is default value;
	
	public static void main (String [] args) {
		boolean myBoolean;
		byte myByte;
		myByte = 10;
	
		byte mySecondByte = 20;
		System.out.println ("myByte: " + myByte);
		System.out.println ("mySecondByte: " + mySecondByte)
		
		Driver d = new Driver ();
		System.out.println ("myInstanceInt: " + d.myInstanceInt);
		
		myByte = 30;
		System.out.println ("myByte?: " + myByte);
		
		short myShort = 2_5767;// is for readability, not to separate digits.
		System.out.println ("");
		
		int myInt = 1_000_000_567;
		long myLong = 434563;
				
				System.out.println ("myShort: " + myShort);
				System.out.println ("myInt: " + myInt);
				System.out.println ("myLong: " + myLong);
				
				
		float myFloat = 3.1415f;
		double myDouble = 41.4545;
		
		// Character Type
		
		// Boolean Type
		boolean myBool1 = True;
		System.out.println (myBool1);
		
		myBool2 = false;
		System.out.println (myBool2);
		
		boolean myBool2 = 10 == 100;
		
		
		
		
		
		
		
	}
}
