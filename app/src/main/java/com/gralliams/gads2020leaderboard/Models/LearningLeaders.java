package com.gralliams.gads2020leaderboard.Models;

import com.google.gson.annotations.SerializedName;

public class LearningLeaders {
    @SerializedName("name")
    private  String name;
    @SerializedName("hours")
    private int hours;
    @SerializedName("country")
    private String country;

    public LearningLeaders(String name, int hours, String country) {
        this.name = name;
        this.hours = hours;
        this.country = country;
    }

    public LearningLeaders() {
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}