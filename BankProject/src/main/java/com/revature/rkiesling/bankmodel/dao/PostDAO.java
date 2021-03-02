package com.revature.rkiesling.bankmodel.dao;

import com.revature.rkiesling.bankmodel.BalanceTable;
import com.revature.rkiesling.bankmodel.TransactionTable;
import com.revature.rkiesling.bankmodel.Postable;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.util.JDBCConnection;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.StringBuffer;
import java.sql.ResultSet;

public class PostDAO implements BalanceTable, AuthLevel,
                                TransactionTable, Postable {

    private static Logger log = Logger.getLogger(UserDAO.class);
        
    public void postPendingAppl (User user) {
        Connection c = null;
        try  {
        	c = JDBCConnection.getConnection ();
        } catch (SQLException e) {
            System.out.println ("Connection error: " + e.getMessage ());
            log.error ("JDBCConnection error: " + e.getMessage ());
            System.exit(AuthLevel.FAIL);
        }

        StringBuffer sql = new StringBuffer ("insert into " +
                                         TransactionTable.transactionTableName +
                                             "(username, " +
                                             "ttype, " +
                                             "amount, " +
                                             "completed)");
        sql.append(" values (");
        sql.append("'" + user.userName () + "', ");
        sql.append (Postable.POST_NEW_ACCOUNT_APPL + ", ");
        sql.append(user.balance() + ", ");
        sql.append(Postable.INCOMPLETE + ")");

        // log.info(sql);

        try {
            Statement stmt = c.createStatement ();
            @SuppressWarnings("unused")
                Integer nUpdates = stmt.executeUpdate(sql.toString ());
        } catch (SQLException e) {
            log.error("Bad SQL query: " + sql);
            // throw e;  // needed? 
        } 
        try { 
            c.close ();
        } catch (SQLException e) {
            log.error("JDBCConnection.close(): " + e.getMessage()); 
        }
    }

    public void addBalance (User user, Double balance, Integer auth)
        throws SQLException {
        Connection c = null;
        try {
            c = JDBCConnection.getConnection ();
        } catch (SQLException e) {
            System.out.println ("Connection error: " + e.getMessage ());
            log.error ("JDBCConnection error: " + e.getMessage ());
            System.exit(AuthLevel.FAIL);
        }
        StringBuffer sql = new StringBuffer ("insert into " +
                                             BalanceTable.balanceTableName +
                                             "(username, " +
                                             "balance," +
                                             "auth) ");
        sql.append("values (");
        sql.append("'" + user.userName () + "', ");
        sql.append(balance + ", ");
        sql.append(auth + ")");

        try {
            Statement stmt = c.createStatement ();
            @SuppressWarnings("unused")
                Integer nUpdates = stmt.executeUpdate(sql.toString ());
        } catch (SQLException e) {
            log.error("Bad SQL query: " + sql);
            throw e;
        } 
        try { 
            c.close ();
        } catch (SQLException e) {
            log.error("JDBCConnection.close(): " + e.getMessage()); 
        }
    }

    public void getBalanceForUser (User user) {

        try (Connection c = JDBCConnection.getConnection ()) {
            StringBuffer sql = new StringBuffer ("select (balance) from " + BalanceTable.balanceTableName +
                                                 " where username = '");
	    sql.append (user.userName () + "'");

            try {
                Statement stmt = c.createStatement ();
                ResultSet rs = stmt.executeQuery (sql.toString ());
                if (rs.next ()) {
		    double d = (double)rs.getFloat ("balance");
		    log.info ("d = " + d);
                    user.balance(d);
                }
            } catch (SQLException e) {
                log.error("Bad SQL query: " + sql);
                // throw e;  // needed? 
            } 
            try { 
                c.close ();
            } catch (SQLException e) {
                log.error("JDBCConnection.close(): " + e.getMessage()); 
            }

        } catch (SQLException e) {
            System.out.println ("Connection error: " + e.getMessage ());
            log.error ("JDBCConnection error: " + e.getMessage ());
            System.exit(AuthLevel.FAIL);
        }

    }
}
