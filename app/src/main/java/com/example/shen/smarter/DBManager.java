package com.example.shen.smarter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shen.smarter.models.DBStructure;
import com.example.shen.smarter.models.Eusage;
import com.google.android.gms.common.util.DbUtils;


import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DBManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "usage";
    public static final int DATABASE_VERSION = 1;


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBStructure.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("delete from " + DBStructure.TABLE_NAME);
        onCreate(db);

    }

    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DBStructure.TABLE_NAME);
        onCreate(db);
    }
    public HashMap<Long, DBStructure> getAllReocords() {
        HashMap<Long, DBStructure> recorddb = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBStructure.TABLE_NAME, null);
        // Add each book to hashmap (Each row has 1 person)
        while (cursor.moveToNext()) {
            DBStructure dbStructure = new DBStructure(cursor.getLong(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    cursor.getDouble(5),
                    cursor.getDouble(6),
                    cursor.getDouble(7));
            recorddb.put(dbStructure.get_id(), dbStructure);
        }
        cursor.close();
        db.close();
        if (recorddb.size() == 0) {
            // If there are no book in the db then add some default books
            //createDefaultBook();
            recorddb = getAllReocords();
        }
        return recorddb;
    }



    public void addUsage(DBStructure dbStructure) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBStructure.COLUMN_USAGEID, dbStructure.getUsageId());
        values.put(DBStructure.COLUMN_DATE,dbStructure.getDate());
        values.put(DBStructure.COLUMN_RECORDHOUR,dbStructure.getRecordHour());
        values.put(DBStructure.COLUMN_FRIDGEUSAGE,dbStructure.getFridgeUsage());
        values.put(DBStructure.COLUMN_AIRCONUSAGE,dbStructure.getAirconUsage());
        values.put(DBStructure.COLUMN_WASHINGUSAGE,dbStructure.getWashingUsage());
        values.put(DBStructure.COLUMN_TEMPERATURE,dbStructure.getTemperature());
        db.insert(DBStructure.TABLE_NAME,null,values);
        db.close();
    }

    }

