package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LaunchNoticeActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText title_edittext;
    private EditText description_edittext;
    public HMSOpenHelper mDBOpenhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_notice);

        findViewById(R.id.notice_register_complaint_button).setOnClickListener(this);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        mDBOpenhelper=new HMSOpenHelper(this);
        title_edittext=(EditText)findViewById(R.id.notice_title_input);
        description_edittext=(EditText)findViewById(R.id.notice_description_input);

    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i==R.id.notice_register_complaint_button) {
            String Title=title_edittext.getText().toString();
            String content=description_edittext.getText().toString();

            if(Title=="")
            {
                title_edittext.setError("Title required");
                return;
            }
            if(content=="")
            {
                description_edittext.setError("Description required");
                return;
            }
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String Email=user.getEmail();


            String whereClause = " email_id = ? " ;
            String[] whereArgs = new String[] {
                    Email
            };
            DataManager.loadFromDatabase(mDBOpenhelper);
            SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();
            Cursor cursor=db.query(HMSDataBaseContract.Student_info_Entry.TABLE_name,null,whereClause,whereArgs,null,null,null);
            String hallCode="";
            String name="";
            if(cursor.moveToFirst()){
                hallCode = cursor.getString(cursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_hall_code));
                name = cursor.getString(cursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_name));
            }
            cursor.close();

            insertData(
                    title_edittext.getText().toString(),
                    description_edittext.getText().toString(),
                    name,
                    new SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(new Date()),
                    hallCode
            );

            Context context = getApplicationContext();
            CharSequence text = "New Notice Published!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            finish();
//            startActivity(new Intent(LaunchComplaintActivity.this,MainActivity.class));
        }
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }

    public void insertData(String title,String content,String issuing,String date,String hallCode)
    {
        SQLiteDatabase db= mDBOpenhelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(HMSDataBaseContract.Notice_info_Entry.COLUMN_title,title);
        contentValues.put(HMSDataBaseContract.Notice_info_Entry.COLUMN_content,content);
        contentValues.put(HMSDataBaseContract.Notice_info_Entry.COLUMN_issuing_auth,issuing);
        contentValues.put(HMSDataBaseContract.Notice_info_Entry.COLUMN_date,date);
        contentValues.put(HMSDataBaseContract.Notice_info_Entry.COLUMN_hall_code,hallCode);

        db.insert(HMSDataBaseContract.Notice_info_Entry.TABLE_name,null,contentValues);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
