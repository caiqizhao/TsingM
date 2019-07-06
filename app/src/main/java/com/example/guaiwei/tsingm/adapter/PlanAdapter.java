package com.example.guaiwei.tsingm.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guaiwei.tsingm.activity.ExerciseListActivity;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.db.DayPlanInfo;
import com.google.gson.Gson;

import java.util.List;

/**
 * 主界面显示每天锻炼的名字的列表的适配器
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<String> mPlanName;
    private DayPlanInfo dayPlan;
    private int Images[]={R.mipmap.yd1,R.mipmap.yd2,R.mipmap.yd3,R.mipmap.yd4,R.mipmap.yd5};
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView planName;
        TextView planTime;
        ImageView planImage;
        ImageView isComplete;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            planName=itemView.findViewById(R.id.plan_name);
            planTime=itemView.findViewById(R.id.plan_time);
            planImage=itemView.findViewById(R.id.plan_image);
            isComplete=itemView.findViewById(R.id.is_complete_image);
            view=itemView;
        }
    }
    public PlanAdapter(List<String> planName,DayPlanInfo dayPlan){
        mPlanName=planName;
        this.dayPlan = dayPlan;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate((R.layout.plan_item),viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.planTime.setText(dayPlan.getTime()+"");//设置时间总长
        if(dayPlan.getCompleteAction()!=null&&dayPlan.getCountAction().equals(dayPlan.getCompleteAction())){
            viewHolder.isComplete.setImageResource(R.mipmap.complete);//如果运动完成则更换图片
        }else{
            viewHolder.isComplete.setImageResource(R.mipmap.not_complete);
        }
        rectRoundBitmap(viewHolder, dayPlan.getId());
    }

    @Override
    public int getItemCount() {
        return mPlanName.size();
    }

    //设置图片为圆角图片
    private void rectRoundBitmap(ViewHolder viewHolder,int i){
        //得到资源文件的BitMap
        Bitmap image= BitmapFactory.decodeResource(viewHolder.view.getResources(),Images[i%5]);
        //创建RoundedBitmapDrawable对象
        RoundedBitmapDrawable roundImg =RoundedBitmapDrawableFactory.create(viewHolder.view.getResources(),image);
        //抗锯齿
        roundImg.setAntiAlias(true);
        //设置圆角半径
        roundImg.setCornerRadius(15);
        //设置显示图片
        viewHolder.planImage.setImageDrawable(roundImg);
    }
}
