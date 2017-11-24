package com.kapp.rxabin.kuadrilapp.obj;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by xabinrodriguez on 3/11/17.
 */

public class Event implements Parcelable {

    private String id;
    private String owner;
    private String name;
    private String description;
    private String location;
    private String icon;
    private ArrayList<DateVote> dateVotes;
    private ArrayList<String> members;
    private HashMap<String,String> userRole;

    public Event(){
        //
    }

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

    public Event(Parcel in){
        this.id = in.readString();
        this.owner = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.dateVotes= in.readArrayList(DateVote.class.getClassLoader());
        this.location = in.readString();
        this.members = in.readArrayList(String.class.getClassLoader());
        this.icon = in.readString();
        this.userRole=in.readHashMap(HashMap.class.getClassLoader());
    }

    public boolean hasMember(String uid){
        if (this.members.contains(uid)) return true;
        else return false;
    }

    public int numOfMembers(){
        return this.members.size();
    }

    @Override
    public boolean equals(Object o){
        boolean same = false;
        if(o != null && o instanceof Event){
            Event e = (Event) o;
            same = this.id.equals(e.getId());
        }
        return same;
    }


    public void sortDateList() {
        if (this.dateVotes != null) {
            Collections.sort(this.dateVotes, new Comparator<DateVote>() {
                public int compare(DateVote dv1, DateVote dv2) {
                    // avoiding NullPointerException in case name is null
                    Integer l1 = dv1.calculateValue();
                    Integer l2 = dv2.calculateValue();
                    return l2.compareTo(l1);
                }
            });
        }
    }

    public int getDateVote(String uid){

        boolean found = false;

        int pos = -1;

        int i = 0;
        while (i<this.dateVotes.size() && !found){
            if (this.dateVotes.get(i).getCreator().equals(uid)){
                pos = i;
                found=true;
            }
            else{
                i++;
            }
        }
        return pos;
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

    public void setName(String name) { this.name = name; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeList(dateVotes);
        dest.writeString(location);
        dest.writeList(members);
        dest.writeString(icon);
        dest.writeMap(userRole);

    }

    public static final Creator CREATOR = new Creator() {

        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[0];
        }

    };

}
