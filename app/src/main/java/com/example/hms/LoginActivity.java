package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;

    private HMSOpenHelper mDBOpenhelper;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_login);


        mDBOpenhelper=new HMSOpenHelper(this);

        //Views
        mEmailField=findViewById(R.id.input_email_id);
        mPasswordField=findViewById(R.id.input_password);
        //Buttons
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.new_user_register).setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onDestroy() {
        mDBOpenhelper.close();
        super.onDestroy();
    }

    public void createAccount(String email, String password)
    {
        if (!validateForm())
        {
            return;
        }


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            mEmailField.setError("Required.");
            valid = false;
        }
        else if(!isValidEmailId(email))
        {
            mEmailField.setError("Invalid Email.");
            valid = false;
        }
        else
        {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            mPasswordField.setError("Required.");
            valid = false;
        }
        else
        {
            mPasswordField.setError(null);
        }

        return valid;
    }
    public void launchRegistration(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        // Do something in response to button click
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if(i==R.id.new_user_register)
        {
            startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        }
        if (i == R.id.login_button)
        {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        else if(i==R.id.forgot_password)
        {
            startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
        }
    }

    private void signIn(final String email, String password)
    {
        if (!validateForm()) {
            return;
        }


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            SQLiteDatabase db= mDBOpenhelper.getReadableDatabase();

                            String whereClause = " email_id = ? " ;
                            String[] whereArgs = new String[] {
                                    email
                            };

                            final Cursor studentCursor =db.query(HMSDataBaseContract.Student_info_Entry.TABLE_name, null,whereClause,whereArgs,null,null,null);

                            String type="";

                            if(studentCursor.moveToFirst())
                            {
                                type=studentCursor.getString(studentCursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_type));
                            }
                            Log.d("type",type);
                            Log.d("type",email);
                            if(type.equals("B")) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                            else if(type.equals("W")) {
                                startActivity(new Intent(LoginActivity.this, WardenActivity.class));
                            }
                            else {
                                startActivity(new Intent(LoginActivity.this,HCMActivity.class));
                            }
                            mEmailField.getText().clear();
                            mPasswordField.getText().clear();
                            //switch to next activity according ti the type of user
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mEmailField.getText().clear();
                            mPasswordField.getText().clear();
                        }
                    }
                });
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button to exit the application", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();

    }
}
