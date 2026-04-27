package com.example.a7movies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerReviewsResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("items")
    private List<Review>reviews;


    public ServerReviewsResponse(int total, int totalPages, List<Review> reviews) {
        this.total = total;
        this.totalPages = totalPages;
        this.reviews = reviews;
    }

    public int getTotal() { return total; }

    public int getTotalPages() { return totalPages; }

    public List<Review> getReviews() { return reviews; }

    @Override
    public String toString() {
        return "ServerReviewsResponse{" +
                    "total=" + total +
                    ", totalPages=" + totalPages +
                    ", reviews=" + reviews +
                '}';
    }
}
