package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.guaiwei.tsingm.bean.BaseActivity;
import com.example.guaiwei.tsingm.fragment.EvaluateBeforeFragment;
import com.example.guaiwei.tsingm.fragment.FoodFragment;
import com.example.guaiwei.tsingm.fragment.MyFragment;
import com.example.guaiwei.tsingm.fragment.PlanFragment;

public class MainActivity extends BaseActivity {
    public static boolean isEvaluate=false;//判断用户是否已经进行评估
    private Toolbar toolbar;//顶部标题栏
    BottomNavigationView navigation;   //底部菜单栏
    MenuItem signOutPlan,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化组件视图
     */
    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 实例化顶部ToolBar
        toolbar = (Toolbar) findViewById(R.id.my_tool_bar);
        setSupportActionBar(toolbar);
        //如果用户已经完成测评则显示计划的界面，否则显示提示测评的界面
        if(isEvaluate){
            replaceFragment(new PlanFragment());
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
                        replaceFragment(new PlanFragment());
                        signOutPlan.setVisible(true);
                        search.setVisible(false);
                    }
                    else{
                        replaceFragment(new EvaluateBeforeFragment());
                        signOutPlan.setVisible(false);
                        search.setVisible(false);
                    }
                    toolbar.setTitle("智能训练计划");
                    return true;
                case R.id.navigation_food:
                    replaceFragment(new FoodFragment());
                    toolbar.setTitle("饮食规划");
                    signOutPlan.setVisible(false);
                    search.setVisible(true);
                    return true;
                case R.id.navigation_Me:
                    replaceFragment(new MyFragment());
                    toolbar.setTitle("我的");
                    signOutPlan.setVisible(false);
                    search.setVisible(false);
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

    /**
     * menu初始化
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        signOutPlan=menu.findItem(R.id.sign_out_plan);
        search=menu.findItem(R.id.search);
        signOutPlan.setVisible(false);
        search.setVisible(false);
        return true;
    }

    /**
     * menu点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Intent intent=new Intent(MainActivity.this,SearchFoodActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_out_plan:
                isEvaluate=false;
                replaceFragment(new EvaluateBeforeFragment());
                break;
            default:
                break;
        }
        return true;
    }
}
