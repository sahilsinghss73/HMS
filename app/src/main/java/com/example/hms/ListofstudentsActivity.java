package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ListofstudentsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private HMSOpenHelper mDBOpenhelper;
    private StudentsRecyclerAdapter studentsRecyclerAdapter;
    String Email,hallCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofstudents);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

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

        recyclerView=(RecyclerView)findViewById(R.id.los_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        studentsRecyclerAdapter=new StudentsRecyclerAdapter(this,null);

        recyclerView.setAdapter(studentsRecyclerAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
    }
    private void loadStudents() {
        SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();

        String whereClause = " hall_code = ? and (type = ? or type = ? ) " ;
        String[] whereArgs = new String[] {
                hallCode,
                "B",
                "H"
        };
        Log.d("hallCode",hallCode);
        final Cursor studentCursor =db.query(HMSDataBaseContract.Student_info_Entry.TABLE_name, null,whereClause,whereArgs,null,null,null);

        studentsRecyclerAdapter.changeCursor(studentCursor);
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
