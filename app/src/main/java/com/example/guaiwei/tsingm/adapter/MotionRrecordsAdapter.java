package com.example.guaiwei.tsingm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;

import java.util.List;

public class MotionRrecordsAdapter extends RecyclerView.Adapter<MotionRrecordsAdapter.ViewHolder> {
    private List<MotionRecordsEntity> motionRecordsEntityList;

    public MotionRrecordsAdapter(List<MotionRecordsEntity> list){
        motionRecordsEntityList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_records_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MotionRecordsEntity motionRecordsEntity = motionRecordsEntityList.get(position);
        if(motionRecordsEntity.getMovement_type().equals("行走")){
            holder.linearLayout.setVisibility(View.GONE);
            holder.imageView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.foot));
        }else if(motionRecordsEntity.getMovement_type().equals("健身")){
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.imageView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.fitness));
            holder.time.setText(motionRecordsEntity.getTime());
            holder.haoneng.setText(motionRecordsEntity.getHaoneng()+"千卡");
        }
        else{
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.imageView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.run));
            holder.time.setText(motionRecordsEntity.getTime());
            holder.haoneng.setText(motionRecordsEntity.getHaoneng()+"千卡");
        }
        holder.textView.setText(motionRecordsEntity.getMovement_content());
    }

    @Override
    public int getItemCount() {
        return motionRecordsEntityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView,time,haoneng;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movement_type);
            textView = itemView.findViewById(R.id.sports_content);
            linearLayout=itemView.findViewById(R.id.other);
            time=itemView.findViewById(R.id.time);
            haoneng=itemView.findViewById(R.id.haoneng);
        }
    }
}
