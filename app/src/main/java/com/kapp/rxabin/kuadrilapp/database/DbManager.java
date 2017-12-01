package com.kapp.rxabin.kuadrilapp.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.adapter.DateVoteAdapter;
import com.kapp.rxabin.kuadrilapp.adapter.EventAdapter;
import com.kapp.rxabin.kuadrilapp.EventsFragment;
import com.kapp.rxabin.kuadrilapp.adapter.UserAdapter;
import com.kapp.rxabin.kuadrilapp.adapter.UserDialogAdapter;
import com.kapp.rxabin.kuadrilapp.adapter.UserInEventAdapter;
import com.kapp.rxabin.kuadrilapp.helper.DateHelper;
import com.kapp.rxabin.kuadrilapp.helper.EventHelper;
import com.kapp.rxabin.kuadrilapp.obj.DateVote;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DbManager {

    private static DatabaseReference mDatabase;

    public static boolean createEvent(String name, String desc, String useruid, String username, String type, String location, String date, String time, ArrayList<User> ul){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Event e = new Event(mDatabase.child("events").push().getKey(),useruid,name,desc,location);
        e.setIcon(EventHelper.getType(type));
        ArrayList<String> users = new ArrayList<>();

        for (int i = 0; i<ul.size();i++){
            users.add(ul.get(i).getUid());
        }
        if (!users.contains(useruid)) users.add(useruid);

        e.setMembers(users);
        DateVote dv = new DateVote(useruid, date, time, username);
        e.getDateVotes().add(dv);

        e.setUserRole(new HashMap<String,String>());
        e.getUserRole().put(useruid,"Admin");

        mDatabase.child("events").child(e.getId()).setValue(e);


        return true;
    }

    public static boolean editEvent(Event e, String name, String desc, String location, ArrayList<User> ul){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        e.setName(name);
        e.setDescription(desc);
        e.setLocation(location);

        ArrayList<String> users = new ArrayList<>();

        for (int i = 0; i<ul.size();i++){
            users.add(ul.get(i).getUid());
        }
        if (!users.contains(e.getOwner())) users.add(e.getOwner());

        e.setMembers(users);

        mDatabase.child("events").child(e.getId()).setValue(e);


        return true;
    }



    public static void storeUser(String uid, String name, String email){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        User user = new User(uid,name,email);

        mDatabase.child("users").child(user.getUid()).setValue(user);

    }




    public static void getUserEvents(final EventAdapter eAdapter, final String uid, final RecyclerView rv, final Context context){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        final ArrayList<Event> el = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.hasMember(uid)){
                        e.sortDateList();
                        if (!DateHelper.isOver(e.getDateVotes().get(0).getDate())) el.add(e);
                    }
                }
                EventsFragment.updateUI_events(el);
                eAdapter.setEvents(el);
                eAdapter.sortListByDate();
                eAdapter.notifyDataSetChanged();
                rv.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {

                            @Override
                            public boolean onPreDraw() {
                                rv.getViewTreeObserver().removeOnPreDrawListener(this);

                                int ea = rv.getChildCount();
                                for (int i = 0; i < rv.getChildCount(); i++) {
                                    View v = rv.getChildAt(i);
                                    v.setAlpha(0.0f);
                                    v.animate().alpha(1.0f)
                                            .setDuration(200)
                                            .setStartDelay(i * 70)
                                            .start();
                                    v.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
                                }

                                return true;
                            }
                        });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }

    public static void getUserAllEvents(final EventAdapter eAdapter, final String uid, final RecyclerView rv, final Context context){

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
                eAdapter.sortListByDate();
                eAdapter.notifyDataSetChanged();
                rv.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {

                            @Override
                            public boolean onPreDraw() {
                                rv.getViewTreeObserver().removeOnPreDrawListener(this);

                                int ea = rv.getChildCount();
                                for (int i = 0; i < rv.getChildCount(); i++) {
                                    View v = rv.getChildAt(i);
                                    v.setAlpha(0.0f);
                                    v.animate().alpha(1.0f)
                                            .setDuration(300)
                                            .setStartDelay(i * 50)
                                            .start();

                                    v.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
                                }

                                return true;
                            }
                        });


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }


    /*
    By providing an email and an userAdapter, add the user with the email address to the adapter.
     */

    public static void getUser(final UserAdapter uAdapter, final String email, final RecyclerView rv){

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
                            final int num = uAdapter.getItemCount();
                            rv.getViewTreeObserver().addOnPreDrawListener(
                                    new ViewTreeObserver.OnPreDrawListener() {

                                        @Override
                                        public boolean onPreDraw() {
                                            rv.getViewTreeObserver().removeOnPreDrawListener(this);

                                            View v = rv.getChildAt(num-1);
                                            v.setAlpha(0.0f);
                                            v.animate().alpha(1.0f)
                                                    .setDuration(300)
                                                    .start();

                                            return true;
                                        }
                                    });
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



    /*
    When inviting people in an event show the list of the users who weren't invited excluding yourself
     */

    public static void getUsernamesExceptYourself(final UserDialogAdapter uAdapter, final String uid, UserAdapter uAdapter2, final RecyclerView rv, final Context context, final ProgressBar pb){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        User user = new User();
        final ArrayList<User> usersOnScreen = uAdapter2.getUsers();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    User u = userDataSnapshot.getValue(User.class);
                    if(!u.getUid().equals(uid) && !usersOnScreen.contains(u)) {
                        uAdapter.addUser(u);
                    }
                }
                uAdapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                rv.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {

                            @Override
                            public boolean onPreDraw() {
                                rv.getViewTreeObserver().removeOnPreDrawListener(this);

                                int ea = rv.getChildCount();
                                for (int i = 0; i < rv.getChildCount(); i++) {
                                    View v = rv.getChildAt(i);
                                    v.setAlpha(0.0f);
                                    v.animate().alpha(1.0f)
                                            .setDuration(300)
                                            .setStartDelay(i * 50)
                                            .start();

                                    v.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
                                }

                                return true;
                            }
                        });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }


    public static void getUsernamesFromEvent(final UserInEventAdapter uieAdapter, final ArrayList<String> members, final String uid){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        User user = new User();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    User u = userDataSnapshot.getValue(User.class);
                    if(members.contains(u.getUid())) {
                        if (u.getUid().equals(uid)){
                            uieAdapter.addUserInFront(u);
                        }else {
                            uieAdapter.addUser(u);
                        }
                    }
                }
                uieAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }




    public static void deleteEvent(final String id){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.getId().equals(id)){
                        eventDataSnapshot.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }

    public static void removeMember(final String id, final String useruid){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.getId().equals(id)){
                        e.getMembers().remove(useruid);
                        mDatabase.child(e.getId()).setValue(e);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }


    public static void updateRole(final Event ev, final String uid, final String newrole, final UserInEventAdapter uieAdapter){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.getId().equals(ev.getId())){
                        e.getUserRole().put(uid,newrole);
                        mDatabase.child(e.getId()).child("userRole").setValue(e.getUserRole());
                        uieAdapter.getEvent().getUserRole().put(uid,newrole);
                    }
                    uieAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }

    public static void updateUser(final String uid, final String newname){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    User u = userDataSnapshot.getValue(User.class);
                    if (u.getUid().equals(uid)){
                        mDatabase.child(uid).child("username").setValue(newname);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }

    public static void addDateVote(final DateVoteAdapter dvAdapter, String userid, String username, String date, String time){


        DateVote dv;

        if (dvAdapter.getEvent().getDateVote(userid)==-1) {
            dv = new DateVote(userid, date, time, username);
            dvAdapter.getDateVotes().add(dv);
            dvAdapter.getEvent().sortDateList();
            dvAdapter.notifyDataSetChanged();
        } else {
            int position = dvAdapter.getEvent().getDateVote(userid);
            dv = dvAdapter.getEvent().getDateVotes().get(position);
            dvAdapter.getDateVotes().remove(position);
            dv.setDate(date);
            dv.setTime(time);
            dvAdapter.getDateVotes().add(position,dv);
            //dvAdapter.getEvent().sortDateList();
            dvAdapter.notifyDataSetChanged();
        }

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.getId().equals(dvAdapter.getEvent().getId())){
                        mDatabase.child(e.getId()).child("dateVotes").setValue(dvAdapter.getEvent().getDateVotes());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });

    }


    public static void rateDateVote(final DateVoteAdapter dvAdapter, DateVote dv, String userid, boolean like){


        int pos = dvAdapter.getEvent().getDateVote(dv.getCreator());
        dvAdapter.getDateVotes().remove(pos);

        if (like) dv.userLikes(userid);
        else dv.userDislikes(userid);

        dvAdapter.getDateVotes().add(pos,dv);
        //dvAdapter.getEvent().sortDateList();
        dvAdapter.notifyDataSetChanged();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.getId().equals(dvAdapter.getEvent().getId())){
                        mDatabase.child(e.getId()).child("dateVotes").setValue(dvAdapter.getEvent().getDateVotes());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });

    }

    public static void getEditEventUsers(final Event e, final UserAdapter uAdapter){

        final ArrayList<String> members = e.getMembers();
        final String editor = e.getOwner();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    User u = userDataSnapshot.getValue(User.class);
                    if(members.contains(u.getUid())) {
                        if (!u.getUid().equals(editor)){
                            uAdapter.addUser(u);
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


}
