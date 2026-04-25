package com.example.a7movies.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("previewUrl")
    private String previewUrl;


    public Image(String previewUrl, String imageUrl) {
        this.previewUrl = previewUrl;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageUrl='" + imageUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                '}';
    }
}
