package com.example.guaiwei.tsingm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guaiwei.tsingm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkCountFragment extends Fragment {


    public WorkCountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_count, container, false);
    }

}