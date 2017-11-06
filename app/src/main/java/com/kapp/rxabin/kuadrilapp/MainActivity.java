package com.kapp.rxabin.kuadrilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KuadrilApp";

    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPass;
    private ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.input_email);
        mPass = (EditText) findViewById(R.id.input_password);
        mLoading = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)

        if (getIntent().getBooleanExtra("settings",false)){
            Intent i = new Intent(this, FrontpageActivity.class);
            i.putExtra("settings",true);
            startActivity(i);
            finish();
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            if (currentUser.isEmailVerified())
                openFrontpage();
        }
        mLoading.setVisibility(View.GONE);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {
            Toast.makeText(MainActivity.this, "Fill all the fields correctly.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) openFrontpage();
                            else {
                                Toast.makeText(MainActivity.this, "User is not verified, verification resent.",
                                        Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPass.setError("Required.");
            valid = false;
        } else {
            mPass.setError(null);
        }

        return valid;
    }

    public void openFrontpage(){
        Intent i = new Intent(MainActivity.this,FrontpageActivity.class);
        startActivity(i);
        finish();
    }


    public void onClick(View v) {
        int i = v.getId();

        switch(i){
            case R.id.btn_login:
                mLoading.setVisibility(View.VISIBLE);
                signIn(mEmail.getText().toString(), mPass.getText().toString());
                mLoading.setVisibility(View.GONE);
                break;

            case R.id.link_signup:
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
                break;

        }
    }
}
