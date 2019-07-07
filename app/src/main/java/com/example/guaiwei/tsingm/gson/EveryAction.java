package com.example.guaiwei.tsingm.gson;

public class EveryAction {
    private int id;
    private String action_name;
    private String action_time;
    private String url;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return action_name +":"+ action_time +":"+url;
    }
}
