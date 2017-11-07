package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;
import java.util.Vector;


public class EventsFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private EventAdapter eAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        fillRecyclerView();

        return view;
    }

    public void fillRecyclerView(){

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        eAdapter = new EventAdapter();
        DbManager.reloadEvents();
        ArrayList<Event> el = DbManager.getEvents();
        Log.d("EVENT ADAPTER", Integer.toString(DbManager.getEvents().size()));
        eAdapter.setEvents(el);
        recyclerView.setAdapter(eAdapter);
    }

}
