package com.kapp.rxabin.kuadrilapp.database;

import com.kapp.rxabin.kuadrilapp.obj.Event;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DbManager {

    private Boolean createEvent(String name, String desc, String username, String type, String location){
        Event e = new Event(Integer.toString(getEventCount()),username,name,desc,location);
        e.setIcon(type);
        return true;
    }


    private int getEventCount(){
        return 1;
    }
}
