package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.helper.EventHelper;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.Calendar;

public class FrontpageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Fragment mCurrentFragment;
    private PrefFragment pf;
    private BottomNavigationView navigation;
    private CreateEventFragment cef;
    private EventsFragment ef = new EventsFragment();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventAdapter eAdapter;

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


                if (mCurrentFragment!=null) getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mCurrentFragment)
                        .commit();

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
                    TextView eventType = (TextView) cef.getView().findViewById(R.id.tvEventType);
                    TextView date = (TextView) cef.getView().findViewById(R.id.tvDate);
                    TextView time = (TextView) cef.getView().findViewById(R.id.tvTime);


                    boolean good = true;

                    if (!cef.isDatetimeSet()){
                        date.setError(getResources().getString(R.string.errorEmpty));
                        good = false;
                    } else {
                        date.setError(null);
                    }

                    if (TextUtils.isEmpty(title.getText())) {
                        title.setError(getResources().getString(R.string.errorEmpty));
                        good = false;
                    }  else {
                        title.setError(null);
                    }

                    if (good){
                        DbManager.createEvent(title.getText().toString(),
                                desc.getText().toString(),
                                mAuth.getCurrentUser().getUid(),
                                eventType.getText().toString(),
                                location.getText().toString(),
                                date.getText().toString(),
                                time.getText().toString());

                        Toast.makeText(FrontpageActivity.this, getResources().getString(R.string.eventCreated),
                                Toast.LENGTH_SHORT).show();

                        navigation.setSelectedItemId(R.id.nav_events);
                    }
                }
                break;
        }
    }


    public void typeSelect(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.typeEvent));
        builder.setItems(getResources().getStringArray(R.array.eventType), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView eventType = (TextView) cef.getView().findViewById(R.id.tvEventType);
                String[] types = getResources().getStringArray(R.array.eventType);
                eventType.setText(types[which]);
                Log.d("EventType", eventType.getText().toString());
                ImageView img = (ImageView) cef.getView().findViewById(R.id.imageView);
                img.setImageDrawable(getResources().getDrawable(EventHelper.getIcon(EventHelper.getType(eventType.getText().toString()))));
            }
        });
        builder.show();
    }

    public void setDate(View v){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cef.setDate(String.format("%02d",dayOfMonth) + "/" + String.format("%02d",monthOfYear + 1) + "/" + year);
                        Log.d("DATE",cef.getDate());
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timePicker(){
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cef.setTime(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
                        Log.d("DATE",cef.getTime());
                        TextView date = (TextView) cef.getView().findViewById(R.id.tvDate);
                        TextView time = (TextView) cef.getView().findViewById(R.id.tvTime);
                        date.setText(cef.getDate());
                        time.setText(cef.getTime());
                        cef.setDatetimeSet(true);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

}
