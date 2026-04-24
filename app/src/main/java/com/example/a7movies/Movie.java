package com.example.a7movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    // implements Serializable - чтоб объекты класса можно было переводить в байты (дальше передать параметром или сохранить в файл)
    // @SerializedName("kinopoiskId")   - для обфускации. подробнее в readme + связывает имя ключа JSON с удобным названием переменной
    @SerializedName("kinopoiskId")
    private int kinopoiskId;
    @SerializedName("nameRu")
    private String nameRu;
    @SerializedName("nameEn")
    private String nameEn;
    @SerializedName("countries")
    private List<Country> countries;
    @SerializedName("genres")
    private List<Genre> genres;
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


    public Movie(int kinopoiskId, String nameRu, String nameEn, List<Country> countries, List<Genre> genres, float ratingKinopoisk, float ratingImdb, int year, String posterUrl, String posterUrlPreview) {
        this.kinopoiskId = kinopoiskId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.countries = countries;
        this.genres = genres;
        this.ratingKinopoisk = ratingKinopoisk;
        this.ratingImdb = ratingImdb;
        this.year = year;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
    }

    public int getKinopoiskId() { return kinopoiskId; }

    public String getNameRu() { return nameRu; }

    public String getNameEn() { return nameEn; }

    public List<Country> getCountries() { return countries; }

    public List<Genre> getGenres() { return genres; }

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
                    ", countries=" + countries +
                    ", genres=" + genres +
                    ", ratingKinopoisk=" + ratingKinopoisk +
                    ", ratingImdb=" + ratingImdb +
                    ", year=" + year +
                    ", posterUrl='" + posterUrl + '\'' +
                    ", posterUrlPreview='" + posterUrlPreview + '\'' +
                '}';
    }
}
