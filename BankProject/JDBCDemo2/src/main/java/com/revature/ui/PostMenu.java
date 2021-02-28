package com.revature.ui;

public class PostMenu implements Menu {
	
	public void clearScreen () {
		
	}
	public void display () {
		int choice = 0;
		do {
			System.out.println ("===Post Menu===");
			System.out.println ("Please select an option below.");
			System.out.println ("1.) Back");
			System.out.println ("2.) Create post");
			
			try {
				choice = Integer.parseInt (Menu.sc.nextLine ());
			} catch (NumberFormatException e) {
				
			}
		
			switch (choice)
			{
			case 1:
				break;
			case 2:
				String username = getUserNameInput ();
				break;
				default:
					System.out.println ("No valid choice entered.  Please try again.");			
			}
		} while (choice != 1);
	}
	
	public String getUserNameInput () {
		System.out.println ("Enter a user name that you would like to create a post for:");
		String input = Menu.sc.nextLine().trim ();
		return input;
		
	}
}
