package com.example.guaiwei.tsingm.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guaiwei.tsingm.R;


public class WorkMonthFragment extends Fragment {

    public WorkMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_month, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
