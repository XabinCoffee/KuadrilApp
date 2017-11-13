package com.kapp.rxabin.kuadrilapp;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kapp.rxabin.kuadrilapp.adapter.UserInEventAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;

public class ReadEventActivity extends AppCompatActivity {

    private TextView title;
    private TextView location;
    private TextView description;
    private TextView members;
    private TextView date;
    private TextView time;
    private RecyclerView rv;

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
        title = (TextView) findViewById(R.id.tvTitle);
        location = (TextView) findViewById(R.id.tvLocation);
        description = (TextView) findViewById(R.id.tvDescription);
        members = (TextView) findViewById(R.id.tvMembers);
        date = (TextView) findViewById(R.id.tvDate);
        time = (TextView) findViewById(R.id.tvTime);
        rv = (RecyclerView) findViewById(R.id.rvUsersEvent);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        UserInEventAdapter uieAdapter = new UserInEventAdapter(this,mAuth.getCurrentUser().getUid(), e.getUserRole());
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

}
