package com.example.guaiwei.tsingm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guaiwei.tsingm.Evaluate.FlexibilityActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.Utils.GetBeforeData;
import com.example.guaiwei.tsingm.adapter.DataAdapter;
import com.example.guaiwei.tsingm.adapter.PlanAdapter;
import com.example.guaiwei.tsingm.bean.PlanData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {
    private List<PlanData> planData=new ArrayList<>();
    private List<List<String>> plan=new ArrayList<>();//存储运动计划的列表
    private PlanAdapter adapterV;
    RecyclerView recyclerViewVercial;
    public PlanFragment() {
        // Required empty public constructor
        initPlanData();
        for(int i=0;i<21;i++){
            List<String> list=new ArrayList<>();
            list.add("何青青"+i);
            list.add("劳茜"+i);
            plan.add(list);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan, container, false);

        RecyclerView recyclerView=view.findViewById(R.id.plan_rv);//横向的日期选择列表
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        final DataAdapter adapter=new DataAdapter(planData);
        recyclerView.setAdapter(adapter);
        //每个横向的日期对应的纵向列表
        recyclerViewVercial=view.findViewById(R.id.exercise_rv);
        LinearLayoutManager layoutManagerV=new LinearLayoutManager(this.getContext());
        layoutManagerV.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewVercial.setLayoutManager(layoutManagerV);
        adapterV=new PlanAdapter(plan.get(0));
        recyclerViewVercial.setAdapter(adapterV);
        // Inflate the layout for this fragment
        adapter.setOnItemClickListener(new DataAdapter.OnRecycleItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                adapterV=new PlanAdapter(plan.get(pos));
                recyclerViewVercial.setAdapter(adapterV);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

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

}
