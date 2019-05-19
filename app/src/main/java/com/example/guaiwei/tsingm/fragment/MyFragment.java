package com.example.guaiwei.tsingm.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.guaiwei.tsingm.Evaluate.EvaluateStartActivity;
import com.example.guaiwei.tsingm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    public MyFragment() {
        // Required empty public constructor
//        Toolbar toolbar=this.getActivity().findViewById(R.id.my_tool_bar);
//        toolbar.setTitle("我的");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my, container, false);
//        Toolbar toolbar=this.getActivity().findViewById(R.id.my_tool_bar);
//        toolbar.setTitle("我的");
        // Inflate the layout for this fragment
        return view;
    }

}
