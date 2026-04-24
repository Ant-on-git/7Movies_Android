package com.example.a7movies;

import com.google.gson.annotations.SerializedName;

public class MovieFact {
    @SerializedName("text")
    private String text;
    @SerializedName("type")
    private String type;
    @SerializedName("spoiler")
    private boolean spoiler;

    public MovieFact(String text, String type, boolean spoiler) {
        this.text = text;
        this.type = type;
        this.spoiler = spoiler;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    @Override
    public String toString() {
        return "MovieFact{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", spoiler=" + spoiler +
                '}';
    }
}
