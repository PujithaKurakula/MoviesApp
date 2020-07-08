package com.example.lenovo.nanodegreeproject2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context;
    ArrayList<MovieModel> search;

    public MovieAdapter(Context mainActivity, ArrayList<MovieModel> moviesearche) {
        this.context=mainActivity;
        this.search=moviesearche;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(context).inflate(R.layout.design,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder myViewHolder, final int i) {

        MovieModel movies=search.get(i);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+movies.mimage).placeholder(R.drawable.ic_launcher_background).into(myViewHolder.iv);
        myViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] str=new String[8];
                str[0] = search.get(i).getMtitle();
                str[1] = search.get(i).getMoriginal();
                str[2] = search.get(i).getMdate();
                str[3]=search.get(i).getMdes();
                str[4]=search.get(i).getMimage();
                str[5]=search.get(i).getMback();
                str[6]=search.get(i).getMid();
                str[7]=search.get(i).getMrating();
                Intent i = new Intent(context, Movie_Results.class);
                i.putExtra("data", str  );
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return  search.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.imageview);
        }
    }
}
