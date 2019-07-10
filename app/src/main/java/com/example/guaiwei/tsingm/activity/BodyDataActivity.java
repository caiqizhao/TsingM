package com.example.guaiwei.tsingm.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.adapter.StageAdapter;
import com.example.guaiwei.tsingm.gson.BaseActivity;
import com.example.guaiwei.tsingm.gson.User;
import com.example.guaiwei.tsingm.utils.ChangeColor;
import com.example.guaiwei.tsingm.utils.ToastUtil;
import com.example.guaiwei.tsingm.utils.Utility;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class BodyDataActivity extends BaseActivity implements View.OnClickListener{
    private TextView weightTv,heightTv,BIMTv,hiplineTv,bustTv,waistlineTv,saveTv;
    private LinearLayout weightLL,heightLL,BIMLL,hiplineLL,bustLL,waistlineLL;
    private double weight,height,BIM,hipline,bust,waistline;
    private RecyclerView nowAbdominalRV,nowChestRV,nowCardioPulmonaryRV,nowLowerLimbRv,nowFlexRV;
    private List<List<Integer>> nowStage=new ArrayList<>();//存储各部位强度
    private User user;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_data);
        ChangeColor.changeColor(this,Color.parseColor("#584f60"));
        initComponent();
        pref=PreferenceManager.getDefaultSharedPreferences(BodyDataActivity.this);
        editor=pref.edit();
        String userStr=pref.getString("user_data",null);
        user=new Gson().fromJson(userStr,User.class);
        weight=user.getWeight();
        height=user.getHeight();
        BIM=(weight/(height*height))*10000;
        setData();
        initRecycleView();
        weightLL.setOnClickListener(this);
        heightLL.setOnClickListener(this);
        BIMLL.setOnClickListener(this);
        hiplineLL.setOnClickListener(this);
        bustLL.setOnClickListener(this);
        waistlineLL.setOnClickListener(this);
        saveTv.setOnClickListener(this);
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        weightTv=(TextView)findViewById(R.id.weight);
        heightTv=(TextView)findViewById(R.id.height);
        BIMTv=(TextView)findViewById(R.id.bim);
        hiplineTv=(TextView)findViewById(R.id.hipline);
        bustTv=(TextView)findViewById(R.id.bust);
        waistlineTv=(TextView)findViewById(R.id.waistline);
        saveTv=(TextView)findViewById(R.id.save);
        weightLL=(LinearLayout)findViewById(R.id.weight_ll);
        heightLL=(LinearLayout)findViewById(R.id.height_ll);
        BIMLL=(LinearLayout)findViewById(R.id.bim_ll);
        hiplineLL=(LinearLayout)findViewById(R.id.hipline_ll);
        bustLL=(LinearLayout)findViewById(R.id.bust_ll);
        waistlineLL=(LinearLayout)findViewById(R.id.waistline_ll);
        nowAbdominalRV=(RecyclerView)findViewById(R.id.abdominal_rv);
        nowCardioPulmonaryRV=(RecyclerView)findViewById(R.id.cardio_pulmonary_rv);
        nowChestRV=(RecyclerView)findViewById(R.id.chest_rv);
        nowLowerLimbRv=(RecyclerView)findViewById(R.id.lower_limb_rv);
        nowFlexRV=(RecyclerView)findViewById(R.id.flexibility_rv);
    }

    /**
     *
     */
    private void initRecycleView(){
        LinearLayoutManager layoutManager01=new LinearLayoutManager(this);
        layoutManager01.setOrientation(LinearLayoutManager.HORIZONTAL);
        nowAbdominalRV.setLayoutManager(layoutManager01);
        LinearLayoutManager layoutManager02=new LinearLayoutManager(this);
        layoutManager02.setOrientation(LinearLayoutManager.HORIZONTAL);
        nowChestRV.setLayoutManager(layoutManager02);
        LinearLayoutManager layoutManager03=new LinearLayoutManager(this);
        layoutManager03.setOrientation(LinearLayoutManager.HORIZONTAL);
        nowCardioPulmonaryRV.setLayoutManager(layoutManager03);
        LinearLayoutManager layoutManager04=new LinearLayoutManager(this);
        layoutManager04.setOrientation(LinearLayoutManager.HORIZONTAL);
        nowLowerLimbRv.setLayoutManager(layoutManager04);
        LinearLayoutManager layoutManager05=new LinearLayoutManager(this);
        layoutManager05.setOrientation(LinearLayoutManager.HORIZONTAL);
        nowFlexRV.setLayoutManager(layoutManager05);
        StageAdapter stageAdapter=new StageAdapter(nowStage.get(0));nowAbdominalRV.setAdapter(stageAdapter);
        stageAdapter=new StageAdapter(nowStage.get(1));nowCardioPulmonaryRV.setAdapter(stageAdapter);
        stageAdapter=new StageAdapter(nowStage.get(2));nowChestRV.setAdapter(stageAdapter);
        stageAdapter=new StageAdapter(nowStage.get(3));nowLowerLimbRv.setAdapter(stageAdapter);
        stageAdapter=new StageAdapter(nowStage.get(4));nowFlexRV.setAdapter(stageAdapter);
    }
    /**
     *为各组件设置数据
     */
    private void setData(){
        DecimalFormat df=new DecimalFormat("0.0");
        weightTv.setText(weight+"kg");
        heightTv.setText(height+"cm");
        BIMTv.setText(df.format(BIM));
        int userAE=user.getUserFitnessStage().getAbdominalEndurance();int userCE=user.getUserFitnessStage().getChestEndurance();int userLL=user.getUserFitnessStage().getLowerLimb();
        int userF=user.getUserFitnessStage().getFlexibility();int userCP=user.getUserFitnessStage().getCardioPulmonary();
        nowStage.add(Utility.getStageList(userAE));
        nowStage.add(Utility.getStageList(userCP));
        nowStage.add(Utility.getStageList(userCE));
        nowStage.add(Utility.getStageList(userLL));
        nowStage.add(Utility.getStageList(userF));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weight_ll:
                dialogEditText("请输入当前体重","kg",weightTv);
//                user.setWeight(Double.parseDouble(weightTv.getText().toString().split("0~9.")[0]));
//                editor.putString("user_data",new Gson().toJson(user));
//                editor.apply();
                break;
            case R.id.height_ll:
                dialogEditText("请输入当前身高","cm",heightTv);
//                user.setHeight(Double.parseDouble(heightTv.getText().toString().split("0~9.")[0]));
//                editor.putString("user_data",new Gson().toJson(user));
//                editor.apply();
                break;
            case R.id.hipline_ll:
                dialogEditText("请输入当前臀围","cm",hiplineTv);
                break;
            case R.id.bust_ll:
                dialogEditText("请输入当前胸围","cm",bustTv);
                break;
            case R.id.waistline_ll:
                dialogEditText("请输入当前腰围","cm",waistlineTv);
                break;
            case R.id.save:
                user.setHeight(Double.parseDouble(heightTv.getText().toString().replaceAll("[^0-9.]", "")));
                user.setWeight(Double.parseDouble(weightTv.getText().toString().replaceAll("[^0-9.]", "")));
                editor.putString("user_data",new Gson().toJson(user));
                editor.apply();
                break;
        }
    }

    /**
     * 可输入的对框框
     */
    private void dialogEditText(String info, final String unit, final TextView textView) {
        final AlertDialog grDdialog = new AlertDialog.Builder(this).create();
        LayoutInflater flater = LayoutInflater.from(this);
        final View view = flater.inflate(R.layout.edit_dialog, null);
        Button okBtn=(Button)view.findViewById(R.id.btn_ok);
        TextView cancel=(TextView) view.findViewById(R.id.cancel);
        final EditText editText=(EditText)view.findViewById(R.id.user_info) ;
        TextView unitTv=(TextView)view.findViewById(R.id.unit);
        TextView infoTv=(TextView)view.findViewById(R.id.info);
        unitTv.setText(unit);
        infoTv.setText(info);
        grDdialog.setView(view);
        grDdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // 解决EditText, 在dialog中无法自动弹出对话框的问题
                showKeyboard(editText);
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())){
                    //学科
                    textView.setText(editText.getText().toString()+unit);
                    grDdialog.dismiss();
                } else {
                    ToastUtil.showToast(BodyDataActivity.this, "请确保输入的内容不为空!");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grDdialog.dismiss();
            }
        });
        grDdialog.show();

    }
    /**
     * 解决在dialog中无法自动弹出对话框的问题
     **/
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
