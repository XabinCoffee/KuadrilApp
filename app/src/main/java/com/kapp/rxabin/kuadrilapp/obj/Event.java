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
    private ArrayList<DateVote> dateVotes;
    private String location;
    private String icon;
    private HashMap<String,String> userRole;


    public Event(String id, String owner, String name, String description, String location){
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.dateVotes=new ArrayList<>();
        this.location = location;
        this.members = new ArrayList<>();
        this.icon = "restaurant";
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


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<DateVote> getDateVotes() {
        return dateVotes;
    }

    public void setDateVotes(ArrayList<DateVote> dateVotes) {
        this.dateVotes = dateVotes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
