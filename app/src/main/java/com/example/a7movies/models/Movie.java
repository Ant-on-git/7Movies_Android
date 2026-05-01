package com.example.a7movies.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favorite_movies")
public class Movie implements Serializable {
    // implements Serializable - чтоб объекты класса можно было переводить в байты (дальше передать параметром или сохранить в файл)
    // @SerializedName("kinopoiskId")   - для обфускации. подробнее в readme + связывает имя ключа JSON с удобным названием переменной
    @PrimaryKey
    @SerializedName("kinopoiskId")
    private int kinopoiskId;
    @SerializedName("nameRu")
    private String nameRu;
    @SerializedName("nameEn")
    private String nameEn;
    @SerializedName("ratingKinopoisk")
    private float ratingKinopoisk;
    @SerializedName("ratingImdb")
    private float ratingImdb;
    @SerializedName("year")
    private int year;
    @SerializedName("posterUrl")
    private String posterUrl;
    @SerializedName("posterUrlPreview")
    private String posterUrlPreview;


    public Movie(int kinopoiskId, String nameRu, String nameEn, float ratingKinopoisk, float ratingImdb, int year, String posterUrl, String posterUrlPreview) {
        this.kinopoiskId = kinopoiskId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.ratingKinopoisk = ratingKinopoisk;
        this.ratingImdb = ratingImdb;
        this.year = year;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
    }

    public int getKinopoiskId() { return kinopoiskId; }

    public String getNameRu() { return nameRu; }

    public String getNameEn() { return nameEn; }

    public float getRatingKinopoisk() { return ratingKinopoisk; }

    public float getRatingImdb() { return ratingImdb; }

    public int getYear() { return year; }

    public String getPosterUrl() { return posterUrl; }

    public String getPosterUrlPreview() { return posterUrlPreview; }

    @Override
    public String toString() {
        return "Movie{" +
                    "kinopoiskId=" + kinopoiskId +
                    ", nameRu='" + nameRu + '\'' +
                    ", nameEn='" + nameEn + '\'' +
                    ", ratingKinopoisk=" + ratingKinopoisk +
                    ", ratingImdb=" + ratingImdb +
                    ", year=" + year +
                    ", posterUrl='" + posterUrl + '\'' +
                    ", posterUrlPreview='" + posterUrlPreview + '\'' +
                '}';
    }
}
