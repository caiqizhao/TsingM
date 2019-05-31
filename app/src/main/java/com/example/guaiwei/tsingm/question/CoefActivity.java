package com.example.guaiwei.tsingm.question;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.guaiwei.tsingm.Collector.ActivityCollector;
import com.example.guaiwei.tsingm.Evaluate.FlexibilityActivity;
import com.example.guaiwei.tsingm.MainActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.SplashActivity;
import com.example.guaiwei.tsingm.Utils.GetBeforeData;
import com.example.guaiwei.tsingm.Utils.ToastUtil;
import com.example.guaiwei.tsingm.bean.BaseActivity;
import com.example.guaiwei.tsingm.bean.Nutriment;
import com.example.guaiwei.tsingm.bean.User;
import com.example.guaiwei.tsingm.bean.User_Plan;
import com.example.guaiwei.tsingm.service.FoodService;
import com.google.gson.Gson;

/**
 * 用户的平时活动系数
 */
public class CoefActivity extends BaseActivity {

    public static Handler handler;//消息处理
    private Button submitButton;//完成按钮
    private RadioGroup coefRadio;//判断用户运动目标的单选按钮组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coef);
        handler=new MessageUtil();

        //获取相应的控件
        submitButton=findViewById(R.id.next_coef);
        coefRadio=findViewById(R.id.radio_coef);

        //设置按钮为不可点击
        submitButton.setEnabled(false);
        submitButton.setAlpha(0.5f);//设置按钮的透明度

        //为下一步按钮设置点击事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CoefActivity.this,FoodService.class);
                //开启服务向服务器发出请求
                startService(intent);
//                Intent intent=new Intent(CoefActivity.this,MainActivity.class);//成功获取服务器传递的计划数据，跳转到主界面
//                startActivity(intent);
            }
        });

        //为单选按钮添加点击事件
        coefRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submitButton.setEnabled(true);
                submitButton.setAlpha(1.0f);//设置按钮的透明度
                switch (i) {
                    case R.id.radio_coef1:
                    case R.id.radio_coef2:
                        User.user.setCoef(0.2);//如果用户选择了第1/2项，则将用户的运动目标设置为0.2（减脂）
                        break;
                    case R.id.radio_coef3:
                        User.user.setCoef(0.3);//如果用户选择了第3项，则将用户的心肺功能设置为0.3（增肌）
                        break;
                    case R.id.radio_coef4:
                        User.user.setCoef(0.4);//如果用户选择了第4项，则将用户的心肺功能设置为0.4（保持健康）
                        break;
                    case R.id.radio_coef5:
                        User.user.setCoef(0.5);//如果用户选择了第5项，则将用户的心肺功能设置为0.5（保持健康）
                        break;
                    default:
                        break;
                }
            }
        });
    }/**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x005){
                //获得消息中的数据赋值给用户计划实例
                Bundle data = msg.getData();
                String foodStr=data.getString("food");//人体每天需要摄入的营养素
                Log.v("1",foodStr);
                Gson gson=new Gson();
                Nutriment.nutriment=gson.fromJson(foodStr,Nutriment.class);
//                SplashActivity.editor.putString("nutriment",foodStr);
//                SplashActivity.editor.apply();
                Intent intent=new Intent(CoefActivity.this,MainActivity.class);//成功获取服务器传递的计划数据，跳转到主界面
                startActivity(intent);
//                SplashActivity.editor.putBoolean("isFirst",false);
//                SplashActivity.editor.apply();
                ActivityCollector.finishAll();//销毁之前的活动
            }
            else if(msg.what==0x006){
                Bundle data = msg.getData();
                String str = data.getString("food");
                ToastUtil.showToast(CoefActivity.this,str);
            }
        }
    }

}
