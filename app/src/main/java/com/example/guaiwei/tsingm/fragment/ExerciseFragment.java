package com.example.guaiwei.tsingm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.adapter.PlanFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ExerciseFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PlanFragmentAdapter planFragmentAdapter;

    private TabLayout.Tab fitness;
    private TabLayout.Tab foot;
    private TabLayout.Tab run;
    private TabLayout.Tab haoNeng;

    private List<Fragment> fragments;
    private Fragment planFragment;
    private Fragment footFragment;
    private Fragment haoNengFragment;
    private Fragment runFragment;

    private List<String> list_title;

    private View view;


    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_exercise, container, false);
        this.view=view;
        initComponent();
        return view;
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        mTabLayout = (TabLayout) view.findViewById(R.id.exercise_tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.exercise_view_pager);
        //初始化各fragment
        planFragment = new PlanFragment();
        footFragment = new FootFragment();
        haoNengFragment = new HaoNengFragment();
        runFragment=new RunFragment();

        //将fragment装进列表中
        fragments = new ArrayList<Fragment>();
        fragments.add(planFragment);
        fragments.add(footFragment);
        fragments.add(runFragment);
        fragments.add(haoNengFragment);


        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("健身");
        list_title.add("行走");
        list_title.add("跑步");
        list_title.add("耗能");



        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(3)));

        planFragmentAdapter = new PlanFragmentAdapter(getChildFragmentManager(), fragments, list_title);

        //viewpager加载adapter
        mViewPager.setAdapter(planFragmentAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
