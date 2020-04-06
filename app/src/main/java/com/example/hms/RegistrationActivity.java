package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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

    //Layouts
    private LinearLayout temp_layout_1;
    private LinearLayout temp_layout_2;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name_edittext=findViewById(R.id.name_input);
        email_edittext=findViewById(R.id.email_input);
        password_edittext=findViewById(R.id.password_input);
        confirmpassword_edittext=findViewById(R.id.confirm_input);
        rollnum_edittext=findViewById(R.id.rollnum_input);
        categoryRadioGroup=findViewById(R.id.radio);
        phoneNum_edittext=findViewById(R.id.phone_num_text_input);
        branchSpinner=findViewById(R.id.branch_list_input);
        hallOfResidenceSpinner=findViewById(R.id.hallofresidence_input);
//TODO dob left
        registrationButton=findViewById(R.id.register_button_registration_activity);

        mAuth=FirebaseAuth.getInstance();

        dob= (EditText) findViewById(R.id.birthday);
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
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegistrationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//        phoneNum_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                temp_layout_1=(LinearLayout)findViewById(R.id.constituency_not_verified);
//                temp_layout_2=(LinearLayout)findViewById(R.id.constituency_verified);
//
//                temp_layout_1.setVisibility(View.VISIBLE);
//                temp_layout_2.setVisibility(View.GONE);
//            }
//        });

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

//        name_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b)
//            {
//
//                String name=name_edittext.getText().toString();
////                newUser.setName(name);
//                temp_layout_1=(LinearLayout)findViewById(R.id.name_not_verified);
//                temp_layout_2=(LinearLayout)findViewById(R.id.name_verified);
//                if(name.equals("")==true)
//                {
//                    temp_layout_1.setVisibility(View.VISIBLE);
//                    temp_layout_2.setVisibility(View.GONE);
//
//                }
//                else
//                {
//                    temp_layout_1.setVisibility(View.GONE);
//                    temp_layout_2.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        password_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                String password=password_edittext.getText().toString();
////                newUser.setPassword(password);
//                temp_layout_1=(LinearLayout)findViewById(R.id.password_not_verified);
//                temp_layout_2=(LinearLayout)findViewById(R.id.password_verified);
////                if(newUser.validatePassword())
//                {
//                    temp_layout_2.setVisibility(View.VISIBLE);
//                    temp_layout_1.setVisibility(View.GONE);
//
//                }
////                else//! a valid password
//                {
//
//                    temp_layout_2.setVisibility(View.GONE);
//                    temp_layout_1.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });
//
//        confirmpassword_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                String password=confirmpassword_edittext.getText().toString();
//
//                temp_layout_1=(LinearLayout)findViewById(R.id.confirm_not_verified);
//                temp_layout_2=(LinearLayout)findViewById(R.id.confirm_verified);
////                if(newUser.validateConfirmPassword(password))
//                {
//                    temp_layout_2.setVisibility(View.VISIBLE);
//                    temp_layout_1.setVisibility(View.GONE);
//
//                }
////                else
//                {
//                    temp_layout_2.setVisibility(View.GONE);
//                    temp_layout_1.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        email_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener()
//        {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                String email=email_edittext.getText().toString();
////                newUser.setUser_name(email);
//                temp_layout_1=(LinearLayout)findViewById(R.id.email_not_verified);
//                temp_layout_2=(LinearLayout)findViewById(R.id.email_verified);
////                if(newUser.validateEmail())
//                {
//// TODO                   if(DBHelper.checkIfPresent(email))
//                    {
//                        TextView message=findViewById(R.id.email_not_verified_message);
//                        message.setText("Already Registered!");
//
//                        temp_layout_2.setVisibility(View.GONE);
//                        temp_layout_1.setVisibility(View.VISIBLE);
//
//                    }
////                    else
//                    {
//
//                        temp_layout_2.setVisibility(View.VISIBLE);
//                        temp_layout_1.setVisibility(View.GONE);
//                    }
//
//                }
////                else
//                {
//                    TextView message=findViewById(R.id.email_not_verified_message);
//                    message.setText("Invalid Email!");
//
//                    temp_layout_2.setVisibility(View.GONE);
//                    temp_layout_1.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });
//
//        rollnum_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                String rollnum=rollnum_edittext.getText().toString();
////                newUser.setVoterID(voterid);
//                temp_layout_1=(LinearLayout)findViewById(R.id.voterid_not_verified);
//                temp_layout_2=(LinearLayout)findViewById(R.id.voterid_verified);
//                if(!rollnum.equals(""))
//                {
//                    temp_layout_2.setVisibility(View.VISIBLE);
//                    temp_layout_1.setVisibility(View.GONE);
//
//                }
//                else
//                {
//                    temp_layout_2.setVisibility(View.GONE);
//                    temp_layout_1.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });



        registrationButton.setOnClickListener(new View.OnClickListener() //TODO: add new user to firebase after someone clicks on this
        {
            @Override
            public void onClick(View view)
            {
                Context context=getApplicationContext();
                CharSequence text = "Successfully registered";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                createAccount(email_edittext.getText().toString(), password_edittext.getText().toString());
                finish();
            }
        });


    }//Oncreate ends here
    public void createAccount(String email, String password)
    {
        if (!validateForm())
        {
            return;
        }
        Log.d("email",email);
        Log.d("password",password);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistrationActivity.this, "Authentication Successful.",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
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


}
