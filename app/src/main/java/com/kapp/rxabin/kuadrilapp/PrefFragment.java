package com.kapp.rxabin.kuadrilapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * Created by xabinrodriguez on 25/10/17.
 */

public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SwitchPreference swPref;
    private ListPreference lPref;
    private BottomNavigationView t;
    private BottomNavigationItemView nav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource

        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        lPref = (ListPreference) findPreference("listLang");

        Preference usernamepref = findPreference("editusername");
        usernamepref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getActivity(), EditUsernameActivity.class);
                startActivity(intent);
                return true;
            }
        });


        Preference logoutpref = findPreference("logout");
        logoutpref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                return true;
            }
        });

        return inflater.inflate(R.layout.pref_layout, group, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if(key.equals("listLang")){
            if(sharedPreferences.getString("listLang","").equals("es")){

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("settings", true);
                startActivity(intent);
                getActivity().finish();

            }
            else{

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("settings", true);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }


}
