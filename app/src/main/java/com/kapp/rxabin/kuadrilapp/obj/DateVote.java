package com.kapp.rxabin.kuadrilapp.obj;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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
    private String likes;
    private String dislikes;
    private HashMap<String,String> voters;


    /*public DateVote(String creator, String datetime){

        this.creator = creator;

        String[] a = datetime.split(" ");
        this.date = a[0];
        this.time = a[1];
        this.likes= "1";
        this.dislikes = "0";
    }*/


    public DateVote(){}

    public DateVote(String creator, String date, String time, String likes, String dislikes){
        this.creator = creator;
        this.date = date;
        this.time = time;
        this.likes = likes;
        this.dislikes = dislikes;
        this.voters = new HashMap<String,String>();
        this.voters.put(creator,"like");
    }

    public DateVote(Parcel in){
        this.creator = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.likes = in.readString();
        this.dislikes = in.readString();
        this.voters=in.readHashMap(String.class.getClassLoader());
    }


    public String toString(){
        return date + " " + time;
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public HashMap<String, String> getVoters() {
        return voters;
    }

    public void setVoters(HashMap<String, String> voters) {
        this.voters = voters;
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
        dest.writeString(likes);
        dest.writeString(dislikes);
        dest.writeMap(voters);
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
        Iterator it = this.getVoters().entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("like")) {
                i++;
                it.remove();
            }
        }
        return i;
    }

    public int countDislikes(){
        Iterator it = this.getVoters().entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals("dislike")) {
                i++;
                it.remove();
            }
        }
        return i;
    }

}
