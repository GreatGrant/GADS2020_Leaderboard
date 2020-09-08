package com.gralliams.gads2020leaderboard.Models;

import com.google.gson.annotations.SerializedName;

public class SkillIqLeaders {
    @SerializedName("name")
    private String name;
    @SerializedName("score")
    private int skillIq;
    @SerializedName("country")
    private String country;

    public SkillIqLeaders() {
    }

    public SkillIqLeaders(String name, int skillIq, String country) {
        this.name = name;
        this.skillIq = skillIq;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public int getSkillIq() {
        return skillIq;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkillIq(int skillIq) {
        this.skillIq = skillIq;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}