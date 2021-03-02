package com.revature.rkiesling.bankmodel;

public class Post implements Postable {
        
    private int postType = Post.POST_NULL;
    private String timestamp = "";
    private String userName = "";
    private Integer tType = Postable.POST_NULL;
    private Double amount = 0.0;
    private String destUser = "";
    private Integer completed = Postable.INCOMPLETE;
        
    public Post () {
        super ();
    }
    public Post (int p) {
        super ();
        this.postType = p;
    }

    public Post (int posttype, String timestamp, String username,
                 int ttype, double amount, String rcvr, int completed) {
        super ();
        this.postType = posttype;
        this.timestamp = timestamp;
        this.userName = username;
        this.tType = ttype;
        this.amount = amount;
        this.destUser = rcvr;
        this.completed = completed;
    }

    public int postType ( ) {
        return this.postType;
    }
    public void postType (int p) {
        this.postType = p;
    }
    public String timestamp () {
        return this.timestamp;
    }
    public void timestamp (String d) {
        this.timestamp = d;
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
    public String destUser () {
        return this.destUser;
    }
    public void destUser (String s) {
        this.destUser = s;
    }
    public Integer completed () {
        return this.completed;
    }
    public void completed (int i) {
        this.completed = i;
    }
}
