package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.Utils.ToastUtil;
import com.example.guaiwei.tsingm.adapter.SearchFoodAdapter;
import com.example.guaiwei.tsingm.bean.Food;
import com.example.guaiwei.tsingm.service.SearchFoodService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity {

    private List<Food> foodList;//数据源

    public static Handler mHandler;

    private EditText foodEditText;
    private Button searchButton;
    private Button backButton;
    private RecyclerView foodRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        mHandler=new MessageUtil();
        foodList=new ArrayList<>();
        initComponent();
        initSearchAdapter();
        setButtonOnclickListening();
    }
    /**
     * 初始化各组件
     */
    private void initComponent(){
        foodEditText=findViewById(R.id.search_edit);
        searchButton=findViewById(R.id.search_btn);
        backButton=findViewById(R.id.back_search);
        foodRV=findViewById(R.id.food_search_RV);
    }

    /**
     * 为RecycleView设置数据和适配器
     */
    private void initSearchAdapter(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        foodRV.setLayoutManager(linearLayoutManager);
        SearchFoodAdapter foodAdapter=new SearchFoodAdapter(foodList);
        foodRV.setAdapter(foodAdapter);
    }

    /**
     * 设置按钮的点击事件
     */
    private void setButtonOnclickListening(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=foodEditText.getText().toString();
                if(!TextUtils.isEmpty(str)&&str!=null){
                    Intent intent=new Intent(SearchFoodActivity.this,SearchFoodService.class);
                    intent.putExtra("food_name",str);
                    startService(intent);
                }

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
     * 内部类消息处理类
     */
    private class MessageUtil extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x009){
                Bundle data = msg.getData();
                String str=data.getString("food_detail");
                Gson gson=new Gson();
                foodList=gson.fromJson(str,new TypeToken<List<Food>>(){}.getType());
                //重新设置RecycleView的数据
                SearchFoodAdapter foodAdapter=new SearchFoodAdapter(foodList);
                foodRV.setAdapter(foodAdapter);
            }
            else if(msg.what==0x010){
                Bundle data = msg.getData();
                String str=data.getString("food_detail");
                ToastUtil.showToast(SearchFoodActivity.this,str);
            }
        }
    }
}
