package com.example.hms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hms.HMSDataBaseContract.Complaint_info_Entry;
import com.example.hms.HMSDataBaseContract.Notice_info_Entry;
import com.example.hms.HMSDataBaseContract.Student_info_Entry;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance=null;

    public List<Student> mStudents = new ArrayList<>();
    public List<Complaint> mComplaints = new ArrayList<>();
    public List<Notice> mNotices=new ArrayList<>();

    public static DataManager getInstance() {

        if(ourInstance==null){
            ourInstance=new DataManager();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(HMSOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        final String[] studentColumns = {
                Student_info_Entry.COLUMN_roll_no,
                Student_info_Entry.COLUMN_name,
                Student_info_Entry.COLUMN_branch_code,
                Student_info_Entry.COLUMN_email_id,
                Student_info_Entry.COLUMN_dob,
                Student_info_Entry.COLUMN_phone_num,
                Student_info_Entry.COLUMN_hall_code,
                Student_info_Entry.COLUMN_type
        };
        final Cursor studentCursor = db.query(Student_info_Entry.TABLE_name, studentColumns, null, null, null, null, null);

        loadStudentsFromDatabase(studentCursor);

        final String[] complaintColumns = {
                Complaint_info_Entry.COLUMN_complaint_no, Complaint_info_Entry.COLUMN_title,
                Complaint_info_Entry.COLUMN_category, Complaint_info_Entry.COLUMN_content,
                Complaint_info_Entry.COLUMN_roll_no, Complaint_info_Entry.COLUMN_date,
                Complaint_info_Entry.COLUMN_hall_code
        };
        final Cursor complaintCursor =db.query(Complaint_info_Entry.TABLE_name, complaintColumns,null,null,null,null,null);

        loadComplaintsFromDatabase(complaintCursor);

        final String[] noticeColumns = {
                Notice_info_Entry.COLUMN_title,Notice_info_Entry.COLUMN_content,
                Notice_info_Entry.COLUMN_issuing_auth,Notice_info_Entry.COLUMN_date,
                Notice_info_Entry.COLUMN_hall_code
        };
        final Cursor noticeCursor=db.query(Notice_info_Entry.TABLE_name,noticeColumns,null,null,null,null,null);
        loadNoticesFromDatabase(noticeCursor);
    }

    private static void loadNoticesFromDatabase(Cursor cursor) {
        int titlePos=cursor.getColumnIndex(Notice_info_Entry.COLUMN_title);
        int contentPos=cursor.getColumnIndex(Notice_info_Entry.COLUMN_content);
        int issuingAuthPos=cursor.getColumnIndex(Notice_info_Entry.COLUMN_issuing_auth);
        int datePos=cursor.getColumnIndex(Notice_info_Entry.COLUMN_date);
        int hallPos=cursor.getColumnIndex(Notice_info_Entry.COLUMN_hall_code);

        DataManager dm=getInstance();
        dm.mNotices.clear();
        while(cursor.moveToNext())  {

            String title=cursor.getString(titlePos);
            String content=cursor.getString(contentPos);
            String issuing_auth=cursor.getString(issuingAuthPos);
            String date=cursor.getString(datePos);
            String hall=cursor.getString(hallPos);

            Notice notice=new Notice(title,content,issuing_auth,date,hall);
            dm.mNotices.add(notice);
        }
        cursor.close();
    }

    private static void loadComplaintsFromDatabase(Cursor cursor) {
        int complaint_noPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_complaint_no);
        int titlePos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_title);
        int categoryPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_category);
        int contentPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_content);
        int roll_noPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_roll_no);
        int datePos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_date);
        int hall_codePos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_hall_code);

        DataManager dm = getInstance();
        dm.mComplaints.clear();
        while(cursor.moveToNext())  {

            String complaintNo=cursor.getString(complaint_noPos);
            String category=cursor.getString(categoryPos);
            String title=cursor.getString(titlePos);
            String content=cursor.getString(contentPos);
            String rollNo=cursor.getString(roll_noPos);
            String date=cursor.getString(datePos);
            String hallCode=cursor.getString(hall_codePos);

            Complaint complaint=new Complaint(complaintNo,category,title,content,rollNo,date,hallCode);
            dm.mComplaints.add(complaint);
        }
        cursor.close();
    }

    private static void loadStudentsFromDatabase(Cursor cursor) {
        int rollnoPos=cursor.getColumnIndex(Student_info_Entry.COLUMN_roll_no);
        int namePos=cursor.getColumnIndex(Student_info_Entry.COLUMN_name);
        int branch_codePos=cursor.getColumnIndex(Student_info_Entry.COLUMN_branch_code);
        int emailPos=cursor.getColumnIndex(Student_info_Entry.COLUMN_email_id);
        int dobPos=cursor.getColumnIndex(Student_info_Entry.COLUMN_dob);
        int phone_numPos=cursor.getColumnIndex(Student_info_Entry.COLUMN_phone_num);
        int hall_codePos=cursor.getColumnIndex(Student_info_Entry.COLUMN_hall_code);
        int typePos=cursor.getColumnIndex(Student_info_Entry.COLUMN_type);
        DataManager dm = getInstance();
        dm.mStudents.clear();
        while(cursor.moveToNext())  {

            String rollNo=cursor.getString(rollnoPos);
            String name=cursor.getString(namePos);
            String branchCode=cursor.getString(branch_codePos);
            String dateOfBirth=cursor.getString(dobPos);
            String emailID=cursor.getString(emailPos);
            String phoneNo=cursor.getString(phone_numPos);
            String hallCode=cursor.getString(hall_codePos);
            String type=cursor.getString(typePos);
            Student student=new Student(rollNo,name,branchCode,dateOfBirth,emailID,phoneNo,hallCode,type);

            dm.mStudents.add(student);
        }
        cursor.close();
    }
}
