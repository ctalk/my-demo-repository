package com.revature.rkiesling.bankmodel;

import java.util.ArrayList;
import java.util.Arrays;

public interface TransactionTable {
        public static final String schemaName = "bank_app";
        
        public static final String transactionTableName = "bank_app.transactions";      

        public static final ArrayList<String> colDefs = 
                        new ArrayList<>(Arrays.asList
                                        ("id serial primary key not null,",
                                         "datetime timestamp default current_timestamp,",
                                         "username varchar(64),",
                                         "ttype int,", 
                                         "amount numeric,",
                                         "rcvr varchar(64),",
                                         "completed int,",
                                         "comment text)"));

}
