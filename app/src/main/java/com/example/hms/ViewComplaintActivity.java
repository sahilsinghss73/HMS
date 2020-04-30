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
import android.view.View;

import com.example.hms.HMSDataBaseContract.Complaint_info_Entry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private HMSOpenHelper mDBOpenhelper;
    private ComplaintsRecyclerAdapter complaintsRecyclerAdapter;
    String Email,hallCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        mDBOpenhelper=new HMSOpenHelper(this);


        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        DataManager.loadFromDatabase(mDBOpenhelper);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Email=user.getEmail();
        String whereClause = " email_id = ? " ;
        String[] whereArgs = new String[] {
                Email
        };
        SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();
        Cursor cursor=db.query(HMSDataBaseContract.Student_info_Entry.TABLE_name,null,whereClause,whereArgs,null,null,null);
        if(cursor.moveToFirst()){

            hallCode = cursor.getString(cursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_hall_code));
        }

        cursor.close();
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

        String whereClause = " hall_code = ? " ;
        String[] whereArgs = new String[] {
                hallCode
        };
        Log.d("hallCode",hallCode);
        final Cursor complaintCursor =db.query(Complaint_info_Entry.TABLE_name, null,whereClause,whereArgs,null,null,null);

        complaintsRecyclerAdapter.changeCursor(complaintCursor);
    }

    @Override
    protected void onDestroy() {
        mDBOpenhelper.close();
        super.onDestroy();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
