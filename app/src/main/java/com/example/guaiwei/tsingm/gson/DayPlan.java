package com.example.guaiwei.tsingm.gson;

import java.util.List;

public class DayPlan {
    private String plan_name;
    private String CountAction;
    private String time;
    private String nengliang;
    private List<EveryAction> actions;
    private int bushu;
    private Nutriment nutriment;

    @Override
    public String toString() {
        return "DayPlan{" +
                "plan_name='" + plan_name + '\'' +
                ", CountAction='" + CountAction + '\'' +
                ", time='" + time + '\'' +
                ", nengliang='" + nengliang + '\'' +
                ", actions=" + actions +
                ", bushu=" + bushu +
                ", nutriment=" + nutriment +
                '}';
    }

    public Nutriment getNutriment() {
        return nutriment;
    }

    public void setNutriment(Nutriment nutriment) {
        this.nutriment = nutriment;
    }

    public int getBushu() { return bushu; }

    public void setBushu(int bushu) { this.bushu = bushu; }

    public List<EveryAction> getActions() {
        return actions;
    }

    public void setActions(List<EveryAction> actions) {
        this.actions = actions;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
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


}
