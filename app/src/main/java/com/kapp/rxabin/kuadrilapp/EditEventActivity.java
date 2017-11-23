package com.kapp.rxabin.kuadrilapp;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.adapter.UserAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;

public class EditEventActivity extends AppCompatActivity implements UserAdapter.OnUserRemovalSelectedListener{

    private EditText title;
    private EditText desc;
    private EditText location;
    private TextView eventType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.etTitle);
        desc = (EditText) findViewById(R.id.etDesc);
        location = (EditText) findViewById(R.id.etLocation);
        eventType = (TextView) findViewById(R.id.tvEventType);


        Event e = getIntent().getParcelableExtra("event");
        title.setText(e.getName());
        desc.setText(e.getDescription());
        location.setText(e.getLocation());
        eventType.setText(e.getIcon());

        ArrayList<String> ul = e.getMembers();
        UserAdapter uAdapter = new UserAdapter(this, this);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserRemovalSelected(User userData) {
        //TODO
    }
}
