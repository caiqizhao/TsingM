package com.example.guaiwei.tsingm.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;//用户名
    private String sex;//用户性别
    private double height;//用户的身高
    private double weight;//用户的体重
    private Birthday userBirthday;//用户的出生年月
    private FitnessStage userFitnessStage;//用户的健身阶段数据
    public User(){
        userBirthday=new Birthday();
        userFitnessStage=new FitnessStage();
    }

    public Birthday getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Birthday userBirthday) {
        this.userBirthday = userBirthday;
    }

    public FitnessStage getUserFitnessStage() {
        return userFitnessStage;
    }

    public void setUserFitnessStage(FitnessStage userFitnessStage) {
        this.userFitnessStage = userFitnessStage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
