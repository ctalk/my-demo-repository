package com.revature.rkiesling.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankDBUtil {
	
	public static final String schemaName = "bank_app";
	
	public static void makeBankSchema () {
		try (Connection conSchema = JDBCConnection.getConnection ()) {
			String sql = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";
			PreparedStatement p = conSchema.prepareStatement(sql);
			p.setString(1, schemaName);
			ResultSet rs = p.executeQuery ();
			if (rs.next ()) {
				System.out.println ("Found, \"" + schemaName + ",\"schema.");
			} else {
				System.out.println ("Schema, \"" + schemaName + ",\" not found - creating.");
				try {
					sql = "create schema " + schemaName;
					p = conSchema.prepareStatement (sql);
					rs = p.executeQuery ();
				} catch (SQLException e) {
					System.out.println ("makeBankSchema: " + e.getMessage ());
				}
			}
			
		} catch (SQLException e) {
			System.out.println ("makeBankSchema: " + e.getMessage ());
			e.printStackTrace ();
		}
	}

}
