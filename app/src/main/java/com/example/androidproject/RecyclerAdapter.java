package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
     Context context;
     ArrayList<databaseHelper> matchesList;

    public RecyclerAdapter(Context context, ArrayList<databaseHelper> matchesList) {
        this.context = context;
        this.matchesList = matchesList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.matchrowlayout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        databaseHelper databasehelper=matchesList.get(position);
        holder.leagueName.setText(databasehelper.lName);
        holder.description.setText(databasehelper.description);
        holder.details.setText(databasehelper.details);
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView leagueName,description,details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueName=itemView.findViewById(R.id.leagueNamelable);
            description=itemView.findViewById(R.id.description);
            details=itemView.findViewById(R.id.details);
        }
    }
}
