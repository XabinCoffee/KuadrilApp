package com.kapp.rxabin.kuadrilapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.kapp.rxabin.kuadrilapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {

    private Spinner spinner;
    private EditText title;
    private EditText desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View iv = inflater.inflate(R.layout.fragment_create_event, container, false);
        spinner = (Spinner) iv.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.eventType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        title = (EditText) iv.findViewById(R.id.etTitle);
        desc = (EditText) iv.findViewById(R.id.etDesc);


        return iv;

    }



}
