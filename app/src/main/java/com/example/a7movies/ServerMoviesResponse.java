package com.example.a7movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerMoviesResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("items")
    private List<Movie> movies;


    public ServerMoviesResponse(int total, int totalPages, List<Movie> movies) {
        this.total = total;
        this.totalPages = totalPages;
        this.movies = movies;
    }

    public int getTotal() { return total; }

    public int getTotalPages() { return totalPages; }

    public List<Movie> getMovies() { return movies; }

    @Override
    public String toString() {
        return "ServerMoviesResponse{" +
                    "total=" + total +
                    ", totalPages=" + totalPages +
                    ", movies=" + movies +
                '}';
    }
}
