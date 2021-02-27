package com.revature.rkiesling.bankmodel;

import java.util.ArrayList;
import java.util.Arrays;

public interface TransactionTable {
	public static final String schemaName = "bank_app";
	
	public static final String transactionTableName = "bank_app.transactions";	

	public static final ArrayList<String> colDefs = 
			new ArrayList<>(Arrays.asList
					("id serial primary key not null,",
					 "username varchar(64) not null)"));

}
