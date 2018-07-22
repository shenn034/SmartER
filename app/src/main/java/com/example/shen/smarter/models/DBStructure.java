package com.example.shen.smarter.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DBStructure implements Parcelable {
    public static final String TABLE_NAME = "usage";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USAGEID = "usageid";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_RECORDHOUR = "recordhour";
    public static final String COLUMN_FRIDGEUSAGE = "fridgeusage";
    public static final String COLUMN_AIRCONUSAGE = "airconusage";
    public static final String COLUMN_WASHINGUSAGE = "washingusage";
    public static final String COLUMN_TEMPERATURE = "temperature";

    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_USAGEID + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_RECORDHOUR + " TEXT, " +
            COLUMN_FRIDGEUSAGE + " TEXT, " +
            COLUMN_AIRCONUSAGE + " TEXT, " +
            COLUMN_WASHINGUSAGE + " TEXT, " +
            COLUMN_TEMPERATURE + " TEXT " + ")";


    private long _id;
    private int usageid;
    private String date;
    private int recordhour;
    private double fridgeusage;
    private double airconusage;
    private double washingusage;
    private double temperature;

    protected DBStructure(Parcel in){
        _id = in.readLong();
        usageid = in.readInt();
        date = in.readString();
        recordhour = in.readInt();
        fridgeusage = in.readDouble();
        airconusage = in.readDouble();
        washingusage = in.readDouble();
        temperature = in.readDouble();
    }

    public DBStructure(){
        usageid = 0;
        date = "default";
        recordhour = 0;
        fridgeusage = 0.0;
        airconusage = 0.0;
        washingusage = 0.0;
        temperature = 0.0;
    }

    public static final Creator<DBStructure> CREATOR = new Creator<DBStructure>() {
        @Override
        public DBStructure createFromParcel(Parcel in) {
            return new DBStructure(in);
        }

        @Override
        public DBStructure[] newArray(int size) {
            return new DBStructure[size];
        }
    };



    public DBStructure( long _id, int usageId, String date, int recordHour, double fridgeUsage, double airconUsage, double washingUsage, double temperature) {
        this._id = _id;
        this.usageid = usageId;
        this.date = date;
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


    public int getRecordHour() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    public long get_id() {return _id;}
    public void set_id(long _id) {this._id = _id;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeInt(usageid);
        parcel.writeString(date);
        parcel.writeInt(recordhour);
        parcel.writeDouble(fridgeusage);
        parcel.writeDouble(airconusage);
        parcel.writeDouble(washingusage);
        parcel.writeDouble(temperature);

    }

}
