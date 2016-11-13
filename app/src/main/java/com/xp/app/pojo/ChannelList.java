package com.xp.app.pojo;



public class ChannelList {

    String Channeltitle;
    String thumbnailurl;
    String Title;
    String description;
    String datetime;
    String videoId;



    public String getVideoId() {
        return videoId;
    }



    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public String getChanneltitle() {
        return Channeltitle;
    }

    public void setChanneltitle(String channeltitle) {
        Channeltitle = channeltitle;
    }

}
