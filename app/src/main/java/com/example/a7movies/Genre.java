package com.example.a7movies;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("genre")
    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
