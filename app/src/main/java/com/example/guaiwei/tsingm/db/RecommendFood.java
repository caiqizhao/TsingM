package com.example.guaiwei.tsingm.db;

import org.litepal.crud.DataSupport;

public class RecommendFood extends DataSupport {
    private int id;
    private String food_name;//食物的名称
    private String foodG;//食物克数
    private String energy;//食物的热量
    private String rl;//100g食物的热量
//    private String url;//图片地址
    private String dbz;
    private String cshhw;
    private String zf;
    private String type;
    private String data;
    private Boolean isComplete;//打卡是否完成
    private String kind;//推荐的食物三餐类型

    public RecommendFood(){}
    public RecommendFood(String food_name, String foodG,String energy, String rl, String dbz, String cshhw, String zf, String type,boolean isComplete,String kind) {
        this.food_name = food_name;
        this.foodG = foodG;
        this.energy=energy;
        this.rl = rl;
        this.dbz = dbz;
        this.cshhw = cshhw;
        this.zf = zf;
        this.type = type;
        this.isComplete=isComplete;
        this.kind=kind;
    }

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

    public String getFood_name() { return food_name; }

    public void setFood_name(String food_name) { this.food_name = food_name; }

    public String getFoodG() { return foodG; }

    public void setFoodG(String foodG) { this.foodG = foodG; }

    public String getRl() { return rl; }

    public void setRl(String rl) { this.rl = rl; }

    public String getEnergy() { return energy; }

    public void setEnergy(String energy) { this.energy = energy; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Boolean getComplete() { return isComplete; }

    public void setComplete(Boolean complete) { isComplete = complete; }

    public String getKind() { return kind; }

    public void setKind(String kind) { this.kind = kind; }
}
