package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xabinrodriguez on 9/11/17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnUserRemovalSelectedListener{
        void onUserRemovalSelected(User userData);
    }

    private ArrayList<User> userList;
    private Context context;
    private OnUserRemovalSelectedListener mListener;

    public UserAdapter(Context context, OnUserRemovalSelectedListener newListener){

        this.userList = new ArrayList<>();
        this.context = context;
        this.mListener = newListener;
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

            uvh.remove.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onUserRemovalSelected(uvh.u);

                    }
                }
            });
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
        private ImageButton remove;


        public UserViewHolder(View v){
            super(v);
            username = (TextView) v.findViewById(R.id.tvCreditsTitle);
            remove = (ImageButton) v.findViewById(R.id.btnRemove);
        }

    }

    public void addUser(User u) {
        this.userList.add(u);
        notifyDataSetChanged();
    }

    public ArrayList<User> getUsers(){
        return this.userList;
    }

    public void setUsers(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }
}
