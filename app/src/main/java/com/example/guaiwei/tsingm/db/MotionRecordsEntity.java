package com.example.guaiwei.tsingm.db;

import org.litepal.crud.DataSupport;

public class MotionRecordsEntity extends DataSupport {
    private int id;
    private String movement_type;
    private String movement_content;
    private String data;
    private String time;
    private double haoneng;
    private int dayplanId;

    public int getDayplanId() {
        return dayplanId;
    }

    public void setDayplanId(int dayplanId) {
        this.dayplanId = dayplanId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getHaoneng() {
        return haoneng;
    }

    public void setHaoneng(double haoneng) {
        this.haoneng = haoneng;
    }

    public String getMovement_type() {
        return movement_type;
    }

    public void setMovement_type(String movement_type) {
        this.movement_type = movement_type;
    }

    public String getMovement_content() {
        return movement_content;
    }

    public void setMovement_content(String movement_content) {
        this.movement_content = movement_content;
    }
}
