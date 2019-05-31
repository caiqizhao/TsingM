package com.example.guaiwei.tsingm;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guaiwei.tsingm.Evaluate.ExerciseSiteActivity;
import com.example.guaiwei.tsingm.Utils.ToastUtil;
import com.example.guaiwei.tsingm.adapter.PlanDetailAdapter;
import com.example.guaiwei.tsingm.bean.DayPlan;
import com.example.guaiwei.tsingm.bean.EveryAction;
import com.example.guaiwei.tsingm.bean.User_Plan;
import com.example.guaiwei.tsingm.bean.xx_action;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每天训练的每个动作动作页面
 */
public class ExerciseListActivity extends AppCompatActivity {

    public static Handler handler;//消息处理
    private RecyclerView recyclerView;
    DayPlan dayPlan;//每天的计划数据
    private Button startButton;//开始训练按钮
    private List<EveryAction> everyActions;//数据源
    private TextView timeCountTV,actionCountTV, fatBurningTV;//显示一天训练的总时长，总动作数量，总燃脂的文本框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        handler=new MessageUtil();
        String dayPlanStr=getIntent().getStringExtra("day_plan");//获取传递过来的每天的训练数据
        Gson gson=new Gson();
        dayPlan=(DayPlan)gson.fromJson(dayPlanStr,DayPlan.class);//解析成DayPlan对象
        initActionAdapter();
        setTextViewData();
    }

    /**
     * 设置总时长，总动作数量，总燃脂的文本框数据
     */
    private void setTextViewData() {
        //各组件实例化
        timeCountTV=findViewById(R.id.duration);
        actionCountTV=findViewById(R.id.action_count);
        fatBurningTV=findViewById(R.id.fatBurning);
        startButton=findViewById(R.id.start_work);
        //设置文本框内容
        timeCountTV.setText(dayPlan.getTime()+"分钟");
        actionCountTV.setText(dayPlan.getCountAction()+"个");
        fatBurningTV.setText(dayPlan.getNengliang()+"千卡");
    }

    /**
     * 初始化recyclerView的适配器
     */
    private void initActionAdapter(){
        everyActions=dayPlan.getActions();//获得数据源
        recyclerView=findViewById(R.id.motionList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        PlanDetailAdapter planDetailAdapter=new PlanDetailAdapter(everyActions);
        recyclerView.setAdapter(planDetailAdapter);
    }
    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x003){
                //获得消息中的数据赋值给用户计划实例
                Bundle data = msg.getData();
                String str=data.getString("action_detail");
                Log.v("1","接收"+str);
                Gson gson=new Gson();
                xx_action xxAction=(xx_action)gson.fromJson(str,xx_action.class);
                Intent intent=new Intent(ExerciseListActivity.this,ActionDetailActivity.class);//成功获取服务器传递的计划数据，跳转到主界面
                String s=gson.toJson(xxAction);
                String dayPlayStr=gson.toJson(dayPlan);
                intent.putExtra("action_detail",s);
                intent.putExtra("count_action",dayPlan.getCountAction());
                intent.putExtra("dayplan",dayPlayStr);
                startActivity(intent);
            }
            else if(msg.what==0x004){
                Bundle data = msg.getData();
                String str = data.getString("action_detail");
                ToastUtil.showToast(ExerciseListActivity.this,str);
            }
        }
    }
}
