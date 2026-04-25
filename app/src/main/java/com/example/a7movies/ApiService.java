package com.example.a7movies;

import com.example.a7movies.models.ServerMovieFactsResponse;
import com.example.a7movies.models.ServerMoviesResponse;
import com.example.a7movies.models.ServerImagesResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("films?order=NUM_VOTE&type=FILM&ratingFrom=5&ratingTo=10&yearFrom=2020&yearTo=3000")
    @Headers({
            "X-API-KEY: " + BuildConfig.KINOPOISKUNOFF_API_KEY,
            "Content-Type: application/json"
    })
    Single<ServerMoviesResponse> loadMovies(@Query("page") int page );
    // @Query("page") int page   :    передаем параметром номер страницы и он встанет в url в виде &page=1



    @GET("films/{id}/facts")
    @Headers({
            "X-API-KEY: " + BuildConfig.KINOPOISKUNOFF_API_KEY,
            "Content-Type: application/json"
    })
    Single<ServerMovieFactsResponse> loadMovieFacts(@Path("id") int id );


    @GET("films/{id}/images")
    @Headers({
            "X-API-KEY: " + BuildConfig.KINOPOISKUNOFF_API_KEY,
            "Content-Type: application/json"
    })
    Single<ServerImagesResponse> loadImages(@Path("id") int id );

}
