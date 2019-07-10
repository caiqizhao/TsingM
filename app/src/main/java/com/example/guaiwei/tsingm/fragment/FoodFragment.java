package com.example.guaiwei.tsingm.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.activity.SearchFoodActivity;
import com.example.guaiwei.tsingm.utils.DensityUtil;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.adapter.FoodAdapter;
import com.example.guaiwei.tsingm.gson.Nutriment;
import com.example.guaiwei.tsingm.gson.RecommendFood;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.example.guaiwei.tsingm.utils.ToastUtil;
import com.example.guaiwei.tsingm.utils.Util;
import com.example.guaiwei.tsingm.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 饮食Fragment
 * A simple {@link Fragment} subclass.
 * author hqq 2019.6.27
 */
public class FoodFragment extends Fragment implements View.OnClickListener{
    private List<Integer> imageRes=new ArrayList<>();//轮播图的图片资源
    private List<String> titles=new ArrayList<>();//轮播图的标题
    private Boolean isEvaluate;
    private String breakFast1,lunch1,supper1;
    private String breakFast2,lunch2,supper2;
    private List<List<RecommendFood>> breakFasts;//早餐列表
    private List<List<RecommendFood>> lunchs;//午餐列表
    private List<List<RecommendFood>> suppers;//晚餐列表
    public static int i=0,j=0,k=0;
    public static int id=0;//记录现在查看的是哪一餐

    private Banner banner;//播放轮播图的组件
    private Nutriment nutriment;//营养素
    private TextView changeFood;
    private Button createFood;
    private LinearLayout searchViewLL;
    private SharedPreferences prefs;

    private RecyclerView foodRV;//显示食物的列表
    private FoodAdapter foodAdapter;//适配器

    //三餐导航个组件
    private LinearLayout zaocanLL,wucanLL,wangcanLL;
    private ImageView zaocanIV,wucanIV,wangcanIV;
    private TextView zaocanTV,wucanTV,wangcanTV;

    private LinearLayout noFoodLL;
    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;//进度对话框


    private View view;    //布局
    public FoodFragment() {
        // Required empty public constructor
        initBannerData();
    }

    /**
     * 初始化食物列表的数据
     */
    private void initRecommendFoodData() {
        SharedPreferences.Editor editor=prefs.edit();
        Gson gson=new Gson();
        String breakFast1=gson.toJson(Utility.getBreakFast().get(0));
        String breakFast2=gson.toJson(Utility.getBreakFast().get(1));
        String lunch1=gson.toJson(Utility.getLunch().get(0));
        String lunch2=gson.toJson(Utility.getLunch().get(1));
        String supper1=gson.toJson(Utility.getSupper().get(0));
        String supper2=gson.toJson(Utility.getSupper().get(1));
        editor.putString("breakFast1",breakFast1);
        editor.putString("lunch1",lunch1);
        editor.putString("supper1",supper1);
        editor.putString("breakFast2",breakFast2);
        editor.putString("lunch2",lunch2);
        editor.putString("supper2",supper2);
        editor.apply();
    }

    /**
     * 初始化轮播图的数据
     */
    private void initBannerData() {
        imageRes.add(R.drawable.zhifang);
        imageRes.add(R.drawable.dangbaizhi);
        imageRes.add(R.drawable.tanshuihuahewu);
        imageRes.add(R.drawable.reliang);

        prefs=PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        if(prefs.getBoolean("isEvaluate",false)){
            NutrimentInfo nutrimentInfo=DataSupport.where("data=?",GetBeforeData.getBeforeData(null,0).get(0)).find(NutrimentInfo.class).get(0);
            if(nutrimentInfo!=null){
                titles.add("建议每天摄入脂肪："+nutrimentInfo.getFat()+'g');
                titles.add("建议每天摄入的蛋白质："+nutrimentInfo.getProtein()+"g");
                titles.add("建议每天摄入的碳水化合物："+nutrimentInfo.getCarbohydrate()+"g");
                titles.add("建议每天摄入的热量："+nutrimentInfo.getHeat()+"千卡");
            }
        }
        else{
            nutriment=new Gson().fromJson(prefs.getString("nutriment",null),Nutriment.class);
            if(nutriment!=null){
                titles.add("建议每天摄入脂肪："+nutriment.getFat()+'g');
                titles.add("建议每天摄入的蛋白质："+nutriment.getProtein()+"g");
                titles.add("建议每天摄入的碳水化合物："+nutriment.getCarbohydrate()+"g");
                titles.add("建议每天摄入的热量："+nutriment.getHeat()+"千卡");
            }
        }
    }
    /**
     * 初始化列表的适配器
     */
    private void initRecycleView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        foodRV.setLayoutManager(layoutManager);
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        foodRV=view.findViewById(R.id.food_list);
        changeFood=view.findViewById(R.id.change_food);
        createFood=view.findViewById(R.id.create_food);
        noFoodLL=view.findViewById(R.id.no_food);
        relativeLayout=view.findViewById(R.id.change_RL);
        searchViewLL=view.findViewById(R.id.search_ll);
    }

    /**
     * 初始化三餐的导航栏
     */
    private void initNavigationView() {
        zaocanLL=view.findViewById(R.id.zaocan);wucanLL=view.findViewById(R.id.wucan);wangcanLL=view.findViewById(R.id.wancan);
        zaocanIV=view.findViewById(R.id.zaocan_image);wucanIV=view.findViewById(R.id.wucan_image);wangcanIV=view.findViewById(R.id.wancan_image);
        zaocanTV=view.findViewById(R.id.zaocan_text);wucanTV=view.findViewById(R.id.wucan_text);wangcanTV=view.findViewById(R.id.wancan_text);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        banner=view.findViewById(R.id.banner_food);
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader(){

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setImageResource((Integer)path);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.RIGHT);//设置指示器位置
        banner.setDelayTime(3000);//设置轮播时间
        banner.setImages(imageRes);//设置图片源
        banner.setBannerTitles(titles);//设置标题源
        banner.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_food, container, false);
        this.view=view;
        initBanner();
        initNavigationView();
        initComponent();
        initRecycleView();
        zaocanLL.setOnClickListener(this);
        wucanLL.setOnClickListener(this);
        wangcanLL.setOnClickListener(this);
        searchViewLL.setOnClickListener(this);
        createFood.setOnClickListener(this);
        changeFood.setOnClickListener(this);
        if (id==0){
            zaocanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
            zaocanIV.setImageResource(R.drawable.zaocan_after);
        }
        if (id==1){
            wucanIV.setImageResource(R.drawable.wucan_after);
            wucanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
        }
        if (id==2){
            wangcanIV.setImageResource(R.drawable.wancan_after);
            wangcanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
        }
        isEvaluate=prefs.getBoolean("isEvaluate",false);
        getPrefs();
//        recommendFoods=DataSupport.where("data=?",GetBeforeData.getBeforeData(null,0).get(0)).find(RecommendFood.class);
        if(breakFasts.size()==0){
            noFoodView();
        }
        else{
            haveFoodView();
            if(id==0){
                foodAdapter=new FoodAdapter(breakFasts.get(i%2));
                foodRV.setAdapter(foodAdapter);
            }
            if (id==1){
                foodAdapter = new FoodAdapter(lunchs.get(j%2));
                foodRV.setAdapter(foodAdapter);
            }
            if (id==2){
                foodAdapter = new FoodAdapter(suppers.get(k%2));
                foodRV.setAdapter(foodAdapter);
            }
        }
//        if(!isEvaluate&&recommendFood!=null){
//            haveFoodView();
//        }
//        if(isEvaluate&&recommendFoods.size()==0){
//            noFoodView();
//        }
//        if(isEvaluate&&recommendFoods.size()!=0){
//            haveFoodView();
//        }
        return view;
    }

    /**
     * 获取SharedPreferences中的食物数据
     */
    private void getPrefs(){
        breakFasts=new ArrayList<>();
        lunchs=new ArrayList<>();
        suppers=new ArrayList<>();
        breakFast1=prefs.getString("breakFast1",null);
        lunch1=prefs.getString("lunch1",null);
        supper1=prefs.getString("supper1",null);
        breakFast2=prefs.getString("breakFast2",null);
        lunch2=prefs.getString("lunch2",null);
        supper2=prefs.getString("supper2",null);
        if(breakFast1!=null){
            Gson gson=new Gson();
            List<RecommendFood> bf1=gson.fromJson(breakFast1,new TypeToken<List<RecommendFood>>(){}.getType());
            List<RecommendFood>bf2=gson.fromJson(breakFast2,new TypeToken<List<RecommendFood>>(){}.getType());
            breakFasts.add(bf1);
            breakFasts.add(bf2);
            List<RecommendFood> l1=gson.fromJson(lunch1,new TypeToken<List<RecommendFood>>(){}.getType());
            List<RecommendFood> l2=gson.fromJson(lunch2,new TypeToken<List<RecommendFood>>(){}.getType());
            lunchs.add(l1);
            lunchs.add(l2);
            List<RecommendFood> s1=gson.fromJson(supper1,new TypeToken<List<RecommendFood>>(){}.getType());
            List<RecommendFood> s2=gson.fromJson(supper2,new TypeToken<List<RecommendFood>>(){}.getType());
            suppers.add(s1);
            suppers.add(s2);
        }
    }

    /**
     * 设置没有推荐食物时界面的显示
     */
    public void noFoodView(){
        //显示没有推荐食物的布局
        noFoodLL.setVisibility(View.VISIBLE);
        //隐藏有食物的布局
        relativeLayout.setVisibility(View.GONE);
        foodRV.setVisibility(View.GONE);
    }

    /**
     * 设置有食物的布局
     */
    public void haveFoodView(){
        //隐藏没有推荐食物的布局
        noFoodLL.setVisibility(View.GONE);
        //显示有食物的布局
        relativeLayout.setVisibility(View.VISIBLE);
        foodRV.setVisibility(View.VISIBLE);
    }

    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
        zaocanIV.setImageResource(R.drawable.zaocan_before);
        wucanIV.setImageResource(R.drawable.wucan_before);
        wangcanIV.setImageResource(R.drawable.wancan_before);
        zaocanTV.setTextColor(getContext().getResources().getColor(R.color.colorGray));
        wucanTV.setTextColor(getContext().getResources().getColor(R.color.colorGray));
        wangcanTV.setTextColor(getContext().getResources().getColor(R.color.colorGray));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zaocan:
                clearChioce();
                zaocanIV.setImageResource(R.drawable.zaocan_after);
                zaocanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
                if(breakFasts.size()!=0){
                    foodAdapter=new FoodAdapter(breakFasts.get(i%2));
                    foodRV.setAdapter(foodAdapter);
                }
                id=0;
                break;
            case R.id.wucan:
                clearChioce();
                wucanIV.setImageResource(R.drawable.wucan_after);
                wucanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
                if (lunchs.size()!=0) {
                    foodAdapter = new FoodAdapter(lunchs.get(j%2));
                    foodRV.setAdapter(foodAdapter);
                }
                id=1;
                break;
            case R.id.wancan:
                clearChioce();
                wangcanIV.setImageResource(R.drawable.wancan_after);
                wangcanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
                if (suppers.size()!=0){
                    foodAdapter=new FoodAdapter(suppers.get(k%2));
                    foodRV.setAdapter(foodAdapter);
                }
                id=2;
                break;
            case R.id.change_food:
//                showProgressDialog();
                //换一批食物点击事件（未完）

                if(id==0){
                    i++;
                    foodAdapter=new FoodAdapter(breakFasts.get(i%2));
                    foodRV.setAdapter(foodAdapter);
                }
                if (id==1){
                    j++;
                    foodAdapter = new FoodAdapter(lunchs.get(j%2));
                    foodRV.setAdapter(foodAdapter);
                }
                if (id==2){
                    k++;
                    foodAdapter = new FoodAdapter(suppers.get(k%2));
                    foodRV.setAdapter(foodAdapter);
                }
//                closeProgressDialog();
                break;
            case R.id.create_food:
//                showProgressDialog();
                //生成食物
                initRecommendFoodData();
                haveFoodView();
                getPrefs();
                if(id==0){
                    foodAdapter=new FoodAdapter(breakFasts.get(i%2));
                    foodRV.setAdapter(foodAdapter);
                }
                if (id==1){
                    foodAdapter = new FoodAdapter(lunchs.get(j%2));
                    foodRV.setAdapter(foodAdapter);
                }
                if (id==2){
                    foodAdapter = new FoodAdapter(suppers.get(k%2));
                    foodRV.setAdapter(foodAdapter);
                }
//                if(breakFasts!=null){
//                    foodAdapter=new FoodAdapter(breakFasts.get(i%2));
//                    foodRV.setAdapter(foodAdapter);
//                }
//                closeProgressDialog();
                break;
            case R.id.search_ll:
                Intent intent=new Intent(view.getContext(),SearchFoodActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    /**
     * 显示进度对话框
     */
    private  void  showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(MyApplication.getContext());
            progressDialog.setMessage("请稍等...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void  closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
