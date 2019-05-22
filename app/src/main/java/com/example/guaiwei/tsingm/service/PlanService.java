package com.example.guaiwei.tsingm.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.guaiwei.tsingm.Evaluate.FlexibilityActivity;
import com.example.guaiwei.tsingm.Utils.VariableUtil;
import com.example.guaiwei.tsingm.bean.User;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlanService extends Service {
    Thread plan;
    public PlanService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = intent.getExtras();
        User user = (User)data.getSerializable("user_data");
        Gson gson = new Gson();
        final String userJson = gson.toJson(user);
        plan = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_data",userJson)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"TsingM/plan/Recommend.do")
                        .post(requestBody)
                        .build();
                String str="网络连接失败，请重试";//初始化服务器返回的数据
                try {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    Response response = client.newCall(request).execute();
                    str = response.body().string();
                    data.putString("plan_data",str);
                    message.setData(data);
                    message.what = 0x001;
                    FlexibilityActivity.handler.sendMessage(message);
                    stopSelf();//关闭服务
                } catch (IOException e) {
                    Message message = new Message();
                    Log.v("1","1");
                    Bundle data = new Bundle();
                    Log.v("1","2");
                    data.putString("plan_data",str);
                    Log.v("1","3");
                    message.setData(data);
                    Log.v("1","4");
                    message.what = 0x002;
                    Log.v("1","5");
                    FlexibilityActivity.handler.sendMessage(message);
                    Log.v("1","6");
                }
            }
        });
        plan.start();
        return START_STICKY;
    }
}
