package com.example.jhatpatdownload.data.model;

import java.util.List;

public class VideoFormatResponse {
    private String status;
    private List<VideoFormat> formats;

    public String getStatus() {
        return status;
    }

    public VideoFormatResponse(String status, List<VideoFormat> formats) {
        this.status = status;
        this.formats = formats;
    }

    public List<VideoFormat> getFormats() {
        return formats;
    }
}
