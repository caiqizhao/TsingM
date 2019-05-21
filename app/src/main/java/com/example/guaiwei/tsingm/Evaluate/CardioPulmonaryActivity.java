package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.User;


/**
 * 判断用户心肺功能的问题界面
 */
public class CardioPulmonaryActivity extends AppCompatActivity {
    private User user;
    private Button nextButton;//下一步按钮
    private RadioGroup CPRadio;//判断用户心肺功能问题的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_pulmonary);
        ActivityCollector.addActivity(this);
        //获取相应的控件
        nextButton=findViewById(R.id.next3);
        CPRadio=findViewById(R.id.radio_cardio_pulmonary);
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CardioPulmonaryActivity.this,ChestEnduranceActivity.class);
                intent.putExtra("user_data",user);//将用户的信息数据传递至下一界面
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
                        user.getUserFitnessStage().setCardioPulmonary(1);//如果用户选择了第1项，则将用户的心肺功能设置为1
                        break;
                    case R.id.cardio_pulmonary2:
                        user.getUserFitnessStage().setCardioPulmonary(2);//如果用户选择了第2项，则将用户的心肺功能设置为2
                        break;
                    case R.id.cardio_pulmonary3:
                        user.getUserFitnessStage().setCardioPulmonary(3);//如果用户选择了第3项，则将用户的心肺功能设置为3
                        break;
                    case R.id.cardio_pulmonary4:
                        user.getUserFitnessStage().setCardioPulmonary(5);//如果用户选择了第4项，则将用户的心肺功能设置为4
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
