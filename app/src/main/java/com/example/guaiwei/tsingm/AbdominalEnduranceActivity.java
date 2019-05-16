package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.bean.User;


/**
 * 判断用户能做几个标准卷腹的界面，测试用户的腹肌耐力
 */
public class AbdominalEnduranceActivity extends AppCompatActivity {
    private User user;
    private Button nextButton;//下一步按钮
    private RadioGroup AERadio;//判断用户腹肌耐力问题的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abdominal_endurance);
        ActivityCollector.addActivity(this);
        //获取相应的控件
        nextButton=findViewById(R.id.next5);
        AERadio=findViewById(R.id.radio_abdominal_endurance);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AbdominalEnduranceActivity.this,LowerLimbActivity.class);
                intent.putExtra("user_data",user);//将用户的信息数据传递至下一界面
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
                        user.getUserFitnessStage().setAbdominalEndurance(1);;//如果用户选择了第1项，则将用户的腹肌耐力设置为1
                        break;
                    case R.id.radio_abdominal_endurance2:
                        user.getUserFitnessStage().setAbdominalEndurance(2);//如果用户选择了第2项，则将用户的腹肌耐力设置为2
                        break;
                    case R.id.radio_abdominal_endurance3:
                        user.getUserFitnessStage().setAbdominalEndurance(3);//如果用户选择了第3项，则将用户的腹肌耐力设置为3
                        break;
                    case R.id.radio_abdominal_endurance4:
                        user.getUserFitnessStage().setAbdominalEndurance(4);//如果用户选择了第4项，则将用户的腹肌耐力设置为4
                        break;
                    case R.id.radio_abdominal_endurance5:
                        user.getUserFitnessStage().setAbdominalEndurance(5);//如果用户选择了第5项，则将用户的腹肌耐力设置为5
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
