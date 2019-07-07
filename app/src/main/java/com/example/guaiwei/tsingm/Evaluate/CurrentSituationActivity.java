package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.gson.User;
import com.google.gson.Gson;

/**
 * 判断用户的运动阶段的问题的界面
 */
public class CurrentSituationActivity extends BaseActivity {
    private Button nextButton;//下一步按钮
    private RadioGroup CSRadio;//判断用户运动状态问题的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_situation);
        //获取相应的控件
        nextButton=(Button) findViewById(R.id.next_cs);
        CSRadio=(RadioGroup) findViewById(R.id.radio_current_situation);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度

        //获取存储在本地的User数据
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(CurrentSituationActivity.this);
        String userData=prefs.getString("user_data",null);
        User.user=new Gson().fromJson(userData,User.class);

        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CurrentSituationActivity.this,ExerciseSiteActivity.class);
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
            }
        });
        //为单选按钮添加点击事件
        CSRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.radio_current_situation1:
                        User.user.getUserFitnessStage().setCurrentSituation(1);//如果用户选择了第1项，则将用户的运动状态设置为1（零基础）
                        break;
                    case R.id.radio_current_situation2:
                        User.user.getUserFitnessStage().setCurrentSituation(2);//如果用户选择了第2项，则将用户的运动状态设置为2（有经验）
                        break;
                    case R.id.radio_current_situation3:
                        User.user.getUserFitnessStage().setCurrentSituation(3);//如果用户选择了第3项，则将用户的运动状态设置为3（经验丰富）
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
