package com.example.guaiwei.tsingm.db;

import org.litepal.crud.DataSupport;

public class EveryActionInfo extends DataSupport {
    private int id;
    private String name;
    private String time;
    private int url;
    private int dayId;
    private boolean isComplete;//动作是否完成

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }
}
