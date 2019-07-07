package com.example.guaiwei.tsingm.bean;

import java.util.List;

public class DayPlan {
    private String name;
    private String CountAction;
    private String time;
    private String nengliang;
    private List<EveryAction> actions;

    public List<EveryAction> getActions() {
        return actions;
    }

    public void setActions(List<EveryAction> actions) {
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountAction() {
        return CountAction;
    }

    public void setCountAction(String countAction) {
        CountAction = countAction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNengliang() {
        return nengliang;
    }

    public void setNengliang(String nengliang) {
        this.nengliang = nengliang;
    }

    @Override
    public String toString() {
        return "DayPlan{" +
                "name='" + name + '\'' +
                ", CountAction='" + CountAction + '\'' +
                ", time='" + time + '\'' +
                ", nengliang='" + nengliang + '\'' +
                ", actions=" + actions +
                '}';
    }
}
