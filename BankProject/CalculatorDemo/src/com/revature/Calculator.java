package com.revature;

public class Calculator {
	// This is an example of an instance variable that will belong to an object.
	/*
	 *  Whenever there is no static keyword on the variable or the method, 
	 */
	public String name;
	
	public static String nameTwo;
	
	// This method is not static like our previous examples,
	// meaning that this method does not belong to a class itself, but to an object of that class.
	// It is also not void like the previous examples.
	// It returns some sort of value.. in this case the two parameters being added together.
	// The naming does not actually matter in how the method itself behaves, we could name it whatever we want to.
	// But in this case, it makes sense to call it add, because we know that it is adding two numbers together.
	public int add (int x, int y) {
		int result = x + y;
		return result;
	}
	
	public int subtract (int x, int y) {
		return x - y;
	}
	
	public void setName (String newName) {
		this.name = newName;
	}
	
	public static useCalculator (Calculator.calc) {
		calc.setName ("sdfsdf");
		calc.add (5, 100);
	}
}
