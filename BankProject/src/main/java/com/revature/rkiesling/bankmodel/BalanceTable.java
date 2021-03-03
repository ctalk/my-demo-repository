package com.revature.rkiesling.bankmodel;

import java.util.ArrayList;
import java.util.Arrays;

public interface BalanceTable {
        public static final String balanceTableName = "bank_app.balances";      

        public static final ArrayList<String> colDefs = 
                        new ArrayList<>(Arrays.asList
                                        ("id serial primary key not null,",
                                          "username varchar(64) not null,",
                                          "balance numeric not null,",
                                          "auth numeric not null)"));
        
        // Some types of balances need authorization (like new balances).
        public static final int NEEDS_AUTH = 0;
        public static final int BAL_AUTH = 1;
                                        
}
