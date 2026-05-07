package com.example.a7movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a7movies.database.MovieDao;
import com.example.a7movies.database.MovieDatabase;
import com.example.a7movies.models.Movie;

import java.util.List;


public class FavoritesViewModel  extends AndroidViewModel {
    private final MovieDao movieDao;



    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }


    public LiveData<List<Movie>> getFavoritesList() {
        return movieDao.getAllFavoriteMovies();
    }
}
