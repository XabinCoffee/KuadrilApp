package com.kapp.rxabin.kuadrilapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xabinrodriguez on 13/11/17.
 */

public class UserInEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface OnAddDateSelectedListener{
        void onAddDateSelected(String uid, Event e);
    }

    public interface OnEditRoleSelectedListener{
        void onEditRoleSelected(String uid, Event e);
    }

    private ArrayList<User> userList;
    private String uid;
    private Context context;
    private HashMap<String,String> roles;
    private OnAddDateSelectedListener mDateListener;
    private OnEditRoleSelectedListener mRoleListener;
    private Event event;

    public UserInEventAdapter(Context context, String uid, Event e, OnAddDateSelectedListener dateListener, OnEditRoleSelectedListener roleListener){

        this.userList = new ArrayList<>();
        this.context = context;
        this.uid = uid;
        this.mDateListener = dateListener;
        this.mRoleListener = roleListener;
        this.event = e;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
        View iv = LayoutInflater.from(vg.getContext()).inflate(R.layout.user_role_row, vg, false);
        return new UserInEventViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UserInEventViewHolder){
            final UserInEventViewHolder uvh = (UserInEventViewHolder) holder;

            User u = userList.get(position);
            uvh.u = u;
            uvh.username.setText(u.getUsername());

            if(event.getUserRole().get(u.getUid())!=null) {
                uvh.role.setText(event.getUserRole().get(u.getUid()));
            }

            boolean yourRow = (u.getUid().equals(uid));

            if (yourRow){
                uvh.addDate.setVisibility(View.VISIBLE);
                uvh.editRole.setVisibility(View.VISIBLE);

                float scale = context.getResources().getDisplayMetrics().density;
                int dpAsPixels = (int) (25*scale + 0.5f);
                uvh.role.setPadding(dpAsPixels/5,dpAsPixels,dpAsPixels/5,dpAsPixels/4);

                uvh.addDate.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {

                        if (null != mDateListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mDateListener.onAddDateSelected(uid,event);

                        }
                    }
                });

                uvh.editRole.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {

                        if (null != mRoleListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mRoleListener.onEditRoleSelected(uid,event);

                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (userList!=null) return userList.size();
        else return 0;
    }


    public class UserInEventViewHolder extends RecyclerView.ViewHolder{

        private User u;
        private TextView username;
        private TextView role;
        private Button addDate;
        private Button editRole;


        public UserInEventViewHolder(View v){
            super(v);
            username = (TextView) v.findViewById(R.id.tvUsername);
            role = (TextView) v.findViewById(R.id.tvRole);
            addDate = (Button) v.findViewById(R.id.btnChoseDatetime);
            editRole = (Button) v.findViewById(R.id.btnSetRole);
        }

    }

    public void addUserInFront(User u) {
        this.userList.add(0,u);
        notifyDataSetChanged();
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

    public Event getEvent(){
        return this.event;
    }

}
