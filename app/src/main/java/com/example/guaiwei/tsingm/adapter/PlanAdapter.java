package com.example.guaiwei.tsingm.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guaiwei.tsingm.ExerciseListActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.DayPlan;
import com.google.gson.Gson;

import java.util.List;

/**
 * 主界面显示每天锻炼的名字的列表的适配器
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<String> mPlanName;
    private DayPlan dayPlan;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView planName;
        TextView planTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            planName=itemView.findViewById(R.id.plan_name);
            planTime=itemView.findViewById(R.id.plan_time);
        }
    }
    public PlanAdapter(List<String> planName,DayPlan dayPlan){
        mPlanName=planName;
        this.dayPlan = dayPlan;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate((R.layout.plan_item),viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.planName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ExerciseListActivity.class);
                Gson gson=new Gson();
                String strDayPlan=gson.toJson(dayPlan);//将DayPlan对象转成JSON格式，传递给下一个Activity
                intent.putExtra("day_plan",strDayPlan);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String str=mPlanName.get(i);
        viewHolder.planName.setText(str);//设置训练的名称
        viewHolder.planTime.setText(dayPlan.getTime()+"分钟");//设置时间总长
    }

    @Override
    public int getItemCount() {
        return mPlanName.size();
    }
}
