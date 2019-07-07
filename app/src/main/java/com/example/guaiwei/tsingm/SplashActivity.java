package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guaiwei.tsingm.question.SportTargetActivity;
import com.example.guaiwei.tsingm.utils.FullScreenUtil;
import com.example.guaiwei.tsingm.activity.LoginActivity;

/**
 * 启动欢迎界面
 */
public class SplashActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    protected int splashTime=3000;
    private Thread splashThread;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置全屏
        FullScreenUtil.setFullScreen(this);
        textView=findViewById(R.id.title);
        imageView=findViewById(R.id.image);
        //得到AssetManager
        AssetManager mgr=getAssets();

//根据路径得到Typeface
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/comic sans ms.ttf");

//设置字体
        textView.setTypeface(tf);


        final SplashActivity splashActivity=this;
        pref=PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        editor=pref.edit();

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
                    boolean isFirst = pref.getBoolean("isFirst",true);
                    if(isFirst){
                        Intent intent=new Intent(splashActivity,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent =new Intent(splashActivity,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        splashThread.start();
    }
    //设置图片为圆角图片
    private void rectRoundBitmap(){
        //得到资源文件的BitMap
        Bitmap image= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        //创建RoundedBitmapDrawable对象
        RoundedBitmapDrawable roundImg =RoundedBitmapDrawableFactory.create(getResources(),image);
        //抗锯齿
        roundImg.setAntiAlias(true);
        //设置圆角半径
        roundImg.setCornerRadius(15);
        //设置显示图片
        imageView.setImageDrawable(roundImg);
    }
}
