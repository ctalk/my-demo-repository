package com.revature.demo;

public class Application {
	public static void main (String [] args) {
		System.out.println ("Hello, world.");
		
		anotherMethod ();
		
		yetAnotherMethod ();
		
		greetUser ("Bach");
		
		AnotherClass.hotdogs ();
		
		System.out.println ("We're now done.");
	}
	
	public static void anotherMethod () {
		System.out.println ("I am inside anotherMethod");
		
	}

	public static void yetAnotherMethod () {
		System.out.println ("yetAnotherMethod running...");
	}
	
	public static void greetUser (String name) {
		System.out.println ("Hello, " + name);
	}
}
