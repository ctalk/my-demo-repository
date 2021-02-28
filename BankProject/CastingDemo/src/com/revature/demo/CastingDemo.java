package com.revature.demo;

public class CastingDemo {
	public static void main (String[] args) {
		long myLong;
		int myInt = 1000;
		
		myLong = myInt; // implicit cast because tgt is wider
		System.out.println ("myLong: " + myLong);
		
		// Narrowing conversion
		
		int myInt2;
		myInt2 = (int) myLong;  // causes error without cast.
		
		long myLong2 = 100;    // can fit in long
		myLong2 = 1000000;
		
		byte myByte = (byte) myLong2;
		System.out.println ("myByte: " + myByte);  // Would only use least significant bits.
		
		double myDouble = 13.9999999;
		System.out.println ((int)myDouble);
		
		System.out.println(Math.ceil(13.999));
		
		
		// Reference Casting
		// upcasting from more specific to less specific
		// downcasting from less specific to more specific
		String myStr = "Hello, world!";
		
		// Every class extends from a class called Object
		// (A string is an Object, but not all Objects are 
		// Strings
		// Upcasting:
		Object myObj = myStr;
		
		// Downcasting is unsafe
		// We would need to know what object is actually being referred to in order to avoid issues.
		String myStr2 = (String) myObj;
		
		ArrayList al = (ArrayList) myObj;  // compiles and runs, would cause some sort of exception
		
	}
}
