package com.example.jhatpatdownload.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.jhatpatdownload.data.model.Playlist;
import com.example.jhatpatdownload.data.model.VideoFormat;
import com.example.jhatpatdownload.data.model.VideoItem;
import com.example.jhatpatdownload.data.repository.PlaylistRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {

    private final PlaylistRepository repository;
    private final MutableLiveData<Playlist> playlistLiveData = new MutableLiveData<>();
    private MutableLiveData<List<VideoFormat>> videoFormats = new MutableLiveData<>();

    public MainViewModel() {
        repository = new PlaylistRepository();
    }

    public LiveData<Playlist> getPlaylist() {
        return playlistLiveData;
    }

    public void fetchPlaylist(String url) {
        repository.fetchPlaylist(url).observeForever(playlistLiveData::setValue);
    }

    public LiveData<List<VideoFormat>> getVideoFormats() {
        return videoFormats;
    }


    // video url se quality fetch karna hai

    public void fetchVideoFormats(String videoUrl) {
        repository.fetchVideoFormats(videoUrl).observeForever(formats -> {
            if (formats == null) {
                videoFormats.setValue(null);
                return;
            }

            // quality jo dikhani hai
            List<String> allowedHeights = Arrays.asList("360", "480", "720", "1080");
            // Store one format per height
            Map<String, VideoFormat> uniqueFormats = new HashMap<>();

            for (VideoFormat format : formats) {
                if (format.getExt() != null && format.getExt().equalsIgnoreCase("mp4")) {
                    String resolution = format.getResolution(); // "494x360"
                    if (resolution != null && resolution.contains("x")) {
                        String height = resolution.split("x")[1]; // eg. "360"

                        // Only add first match for each height
                        if (allowedHeights.contains(height) && !uniqueFormats.containsKey(height)) {
                            uniqueFormats.put(height, format);
                        }
                    }
                }
            }

            // Output list of only 360, 480, 720, 1080 (if present)
            List<VideoFormat> filtered = new ArrayList<>();
            for (String h : allowedHeights) {
                if (uniqueFormats.containsKey(h)) {
                    filtered.add(uniqueFormats.get(h));
                }
            }

            videoFormats.setValue(filtered);
        });
    }




}

