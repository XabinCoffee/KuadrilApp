package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xabinrodriguez on 3/11/17.
 */


public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Event> eventsList;

    public EventAdapter(){
        this.eventsList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View iv = LayoutInflater.from(vg.getContext()).inflate(R.layout.event_row, vg, false);
        return new EventViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EventViewHolder){
            final EventViewHolder evh = (EventViewHolder) holder;

            Event e = eventsList.get(position);
            evh.e = e;
            evh.title.setText(e.getName());

        }
    }

    @Override
    public int getItemCount() {
        if (eventsList!=null) return eventsList.size(); else return 0;
    }


    public class EventViewHolder extends RecyclerView.ViewHolder{

        private Event e;
        private TextView title;

        public EventViewHolder(View v){
            super(v);
            title = v.findViewById(R.id.tvTitle);
        }

    }

    public void setEvents(List<Event> eventList) {
        this.eventsList.clear();
        this.eventsList.addAll(eventList);
        notifyDataSetChanged();
    }

}
