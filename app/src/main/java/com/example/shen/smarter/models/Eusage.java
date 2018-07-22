package com.example.shen.smarter.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Spinner;

public class Eusage {
    private Resident resid;
    private int usageid;
    private String date;
    private int recordhour;
    private double fridgeusage;
    private double airconusage;
    private double washingusage;
    private double temperature;




    public Eusage(){
        resid = new Resident();
        usageid = 0;
        date = "default";
        recordhour = 0;
        fridgeusage = 0.0;
        airconusage = 0.0;
        washingusage = 0.0;
        temperature = 0.0;
    }



    public Eusage( int usageId, String date, Resident resid, int recordHour, double fridgeUsage, double airconUsage, double washingUsage, double temperature) {
        this.usageid = usageId;
        this.date = date;
        this.resid = resid;
        this.recordhour = recordHour;
        this.fridgeusage = fridgeUsage;
        this.airconusage = airconUsage;
        this.washingusage = washingUsage;
        this.temperature = temperature;
    }

    public int getUsageId() {
        return usageid;
    }

    public void setUsageId(int usageId) {
        this.usageid = usageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    public double getRecordHour() {
        return recordhour;
    }

    public void setRecordHour(int recordHour) {
        this.recordhour = recordHour;
    }

    public double getFridgeUsage() {
        return fridgeusage;
    }

    public void setFridgeUsage(double fridgeUsage) {
        this.fridgeusage = fridgeUsage;
    }

    public double getAirconUsage() {
        return airconusage;
    }

    public void setAirconUsage(double airconUsage) {
        this.airconusage = airconUsage;
    }

    public double getWashingUsage() {
        return washingusage;
    }

    public void setWashingUsage(double washingUsage) {
        this.washingusage = washingUsage;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


}
