package com.appmaester.apptest;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appmaester.apptest.Helpers.CustomTypefaceSpan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Nik on 4/23/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Drawable d=getResources().getDrawable(R.drawable.actionbarbg2);
        setActionBarTitle("AppTest");
        getSupportActionBar().setBackgroundDrawable(d);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_confirm_password);


        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }

    private void setActionBarTitle(String title){
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/GillSansUltraBold.ttf");
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);
        ssb.setSpan(new CustomTypefaceSpan("", font), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(ssb);
    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            Toast.makeText(this, "Password too short or does not match!", Toast.LENGTH_SHORT).show();
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "This field is required!", Toast.LENGTH_SHORT).show();
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, "This email address is invalid!", Toast.LENGTH_SHORT).show();
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            createFirUser();
        }
    }

    // Email validity checking logic here.
    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isPasswordValid(String password) {
        //Logic to check for a valid password (minimum 6 characters) & if password matches or not
        String confirmPass = mConfirmPasswordView.getText().toString();
        return confirmPass.equals(password) && password.length()>=6;
    }

    // Create a Firebase user
    private void createFirUser(){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("TestApp", "createUser onComplete: "+task.isSuccessful());
                if (!task.isSuccessful()){
                    Log.d("TestApp", "createUser Failed");
                    showErrDialog("Registration Failed!");
                } else {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }


    // Alert dialog to show in case registration failed
    private void showErrDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops!!!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .show();
    }

}

