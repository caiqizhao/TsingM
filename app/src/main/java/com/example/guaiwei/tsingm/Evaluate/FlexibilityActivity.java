package com.example.guaiwei.tsingm.Evaluate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.Utils.GetBeforeData;
import com.example.guaiwei.tsingm.Utils.ToastUtil;
import com.example.guaiwei.tsingm.bean.User;
import com.example.guaiwei.tsingm.bean.User_Plan;
import com.example.guaiwei.tsingm.service.PlanService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断用户坐姿体前屈的程度的界面，测试用户的髋关节柔韧性
 */
public class FlexibilityActivity extends AppCompatActivity {
    private User user;
    private Button submitButton;//提交按钮
    private RadioGroup FRadio;//判断用户下肢耐力问题的单选按钮组
    public static List<String> StringData=new ArrayList<>();
    public static Handler handler;//消息处理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexibility);
        //获取相应的控件
        submitButton=findViewById(R.id.evaluate_complete);
        FRadio=findViewById(R.id.radio_flexibility);
        handler=new MessageUtil();
        //设置按钮为不可点击
        submitButton.setEnabled(false);
        submitButton.setAlpha(0.5f);//设置按钮变透明
        user=(User)getIntent().getSerializableExtra("user_data");
        //为提交按钮设置点击事件，将用户的测试数据提交至服务器
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框，询问用户是否生成训练计划
                createDialog();
//                Toast.makeText(FlexibilityActivity.this, "1."+user.getSex()+user.getUserBirthday().toString()+","+user.getHeight()+","+user.getWeight()+","+user.getExerciseSite()[0]+","+user.getExerciseSite()[1]
//                        +user.getUserFitnessStage().getCurrentSituation()+","+user.getUserFitnessStage().getSportTarget()+","+user.getUserFitnessStage().getCardioPulmonary()+","+user.getUserFitnessStage().getChestEndurance()+","
//                        +user.getUserFitnessStage().getAbdominalEndurance()+","+user.getUserFitnessStage().getLowerLimb()+","+user.getUserFitnessStage().getFlexibility()+".", Toast.LENGTH_SHORT).show();
            }
        });
        //为单选按钮添加点击事件
        FRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submitButton.setEnabled(true);//设置Button可点击
                submitButton.setAlpha(1.0f);//设置按钮恢复颜色
                switch (i) {
                    case R.id.radio_flexibility1:
                        user.getUserFitnessStage().setFlexibility(1);//如果用户选择了第1项，则将用户的髋关节柔韧性设置为1
                        break;
                    case R.id.radio_flexibility2:
                        user.getUserFitnessStage().setFlexibility(2);//如果用户选择了第2项，则将用户的髋关节柔韧性设置为2
                        break;
                    case R.id.radio_flexibility3:
                        user.getUserFitnessStage().setFlexibility(3);//如果用户选择了第3项，则将用户的髋关节柔韧性设置为3
                        break;
                    case R.id.radio_flexibility4:
                        user.getUserFitnessStage().setFlexibility(5);//如果用户选择了第4项，则将用户的髋关节柔韧性设置为4
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 创建对话框
     */
    private void createDialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(FlexibilityActivity.this);//实例化一个对话框
        dialog.setTitle("训练计划");//设置对话框的标题
        dialog.setMessage("是否生成训练计划");//设置对话框的提示信息
        dialog.setCancelable(false);//不能通过Back关闭
        //点击确定的事件处理
        dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(FlexibilityActivity.this,PlanService.class);
                intent.putExtra("user_data",user);
                startService(intent);//开启服务，向服务器发送请求，请求训练计划

            }
        });
        //点击取消的事件处理
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(FlexibilityActivity.this,MainActivity.class);
                startActivity(intent);
                ActivityCollector.finishAll();//销毁之前的活动
                finish();
            }
        });
        dialog.show();
    }

    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("1","before");
            if(msg.what == 0x001){
                //获得消息中的数据赋值给用户计划实例
                Bundle data = msg.getData();
                String str = data.getString("plan_data");
                Gson gson=new Gson();
                User_Plan.userPlan=gson.fromJson(str,User_Plan.class);//解析JSON数据
                StringData= GetBeforeData.getBeforeData(null, 20);//存储日期列表
                Intent intent=new Intent(FlexibilityActivity.this,MainActivity.class);//成功获取服务器传递的计划数据，跳转到主界面
                startActivity(intent);
                MainActivity.isEvaluate=true;//设置用户评估状态为已进行评估
                ActivityCollector.finishAll();//销毁之前的活动
                finish();
            }
            else if(msg.what==0x002){
                Log.v("1","7");
                Bundle data = msg.getData();
                Log.v("1","8");
                String str = data.getString("plan_data");
                Log.v("1","9");
                ToastUtil.showToast(FlexibilityActivity.this,str);
                Log.v("1","0");
            }
        }
    }
}
