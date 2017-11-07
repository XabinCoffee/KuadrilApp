package com.kapp.rxabin.kuadrilapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.User;

public class FrontpageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Fragment mCurrentFragment;
    private PrefFragment pf;
    private BottomNavigationView navigation;
    private CreateEventFragment cef;
    private EventsFragment ef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        mAuth = FirebaseAuth.getInstance();


        if (getIntent().getBooleanExtra("gotosettings",false)){
            navigation.setSelectedItemId(R.id.nav_settings);
        } else {
            navigation.setSelectedItemId(R.id.nav_events);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_events:

                if (pf!=null) getFragmentManager().beginTransaction().hide(pf).commit();
                ef = new EventsFragment();
                switchContent(ef);

                return true;
            case R.id.nav_new:

                if (pf!=null) getFragmentManager().beginTransaction().hide(pf).commit();
                cef = new CreateEventFragment();
                switchContent(cef);

                return true;
            case R.id.nav_settings:

                if (mCurrentFragment!=null) getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
                pf = new PrefFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, pf).commit();

                return true;

        }
        return false;
    }

    public void switchContent(Fragment fragment) {
        mCurrentFragment = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        currentUser = mAuth.getCurrentUser();

    }


    public void onClick(View v) {
        int i = v.getId();

        switch(i){
            case R.id.btn_create_event:
                if (cef!=null){
                    EditText title =(EditText) cef.getView().findViewById(R.id.etTitle);
                    EditText desc = (EditText) cef.getView().findViewById(R.id.etDesc);
                    EditText location = (EditText) cef.getView().findViewById(R.id.etLocation);
                    Spinner spinner = (Spinner) cef.getView().findViewById(R.id.spinner);

                    DbManager.createEvent(title.getText().toString(),
                            desc.getText().toString(),
                            mAuth.getCurrentUser().getUid(),
                            spinner.getSelectedItem().toString(),
                            location.getText().toString());

                    Toast.makeText(FrontpageActivity.this, "Event created.",
                            Toast.LENGTH_SHORT).show();

                    navigation.setSelectedItemId(R.id.nav_events);
                }

                break;
        }
    }
}
