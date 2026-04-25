package com.example.a7movies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerMovieFactsResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("items")
    private List<MovieFact> factList;

    public ServerMovieFactsResponse(int total, List<MovieFact> factList) {
        this.total = total;
        this.factList = factList;
    }

    public int getTotal() {
        return total;
    }

    public List<MovieFact> getFactList() {
        return factList;
    }

    @Override
    public String toString() {
        return "ServerMovieFactsResponse{" +
                "total=" + total +
                ", factList=" + factList +
                '}';
    }
}
