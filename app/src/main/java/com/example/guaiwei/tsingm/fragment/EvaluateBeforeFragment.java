package com.example.guaiwei.tsingm.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.guaiwei.tsingm.Evaluate.EvaluateStartActivity;
import com.example.guaiwei.tsingm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluateBeforeFragment extends Fragment {
    private Button btn;

    public EvaluateBeforeFragment() {
        // Required empty public constructor
//        Toolbar toolbar=this.getActivity().findViewById(R.id.my_tool_bar);
//        toolbar.setTitle("智能训练计划");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_evaluate_before, container, false);
        btn=view.findViewById(R.id.plan_btn);
//        Toolbar toolbar=this.getActivity().findViewById(R.id.my_tool_bar);
//        toolbar.setTitle("智能训练计划");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),EvaluateStartActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
