package com.example.guaiwei.tsingm.Evaluate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.ToastUtil;
import com.example.guaiwei.tsingm.utils.Utility;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.gson.User;
import com.example.guaiwei.tsingm.service.PlanService;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 判断用户坐姿体前屈的程度的界面，测试用户的髋关节柔韧性
 */
public class FlexibilityActivity extends BaseActivity {
    private Button submitButton;//提交按钮
    private RadioGroup FRadio;//判断用户下肢耐力问题的单选按钮组
    private ImageView flexibilityIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexibility);

        //获取相应的控件
        submitButton=(Button)findViewById(R.id.evaluate_complete);
        FRadio=(RadioGroup) findViewById(R.id.radio_flexibility);
        flexibilityIV=(ImageView)findViewById(R.id.flexibility_image);


        //设置按钮为不可点击
        submitButton.setEnabled(false);
        submitButton.setAlpha(0.5f);//设置按钮变透明



        Glide.with(this).load(R.mipmap.qiangqu).into(flexibilityIV);

        //为提交按钮设置点击事件，将用户的测试数据提交至服务器
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FlexibilityActivity.this,ChestEnduranceActivity.class);//成功获取服务器传递的计划数据，跳转到主界面
                startActivity(intent);
           }
        });
        //为单选按钮添加点击事件
        FRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submitButton.setEnabled(true);//设置Button可点击
                submitButton.setAlpha(1.0f);//设置按钮恢复颜色
                switch (i) {
                    case R.id.radio_flexibility1:
                        User.user.getUserFitnessStage().setFlexibility(1);//如果用户选择了第1项，则将用户的髋关节柔韧性设置为1
                        break;
                    case R.id.radio_flexibility2:
                        User.user.getUserFitnessStage().setFlexibility(2);//如果用户选择了第2项，则将用户的髋关节柔韧性设置为2
                        break;
                    case R.id.radio_flexibility3:
                        User.user.getUserFitnessStage().setFlexibility(3);//如果用户选择了第3项，则将用户的髋关节柔韧性设置为3
                        break;
                    case R.id.radio_flexibility4:
                        User.user.getUserFitnessStage().setFlexibility(5);//如果用户选择了第4项，则将用户的髋关节柔韧性设置为4
                        break;
                    default:
                        break;
                }
            }
        });
        ChangeColor.changeColor(this,Color.parseColor("#80000000"));
    }


}
