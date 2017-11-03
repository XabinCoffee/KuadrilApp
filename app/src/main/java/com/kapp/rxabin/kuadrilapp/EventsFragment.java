package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventAdapter eAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        eAdapter = new EventAdapter();
        ArrayList<Event> el = new ArrayList();
        Event e = new Event("a","a","aaaaaaaaaaaaaaa","");
        el.add(e);
        eAdapter.setEvents(el);
        recyclerView.setAdapter(eAdapter);

        return view;
    }

}
