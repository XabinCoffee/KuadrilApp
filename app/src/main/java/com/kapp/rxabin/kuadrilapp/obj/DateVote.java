package com.kapp.rxabin.kuadrilapp.obj;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DateVote implements Parcelable {

    private String creator;
    private String date;
    private String time;
    private ArrayList<String> likes;
    private ArrayList<String> dislikes;


    //private HashMap<String,String> voters;


    /*public DateVote(String creator, String datetime){

        this.creator = creator;

        String[] a = datetime.split(" ");
        this.date = a[0];
        this.time = a[1];
        this.likes= "1";
        this.dislikes = "0";
    }*/


    public DateVote(){}

    public DateVote(String creator, String date, String time){
        this.creator = creator;
        this.date = date;
        this.time = time;
        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();
        this.likes.add(creator);
    }

    public DateVote(Parcel in){
        this.creator = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.likes = in.readArrayList(String.class.getClassLoader());
        this.dislikes = in.readArrayList(String.class.getClassLoader());
    }


    public String toString(){
        return date + " " + time;
    }

    public String toStringLong(){
        return date + " " + time + ", likes: " + countLikes() + ", dislikes: " + countDislikes();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getLikes() {return this.likes;}

    public ArrayList<String> getDislikes() {return this.dislikes;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(creator);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeList(likes);
        dest.writeList(dislikes);
    }

    public static final Creator CREATOR = new Creator() {

        public DateVote createFromParcel(Parcel in) {
            return new DateVote(in);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[0];
        }

    };

    public int countLikes() {
       if (this.likes != null) return this.likes.size(); else return 0;
    }

    public int countDislikes(){
        if (this.dislikes != null) return this.dislikes.size(); else return 0;
    }


    public void userLikes(String uid){

        //TODO

    /*
        if (this.voters == null) this.voters = new HashMap<String,String>();

        if (this.voters.get(uid)==null) this.voters.put(uid,"like");

        else {
            if (this.voters.get(uid).equalsIgnoreCase("like")) this.voters.remove(uid);
            else if (this.voters.get(uid).equalsIgnoreCase("dislike")) this.voters.put(uid, "like");
        }
        */
    }


    public void userDislikes(String uid){

        //TODO

        /*
        if (this.voters == null) this.voters = new HashMap<String,String>();

        if (this.voters.get(uid)==null) this.voters.put(uid,"dislike");

        else {
            if (this.voters.get(uid).equalsIgnoreCase("dislike")) this.voters.remove(uid);
            else if (this.voters.get(uid).equalsIgnoreCase("like")) this.voters.put(uid, "dislike");
        }*/
    }



}
