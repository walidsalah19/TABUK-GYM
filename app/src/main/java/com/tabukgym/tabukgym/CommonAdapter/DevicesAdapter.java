package com.tabukgym.tabukgym.CommonAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tabukgym.tabukgym.Admin.Adapter.NewClubAdapter;
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.Models.DeviceModel;
import com.tabukgym.tabukgym.R;

import java.util.ArrayList;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.help>{
    private ArrayList<DeviceModel> devices;
    private Context context;
    public DevicesAdapter(ArrayList<DeviceModel> devices, Context context) {
        this.devices = devices;
        this.context=context;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.devices_adapter,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(devices.get(position).getName());
        holder.des.setText(devices.get(position).getDescription());
        Glide.with(context).load(devices.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
    public class help extends RecyclerView.ViewHolder
    {
        TextView name,des;
        ImageView image;
        public help(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.deviceName);
            des=itemView.findViewById(R.id.deviceDescription);
            image=itemView.findViewById(R.id.image);
        }
    }
}
