package com.revature.rkiesling.user;
import com.revature.rkiesling.bankmodel.AuthLevel;

public class User implements AuthLevel {
        private int auth = User.AUTH_NONE;
        public User () {
                super ();
        }
        public User (int a) {
                this.auth = a;
        }
        public int auth () {
                return this.auth;
        }
        public void auth (int a) {
                this.auth = a;
        }
}
