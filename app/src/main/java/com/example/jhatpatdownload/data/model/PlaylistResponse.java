package com.example.jhatpatdownload.data.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlaylistResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Data data;

    public Playlist toPlaylist() {
        return new Playlist(
                data.title,
                data.channel,
                data.entries
        );
    }

    static class Data {
        @SerializedName("title")
        String title;

        @SerializedName("channel")
        String channel;

        @SerializedName("entries")
        List<VideoItem> entries;
    }
}
