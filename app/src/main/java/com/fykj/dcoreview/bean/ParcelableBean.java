package com.fykj.dcoreview.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by administrator on 2018/7/26.
 * Parcelable 实例化测试类
 */

public class ParcelableBean implements Parcelable {

    private String color;
    private int size;


    /******************** 使用 Parcelable 插件生成的部分***********************/

    @Override
    public int describeContents() {
        return 0;  // 内容接口描述，默认返回0即可。
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);   // 写出 color
        dest.writeInt(this.size);   // 写出 size
    }

    // 系统自动添加，给createFromParcel里面用
    protected ParcelableBean(Parcel in) {
        this.color = in.readString();
        this.size = in.readInt();
    }

    public static final Parcelable.Creator<ParcelableBean> CREATOR = new Parcelable.Creator<ParcelableBean>() {

        /**
         * @param source
         * @return
         * createFromParcel()方法中我们要去读取刚才写出的name和age字段，
         * 并创建一个Person对象进行返回，其中color和size都是调用Parcel的readXxx()方法读取到的，
         * 注意这里读取的顺序一定要和刚才写出的顺序完全相同。
         * 读取的工作我们利用一个构造函数帮我们完成了
         */
        @Override
        public ParcelableBean createFromParcel(Parcel source) {
            return new ParcelableBean(source);
        }

        //供反序列化本类数组时调用的
        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };


    /******************** 自己写的部分***********************/
    public ParcelableBean() {}

    public ParcelableBean(String color, int size) {
        this.color = color;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
