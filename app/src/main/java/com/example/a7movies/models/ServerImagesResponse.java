package com.example.a7movies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServerImagesResponse {
    @SerializedName("total")
    private String total;
    @SerializedName("totalPages")
    private String totalPages;
    @SerializedName("items")
    private List<Image>imagesList = new ArrayList<>();


    public ServerImagesResponse(String total, String totalPages, List<Image> videosList) {
        this.total = total;
        this.totalPages = totalPages;
        this.imagesList = videosList;
    }

    public String getTotal() {
        return total;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public List<Image> getVideosList() {
        return imagesList;
    }

    @Override
    public String toString() {
        return "ServerImagesResponse{" +
                "total='" + total + '\'' +
                ", totalPages='" + totalPages + '\'' +
                ", videosList=" + imagesList +
                '}';
    }
}
