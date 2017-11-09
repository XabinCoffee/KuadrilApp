package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xabinrodriguez on 9/11/17.
 */

public class UserDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<String> emailList;
    private Context context;

    public UserDialogAdapter(Context context){
        this.emailList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View iv = LayoutInflater.from(vg.getContext()).inflate(R.layout.user_pick_row, vg, false);
        return new UsernameViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UsernameViewHolder){
            final UsernameViewHolder uvh = (UsernameViewHolder) holder;

            String s = emailList.get(position);
            uvh.s = s;
            uvh.name.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        if (emailList!=null) return emailList.size(); else return 0;
    }

    public class UsernameViewHolder  extends RecyclerView.ViewHolder{

        private String s;
        private TextView name;

        public UsernameViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.tvUsername);

        }

    }

    public ArrayList<String> getEmails(){
        return this.emailList;
    }

    public void setEmails(List<String> emailList) {
        this.emailList.clear();
        this.emailList.addAll(emailList);
        notifyDataSetChanged();
    }

    public void addEmail(String email) {
        this.emailList.add(email);
        notifyDataSetChanged();
    }
}
