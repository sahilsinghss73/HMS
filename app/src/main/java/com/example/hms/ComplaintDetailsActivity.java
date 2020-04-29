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


public class ComplaintDetailsActivity extends AppCompatActivity {


    private HMSOpenHelper mDBOpenhelper;

    private TextView title,description,category,date,roll;
    private Button ACK;
    private ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);

        title=(TextView)findViewById(R.id.DetCom_title);
        description=(TextView)findViewById(R.id.DetCom_description);
        category=(TextView)findViewById(R.id.DetCom_category);
        date=(TextView) findViewById(R.id.DetCom_date);
        roll=(TextView) findViewById(R.id.DetCom_lodged_by);
        ACK=(Button) findViewById(R.id.acknowledged_button);
        close=(ImageView) findViewById(R.id.DetCom_close);
        ACK.setVisibility(View.GONE);





        mDBOpenhelper=new HMSOpenHelper(this);
        DataManager.loadFromDatabase(mDBOpenhelper);
        SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();
        Cursor cursor=db.query(HMSDataBaseContract.Complaint_info_Entry.TABLE_name,null,null,null,null,null,null);
        if(cursor.moveToPosition(position))
        {

            int titlePos=cursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_title);
            int categoryPos=cursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_category);
            int contentPos=cursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_content);
            int roll_noPos=cursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_roll_no);
            int datePos=cursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_date);



            String category1=cursor.getString(categoryPos);
            String title1=cursor.getString(titlePos);
            String content1=cursor.getString(contentPos);
            String rollNo1=cursor.getString(roll_noPos);
            String date1=cursor.getString(datePos);

            title.setText(title1);
            description.setText(content1);
            category.setText(category1);
            date.setText(date1);
            roll.setText(rollNo1);




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
