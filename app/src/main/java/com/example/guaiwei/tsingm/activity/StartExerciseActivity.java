package com.example.guaiwei.tsingm.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.DownloadUtil;
import com.example.guaiwei.tsingm.utils.FullScreenUtil;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 开始训练活动
 */
public class StartExerciseActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    Boolean isStart=false;//标志是否在计时
    int i=0;//记录现在播放的视频的索引
    private int complete_i=0;//记录用户完成的动作个数
    private Thread timeThread;//计时线程
    Runnable timeRun;
    public static int m=0,n=0;//单个训练视频的计时，和总计时
    private int currentPosition=0;

    private double factHaoNeng;

    private DayPlanInfo dayPlan;
    private SurfaceView videoSuf;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mPlayer=new MediaPlayer();
    private Button backBtn,nextVideoBtn,preVideoBtn,startPlayBtn;
    private TextView countAction,videoNameTx,yaoDianTx,nowTimeTx,actionTimeTx,countTimeTx;

    private List<EveryActionInfo> everyActions=new ArrayList<>();
    private List<String> everyActionUrl=new ArrayList<>();

    public static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);
        Log.v("1","StartActivityOnCreate");

        //设置全屏
        FullScreenUtil.setFullScreen(this);

        //初始化动作列表
        String everyAction=getIntent().getStringExtra("every_actions");
        Gson gson=new Gson();
        everyActions=gson.fromJson(everyAction,new TypeToken<List<EveryActionInfo>>(){}.getType());
        dayPlan=gson.fromJson(getIntent().getStringExtra("dayplan"),DayPlanInfo.class);
        for(int i=0;i<everyActions.size();i++){
            everyActionUrl.add(DownloadUtil.getDownFilePath(everyActions.get(i).getName(),"mp4"));
        }
        mHandler=new MessageUtil();
        //计时
        timeRun=new Runnable() {
            @Override
            public void run() {
                try {
                    while(!Thread.interrupted()) {
                        Message msg = new Message();//生成消息
                        msg.what = 0x001;//设置消息类型
                        Bundle data = new Bundle();//生成Bundle携带消息
                        data.putInt("time", m);
                        data.putInt("time_count", n);
                        msg.setData(data);
                        StartExerciseActivity.mHandler.sendMessage(msg);
                        m++;
                        n++;
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    Log.v("timeThread","线程终止了");
                }
            }
        };
        initComponent();
        setData();
        initSurfaceView();
        initMediaPLay();
        setListerners();
    }

    /**
     * 初始化媒体播放器
     */
    private void initMediaPLay() {
        if(mPlayer==null){
            mPlayer=new MediaPlayer();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setLooping(true);
    }

    /**
     * 设置事件监听
     */
    private void setListerners() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseDb();
                m=0;n=0;
                finish();
            }
        });
        nextVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preVideoBtn.setVisibility(View.VISIBLE);
                i++;
                if(i==everyActions.size()-1){
                    nextVideoBtn.setVisibility(View.GONE);
                }
                resetTime();
            }
        });
        preVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextVideoBtn.setVisibility(View.VISIBLE);
                i--;
                if(i==0){
                    preVideoBtn.setVisibility(View.GONE);
                }
                resetTime();
            }
        });
        startPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart){
                    isStart=false;
                    startPlayBtn.setBackground(getResources().getDrawable(R.drawable.stop_btn));
                    if (timeThread!=null&&timeThread.isAlive()){
                        timeThread.interrupt();
                        timeThread=null;
                    }
                }
                else{
                    isStart=true;
                    startPlayBtn.setBackground(getResources().getDrawable(R.drawable.start_btn));
                    if (timeThread==null){
                        timeThread=new Thread(timeRun);
                        timeThread.start();
                    }
                }
            }
        });
        yaoDianTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StartExerciseActivity.this,ActionDetailActivity.class);
                Gson gson=new Gson();
                String dayplanStr=gson.toJson(dayPlan);
                intent.putExtra("dayplan",dayplanStr);
                intent.putExtra("action_name",everyActions.get(i).getName());
                ActionDetailActivity.i=i;
                startActivity(intent);
            }
        });
    }

    /**
     * 监听手机返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exerciseDb();
        m=0;n=0;
        finish();
    }

    /**
     * 设置数据
     */
    private void setData() {
        if(everyActions.get(i).isComplete()){
            i++;
            complete_i++;
        }
        countAction.setText(i+1+"");
        videoNameTx.setText(everyActions.get(i).getName());
        String time=everyActions.get(i).getTime();
        try{
            String str[]=time.split("x");
            actionTimeTx.setText(GetBeforeData.formatTime(Integer.parseInt(str[0])*Integer.parseInt(str[1])));

        } catch (Exception e){
            actionTimeTx.setText(GetBeforeData.formatTime(Integer.parseInt(time)));
        }

    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        countAction=findViewById(R.id.count_action);
        videoSuf=findViewById(R.id.surfaceView);
        backBtn=findViewById(R.id.video_back);
        nextVideoBtn=findViewById(R.id.next_video);
        preVideoBtn=findViewById(R.id.pre_video);
        startPlayBtn=findViewById(R.id.start_play);
        videoNameTx=findViewById(R.id.video_name);
        yaoDianTx=findViewById(R.id.yaodian);
        nowTimeTx=findViewById(R.id.now_time);
        actionTimeTx=findViewById(R.id.action_time);
        countTimeTx=findViewById(R.id.count_time);
        if (i==0){
            preVideoBtn.setVisibility(View.GONE);//刚进入时，向前按钮不能被点击
        }
        if (i==Integer.parseInt(dayPlan.getCountAction())-1){
            nextVideoBtn.setVisibility(View.GONE);
        }
    }

    private void initSurfaceView() {
        videoSuf.setZOrderOnTop(false);
        surfaceHolder=videoSuf.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //设置显示视频显示在surfaceView上
        mPlayer.setDisplay(surfaceHolder);
        play();
        Log.v("1","surfaceCreated");
    }

    /**
     *播放视频
     */
    private void play(){
        try{
            mPlayer.reset();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                    mp.start();
                }
            });
            File file = new File(everyActionUrl.get(i));
            FileInputStream fis = new FileInputStream(file);
            mPlayer.setDataSource(fis.getFD());
            // 准备并播放
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.seekTo(currentPosition);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mPlayer!=null&&mPlayer.isPlaying()){
            currentPosition=mPlayer.getCurrentPosition();
            mPlayer.stop();
        }
        Log.v("1","surfaceDestroyed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPlayer.isPlaying()){
            mPlayer.stop();
        }
        mPlayer.release();
        if (timeThread!=null&&timeThread.isAlive()){
            timeThread.interrupt();
            timeThread=null;
        }
        m=0;n=0;//重置
        currentPosition=0;
        Log.v("1","训练界面onDestroy");
    }

    /**
     * 重置计时器和界面数据
     */
    private void resetTime(){
        currentPosition=0;m=0;
        nowTimeTx.setText("");
        setData();
        play();
        if (timeThread!=null&&timeThread.isAlive()){
            timeThread.interrupt();
            timeThread=null;
        }
        isStart=false;
        startPlayBtn.setBackground(getResources().getDrawable(R.drawable.stop_btn));
    }

    /**
     * 将运动数据存储数据库中
     */
    public void exerciseDb(){
        DecimalFormat df = new DecimalFormat("0.0");
        //计算实际耗能
        factHaoNeng=(complete_i*1.0/everyActions.size()*1.0)*Integer.parseInt(dayPlan.getNengliang());
        dayPlan.setCompleteAction(complete_i+"");
        dayPlan.setCompleteData(GetBeforeData.getBeforeData(null,0).get(0));
//        if(dayPlan.getShijihaoneng()!=null||!TextUtils.isEmpty(dayPlan.getShijihaoneng())){
//            dayPlan.setShijihaoneng(Double.parseDouble(dayPlan.getShijihaoneng())+Double.parseDouble(df.format(factHaoNeng))+"");
//        }
//        else
        dayPlan.setShijihaoneng(df.format(factHaoNeng));
        dayPlan.updateAll("id=?",String.valueOf(dayPlan.getId()));

        List<MotionRecordsEntity> motionRecordsEntitys=DataSupport.where("dayplanId=?",String.valueOf(dayPlan.getId())).find(MotionRecordsEntity.class);
        if(motionRecordsEntitys.size()==0){
            MotionRecordsEntity motionRecordsEntity=new MotionRecordsEntity();
            motionRecordsEntity.setData(dayPlan.getCompleteData());
            motionRecordsEntity.setHaoneng(Double.parseDouble(dayPlan.getShijihaoneng()));
            motionRecordsEntity.setMovement_type("健身");
            motionRecordsEntity.setTime(GetBeforeData.formatTime(n));
            motionRecordsEntity.setMovement_content(dayPlan.getName());
            motionRecordsEntity.setDayplanId(dayPlan.getId());
            motionRecordsEntity.save();
        }
        else{
            MotionRecordsEntity motionRecordsEntity=motionRecordsEntitys.get(0);
            motionRecordsEntity.setHaoneng(Double.parseDouble(dayPlan.getShijihaoneng()));
            motionRecordsEntity.setData(dayPlan.getCompleteData());
            String time[]=motionRecordsEntity.getTime().split(":");
            int t=Integer.parseInt(time[0])*60+Integer.parseInt(time[1])+n;
            motionRecordsEntity.setTime(GetBeforeData.formatTime(t));
            motionRecordsEntity.save();
        }
    }

    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x001){
                Bundle data=msg.getData();
                String time_i=GetBeforeData.formatTime(data.getInt("time"));
                String time_j=GetBeforeData.formatTime(data.getInt("time_count"));
                String str=actionTimeTx.getText().toString();
                nowTimeTx.setText(time_i+"/");
                countTimeTx.setText(time_j);
                if(time_i.equals(str)&&data.getInt("time")!=0&&i!=everyActions.size()-1){
                    EveryActionInfo everyActionInfo=new EveryActionInfo();
                    everyActionInfo.setComplete(true);//设置该动作已完成
                    everyActionInfo.updateAll("id=?",String.valueOf(everyActions.get(i).getId()));
                    preVideoBtn.setClickable(true);
                    i++;
                    if(i==everyActions.size()-1){
                        nextVideoBtn.setClickable(false);
                    }
                    resetTime();
                    time_i="";
                    complete_i++;
                }
                if(time_i.equals(str)&&data.getInt("time")!=0&&i==everyActions.size()-1){
                    EveryActionInfo everyActionInfo=new EveryActionInfo();
                    everyActionInfo.setComplete(true);//设置该动作已完成
                    everyActionInfo.updateAll("id=?",String.valueOf(everyActions.get(i).getId()));
                    complete_i++;
                    Intent intent=new Intent(StartExerciseActivity.this,CompleteActivity.class);
                    exerciseDb();
                    intent.putExtra("count_time",time_j);
                    intent.putExtra("nengliang",factHaoNeng);
                    intent.putExtra("count_action",complete_i+"");
                    startActivity(intent);
                    ActivityCollector.finishAll();
                    finish();
                }
            }
        }
    }
}
