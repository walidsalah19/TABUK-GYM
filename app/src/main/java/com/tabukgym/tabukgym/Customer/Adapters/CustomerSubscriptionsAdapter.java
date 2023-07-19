package com.tabukgym.tabukgym.Customer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabukgym.tabukgym.Models.SubscriptionModel;
import com.tabukgym.tabukgym.R;

import java.util.ArrayList;

public class CustomerSubscriptionsAdapter extends RecyclerView.Adapter<CustomerSubscriptionsAdapter.help>{
    private ArrayList<SubscriptionModel> arrayList;
    private Context context;
    public CustomerSubscriptionsAdapter(ArrayList<SubscriptionModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_subscription,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.subscriptionPrice.setText(arrayList.get(position).getSubPrice());
        holder.date.setText(arrayList.get(position).getDate());
        holder.status.setText(arrayList.get(position).getSubStatus());
        holder.subPeriod.setText(arrayList.get(position).getSubPeriod());
        holder.trainingPeriod.setText(arrayList.get(position).getTrainingPeriod());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class help extends RecyclerView.ViewHolder
    {
        TextView clubName,subscriptionPrice,date,status,subPeriod,trainingPeriod;
        public help(@NonNull View itemView) {
            super(itemView);
            subscriptionPrice=itemView.findViewById(R.id.subscriptionPrice);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            clubName=itemView.findViewById(R.id.clubName);
            subPeriod=itemView.findViewById(R.id.subPeriod);
            trainingPeriod=itemView.findViewById(R.id.trainingPeriod);
        }
    }
}
