package com.revature.rkiesling.ui;

import java.util.Scanner;

public class ScreenUtil {
	
		public static final void clear () {
			for (int i = 0; i < 1000; i++)
				System.out.println ("\n");
		}
		
		public static final void pause () {
			Scanner s = new Scanner (System.in);
			System.out.println ("Press [Enter] to continue.");
			s.nextLine ();
		}

}
