package com.example.hms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HMSOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="hms.db";
    public static final int DATABASE_VERSION =1;
    public HMSOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HMSDataBaseContract.Student_info_Entry.SQL_CREATE_TABLE);
        db.execSQL(HMSDataBaseContract.Complaint_info_Entry.SQL_CREATE_TABLE);
        db.execSQL(HMSDataBaseContract.Notice_info_Entry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
