package com.kapp.rxabin.kuadrilapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;

public class ReadEventActivity extends AppCompatActivity {

    private TextView title;
    private TextView location;
    private TextView description;
    private TextView members;
    private TextView date;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
