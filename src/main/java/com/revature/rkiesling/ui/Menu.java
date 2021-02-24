package com.revature.rkiesling.ui;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.InputStreamReader;
import java.io.IOException;

public class Menu {
	
	private ArrayList<String> items = new ArrayList<>();
	
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
		
		Scanner s = new Scanner (System.in);
		n = items.size ();
		System.out.print ("(1-" + n + "): ");
		while (!haveInput) {
			try {
				String str = new String (s.nextLine ());
				char c = str.charAt (0);
				i = c - '0';
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
	
	public void display (String heading) {
		// Displays menu until user enters an Integer, 1 <= n <= items.size
		int i;
		int user_i;
		
		System.out.println (heading);
		do {
				i = 1;
			for (String it: items) {
				System.out.println (i + ".) " + it);
				++i;
			}
			user_i = this.getOption ();
			System.out.println ("");
		} while (user_i > items.size ());
	}

}
