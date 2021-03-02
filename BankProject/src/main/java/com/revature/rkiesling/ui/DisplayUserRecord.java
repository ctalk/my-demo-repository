package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.AuthLevel;

public class DisplayUserRecord {

    public static void printRec (User rec) {
        String authString = "";
    
        switch (rec.auth ())
            {
            case AuthLevel.AUTH_GUEST:
                authString = "Guest";
                break;
            case AuthLevel.AUTH_CUSTOMER:
                authString = "Customer";
                break;
            case AuthLevel.AUTH_EMPLOYEE:
                authString = "Employee";
                break;
            case AuthLevel.AUTH_ADMIN:
                authString = "Admin";
                break;
            }
        System.out.println ("User name:\t" + rec.userName ());
        System.out.println ("First name:\t" + rec.firstName ());
        System.out.println ("Last name:\t" + rec.lastName ());
        System.out.println ("Password:\t-----");
        System.out.println ("Auth:\t\t" + authString);
        System.out.println ("Address:\t" + rec.address ());
        System.out.println ("Zip code:\t" + rec.zipCode().toString ());
        System.out.println ("Balance:\t" + rec.balance ().toString ());
        }
}
