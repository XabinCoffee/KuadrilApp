package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.kapp.rxabin.kuadrilapp.adapter.UserAdapter;
import com.kapp.rxabin.kuadrilapp.adapter.UserDialogAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity implements UserAdapter.OnUserRemovalSelectedListener, UserDialogAdapter.OnUserSelectedListener{

    private EditText title;
    private EditText desc;
    private EditText location;
    private TextView eventType;
    private RecyclerView rv;
    private LinearLayoutManager mLayoutManager;
    private UserAdapter uAdapter;
    private AlertDialog alertDialog;


    private FirebaseAuth mAuth;
    private Event e;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        mAuth = FirebaseAuth.getInstance();

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
        rv = (RecyclerView) findViewById(R.id.rvUser);


        e = getIntent().getParcelableExtra("event");
        title.setText(e.getName());
        desc.setText(e.getDescription());
        location.setText(e.getLocation());
        eventType.setText(e.getIcon());

        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        uAdapter = new UserAdapter(this, this);
        DbManager.getEditEventUsers(e,uAdapter);
        rv.setAdapter(uAdapter);

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
        uAdapter.getUsers().remove(userData);
        uAdapter.notifyDataSetChanged();
    }

    public void onClick(View v){
        int i = v.getId();

        switch(i){
            case R.id.btn_create_event:

                    ArrayList<User> ul = uAdapter.getUsers();

                    boolean good = true;

                    if (TextUtils.isEmpty(title.getText())) {
                        title.setError(getResources().getString(R.string.errorEmpty));
                        good = false;
                    }  else {
                        title.setError(null);
                    }

                    if (good){
                        DbManager.editEvent(e,title.getText().toString(),desc.getText().toString(),"Comida",location.getText().toString(),ul);

                        Toast.makeText(EditEventActivity.this, getResources().getString(R.string.eventCreated),
                                Toast.LENGTH_SHORT).show();

                        finish();
                        ReadEventActivity.readEvent.finish();

                    }

                break;
        }
    }


    public void addMember2(View v){

        UserDialogAdapter uda = new UserDialogAdapter(this, this);

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
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rv.setAdapter(uda);
        DbManager.getUsernamesExceptYourself(uda,mAuth.getCurrentUser().getUid(),uAdapter);

    }

    @Override
    public void onUserSelected(User userData) {
        DbManager.getUser(uAdapter,userData.getEmail());
        alertDialog.cancel();
    }
}
