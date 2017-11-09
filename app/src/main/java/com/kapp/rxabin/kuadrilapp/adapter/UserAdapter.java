package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xabinrodriguez on 9/11/17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<User> userList;
    private Context context;

    public UserAdapter(Context context){

        this.userList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View iv = LayoutInflater.from(vg.getContext()).inflate(R.layout.user_edit_row, vg, false);
        return new UserViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UserViewHolder){
            final UserViewHolder uvh = (UserViewHolder) holder;

            User u = userList.get(position);
            uvh.u = u;
            uvh.username.setText(u.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        if (userList!=null) return userList.size();
        else return 0;
    }


    public class UserViewHolder extends RecyclerView.ViewHolder{

        private User u;
        private TextView username;
        private TextView remove;


        public UserViewHolder(View v){
            super(v);
            username = (TextView) v.findViewById(R.id.tvUsername);
            remove = (TextView) v.findViewById(R.id.tvRemove);
        }

    }

    public void setEvents(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }
}
