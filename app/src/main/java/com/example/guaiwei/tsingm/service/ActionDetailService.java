package com.example.guaiwei.tsingm.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.guaiwei.tsingm.ActionDetailActivity;
import com.example.guaiwei.tsingm.Evaluate.FlexibilityActivity;
import com.example.guaiwei.tsingm.ExerciseListActivity;
import com.example.guaiwei.tsingm.Utils.VariableUtil;
import com.example.guaiwei.tsingm.bean.xx_action;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 获取每个动作的详细说明的服务
 */
public class ActionDetailService extends Service {
    public ActionDetailService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String actionName=intent.getStringExtra("action_name");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("action_name",actionName)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"plan/Action.do")
                        .post(requestBody)
                        .build();
                String str="网络连接失败，请重试";//初始化服务器返回的数据
                try {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    Response response = client.newCall(request).execute();
                    str = response.body().string();
                    data.putString("action_detail",str);
                    Log.v("1","发送"+str);
                    message.setData(data);
                    message.what = 0x003;
                    ExerciseListActivity.handler.sendMessage(message);
                    stopSelf();//关闭服务
                } catch (IOException e) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString("action_detail",str);
                    message.setData(data);
                    message.what = 0x004;
                    ExerciseListActivity.handler.sendMessage(message);
                }
            }
        }).start();
        return START_STICKY;
    }
}
