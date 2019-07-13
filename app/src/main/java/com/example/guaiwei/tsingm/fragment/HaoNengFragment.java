package com.example.guaiwei.tsingm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.view.ConsumeEnergyView;
import com.example.guaiwei.tsingm.activity.HistoryWorkDataActivity;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 显示消耗能量的fragment
 */
public class HaoNengFragment extends Fragment {
    private ConsumeEnergyView consumeEnergyView;
    private TextView footTv,fitTv,runTv;
    private Button historyBtn;
    private int mProgress;
    private View view;
    private double shijiHaoneng;//实际总耗能
    private double tuijianHaoneng;//推荐总耗能
    private double footHaoneng;//步行耗能
    private double fitHoaneng;//健身耗能
    private double runHaoneng;//跑步耗能
    private ImageView isComplete;
    public HaoNengFragment() {
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
        View view=inflater.inflate(R.layout.fragment_hao_neng, container, false);
        this.view=view;
        shijiHaoneng=0;
        fitHoaneng=0;
        runHaoneng=0;
        mProgress=0;
        initComponent();
        setData();
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyApplication.getContext(),HistoryWorkDataActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        footTv=view.findViewById(R.id.haoneng_foot);
        runTv=view.findViewById(R.id.haoneng_run);
        fitTv=view.findViewById(R.id.haoneng_fit);
        historyBtn=view.findViewById(R.id.checkHistoryData);
        consumeEnergyView=view.findViewById(R.id.circleProgress);
        isComplete=view.findViewById(R.id.iscomplete);
    }

    /**
     * 为各组件设置数据
     */
    private void setData() {
        searchDb();
        mProgress=(int)shijiHaoneng;
        consumeEnergyView.setValueDuringRefresh(mProgress,(int)tuijianHaoneng);
        footTv.setText(footHaoneng+"");
        fitTv.setText(fitHoaneng+"");
        runTv.setText(runHaoneng+"");
        if(shijiHaoneng>tuijianHaoneng){
            isComplete.setVisibility(View.VISIBLE);
        }
        else
            isComplete.setVisibility(View.GONE);
    }

    /**
     *查询数据库中的耗能数据
     */
    private void searchDb(){
        String data=GetBeforeData.getBeforeData(null,0).get(0);
        DayPlanInfo dayPlanInfo=DataSupport.where("data=?",data).find(DayPlanInfo.class).get(0);
        tuijianHaoneng=Double.parseDouble(dayPlanInfo.getNengliang());
        List<MotionRecordsEntity> motionRecordsEntitys=DataSupport.where("data=?",data).find(MotionRecordsEntity.class);
        for(MotionRecordsEntity motionRecordsEntity:motionRecordsEntitys){
            if(motionRecordsEntity.getMovement_type().equals("行走")){
                footHaoneng=motionRecordsEntity.getHaoneng();
                shijiHaoneng+=motionRecordsEntity.getHaoneng();
            }
            else if(motionRecordsEntity.getMovement_type().equals("健身")){
                fitHoaneng=motionRecordsEntity.getHaoneng();
                shijiHaoneng+=motionRecordsEntity.getHaoneng();
            }
            else{
                runHaoneng=motionRecordsEntity.getHaoneng();
                shijiHaoneng+=motionRecordsEntity.getHaoneng();
            }
        }
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
