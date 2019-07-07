package com.example.guaiwei.tsingm.utils;

import com.example.guaiwei.tsingm.gson.DayPlan;
import com.example.guaiwei.tsingm.gson.EveryAction;
import com.example.guaiwei.tsingm.gson.Nutriment;
import com.example.guaiwei.tsingm.gson.User_Plan;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.google.gson.Gson;

import java.util.List;

/**
 * j解析服务器返回的JSON数据的工具类
 */
public class Utility {

    /**
     * 将返回的JSON数据解析成User_Plan实体类
     */
    public static User_Plan handleUserPlanResponse(String response){
        try{
            Gson gson=new Gson();
            User_Plan user_plan=gson.fromJson(response,User_Plan.class);
            return user_plan;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将服务器返回的训练数据存入数据库
     */
    public static void dbSave(String response){
        User_Plan user_plan=handleUserPlanResponse(response);
        List<DayPlan> dayPlans=user_plan.getDayPlans();
        List<String> data=GetBeforeData.getBeforeData(null,4);
        for(int i=0;i<dayPlans.size();i++){
            DayPlanInfo dayPlanInfo=new DayPlanInfo();
            dayPlanInfo.setCountAction(dayPlans.get(i).getCountAction());
            dayPlanInfo.setName(dayPlans.get(i).getPlan_name());
            dayPlanInfo.setNengliang(dayPlans.get(i).getNengliang());
            dayPlanInfo.setTime(dayPlans.get(i).getTime());
            dayPlanInfo.setData(data.get(i));
            dayPlanInfo.save();

            Nutriment nutriment=dayPlans.get(i).getNutriment();
            NutrimentInfo nutrimentInfo=new NutrimentInfo();
            nutrimentInfo.setCarbohydrate(nutriment.getCarbohydrate());
            nutrimentInfo.setFat(nutriment.getFat());
            nutrimentInfo.setHeat(nutriment.getHeat());
            nutrimentInfo.setProtein(nutriment.getProtein());
            nutrimentInfo.setData(data.get(i));
            nutrimentInfo.save();

            List<EveryAction> everyActions=dayPlans.get(i).getActions();
            for(int j=0;j<everyActions.size();j++){
                EveryActionInfo everyActionInfo=new EveryActionInfo();
                everyActionInfo.setDayId(dayPlanInfo.getId());
                everyActionInfo.setName(everyActions.get(j).getAction_name());
                everyActionInfo.setTime(everyActions.get(j).getAction_time());
                everyActionInfo.setUrl(everyActions.get(j).getUrl());
                everyActionInfo.setComplete(false);//默认没有完成
                everyActionInfo.save();
            }
        }
    }
}
