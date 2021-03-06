package com.revature.rkiesling.bankmodel.dao;

import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import com.revature.rkiesling.bankmodel.User;

import com.revature.rkiesling.util.JDBCConnection;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.UserTable;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.StringBuffer;
import java.util.ArrayList;


public class UserDAO implements AuthLevel, UserTable {

        private static Logger log = Logger.getLogger(UserDAO.class);
        

    // When used with two args, matches both the user name and password,
    // when used with one arg, matches the username only, and is here
    // mainly to check whether a user record exists.
    public User getLoginInfo (String userName, String password) throws UserNotFoundException {
                Connection c = null;
                try {
                        c = JDBCConnection.getConnection ();
                } catch (SQLException e) {
                        System.out.println ("Connection error: " + e.getMessage ());
                        log.error ("JDBCConnection error: " + e.getMessage ());
                        System.exit(AuthLevel.FAIL);
                }
                String sql = "select * from bank_app.users where username = '" + userName + "'";
                try {
                        Statement stmt = c.createStatement ();
                        ResultSet rs = stmt.executeQuery(sql);
                        User user = null;
                        if (rs.next ()) {
                                String dbpass = null;
                                dbpass = rs.getString("password");
                                if (dbpass.equals(password)) {
                                        user = new User (rs.getString("username"), rs.getString("firstName"),
                                                        rs.getString("lastName"), rs.getInt ("authlevel"), 
                                                        rs.getString("zipcode"), rs.getString("address"), rs.getString("comment"));
                                        c.close ();
                                        log.info ("User " + userName + ": Login successful.");
                                        return user;
                                }
                        }
                        
                        log.info ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
                        throw new UserNotFoundException ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
                        
                } catch (SQLException e) {
                        log.error("Bad SQL query: " + sql);
                } 
        
                JDBCConnection.close (c);
                return null;

        }
        
        public User getLoginInfo (String userName) throws UserNotFoundException {
                Connection c = null;
                try {
                        c = JDBCConnection.getConnection ();
                } catch (SQLException e) {
                        System.out.println ("Connection error: " + e.getMessage ());
                        log.error ("JDBCConnection error: " + e.getMessage ());
                        System.exit(AuthLevel.FAIL);
                }
                String sql = "select * from bank_app.users where username = '" + userName + "'";
                try {
                        Statement stmt = c.createStatement ();
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next ()) {
                           User user = new User (rs.getString("username"), rs.getString("firstName"),
                                                        rs.getString("lastName"), rs.getInt ("authlevel"), 
                                                        rs.getString("zipcode"), rs.getString("address"), rs.getString("comment"));
                           return user;
                        }
                        
                        log.info ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
                        throw new UserNotFoundException ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
                        
                } catch (SQLException e) {
                        log.error("Bad SQL query: " + sql);
                } 
        
                JDBCConnection.close (c);
                return null;

        }
        
        public void addUser (User user) throws SQLException {
                Connection c = null;
                try {
                        c = JDBCConnection.getConnection ();
                } catch (SQLException e) {
                        System.out.println ("Connection error: " + e.getMessage ());
                        log.error ("JDBCConnection error: " + e.getMessage ());
                        System.exit(AuthLevel.FAIL);
                }
                StringBuffer sql = new StringBuffer ("insert into " + UserTable.userTableName + "(");
                // sql.append(UserTable.userTableName + "(");
                for (String col: UserTable.colNames) {
                        sql.append (col);
                }
                sql.append("values (");
                sql.append("'" + user.userName () + "', ");
                sql.append("'" + user.firstName () + "', ");
                sql.append("'" + user.lastName () + "', ");
                sql.append("'" + user.passWord() + "', ");
                sql.append(user.auth () + ", ");
                sql.append("'" + user.address() + "', ");
                sql.append(user.zipCode() + ", ");
                sql.append("'" + user.comment() + "')");
                
                try {
                        Statement stmt = c.createStatement ();
                        @SuppressWarnings("unused")
                        Integer nUpdates = stmt.executeUpdate(sql.toString ());
                } catch (SQLException e) {
                        log.error("Bad SQL query: " + sql);
                        throw e;
                } 
                JDBCConnection.close (c);
        }

    public ArrayList<User> getUsers (int authstatus) {
        ArrayList<User> userSet = new ArrayList<User>();
        try (Connection c = JDBCConnection.getConnection ()) {
            StringBuffer sql = new StringBuffer ("select * from " + UserTable.userTableName + " where authlevel = " + AuthLevel.AUTH_GUEST);
            Statement stmt = c.createStatement ();
            ResultSet rs = stmt.executeQuery(sql.toString ());
            while (rs.next ()) {
                Double zip = new Double (rs.getDouble ("zipcode"));
                Integer zipInt = zip.intValue();
                User u = new User (rs.getString ("username"),
                                   rs.getString ("firstname"),
                                   rs.getString ("lastname"),
                                   rs.getInt ("authlevel"),
                                   zipInt.toString (),
                                   rs.getString ("address"),
                                   rs.getString ("comment"));
                userSet.add(u);
            }
        } catch (SQLException e) {
            log.error ("getUsers (int): " + e.getMessage ());
        }
        return userSet;
    }

    public void update (User u, Integer newAuthStatus) {
        try (Connection c = JDBCConnection.getConnection ()) {
            StringBuffer sql = new StringBuffer ("update " + UserTable.userTableName + " set authlevel = " + newAuthStatus +
                                                 " where username = '" + u.userName () + "'");
            Statement stmt = c.createStatement ();
            log.info (sql.toString ());
            @SuppressWarnings("unused")
            Integer nUpdates = stmt.executeUpdate(sql.toString ());

        } catch (SQLException e) {
            log.error ("update (User, int): " + e.getMessage ());
        }
    }
}
