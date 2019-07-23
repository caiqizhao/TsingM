package com.example.guaiwei.tsingm.Evaluate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.example.guaiwei.tsingm.db.NutrimentInfo;
import com.example.guaiwei.tsingm.service.PlanService;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.gson.User;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.utils.ToastUtil;
import com.example.guaiwei.tsingm.utils.Utility;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 判断用户心肺功能的问题界面
 */
public class CardioPulmonaryActivity extends BaseActivity {
    private Button nextButton;//下一步按钮
    private RadioGroup CPRadio;//判断用户心肺功能问题的单选按钮组
    private ImageView CPImage;//背景动图
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public static Handler handler;//消息处理
    private ProgressDialog progressDialog;//进度对话框

    private List<String> stringData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_pulmonary);
        //获取相应的控件
        nextButton=(Button) findViewById(R.id.next3);
        CPRadio=(RadioGroup) findViewById(R.id.radio_cardio_pulmonary);
        CPImage=(ImageView) findViewById(R.id.xingfei_image);
        //设置按钮为不可点击
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.5f);//设置按钮的透明度

        prefs=PreferenceManager.getDefaultSharedPreferences(CardioPulmonaryActivity.this);
        editor=prefs.edit();

        handler=new MessageUtil();

        Glide.with(this).load(R.mipmap.upstair).into(CPImage);

        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                //将用户数据信息存入数据库
                String userData=new Gson().toJson(User.user);
                editor.putString("user_data",userData);
                editor.apply();
                Intent intent=new Intent(CardioPulmonaryActivity.this,PlanService.class);
                startService(intent);//开启服务，向服务器发送请求，请求训练计划

            }
        });
        //为单选按钮添加点击事件
        CPRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.cardio_pulmonary1:
                        User.user.getUserFitnessStage().setCardioPulmonary(1);//如果用户选择了第1项，则将用户的心肺功能设置为1
                        break;
                    case R.id.cardio_pulmonary2:
                        User.user.getUserFitnessStage().setCardioPulmonary(2);//如果用户选择了第2项，则将用户的心肺功能设置为2
                        break;
                    case R.id.cardio_pulmonary3:
                        User.user.getUserFitnessStage().setCardioPulmonary(3);//如果用户选择了第3项，则将用户的心肺功能设置为3
                        break;
                    case R.id.cardio_pulmonary4:
                        User.user.getUserFitnessStage().setCardioPulmonary(5);//如果用户选择了第4项，则将用户的心肺功能设置为4
                        break;
                    default:
                        break;
                }
            }
        });
        ChangeColor.changeColor(this,Color.parseColor("#80000000"));//改变状态栏颜色
    }

    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x001){
                //获得消息中的数据赋值给用户计划实例
                Bundle data = msg.getData();
                String str = data.getString("plan_data");
//                User_Plan userPlan=Utility.handleUserPlanResponse(str);
//                if(userPlan!=null){
//                    editor=PreferenceManager.getDefaultSharedPreferences(FlexibilityActivity.this).edit();
//                    editor.putString("user_plan",str);
//                    editor.apply();
//                }
                if(prefs.getBoolean("isEvaluate",false)){
                    DataSupport.deleteAll(DayPlanInfo.class);
                    DataSupport.deleteAll(EveryActionInfo.class);
                    DataSupport.deleteAll(NutrimentInfo.class);
                }
                Utility.dbSave(str);//将服务器传递的数据存储到数据库中
                stringData=GetBeforeData.getBeforeData(null, 20);//存储日期列表
                Gson gson=new Gson();
                String dataStr=gson.toJson(stringData);
                // 设置用户评估状态为已进行评估
                editor.putBoolean("isEvaluate",true);
                editor.putString("plan_data",dataStr);
                editor.apply();
                closeProgressDialog();
                Intent intent=new Intent(CardioPulmonaryActivity.this,MainActivity.class);
                //开启新的activity，进入下一个问题页面
                startActivity(intent);
//                MainActivity.isEvaluate=true;
                ActivityCollector.finishAll();//销毁之前的活动
            }
            else if(msg.what==0x002){
                closeProgressDialog();
                Bundle data = msg.getData();
                String str = data.getString("plan_data");
                ToastUtil.showToast(CardioPulmonaryActivity.this,str);
            }
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在生成训练&饮食计划...");
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
}
