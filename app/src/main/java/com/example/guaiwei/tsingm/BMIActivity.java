package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.bean.User;

/**
 * 获取用户的身高体重的界面
 */
public class BMIActivity extends AppCompatActivity {
    private User user;//用户
    private Button nextButton;//下一步按钮
    private EditText userHeight;//记录用户身高的编辑框
    private EditText userWeight;//记录用户体重的编辑框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        ActivityCollector.addActivity(this);
        //获取各控件
        nextButton=findViewById(R.id.next2);
        userHeight=findViewById(R.id.user_height);
        userWeight=findViewById(R.id.user_weight);
        //获取上一个界面传递过来的用户数据
        user=(User)getIntent().getSerializableExtra("user_data");
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    user.setHeight(Double.parseDouble(userHeight.getText().toString()));//获取用户的身高
                    user.setWeight(Double.parseDouble(userWeight.getText().toString()));//获取用户的体重
                    Intent intent=new Intent(BMIActivity.this,CardioPulmonaryActivity.class);
                    intent.putExtra("user_data",user);
                    //开启新的activity，进入下一个问题页面
                    startActivity(intent);
                }catch(Exception ex){
                    Toast.makeText(BMIActivity.this, "请输入正确的身高体重", Toast.LENGTH_SHORT).show();
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
