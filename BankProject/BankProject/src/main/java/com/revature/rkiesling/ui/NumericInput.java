package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.AuthLevel;

import org.apache.log4j.Logger;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class NumericInput implements AuthLevel {

    private static Logger log = Logger.getLogger (NewAccountService.class);

    public static double getDouble (String promptStr) {
        @SuppressWarnings("resource")
        Scanner s = new Scanner (System.in);
        // System.in can't be closed (at least here).
        double doubleInput = 0.0f;
        int retries = 0;

        while (true) {
            System.out.print (promptStr);
            try {
                doubleInput = Double.parseDouble (s.nextLine ());
                return doubleInput;
            } catch (NumberFormatException e) {
                if (++retries > AuthLevel.maxRetries) {
                    System.out.println ("Too many retries - exiting.");
                    System.exit (AuthLevel.SUCCESS);
                    log.info ("NumericInput.getDouble (): - Too many retries.  Exiting.");
                } else {
                    System.out.println ("Invalid number format - please re-enter");
                }
            }
        }
        
        // return input; // notreached
    }

    public static int getInt (String promptStr) {
        @SuppressWarnings("resource")
        Scanner s = new Scanner (System.in);
        // System.in can't be closed (at least here).
        int intInput = 0;
        int retries = 0;

        while (true) {
            System.out.print (promptStr);
            try {
                intInput = Integer.parseInt (s.nextLine ());
                return intInput;
            } catch (NumberFormatException e) {
                if (++retries > AuthLevel.maxRetries) {
                    System.out.println ("Too many retries - exiting.");
                    System.exit (AuthLevel.SUCCESS);
                    log.info ("NumericInput.getDouble (): - Too many retries.  Exiting.");
                } else {
                    System.out.println ("Invalid number format - please re-enter");
                }
            }
        }
        
        // return input; // notreached
    }
}
