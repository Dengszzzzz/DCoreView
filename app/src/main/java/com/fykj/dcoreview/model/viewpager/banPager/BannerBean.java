package com.fykj.dcoreview.model.viewpager.banPager;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 * 广告 实体类
 */

public class BannerBean {

    private int id;
    private String imgPath;
    private String linkUrl;

    public BannerBean() {
    }

    public BannerBean(int id, String imgPath, String linkUrl) {
        this.id = id;
        this.imgPath = imgPath;
        this.linkUrl = linkUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
