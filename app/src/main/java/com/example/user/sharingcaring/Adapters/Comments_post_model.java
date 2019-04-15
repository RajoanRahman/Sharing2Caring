package com.example.user.sharingcaring.Adapters;

public class Comments_post_model {

    String comment,uid,userName;

    public Comments_post_model() {
    }

    public Comments_post_model(String comment, String uid, String userName) {
        this.comment = comment;
        this.uid = uid;
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
