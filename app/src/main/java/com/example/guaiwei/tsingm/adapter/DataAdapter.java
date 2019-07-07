package com.example.guaiwei.tsingm.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.gson.PlanData;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<PlanData> mPlanData;//数据源
    private List<Boolean> list_click=new ArrayList<>();//判断该item 是否被点击
    private OnRecycleItemClickListener mListener;//设置item点击监听事件

    public DataAdapter(List<PlanData> planData){
        mPlanData=planData;
        resetIsClick();
        list_click.add(0,true);//设置初始状态
    }

    /**
     * 重置每个Item的点击状态
     */
    private void resetIsClick() {
        list_click.clear();
        for (int i=0;i<mPlanData.size();i++){
            list_click.add(false);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView weekText;
        TextView dataText;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            weekText=itemView.findViewById(R.id.data_week);
            dataText=itemView.findViewById(R.id.data_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate((R.layout.data_item),viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        final ViewHolder myHolder=(ViewHolder)viewHolder;
        PlanData planData=mPlanData.get(i);
        myHolder.weekText.setText(planData.getWeek());
        myHolder.dataText.setText(planData.getData());
        if (!list_click.get(i)){//判断Item的点击状态，设置背景色和字体颜色
            myHolder.dataText.setTextColor(Color.rgb(70,70,70));
            myHolder.dataText.setBackground(myHolder.view.getResources().getDrawable(R.drawable.shape_text));
        }else {
            myHolder.dataText.setTextColor(Color.rgb(255,255,255));
            myHolder.dataText.setBackground(myHolder.view.getResources().getDrawable(R.drawable.focus_text));
        }
        myHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    resetIsClick();
                    list_click.remove(i);
                    list_click.add(i,true);
                    mListener.onItemClick(i);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlanData.size();
    }
    //设置监听
    public void setOnItemClickListener(OnRecycleItemClickListener listener){
        mListener=listener;
    }

    /**
     * RecycleView点击事件监听接口
     */
    public interface OnRecycleItemClickListener{
        void onItemClick(int pos);
    }
}
