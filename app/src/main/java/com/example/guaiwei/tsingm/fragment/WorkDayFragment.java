package com.example.guaiwei.tsingm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.LineChartManager;
import com.example.guaiwei.tsingm.adapter.MotionRrecordsAdapter;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * author hqq 2019.6.27
 * A simple {@link Fragment} subclass.
 */
public class WorkDayFragment extends Fragment {
    private List<String> stringData=new ArrayList<>();
    private List<MotionRecordsEntity> motionRecordsEntities;
    private List<DayPlanInfo> dayPlanInfos;
    private List<String> motionDates=new ArrayList<>();
    private List<String> names=new ArrayList<>();//折线名字集合
    private List<Integer> colors=new ArrayList<>();//折线颜色集合
    private List<Float> datas=new ArrayList<>();//数据集合
    private int id;//数据的索引

    private RecyclerView recordRV;
    private TextView timeTv,countTv,haonengTv;
    private LineChart mLineChart;
    private View view;

    public WorkDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 设置数据
     */
    private void setData() {

    }

    /**
     * 设置折线图的数据
     */
    public void setChartData(){
        motionRecordsEntities=new ArrayList<>();
        names.add("实际耗能");
        names.add("推荐耗能");
        colors.add(getResources().getColor(R.color.colorBlue));
        colors.add(getResources().getColor(R.color.colorGray));
        LineChartManager lineChartManager=new LineChartManager(mLineChart,names,colors);
        lineChartManager.setYAxis(0,10);
        dayPlanInfos=DataSupport.findAll(DayPlanInfo.class);
        motionRecordsEntities=DataSupport.findAll(MotionRecordsEntity.class);//找出所有的记录
        List<String> dates=new ArrayList<>();
        List<Float> values=new ArrayList<>();
        for(DayPlanInfo dayPlanInfo:dayPlanInfos){
            float value=0;
            dates.add(dayPlanInfo.getData().substring(5,10));
            for(MotionRecordsEntity motionRecordsEntity:motionRecordsEntities){
                if(motionRecordsEntity.getData().equals(dayPlanInfo.getData())){
                    value+=(float)motionRecordsEntity.getHaoneng();
                }
            }
            values.add(value);
            if(dayPlanInfo.getData().equals(GetBeforeData.getBeforeData(null,0))){
                break;
            }
        }
        for(int i=0;i<dates.size();i++){
            datas.add(values.get(i));
            datas.add(Float.parseFloat(dayPlanInfos.get(i).getNengliang()));
            lineChartManager.addEntry(datas,dates.get(i));
            datas.clear();
        }
    }

    /**
     * 为recycleView设置适配器
     */
    private void initAdapter() {

        LinearLayoutManager layoutManager1=new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recordRV.setLayoutManager(layoutManager1);
        MotionRrecordsAdapter motionRrecordsAdapter=new MotionRrecordsAdapter(motionRecordsEntities);
        recordRV.setAdapter(motionRrecordsAdapter);
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        recordRV=view.findViewById(R.id.day_record);
        mLineChart = view.findViewById(R.id.day_data);
        countTv=view.findViewById(R.id.day_complete_count);
        timeTv=view.findViewById(R.id.day_time);
        haonengTv=view.findViewById(R.id.day_haoneng);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_work_day, container, false);
        this.view=view;
        initComponent();
        setChartData();
        setData();
        initAdapter();
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() { // 值选择监听器
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                id=(int)e.getX();
            }
            @Override
            public void onNothingSelected() {
                // 未选中
            }
        });
        return view;
    }

}
