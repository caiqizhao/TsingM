package com.example.guaiwei.tsingm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.Utils.VariableUtil;
import com.example.guaiwei.tsingm.bean.Food;
import com.google.gson.Gson;

/**
 * 搜索的食物详情页
 */
public class SearchFoodDetailActivity extends AppCompatActivity {
    private Food food=new Food();//食物数据
    private Button backButton;//返回按钮
    private TextView foodName,foodNengLiang,foodRL,foodDBZ,foodTSW,foodZF,foodType;
    private ImageView foodPhoto;//食物图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_detail);
        //获取上一个 activity传递过来的JSON数据
        String str=getIntent().getStringExtra("food_detail");
        //将JSON数据转换成Food对象
        Gson gson=new Gson();
        food=gson.fromJson(str,Food.class);

        initComponent();
        setData();
        /**
         * 为返回按钮设置点击事件
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        backButton=findViewById(R.id.detail_food_back);
        foodName=findViewById(R.id.detail_food_name);
        foodPhoto=findViewById(R.id.detail_food_photo);
        foodNengLiang=findViewById(R.id.detail_food_nengliang);
        foodRL=findViewById(R.id.detail_food_rl);
        foodDBZ=findViewById(R.id.detail_food_dbz);
        foodTSW=findViewById(R.id.detail_food_tsw);
        foodZF=findViewById(R.id.detail_food_zf);
        foodType=findViewById(R.id.detail_food_type);
    }

    /**
     * 设置各组件的数据
     */
    private void setData() {
        foodName.setText(food.getFood_name());
        String url=VariableUtil.Service_IP+"food/"+food.getFood_name()+".jpg";
        Glide.with(this).load(url).into(foodPhoto);
        foodNengLiang.setText(food.getRl()+"千卡/100g(毫升)");
        foodRL.setText(""+food.getRl());
        foodDBZ.setText(""+food.getDbz());
        foodTSW.setText(""+food.getCshhw());
        foodZF.setText(""+food.getZf());
        foodType.setText(""+food.getType());
    }


}
