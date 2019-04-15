package com.example.user.sharingcaring.Adapters;

public class DonatePostModel {

    public String date,aboutDonation,cellNumber,fullname,profileimage,time,uid,othersInfo;


    public DonatePostModel() {
    }

    public DonatePostModel(String date, String aboutDonation, String cellNumber, String fullname,
                           String profileimage, String time, String uid, String othersInfo) {
        this.date = date;
        this.aboutDonation = aboutDonation;
        this.cellNumber = cellNumber;
        this.fullname = fullname;
        this.profileimage = profileimage;
        this.time = time;
        this.uid = uid;
        this.othersInfo = othersInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAboutDonation() {
        return aboutDonation;
    }

    public void setAboutDonation(String aboutDonation) {
        this.aboutDonation = aboutDonation;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOthersInfo() {
        return othersInfo;
    }

    public void setOthersInfo(String othersInfo) {
        this.othersInfo = othersInfo;
    }
}
