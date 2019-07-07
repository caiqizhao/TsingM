package com.example.guaiwei.tsingm.gson;

import org.litepal.crud.DataSupport;

public class RecommendFood extends DataSupport {
    private int id;
    private String foodName;//食物的名称
    private String foodG;//食物克数
    private String foodReLiang;//食物的热量
    private String url;//图片地址
    private String dbz;
    private String cshhw;
    private String zf;
    private String type;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDbz() {
        return dbz;
    }

    public void setDbz(String dbz) {
        this.dbz = dbz;
    }

    public String getCshhw() {
        return cshhw;
    }

    public void setCshhw(String cshhw) {
        this.cshhw = cshhw;
    }

    public String getZf() {
        return zf;
    }

    public void setZf(String zf) {
        this.zf = zf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodG() {
        return foodG;
    }

    public void setFoodG(String foodG) {
        this.foodG = foodG;
    }

    public String getFoodReLiang() {
        return foodReLiang;
    }

    public void setFoodReLiang(String foodReLiang) {
        this.foodReLiang = foodReLiang;
    }
}
