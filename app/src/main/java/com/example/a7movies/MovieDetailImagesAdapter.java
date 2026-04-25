package com.example.a7movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a7movies.models.Image;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailImagesAdapter extends RecyclerView.Adapter< MovieDetailImagesAdapter.MovieDetailImagesViewHolder >{
    private List<Image> images = new ArrayList<>();


    public void setImages(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MovieDetailImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageItem_FromXml = LayoutInflater
                                        .from( parent.getContext() )
                                        .inflate(
                                                R.layout.imge_item,
                                                parent,
                                                false
                                        );
        return new MovieDetailImagesViewHolder( imageItem_FromXml );
    }



    @Override
    public void onBindViewHolder(@NonNull MovieDetailImagesViewHolder holder, int position) {
        Glide.with( holder.itemView )     // Когда ты создал MovieViewHolder, эта вьюшка сохранилась внутри него под именем itemView. itemView — это вся твоя карточка целиком (весь прямоугольник со всеми потрохами).  Поскольку любая View «знает», в каком контексте она находится, Glide говорит: «О, дай мне itemView, я сам вытащу из неё нужный контекст и нарисую картинку».
                .load( images.get(position).getPreviewUrl() )
                .into( holder.imageView );
    }



    @Override
    public int getItemCount() { return images.size(); }



    static class MovieDetailImagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MovieDetailImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailsImageView);
        }
    }
}
