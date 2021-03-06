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
 * 判断用户能做几个跪姿俯卧撑的界面，测试用户的胸肌耐力
 */
public class ChestEnduranceActivity extends BaseActivity {
    private Button nextButton;//下一步按钮
    private RadioGroup CERadio;//判断用户胸肌耐力问题的单选按钮组
    private ImageView CEImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest_endurance);
        //获取相应的控件
        nextButton=(Button) findViewById(R.id.next4);
        CERadio=(RadioGroup) findViewById(R.id.radio_chest_endurance);
        CEImage=(ImageView)findViewById(R.id.fuwocheng_image);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度

        Glide.with(this).load(R.mipmap.fuwc).into(CEImage);

        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChestEnduranceActivity.this,AbdominalEnduranceActivity.class);
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        CERadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1f);
                switch (i) {
                    case R.id.radio_chest_endurance1:
                        User.user.getUserFitnessStage().setChestEndurance(1);//如果用户选择了第1项，则将用户的胸肌耐力设置为1
                        break;
                    case R.id.radio_chest_endurance2:
                        User.user.getUserFitnessStage().setChestEndurance(2);//如果用户选择了第2项，则将用户的胸肌耐力设置为2
                        break;
                    case R.id.radio_chest_endurance3:
                        User.user.getUserFitnessStage().setChestEndurance(3);//如果用户选择了第3项，则将用户的胸肌耐力设置为3
                        break;
                    case R.id.radio_chest_endurance4:
                        User.user.getUserFitnessStage().setChestEndurance(4);//如果用户选择了第4项，则将用户的胸肌耐力设置为4
                        break;
                    case R.id.radio_chest_endurance5:
                        User.user.getUserFitnessStage().setChestEndurance(5);//如果用户选择了第5项，则将用户的胸肌耐力设置为5
                        break;
                    default:
                        break;
                }
            }
        });
        ChangeColor.changeColor(this,Color.parseColor("#80000000"));
    }
}
