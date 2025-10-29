package com.example.jhatpatdownload.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jhatpatdownload.data.model.Playlist;
import com.example.jhatpatdownload.data.model.PlaylistRequest;
import com.example.jhatpatdownload.data.model.PlaylistResponse;
import com.example.jhatpatdownload.data.model.VideoFormat;
import com.example.jhatpatdownload.data.model.VideoFormatResponse;
import com.example.jhatpatdownload.data.model.VideoItem;
import com.example.jhatpatdownload.data.model.VideoUrlRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistRepository {

    private final PlaylistApiService api;

    public PlaylistRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.161:5000/")   // local host
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(PlaylistApiService.class);
    }

    public LiveData<Playlist> fetchPlaylist(String url) {
        MutableLiveData<Playlist> data = new MutableLiveData<>();

        api.getPlaylistInfo(new PlaylistRequest(url)).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    data.setValue(response.body().toPlaylist());
                }
                else {
                    data.setValue(null); // This will trigger the observer with null
                }
            }



            @Override
            public void onFailure(Call<PlaylistResponse> call, Throwable t) {
                data.setValue(null);

            }
        });

        return data;
    }

    public LiveData<List<VideoFormat>> fetchVideoFormats(String url){

        MutableLiveData<List<VideoFormat>> data = new MutableLiveData<>();

        api.getVideoFormats(new VideoUrlRequest(url)).enqueue(new Callback<VideoFormatResponse>() {
            @Override
            public void onResponse(Call<VideoFormatResponse> call, Response<VideoFormatResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    data.setValue(response.body().getFormats());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<VideoFormatResponse> call, Throwable t) {
                data.setValue(null);
            }
        });


        return data;
    }
}
