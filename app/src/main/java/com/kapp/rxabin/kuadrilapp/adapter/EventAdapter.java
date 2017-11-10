package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.helper.EventHelper;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xabinrodriguez on 3/11/17.
 */


public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnEventLongClickListener{
        void onEventLongClick(Event eventData);
    }

    public interface OnEventSelectedListener{
        void onEventSelected(Event eventData);
    }

    private ArrayList<Event> eventsList;
    private Context context;
    private OnEventLongClickListener longListener;
    private OnEventSelectedListener mListener;

    public EventAdapter(Context context, OnEventLongClickListener newLongListener, OnEventSelectedListener newListener){

        this.eventsList = new ArrayList<>();
        this.context = context;
        this.longListener = newLongListener;
        this.mListener = newListener;
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
            evh.description.setText(e.getDescription());
            int icon_id = EventHelper.getIcon(e.getIcon());
            evh.icon.setImageDrawable(context.getResources().getDrawable(icon_id));
            evh.members.setText(Integer.toString(e.numOfMembers()));
            evh.location.setText(e.getLocation());
            evh.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onEventSelected(evh.e);

                    }
                }
            });

            evh.itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View view) {

                    if (null != longListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        longListener.onEventLongClick(evh.e);
                    }
                    return true;
                }
            });

            //evh.date.setText(e.getDate());


        }
    }

    @Override
    public int getItemCount() {
        if (eventsList!=null) return eventsList.size();
        else return 0;
    }


    public class EventViewHolder extends RecyclerView.ViewHolder{

        private Event e;
        private TextView title;
        private TextView description;
        private ImageView icon;
        private TextView members;
        private TextView location;
        //private TextView date;

        public EventViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.tvTitle);
            description = (TextView) v.findViewById(R.id.tvDescription);
            icon = (ImageView) v.findViewById(R.id.imgLogo);
            members = (TextView) v.findViewById(R.id.tvMembers);
            location = (TextView) v.findViewById(R.id.tvLocation);
            //date = v.findViewById(R.id.tvDate);

        }

    }

    public void removeEvent(Event e){
        this.eventsList.remove(e);
        notifyDataSetChanged();
    }

    public void setEvents(List<Event> eventList) {
        this.eventsList.clear();
        this.eventsList.addAll(eventList);
        notifyDataSetChanged();
    }

}
