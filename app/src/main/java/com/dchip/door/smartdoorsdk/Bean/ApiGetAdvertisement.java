package com.dchip.door.smartdoorsdk.Bean;

import java.util.List;

/**
 * Created by jelly on 2018/1/4.
 */

public class ApiGetAdvertisement {
    List<AdvertisementModel> bannerVideoList;
    List<AdvertisementModel> bannerPicList;

    public List<AdvertisementModel> getBannerVideoList() {
        return bannerVideoList;
    }

    public void setBannerVideoList(List<AdvertisementModel> bannerVideoList) {
        this.bannerVideoList = bannerVideoList;
    }
}
