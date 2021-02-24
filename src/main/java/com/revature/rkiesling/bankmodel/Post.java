package com.revature.rkiesling.bankmodel;

public class Post implements Postable {
	
	private int postType = Post.POST_NULL;
	
	public Post () {
		super ();
	}
	public Post (int p) {
		this.postType = p;
	}
	public int postType ( ) {
		return this.postType;
	}
	public void postType (int p) {
		this.postType = p;
	}
	
}