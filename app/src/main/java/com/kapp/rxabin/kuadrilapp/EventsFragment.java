package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.kapp.rxabin.kuadrilapp.adapter.EventAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.helper.DateHelper;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;


public class EventsFragment extends Fragment implements EventAdapter.OnEventLongClickListener, EventAdapter.OnEventSelectedListener{

    private RecyclerView.LayoutManager mLayoutManager;


    private EventAdapter eAdapter;
    private String lang;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private static ProgressBar mLoading;
    private static CardView mEmpty;
    private AlertDialog alertDialog;
    private boolean hideOld;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
        mEmpty = (CardView) view.findViewById(R.id.empty);

        mAuth = FirebaseAuth.getInstance();



        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        hideOld = pref.getBoolean("switch_hideold",true);

        if (mAuth.getCurrentUser()!=null) {
            fillRecyclerView();
        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        hideOld = pref.getBoolean("switch_hideold",true);
        fillRecyclerView();
    }


    public void fillRecyclerView(){

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        eAdapter = new EventAdapter(getContext(),this,this);
        if (hideOld) DbManager.getUserEvents(eAdapter,mAuth.getCurrentUser().getUid(), recyclerView, getContext());
        else DbManager.getUserAllEvents(eAdapter,mAuth.getCurrentUser().getUid(), recyclerView, getContext());
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


    @Override
    public void onEventLongClick(final Event eventData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final String currentUid = mAuth.getCurrentUser().getUid();
        if (eventData.getOwner().equals(currentUid)) {
            builder.setMessage(getResources().getString(R.string.confirmDeleteEvent));
            builder.setTitle(getResources().getString(R.string.confirmDeleteEventTitle));
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    DbManager.deleteEvent(eventData.getId());
                    eAdapter.removeEvent(eventData);
                    if (eAdapter.getItemCount()==0){
                        mEmpty.setVisibility(View.VISIBLE);
                    }else{
                        mEmpty.setVisibility(View.GONE);
                    }
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.cancel();
                }
            });
            alertDialog = builder.create();

            alertDialog.show();
        } else {
            builder.setMessage(getResources().getString(R.string.leaveEvent));
            builder.setTitle(getResources().getString(R.string.leaveEventTitle));
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    DbManager.removeMember(eventData.getId(),currentUid);
                    eAdapter.removeEvent(eventData);

                    if (eAdapter.getItemCount()==0){
                        mEmpty.setVisibility(View.VISIBLE);
                    }else{
                        mEmpty.setVisibility(View.GONE);
                    }

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
    }


    @Override
    public void onEventSelected(Event eventData) {

        Log.d("OnTouch","Event Selected " + DateHelper.isOver(eventData.getDateVotes().get(0).getDate()));
        Intent i = new Intent(getContext(),ReadEventActivity.class);
        i.putExtra("event", eventData);
        startActivity(i);
    }

    public EventAdapter geteAdapter() {
        return eAdapter;
    }

    public void seteAdapter(EventAdapter eAdapter) {
        this.eAdapter = eAdapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
