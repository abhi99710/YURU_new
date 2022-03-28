
package com.app.yuru.ui.retrofitApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_url")
    @Expose
    private String authorUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("provider_url")
    @Expose
    private String providerUrl;
    @SerializedName("thumbnail_height")
    @Expose
    private Integer thumbnailHeight;
    @SerializedName("thumbnail_width")
    @Expose
    private Integer thumbnailWidth;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("mainlink")
    @Expose
    private String mainlink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getMainlink() {
        return mainlink;
    }

    public void setMainlink(String mainlink) {
        this.mainlink = mainlink;
    }

}
