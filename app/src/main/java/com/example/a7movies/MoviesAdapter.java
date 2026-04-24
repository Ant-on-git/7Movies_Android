// адаптер для recykclerView

package com.example.a7movies;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter< MoviesAdapter.MovieViewHolder >{
    private List<Movie> movies = new ArrayList<>();


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged(); // встроенный метод (RecyclerView.Adapter) для обновления данных в адаптере
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // onCreateViewHolder вызывается только тогда, когда системе нужно создать новую ячейку (когда старых не хватило для заполнения экрана)
        // из макета делаем View
        View movieItem_viewFromXml = LayoutInflater
                                            .from( parent.getContext() )    // parent — это и есть ваш RecyclerView. Раз RecyclerView уже лежит на каком-то экране (Activity), у него точно есть правильный контекст.  Использовать parent.getContext() — самый безопасный способ получить доступ к ресурсам и темам именно того экрана, где будет список.
                                            .inflate(
                                                    R.layout.movie_item,
                                                    parent,                 // чтобы знать ширину карточки
                                                    false                   // чтобы карточка не «прилипла» к списку раньше времени.
                                            );

        return new MovieViewHolder( movieItem_viewFromXml );
    }


    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // вызывается для каждого отображаемого элемента
        Movie currentMuvie = movies.get( position );

        Glide.with( holder.itemView )     // Когда ты создал MovieViewHolder, эта вьюшка сохранилась внутри него под именем itemView. itemView — это вся твоя карточка целиком (весь прямоугольник со всеми потрохами).  Поскольку любая View «знает», в каком контексте она находится, Glide говорит: «О, дай мне itemView, я сам вытащу из неё нужный контекст и нарисую картинку».
                .load( currentMuvie.getPosterUrl() )
                .into( holder.posterImageView );

        float rating = currentMuvie.getRatingKinopoisk();
        int ratingBackroundCircleID;
        if      ( rating > 7.5 ) { ratingBackroundCircleID = R.drawable.circle_green; }
        else if ( rating > 6.5 ) { ratingBackroundCircleID = R.drawable.circle_orange; }
        else                     { ratingBackroundCircleID = R.drawable.circle_red; }

        // получаем объект (сам круг с нужным цветом) - нужно перевести id в drawable
        Drawable backgroundCircle = ContextCompat.getDrawable( holder.itemView.getContext(), ratingBackroundCircleID );

        holder.ratingTextView.setBackground( backgroundCircle );
        holder.ratingTextView.setText( String.valueOf(rating) );

        // определяем что прокрутили до конца и вызываем колбэк (установленный из активити) для дозагрузки след страницы
        if (position == movies.size()-5) {
            onMoviesListEndListener.onMoviesListEnd();
        }

        // слушать клика по фильму - открыть экран с детальной инф о фильме
        holder.itemView.setOnClickListener(
                view -> {
                    if (onMovieClickListener != null) {   // проверка на null на случай если забудем или не захотим определять слушатель.
                        onMovieClickListener.onMovieClick( currentMuvie );
                    }
                }
        );
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }


    // интерфейс - чтобы установить в метод onMoviesListEnd из активити колбэк (код) для дозагрузки следующей страницы при прокрутке списка фильмов до конца
    private OnMoviesListEndListener onMoviesListEndListener;
    interface OnMoviesListEndListener { void onMoviesListEnd(); }
    public void setOnMoviesListEndListener(OnMoviesListEndListener onMoviesListEndListener) {
        this.onMoviesListEndListener = onMoviesListEndListener;
    }


    // интерфейс для реализации слушателя клика на карточку фильма (открыть окно с детальной инф)
    private OnMovieClickListener onMovieClickListener;
    interface OnMovieClickListener{ void onMovieClick(Movie movie); }
    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }


    static class MovieViewHolder extends RecyclerView.ViewHolder {
        //      Задача ViewHolder - Однажды найти view в макете, в которые надо вставлять данные
        // Потом в onBindViewHolder обращаемся напрямую к holder.textViewNote,
        // а не делаем findViewById каждый раз. Сильно экономит ресурсы, особенно при длинных списках.

        private final ImageView posterImageView;
        private final TextView ratingTextView;
        public MovieViewHolder(@NonNull View itemView) {    // itemView - тот вью, что создается из макета (noteItem_viewFromXML в onCreateViewHolder)
            super(itemView);                                // itemView = movie_item
            posterImageView = itemView.findViewById(R.id.posterImageView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }
    }
}
