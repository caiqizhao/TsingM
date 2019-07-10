package com.example.guaiwei.tsingm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.gson.User;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 显示计步的fragment
 */
public class FootFragment extends Fragment {
    private TextView bushuTV;//显示步数的TextView
    private TextView footHaoNeng;//显示行走消耗的能量
    private TextView updataTV;//点击更新步数
    private View view;
    User user;
    double haoneng;//步行耗能
    DecimalFormat df;

    public FootFragment() {
        // Required empty public constructor
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        String userData=prefs.getString("user_data",null);
        user=new Gson().fromJson(userData,User.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("footgragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.print("footgragment onCreateView");
        View view=inflater.inflate(R.layout.fragment_foot, container, false);
        this.view=view;
        initComponent();
        setData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.print("footgragment onActivityCreated");

    }

    /**
     * 为各组件设置数据
     */
    private void setData() {
        df = new DecimalFormat("0.0");
        haoneng=getEnergy((int)MainActivity.bushu,user.getWeight(),user.getHeight());
        bushuTV.setText((int)MainActivity.bushu+"");
        footHaoNeng.setText(df.format(haoneng)+"");
        createFootDb();
    }

    /**
     * 生成走路的记录，存入数据库中
     */
    public void createFootDb(){
        List<MotionRecordsEntity> motionRecordsEntitys=DataSupport.where("data=? and movement_type=?",GetBeforeData.getBeforeData(null,0).get(0),"行走").find(MotionRecordsEntity.class);
        System.out.print(motionRecordsEntitys.size()+"");
        if(motionRecordsEntitys.size()==0){
            MotionRecordsEntity motionRecordsEntity=new MotionRecordsEntity();
            motionRecordsEntity.setMovement_content("行走"+(int)MainActivity.bushu+"步");
            motionRecordsEntity.setMovement_type("行走");
            motionRecordsEntity.setHaoneng(Double.parseDouble(df.format(haoneng)));
            motionRecordsEntity.setData(GetBeforeData.getBeforeData(null,0).get(0));
            motionRecordsEntity.save();
        }
        else{
            MotionRecordsEntity motionRecordsEntity=motionRecordsEntitys.get(0);
//            if(MainActivity.bushu==0){
//                MainActivity.bushu+=Double.parseDouble(motionRecordsEntity.getMovement_content().split("\\D+")[0]);
//            }
            motionRecordsEntity.setMovement_content("行走"+(int)MainActivity.bushu+"步");
            motionRecordsEntity.setHaoneng(Double.parseDouble(df.format(haoneng)));
            motionRecordsEntity.save();
        }
    }

    /**
     * 通过步数计算消耗的能量
     */
    public static double getEnergy(int bushu,double weight,double hight){
        return (weight*1.036*(hight-132.1)*Math.pow(10,-5)*bushu)/0.54;
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        bushuTV=view.findViewById(R.id.bushu);
        footHaoNeng=view.findViewById(R.id.foot_nengliang);
        updataTV=view.findViewById(R.id.change_foot);
        updataTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.print("footgragment onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.print("footgragment onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.print("footgragment onDestroyView");
    }
}
