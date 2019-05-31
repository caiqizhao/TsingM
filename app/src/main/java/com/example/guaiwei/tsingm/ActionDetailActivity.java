package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.guaiwei.tsingm.Utils.ToastUtil;
import com.example.guaiwei.tsingm.Utils.VariableUtil;
import com.example.guaiwei.tsingm.adapter.JiRouAdapter;
import com.example.guaiwei.tsingm.adapter.PlanDetailAdapter;
import com.example.guaiwei.tsingm.adapter.YaoLinAdapter;
import com.example.guaiwei.tsingm.bean.DayPlan;
import com.example.guaiwei.tsingm.bean.EveryAction;
import com.example.guaiwei.tsingm.bean.User_Plan;
import com.example.guaiwei.tsingm.bean.xx_action;
import com.example.guaiwei.tsingm.service.NextActionService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActionDetailActivity extends AppCompatActivity {

    public static int i;//索引
    private DayPlan dayPlan;
    public static Handler handler;//消息处理
    private VideoView videoView;//视频播放组件
    private xx_action action;//每个动作详细数据
    private ImageView actionIV;//放动作的动图的组件
    private TextView actionNameTV;//显示动作名称的组件
    private TextView leixingTV,mainjiqunTV,otherjiqunTV,actionYaolingTV;//描述训练类型，主要肌群，其他肌群，动作要领的文本框
    private TextView dangqianTV,amounntTV;//显示当前的动作在总的动作中是第几个和总的动作数的文本框
    private RecyclerView actionImageRV,jirouImageRV;//展示动作图片和肌群图片的列表
    private List<String> yaoLingImage=new ArrayList<>();//要领动作图片数据源
    private List<String> jiRouImage=new ArrayList<>();//和肌肉图片数据源
    private Button nextButton,preButton;//下一个动作按钮，和前一个动作按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detail);
        initComponent();
        //初始化数据，获得上一个activity传递的对象
        final String actionStr=getIntent().getStringExtra("action_detail");
        final String dayPlanStr=getIntent().getStringExtra("dayplan");
        Gson gson=new Gson();
        action=(xx_action)gson.fromJson(actionStr,xx_action.class);
        dayPlan=(DayPlan)gson.fromJson(dayPlanStr,DayPlan.class) ;
        //设置个组件的数据
        setData();
        initYaoLinAdapter();
        initJiRouAdapter();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (ActionDetailActivity.i)++;
                if((ActionDetailActivity.i+1)==Integer.parseInt(dayPlan.getCountAction())){
                    nextButton.setVisibility(View.GONE);
                }
                nextButton.setVisibility(View.VISIBLE);
                preButton.setVisibility(View.VISIBLE);
                String actionName=dayPlan.getActions().get(i).getName();
                Intent intent=new Intent(ActionDetailActivity.this,NextActionService.class);
                intent.putExtra("action_name",actionName);
                startService(intent);
            }
        });
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (ActionDetailActivity.i)--;
                if((ActionDetailActivity.i+1)==1){
                    preButton.setVisibility(View.GONE);
                }
                nextButton.setVisibility(View.VISIBLE);
                preButton.setVisibility(View.VISIBLE);
                String actionName=dayPlan.getActions().get(i).getName();
                Intent intent=new Intent(ActionDetailActivity.this,NextActionService.class);
                intent.putExtra("action_name",actionName);
                startService(intent);
            }
        });
        handler=new MessageUtil();
    }


    /**
     * 设置各组件的数据
     */
    private void setData() {
        if(action.getGif()!=null){
            videoView.setVisibility(View.GONE);//如果有动图则设置视频播放组件隐藏
            String gifUrl=VariableUtil.Service_IP+action.getGif();
            Glide.with(this).load(gifUrl).into(actionIV);//使用Glide加载动图
        }
        else if(action.getMp4()!=0){
            videoView.setVisibility(View.VISIBLE);//设置视频播放组件可见
            String mp4Url=VariableUtil.Service_IP+action.getTitle()+".mp4";
            videoView.setVideoPath(mp4Url);
            videoView.start();//开始播放视频
        }
        else{
            videoView.setVisibility(View.GONE);//如果动图视频都没有则将
        }
        actionNameTV.setText(action.getTitle());
        leixingTV.setText(action.getAction_type());
        mainjiqunTV.setText(action.getZy_jiqun());
        otherjiqunTV.setText(action.getQt_jiqun());
        actionYaolingTV.setText(action.getYl_str());
        String countAction=getIntent().getStringExtra("count_action");
        dangqianTV.setText(ActionDetailActivity.i+1+"");
        amounntTV.setText(" / "+countAction);
        if(action.getJr_png()!=0){
            for(int i=0;i<action.getJr_png();i++){
                jiRouImage.add(VariableUtil.Service_IP+action.getTitle()+i+".png");
            }
        }
        if(action.getYl_jpg()!=0){
            for(int i=0;i<action.getYl_jpg();i++){
                yaoLingImage.add(VariableUtil.Service_IP+"yl/"+action.getTitle()+i+".jpg");
            }
        }
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        actionIV=findViewById(R.id.action_gif);
        actionNameTV=findViewById(R.id.action_name);
        leixingTV=findViewById(R.id.leixing);
        mainjiqunTV=findViewById(R.id.main_jiqun);
        otherjiqunTV=findViewById(R.id.other_jiqun);
        actionYaolingTV=findViewById(R.id.action_yaoling);
        dangqianTV=findViewById(R.id.dangqian_cont);
        amounntTV=findViewById(R.id.sum_count);
        actionImageRV=findViewById(R.id.action_image_rv);
        jirouImageRV=findViewById(R.id.jirou_image_rv);
        nextButton=findViewById(R.id.next_action);
        preButton=findViewById(R.id.pre_action);
        videoView=findViewById(R.id.video);
        if(ActionDetailActivity.i==0||ActionDetailActivity.i==Integer.parseInt(dayPlan.getCountAction())){
            preButton.setVisibility(View.GONE);
        }
    }

    /**
     * 为动作要领图的列表设置适配器
     */
    private void initYaoLinAdapter(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        actionImageRV.setLayoutManager(layoutManager);
        YaoLinAdapter yaoLingAdapter=new YaoLinAdapter(yaoLingImage);
        actionImageRV.setAdapter(yaoLingAdapter);
    }
    /**
     * 为动作肌肉图的列表设置适配器
     */
    private void initJiRouAdapter() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        jirouImageRV.setLayoutManager(layoutManager);
        JiRouAdapter jiRouAdapter=new JiRouAdapter(jiRouImage);
        jirouImageRV.setAdapter(jiRouAdapter);
    }
    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x007){
                //获得消息中的数据赋值给用户计划实例
                Bundle data = msg.getData();
                String str=data.getString("action_detail");
                Log.v("1","接收"+str);
                Gson gson=new Gson();
                action=(xx_action)gson.fromJson(str,xx_action.class);
                //重新设置数据
                setData();
                initYaoLinAdapter();
                initJiRouAdapter();
            }
            else if(msg.what==0x008){
                Bundle data = msg.getData();
                String str = data.getString("action_detail");
                ToastUtil.showToast(ActionDetailActivity.this,str);
            }
        }
    }

}
