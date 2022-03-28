package com.app.yuru.ui.library;

import java.io.Serializable;

public class ModelLibrary implements Serializable {

    private String id;
    private String nameVideo;
    private String author;
    private String duration;
    private String thumb;
    private String videoLink;
    private String authorImg;
    private String description;

    public ModelLibrary(String id, String nameVideo, String author, String duration, String thumb, String videoLink, String authorImg, String description) {
        this.id = id;
        this.nameVideo = nameVideo;
        this.author = author;
        this.duration = duration;
        this.thumb = thumb;
        this.videoLink = videoLink;
        this.authorImg = authorImg;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
