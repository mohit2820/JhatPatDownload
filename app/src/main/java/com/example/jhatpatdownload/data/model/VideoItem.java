package com.example.jhatpatdownload.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VideoItem {

    @SerializedName("title")          // JSON → title
    private String title;

    @SerializedName("url")            // JSON → url
    private String videoUrl;

    @SerializedName("thumbnails")     // JSON → thumbnails[]
    private List<Thumb> thumbnails;   // internal list

    static class Thumb {
        @SerializedName("url")
        String url;
    }


    public String getTitle() {
        return title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }


    // we get the first thumbnail
    public String getThumbnailUrl() {
        return (thumbnails != null && !thumbnails.isEmpty())
                ? thumbnails.get(0).url
                : null;
    }
}
