package com.example.guaiwei.tsingm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guaiwei.tsingm.R;

/**
 * 用户基本信息Fragment
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    public MyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

}
