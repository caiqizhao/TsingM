package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.BaseActivity;
import com.example.guaiwei.tsingm.bean.User;


/**
 * 判断用户能做几个深蹲的界面，测试用户的下肢耐力
 */
public class LowerLimbActivity extends BaseActivity {
    private Button nextButton;//下一步按钮
    private RadioGroup LLRadio;//判断用户下肢耐力问题的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_limb);
        //获取相应的控件
        nextButton=findViewById(R.id.next6);
        LLRadio=findViewById(R.id.radio_lower_limb);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度

        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LowerLimbActivity.this,FlexibilityActivity.class);
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        LLRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.radio_lower_limb1:
                        User.user.getUserFitnessStage().setLowerLimb(1);//如果用户选择了第1项，则将用户的下肢耐力设置为1
                        break;
                    case R.id.radio_lower_limb2:
                        User.user.getUserFitnessStage().setLowerLimb(2);//如果用户选择了第2项，则将用户的下肢耐力设置为2
                        break;
                    case R.id.radio_lower_limb3:
                        User.user.getUserFitnessStage().setLowerLimb(3);//如果用户选择了第3项，则将用户的下肢耐力设置为3
                        break;
                    case R.id.radio_lower_limb4:
                        User.user.getUserFitnessStage().setLowerLimb(4);//如果用户选择了第4项，则将用户的下肢耐力设置为4
                        break;
                    case R.id.radio_lower_limb5:
                        User.user.getUserFitnessStage().setLowerLimb(5);//如果用户选择了第5项，则将用户的下肢耐力设置为5
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
