package com.example.shen.smarter.models;

import java.sql.Date;

public class Credentials {
    private String username;
    private String password;
    private Resident resid;
    private String regdate;

    public Credentials(String username, String password, Resident resid, String date) {
        this.username = username;
        this.password = password;
        this.resid = resid;
        this.regdate = date;
    }

    public Credentials() {
        this.username = "default";
        this.password = "default";
        this.resid = new Resident();
        this.regdate = "default";
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    public String getDate() {
        return regdate;
    }

    public void setDate(String date) {
        this.regdate = date;
    }
}
