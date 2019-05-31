package com.example.guaiwei.tsingm.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guaiwei.tsingm.Evaluate.FlexibilityActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.adapter.DataAdapter;
import com.example.guaiwei.tsingm.adapter.PlanAdapter;
import com.example.guaiwei.tsingm.bean.PlanData;
import com.example.guaiwei.tsingm.bean.User_Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * 训练计划的Fragment
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {
    private List<PlanData> planData=new ArrayList<>();//锻炼计划时间列表
    private List<List<String>> plan=new ArrayList<>();//存储每天的运动计划名称的列表
    public Context context;    //活动上下文
    private View view;    //布局
    private PlanAdapter planAdapter;//计划名称列表的适配器
    RecyclerView PlanNameRV;
    public PlanFragment() {
        // Required empty public constructor
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
        initDataAdapter();//初始化时间列表适配器
        initPlanAdapter();//初始化训练名称列表适配器
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
        PlanNameRV=view.findViewById(R.id.exercise_rv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        PlanNameRV.setLayoutManager(layoutManager);
        planAdapter=new PlanAdapter(plan.get(0),User_Plan.userPlan.getDayPlans().get(0));
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
        final DataAdapter dataAdapter=new DataAdapter(planData);
        recyclerView.setAdapter(dataAdapter);
        //设置日期列表的点击事件
        dataAdapter.setOnItemClickListener(new DataAdapter.OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                try{
                    planAdapter=new PlanAdapter(plan.get(pos),User_Plan.userPlan.getDayPlans().get(pos));
                    PlanNameRV.setAdapter(planAdapter);//改变训练名称列表数据源
                    dataAdapter.notifyDataSetChanged();//刷新数据
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 初始化计划时间列表
     */
    private void initPlanData() {
        List<String> StringData=FlexibilityActivity.StringData;
        for(String str:StringData){
            PlanData data=new PlanData();
            String string[]=str.split("-");
            data.setData(string[0]);
            data.setWeek(string[1].substring(2));
            planData.add(data);
        }
    }

    /**
     * 初始化训练计划名称列表
     */
    private void initPlanName() {
        for(int i = 0; i<21; i++){
            List<String> list=new ArrayList<>();
            if(User_Plan.userPlan.getDayPlans().size()<=i){
                list.add("请先完成本周训练");
            }
            else{
                list.add(User_Plan.userPlan.getDayPlans().get(i).getName());
            }
            plan.add(list);
        }
    }
}
