package com.kapp.rxabin.kuadrilapp.obj;

/**
 * Created by xabinrodriguez on 7/11/17.
 */

public class User {
    private String uid;
    private String username;
    private String email;

    public User(){}

    public User(String uid, String username, String email){
        this.uid = uid;
        this.username=username;
        this.email=email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object object){
        boolean same = false;
        if(object != null && object instanceof User){
            User u = (User) object;
            same = this.uid.equals(u.getUid());
        }

        return same;
    }

}
