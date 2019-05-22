package com.example.guaiwei.tsingm.Evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.BaseActivity;
import com.example.guaiwei.tsingm.bean.User;

import java.util.ArrayList;

public class ExerciseSiteActivity extends BaseActivity {
    private User user;
    private Button nextButton;//下一步按钮
    private ArrayList<CheckBox> checkBoxList=new ArrayList<>();//复选按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_site);
        nextButton=findViewById(R.id.next_site);
        user=(User)getIntent().getSerializableExtra("user_data");
        checkBoxList.add((CheckBox)findViewById(R.id.check_chest_site));
        checkBoxList.add((CheckBox)findViewById(R.id.check_shoulder_site));
        checkBoxList.add((CheckBox)findViewById(R.id.check_back_site));
        checkBoxList.add((CheckBox)findViewById(R.id.check_buttock_site));
        checkBoxList.add((CheckBox)findViewById(R.id.check_leg_site));
        checkBoxList.add((CheckBox)findViewById(R.id.check_arm_site));
        checkBoxList.add((CheckBox)findViewById(R.id.check_body_site));
        //为下一步按钮设置点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String str[]=new String[3];
                    int i=0;
                    for(CheckBox checkBox :checkBoxList){
                        if(checkBox.isChecked()){
                            str[i++]=checkBox.getText().toString();
                        }
                    }
                    user.setExerciseSite(str);
                    Intent intent=new Intent(ExerciseSiteActivity.this,InfoActivity.class);
                    intent.putExtra("user_data",user);//将用户的信息数据传递至下一界面
                    //开启新的activity，进入下一个问题页面
                    startActivity(intent);
                }
                catch (Exception ex){
                    Toast.makeText(ExerciseSiteActivity.this,"最多选择三个部位", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
