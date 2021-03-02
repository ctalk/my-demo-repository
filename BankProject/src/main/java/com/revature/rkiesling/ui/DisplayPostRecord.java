package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.Postable;
import com.revature.rkiesling.bankmodel.Post;

public class DisplayPostRecord implements Postable {

    public static void printRec (Post rec) {
        String postTypeStr = "";
        String completeStr = "";

        switch (rec.postType ())
            {
            case Postable.POST_NEW_ACCOUNT_APPL:
                postTypeStr = "New Account Application";
                break;
            case Postable.POST_NEW_ACCOUNT_AUTH:
                postTypeStr = "New Account Authorization";
                break;
            case Postable.POST_DEPOSIT:
                postTypeStr = "Deposit";
                break;
            case Postable.POST_WITHDRAWAL:
                postTypeStr = "Withdrawal";
                break;
            case Postable.POST_VIEW_BALANCE:
                postTypeStr = "View Balance";
                break;
            case Postable.POST_SEND_XFER:
                postTypeStr = "Send Money to User";
                break;
            case Postable.POST_RECEIVE_XFER:
                postTypeStr = "Receive Money from User";
                break;
            }

        switch (rec.completed ())
            {
            case Postable.COMPLETE:
                completeStr = "Completed";
                break;
            case Postable.INCOMPLETE:
                completeStr = "Incomplete";
                break;
            }

        
        System.out.println ("Type:\t(" + rec.postType () + ") " + postTypeStr);
        System.out.println ("Date and time:\t" + rec.timestamp ());
        System.out.println ("User:\t" + rec.userName ());
        System.out.println ("Amount:\t\t" + rec.amount ());
        System.out.println ("Dest. User:\t" + rec.destUser ());
        System.out.println ("Completed:\t" + completeStr);
        }

}
