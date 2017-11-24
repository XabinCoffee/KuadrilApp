package com.kapp.rxabin.kuadrilapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SIGNUP";

    private FirebaseAuth mAuth;

    private EditText mEmail;
    private EditText mPass;
    private EditText mName;
    private EditText mPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEmail = (EditText) findViewById(R.id.mEmail);
        mPass = (EditText) findViewById(R.id.mPass);
        mPass2 = (EditText) findViewById(R.id.mPass2);
        mName = (EditText) findViewById(R.id.mName);

        mAuth = FirebaseAuth.getInstance();

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getResources().getString(R.string.errorEmpty));
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPass.getText().toString();
        String pw2 = mPass2.getText().toString();

        if (TextUtils.isEmpty(password)) {
            mPass.setError(getResources().getString(R.string.errorEmpty));
            valid = false;
        } else {
            mPass.setError(null);
            if (!password.equals(pw2)){
                mPass.setError(getResources().getString(R.string.diffPasswords));
                valid = false;
            } else {
                mPass.setError(null);
            }
        }

        return valid;
    }


    private void createAccount(String email, String password, String name) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;

        }

        final String nam, ema;
        nam = name; ema = email;

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            final FirebaseUser u = user;
                            user.sendEmailVerification();
                            Toast.makeText(SignupActivity.this, getResources().getString(R.string.verificationSent),
                                    Toast.LENGTH_SHORT).show();

                            //Erabiltzailea sortu ondoren honen izena aldatu.
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mName.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                                                mAuth.signOut();

                                                finish();
                                            }
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        int i = v.getId();

        switch(i){
            case R.id.btn_signup:
                createAccount(mEmail.getText().toString(), mPass.getText().toString(), mName.getText().toString());
                break;

        }
    }

}



