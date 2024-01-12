package com.aaw.aaw.O_solidObjects.simpleObjects;


import lombok.Getter;

public class privateUser {
    int uid;//不可读不可写不可改
    String email;//不可读必写可改
    String password;//不可读必写可改

    public int getUid() {
        return uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equalPassword(String password){
        return this.password.equals(password);
    }
    public boolean equalEmail(String email){
        return this.email.equals(email);
    }
    public privateUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
