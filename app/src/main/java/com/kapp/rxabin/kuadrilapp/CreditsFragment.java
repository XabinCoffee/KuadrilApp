package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;

import com.kapp.rxabin.kuadrilapp.adapter.CreditsAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class CreditsFragment extends Fragment {

    private RecyclerView rv;

    public CreditsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_credits, container, false);

        rv = v.findViewById(R.id.rvCredits);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(new CreditsAdapter());
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event



    public static CreditsFragment newInstance() {
        CreditsFragment fragment = new CreditsFragment();
        return fragment;
    }


}
