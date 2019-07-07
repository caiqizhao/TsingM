package com.example.guaiwei.tsingm.db;

import org.litepal.crud.DataSupport;

public class DayPlanInfo extends DataSupport {
    private int id;
    private String name;
    private String CountAction;
    private String time;
    private String nengliang;
    private String completeAction;//动作完成个数
    private String completeData;//训练完成时间
    private String shijihaoneng;//实际消耗能量
    private String data;//训练日期

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getCompleteAction() {
        return completeAction;
    }

    public void setCompleteAction(String completeAction) {
        this.completeAction = completeAction;
    }

    public String getCompleteData() {
        return completeData;
    }

    public void setCompleteData(String completeData) {
        this.completeData = completeData;
    }

    public String getShijihaoneng() {
        return shijihaoneng;
    }

    public void setShijihaoneng(String shijihaoneng) {
        this.shijihaoneng = shijihaoneng;
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
