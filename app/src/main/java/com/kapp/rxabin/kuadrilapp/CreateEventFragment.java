package com.kapp.rxabin.kuadrilapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {

    private Spinner spinner;
    private EditText title;
    private EditText desc;
    private ImageView imageView;
    private TextView eventType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View iv = inflater.inflate(R.layout.fragment_create_event, container, false);

        title = (EditText) iv.findViewById(R.id.etTitle);
        desc = (EditText) iv.findViewById(R.id.etDesc);
        imageView = (ImageView) iv.findViewById(R.id.imageView);
        eventType = (TextView) iv.findViewById(R.id.tvEventType);
        eventType.setText(getResources().getStringArray(R.array.eventType)[0].toString());

        return iv;

    }


}
