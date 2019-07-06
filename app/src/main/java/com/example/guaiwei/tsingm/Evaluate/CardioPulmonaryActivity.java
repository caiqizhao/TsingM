package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.gson.User;


/**
 * 判断用户心肺功能的问题界面
 */
public class CardioPulmonaryActivity extends BaseActivity {
    private Button nextButton;//下一步按钮
    private RadioGroup CPRadio;//判断用户心肺功能问题的单选按钮组
    private ImageView CPImage;//背景动图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_pulmonary);
        //获取相应的控件
        nextButton=(Button) findViewById(R.id.next3);
        CPRadio=(RadioGroup) findViewById(R.id.radio_cardio_pulmonary);
        CPImage=(ImageView) findViewById(R.id.xingfei_image);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度

        Glide.with(this).load(R.mipmap.upstair).into(CPImage);

        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CardioPulmonaryActivity.this,ChestEnduranceActivity.class);
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        CPRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.cardio_pulmonary1:
                        User.user.getUserFitnessStage().setCardioPulmonary(1);//如果用户选择了第1项，则将用户的心肺功能设置为1
                        break;
                    case R.id.cardio_pulmonary2:
                        User.user.getUserFitnessStage().setCardioPulmonary(2);//如果用户选择了第2项，则将用户的心肺功能设置为2
                        break;
                    case R.id.cardio_pulmonary3:
                        User.user.getUserFitnessStage().setCardioPulmonary(3);//如果用户选择了第3项，则将用户的心肺功能设置为3
                        break;
                    case R.id.cardio_pulmonary4:
                        User.user.getUserFitnessStage().setCardioPulmonary(5);//如果用户选择了第4项，则将用户的心肺功能设置为4
                        break;
                    default:
                        break;
                }
            }
        });
        ChangeColor.changeColor(this,Color.parseColor("#80000000"));//改变状态栏颜色
    }
}
