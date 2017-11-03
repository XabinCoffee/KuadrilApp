package com.kapp.rxabin.kuadrilapp.obj;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xabinrodriguez on 3/11/17.
 */

public class Event {

    private String id;
    private String owner;
    private ArrayList<String> members;
    private String name;
    private String description;
    private HashMap<String,String> userRole;


    public Event(String id, String owner, String name, String description){
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
        this.userRole = new HashMap<>();
    }


    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }


    public HashMap<String, String> getUserRole() {
        return userRole;
    }

    public void setUserRole(HashMap<String, String> userRole) {
        this.userRole = userRole;
    }




}
