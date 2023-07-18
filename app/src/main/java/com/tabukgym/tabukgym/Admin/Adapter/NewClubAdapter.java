package com.tabukgym.tabukgym.Admin.Adapter;

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
import com.tabukgym.tabukgym.Models.ClubModel;
import com.tabukgym.tabukgym.R;

import java.util.ArrayList;

public class NewClubAdapter extends RecyclerView.Adapter<NewClubAdapter.help>{
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClicked(int pos);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    private ArrayList<ClubModel> clubs;
    private Context context;
    public NewClubAdapter(ArrayList<ClubModel> clubs, Context context) {
        this.clubs = clubs;
        this.context=context;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_clubs_adapter,parent,false);
        return new help(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull help holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(clubs.get(position).getName());
        Glide.with(context).load(clubs.get(position).getLogo()).into(holder.image);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }
    public class help extends RecyclerView.ViewHolder
    {
        TextView name;
        Button view;
        ImageView image;
        public help(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.clubName);
            view=itemView.findViewById(R.id.viewDetails);
            image=itemView.findViewById(R.id.image);
        }
    }
}
