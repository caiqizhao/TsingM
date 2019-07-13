package com.example.guaiwei.tsingm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.example.guaiwei.tsingm.gson.Nutriment;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.view.EnergyProgressView;
import com.example.guaiwei.tsingm.view.ScoreView;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.List;

public class WorkDataActivity extends BaseActivity {
    private ScoreView scoreView;
    private TextView scoreTv,foodRecommend,sportRecommend,needEnergyTv;
    private Button sportBtn,foodBtn;
    private ImageView sportWarn,foodWarn;
    private EnergyProgressView sportProgess,foodProgess;
    private double foodRecommendEnergy,foodFactEnergy,sportFactEnergy,sportRecommendEnergy,needEnergy;
    private SharedPreferences prefs;
    private Nutriment nutriment;//没推运动前营养素

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_data);
        prefs=PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        initComponent();
        setData();
        setOnClickListeners();
        ChangeColor.changeColor(this,Color.parseColor("#3e424e"));
    }

    /**
     * 为组件设置点击事件
     */
    private void setOnClickListeners() {
        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WorkDataActivity.this,HistoryWorkDataActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });
        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WorkDataActivity.this,HistoryWorkDataActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
    }

    /**
     * 为组件设置数据
     */
    private void setData() {
        DecimalFormat df = new DecimalFormat("0.0");
        String nutrimentStr=prefs.getString("nutriment",null);
        if (nutrimentStr!=null){
            nutriment=new Gson().fromJson(nutrimentStr,Nutriment.class);
        }
        List<NutrimentInfo> nutrimentInfo=DataSupport.where("data=?",GetBeforeData.getBeforeData(null,0).get(0)).find(NutrimentInfo.class);
        List<DayPlanInfo> dayPlanInfo=DataSupport.where("data=?",GetBeforeData.getBeforeData(null,0).get(0)).find(DayPlanInfo.class);
        foodFactEnergy=getIntent().getDoubleExtra("food_energy",0);
        sportFactEnergy=getIntent().getDoubleExtra("sport_energy",0);
        if(nutrimentInfo.size()!=0){
            foodRecommendEnergy=nutrimentInfo.get(0).getHeat();
        }
        else{
            foodRecommendEnergy=nutriment.getHeat();
        }
        if(dayPlanInfo.size()!=0){
            sportRecommendEnergy=Double.parseDouble(dayPlanInfo.get(0).getNengliang());
        }
        needEnergy=foodRecommendEnergy-sportRecommendEnergy;
//        double food_complete=foodFactEnergy/(foodRecommendEnergy*0.01);
//        double sport_complete;
//        if (sportRecommendEnergy==0){
//            sport_complete=0.0;
//        }else
//            sport_complete=sportFactEnergy/(sportRecommendEnergy*0.01);
//
        int value=100-(int)(Math.abs((foodFactEnergy-sportFactEnergy)-needEnergy)/(needEnergy*0.01));
        scoreView.setValueDuringRefresh(value,100);
        scoreTv.setText(value+"");
        foodRecommend.setText(foodRecommendEnergy+"");
        sportRecommend.setText(sportRecommendEnergy+"");
        needEnergyTv.setText(needEnergy+"");
        sportProgess.setText("已耗能");
        foodProgess.setText("已摄入");
        sportProgess.setTotalAndCurrentCount((float)sportRecommendEnergy,(float)sportFactEnergy);
        foodProgess.setTotalAndCurrentCount((float)foodRecommendEnergy,(float)foodFactEnergy);
        if (foodFactEnergy>foodRecommendEnergy){
            foodWarn.setVisibility(View.VISIBLE);
        }
        else {
            foodWarn.setVisibility(View.GONE);
        }
        if (sportFactEnergy>sportRecommendEnergy){
            sportWarn.setVisibility(View.VISIBLE);
        }
        else{
            sportWarn.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        scoreView=(ScoreView)findViewById(R.id.score_view);
        scoreTv=(TextView)findViewById(R.id.score);
        foodBtn=(Button)findViewById(R.id.food_btn);
        sportBtn=(Button)findViewById(R.id.sport_btn);
        foodRecommend=(TextView)findViewById(R.id.recommend_food) ;
        sportRecommend=(TextView)findViewById(R.id.recommend_sport);
        needEnergyTv=(TextView)findViewById(R.id.energy);
        sportWarn=(ImageView)findViewById(R.id.sport_warn_img);
        foodWarn=(ImageView)findViewById(R.id.food_warn_img);
        sportProgess=(EnergyProgressView)findViewById(R.id.sport_progess);
        foodProgess=(EnergyProgressView)findViewById(R.id.food_progess);
    }
}
