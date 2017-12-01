package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapp.rxabin.kuadrilapp.adapter.UserDialogAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.helper.ContextWrapper;
import com.kapp.rxabin.kuadrilapp.helper.DateHelper;
import com.kapp.rxabin.kuadrilapp.helper.EventHelper;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FrontpageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, UserDialogAdapter.OnUserSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Fragment mCurrentFragment;
    private PrefFragment pf;
    private BottomNavigationView navigation;
    private CreateEventFragment cef;
    private EventsFragment ef = new EventsFragment();

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
        }

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
    protected void attachBaseContext(Context newBase) {

        //Androiden hizkuntza begiratu, gero honen arabera web zerbitzuari
        //deiak euskaraz edo erderaz egingo zaizkio.

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang = pref.getString("listLang", "eu");
        Locale newLocale = new Locale(lang);
        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_events:

                if (pf!=null) getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(pf).commit();
                ef = new EventsFragment();
                switchContent(ef);

                return true;
            case R.id.nav_new:

                if (pf!=null) getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(pf).commit();

                if (!(mCurrentFragment instanceof CreateEventFragment)) {
                    cef = new CreateEventFragment();
                    switchContent(cef);
                }

                return true;
            case R.id.nav_settings:


                if (mCurrentFragment!=null) getSupportFragmentManager()
                        .beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .remove(mCurrentFragment)
                        .commit();

                pf = new PrefFragment();
                mCurrentFragment = null;
                getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.fragment, pf).commit();

                return true;

        }
        return false;
    }

    public void switchContent(Fragment fragment) {
        mCurrentFragment = fragment;

        getSupportFragmentManager()
                .beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
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
                    TextView date = (TextView) cef.getView().findViewById(R.id.tvCreditsTitle);
                    TextView time = (TextView) cef.getView().findViewById(R.id.tvTime);

                    ArrayList<User> ul = cef.getUserAdapter().getUsers();

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
                                mAuth.getCurrentUser().getDisplayName(),
                                eventType.getText().toString(),
                                location.getText().toString(),
                                cef.getDate(),
                                cef.getTime(),
                                ul);

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
                ImageButton img = (ImageButton) cef.getView().findViewById(R.id.imageButton);
                img.setImageResource(EventHelper.getIcon(EventHelper.getType(eventType.getText().toString())));
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

                        if (DateHelper.isOver(cef.getDate()))
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.datepassed),
                                    Toast.LENGTH_LONG).show();
                        
                        else {
                            TextView date = (TextView) cef.getView().findViewById(R.id.tvCreditsTitle);
                            timePicker();
                        }
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
                        TextView date = (TextView) cef.getView().findViewById(R.id.tvCreditsTitle);
                        TextView time = (TextView) cef.getView().findViewById(R.id.tvTime);
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String lang = pref.getString("listLang", "eu");
                        if (lang.equals("es")) date.setText(DateHelper.espDate(cef.getDate()));
                        else date.setText(DateHelper.eusDate(cef.getDate()));
                        time.setText(cef.getTime());
                        cef.setDatetimeSet(true);
                        date.setError(null);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void addMember2(View v){

        UserDialogAdapter uda = new UserDialogAdapter(getApplicationContext(), this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);
        builder.setTitle(R.string.inviteUser);
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                alertDialog.cancel();
            }
        });
        alertDialog = builder.create();

        alertDialog.show();

        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.rvDialog);
        ProgressBar loading = (ProgressBar) dialogView.findViewById(R.id.pbUsers);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rv.setAdapter(uda);
        DbManager.getUsernamesExceptYourself(uda,mAuth.getCurrentUser().getUid(),cef.getUserAdapter(), rv, this, loading);

    }


    @Override
    public void onUserSelected(User userData) {
        DbManager.getUser(cef.getUserAdapter(),userData.getEmail(), cef.getRv());
        alertDialog.cancel();
    }

}
