package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.MutableInt;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hms.HMSDataBaseContract.Student_info_Entry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.time.Year.isLeap;

public class RegistrationActivity extends AppCompatActivity
{

    //Buttons
    private Button registrationButton;

    //Edittexts
    private EditText name_edittext;
    private EditText email_edittext;
    private EditText password_edittext;
    private EditText confirmpassword_edittext;
    private EditText rollnum_edittext;
    private EditText dob;
    //Spinners

    private Spinner hallOfResidenceSpinner;
    private Spinner branchSpinner;
    private EditText phoneNum_edittext;
    private RadioGroup categoryRadioGroup;

    private HMSOpenHelper mDBOpenhelper;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDBOpenhelper=new HMSOpenHelper(this);
        name_edittext=findViewById(R.id.name_input);
        email_edittext=findViewById(R.id.email_input);
        password_edittext=findViewById(R.id.password_input);
        confirmpassword_edittext=findViewById(R.id.confirm_input);
        rollnum_edittext=findViewById(R.id.rollnum_input);
        categoryRadioGroup=findViewById(R.id.radio);
        phoneNum_edittext=findViewById(R.id.phone_num_text_input);
        branchSpinner=findViewById(R.id.branch_list_input);
        hallOfResidenceSpinner=findViewById(R.id.hallofresidence_input);
        dob= (EditText) findViewById(R.id.birthday);
        registrationButton=findViewById(R.id.register_button_registration_activity);

        mAuth=FirebaseAuth.getInstance();

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dob.setText(sdf.format(myCalendar.getTime()));
            }
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };




        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegistrationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        categoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {

                switch(radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.radio_boarder:
//                        newUser.setSex("Male");
                        break;
                    case R.id.radio_warden:
//                        newUser.setSex("Ot");
                        break;

                }
            }
        });


        registrationButton.setOnClickListener(new View.OnClickListener() //add new user to firebase after someone clicks on this
        {
            @Override
            public void onClick(View view)
            {
                Log.d("register","button clicked");
                if(!createAccount(email_edittext.getText().toString(), password_edittext.getText().toString()))
                    return;
                Log.d("123","2341");
                Context context=getApplicationContext();
                CharSequence text = "Successfully registered";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
            }
        });



        String dept[]={"CSE", "ECE", "EE", "ME","CH","IM","MA"};
        ArrayAdapter<String> dept_spinner_adapter=new ArrayAdapter<String>(branchSpinner.getContext(), R.layout.spinner_item, dept);
        branchSpinner.setAdapter(dept_spinner_adapter);

        String Halls[]={"AZD", "VS", "LBS", "RP","RK"};
        ArrayAdapter<String> hall_spinner_adapter=new ArrayAdapter<String>(hallOfResidenceSpinner.getContext(), R.layout.spinner_item, Halls);
        hallOfResidenceSpinner.setAdapter(hall_spinner_adapter);



    }//Oncreate ends here

    public boolean createAccount(String email, String password)
    {
        if (!validateForm())
        {
            return false;
        }


        String name=name_edittext.getText().toString();
        String roll=rollnum_edittext.getText().toString();
        String branch=branchSpinner.getSelectedItem().toString();
        String hall=hallOfResidenceSpinner.getSelectedItem().toString();
        String phone=phoneNum_edittext.getText().toString();
        String Birthday=dob.getText().toString();


        Log.d("name",name);
        Log.d("email",email);
        Log.d("roll",roll);
        Log.d("branch",branch);
        Log.d("hall",hall);
        Log.d("phone",phone);
        Log.d("dob",Birthday);


        insertData(roll,name,branch,Birthday,email,phone,hall);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            Toast.makeText(RegistrationActivity.this, "Authentication Successful.",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        }
                    }
                });
        return true;
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public boolean validateForm()
    {
        boolean valid = true;

        String email = email_edittext.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            email_edittext.setError("Required.");
            valid = false;
        }
        else if(!isValidEmailId(email))
        {
            email_edittext.setError("Invalid Email.");
            valid = false;
        }
        else
        {
            email_edittext.setError(null);
        }

        String password = password_edittext.getText().toString();
        String confirm_password=confirmpassword_edittext.getText().toString();
        if(!password.equals(confirm_password))
        {
            password_edittext.setError("Passwords don't match");
            valid=false;
        }
        else
            password_edittext.setError(null);


        if (TextUtils.isEmpty(password))
        {
            password_edittext.setError("Required.");
            valid = false;
        }
        else
        {
            password_edittext.setError(null);
        }

        return valid;
    }
    public void insertData(String roll,String name,String branch,String date_of_birth,String email,String phone_num,String hall)
    {
        SQLiteDatabase db= mDBOpenhelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Student_info_Entry.COLUMN_roll_no,roll);
        contentValues.put(Student_info_Entry.COLUMN_name,name);
        contentValues.put(Student_info_Entry.COLUMN_branch_code,branch);
        contentValues.put(Student_info_Entry.COLUMN_dob,date_of_birth);
        contentValues.put(Student_info_Entry.COLUMN_email_id,email);
        contentValues.put(Student_info_Entry.COLUMN_phone_num,phone_num);
        contentValues.put(Student_info_Entry.COLUMN_hall_code,hall);

        long id=db.insert(Student_info_Entry.TABLE_name,null,contentValues);

    }

}
