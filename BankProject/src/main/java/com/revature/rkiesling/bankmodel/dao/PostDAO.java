package com.revature.rkiesling.bankmodel.dao;

import com.revature.rkiesling.bankmodel.BalanceTable;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.util.JDBCConnection;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.StringBuffer;

public class PostDAO implements BalanceTable, AuthLevel {

    private static Logger log = Logger.getLogger(UserDAO.class);
        
    public void addBalance (User user, Double balance, Integer auth)
        throws SQLException {
        Connection c = null;
        log.info ("addBalance");
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

        log.info (sql.toString ());

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
}
