package com.example.user.sharingcaring.Adapters;

public class TutorPostModel {
    public String date,aboutTuition,location,fullname,salaryMonthly,profileimage,time,uid,whichClass,phoneNumber;

    public TutorPostModel() {
    }

    public TutorPostModel(String date, String aboutTuition, String location, String fullname,
                          String salaryMonthly, String profileimage, String time, String uid, String whichClass,String phoneNumber) {
        this.date = date;
        this.aboutTuition = aboutTuition;
        this.location = location;
        this.fullname = fullname;
        this.salaryMonthly = salaryMonthly;
        this.profileimage = profileimage;
        this.time = time;
        this.uid = uid;
        this.whichClass = whichClass;
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

    public String getAboutTuition() {
        return aboutTuition;
    }

    public void setAboutTuition(String aboutTuition) {
        this.aboutTuition = aboutTuition;
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

    public String getSalaryMonthly() {
        return salaryMonthly;
    }

    public void setSalaryMonthly(String salaryMonthly) {
        this.salaryMonthly = salaryMonthly;
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

    public String getWhichClass() {
        return whichClass;
    }

    public void setWhichClass(String whichClass) {
        this.whichClass = whichClass;
    }
}
