package com.example.guaiwei.tsingm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.gson.BaseActivity;

public class WorkDataActivity extends BaseActivity {
    private LinearLayout workDataL;
    private Button backButton;
    private TextView bushuTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_data);
        initComponent();
        setData();
        setOnClickListeners();
        ChangeColor.changeColor(this,Color.parseColor("#584f60"));
    }

    /**
     * 为组件设置点击事件
     */
    private void setOnClickListeners() {
        workDataL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WorkDataActivity.this,HistoryWorkDataActivity.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 为组件设置数据
     */
    private void setData() {
        bushuTV.setText(MainActivity.bushu+"");
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        workDataL=(LinearLayout) findViewById(R.id.detail_work_data);
        backButton=(Button) findViewById(R.id.detail_data_back);
        bushuTV=(TextView) findViewById(R.id.bushu);
    }
}
