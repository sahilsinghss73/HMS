package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NoticeDetailsActivity extends AppCompatActivity {

    private HMSOpenHelper mDBOpenhelper;

    private TextView title,description,date,name;
    private ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);

        title=(TextView)findViewById(R.id.DetNot_title);
        description=(TextView)findViewById(R.id.DetNot_description);
        date=(TextView)findViewById(R.id.DetNot_date);
        name=(TextView)findViewById(R.id.DetNot_issued_by);
        close=(ImageView)findViewById(R.id.DetNot_close);

        mDBOpenhelper=new HMSOpenHelper(this);
        DataManager.loadFromDatabase(mDBOpenhelper);
        SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();



        Cursor cursor=db.query(HMSDataBaseContract.Notice_info_Entry.TABLE_name,null,null,null,null,null,null);

        if(cursor.moveToPosition(position))
        {
            int titlePos=cursor.getColumnIndex(HMSDataBaseContract.Notice_info_Entry.COLUMN_title);
            int contentPos=cursor.getColumnIndex(HMSDataBaseContract.Notice_info_Entry.COLUMN_content);
            int issuePos=cursor.getColumnIndex(HMSDataBaseContract.Notice_info_Entry.COLUMN_issuing_auth);
            int datePos=cursor.getColumnIndex(HMSDataBaseContract.Notice_info_Entry.COLUMN_date);

            String title1=cursor.getString(titlePos);
            String content1=cursor.getString(contentPos);
            String issue1=cursor.getString(issuePos);
            String date1=cursor.getString(datePos);

            title.setText(title1);
            description.setText(content1);
            name.setText(issue1);
            date.setText(date1);

        }
        cursor.close();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
