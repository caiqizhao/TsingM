package com.example.guaiwei.tsingm.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.activity.ActionDetailActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.VariableUtil;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.example.guaiwei.tsingm.db.EveryActionInfo;
import com.google.gson.Gson;

import java.util.List;

/**
 * 显示每天具体锻炼项目的列表的适配器
 */
public class PlanDetailAdapter extends RecyclerView.Adapter<PlanDetailAdapter.ViewHolder> {
    private List<EveryActionInfo> mPlanAction;//数据源
    private DayPlanInfo dayPlan;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView planActionName;//动作名称
        TextView planActionTime;//动作时间
        ImageView planActionIV;//动作图片
        ImageView isComplete;//显示完成的图片
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            planActionName=itemView.findViewById(R.id.motion);
            planActionTime=itemView.findViewById(R.id.action_time);
            planActionIV=itemView.findViewById(R.id.photo);
            isComplete=itemView.findViewById(R.id.complete);
        }
    }
    public PlanDetailAdapter(List<EveryActionInfo> PlanAction, DayPlanInfo dayPlan){
        mPlanAction=PlanAction;
        this.dayPlan=dayPlan;
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
                    EveryActionInfo action=mPlanAction.get(position);
                    ActionDetailActivity.i=position;
//                    Intent intent=new Intent(view.getContext(),ActionDetailService.class);
//                    intent.putExtra("action_name",action.getAction_name());//将对应的动作的名称传递给service
//                    view.getContext().startService(intent);
                    Intent intent=new Intent(view.getContext(),ActionDetailActivity.class);
                    intent.putExtra("action_name",action.getName());
                    Gson gson=new Gson();
                    String str=gson.toJson(dayPlan);
                    intent.putExtra("dayplan",str);
                    view.getContext().startActivity(intent);
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
            EveryActionInfo action=mPlanAction.get(i);
            viewHolder.planActionName.setText(action.getName());
            viewHolder.planActionTime.setText(action.getTime()+"\"");
            String gifUrl=VariableUtil.Service_IP+"dongzuo/"+action.getName()+".jpg";
            Glide.with(viewHolder.view.getContext()).load(gifUrl).into(viewHolder.planActionIV);
            if(action.isComplete()){
                viewHolder.isComplete.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.view.getContext()).load(R.mipmap.complete).into(viewHolder.isComplete);
            }
            else{
                viewHolder.isComplete.setVisibility(View.GONE);
            }
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
