package com.kapp.rxabin.kuadrilapp.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    public static Boolean createEvent(String name, String desc, String useruid, String type, String location, String date, String time){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Event e = new Event(mDatabase.child("events").push().getKey(),useruid,name,desc,location);
        e.setIcon(EventHelper.getType(type));
        ArrayList<String> users = new ArrayList<>();
        users.add(useruid);
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

}
