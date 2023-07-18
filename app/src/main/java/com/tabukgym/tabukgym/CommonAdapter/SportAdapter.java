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
import com.tabukgym.tabukgym.Models.DeviceModel;
import com.tabukgym.tabukgym.Models.SportModel;
import com.tabukgym.tabukgym.R;

import java.util.ArrayList;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.help>{
    private ArrayList<SportModel> arrayList;
    private Context context;
    public SportAdapter(ArrayList<SportModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_adapter,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.des.setText(arrayList.get(position).getDescription());
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
