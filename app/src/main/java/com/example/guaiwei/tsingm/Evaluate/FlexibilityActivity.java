package com.example.guaiwei.tsingm.Evaluate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.FitnessStage;
import com.example.guaiwei.tsingm.bean.User;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 判断用户坐姿体前屈的程度的界面，测试用户的髋关节柔韧性
 */
public class FlexibilityActivity extends AppCompatActivity {
    private User user;
    private Button submitButton;//提交按钮
    private RadioGroup FRadio;//判断用户下肢耐力问题的单选按钮组
    //用于接收Http请求的servlet的URL地址
    private String originAddress = "http://192.168.43.124:8080/TsingMWeb/Recommend";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexibility);
        //获取相应的控件
        submitButton=findViewById(R.id.evaluate_complete);
        FRadio=findViewById(R.id.radio_flexibility);
        //设置按钮为不可点击
        submitButton.setEnabled(false);
        submitButton.setAlpha(0.5f);//设置按钮的透明度
        user=(User)getIntent().getSerializableExtra("user_data");
        //为提交按钮设置点击事件，将用户的测试数据提交至服务器
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将用户的数据提交至服务器
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            String userJson = gson.toJson(user);
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("user_data",userJson)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(originAddress)
                                    .post(requestBody)
                                    .build();
                            client.newCall(request).execute();
//                            Response response = client.newCall(request).execute();
                            System.out.println("成功发送"+userJson);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                //弹出对话框，询问用户是否生成训练计划
                createDialog();
                ActivityCollector.finishAll();
                Toast.makeText(FlexibilityActivity.this, "1."+user.getSex()+user.getUserBirthday().toString()+","+user.getHeight()+","+user.getWeight()+","+user.getExerciseSite()[0]+","+user.getExerciseSite()[1]
                        +user.getUserFitnessStage().getCurrentSituation()+","+user.getUserFitnessStage().getSportTarget()+","+user.getUserFitnessStage().getCardioPulmonary()+","+user.getUserFitnessStage().getChestEndurance()+","
                        +user.getUserFitnessStage().getAbdominalEndurance()+","+user.getUserFitnessStage().getLowerLimb()+","+user.getUserFitnessStage().getFlexibility()+".", Toast.LENGTH_SHORT).show();
            }
        });
        //为单选按钮添加点击事件
        FRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submitButton.setEnabled(true);
                submitButton.setAlpha(1.0f);//设置按钮的透明度
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
                        user.getUserFitnessStage().setFlexibility(4);//如果用户选择了第4项，则将用户的髋关节柔韧性设置为4
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void createDialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(FlexibilityActivity.this);
        dialog.setTitle("训练计划");
        dialog.setMessage("是否生成训练计划");
        dialog.setCancelable(false);//不能通过Back关闭
        //点击确定的事件处理
        dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.isEvaluate=true;//设置用户评估状态为已进行评估
                Intent intent=new Intent(FlexibilityActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //点击取消的事件处理
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(FlexibilityActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }
}
