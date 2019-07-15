package com.example.guaiwei.tsingm.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.ToastUtil;
import com.example.guaiwei.tsingm.utils.VariableUtil;
import com.example.guaiwei.tsingm.adapter.JiRouAdapter;
import com.example.guaiwei.tsingm.adapter.YaoLinAdapter;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.gson.xx_action;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.example.guaiwei.tsingm.service.NextActionService;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ActionDetailActivity extends BaseActivity {

    public static int i;//索引
    private DayPlanInfo dayPlan;
    public static Handler handler;//消息处理
    private VideoView videoView;//视频播放组件
    private xx_action action;//每个动作详细数据
    private ImageView actionIV;//放动作的动图的组件
    private TextView actionNameTV;//显示动作名称的组件
    private TextView leixingTV,mainjiqunTV,otherjiqunTV,actionYaolingTV;//描述训练类型，主要肌群，其他肌群，动作要领的文本框
    private TextView dangqianTV,amounntTV;//显示当前的动作在总的动作中是第几个和总的动作数的文本框
    private RecyclerView actionImageRV,jirouImageRV;//展示动作图片和肌群图片的列表
    private List<String> yaoLingImage;//要领动作图片数据源
    private List<String> jiRouImage;//和肌肉图片数据源
    private Button nextButton,preButton;//下一个动作按钮，和前一个动作按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detail);
        //初始化数据，获得上一个activity传递的对象
        final String actionStr=getIntent().getStringExtra("action_name");
        final String dayPlanStr=getIntent().getStringExtra("dayplan");
        Gson gson=new Gson();
//        action=(xx_action)gson.fromJson(actionStr,xx_action.class);
        dayPlan=(DayPlanInfo) gson.fromJson(dayPlanStr,DayPlanInfo.class) ;
        initComponent();
        startServices();
//        //设置个组件的数据
//        setData();
//        initYaoLinAdapter();
//        initJiRouAdapter();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.setVisibility(View.VISIBLE);
                preButton.setVisibility(View.VISIBLE);
                (ActionDetailActivity.i)++;
                if((ActionDetailActivity.i+1)==Integer.parseInt(dayPlan.getCountAction())){
                    nextButton.setVisibility(View.GONE);
                }
                startServices();
            }
        });
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.setVisibility(View.VISIBLE);
                preButton.setVisibility(View.VISIBLE);
                (ActionDetailActivity.i)--;
                if((ActionDetailActivity.i+1)==1){
                    preButton.setVisibility(View.GONE);
                }
                startServices();
            }
        });
        handler=new MessageUtil();
    }

    /**
     * 开启服务获取数据
     */
    private void startServices(){
        List<EveryActionInfo> everyActionInfos=DataSupport.where("dayId=?",String.valueOf(dayPlan.getId())).find(EveryActionInfo.class);
        String actionName=everyActionInfos.get(i).getName();
        Intent intent=new Intent(ActionDetailActivity.this,NextActionService.class);
        intent.putExtra("action_name",actionName);
        startService(intent);
    }

    /**
     * 设置各组件的数据
     */
    private void setData() {
        yaoLingImage=new ArrayList<>();
        jiRouImage=new ArrayList<>();
        if(action.getGif()!=""){
            Log.v("1","动图");
            videoView.setVisibility(View.GONE);//如果有动图则设置视频播放组件隐藏
            String gifUrl=VariableUtil.Service_IP+action.getGif();
            Glide.with(this).load(gifUrl).into(actionIV);//使用Glide加载动图
        }
        else if(action.getMp4()!=0){
            Log.v("1","视频播放");
            videoView.setVisibility(View.VISIBLE);//设置视频播放组件可见
            playVideo();
        }
        else{
            videoView.setVisibility(View.GONE);//如果动图视频都没有则将
        }
        actionNameTV.setText(action.getTitle());
        leixingTV.setText(action.getAction_type());
        mainjiqunTV.setText(action.getZy_jiqun());
        otherjiqunTV.setText(action.getQt_jiqun());
        actionYaolingTV.setText(action.getYl_str());
        dangqianTV.setText(ActionDetailActivity.i+1+"");
        amounntTV.setText(" / "+dayPlan.getCountAction());
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
     * 播放视频
     */
    private void playVideo(){
        String mp4Url=VariableUtil.Service_IP+action.getId()+".mp4";
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoPath(mp4Url);
        videoView.start();//开始播放视频
        videoView.requestFocus();
        /**
         * 视频播放完后的事件
         */
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }
        });
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        actionIV=(ImageView) findViewById(R.id.action_gif);
        actionNameTV=(TextView) findViewById(R.id.action_name);
        leixingTV=(TextView) findViewById(R.id.leixing);
        mainjiqunTV=(TextView) findViewById(R.id.main_jiqun);
        otherjiqunTV=(TextView) findViewById(R.id.other_jiqun);
        actionYaolingTV=(TextView) findViewById(R.id.action_yaoling);
        dangqianTV=(TextView)findViewById(R.id.dangqian_cont);
        amounntTV=(TextView) findViewById(R.id.sum_count);
        actionImageRV=(RecyclerView) findViewById(R.id.action_image_rv);
        jirouImageRV=(RecyclerView) findViewById(R.id.jirou_image_rv);
        nextButton=(Button) findViewById(R.id.next_action);
        preButton=(Button) findViewById(R.id.pre_action);
        videoView=(VideoView) findViewById(R.id.video);
        if(ActionDetailActivity.i==0){
            preButton.setVisibility(View.GONE);
        }
        if(ActionDetailActivity.i==Integer.parseInt(dayPlan.getCountAction())-1){
            nextButton.setVisibility(View.GONE);
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
