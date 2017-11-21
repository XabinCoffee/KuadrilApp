package com.kapp.rxabin.kuadrilapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.kapp.rxabin.kuadrilapp.adapter.EventAdapter;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;
import java.util.HashMap;


public class EventsFragment extends Fragment implements EventAdapter.OnEventLongClickListener, EventAdapter.OnEventSelectedListener{

    public static ArrayList<DateVote> little_trick;

    private RecyclerView.LayoutManager mLayoutManager;
    private EventAdapter eAdapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private static ProgressBar mLoading;
    private static TextView mEmpty;
    private AlertDialog alertDialog;


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

    @Override
    public void onResume() {
        super.onResume();
        fillRecyclerView();
    }


    public void fillRecyclerView(){

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        eAdapter = new EventAdapter(getContext(),this,this);
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
        //TODO
        Log.d("OnTouch","Event Selected");
        //little_trick = eventData.getDateVotes();
        Intent i = new Intent(getContext(),ReadEventActivity.class);
        i.putExtra("event", eventData);
        startActivity(i);
    }
}
