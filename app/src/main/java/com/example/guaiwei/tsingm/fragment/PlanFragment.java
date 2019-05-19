package com.example.guaiwei.tsingm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.guaiwei.tsingm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {


    public PlanFragment() {
        // Required empty public constructor
//        Toolbar toolbar=this.getActivity().findViewById(R.id.my_tool_bar);
//        toolbar.setTitle("智能训练计划");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan, container, false);
//        Toolbar toolbar=this.getActivity().findViewById(R.id.my_tool_bar);
//        toolbar.setTitle("智能训练计划");
        // Inflate the layout for this fragment
        return view;
    }

}
