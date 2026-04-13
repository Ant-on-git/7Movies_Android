package com.example.a7movies;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("country")
    private String country;

    public Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Country{" +
                    "country='" + country + '\'' +
                '}';
    }
}
