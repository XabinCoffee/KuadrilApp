package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.helper.ContextWrapper;
import com.kapp.rxabin.kuadrilapp.jokes.dap;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KuadrilApp";

    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPass;
    private ProgressBar mLoading;
    private int wronguser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        wronguser = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.input_email);
        mPass = (EditText) findViewById(R.id.input_password);
        mLoading = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean("firsttime", false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("firsttime", Boolean.TRUE);
            edit.commit();
            Intent first_i = new Intent(this, AboutKAppActivity.class);
            startActivity(first_i);
            finish();
        }

        if (getIntent().getBooleanExtra("settings",false)){
            Intent i = new Intent(MainActivity.this, FrontpageActivity.class);
            i.putExtra("gotosettings",true);
            startActivity(i);
            finish();
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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            if (currentUser.isEmailVerified())
                openFrontpage();
        }
        mLoading.setVisibility(View.GONE);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {
            //Toast.makeText(MainActivity.this, "Fill all the fields correctly.",Toast.LENGTH_SHORT).show();
            return;
        }


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()){
                                DbManager.storeUser(user.getUid(),user.getDisplayName(), user.getEmail());
                                openFrontpage();
                            }
                            else {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.errorUnverified),
                                        Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.errorCredentials),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getResources().getString(R.string.errorEmpty));
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPass.setError(getResources().getString(R.string.errorEmpty));
            valid = false;
        } else {
            mPass.setError(null);
        }

        return valid;
    }

    public void openFrontpage(){
        Intent i = new Intent(MainActivity.this,FrontpageActivity.class);
        startActivity(i);
        finish();
    }




    public void onClick(View v) {
        int i = v.getId();

        switch(i){
            case R.id.btn_login:
                mLoading.setVisibility(View.VISIBLE);
                signIn(mEmail.getText().toString(), mPass.getText().toString());
                mLoading.setVisibility(View.GONE);
                break;

            case R.id.link_signup:
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
                break;

            case R.id.imgView:

                if ((wronguser%25) == 24){
                    Intent intdap = new Intent(MainActivity.this,dap.class);
                    startActivity(intdap);
                    wronguser++;
                } else wronguser++;

                break;

            case R.id.btnwhat:

                Intent aboutintent = new Intent(MainActivity.this, AboutKAppActivity.class);
                startActivity(aboutintent);
                finish();
        }
    }
}
