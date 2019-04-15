package com.example.user.sharingcaring.Adapters;

public class BloodPostModel {
    public String date,aboutBlood,location,fullname,bloodGroup,profileimage,time,uid,whenNeed,phoneNumber;


    public BloodPostModel() {

    }

    public BloodPostModel(String date, String aboutBlood, String location, String fullname,
                          String blood_group, String profileimage, String time, String uid, String whenNeed,String phoneNumber) {
        this.date = date;
        this.aboutBlood = aboutBlood;
        this.location = location;
        this.fullname = fullname;
        this.bloodGroup = blood_group;
        this.profileimage = profileimage;
        this.time = time;
        this.uid = uid;
        this.whenNeed = whenNeed;
        this.phoneNumber=phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAboutBlood() {
        return aboutBlood;
    }

    public void setAboutBlood(String aboutBlood) {
        this.aboutBlood = aboutBlood;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
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

    public String getWhenNeed() {
        return whenNeed;
    }

    public void setWhenNeed(String whenNeed) {
        this.whenNeed = whenNeed;
    }
}
