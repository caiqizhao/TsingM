package com.example.guaiwei.tsingm;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.utils.Util;
import com.example.guaiwei.tsingm.footInterface.UpdateUiCallBack;
import com.example.guaiwei.tsingm.fragment.EvaluateBeforeFragment;
import com.example.guaiwei.tsingm.fragment.ExerciseFragment;
import com.example.guaiwei.tsingm.fragment.FoodFragment;
import com.example.guaiwei.tsingm.fragment.MyFragment;
import com.example.guaiwei.tsingm.service.BindService;

public class MainActivity extends AppCompatActivity {
    private boolean isEvaluate;//判断用户是否已经进行评估
    BottomNavigationView navigation;   //底部菜单栏
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private View statusBarView;

    //计步服务
    private BindService bindService;
    private boolean isBind = false;
    public static double bushu;//步数

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                bushu = msg.arg1;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.getPermissions(this);
        ActivityCollector.addActivity(this);
//        //设置状态栏颜;
//        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                initStatusBar();
//                getWindow().getDecorView().removeOnLayoutChangeListener(this);
//            }
//        });
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        isEvaluate=pref.getBoolean("isEvaluate",false);

        initView();
        //启动计步服务
        Intent intent = new Intent(this, BindService.class);
        isBind =  bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        System.out.println(isBind);
        startService(intent);
        System.out.println("启动服务");
        ChangeColor.changeColor(this,Color.parseColor("#30464646"));
    }


//    private void initStatusBar() {
//        if (statusBarView == null) {
//            //利用反射机制修改状态栏背景
//            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
//            statusBarView = getWindow().findViewById(identifier);
//        }
//        if (statusBarView != null) {
//            statusBarView.setBackgroundResource(R.drawable.my_bg);
//        }
//    }

    /**
     * 初始化组件视图
     */
    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //如果用户已经完成测评则显示计划的界面，否则显示提示测评的界面
        if(isEvaluate){
            replaceFragment(new ExerciseFragment());
        }
        else{
            replaceFragment(new EvaluateBeforeFragment());
        }
    }

    /**
     * 注册底部控件响应事件
     * taobolisb
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_plan:
                    if(isEvaluate){
                        replaceFragment(new ExerciseFragment());
                        ChangeColor.changeColor(MainActivity.this,Color.parseColor("#20464646"));
                    }
                    else{
                        replaceFragment(new EvaluateBeforeFragment());
                        ChangeColor.changeColor(MainActivity.this,Color.parseColor("#20464646"));
                    }
                    return true;
                case R.id.navigation_food:
                    replaceFragment(new FoodFragment());
                    ChangeColor.changeColor(MainActivity.this,Color.parseColor("#24C68A"));
                    return true;
                case R.id.navigation_Me:
                    replaceFragment(new MyFragment());
                    ChangeColor.changeColor(MainActivity.this,Color.parseColor("#24C68A"));
                    return true;
            }
            return false;
        }
    };

    /**
     * fragment替换事件
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager ft=getSupportFragmentManager();
        FragmentTransaction ftr=ft.beginTransaction();
        ftr.replace(R.id.fl_container,fragment);
        ftr.commit();
    }

//    /**
//     * menu初始化
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        signOutPlan=menu.findItem(R.id.sign_out_plan);
//        search=menu.findItem(R.id.search);
//        if(isEvaluate){
//            signOutPlan.setVisible(true);
//            search.setVisible(false);
//        }else{
//            signOutPlan.setVisible(false);
//            search.setVisible(false);
//        }
//        return true;
//    }

//    /**
//     * menu点击事件
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.search:
//                Intent intent=new Intent(MainActivity.this,SearchFoodActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.sign_out_plan:
//                editor.putBoolean("isEvaluate",false);
//                editor.apply();
//                //删除数据库中的训练计划数据
//                DataSupport.deleteAll(DayPlanInfo.class);
//                DataSupport.deleteAll(EveryActionInfo.class);
//
//                replaceFragment(new EvaluateBeforeFragment());
//                signOutPlan.setVisible(false);
//                search.setVisible(false);
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    //和绷定服务数据交换的桥梁，可以通过IBinder service获取服务的实例来调用服务的方法或者数据
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindService.LcBinder lcBinder = (BindService.LcBinder) service;
            bindService = lcBinder.getService();
            bindService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    //当前接收到stepCount数据，就是最新的步数
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = stepCount;
                    handler.sendMessage(message);
                    Log.i("MainActivity—updateUi","当前步数"+stepCount);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onDestroy() {  //app被关闭之前，service先解除绑定
        super.onDestroy();
        if (isBind) {
            this.unbindService(serviceConnection);
        }
        ActivityCollector.removeActivity(this);
    }
}
