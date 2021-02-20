package com.revature.ui;

public class MainMenu implements Menu {
	
	public void clearScreen ( ) {
		for (int i = 0; i < 30; ++i)
			System.out.println ("");
		System.out.flush ();
	}
	public void display () {
		System.out.println ("Welcome to the application.");
		
		int choice = 0;
		do {
			clearScreen ();
			System.out.println ("===Main Menu===");
			System.out.println ("Please select an option below:");
			System.out.println ("1.) Exit Application");
			System.out.println ("2.) User Menu");
			System.out.println ("3.) Post Menu");
			
			try {
				choice = Integer.parseInt (Menu.sc.nextLine ());
			} catch (NumberFormatException e) {
				System.out.println ("Invalid choice.  Please enter again.");
			}
			
			switch (choice)
			{
			case 1:
				break;
			case 2:
				Menu userMenu = new UserMenu ();
				userMenu.display ();
			case 3:
				Menu postMenu = new PostMenu ();
				postMenu.display ();
				break;
				default:
					System.out.println ("No valid choice entered.  Please try again.");
			}
		} while (choice != 1);
	}

}
