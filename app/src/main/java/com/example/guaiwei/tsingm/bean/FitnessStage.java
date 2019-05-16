package com.example.guaiwei.tsingm.bean;

import java.io.Serializable;

public class FitnessStage implements Serializable {
    private int CurrentSituation;//运动阶段（1~3:表示零基础、有经验、非常有经验）
    private int SportTarget;//运动目标（1~4:表示减脂、局部塑形、增肌、保持健康）
    private int CardioPulmonary;//心肺能力(1~4星）
    private int ChestEndurance;//胸肌肌耐力(1~5星）
    private int AbdominalEndurance;//腹肌肌耐力(1~5星）
    private int LowerLimb;//下肢肌耐力(1~5星）
    private int Flexibility;//髋关节柔韧度(1~4星）
    public FitnessStage(){}

    public int getCurrentSituation() {
        return CurrentSituation;
    }

    public void setCurrentSituation(int currentSituation) {
        CurrentSituation = currentSituation;
    }

    public int getSportTarget() {
        return SportTarget;
    }

    public void setSportTarget(int sportTarget) {
        SportTarget = sportTarget;
    }
    public int getCardioPulmonary() {
        return CardioPulmonary;
    }

    public void setCardioPulmonary(int cardioPulmonary) {
        CardioPulmonary = cardioPulmonary;
    }

    public int getChestEndurance() {
        return ChestEndurance;
    }

    public void setChestEndurance(int chestEndurance) {
        ChestEndurance = chestEndurance;
    }

    public int getAbdominalEndurance() {
        return AbdominalEndurance;
    }

    public void setAbdominalEndurance(int abdominalEndurance) {
        AbdominalEndurance = abdominalEndurance;
    }

    public int getLowerLimb() {
        return LowerLimb;
    }

    public void setLowerLimb(int lowerLimb) {
        LowerLimb = lowerLimb;
    }

    public int getFlexibility() {
        return Flexibility;
    }

    public void setFlexibility(int flexibility) {
        Flexibility = flexibility;
    }

}
