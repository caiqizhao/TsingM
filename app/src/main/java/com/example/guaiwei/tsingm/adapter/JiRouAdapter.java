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

public class JiRouAdapter extends RecyclerView.Adapter<JiRouAdapter.ViewHolder>{
    private List<String> imageUrl;

    public JiRouAdapter(List<String> imageUrl){
        this.imageUrl=imageUrl;
    }
    @NonNull
    @Override
    public JiRouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate((R.layout.jirou_image_item),parent,false);
        final JiRouAdapter.ViewHolder holder=new JiRouAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JiRouAdapter.ViewHolder holder, int position) {

        String imageUrlStr=imageUrl.get(position);
        Glide.with(holder.itemView.getContext()).load(imageUrlStr).into(holder.jirouIV);
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView jirouIV;
        public ViewHolder(View itemView) {
            super(itemView);
            jirouIV=itemView.findViewById(R.id.jirou_image);
        }
    }
}
