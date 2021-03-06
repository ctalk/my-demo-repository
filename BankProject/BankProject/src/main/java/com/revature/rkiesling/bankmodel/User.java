package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.DisplayUserRecord;

public class User {
        
        protected String userName = "";
        protected String passWord = "";
        protected String firstName = "";
        protected String lastName = "";
        protected String address = "";
        protected Integer zipCode = 0;
        protected String comment = "";
        protected Integer auth = 0;
    protected Double balance = 0.0;
        
        public User () {
                super ();
        }
        public User (String username, String firstname, String lastname, Integer authlvl, 
                        String zipcode, String address, String comment) {
                super ();
                userName = username;
                firstName = firstname;
                lastName = lastname;
                auth = authlvl;
                this.zipCode = Integer.parseInt (zipcode);
                this.address = address;
                this.comment = comment;
        }
        
        public String userName () {
                return this.userName;
        }
        public void userName (String s) {
                this.userName = s;
        }
        public void passWord (String s) {
                this.passWord = s;
        }
        public String passWord () {
                return this.passWord;
        }
        public void firstName (String name) {
                this.firstName = name;
        }
        public String firstName () {
                return this.firstName;
        }
        public void lastName (String name) {
                this.lastName = name;
        }
        public String lastName () {
                return this.lastName;
        }
        public void auth (int authlvl) {
                this.auth = authlvl;
        }
        public Integer auth () {
                return this.auth;
        }
        public String address () {
                return this.address;
        }
        public void address (String addr) {
                this.address = addr;
        }
        public Integer zipCode () {
                return this.zipCode;
        }
        public void zipCode (String zip) {
                this.zipCode = Integer.parseInt(zip);
        }
        public String comment () {
                return this.comment;
        }
        public void comment (String c) {
                this.comment = c;
        }
    public Double balance () {
        return this.balance;
    }
    public void balance (double b) {
        this.balance = b;
    }

}
