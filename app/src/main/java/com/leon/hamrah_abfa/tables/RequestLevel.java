package com.leon.hamrah_abfa.tables;

public class RequestLevel {
    private int id;
    private int level;
    private String title;
    private String date;
    private boolean doneLevel;
    private boolean currentLevel;

    public RequestLevel(int level, String title, String date, boolean doneLevel, boolean currentLevel) {
        this.level = level;
        this.title = title;
        this.date = date;
        this.doneLevel = doneLevel;
        this.currentLevel = currentLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDoneLevel() {
        return doneLevel;
    }

    public void setDoneLevel(boolean doneLevel) {
        this.doneLevel = doneLevel;
    }

    public boolean isCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(boolean currentLevel) {
        this.currentLevel = currentLevel;
    }
}
