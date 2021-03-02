package com.revature.rkiesling.bankmodel;

public class Post implements Postable {
        
    private int postType = Post.POST_NULL;
    private String dateTime = "";
    private String userName = "";
    private Integer tType = Postable.POST_NULL;
    private Double amount = 0.0;
    private String receiver = "";
    private Integer completed = Postable.INCOMPLETE;
        
    public Post () {
        super ();
    }
    public Post (int p) {
        super ();
        this.postType = p;
    }

    public Post (int posttype, String datetime, String username,
                 int ttype, double amount, String rcvr, int completed) {
        super ();
        this.postType = posttype;
        this.dateTime = datetime;
        this.userName = username;
        this.tType = ttype;
        this.amount = amount;
        this.receiver = rcvr;
        this.completed = completed;
    }

    public int postType ( ) {
        return this.postType;
    }
    public void postType (int p) {
        this.postType = p;
    }
    public String dateTime () {
        return this.dateTime;
    }
    public void dateTime (String d) {
        this.dateTime = d;
    }
    public String userName () {
        return this.userName;
    }
    public void userName (String u) {
        this.userName = u;
    }
    public Integer tType () {
        return this.tType;
    }
    public void tType (int t) {
        this.tType = t;
    }
    public Double amount () {
        return this.amount;
    }
    public void amount (double d) {
        this.amount = d;
    }
    public String receiver () {
        return this.receiver;
    }
    public void receiver (String s) {
        this.receiver = s;
    }
    public Integer completed () {
        return this.completed;
    }
    public void completed (int i) {
        this.completed = i;
    }
}
