package com.tabukgym.tabukgym.CommonAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tabukgym.tabukgym.Models.SportModel;
import com.tabukgym.tabukgym.Models.SubscriptionModel;
import com.tabukgym.tabukgym.R;

import java.util.ArrayList;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.help>{
    private ArrayList<SubscriptionModel> arrayList;
    private Context context;
    public SubscriptionAdapter(ArrayList<SubscriptionModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_adapter,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.subscriptionPrice.setText(arrayList.get(position).getSubPrice());
        holder.date.setText(arrayList.get(position).getDate());
        holder.status.setText(arrayList.get(position).getSubStatus());
        holder.customerPhone.setText(arrayList.get(position).getCustPhone());
        holder.customerAge.setText(arrayList.get(position).getCustAge());
        holder.customerHeight.setText(arrayList.get(position).getCustHeight());
        holder.customerWeight.setText(arrayList.get(position).getCustWeight());
        holder.subPeriod.setText(arrayList.get(position).getSubPeriod());
        holder.trainingPeriod.setText(arrayList.get(position).getTrainingPeriod());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class help extends RecyclerView.ViewHolder
    {
        TextView subscriptionPrice,date,status,customerPhone,customerAge
                ,customerHeight,customerWeight,subPeriod,trainingPeriod;
        public help(@NonNull View itemView) {
            super(itemView);
            subscriptionPrice=itemView.findViewById(R.id.subscriptionPrice);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            customerPhone=itemView.findViewById(R.id.customerPhone);
            customerAge=itemView.findViewById(R.id.customerAge);
            customerHeight=itemView.findViewById(R.id.customerHeight);
            customerWeight=itemView.findViewById(R.id.customerWeight);
            subPeriod=itemView.findViewById(R.id.subPeriod);
            trainingPeriod=itemView.findViewById(R.id.trainingPeriod);
        }
    }
}
