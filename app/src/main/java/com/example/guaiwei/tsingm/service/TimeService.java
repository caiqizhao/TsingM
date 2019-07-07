package com.example.guaiwei.tsingm.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.guaiwei.tsingm.activity.StartExerciseActivity;

public class TimeService extends Service {
    private Thread timeThread;
    private Message msg;
    public static int i=0,j=0;
    public TimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Runnable timeRun=new Runnable() {
            @Override
            public void run() {
                try {
                    while(!Thread.interrupted()){
                        msg=new Message();//生成消息
                        msg.what=0x001;//设置消息类型
                        Bundle data=new Bundle();//生成Bundle携带消息
                        data.putInt("time",i);
                        data.putInt("time_count",j);
                        msg.setData(data);
                        StartExerciseActivity.mHandler.sendMessage(msg);
                        i++;j++;
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    Log.v("timeThread","线程终止了");
                }
            }
        };
        timeThread=new Thread(timeRun);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //isAlive方法用于判断线程是否被开启
        while (!timeThread.isAlive()){
            timeThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeThread!=null){
            timeThread.interrupt();
        }
    }
}
