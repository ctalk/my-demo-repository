package com.revature.rkiesling.ui;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Menu {
	
	private ArrayList<String> items = new ArrayList<>();
	public int choice;	
	private Scanner sc;
	
	public Menu () {
		sc = new Scanner (System.in);
	}
	
	public void clear () {
		this.items.clear ();
	}
	
	public ArrayList<String> items () {
		return this.items;
	}
	
	private int getOption () {
		int i = 0;
		int n;
		boolean haveInput = false;
		
		n = items.size ();
		System.out.print ("(1-" + n + "): ");
		while (!haveInput) {
			try {
				String str = new String (sc.nextLine ());
				try {
					i = Integer.parseInt(str);
				} catch (NumberFormatException e) {	
					// bad choice, fails the range check in display so we display the 
					// menu again and call this method again
					return 0;
				}
				haveInput = true;
			} catch ( NoSuchElementException e) {			
				System.out.println (e.getMessage ()); 
			}
		}
		return i;
	}
	
	public void add (String s) {
		items.add(s);
	}
	
	public int display (String heading) {
		// Displays menu until user enters an Integer, 1 <= n <= items.size
		int i;
		
		System.out.println (heading);
		do {
				i = 1;
			for (String it: items) {
				System.out.println (i + ".) " + it);
				++i;
			}
			choice = this.getOption ();
			System.out.println ("");
		} while (choice > items.size ());
		return choice;
	}
	
	public int userChoice () {
		return this.choice;
	}
}
	
