package com.mymodule.models;

import java.io.Serializable;

/**
 * Created by Hi on 05-01-2017.
 */

public class MyDetail implements Serializable {
    String id="",name="",email="",password="",dob="",number="",token="";

    public MyDetail() {
    }

    public MyDetail(String id, String name, String email, String password, String dob, String number,String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.number = number;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
