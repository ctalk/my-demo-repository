package com.revature.rkiesling.bankmodel;

public interface Postable {
        public static final int POST_NULL = 0;
        public static final int POST_NEW_ACCOUNT_APPL = 1;
        public static final int POST_NEW_ACCOUNT_AUTH = 2;
        public static final int POST_DEPOSIT = 3;
        public static final int POST_WITHDRAWAL = 4;
        public static final int POST_VIEW_BALANCE = 5;
        public static final int POST_SEND_XFER = 6;
        public static final int POST_RECEIVE_XFER = 7;
        
        // some return constants, also declared in AuthLevel interface.
        public static final int SUCCESS = 0;
        public static final int FAIL = 1;
                
        // If we have a transaction that requires someone else to complete it
        public static final int INCOMPLETE = 0;
        public static final int COMPLETE = 1;
        
}
