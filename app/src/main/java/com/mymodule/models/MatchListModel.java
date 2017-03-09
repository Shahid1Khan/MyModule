package com.mymodule.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hi on 05-01-2017.
 */

public class MatchListModel implements Serializable {

    @SerializedName("TourId")
    String id;
    @SerializedName("TeamName1")
    String team1Name;
    @SerializedName("TeamName2")
    String team2Name;
    @SerializedName("TeamFlag1")
    String team1Flag;
    @SerializedName("TeamFlag2")
    String team2Flag;
    @SerializedName("TName")
    String matchName;
    @SerializedName("Start_Date")
    String matchSchedule;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam1Flag() {
        return team1Flag;
    }

    public void setTeam1Flag(String team1Flag) {
        this.team1Flag = team1Flag;
    }

    public String getTeam2Flag() {
        return team2Flag;
    }

    public void setTeam2Flag(String team2Flag) {
        this.team2Flag = team2Flag;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchSchedule() {
        return matchSchedule;
    }

    public void setMatchSchedule(String matchSchedule) {
        this.matchSchedule = matchSchedule;
    }
}
