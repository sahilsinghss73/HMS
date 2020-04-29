package com.example.hms;

import android.provider.BaseColumns;

public final class HMSDataBaseContract {
    private HMSDataBaseContract () {}//make non creatable

    public static final class Student_info_Entry implements BaseColumns
    {
        public static final String TABLE_name="student_info";
        public static final String COLUMN_roll_no="roll_no";
        public static final String COLUMN_name="name";
        public static final String COLUMN_branch_code="branch";
        public static final String COLUMN_dob="dob";
        public static final String COLUMN_email_id="email_id";
        public static final String COLUMN_phone_num="phone_num";
        public static final String COLUMN_hall_code="hall_code";
        public static final String COLUMN_type="type";

        public static final String SQL_CREATE_TABLE= "CREATE TABLE "+TABLE_name+" ("
                    +_ID +  " INTEGER PRIMARY KEY, "
                    +COLUMN_roll_no  + " TEXT NOT NULL , "
                    +COLUMN_branch_code +" TEXT NOT NULL  , "
                    +COLUMN_dob +" TEXT NOT NULL , "
                    +COLUMN_email_id +" TEXT NOT NULL  , "
                    +COLUMN_phone_num +" TEXT  NOT NULL , "
                    +COLUMN_hall_code +" TEXT  NOT NULL , "
                    +COLUMN_name +" TEXT NOT NULL , "
                    +COLUMN_type + " TEXT NOT NULL )";
    }

    public static final class Complaint_info_Entry implements BaseColumns
    {
        public static final String TABLE_name="complaint_info";
        public static final String COLUMN_complaint_no="complaint_no";
        public static final String COLUMN_category="category";
        public static final String COLUMN_title="title";
        public static final String COLUMN_content="content";
        public static final String COLUMN_roll_no="roll_no";
        public static final String COLUMN_date="date";
        public static final String COLUMN_hall_code="hall_code";

        public static final String SQL_CREATE_TABLE=
                "CREATE TABLE "+TABLE_name+" ("
                    +_ID + " INTEGER PRIMARY KEY, "
                    +COLUMN_complaint_no + " TEXT NOT NULL, "
                    +COLUMN_category + " TEXT NOT NULL, "
                    +COLUMN_title + " TEXT NOT NULL, "
                    +COLUMN_content + " TEXT NOT NULL, "
                    +COLUMN_roll_no + " TEXT NOT NULL, "
                    +COLUMN_date + " TEXT NOT NULL, "
                    +COLUMN_hall_code + " TEXT NOT NULL)";

    }

    //TODO: similar for Notice
    public static final class Notice_info_Entry implements BaseColumns
    {
        public static final String TABLE_name="notice_info";
        public static final String COLUMN_title="title";
        public static final String COLUMN_content="content";
        public static final String COLUMN_issuing_auth="issuing_auth";
        public static final String COLUMN_date="date";
        public static final String COLUMN_hall_code="hall_code";

        public static final String SQL_CREATE_TABLE=
                "CREATE TABLE "+TABLE_name+" ("
                        +_ID + " INTEGER PRIMARY KEY, "
                        +COLUMN_title + " TEXT NOT NULL, "
                        +COLUMN_content + " TEXT NOT NULL, "
                        +COLUMN_issuing_auth+" TEXT NOT NULL, "
                        +COLUMN_date + " TEXT NOT NULL, "
                        +COLUMN_hall_code + " TEXT NOT NULL)";
    }
}
