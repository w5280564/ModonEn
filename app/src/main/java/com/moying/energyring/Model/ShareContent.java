package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * package : com.moying.energyring.wxapi<br>
 * description:<B>        </B><br>
 * email : cookiexie@adinnet.cn<br>
 * date : 2016/1/20 15:31<br>
 *
 * @author Cookie Xie
 * @version v1.0
 */

public class ShareContent implements Parcelable {
    public String url;
    public String title;
    public String content;

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String imgpath;

    public ShareContent(String url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;
    }

    private ShareContent(Parcel in) {
        url = in.readString();
        title = in.readString();
        content = in.readString();
        imgpath = in.readString();
    }

    public static final Creator<ShareContent> CREATOR = new Creator<ShareContent>() {
        @Override
        public ShareContent createFromParcel(Parcel in) {
            return new ShareContent(in);
        }

        @Override
        public ShareContent[] newArray(int size) {
            return new ShareContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imgpath);
    }
}