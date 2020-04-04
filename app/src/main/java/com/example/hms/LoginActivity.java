package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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

        //Views
        mEmailField=findViewById(R.id.input_email_id);
        mPasswordField=findViewById(R.id.input_password);
        //Buttons
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.new_user_register).setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    private void createAccount(String email, String password)
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
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
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
    private boolean validateForm()
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


    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.new_user_register)
        {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        else if (i == R.id.login_button)
        {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
//        else if (i == R.id.signOutButton)
//        {
//            signOut();
//        }
//        else if (i == R.id.verifyEmailButton)
//        {
//            sendEmailVerification();
//        }
//        else if (i == R.id.reloadButton)
//        {
//            reload();
//        }
    }

    private void signIn(String email, String password)
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
        // [END sign_in_with_email]
    }
}
