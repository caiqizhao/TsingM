package com.example.guaiwei.tsingm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.MyApplication;
import com.example.guaiwei.tsingm.activity.RunActivity;

/**
 * 跑步
 */
public class RunFragment extends Fragment {
    private View view;
    private Button startRunBtn;
    private TextView historyTv,instanceTv;

    public RunFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_run, container, false);
        this.view=view;
        initComponent();
        setData();
        startRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyApplication.getContext(),RunActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    /**
     * 初始化各组件
     */
    private void initComponent() {
        startRunBtn=view.findViewById(R.id.start_run);
        historyTv=view.findViewById(R.id.history);
        instanceTv=view.findViewById(R.id.run_instance);
    }

    /**
     * 为各组件设置数据
     */
    private void setData() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
