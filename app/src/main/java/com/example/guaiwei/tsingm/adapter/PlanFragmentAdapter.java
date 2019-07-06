package com.example.guaiwei.tsingm.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guaiwei.tsingm.fragment.FootFragment;
import com.example.guaiwei.tsingm.fragment.HaoNengFragment;
import com.example.guaiwei.tsingm.fragment.PlanFragment;

import java.util.List;

public class PlanFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list_fragment;        //fragment列表
    private List<String> list_Title; //tab名的列表
    public PlanFragmentAdapter(FragmentManager fm,List<Fragment>list_fragment,List<String> list_Title ) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int i) {
        return list_fragment.get(i);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position%list_Title.size());
    }
}
