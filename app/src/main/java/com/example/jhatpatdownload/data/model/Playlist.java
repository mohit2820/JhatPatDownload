package com.example.jhatpatdownload.data.model;

import java.util.List;

public class Playlist {
    private String title;
    private String channel;
    private List<VideoItem> videos;

    public Playlist(String title, String channel, List<VideoItem> videos) {
        this.title = title;
        this.channel = channel;
        this.videos = videos;
    }

    public String getTitle() {
        return title;
    }

    public String getChannel() {
        return channel;
    }

    public List<VideoItem> getVideos() {
        return videos;
    }
}
