package com.revature.rkiesling.bankmodel;

import java.util.ArrayList;
import java.util.Arrays;

public interface UserTable {
        
        public static final String schemaName = "bank_app";
        public static final String userTableName = "bank_app.users";
        
        // The bank_app.users column names - including punctuations
        // which makes building the SQL queries a lot easier
        public static final ArrayList<String> colNames = 
                        new ArrayList<>(Arrays.asList
                                        ("username,", 
                                        "firstname,", 
                                        "lastname,",
                                        "password,",
                                        "authlevel,",
                                        "address,",
                                        "zipcode,",
                                        "comment) "));
        
        // When we create the table.
        public static final ArrayList<String> colDefs = 
                        new ArrayList<>(Arrays.asList
                                        ("id serial primary key not null,",
                                          "username varchar(64) not null unique,",
                                          "firstName varchar(64) not null,",
                                          "lastName varchar(64) not null,",
                                          "password varchar(64) not null,",
                                          "authLevel int not null,",
                                          "address varchar(255) not null,",
                                          "zipCode int not null,",
                                          "comment text)"));
                        
}


