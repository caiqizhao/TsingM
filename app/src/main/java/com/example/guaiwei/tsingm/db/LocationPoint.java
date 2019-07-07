package com.example.guaiwei.tsingm.db;

import org.litepal.crud.DataSupport;

public class LocationPoint extends DataSupport {
    private int id;
    private int data;//日期
    private double longitude;//经度
    private double latitude;//纬度

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
