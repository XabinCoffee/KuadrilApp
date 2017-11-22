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
    private String creator_name;


    public DateVote(){}

    public DateVote(String creator, String date, String time, String creator_name){
        this.creator = creator;
        this.date = date;
        this.time = time;
        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();
        this.likes.add(creator);
        this.creator_name = creator_name;
    }

    public DateVote(Parcel in){
        this.creator = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.likes = in.readArrayList(String.class.getClassLoader());
        this.dislikes = in.readArrayList(String.class.getClassLoader());
        this.creator_name = in.readString();
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

    public String getCreator_name(){
        return this.creator_name;
    }

    public void setCreator_name(String creator_name){
        this.creator_name = creator_name;
    }


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
        dest.writeString(creator_name);
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

        if (this.likes == null) this.likes = new ArrayList<>();
        if (this.dislikes == null) this.dislikes = new ArrayList();

        if (this.likes.contains(uid)) {
            this.likes.remove(uid);
        } else {
            if (this.dislikes.contains(uid)) this.dislikes.remove(uid);
            this.likes.add(uid);
        }

    }


    public void userDislikes(String uid){

        if (this.likes == null) this.likes = new ArrayList<>();
        if (this.dislikes == null) this.dislikes = new ArrayList();

        if (this.dislikes.contains(uid)) {
            this.dislikes.remove(uid);
        } else {
            if (this.likes.contains(uid)) this.likes.remove(uid);
            this.dislikes.add(uid);
        }

    }

    public int calculateValue(){
        return (this.countLikes() * 10) - (this.countDislikes()*15);
    }



}
