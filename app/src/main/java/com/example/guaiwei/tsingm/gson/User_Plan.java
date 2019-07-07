package com.example.guaiwei.tsingm.gson;

import java.util.List;

public class User_Plan {
    public static User_Plan userPlan= null;
    private int day;
    private List<DayPlan> dayPlans;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<DayPlan> getDayPlans() {
        return dayPlans;
    }

    public void setDayPlans(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }

    @Override
    public String toString() {
        return "User_Plan{" +
                "day=" + day +
                ", dayPlans=" + dayPlans +
                '}';
    }
}
