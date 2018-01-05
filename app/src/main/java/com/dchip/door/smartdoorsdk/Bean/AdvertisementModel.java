package com.dchip.door.smartdoorsdk.Bean;

/**
 * Created by jelly on 2018/1/4.
 */

public class AdvertisementModel {
    String photo="";
    int type = 0;
    String video = "";
    String md5 = "";

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
