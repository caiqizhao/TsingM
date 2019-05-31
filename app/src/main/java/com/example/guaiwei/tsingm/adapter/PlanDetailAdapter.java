package com.example.guaiwei.tsingm.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.ActionDetailActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.Utils.VariableUtil;
import com.example.guaiwei.tsingm.bean.DayPlan;
import com.example.guaiwei.tsingm.bean.EveryAction;
import com.example.guaiwei.tsingm.service.ActionDetailService;
import com.google.gson.Gson;

import java.util.List;

/**
 * 显示每天具体锻炼项目的列表的适配器
 */
public class PlanDetailAdapter extends RecyclerView.Adapter<PlanDetailAdapter.ViewHolder> {
    private List<EveryAction> mPlanAction;//数据源

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView planActionName;//动作名称
        TextView planActionTime;//动作时间
        ImageView planActionIV;//动作图片
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            planActionName=itemView.findViewById(R.id.motion);
            planActionTime=itemView.findViewById(R.id.time);
            planActionIV=itemView.findViewById(R.id.photo);

        }
    }
    public PlanDetailAdapter(List<EveryAction> PlanAction){
        mPlanAction=PlanAction;
        Log.v("1",""+mPlanAction.size());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate((R.layout.exercise_detail_item),viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int position=holder.getAdapterPosition();
                    EveryAction action=mPlanAction.get(position);
                    ActionDetailActivity.i=position;
                    Intent intent=new Intent(view.getContext(),ActionDetailService.class);
                    intent.putExtra("action_name",action.getName());//将对应的动作的名称传递给service
                    view.getContext().startService(intent);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try{
            EveryAction action=mPlanAction.get(i);
            viewHolder.planActionName.setText(action.getName());
            viewHolder.planActionTime.setText(action.getTime()+"\"");
            String gifUrl=VariableUtil.Service_IP+"dongzuo/"+action.getName()+".jpg";
            Glide.with(viewHolder.view.getContext()).load(gifUrl).into(viewHolder.planActionIV);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPlanAction.size();
    }
}
