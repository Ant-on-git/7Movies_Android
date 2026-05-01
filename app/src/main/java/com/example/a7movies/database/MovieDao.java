package com.example.a7movies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a7movies.models.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("SELECT * FROM favorite_movies WHERE kinopoiskId = :movieId")
    LiveData<Movie> getFavoriteMovie(int movieId);

    @Insert
    Completable addToFavorites(Movie movie);
    // Completable — тип данных из  RxJava, который используется для операций, не возвращающих никакого значения.
    // Асинхронность: Room автоматически понимает, что если метод возвращает Completable, то его нельзя выполнять в главном потоке (UI thread). Вам нужно будет подписаться на него (subscribe), указав фоновый поток.

    @Query("DELETE FROM favorite_movies WHERE kinopoiskId = :movieId")
    Completable removeFromFavorites(int movieId);
}
