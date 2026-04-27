package com.example.a7movies.models;

import com.google.gson.annotations.SerializedName;


public class Review {
    @SerializedName("kinopoiskId")
    private int kinopoiskId;

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;


    public Review(int kinopoiskId, String type, String date, String author, String title, String description) {
        this.kinopoiskId = kinopoiskId;
        this.type = type;
        this.date = date;
        this.author = author;
        this.title = title;
        this.description = description;
    }

    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Review{" +
                "kinopoiskId=" + kinopoiskId +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
