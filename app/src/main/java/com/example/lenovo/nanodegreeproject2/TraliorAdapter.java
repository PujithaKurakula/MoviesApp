package com.example.lenovo.nanodegreeproject2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TraliorAdapter extends RecyclerView.Adapter<TraliorAdapter.TrailorHolder> {
    Context tralior_context;
    ArrayList<TraliorModel> trailerModels;

    public TraliorAdapter(Context context, ArrayList<TraliorModel> trailerModels) {
        this.tralior_context = context;
        this.trailerModels = trailerModels;
    }

    @NonNull
    @Override
    public TrailorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(tralior_context).inflate(R.layout.tralior_design,parent,false);
        return new TrailorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailorHolder holder,final int position) {

        final TraliorModel model=trailerModels.get(position);
        holder.tralior_text.setText(model.getKey());
        holder.tralior_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = model.getKey();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(string));
                i.setPackage("com.google.android.youtube");
                tralior_context.startActivity(i);
            }
        });
        holder.tralior_text.setText(model.getName());
        holder.tralior_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = model.getKey();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(string));
                i.setPackage("com.google.android.youtube");
                tralior_context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (trailerModels==null)?0:trailerModels.size();
    }

    public class TrailorHolder extends RecyclerView.ViewHolder {
        ImageView tralior_image;
        TextView tralior_text;
        public TrailorHolder(View itemView) {
            super(itemView);
            tralior_image=itemView.findViewById(R.id.image);
            tralior_text=itemView.findViewById(R.id.text);
        }
    }
}
