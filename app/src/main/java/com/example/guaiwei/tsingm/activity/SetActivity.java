package com.example.guaiwei.tsingm.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.example.guaiwei.tsingm.db.RecommendFood;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.utils.Utility;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SetActivity extends BaseActivity {
    private TextView text1,text2,text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initComponent();
        setListeners();
        ChangeColor.changeColor(this,Color.parseColor("#584f60"));
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
    }

    /**
     * 为各组件设置点击事件
     */
    private void setListeners() {
    }
}
