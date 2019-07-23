package com.example.guaiwei.tsingm.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.fragment.WorkCountFragment;
import com.example.guaiwei.tsingm.fragment.WorkDayFragment;
import com.example.guaiwei.tsingm.fragment.WorkMonthFragment;
import com.example.guaiwei.tsingm.fragment.WorkWeekFragment;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.adapter.MyFragmentPagerAdapter;
import com.example.guaiwei.tsingm.gson.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryWorkDataActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab workDay;
    private TabLayout.Tab workWeek;
    private TabLayout.Tab workMonth;
    private TabLayout.Tab workCount;

    private List<Fragment> fragments;
    private Fragment dayFragment;
    private Fragment weekFragment;
    private Fragment monthFragment;
    private Fragment countFragment;
    private List<String> list_title;
    public static int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_work_data);
        type=getIntent().getIntExtra("type",0);
        initComponent();
        ChangeColor.changeColor(this,Color.parseColor("#584f60"));
    }


    private void initComponent() {
        mViewPager=(ViewPager)findViewById(R.id.view_pager);
        mTabLayout=(TabLayout)findViewById(R.id.tab_layout);

        //初始化各fragment
        dayFragment = new WorkDayFragment();
        weekFragment = new WorkWeekFragment();
        monthFragment = new WorkMonthFragment();
        countFragment=new WorkCountFragment();

        //将fragment装进列表中
        fragments = new ArrayList<Fragment>();
        fragments.add(dayFragment);
        fragments.add(weekFragment);
        fragments.add(monthFragment);
        fragments.add(countFragment);


        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("日");
        list_title.add("周");
        list_title.add("月");
        list_title.add("总");



        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(3)));

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, list_title);

        //viewpager加载adapter
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
