package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.helper.DateHelper;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;

/**
 * Created by xabinrodriguez on 13/11/17.
 */

public class DateVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnLikeListener{
        void onLikeSelected(Event e, DateVote dv);
    }

    public interface OnDislikeListener{
        void onDislikeSelected(Event e, DateVote dv);
    }

    private Event e;
    private Context context;
    private OnLikeListener likeListener;
    private OnDislikeListener dislikeListener;


    public DateVoteAdapter(Context context, Event event, OnLikeListener likelistener, OnDislikeListener dislikelistener){
        this.e = event;
        this.context = context;
        this.likeListener = likelistener;
        this.dislikeListener = dislikelistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View iv = LayoutInflater.from(vg.getContext()).inflate(R.layout.datetime_row, vg, false);
        return new DateVoteViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DateVoteViewHolder){
            final DateVoteViewHolder dvh = (DateVoteViewHolder) holder;

            dvh.dv = e.getDateVotes().get(position);

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            String lang = pref.getString("listLang","eu");

            if (lang.equals("eu")) dvh.date.setText(DateHelper.eusDate(dvh.dv.getDate().toString()));
            else dvh.date.setText(DateHelper.espDate(dvh.dv.getDate().toString()));
            dvh.time.setText(dvh.dv.getTime().toString());
            dvh.likes.setText(Integer.toString(dvh.dv.countLikes()));
            dvh.dislikes.setText(Integer.toString(dvh.dv.countDislikes()));
            dvh.username.setText(dvh.dv.getCreator_name());

            /*dvh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("onClickDV",dvh.dv.getDate() + " " + dvh.dv.getTime());
                }
            });*/

            dvh.btnLike.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    if (null != likeListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        likeListener.onLikeSelected(e, dvh.dv);

                    }
                }
            });

            dvh.btnDislike.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    if (null != dislikeListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        dislikeListener.onDislikeSelected(e, dvh.dv);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (this.e.getDateVotes()!=null) return this.e.getDateVotes().size(); else return 0;
    }

    public class DateVoteViewHolder extends RecyclerView.ViewHolder{

        private DateVote dv;
        private TextView date;
        private TextView time;
        private ImageButton btnLike;
        private ImageButton btnDislike;
        private TextView likes;
        private TextView dislikes;
        private TextView username;


        public DateVoteViewHolder(View v){
            super(v);
            date = (TextView) v.findViewById(R.id.tvDate);
            time = (TextView) v.findViewById(R.id.tvTime);
            btnLike = (ImageButton) v.findViewById(R.id.btnLike);
            btnDislike = (ImageButton) v.findViewById(R.id.btnDislike);
            likes = (TextView) v.findViewById(R.id.tvLikes);
            dislikes = (TextView) v.findViewById(R.id.tvDislikes);
            username = (TextView) v.findViewById(R.id.tvUsername);
        }
    }

    public ArrayList<DateVote> getDateVotes(){
        return this.e.getDateVotes();
    }

    public Event getEvent(){
        return this.e;
    }


}
