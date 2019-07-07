package com.example.guaiwei.tsingm.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guaiwei.tsingm.Evaluate.CurrentSituationActivity;
import com.example.guaiwei.tsingm.R;

/**
 * 用户还未进行测评时的Fragment
 * A simple {@link Fragment} subclass.
 */
public class EvaluateBeforeFragment extends Fragment {
    private Button btn;//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_evaluate_before, container, false);
        btn=view.findViewById(R.id.start_evaluate);//实例化按钮控件
        //为按钮设置点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),CurrentSituationActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
