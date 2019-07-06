package com.example.guaiwei.tsingm.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.activity.WorkDataActivity;
import com.example.guaiwei.tsingm.gson.User;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 用户基本信息Fragment
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private TextView heightTV,weightTV,timeCountTV,weekTimeCountTV;//身高体重的文本框
    private RelativeLayout workDataRL,bodyDataRL;
    private ImageView headImage;//头像
    private View view;

    public MyFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponent();
        setData();
        setOnClickListeners();
    }

    /**
     * 为组件设置点击事件
     */
    private void setOnClickListeners() {
        workDataRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WorkDataActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置各组件的数据
     */
    private void setData() {
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        String userData=prefs.getString("user_data",null);
        User user=new Gson().fromJson(userData,User.class);
        if(user!=null){
            heightTV.setText(user.getHeight()+"");
            weightTV.setText(user.getWeight()+"");
        }
        headImage.setImageResource(R.mipmap.head);
        List<MotionRecordsEntity> motionRecordsEntitys=DataSupport.findAll(MotionRecordsEntity.class);
        int countTime=0;double countHaoNeng=0.0;
        if(motionRecordsEntitys!=null){
            for(MotionRecordsEntity motionRecordsEntity:motionRecordsEntitys){
                if(motionRecordsEntity.getMovement_type().equals("健身")){
                    String timeStr= motionRecordsEntity.getTime();
                    String [] timeS=timeStr.split(":");
                    int time=Integer.parseInt(timeS[0])+1;
                    countTime+=time;
                    double haoNeng=motionRecordsEntity.getHaoneng();
                    countHaoNeng+=haoNeng;
                }
            }
        }
        timeCountTV.setText(countTime+"");
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        heightTV=view.findViewById(R.id.height);
        weightTV=view.findViewById(R.id.weight);
        timeCountTV=view.findViewById(R.id.time_count);
        weekTimeCountTV=view.findViewById(R.id.week_time_count);

        bodyDataRL=view.findViewById(R.id.my_body_data);
        workDataRL=view.findViewById(R.id.work_data);

        headImage=view.findViewById(R.id.head_image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my, container, false);
        this.view=view;
        return view;
    }
}
