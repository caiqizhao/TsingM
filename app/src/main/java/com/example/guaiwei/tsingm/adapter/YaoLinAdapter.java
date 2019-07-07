package com.example.guaiwei.tsingm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.R;

import java.util.List;

/**
 * 显示动作要领图片的适配器
 */
public class YaoLinAdapter extends RecyclerView.Adapter<YaoLinAdapter.ViewHolder> {
    private List<String> imageUrl;
    public YaoLinAdapter(List<String> imageUrl){
        this.imageUrl=imageUrl;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate((R.layout.yaoling_item),parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrlStr=imageUrl.get(position);
        Glide.with(holder.itemView.getContext()).load(imageUrlStr).into(holder.yaoLingIV);
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView yaoLingIV;
        public ViewHolder(View itemView) {
            super(itemView);
            yaoLingIV=itemView.findViewById(R.id.yaoling_image);
        }
    }
}
