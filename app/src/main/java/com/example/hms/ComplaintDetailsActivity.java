package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hms.HMSDataBaseContract.Complaint_info_Entry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ComplaintDetailsActivity extends AppCompatActivity {


    private HMSOpenHelper mDBOpenhelper;

    private TextView title,description,category,date,roll;
    private Button ACK;
    private ImageView close;
    String email,type,id;
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





        mDBOpenhelper=new HMSOpenHelper(this);
        DataManager.loadFromDatabase(mDBOpenhelper);
        SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email=user.getEmail();

        String whereClause = " email_id = ? " ;
        String[] whereArgs = new String[] {
                email
        };

        Cursor cursor=db.query(HMSDataBaseContract.Student_info_Entry.TABLE_name,null,whereClause,whereArgs,null,null,null);


        if(cursor.moveToFirst())
        {
            type=cursor.getString(cursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_type));
        }
        cursor.close();

        if(type.equals("W"))
        {
            ACK.setVisibility(View.VISIBLE);
        }
        else
        {
            ACK.setVisibility(View.GONE);
        }


        Log.d("type1",type);
        cursor=db.query(Complaint_info_Entry.TABLE_name,null,null,null,null,null,null);
        if(cursor.moveToPosition(position))
        {
            Log.d("type1","inside");
            int titlePos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_title);
            int categoryPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_category);
            int contentPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_content);
            int roll_noPos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_roll_no);
            int datePos=cursor.getColumnIndex(Complaint_info_Entry.COLUMN_date);
            int idPos=cursor.getColumnIndex(BaseColumns._ID);


            String category1=cursor.getString(categoryPos);
            String title1=cursor.getString(titlePos);
            String content1=cursor.getString(contentPos);
            String rollNo1=cursor.getString(roll_noPos);
            String date1=cursor.getString(datePos);
            id=cursor.getString(idPos);


            title.setText(title1);
            description.setText(content1);
            category.setText(category1);
            date.setText(date1);
            roll.setText(rollNo1);

        }






        cursor.close();



        final SQLiteDatabase db1= mDBOpenhelper.getWritableDatabase();

        ACK.setOnClickListener(new View.OnClickListener() {
                                   @Override
           public void onClick(View v) {
               //TODO delete complaint
               db1.delete(Complaint_info_Entry.TABLE_name, BaseColumns._ID + " = ? ", new String[]{id});
                                       Toast.makeText(ComplaintDetailsActivity.this, "Complaint Acknowledged",
                                               Toast.LENGTH_LONG).show();
               finish();
           }
       });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
