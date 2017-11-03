package com.kapp.rxabin.kuadrilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontpageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        mSignOut = (Button) findViewById(R.id.btnSignOut);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSignOut) {
            signOut();
        }
    }

    private void signOut() {
        mAuth.signOut();
        //updateUI(null);
    }
}
