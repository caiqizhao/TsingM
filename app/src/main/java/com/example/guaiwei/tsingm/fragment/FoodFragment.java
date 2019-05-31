package com.example.guaiwei.tsingm.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.adapter.FoodAdapter;
import com.example.guaiwei.tsingm.bean.Nutriment;
import com.example.guaiwei.tsingm.bean.RecommendFood;
import com.example.guaiwei.tsingm.question.CoefActivity;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 饮食Fragment
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment implements View.OnClickListener{
    private List<Integer> imageRes=new ArrayList<>();//轮播图的图片资源
    List<String> titles=new ArrayList<>();//轮播图的标题
    private Banner banner;//播放轮播图的组件

    private TextView tishiTV;//提示的文本框
    private String tishi[]={"一天之计在于晨，人体经过一夜的消耗，身体需要科学的能量搭配来开始全新的一天。早餐建议以适量的蛋白质，少量的主食为主,最好能再摄入些蔬菜。"
            ,"经过一上午的消耗，还要为下午工作做储备，建议摄入适量的主食，同时摄入较多的蛋白质来为身体持续功能并延长饥饿感到来，油脂摄入量稍加注意。"
            ,"晚餐一定要吃， 因为你可能在接下来的12个小时内都不再进食，主食以地瓜，杂粮饭这种粗粮为主，蛋白质要大量摄入，蔬菜也要多吃以保证维生素的摄入。"};

    List<List<RecommendFood>> recommendFoods=new ArrayList<>();//推荐食物的列表
    private RecyclerView foodRV;//显示食物的列表
    FoodAdapter foodAdapter;//适配器

    //三餐导航个组件
    private LinearLayout zaocanLL,wucanLL,wangcanLL;
    private ImageView zaocanIV,wucanIV,wangcanIV;
    private TextView zaocanTV,wucanTV,wangcanTV;

    private View view;    //布局
    public FoodFragment() {
        // Required empty public constructor
        initBannerData();
        initRecycleViewData();
    }

    /**
     * 初始化食物列表的数据
     */
    private void initRecycleViewData() {
        List<RecommendFood> zaocan=new ArrayList<>();
        RecommendFood zaocan1=new RecommendFood();zaocan1.setFoodName("玉米");zaocan1.setFoodReLiang("127千卡");zaocan1.setFoodG("144g");
        RecommendFood zaocan2=new RecommendFood();zaocan2.setFoodName("鸡蛋");zaocan2.setFoodReLiang("71千卡");zaocan2.setFoodG("53g");
        RecommendFood zaocan3=new RecommendFood();zaocan3.setFoodName("脱脂牛奶");zaocan3.setFoodReLiang("99千卡");zaocan3.setFoodG("301g");
        zaocan.add(zaocan1);zaocan.add(zaocan2);zaocan.add(zaocan3);
        List<RecommendFood> wucan=new ArrayList<>();
        RecommendFood wucan1=new RecommendFood();wucan1.setFoodName("红薯");wucan1.setFoodReLiang("226千卡");wucan1.setFoodG("247g");
        RecommendFood wucan2=new RecommendFood();wucan2.setFoodName("牛肉");wucan2.setFoodReLiang("128千卡");wucan2.setFoodG("56g");
        RecommendFood wucan3=new RecommendFood();wucan3.setFoodName("菠菜");wucan3.setFoodReLiang("19千卡");wucan3.setFoodG("91g");
        wucan.add(wucan1);wucan.add(wucan2);wucan.add(wucan3);
        List<RecommendFood> wangcang=new ArrayList<>();
        RecommendFood wangcang1=new RecommendFood();wangcang1.setFoodName("玉米");wangcang1.setFoodReLiang("191千卡");wangcang1.setFoodG("202g");
        RecommendFood wangcang2=new RecommendFood();wangcang2.setFoodName("牛肉");wangcang2.setFoodReLiang("111千卡");wangcang2.setFoodG("105g");
        RecommendFood wangcang3=new RecommendFood();wangcang3.setFoodName("菠菜");wangcang3.setFoodReLiang("19千卡");wangcang3.setFoodG("91g");
        wangcang.add(wangcang1);wangcang.add(wangcang2);wangcang.add(wangcang3);
        recommendFoods.add(zaocan);recommendFoods.add(wucan);recommendFoods.add(wangcang);
    }

    /**
     * 初始化轮播图的数据
     */
    private void initBannerData() {
        imageRes.add(R.drawable.zhifang);
        imageRes.add(R.drawable.dangbaizhi);
        imageRes.add(R.drawable.tanshuihuahewu);
        imageRes.add(R.drawable.reliang);
        titles.add("建议每天摄入脂肪："+Nutriment.nutriment.getFat()+'g');
        titles.add("建议每天摄入的蛋白质："+Nutriment.nutriment.getProtein()+"g");
        titles.add("建议每天摄入的碳水化合物："+Nutriment.nutriment.getCarbohydrate()+"g");
        titles.add("建议每天摄入的热量："+Nutriment.nutriment.getHeat()+"千卡");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBanner();
        initNavigationView();
        initComponent();
        initRecycleView();
        zaocanLL.setOnClickListener(this);
        wucanLL.setOnClickListener(this);
        wangcanLL.setOnClickListener(this);
        zaocanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
        zaocanIV.setImageResource(R.drawable.zaocan_after);
    }

    /**
     * 初始化列表的适配器
     */
    private void initRecycleView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        foodRV.setLayoutManager(layoutManager);
        foodAdapter=new FoodAdapter(recommendFoods.get(0));
        foodRV.setAdapter(foodAdapter);
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        tishiTV=view.findViewById(R.id.tishi_tv);
        foodRV=view.findViewById(R.id.food_list);
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
        return view;
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
                foodAdapter=new FoodAdapter(recommendFoods.get(0));
                foodRV.setAdapter(foodAdapter);
                tishiTV.setText(tishi[0]);
                break;
            case R.id.wucan:
                clearChioce();
                wucanIV.setImageResource(R.drawable.wucan_after);
                wucanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
                foodAdapter=new FoodAdapter(recommendFoods.get(1));
                foodRV.setAdapter(foodAdapter);
                tishiTV.setText(tishi[1]);
                break;
            case R.id.wancan:
                clearChioce();
                wangcanIV.setImageResource(R.drawable.wancan_after);
                wangcanTV.setTextColor(getContext().getResources().getColor(R.color.colorFood));
                foodAdapter=new FoodAdapter(recommendFoods.get(2));
                foodRV.setAdapter(foodAdapter);
                tishiTV.setText(tishi[2]);
                break;
            default:
                break;
        }
    }
}
