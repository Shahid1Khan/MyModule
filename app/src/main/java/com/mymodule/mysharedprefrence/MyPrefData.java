package com.mymodule.mysharedprefrence;

import android.content.Context;

import com.mymodule.models.MyDetail;

/**
 * Created by wscube on 28/10/16.
 */

public class MyPrefData {


    Context act;
    GetSetSharedPrefs prefs;


    public static String userId = "userId", userName = "userName", userEmail = "userEmail", userPassword = "userPassword";
    public static String userDOB= "userDOB", userNumber= "userNumber", userToken="";

    public static String regToken = "regToken";

    public MyPrefData(Context act) {
        this.act = act;
        prefs = new GetSetSharedPrefs(act, "UserDetail");
    }


    public void setMyDetails(MyDetail user) {
        prefs.putData(userId, user.getId());
        prefs.putData(userName, user.getName());
        prefs.putData(userEmail, user.getEmail());
        prefs.putData(userPassword, user.getPassword());
        prefs.putData(userDOB, user.getDob());
        prefs.putData(userNumber, user.getNumber());
        prefs.putData(userToken, user.getToken());
    }


    public MyDetail getMyDetails() {
        MyDetail user = new MyDetail();
        user.setId(prefs.getData(userId));
        user.setName(prefs.getData(userName));
        user.setEmail(prefs.getData(userEmail));
        user.setPassword(prefs.getData(userPassword));
        user.setDob(prefs.getData(userDOB));
        user.setToken(prefs.getData(userToken));
        return user;
    }


    public String getUserId() {
        String s = "";
        s = prefs.getData(userId);
        return s;
    }



    public void setUserRegToken(String s) {
        prefs.putData(regToken, s);
    }


    public String getUserRegToken() {
        String s = "";
        s = prefs.getData(regToken);
        return s;
    }




}
