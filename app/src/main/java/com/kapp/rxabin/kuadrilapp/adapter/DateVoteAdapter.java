package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xabinrodriguez on 13/11/17.
 */

public class DateVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<DateVote> dvList;
    private Context context;

    public DateVoteAdapter(Context context){
        this.dvList = new ArrayList<>();
        this.context = context;
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
            DateVote dv = dvList.get(position);
            dvh.dv = dv;
            dvh.date.setText(dv.getDate().toString());
            dvh.time.setText(dv.getTime().toString());
        }
    }

    @Override
    public int getItemCount() {
        if (dvList!=null) return dvList.size(); else return 0;
    }

    public class DateVoteViewHolder extends RecyclerView.ViewHolder{

        private DateVote dv;
        private TextView date;
        private TextView time;
        private ImageButton btnLike;
        private ImageButton btnDislike;


        public DateVoteViewHolder(View v){
            super(v);
            date = (TextView) v.findViewById(R.id.tvDate);
            time = (TextView) v.findViewById(R.id.tvTime);
            btnLike = (ImageButton) v.findViewById(R.id.btnLike);
            btnDislike = (ImageButton) v.findViewById(R.id.btnDislike);
        }
    }

    public void addDateVote(DateVote dv){
        this.dvList.add(0,dv);
        notifyDataSetChanged();
    }

    public void setDateVotes(List<DateVote> dvl) {
        this.dvList.clear();
        this.dvList.addAll(dvl);
        notifyDataSetChanged();
    }
}
