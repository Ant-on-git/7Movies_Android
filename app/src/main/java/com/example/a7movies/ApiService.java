package com.example.a7movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {
    @Headers({
            "X-API-KEY: " + BuildConfig.KINOPOISKUNOFF_API_KEY,
            "Content-Type: application/json"
    })
    @GET("films?order=RATING&type=FILM&ratingFrom=6&ratingTo=10&yearFrom=2020&yearTo=3000&page=1")
    Single<ServerMoviesResponse> loadMovies();
}
