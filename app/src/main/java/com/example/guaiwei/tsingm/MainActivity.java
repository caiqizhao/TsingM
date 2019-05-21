package com.example.guaiwei.tsingm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.User_Plan;
import com.example.guaiwei.tsingm.fragment.EvaluateBeforeFragment;
import com.example.guaiwei.tsingm.fragment.FoodFragment;
import com.example.guaiwei.tsingm.fragment.MyFragment;
import com.example.guaiwei.tsingm.fragment.PlanFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static boolean isEvaluate=false;//判断用户是否已经进行评估
    private Toolbar toolbar;
    private Fragment planFragment,foodFragment,myFragment,evaluateBeforeFragment;
    private LinearLayout planLayout,foodLayout,myLayout;
    private ImageView planImage,foodImage,myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        // 实例化顶部ToolBar
        toolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        setSupportActionBar(toolbar);
        initView();
    }

    // 初始化组件视图
    private void initView() {
        // 实例化顶部ToolBar
        toolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        setSupportActionBar(toolbar);
        //实例化4个Fragment
        if(User_Plan.userPlan!=null)
            planFragment=new PlanFragment();
        foodFragment=new FoodFragment();
        myFragment=new MyFragment();
        evaluateBeforeFragment=new EvaluateBeforeFragment();

        replaceFragment(planFragment);
        //实例化底部导航栏的控件
        planLayout=findViewById(R.id.plan_fragment);
        foodLayout=findViewById(R.id.food_fragment);
        myLayout=findViewById(R.id.me_fragment);
        planImage=findViewById(R.id.plan_image);
        foodImage=findViewById(R.id.food_image);
        myImage=findViewById(R.id.me_image);
        planLayout.setOnClickListener(MainActivity.this);
        foodLayout.setOnClickListener(MainActivity.this);
        myLayout.setOnClickListener(MainActivity.this);
        planImage.setImageResource(R.drawable.plan_image_after);
        if(isEvaluate){
            replaceFragment(planFragment);
        }
        else
            replaceFragment(evaluateBeforeFragment);
    }

    /**
     * 设置点击选项卡的事件处理
     */
    @Override
    public void onClick(View v) {
        Bundle data;
        switch (v.getId()) {
            case R.id.plan_fragment:
                clearChioce();
                //改变标题
                toolbar.setTitle("智能训练计划");
                planImage.setImageResource(R.drawable.plan_image_after);
                if(isEvaluate){
                    replaceFragment(planFragment);
                }
                else
                    replaceFragment(evaluateBeforeFragment);
                break;
            case R.id.food_fragment:
                clearChioce();
                foodImage.setImageResource(R.drawable.food_image_after);
                toolbar.setTitle("饮食");
                replaceFragment(foodFragment);
                break;
            case R.id.me_fragment:
                clearChioce();
                myImage.setImageResource(R.drawable.me_image_after);
                toolbar.setTitle("我的");
                replaceFragment(myFragment);
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager ft=getSupportFragmentManager();
        FragmentTransaction ftr=ft.beginTransaction();
        if (fragment!=null)
            ftr.replace(R.id.fl_container,fragment);
        ftr.commit();
    }
    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
        planImage.setImageResource(R.drawable.plan_image);
        foodImage.setImageResource(R.drawable.food_image);
        myImage.setImageResource(R.drawable.me_image);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
