package com.example.a7movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a7movies.models.Review;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailReviewsAdapter extends RecyclerView.Adapter<MovieDetailReviewsAdapter.ReviewViewHolder>{
    List<Review> reviews = new ArrayList<>();


    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviewCardView = LayoutInflater
                .from( parent.getContext() )
                .inflate(
                    R.layout.review_item,
                    parent,
                    false
        );
        return new ReviewViewHolder( reviewCardView );
    }



    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get( position );
        holder.reviewAuthor.setText( review.getAuthor() );
        holder.reviewText.setText( review.getDescription() );

        // меняем цвет отзыва
        String type = review.getType();
        int colorResId = android.R.color.holo_red_light;
        if (type.equals("POSITIVE")) { colorResId = android.R.color.holo_green_light; }
        int color = ContextCompat.getColor( holder.itemView.getContext(), colorResId );
        holder.reviewContainer.setBackgroundColor( color );
    }



    @Override
    public int getItemCount() { return reviews.size(); }




    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout reviewContainer;
        private TextView reviewAuthor;
        private TextView reviewText;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewContainer = itemView.findViewById(R.id.reviewContainer);
            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewText = itemView.findViewById(R.id.review_text);
        }
    }
}
