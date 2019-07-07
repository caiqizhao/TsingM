package com.example.guaiwei.tsingm.utils;

import android.app.Application;
import android.content.Context;

import org.litepal.*;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePalApplication.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
