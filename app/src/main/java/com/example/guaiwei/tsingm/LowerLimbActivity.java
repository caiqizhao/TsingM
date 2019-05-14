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
 * 判断用户能做几个深蹲的界面，测试用户的下肢耐力
 */
public class LowerLimbActivity extends AppCompatActivity {
    private User user;
    private Button nextButton;//下一步按钮
    private RadioGroup LLRadio;//判断用户下肢耐力问题的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_limb);
        ActivityCollector.addActivity(this);
        //获取相应的控件
        nextButton=findViewById(R.id.next6);
        LLRadio=findViewById(R.id.radio_lower_limb);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LowerLimbActivity.this,FlexibilityActivity.class);
                intent.putExtra("user_data",user);//将用户的信息数据传递至下一界面
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        LLRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                switch (i) {
                    case R.id.radio_lower_limb1:
                        user.getUserFitnessStage().setLowerLimb(1);//如果用户选择了第1项，则将用户的下肢耐力设置为1
                        break;
                    case R.id.radio_lower_limb2:
                        user.getUserFitnessStage().setLowerLimb(2);//如果用户选择了第2项，则将用户的下肢耐力设置为2
                        break;
                    case R.id.radio_lower_limb3:
                        user.getUserFitnessStage().setLowerLimb(3);//如果用户选择了第3项，则将用户的下肢耐力设置为3
                        break;
                    case R.id.radio_lower_limb4:
                        user.getUserFitnessStage().setLowerLimb(4);//如果用户选择了第4项，则将用户的下肢耐力设置为4
                        break;
                    case R.id.radio_lower_limb5:
                        user.getUserFitnessStage().setLowerLimb(5);//如果用户选择了第5项，则将用户的下肢耐力设置为5
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
