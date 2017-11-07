package com.kapp.rxabin.kuadrilapp.database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kapp.rxabin.kuadrilapp.obj.Event;
import com.kapp.rxabin.kuadrilapp.obj.User;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DbManager {

    private static DatabaseReference mDatabase;


    public static Boolean createEvent(String name, String desc, String username, String type, String location){

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Event e = new Event(Integer.toString(getEventCount()),username,name,desc,location);
        e.setIcon(type);
        return true;
    }

    public static void storeUser(String uid, String name, String email){

        User user = new User(uid,name,email);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).setValue(user);

    }


    public static int getEventCount(){
        return 1;
    }
}
