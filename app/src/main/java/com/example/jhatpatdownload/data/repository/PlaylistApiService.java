package com.example.jhatpatdownload.data.repository;

import com.example.jhatpatdownload.data.model.PlaylistRequest;
import com.example.jhatpatdownload.data.model.PlaylistResponse;
import com.example.jhatpatdownload.data.model.VideoFormatResponse;
import com.example.jhatpatdownload.data.model.VideoUrlRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlaylistApiService {

    @POST("get_playlist_info")
    Call<PlaylistResponse> getPlaylistInfo(@Body PlaylistRequest request);

    @POST("get_video_links")
    Call<VideoFormatResponse> getVideoFormats(@Body VideoUrlRequest request);
}
