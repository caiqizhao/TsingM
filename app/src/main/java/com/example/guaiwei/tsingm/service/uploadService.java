package com.example.guaiwei.tsingm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class uploadService extends Service {
    public uploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String user_id = intent.getStringExtra("user_id");
        final String bushu = intent.getStringExtra("bushu");
        final String time = intent.getStringExtra("time");
        Thread addContacks = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id",user_id)
                        .add("bushu", bushu)
                        .add("time", time)
                        .build();
                Request request = new Request.Builder()
                        .url("StepNumber/uploadBuShu.do")
                        .post(requestBody)
                        .build();
                try {
                    Response response =  client.newCall(request).execute();
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        addContacks.start();
        return START_REDELIVER_INTENT;
    }
}
