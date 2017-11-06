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

    public DateVote(String creator, String date, String time, String likes, String dislikes){
        this.creator = creator;
        this.date = date;
        this.time = time;
        this.likes = likes;
        this.dislikes = dislikes;
    }

}
