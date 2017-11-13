package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapp.rxabin.kuadrilapp.adapter.UserInEventAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.Calendar;
import java.util.HashMap;

public class ReadEventActivity extends AppCompatActivity implements UserInEventAdapter.OnEditRoleSelectedListener, UserInEventAdapter.OnAddDateSelectedListener {

    private TextView title;
    private TextView location;
    private TextView description;
    private TextView members;
    private TextView date;
    private TextView time;
    private RecyclerView rv;
    private UserInEventAdapter uieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_event);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Event e = getIntent().getParcelableExtra("event");
        //HashMap<String,String> hm = (HashMap) getIntent().getSerializableExtra("map");
        //e.setUserRole(hm);
        title = (TextView) findViewById(R.id.tvTitle);
        location = (TextView) findViewById(R.id.tvLocation);
        description = (TextView) findViewById(R.id.tvDescription);
        members = (TextView) findViewById(R.id.tvMembers);
        date = (TextView) findViewById(R.id.tvDate);
        time = (TextView) findViewById(R.id.tvTime);
        rv = (RecyclerView) findViewById(R.id.rvUsersEvent);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        uieAdapter = new UserInEventAdapter(this,mAuth.getCurrentUser().getUid(), e,this,this);
        DbManager.getUsernamesFromEvent(uieAdapter,e.getMembers(), mAuth.getCurrentUser().getUid());
        rv.setAdapter(uieAdapter);


        title.setText(e.getName().toString());
        location.setText(e.getLocation().toString());
        description.setText(e.getDescription().toString());
        members.setText(Integer.toString(e.numOfMembers()));
        e.sortDateList();
        DateVote dv = e.getDateVotes().get(0);
        date.setText(dv.getDate().toString());
        time.setText(dv.getTime().toString());

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

    public void onAddDateSelected(String uid, Event e) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.format("%02d",dayOfMonth) + "/" + String.format("%02d",monthOfYear + 1) + "/" + year;
                        timePicker(date);
                        Log.d("DATE",date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timePicker(String date){
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute);
                        Log.d("TIME",time);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    @Override
    public void onEditRoleSelected(String uid, final Event e) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Log.d("Role","Role Selected");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.changeRole));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbManager.updateRole(e,mAuth.getCurrentUser().getUid(),input.getText().toString(), uieAdapter);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }




}
