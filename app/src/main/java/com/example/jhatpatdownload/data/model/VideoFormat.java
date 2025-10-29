package com.example.jhatpatdownload.data.model;

public class VideoFormat {
    String formatId;
    String resolution;
    String ext;
    String url;
    String filesize;

    public VideoFormat(String formatId, String resolution, String ext, String url, String filesize) {
        this.formatId = formatId;
        this.resolution = resolution;
        this.ext = ext;
        this.url = url;
        this.filesize = filesize;
    }

    public String getFormatId() {
        return formatId;
    }

    public String getResolution() {
        return resolution;
    }

    public String getExt() {
        return ext;
    }

    public String getUrl() {
        return url;
    }

    public String getFilesize() {
        return filesize;
    }
}
