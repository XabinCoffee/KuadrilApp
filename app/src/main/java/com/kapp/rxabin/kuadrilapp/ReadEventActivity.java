package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.kapp.rxabin.kuadrilapp.adapter.DateVoteAdapter;
import com.kapp.rxabin.kuadrilapp.adapter.UserInEventAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.Calendar;

public class ReadEventActivity extends AppCompatActivity implements UserInEventAdapter.OnEditRoleSelectedListener, UserInEventAdapter.OnAddDateSelectedListener, DateVoteAdapter.OnLikeListener, DateVoteAdapter.OnDislikeListener {

    private TextView title;
    private TextView location;
    private TextView description;
    private TextView members;
    private TextView date;
    private TextView time;
    private RecyclerView rvUsers;
    private RecyclerView rvDateVotes;
    private UserInEventAdapter uieAdapter;
    private DateVoteAdapter dvAdapter;
    private FirebaseAuth mAuth;
    private AlertDialog alertDialog;
    private Context context;
    private Event e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

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

        e = getIntent().getParcelableExtra("event");
        title = (TextView) findViewById(R.id.tvTitle);
        location = (TextView) findViewById(R.id.tvLocation);
        description = (TextView) findViewById(R.id.tvDescription);
        members = (TextView) findViewById(R.id.tvMembers);
        date = (TextView) findViewById(R.id.tvDate);
        time = (TextView) findViewById(R.id.tvTime);
        rvUsers = (RecyclerView) findViewById(R.id.rvUsersEvent);
        rvDateVotes = (RecyclerView) findViewById(R.id.rvDateVotes);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(mLayoutManager);
        uieAdapter = new UserInEventAdapter(this,mAuth.getCurrentUser().getUid(), e,this,this);
        DbManager.getUsernamesFromEvent(uieAdapter,e.getMembers(), mAuth.getCurrentUser().getUid());
        rvUsers.setAdapter(uieAdapter);


        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        rvDateVotes.setLayoutManager(mLayoutManager2);
        dvAdapter = new DateVoteAdapter(this, e,this,this);
        dvAdapter.setDateVotes(e.getDateVotes());
        rvDateVotes.setAdapter(dvAdapter);


        title.setText(e.getName().toString());
        location.setText(e.getLocation().toString());
        description.setText(e.getDescription().toString());
        members.setText(Integer.toString(e.numOfMembers()));
        e.sortDateList();
        DateVote dv = e.getDateVotes().get(0);
        date.setText(dv.getDate().toString());
        time.setText(dv.getTime().toString());

        context = this;

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

    public void timePicker(final String date){
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute);
                        Log.d("TIME",time);
                        openConfirmationDialog(date,time);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void openConfirmationDialog(final String date, final String time){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(date + " " +time);
        builder.setTitle("Add datetime?");

        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Sartzen da","Iuju!");
                DbManager.addDateVote(e,mAuth.getCurrentUser().getUid(),date,time,dvAdapter);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();


    }

    @Override
    public void onEditRoleSelected(String uid, final Event e) {

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

    @Override
    public void onLikeSelected(Event e, DateVote dv) {

        dv.userLikes(dv, mAuth.getCurrentUser().getUid());

        Log.d("DATEVOTE",dv.toStringLong());
    }

    @Override
    public void onDislikeSelected(Event e, DateVote dv) {
        dv.userDislikes(dv, mAuth.getCurrentUser().getUid());
        Log.d("DATEVOTE",dv.toStringLong());
    }
}
