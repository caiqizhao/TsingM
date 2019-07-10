package com.example.guaiwei.tsingm.utils;

import com.example.guaiwei.tsingm.gson.DayPlan;
import com.example.guaiwei.tsingm.gson.EveryAction;
import com.example.guaiwei.tsingm.gson.Nutriment;
import com.example.guaiwei.tsingm.gson.RecommendFood;
import com.example.guaiwei.tsingm.gson.User_Plan;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
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

    /**
     * 获取各部位强度的数据列表
     * @param j 强度等级
     * @return
     */
    public static List<Integer> getStageList(int j){
        List<Integer> list=new ArrayList<>();
        if(j<=5){
            for(int i=0;i<j;i++){
                list.add(1);
            }
            for (int i=j;i<5;i++){
                list.add(0);
            }
        }
        else{
            for (int i=0;i<5;i++){
                list.add(1);
            }
        }
        return list;
    }

    /**
     * 获取推荐的食物(早餐）
     * @return
     */
    public static List<List<RecommendFood>> getBreakFast(){
        List<List<RecommendFood>> breakFasts=new ArrayList<>();
        RecommendFood food01=new RecommendFood("豆浆","220.0毫升","68千卡","31","3.0","1.2","1.6","低热量豆制品");
        RecommendFood food02=new RecommendFood("鸡蛋（煮），又叫水煮蛋，白煮蛋，煮蛋，煮鸡蛋","106.0克","153千卡","144","13.3","8.8","2.8","高胆固醇-肉蛋类");
        RecommendFood food03=new RecommendFood("玉米(鲜)","132.0克","148千卡","112","4.0","1.2","22.8","低碳水化合物-谷薯类");
        RecommendFood food04=new RecommendFood("榛子仁（熟）","16.0克","100千卡","628","15.6","52.9","26.7","坚果、大豆及制品");
        RecommendFood food11=new RecommendFood("牛奶，又叫纯牛奶，牛乳","102.0毫升","55千卡","54","3.0","3.2","3.4","乳制品类");
        RecommendFood food12=new RecommendFood("馒头","102.0克","227千卡","223","7.0","1.1","47.0","主食类");
        RecommendFood food13=new RecommendFood("橙，又叫橙子、金球、香橙、黄橙、脐橙","201.0克","96千卡","48","0.8","0.2","11.1","水果类");
        RecommendFood food14=new RecommendFood("小米粥","192.0克","88千卡","46","1.4","0.7","8.4","主食类");
        List<RecommendFood> foodList1=new ArrayList<>();List<RecommendFood> foodList2=new ArrayList<>();
        foodList1.add(food01);foodList1.add(food02);foodList1.add(food03);foodList1.add(food04);
        foodList2.add(food11);foodList2.add(food12);foodList2.add(food13);foodList2.add(food14);
        breakFasts.add(foodList1);breakFasts.add(foodList2);
        return breakFasts;
    }

    /**
     * 获取推荐的食物(午餐）
     * @return
     */
    public static List<List<RecommendFood>> getLunch(){
        List<List<RecommendFood>> lunchs=new ArrayList<>();
        RecommendFood food01=new RecommendFood("米饭","280.0克","325千卡","116","2.6","0.3","25.9","主食类");
        RecommendFood food02=new RecommendFood("花菜，又叫菜花、花椰菜、椰花菜、洋花菜","82.0克","16千卡","20","1.7","0.2","4.2","蔬菜菌藻类");
        RecommendFood food03=new RecommendFood("猪肉(肋条肉)","70.0克","398千卡","568","9.3","59.0","0.0","肉蛋类");
        RecommendFood food11=new RecommendFood("米饭","291.0克","338千卡","116","2.6","0.3","25.9","主食类");
        RecommendFood food12=new RecommendFood("草鱼，又叫鲩鱼、混子、草鲩、草包鱼、草根鱼、草青、白鲩","321.0克","363千卡","113","16.6","5.2","0.0","海鲜类");
        RecommendFood food13=new RecommendFood("甜瓜，又叫甘瓜、库洪（维吾尔语）","161.0克","42千卡","26","0.4","0.1","6.2","水果类");
        RecommendFood food14=new RecommendFood("上海青，又叫青菜，小青菜","52.0克","9千卡","18","1.7","0.2","3.2","蔬菜类");
        List<RecommendFood> foodList1=new ArrayList<>();List<RecommendFood> foodList2=new ArrayList<>();
        foodList1.add(food01);foodList1.add(food02);foodList1.add(food03);
        foodList2.add(food11);foodList2.add(food12);foodList2.add(food13);foodList2.add(food14);
        lunchs.add(foodList1);lunchs.add(foodList2);
        return lunchs;
    }

    /**
     * 获取推荐的食物(午餐）
     * @return
     */
    public static List<List<RecommendFood>> getSupper(){
        List<List<RecommendFood>> suppers=new ArrayList<>();
        RecommendFood food01=new RecommendFood("米饭","145.0克","168千卡","116","2.6","0.3","25.9","主食类");
        RecommendFood food02=new RecommendFood("菠菜，又叫菠棱菜、赤根菜、波斯草、鹦鹉菜、鼠根菜、角菜","45.0克","13千卡","28","2.6","0.3","4.5","蔬菜菌藻类");
        RecommendFood food03=new RecommendFood("酸奶","121.0克","87千卡","72","2.5","2.7","9.3","高钙-乳制品类");
        RecommendFood food04=new RecommendFood("鸭肉，又叫鸭子、鹜肉、家凫肉","20.0克","48千卡","240","15.5","19.7","0.2","肉类");
        RecommendFood food11=new RecommendFood("米饭","133.0克","154千卡","116","2.6","0.3","25.9","主食类");
        RecommendFood food12=new RecommendFood("猪小排，又叫排骨、猪排","64.0克","178千卡","278","16.7","23.1","0.7","肉类");
        RecommendFood food13=new RecommendFood("小白菜，又叫青菜、油白菜、白菜秧、白菜、杭白菜","52.0克","9千卡","17","1.5","0.3","2.7","蔬菜菌藻类");
        List<RecommendFood> foodList1=new ArrayList<>();List<RecommendFood> foodList2=new ArrayList<>();
        foodList1.add(food01);foodList1.add(food02);foodList1.add(food03);foodList1.add(food04);
        foodList2.add(food11);foodList2.add(food12);foodList2.add(food13);
        suppers.add(foodList1);suppers.add(foodList2);
        return suppers;
    }
}
