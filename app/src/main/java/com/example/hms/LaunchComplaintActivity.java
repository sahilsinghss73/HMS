package com.example.hms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LaunchComplaintActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText title_edittext;
    private EditText description_edittext;
    private Spinner category_spinner;
    private ArrayAdapter<String> category_spinner_adapter;
//    private Complaint newComplaint;

    private LinearLayout message;
    private AlertDialog.Builder alert_builder;

//    Databasehelper DBHelper;
    Bundle extras;
//    User THIS_USER_OBJECT;

    private boolean title_ok;
    private boolean description_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_complaint);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},00);
        findViewById(R.id.register_complaint_button).setOnClickListener(this);
//        DBHelper = new Databasehelper(this);
        extras=getIntent().getExtras();
//        THIS_USER_OBJECT=(User)extras.getSerializable("THIS_USER_OBJECT");
        message=(LinearLayout)findViewById(R.id.message);
        title_edittext=(EditText)findViewById(R.id.title_input);
        description_edittext=(EditText)findViewById(R.id.description_input);

        category_spinner=(Spinner)findViewById(R.id.category_input);
        String domains[]={"Water", "Electricity", "Infrastructure", "Health"};
        category_spinner_adapter=new ArrayAdapter<String>(category_spinner.getContext(), R.layout.spinner_item, domains);
        category_spinner.setAdapter(category_spinner_adapter);


//        newComplaint=new Complaint();
//        newComplaint.setUser_name_launcher(THIS_USER_OBJECT.getUser_name());
//        newComplaint.setConstituency(THIS_USER_OBJECT.getConstituency());


        title_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b)
                {
//                    newComplaint.setTitle(title_edittext.getText().toString());

                    if(title_edittext.getText().toString().equals("")) {

                        title_ok=false;
                    }
                    else {
                        title_ok=true;
                    }
                }

            }
        });
        description_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b)
                {
//                    newComplaint.setDescription(description_edittext.getText().toString());

                    if(description_edittext.getText().toString().equals("")) {

                        description_ok=false;
                    }
                    else {
                        description_ok=true;
                    }
                }

            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String domain=adapterView.getItemAtPosition(i).toString();
//                newComplaint.setDomain(domain);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }


//    protected void onStop()
//    {
//        super.onStop();
//        //Log.e("HELLO", "HELLO");
//        Intent returnIntent=getIntent();
//        returnIntent.putExtra("THIS_USER_OBJECT", THIS_USER_OBJECT);
//        setResult(RESULT_OK, returnIntent);
//        finish();
//
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//
//                Intent returnIntent=getIntent();
//                returnIntent.putExtra("THIS_USER_OBJECT", THIS_USER_OBJECT);
//                setResult(RESULT_OK, returnIntent);
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i==R.id.register_complaint_button) {
            Context context = getApplicationContext();
            CharSequence text = "Complaint Lodged!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            finish();
            startActivity(new Intent(LaunchComplaintActivity.this,MainActivity.class));
        }
    }
    @Override
    public void onBackPressed()
    {

        finish();
        startActivity(new Intent(LaunchComplaintActivity.this,MainActivity.class));
    }


}
