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
 * 判断用户能做几个标准卷腹的界面，测试用户的腹肌耐力
 */
public class AbdominalEnduranceActivity extends BaseActivity {
    private Button nextButton;//下一步按钮
    private RadioGroup AERadio;//判断用户腹肌耐力问题的单选按钮组
    private ImageView juanfuIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abdominal_endurance);
        //获取相应的控件
        nextButton=(Button)findViewById(R.id.next5);
        AERadio=(RadioGroup)findViewById(R.id.radio_abdominal_endurance);
        juanfuIV=(ImageView)findViewById(R.id.juanfu_image);

        //设置背景图片
        Glide.with(this).load(R.mipmap.juanfu).into(juanfuIV);

        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度

        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AbdominalEnduranceActivity.this,CardioPulmonaryActivity.class);

                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        AERadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.radio_abdominal_endurance1:
                        User.user.getUserFitnessStage().setAbdominalEndurance(1);;//如果用户选择了第1项，则将用户的腹肌耐力设置为1
                        break;
                    case R.id.radio_abdominal_endurance2:
                        User.user.getUserFitnessStage().setAbdominalEndurance(2);//如果用户选择了第2项，则将用户的腹肌耐力设置为2
                        break;
                    case R.id.radio_abdominal_endurance3:
                        User.user.getUserFitnessStage().setAbdominalEndurance(3);//如果用户选择了第3项，则将用户的腹肌耐力设置为3
                        break;
                    case R.id.radio_abdominal_endurance4:
                        User.user.getUserFitnessStage().setAbdominalEndurance(4);//如果用户选择了第4项，则将用户的腹肌耐力设置为4
                        break;
                    case R.id.radio_abdominal_endurance5:
                        User.user.getUserFitnessStage().setAbdominalEndurance(5);//如果用户选择了第5项，则将用户的腹肌耐力设置为5
                        break;
                    default:
                        break;
                }
            }
        });
        ChangeColor.changeColor(this,Color.parseColor("#80000000"));//改变状态栏颜色
    }
}
