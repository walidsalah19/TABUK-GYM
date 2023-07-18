package com.tabukgym.tabukgym.Customer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabukgym.tabukgym.Models.DeliveryModel;
import com.tabukgym.tabukgym.Models.SubscriptionModel;
import com.tabukgym.tabukgym.R;

import java.util.ArrayList;

public class CustomerDeliveryAdapter extends RecyclerView.Adapter<CustomerDeliveryAdapter.help>{
    private ArrayList<DeliveryModel> arrayList;
    private Context context;
    public CustomerDeliveryAdapter(ArrayList<DeliveryModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_delivery,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.duration.setText(arrayList.get(position).getDuration());
        holder.date.setText(arrayList.get(position).getDate());
        holder.status.setText(arrayList.get(position).getStatus());
        holder.clubName.setText(arrayList.get(position).getClubName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class help extends RecyclerView.ViewHolder
    {
        TextView duration,clubName,date,status;
        public help(@NonNull View itemView) {
            super(itemView);
            clubName=itemView.findViewById(R.id.clubName);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            duration=itemView.findViewById(R.id.duration);
        }
    }
}
