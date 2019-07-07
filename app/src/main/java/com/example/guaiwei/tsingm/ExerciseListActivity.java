
package com.example.guaiwei.tsingm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.guaiwei.tsingm.Evaluate.ExerciseSiteActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseListActivity extends AppCompatActivity {

    private ListView listView;
    private List<Map<String,Object>> data;//数据源
    private Map<String,Object> item;

    private int image[]={R.drawable.pushup,R.drawable.situp};
    private String motion[]={"俯卧撑","仰卧起坐"};
    private int length=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        //获得listView
        listView = findViewById(R.id.motionList);

        //生成数据源data
        data = new ArrayList<Map<String,Object>>();
        for(int i = 0;i<length;i++){//每一个item对应List中一行
            item = new HashMap<String,Object>();
            item.put("photo",image[i]);
            item.put("motion",motion[i]);
            data.add(item);
        }

        //生成简单适配器
        SimpleAdapter listAdapter = new SimpleAdapter(this,data,R.layout.exercise_detail_item,
                new String[]{"photo","motion"},new int[]{R.id.photo,R.id.motion});

        //设置适配器
        listView.setAdapter(listAdapter);

//        //设置选项点击事件
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(ExerciseListActivity.this,"您选择了第"+position+"项运动",Toast.LENGTH_LONG).show();
//            }
//        });
    }
}