package com.fykj.dcoreview.model.recyclerView.magazineRv;

/**
 * Created by administrator on 2018/7/2.
 */

public class MagazineBean {

    private int resId;    //为-1，代表占位用
    private String value;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MagazineBean(int resId, String value) {
        this.resId = resId;
        this.value = value;
    }
}
