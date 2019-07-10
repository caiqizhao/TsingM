package com.example.guaiwei.tsingm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.activity.HistoryWorkDataActivity;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.activity.RunActivity;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 跑步
 */
public class RunFragment extends Fragment {
    private View view;
    private Button startRunBtn;
    private TextView runEnergyTv,instanceTv;
    private double instance=0.0;
    private double energy=0.0;

    public RunFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_run, container, false);
        this.view=view;
        initComponent();
        setData();
        startRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyApplication.getContext(),RunActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    /**
     * 初始化各组件
     */
    private void initComponent() {
        startRunBtn=view.findViewById(R.id.start_run);
        runEnergyTv=view.findViewById(R.id.run_energy);
        instanceTv=view.findViewById(R.id.run_instance);
    }

    /**
     * 为各组件设置数据
     */
    private void setData() {
        DecimalFormat df=new DecimalFormat("0.0");
        searchDb();
        instanceTv.setText(instance+"");
        runEnergyTv.setText(df.format(energy));
    }

    /**
     * 查询数据库中的跑步的记录
     */
    private void searchDb(){
        List<MotionRecordsEntity> motionRecordsEntities=DataSupport.where("data=? and movement_type=?",GetBeforeData.getBeforeData(null,0).get(0),"跑步").find(MotionRecordsEntity.class);
        for(MotionRecordsEntity motionRecordsEntity:motionRecordsEntities){
            String str=motionRecordsEntity.getMovement_content();
            instance+=Double.parseDouble(str.replaceAll("[^0-9.]", ""));
            energy+=motionRecordsEntity.getHaoneng();
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
