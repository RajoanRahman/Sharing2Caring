package com.example.user.sharingcaring.Adapters;

public class RideSharePostModel {

    public String date,aboutRide,location,fullname,rideTime,profileimage,time,uid,phoneNumber;

    public RideSharePostModel() {
    }

    public RideSharePostModel(String date, String aboutRide, String location, String fullname, String rideTime,
                              String profileimage, String time, String uid,String phoneNumber) {
        this.date = date;
        this.aboutRide = aboutRide;
        this.location = location;
        this.fullname = fullname;
        this.rideTime = rideTime;
        this.profileimage = profileimage;
        this.time = time;
        this.uid = uid;
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

    public String getAboutRide() {
        return aboutRide;
    }

    public void setAboutRide(String aboutRide) {
        this.aboutRide = aboutRide;
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

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
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
}
