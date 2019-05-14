package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.bean.User;

import java.io.IOException;


/**
 * 用户性别年龄信息采集页面
 */
public class InfoActivity extends AppCompatActivity {

    private User user;//用户
    private Button nextButton;//下一步按钮
    private RadioGroup sexRadio;//性别单选组
    private RadioButton sexRadioButton ;//选中的单选按钮
    private EditText userBtdYear;//用户的出生年份编辑框
    private EditText userBtdMonth;//用户的出生月份编辑框
    private EditText userBtdDay;//用户的出生日编辑框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ActivityCollector.addActivity(this);
        //获取各控件
        nextButton=findViewById(R.id.next1);
        sexRadio=findViewById(R.id.radio_sex);
        userBtdYear=findViewById(R.id.user_btd_year);
        userBtdMonth=findViewById(R.id.user_btd_month);
        userBtdDay=findViewById(R.id.user_btd_day);
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //设置button为不可点击状态
        nextButton.setEnabled(false);
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    user.getUserBirthday().setYear(Integer.parseInt(userBtdYear.getText().toString()));//设置用户的出生年份为用户输入的数据
                    user.getUserBirthday().setMonth(Integer.parseInt(userBtdMonth.getText().toString()));//设置用户的出生月份为用户输入的数据
                    user.getUserBirthday().setDay(Integer.parseInt(userBtdDay.getText().toString()));//设置用户的出生日为用户输入的数据
                    //Log.v("1"," "+userBtdYear.getText().toString()+","+userBtdMonth.getText().toString()+","+userBtdDay.getText().toString());
                    Intent intent=new Intent(InfoActivity.this,BMIActivity.class);
                    //将用户数据传递至新的Activity
                    intent.putExtra("user_data",user);
                    //开启新的activity，进入下一个问题页面
                    startActivity(intent);
                }catch(Exception ex){
                    Toast.makeText(InfoActivity.this, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //为secRadio设置监听器
        sexRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);//设置下一步按钮可点击
                sexRadioButton=findViewById(i);
                //设置用户的性别
                user.setSex(sexRadioButton.getText().toString());
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
