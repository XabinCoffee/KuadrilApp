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

public class UserDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnUserSelectedListener{
        void onUserSelected(User userData);
    }

    private ArrayList<User> userList;
    private Context context;
    private OnUserSelectedListener mListener;

    public UserDialogAdapter(Context context, OnUserSelectedListener newListener){
        this.userList = new ArrayList<>();
        this.context = context;
        this.mListener = newListener;
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

            User u = userList.get(position);
            uvh.u = u;
            uvh.name.setText(u.getUsername());

            uvh.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onUserSelected(uvh.u);

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (userList!=null) return userList.size(); else return 0;
    }

    public class UsernameViewHolder  extends RecyclerView.ViewHolder{

        private User u;
        private TextView name;

        public UsernameViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.tvDate);

        }

    }

    public ArrayList<User> getUsers(){
        return this.userList;
    }

    public void setUsers(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    public void addUser(User user) {
        this.userList.add(user);
        notifyDataSetChanged();
    }
}
