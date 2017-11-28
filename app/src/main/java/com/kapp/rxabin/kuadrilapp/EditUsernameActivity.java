package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.helper.ContextWrapper;

import java.util.Locale;

public class EditUsernameActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_edit_username);
        username = (EditText) findViewById(R.id.mName);
        mAuth = FirebaseAuth.getInstance();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateName(View v){
        final String name = username.getText().toString();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("UserUpdate", "User profile updated.");
                    DbManager.updateUser(currentUser.getUid(),name);
                }
            }
        });

        finish();

    }


}
