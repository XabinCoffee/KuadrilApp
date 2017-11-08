package com.kapp.rxabin.kuadrilapp.obj;

/**
 * Created by xabinrodriguez on 6/11/17.
 */

public class DateVote {

    private String creator;
    private String date;
    private String time;
    private String likes;
    private String dislikes;


    public DateVote(String creator, String datetime){

        this.creator = creator;

        String[] a = datetime.split(" ");
        this.date = a[0];
        this.time = a[1];
        this.likes= "1";
        this.dislikes = "0";
    }

    public DateVote(String creator, String date, String time, String likes, String dislikes){
        this.creator = creator;
        this.date = date;
        this.time = time;
        this.likes = likes;
        this.dislikes = dislikes;
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
}
