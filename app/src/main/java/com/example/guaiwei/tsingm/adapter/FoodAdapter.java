package com.example.guaiwei.tsingm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.bean.RecommendFood;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<RecommendFood> recommendFoods;
    public FoodAdapter(List<RecommendFood> recommendFoods){
        this.recommendFoods=recommendFoods;
    }
    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        RecommendFood recommendFood=recommendFoods.get(position);
        holder.foodNaame.setText(recommendFood.getFoodName());
        holder.foodNengLiang.setText(recommendFood.getFoodReLiang());
        holder.foodG.setText(recommendFood.getFoodG());
    }

    @Override
    public int getItemCount() {
        return recommendFoods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodNaame,foodNengLiang,foodG;
        public ViewHolder(View itemView) {
            super(itemView);
            foodNaame=itemView.findViewById(R.id.food_name);
            foodNengLiang=itemView.findViewById(R.id.food_nengliang);
            foodG=itemView.findViewById(R.id.food_weight);
        }
    }
}
