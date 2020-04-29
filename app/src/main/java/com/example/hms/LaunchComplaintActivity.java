package com.example.hms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hms.HMSDataBaseContract.Complaint_info_Entry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LaunchComplaintActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText title_edittext;
    private EditText description_edittext;
    private Spinner category_spinner;
    private ArrayAdapter<String> category_spinner_adapter;
    public HMSOpenHelper mDBOpenhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_complaint);
        findViewById(R.id.register_complaint_button).setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDBOpenhelper=new HMSOpenHelper(this);
        title_edittext=(EditText)findViewById(R.id.title_input);
        description_edittext=(EditText)findViewById(R.id.description_input);

        category_spinner=(Spinner)findViewById(R.id.category_input);
        String domains[]={"Water", "Electricity", "Infrastructure", "Health", "Other"};
        category_spinner_adapter=new ArrayAdapter<String>(category_spinner.getContext(), R.layout.spinner_item, domains);
        category_spinner.setAdapter(category_spinner_adapter);

    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i==R.id.register_complaint_button) {
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
            //TODO query in the database for this email and extract rollNo & hallCode
            insertData("1",
                    category_spinner.getSelectedItem().toString(),
                    title_edittext.getText().toString(),
                    description_edittext.getText().toString(),
                    "",
                    new SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(new Date()),
                    ""
                    );//rollnum,date,hallcode

            Context context = getApplicationContext();
            CharSequence text = "Complaint Lodged!";
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

    public void insertData(String complaintNo,String category,String title,String content,String rollNo,String date,String hallCode)
    {
        SQLiteDatabase db= mDBOpenhelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Complaint_info_Entry.COLUMN_complaint_no,complaintNo);
        contentValues.put(Complaint_info_Entry.COLUMN_category,category);
        contentValues.put(Complaint_info_Entry.COLUMN_title,title);
        contentValues.put(Complaint_info_Entry.COLUMN_content,content);
        contentValues.put(Complaint_info_Entry.COLUMN_roll_no,rollNo);
        contentValues.put(Complaint_info_Entry.COLUMN_date,date);
        contentValues.put(Complaint_info_Entry.COLUMN_hall_code,hallCode);

        long id=db.insert(HMSDataBaseContract.Complaint_info_Entry.TABLE_name,null,contentValues);

    }

}
