package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NoticeActivity extends AppCompatActivity {

    private ComplaintsRecyclerAdapter complaintsRecyclerAdapter;
    private HMSOpenHelper mDBOpenhelper;
    String Email,hallCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDBOpenhelper=new HMSOpenHelper(this);
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



        final RecyclerView recyclerNotes=(RecyclerView) findViewById(R.id.list_notes);
        recyclerNotes.setHasFixedSize(true);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));

        complaintsRecyclerAdapter=new ComplaintsRecyclerAdapter(this,null);

        recyclerNotes.setAdapter(complaintsRecyclerAdapter);
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

        final Cursor complaintCursor =db.query(HMSDataBaseContract.Notice_info_Entry.TABLE_name, null,whereClause,whereArgs,null,null,null);

        complaintsRecyclerAdapter.changeCursor(complaintCursor);
    }
    @Override
    protected void onDestroy() {
        mDBOpenhelper.close();
        super.onDestroy();
    }
}
