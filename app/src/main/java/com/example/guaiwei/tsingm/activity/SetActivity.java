package com.example.guaiwei.tsingm.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.utils.ChangeColor;

public class SetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ChangeColor.changeColor(this,Color.parseColor("#584f60"));
    }
}
