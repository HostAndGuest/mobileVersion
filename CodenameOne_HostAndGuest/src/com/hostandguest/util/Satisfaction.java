package com.hostandguest.util;


public class Satisfaction {
    private int mood;
    private int sLevel;

    public Satisfaction(int mood, int sLevel) {
        this.mood = mood;
        this.sLevel = sLevel;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getsLevel() {
        return sLevel;
    }

    public void setsLevel(int sLevel) {
        this.sLevel = sLevel;
    }
}
