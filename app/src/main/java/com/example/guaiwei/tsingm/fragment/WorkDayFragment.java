package com.example.guaiwei.tsingm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.activity.HistoryWorkDataActivity;
import com.example.guaiwei.tsingm.activity.WorkDataActivity;
import com.example.guaiwei.tsingm.adapter.FoodRecordAdapter;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.example.guaiwei.tsingm.db.RecommendFood;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.LineChartManager;
import com.example.guaiwei.tsingm.adapter.MotionRrecordsAdapter;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 饮食和运动数据记录的界面
 * author hqq 2019.6.27
 * A simple {@link Fragment} subclass.
 */
public class WorkDayFragment extends Fragment {
    private List<MotionRecordsEntity> motionRecordsEntities;//所有的运动数据记录
    private List<RecommendFood> allRecommendFoods;//所有的饮食数据记录
    private List<MotionRecordsEntity> dayRecords;//某一天的运动数据记录
    private List<RecommendFood> breakFasts;//早餐记录
    private List<RecommendFood> lunchs;//午餐记录‘
    private List<RecommendFood> suppers;//晚餐记录
    private List<DayPlanInfo> dayPlanInfos;
    private List<NutrimentInfo> nutrimentInfos;
    private List<String> names;//折线名字集合
    private List<Integer> colors;//折线颜色集合
    private List<Float> datas;//数据集合
    private List<String> dates;//日期集合
    private List<Float> values;//纵坐标的数据集合
    private int id=0;//数据的索引
    private int type;//判断是饮食记录还是运动记录

    private RecyclerView recordRV,breakfastRv,lunchRv,supperRv;
    private TextView timeTv,countTv,haonengTv,dayDate,energyTv,noDataTv;
    private LinearLayout sportText1,foodLL,breakfastLL,lunchLL,supperLL;
    private LineChart mLineChart;
    private View view;

    public WorkDayFragment() {
        // Required empty public constructor
        type=HistoryWorkDataActivity.type;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 设置运动记录数据
     */
    private void setSportData() {
        dayRecords=new ArrayList<>();
        int countTime=0;//运动时间
        if(id>=0){
            dayDate.setText(dates.get(id));
            for(MotionRecordsEntity motionRecordsEntity:motionRecordsEntities){
                String data=motionRecordsEntity.getData();
                if (dayPlanInfos.get(id).getData().equals(data)){
                    dayRecords.add(motionRecordsEntity);
                    if (motionRecordsEntity.getTime()!=null) {
                        countTime+=GetBeforeData.transforTime(motionRecordsEntity.getTime());
                    }
                }
            }
            timeTv.setText(countTime+"");
            DecimalFormat df=new DecimalFormat("0.0");
            haonengTv.setText(df.format(values.get(id)));
            float complete=values.get(id)/Float.parseFloat(dayPlanInfos.get(id).getNengliang());
            countTv.setText(df.format(complete*100));
        }
    }

    /**
     * 设置饮食记录数据
     */
    private void setFoodData() {
        breakFasts=new ArrayList<>();
        lunchs=new ArrayList<>();
        suppers=new ArrayList<>();
        if(id>=0){
            for (RecommendFood recommendFood:allRecommendFoods){
                if (recommendFood.getData().equals(nutrimentInfos.get(id).getData())){
                    if(recommendFood.getKind().equals("早餐")){
                        breakFasts.add(recommendFood);
                    }
                    else if (recommendFood.getKind().equals("午餐")){
                        lunchs.add(recommendFood);
                    }
                    else {
                        suppers.add(recommendFood);
                    }
                }
            }
            DecimalFormat df=new DecimalFormat("0.0");
            haonengTv.setText(df.format(values.get(id)));
            float complete=values.get(id)/(float)(nutrimentInfos.get(id).getHeat());
            countTv.setText(df.format(complete*100));
        }
    }

    /**
     * 设置运动记录折线图的数据
     */
    public void setSportChartData(){
        motionRecordsEntities=new ArrayList<>();
        dayPlanInfos=new ArrayList<>();
        names=new ArrayList<>();
        colors=new ArrayList<>();
        datas=new ArrayList<>();
        names.add("实际耗能");
        names.add("推荐耗能");
        colors.add(getResources().getColor(R.color.colorBlue));
        colors.add(getResources().getColor(R.color.colorGray));
        LineChartManager lineChartManager=new LineChartManager(mLineChart,names,colors);
        lineChartManager.setYAxis(0,10);
        dayPlanInfos=DataSupport.findAll(DayPlanInfo.class);
        motionRecordsEntities=DataSupport.findAll(MotionRecordsEntity.class);//找出所有的运动记录
        dates=new ArrayList<>();
        values=new ArrayList<>();
        for(DayPlanInfo dayPlanInfo:dayPlanInfos){
            float value=0;
            String date[]=dayPlanInfo.getData().substring(5,10).split("-");
            dates.add(date[0]+"月"+date[1]+"日");
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

    private void setFoodChartData(){
        allRecommendFoods=new ArrayList<>();
        nutrimentInfos=new ArrayList<>();
        names=new ArrayList<>();
        colors=new ArrayList<>();
        datas=new ArrayList<>();
        names.add("实际摄入");
        names.add("推荐摄入");
        colors.add(getResources().getColor(R.color.colorBlue));
        colors.add(getResources().getColor(R.color.colorGray));
        LineChartManager lineChartManager=new LineChartManager(mLineChart,names,colors);
        lineChartManager.setYAxis(0,10);
        allRecommendFoods=DataSupport.findAll(RecommendFood.class);//找出所有的饮食记录
        nutrimentInfos=DataSupport.findAll(NutrimentInfo.class);
        dates=new ArrayList<>();
        values=new ArrayList<>();
        for(NutrimentInfo nutrimentInfo:nutrimentInfos){
            float value=0;
            String date[]=nutrimentInfo.getData().substring(5,10).split("-");
            dates.add(date[0]+"月"+date[1]+"日");
            for(RecommendFood recommendFood:allRecommendFoods){
                if(recommendFood.getData().equals(nutrimentInfo.getData())){
                    value+=Float.parseFloat(recommendFood.getEnergy().replaceAll("[^0-9.]",""));
                }
            }
            values.add(value);
            if(nutrimentInfo.getData().equals(GetBeforeData.getBeforeData(null,0))){
                break;
            }
        }
        for(int i=0;i<dates.size();i++){
            datas.add(values.get(i));
            datas.add((float)nutrimentInfos.get(i).getHeat());
            lineChartManager.addEntry(datas,dates.get(i));
            datas.clear();
        }
    }

    /**
     * 为运动记录recycleView设置适配器
     */
    private void initSportAdapter() {
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recordRV.setLayoutManager(layoutManager1);
        MotionRrecordsAdapter motionRrecordsAdapter=new MotionRrecordsAdapter(dayRecords);
        recordRV.setAdapter(motionRrecordsAdapter);
    }

    /**
     * 为饮食记录recycleView设置适配器
     */
    private void initFoodAdapter() {
        if(breakFasts.size()==0){
            breakfastLL.setVisibility(View.GONE);
            breakfastRv.setVisibility(View.GONE);
        }
        else {
            breakfastLL.setVisibility(View.VISIBLE);
            breakfastRv.setVisibility(View.VISIBLE);
            LinearLayoutManager breafastLayoutManager=new LinearLayoutManager(getContext());
            breafastLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            breakfastRv.setLayoutManager(breafastLayoutManager);
            FoodRecordAdapter adapter=new FoodRecordAdapter(breakFasts);
            breakfastRv.setAdapter(adapter);
        }
        if(lunchs.size()==0){
            lunchLL.setVisibility(View.GONE);
            lunchRv.setVisibility(View.GONE);
        }
        else {
            lunchLL.setVisibility(View.VISIBLE);
            lunchRv.setVisibility(View.VISIBLE);
            LinearLayoutManager lunchLayoutManger=new LinearLayoutManager(getContext());
            lunchLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);
            lunchRv.setLayoutManager(lunchLayoutManger);
            FoodRecordAdapter adapter=new FoodRecordAdapter(lunchs);
            lunchRv.setAdapter(adapter);
        }
        if(suppers.size()==0){
            supperLL.setVisibility(View.GONE);
            supperRv.setVisibility(View.GONE);
        }
        else {
            supperLL.setVisibility(View.VISIBLE);
            supperRv.setVisibility(View.VISIBLE);
            LinearLayoutManager supperLayoutManger=new LinearLayoutManager(getContext());
            supperLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);
            supperRv.setLayoutManager(supperLayoutManger);
            FoodRecordAdapter adapter=new FoodRecordAdapter(breakFasts);
            supperRv.setAdapter(adapter);
        }
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
        dayDate=view.findViewById(R.id.day_date);
        sportText1=view.findViewById(R.id.sport_text1);
        noDataTv=view.findViewById(R.id.no_data);
        energyTv=view.findViewById(R.id.energy_tv);
        breakfastRv=view.findViewById(R.id.breakfast_record);
        lunchRv=view.findViewById(R.id.lunch_record);
        supperRv=view.findViewById(R.id.supper_record);
        foodLL=view.findViewById(R.id.food_record);
        breakfastLL=view.findViewById(R.id.breakfast);
        lunchLL=view.findViewById(R.id.lunch);
        supperLL=view.findViewById(R.id.supper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_work_day, container, false);
        this.view=view;
        initComponent();
        if (type==0){
            sportView();
            setSportChartData();
            id=dates.size()-1;
            setSportData();
            initSportAdapter();
        }
        else {
            foodView();
            setFoodChartData();
            id=dates.size()-1;
            setFoodData();
            initFoodAdapter();
        }

        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() { // 值选择监听器
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                id=(int)e.getX();
                if (type==0){
                    dayRecords.clear();
                    setSportData();
                    initSportAdapter();
                }
                else {
                    breakFasts.clear();
                    lunchs.clear();
                    suppers.clear();
                    setFoodData();
                    initFoodAdapter();
                }
            }
            @Override
            public void onNothingSelected() {
                // 未选中
            }
        });
        System.out.print("WorkdayFragmentCreatView");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mLineChart=null;
        dates.clear();
        values.clear();
        colors.clear();
        names.clear();
        System.out.print("WorkdayFragmentDestroyView");
    }

    /**
     * 运动记录视图
     */
    private void sportView(){
        foodLL.setVisibility(View.GONE);
        sportText1.setVisibility(View.VISIBLE);
        timeTv.setVisibility(View.VISIBLE);
        energyTv.setText("消耗（千卡）");
    }

    /**
     * 食物记录视图
     */
    private void foodView(){
        foodLL.setVisibility(View.VISIBLE);
        sportText1.setVisibility(View.GONE);
        timeTv.setVisibility(View.GONE);
        energyTv.setText("摄入（千卡）");
    }
}
