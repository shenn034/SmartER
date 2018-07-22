package com.example.shen.smarter.models;


import java.util.Calendar;
import java.util.Date;

public class Resident {
    private int resid;
    private String fname;
    private String lname;
    private String dob;
    private String address;
    private int postcode;
    private String email;
    private String mobile;
    private int numberofres;
    private String provider;

    public Resident(int resid, String fname, String lname, String dob, String address, int postcode, String email, String mobile, int nob, String provider) {
        this.resid = resid;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        this.mobile = mobile;
        this.numberofres = nob;
        this.provider = provider;
    }

    public Resident() {
        resid = 0;
        fname = "test";
        lname = "test";
        dob = "test";
        address = "test";
        postcode = 1111;
        email = "test";
        mobile = "test";
        numberofres = 1;
        provider = "test";
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getNor() {
        return numberofres;
    }

    public void setNor(int nor) {
        this.numberofres = nor;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
