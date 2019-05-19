package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.User;

/**
 * 判断用户运动目标的界面
 */
public class SportTargetActivity extends AppCompatActivity {

    private User user;
    private Button nextButton;//下一步按钮
    private RadioGroup STRadio;//判断用户运动目标的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_target);
        ActivityCollector.addActivity(this);
        //获取相应的控件
        nextButton=findViewById(R.id.next_st);
        STRadio=findViewById(R.id.radio_sport_target);
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SportTargetActivity.this,ExerciseSiteActivity.class);
                intent.putExtra("user_data",user);//将用户的信息数据传递至下一界面
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        STRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.radio_sport_target1:
                        user.getUserFitnessStage().setSportTarget(1);//如果用户选择了第1项，则将用户的运动目标设置为1（减脂）
                        break;
                    case R.id.radio_sport_target2:
                        user.getUserFitnessStage().setSportTarget(2);//如果用户选择了第2项，则将用户的心肺功能设置为2（局部塑形）
                        break;
                    case R.id.radio_sport_target3:
                        user.getUserFitnessStage().setSportTarget(3);//如果用户选择了第3项，则将用户的心肺功能设置为3（增肌）
                        break;
                    case R.id.radio_sport_target4:
                        user.getUserFitnessStage().setSportTarget(4);//如果用户选择了第4项，则将用户的心肺功能设置为4（保持健康）
                        break;
                    default:
                        break;
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
