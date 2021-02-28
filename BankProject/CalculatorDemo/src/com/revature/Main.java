package com.revature;

public class Main {
		public static void main (String [] args) {
			Calculator calcOne = new Calculator ();
			// new Calculator creates a new Calculatr object, and then gives it the location (memory address) of the object
			// to the new calcOne variable.
			
			Calculator calcTwo = new Calculator ();
			calcTwo.name = "Casio Calculator";
			
			
			
			Calculator.nameTwo = "test";
			
			// Set the property name to something.
			calcOne.name = "TI-84";
			
			int sum = calcOne.add((10, 20);
			System.out.println (sum);
			
			int difference = calcOne.subtract(20,5);
			System.out.println (difference);
			
					System.out.println ("Name of calcOne: " + calcOne.name);
			System.out.println ("Name of calcTwo: " + calcTwo.name);
			
			calcOne.nameTwo = "changedNameTwo";
	
			System.out.println ("Changed name of calcTwo: " + calcTwo.nameTwo);
			
			System.out.println ("==========================");

			calcOne.setName ("Reliable");
			
			System.out.println ("Changed name of calcOne: " + calcOne.name);
			System.out.println ("Changed name of calcTwo: " + calcTwo.name);
			
			
			
		}
	
}
