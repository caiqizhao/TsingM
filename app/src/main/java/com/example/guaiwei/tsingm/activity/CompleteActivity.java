package com.example.guaiwei.tsingm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;

/**
 * 训练完成之后的界面
 */
public class CompleteActivity extends AppCompatActivity {
    TextView completeAction,countTime,realseNL;//完成动作的个数、总时间、消耗能量
    Button completeBtn;//完成按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        initComponent();
        setData();
        setListeners();
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        completeAction=findViewById(R.id.number);
        countTime=findViewById(R.id.time);
        realseNL=findViewById(R.id.calorie);
        completeBtn=findViewById(R.id.complete_button);
    }

    /**
     * 为各组件设置数据
     */
    private void setData() {
        completeAction.setText("完成动作 |  "+getIntent().getStringExtra("count_action")+"个");
        countTime.setText("总计用时 |  "+getIntent().getStringExtra("count_time")+"");
        realseNL.setText("今日消耗 |  "+getIntent().getStringExtra("nengliang")+"千卡");
    }

    /**
     * 为各组件设置监听事件
     */
    private void setListeners() {
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CompleteActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
