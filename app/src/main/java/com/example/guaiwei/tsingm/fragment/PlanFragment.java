package com.example.guaiwei.tsingm.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.adapter.DataAdapter;
import com.example.guaiwei.tsingm.adapter.PlanAdapter;
import com.example.guaiwei.tsingm.gson.PlanData;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 训练计划的Fragment
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {
    private List<String> stringData=new ArrayList<>();
    private List<PlanData> planDatas=new ArrayList<>();//锻炼计划时间列表
    private List<List<String>> plan=new ArrayList<>();//存储每天的运动计划名称的列表
    private List<DayPlanInfo> dayPlanInfo;
    public Context context;    //活动上下文
    private View view;    //布局
    private PlanAdapter planAdapter;//计划名称列表的适配器
    RecyclerView PlanNameRV;
    private TextView geshuTV,reliangTV;


    private TextView textView;
    private CardView cardView;
    private LinearLayout linearLayout;

    int i=0;
    public PlanFragment() {
        initPlanData();//初始化时间列表
        initPlanName();//初始化训练计划名称列表

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initComponent();//初始化各控件
        setData();//初始化效果模块的数据
        initDataAdapter();//初始化时间列表适配器
        initPlanAdapter();//初始化训练名称列表适配器
    }

    /**
     * 初始化各控件
     */
    private void initComponent() {
        geshuTV=view.findViewById(R.id.geshu);
        reliangTV=view.findViewById(R.id.reliang);
        textView=view.findViewById(R.id.text3);
        cardView=view.findViewById(R.id.card);
        linearLayout=view.findViewById(R.id.ll_1);
    }

    /**
     * 初始化效果模块的数据
     */
    private void setData() {
        PlanNameRV=view.findViewById(R.id.exercise_rv);
        geshuTV.setText(dayPlanInfo.get(i).getCountAction()+"");
        reliangTV.setText(dayPlanInfo.get(i).getNengliang()+"");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan, container, false);
        this.view=view;
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * 初始化训练名称列表适配器
     */
    private void initPlanAdapter() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        PlanNameRV.setLayoutManager(layoutManager);
        planAdapter=new PlanAdapter(plan.get(0),dayPlanInfo.get(0));
        PlanNameRV.setAdapter(planAdapter);
    }

    /**
     * 初始化时间列表适配器
     */
    private void initDataAdapter() {
        RecyclerView recyclerView=view.findViewById(R.id.plan_rv);//横向的日期选择列表
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        final DataAdapter dataAdapter=new DataAdapter(planDatas);
        recyclerView.setAdapter(dataAdapter);
        //设置日期列表的点击事件
        dataAdapter.setOnItemClickListener(new DataAdapter.OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                try{
                    //有训练计划时设置一下组件可见
                    PlanNameRV.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    //没有时设置一下不可见
                    linearLayout.setVisibility(View.GONE);

                    planAdapter=new PlanAdapter(plan.get(pos),dayPlanInfo.get(pos));
                    PlanNameRV.setAdapter(planAdapter);//改变训练名称列表数据源
                    dataAdapter.notifyDataSetChanged();//刷新数据
                    i=pos;
                    setData();
                }
                catch (Exception e){
                    //没有训练计划时设置一下组件可见
                    linearLayout.setVisibility(View.VISIBLE);
                    //有时设置以下不可见
                    PlanNameRV.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        });
        return view;
    }


    /**
     * 初始化计划时间列表
     */
    private void initPlanData() {
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        String planDataStr=pref.getString("plan_data",null);
        Gson gson=new Gson();
        stringData=gson.fromJson(planDataStr,new TypeToken<List<String>>(){}.getType());
        for (String strData : stringData) {
            PlanData planData = new PlanData();
            String string[] = strData.split("-");
            planData.setData(string[2]);
            planData.setWeek(string[3].substring(2));
            planDatas.add(planData);
        }
    }

    /**
     * 初始化训练计划名称列表
     */
    private void initPlanName() {
        dayPlanInfo=DataSupport.findAll(DayPlanInfo.class);
        for(int i = 0; i<21; i++){
            List<String> list=new ArrayList<>();
            if(dayPlanInfo.size()<=i){
                list.add("请先完成本周训练");
            }
            else{
                list.add(dayPlanInfo.get(i).getName());
            }
            plan.add(list);
        }
    }
}
