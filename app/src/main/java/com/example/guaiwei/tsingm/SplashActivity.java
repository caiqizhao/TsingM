package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guaiwei.tsingm.question.SportTargetActivity;

/**
 * 启动欢迎界面
 */
public class SplashActivity extends AppCompatActivity {
//    public static SharedPreferences.Editor editor;
    protected int splashTime=3000;
    private Thread splashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SplashActivity splashActivity=this;
//        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
//        editor.apply();
        splashThread=new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    synchronized (this){
                        wait(splashTime);
                    }
                }catch (InterruptedException e){}
                finally {
                    finish();
//                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
//                    boolean isFirst = pref.getBoolean("isFirst",true);
//                    if(isFirst){
                        Intent intent=new Intent(splashActivity,SportTargetActivity.class);
                        startActivity(intent);
//                    }
//                    else{
//                        Intent intent =new Intent(splashActivity,MainActivity.class);
//                        startActivity(intent);
//                    }
                }
            }
        };
        splashThread.start();
    }
}
