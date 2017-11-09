package com.kapp.rxabin.kuadrilapp.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.adapter.EventAdapter;
import com.kapp.rxabin.kuadrilapp.EventsFragment;
import com.kapp.rxabin.kuadrilapp.adapter.UserAdapter;
import com.kapp.rxabin.kuadrilapp.adapter.UserDialogAdapter;
import com.kapp.rxabin.kuadrilapp.helper.EventHelper;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DbManager {

    private static DatabaseReference mDatabase;

    public static Boolean createEvent(String name, String desc, String useruid, String type, String location, String date, String time, ArrayList<User> ul){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Event e = new Event(mDatabase.child("events").push().getKey(),useruid,name,desc,location);
        e.setIcon(EventHelper.getType(type));
        ArrayList<String> users = new ArrayList<>();

        for (int i = 0; i<ul.size();i++){
            users.add(ul.get(i).getUid());
        }
        if (!users.contains(useruid)) users.add(useruid);

        e.setMembers(users);
        DateVote dv = new DateVote(useruid, date, time, "1","0");
        e.getDateVotes().add(dv);

        mDatabase.child("events").child(e.getId()).setValue(e);


        return true;
    }


    public static void storeUser(String uid, String name, String email){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        User user = new User(uid,name,email);

        mDatabase.child("users").child(user.getUid()).setValue(user);

    }




    public static void getUserEvents(final EventAdapter eAdapter, final String uid){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        final ArrayList<Event> el = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.hasMember(uid)){
                        el.add(e);
                    }
                }
                EventsFragment.updateUI_events(el);
                eAdapter.setEvents(el);
                eAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }


    //
    public static void getUser(final UserAdapter uAdapter, final String email){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        User user = new User();
        final ArrayList<User> ul = uAdapter.getUsers();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    User u = userDataSnapshot.getValue(User.class);

                    if (u.getEmail().equalsIgnoreCase(email)){
                        if (!ul.contains(u)){
                            uAdapter.addUser(u);
                            uAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }

    public static void getUsernames(final UserDialogAdapter uAdapter){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        User user = new User();
        final ArrayList<String> sl = uAdapter.getEmails();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    User u = userDataSnapshot.getValue(User.class);
                    uAdapter.addEmail(u.getEmail());
                    Log.d("AAAAA","UUUUU");
                    uAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }
    /*public static void getAllUsers(Context context){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add member");
        final ArrayList<String> al = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User u = userDataSnapshot.getValue(User.class);
                    al.add(u.getEmail());
                }
                builder.setItems(al);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });

    }*/


}
