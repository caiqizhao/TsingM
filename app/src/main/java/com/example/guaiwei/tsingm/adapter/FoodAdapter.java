package com.example.guaiwei.tsingm.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.activity.SearchFoodDetailActivity;
import com.example.guaiwei.tsingm.gson.RecommendFood;
import com.example.guaiwei.tsingm.utils.VariableUtil;
import com.google.gson.Gson;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<RecommendFood> recommendFoods;
    public FoodAdapter(List<RecommendFood> recommendFoods){
        this.recommendFoods=recommendFoods;
    }
    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendFood recommendFood=recommendFoods.get(holder.getAdapterPosition());
                Gson gson=new Gson();
                String foodStr=gson.toJson(recommendFood);
                Intent intent=new Intent(view.getContext(),SearchFoodDetailActivity.class);
                intent.putExtra("food_detail",foodStr);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        RecommendFood recommendFood=recommendFoods.get(position);
        holder.foodNaame.setText(recommendFood.getFood_name().split("，")[0]);
        holder.foodNengLiang.setText(recommendFood.getEnergy());
        holder.foodG.setText(recommendFood.getFoodG());
        String url=VariableUtil.Service_IP+"food/"+recommendFood.getFood_name()+".jpg";
        Glide.with(holder.view.getContext()).load(url).into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return recommendFoods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodNaame,foodNengLiang,foodG;
        ImageView foodImage;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            foodNaame=itemView.findViewById(R.id.food_name);
            foodNengLiang=itemView.findViewById(R.id.food_nengliang);
            foodG=itemView.findViewById(R.id.food_weight);
            foodImage=itemView.findViewById(R.id.food_image);
        }
    }
}
