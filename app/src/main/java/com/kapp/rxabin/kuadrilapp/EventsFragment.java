package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.kapp.rxabin.kuadrilapp.adapter.EventAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private EventAdapter eAdapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private static ProgressBar mLoading;
    private static TextView mEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
        mEmpty = (TextView) view.findViewById(R.id.empty);

        mAuth = FirebaseAuth.getInstance();

        fillRecyclerView();

        return view;
    }

    public void fillRecyclerView(){

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        EventAdapter eAdapter = new EventAdapter(getContext());
        DbManager.getUserEvents(eAdapter,mAuth.getCurrentUser().getUid());
        recyclerView.setAdapter(eAdapter);

    }

    public static void updateUI_events(ArrayList<Event> el){

        if (el.size()==0){
            mEmpty.setVisibility(View.VISIBLE);
        }else{
            mEmpty.setVisibility(View.GONE);
        }
        mLoading.setVisibility(View.GONE);
    }


}
