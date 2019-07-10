package com.example.guaiwei.tsingm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.guaiwei.tsingm.R;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.ViewHolder>{
    private List<Integer> mStageList;
    public StageAdapter(List<Integer> stageList){
        mStageList=stageList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate((R.layout.stage_item),parent,false);
        final StageAdapter.ViewHolder holder=new StageAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i=mStageList.get(position);
        if (i==0){
            holder.imageView.setBackground(holder.view.getResources().getDrawable(R.drawable.stage0));
        }
        else{
            holder.imageView.setBackground(holder.view.getResources().getDrawable(R.drawable.stage1));
        }
    }

    @Override
    public int getItemCount() {
        return mStageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.stage_image);
            this.view=itemView;
        }
    }
}
