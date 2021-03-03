package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.NumericInput;
import com.revature.rkiesling.ui.DisplayUserRecord;
import com.revature.rkiesling.ui.DisplayPostRecord;
import com.revature.rkiesling.ui.ScreenUtil;
import com.revature.rkiesling.ui.NewAccountService;
import com.revature.rkiesling.bankmodel.TransactionTable;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.exception.UserAlreadyExistsException;

import java.util.Scanner;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer implements Postable, TransactionTable {

    private static Logger log = Logger.getLogger(Customer.class);
    private static Scanner sc = new Scanner (System.in);

    public static void sendMoney (User user) {
        PostDAO pdao = new PostDAO ();
        UserDAO udao = new UserDAO ();

        double amount = NumericInput.getDouble ("Enter the amount to transfer: ");
        pdao.getBalanceForUser (user);

        if (amount > user.balance ()) {
            System.out.println ("You don't have enough money in your account.");
            ScreenUtil.pause ();
            return;
        } else if (amount <= 0) {
            System.out.println ("Please enter a positive number.");
            ScreenUtil.pause ();
            return;
        }

        System.out.print ("Enter the receiver's user name: ");
        String destUserStr = sc.nextLine ();
        try {
            User destUser = udao.getLoginInfo (destUserStr);
            pdao.postSendMoney (user, destUser, amount);
        } catch (UserNotFoundException e) {
            System.out.println ("User " + destUserStr + " not found.");
            ScreenUtil.pause ();
            return;
        }       
        
    }

    private static void daoBalance (User u, Integer ttype) {
        PostDAO pdao = new PostDAO ();
        pdao.updateBalance (u, Postable.COMPLETE);
        pdao.postBalanceOp (u, ttype);
    }

    public static void customerWithdraw (User u) {
        PostDAO pdao = new PostDAO ();
        pdao.getBalanceForUser (u);
        double d = NumericInput.getDouble ("Enter the withdrawal amount: ");
        if (d > u.balance ()) {
            System.out.println ("There are not funds in this account.");
            ScreenUtil.pause ();
        } else if (d < 0.0) {
            System.out.println ("You can't withdraw a negative amount.");
            ScreenUtil.pause ();
        } else {
            double d1 = u.balance () - d;
            u.balance (d1);
            daoBalance (u, Postable.POST_WITHDRAWAL);
        }
    }

    public static void customerDeposit (User u) {
        PostDAO pdao = new PostDAO ();
        pdao.getBalanceForUser (u);
        double d = NumericInput.getDouble ("Enter the deposit amount: ");
        if (d < 0.0) {
            System.out.println ("You can't deposit a negative amount.");
            ScreenUtil.pause ();
        } else {
            double d1 = u.balance () + d;
            u.balance (d1);
            daoBalance (u, Postable.POST_DEPOSIT);
        }
    }

    public static void acceptMoneyTransfer (User user, Post post) {
        PostDAO dao = new PostDAO ();
        double newBalance;

        dao.getBalanceForUser (user);
        
        newBalance = post.amount ();
        newBalance += user.balance ();
        user.balance (newBalance);
        daoBalance (user, Postable.POST_DEPOSIT);

        // First set the sender's transaction to completed.
        String sql = "update " + TransactionTable.transactionTableName +
            " set completed = " + Postable.COMPLETE + " where rcvr = '" + user.userName () +
            "'";
        dao.postSQLUpdate (sql);
        // Then add the posting that we've received the money.
        sql = "insert into " + TransactionTable.transactionTableName +
            "(username, ttype, amount, completed) values ('" + user.userName () + "', " + 
            Postable.POST_RECEIVE_XFER + ", " + user.balance () + ", " + Postable.COMPLETE + ")";
        log.info (sql);
        dao.postSQLUpdate (sql);
    }

    public static Boolean haveMoneyTransfers (User user) {
        String sql = "Select * from " + TransactionTable.transactionTableName +
            " where rcvr = '" + user.userName () + "' and ttype = " + Postable.POST_SEND_XFER +
            " and completed != " + Postable.COMPLETE;
        PostDAO pdao = new PostDAO ();
        ArrayList<Post> records = pdao.queryTransactions (sql);
        if (records.size () > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Post> getMoneyTransfers (User user) {
        String sql = "Select * from " + TransactionTable.transactionTableName +
            " where rcvr = '" + user.userName () + "' and ttype = " + Postable.POST_SEND_XFER +
            " and completed != " + Postable.COMPLETE;
        PostDAO pdao = new PostDAO ();
        ArrayList<Post> records = pdao.queryTransactions (sql);
        return records;
    }

    public static void customerMenu (User user) {
        Menu m = new Menu ();
        UserDAO dao = new UserDAO ();
        PostDAO pdao = new PostDAO ();

        m.add("View your account");
        m.add("Withdraw money");
        m.add("Deposit money");
        m.add("Add another account");
        m.add("Receive money");
        m.add("Send money");
        m.add("Exit");

        while (true) {
            switch (m.display("Please select an option:"))
                {
                case 1:
                    System.out.println ("Your Account:");
                    System.out.println ("---- -------");
                    try {
                        User nuser = dao.getLoginInfo (user.userName ());
                        pdao.getBalanceForUser (nuser);
                        DisplayUserRecord.printRec(nuser);
                        System.out.println ("");
                    } catch (UserNotFoundException e) {
                        log.error ("newUserMenu: " + e.getMessage ());
                    }
                    break;
                case 2:
                    customerWithdraw (user);
                    break;
                case 3:
                    customerDeposit (user);
                    break;
                case 4:
                    User newUser = null;
                    int retries = 0;
                    do {
                        try {
                            newUser = NewAccountService.newUserForm ("Please enter the new account information.", AuthLevel.AUTH_GUEST);
                        } catch (UserAlreadyExistsException e) {
                            if (retries == AuthLevel.maxRetries) {
                                System.out.println ("Login failed - too many retries.  Goodbye.");
                                log.info("User login unsuccessful - exiting.");
                                System.exit(AuthLevel.SUCCESS);                                 
                            } else {
                                System.out.println ("User not found. Please re-enter.");
                            }
                        }
                    } while ((newUser == null) && (++retries <= AuthLevel.maxRetries));

                    try {
                        dao.addUser (newUser);
                        //                      if (authlvl == AuthLevel.AUTH_GUEST) {
                            newUser.balance(NumericInput.getDouble ("Opening balance: "));
                            pdao.addBalance (newUser, newUser.balance (),
                                             BalanceTable.NEEDS_AUTH);
                            pdao.postPendingAppl (newUser);
                            //}
                    } catch (SQLException e) {
                        // Don't print anything at the moment - there should only be
                        // duplicate record exceptions, and the new user form has already
                        // checked for them.
                        log.info("Customer.customerMenu (): " + e.getMessage ());
                    }
                    
                    break;
                case 5:
                    if (haveMoneyTransfers (user)) {
                        ArrayList<Post> transfers = getMoneyTransfers (user);
                        if (transfers.size () == 1) {
                            System.out.println ("There is 1 transfer for you.");
                        } else {
                            System.out.println ("There are " + transfers.size () + " transfers for you.");
                        }
                        System.out.println ("");
                        for (Post t: transfers) {
                            DisplayPostRecord.printRec (t);
                            System.out.println ("(A) Accept or (D) Decline?");
                            String ans = sc.nextLine ();
                            if (ans.equals ("a") || ans.equals ("A")) {
                                acceptMoneyTransfer (user, t);
                            }
                        }
                        ScreenUtil.pause ();
                    } else {
                        System.out.println ("There are no money transfers for you.");
                        ScreenUtil.pause ();
                    }
                    break;
                case 6:
                    sendMoney (user);
                    break;
                case 7:
                    System.out.println ("\nExiting - goodbye.");
                    System.exit(AuthLevel.SUCCESS);
                    break;
                }
        }
    }

}
