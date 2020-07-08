package com.example.lenovo.nanodegreeproject2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    Context review_context;
    ArrayList<ReviewModel> reviews;

    public ReviewAdapter(Movie_Results movie_results, ArrayList<ReviewModel> reviewModel) {
        this.review_context=movie_results;
        this.reviews=reviewModel;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(review_context).inflate(R.layout.review_design,viewGroup,false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        ReviewModel reviewModel=reviews.get(i);
        reviewViewHolder.review_author.setText(reviewModel.getAuthor());
        reviewViewHolder.review_content.setText(reviewModel.getContent());
        }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView review_author,review_content;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            review_author=itemView.findViewById(R.id.author);
            review_content=itemView.findViewById(R.id.content);
        }
    }
}
