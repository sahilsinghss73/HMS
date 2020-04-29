package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.hms.HMSDataBaseContract.Complaint_info_Entry;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private HMSOpenHelper mDBOpenhelper;
    private ComplaintsRecyclerAdapter complaintsRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDBOpenhelper=new HMSOpenHelper(this);

        DataManager.loadFromDatabase(mDBOpenhelper);


        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        complaintsRecyclerAdapter=new ComplaintsRecyclerAdapter(this,null);

        recyclerView.setAdapter(complaintsRecyclerAdapter);


    }
    @Override
    protected void onResume() {
        super.onResume();
        loadComplaints();


    }

    private void loadComplaints() {
        SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();
        final String[] complaintColumns = {
                Complaint_info_Entry.COLUMN_complaint_no, Complaint_info_Entry.COLUMN_title,
                Complaint_info_Entry.COLUMN_category, Complaint_info_Entry.COLUMN_content,
                Complaint_info_Entry.COLUMN_roll_no, Complaint_info_Entry.COLUMN_date,
                Complaint_info_Entry.COLUMN_hall_code
        };
        final Cursor complaintCursor =db.query(Complaint_info_Entry.TABLE_name, complaintColumns,null,null,null,null,null);

        complaintsRecyclerAdapter.changeCursor(complaintCursor);
    }

    @Override
    protected void onDestroy() {
        mDBOpenhelper.close();
        super.onDestroy();
    }
}
