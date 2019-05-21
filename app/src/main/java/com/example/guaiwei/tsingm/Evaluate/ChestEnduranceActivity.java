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
 * 判断用户能做几个跪姿俯卧撑的界面，测试用户的胸肌耐力
 */
public class ChestEnduranceActivity extends AppCompatActivity {
    private User user;
    private Button nextButton;//下一步按钮
    private RadioGroup CERadio;//判断用户胸肌耐力问题的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest_endurance);
        ActivityCollector.addActivity(this);
        //获取相应的控件
        nextButton=findViewById(R.id.next4);
        CERadio=findViewById(R.id.radio_chest_endurance);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChestEnduranceActivity.this,AbdominalEnduranceActivity.class);
                intent.putExtra("user_data",user);//将用户的信息数据传递至下一界面
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
                        user.getUserFitnessStage().setChestEndurance(1);//如果用户选择了第1项，则将用户的胸肌耐力设置为1
                        break;
                    case R.id.radio_chest_endurance2:
                        user.getUserFitnessStage().setChestEndurance(2);//如果用户选择了第2项，则将用户的胸肌耐力设置为2
                        break;
                    case R.id.radio_chest_endurance3:
                        user.getUserFitnessStage().setChestEndurance(3);//如果用户选择了第3项，则将用户的胸肌耐力设置为3
                        break;
                    case R.id.radio_chest_endurance4:
                        user.getUserFitnessStage().setChestEndurance(4);//如果用户选择了第4项，则将用户的胸肌耐力设置为4
                        break;
                    case R.id.radio_chest_endurance5:
                        user.getUserFitnessStage().setChestEndurance(5);//如果用户选择了第5项，则将用户的胸肌耐力设置为5
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
