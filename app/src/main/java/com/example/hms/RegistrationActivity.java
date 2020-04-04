package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.MutableInt;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

import static java.time.Year.isLeap;

public class RegistrationActivity extends AppCompatActivity {

    //Buttons
    private Button registrationButton;

    //Edittexts
    private EditText name_edittext;
    private EditText email_edittext;
    private EditText password_edittext;
    private EditText confirmpassword_edittext;
    private EditText rollnum_edittext;

    //Spinners
    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Spinner daySpinner;
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
        daySpinner=findViewById(R.id.dob_d_input);
        monthSpinner=findViewById(R.id.dob_m_input);
        yearSpinner=findViewById(R.id.dob_y_input);
        registrationButton=findViewById(R.id.register_button_registration_activity);


        phoneNum_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                temp_layout_1=(LinearLayout)findViewById(R.id.constituency_not_verified);
                temp_layout_2=(LinearLayout)findViewById(R.id.constituency_verified);

                temp_layout_1.setVisibility(View.VISIBLE);
                temp_layout_2.setVisibility(View.GONE);
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

                    case R.id.radio_hcm:
//                        newUser.setSex("Female");
                        break;
                    case R.id.radio_warder:
//                        newUser.setSex("Ot");
                        break;

                }
            }
        });

        name_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b)
            {

                String name=name_edittext.getText().toString();
//                newUser.setName(name);
                temp_layout_1=(LinearLayout)findViewById(R.id.name_not_verified);
                temp_layout_2=(LinearLayout)findViewById(R.id.name_verified);
                if(name.equals("")==true)
                {
                    temp_layout_1.setVisibility(View.VISIBLE);
                    temp_layout_2.setVisibility(View.GONE);

                }
                else
                {
                    temp_layout_1.setVisibility(View.GONE);
                    temp_layout_2.setVisibility(View.VISIBLE);
                }
            }
        });

        password_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                String password=password_edittext.getText().toString();
//                newUser.setPassword(password);
                temp_layout_1=(LinearLayout)findViewById(R.id.password_not_verified);
                temp_layout_2=(LinearLayout)findViewById(R.id.password_verified);
//                if(newUser.validatePassword())
                {
                    temp_layout_2.setVisibility(View.VISIBLE);
                    temp_layout_1.setVisibility(View.GONE);

                }
//                else//! a valid password
                {

                    temp_layout_2.setVisibility(View.GONE);
                    temp_layout_1.setVisibility(View.VISIBLE);

                }

            }
        });

        confirmpassword_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                String password=confirmpassword_edittext.getText().toString();

                temp_layout_1=(LinearLayout)findViewById(R.id.confirm_not_verified);
                temp_layout_2=(LinearLayout)findViewById(R.id.confirm_verified);
//                if(newUser.validateConfirmPassword(password))
                {
                    temp_layout_2.setVisibility(View.VISIBLE);
                    temp_layout_1.setVisibility(View.GONE);

                }
//                else
                {
                    temp_layout_2.setVisibility(View.GONE);
                    temp_layout_1.setVisibility(View.VISIBLE);
                }

            }
        });

        email_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b) {

                String email=email_edittext.getText().toString();
//                newUser.setUser_name(email);
                temp_layout_1=(LinearLayout)findViewById(R.id.email_not_verified);
                temp_layout_2=(LinearLayout)findViewById(R.id.email_verified);
//                if(newUser.validateEmail())
                {
// TODO                   if(DBHelper.checkIfPresent(email))
                    {
                        TextView message=findViewById(R.id.email_not_verified_message);
                        message.setText("Already Registered!");

                        temp_layout_2.setVisibility(View.GONE);
                        temp_layout_1.setVisibility(View.VISIBLE);

                    }
                    else
                    {

                        temp_layout_2.setVisibility(View.VISIBLE);
                        temp_layout_1.setVisibility(View.GONE);
                    }

                }
                else
                {
                    TextView message=findViewById(R.id.email_not_verified_message);
                    message.setText("Invalid Email!");

                    temp_layout_2.setVisibility(View.GONE);
                    temp_layout_1.setVisibility(View.VISIBLE);

                }

            }
        });

        rollnum_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                String rollnum=rollnum_edittext.getText().toString();
//                newUser.setVoterID(voterid);
                temp_layout_1=(LinearLayout)findViewById(R.id.voterid_not_verified);
                temp_layout_2=(LinearLayout)findViewById(R.id.voterid_verified);
                if(!rollnum.equals(""))
                {
                    temp_layout_2.setVisibility(View.VISIBLE);
                    temp_layout_1.setVisibility(View.GONE);

                }
                else
                {
                    temp_layout_2.setVisibility(View.GONE);
                    temp_layout_1.setVisibility(View.VISIBLE);
                }

            }
        });

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Integer[] years=new Integer[101];
        for(int i=0; i<years.length; i++)
        {
            years[i] = new Integer(year - i);
        }

        ArrayAdapter<Integer> yearSpinnerAdapter=new ArrayAdapter<Integer>(yearSpinner.getContext() , R.layout.spinner_item, years);
        yearSpinner.setAdapter(yearSpinnerAdapter);

        String[] months={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> monthSpinnerAdapter=new ArrayAdapter<String>(monthSpinner.getContext(), R.layout.spinner_item, months);
        monthSpinner.setAdapter(monthSpinnerAdapter);

        daySpinner=(Spinner)findViewById(R.id.dob_d_input);
        final MutableInt month_pos=new MutableInt();
        final int days_in_month[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selected_year=(Integer)parent.getItemAtPosition(position);
//                newUser.setB_year(selected_year);
                int prev_value;
                if(isLeap(selected_year))
                {
                    prev_value=days_in_month[1];
                    days_in_month[1]=29;
                }
                else
                {
                    prev_value=days_in_month[1];
                    days_in_month[1]=28;
                }
                if(prev_value!=days_in_month[1] && month_pos.getValue()==1)
                {
                    int RANGE = days_in_month[month_pos.getValue()];
                    Integer dates_list[] = new Integer[RANGE];
                    for (int i = 0; i < RANGE; i++) {
                        dates_list[i] = new Integer(i + 1);
                    }
                    ArrayAdapter<Integer> daySpinnerAdapter = new ArrayAdapter<Integer>(daySpinner.getContext(), R.layout.spinner_item, dates_list);
                    daySpinner.setAdapter(daySpinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int RANGE=days_in_month[position];
                month_pos.setValue(position);
//                newUser.setB_month(position+1);
                Integer dates_list[]=new Integer[RANGE];
                for(int i=0; i<RANGE; i++)
                {
                    dates_list[i]=new Integer(i+1);
                }
                ArrayAdapter<Integer> daySpinnerAdapter=new ArrayAdapter<Integer>(daySpinner.getContext(), R.layout.spinner_item, dates_list);
                daySpinner.setAdapter(daySpinnerAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selected_day=(Integer)adapterView.getItemAtPosition(i);
//                newUser.setB_day(selected_day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                finish();
            }
        });


    }//Oncreate ends here

}
