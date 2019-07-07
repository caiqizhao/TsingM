package com.example.guaiwei.tsingm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.DownloadUtil;
import com.example.guaiwei.tsingm.utils.VariableUtil;
import com.example.guaiwei.tsingm.adapter.PlanDetailAdapter;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 每天训练的每个动作动作页面
 */
public class ExerciseListActivity extends BaseActivity {

    public static Handler handler;//消息处理
    private RecyclerView recyclerView;
    DayPlanInfo dayPlan;//每天的计划数据
    private Button startButton;//开始训练按钮
    private List<EveryActionInfo> everyActions;//数据源
    private TextView timeCountTV,actionCountTV, fatBurningTV;//显示一天训练的总时长，总动作数量，总燃脂的文本框
    private ProgressDialog progressDialog;//进度对话框

    private List<String> actionUrl=new ArrayList<>();
    private List<String> actionName=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        handler=new MessageUtil();
        String dayPlanStr=getIntent().getStringExtra("day_plan");//获取传递过来的每天的训练数据
        final Gson gson=new Gson();
        dayPlan=(DayPlanInfo) gson.fromJson(dayPlanStr,DayPlanInfo.class);//解析成DayPlan对象
        initActionAdapter();
        setTextViewData();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                Log.v("1","下载");
                DownloadUtil.downFile(actionUrl,actionName,"mp4");
            }
        });
    }

    /**
     * 设置总时长，总动作数量，总燃脂的文本框数据
     */
    private void setTextViewData() {
        //各组件实例化
        timeCountTV=(TextView) findViewById(R.id.duration);
        actionCountTV=(TextView) findViewById(R.id.action_count);
        fatBurningTV=(TextView) findViewById(R.id.fatBurning);
        startButton=(Button) findViewById(R.id.start_work);
        //设置文本框内容
        timeCountTV.setText(dayPlan.getTime()+"");
        actionCountTV.setText(dayPlan.getCountAction()+"");
        fatBurningTV.setText(dayPlan.getNengliang()+"");
        for(int i=0;i<everyActions.size();i++){
            actionUrl.add(VariableUtil.Service_IP+everyActions.get(i).getId()+".mp4");
            actionName.add(everyActions.get(i).getName());
        }
    }

    /**
     * 初始化recyclerView的适配器
     */
    private void initActionAdapter(){
        everyActions=DataSupport.where("dayId=?",String.valueOf(dayPlan.getId())).find(EveryActionInfo.class);//获得数据源
        recyclerView=(RecyclerView) findViewById(R.id.motionList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        PlanDetailAdapter planDetailAdapter=new PlanDetailAdapter(everyActions,dayPlan);
        recyclerView.setAdapter(planDetailAdapter);
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在下载训练视频...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x001) {
                for(int i=0;i<everyActions.size();i++){
                    DownloadUtil.updateMedia(DownloadUtil.getDownFilePath(actionName.get(i),"mp4"));
                }
                Intent intent = new Intent(ExerciseListActivity.this, StartExerciseActivity.class);//成功获取服务器传递的计划数据，跳转到主界面
                Gson gson=new Gson();
                String str=gson.toJson(everyActions);
                String dayplanStr=gson.toJson(dayPlan);
                intent.putExtra("every_actions",str);
                intent.putExtra("dayplan",dayplanStr);
                startActivity(intent);
                closeProgressDialog();
            } else if (msg.what == 0x002) {
                Toast.makeText(ExerciseListActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
       }
    }
}
