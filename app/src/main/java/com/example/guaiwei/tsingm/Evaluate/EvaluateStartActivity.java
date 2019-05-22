package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.BaseActivity;

public class EvaluateStartActivity extends BaseActivity {

    private Button startEvaluate;//开始测评按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_start);

        startEvaluate=findViewById(R.id.start_evaluate);
        //设置开始测评按钮点击事件
        startEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EvaluateStartActivity.this,CurrentSituationActivity.class);//点击按钮后跳转到InfoActivity
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
