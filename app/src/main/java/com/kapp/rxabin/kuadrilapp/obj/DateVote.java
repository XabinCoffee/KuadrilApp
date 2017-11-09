package com.kapp.rxabin.kuadrilapp.obj;

import java.util.HashMap;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DateVote {

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
}
