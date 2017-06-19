package com.moying.energyring.waylenBaseView.viewpagercards;


public class Radio_CardItem {
    private String imgUri;
    private String radio_Name;

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getRadio_Name() {
        return radio_Name;
    }

    public void setRadio_Name(String radio_Name) {
        this.radio_Name = radio_Name;
    }



//    private int mTextResource;
//    private int mTitleResource;

    public Radio_CardItem(String imgurl,String radio_Name) {
//        mTitleResource = title;
//        mTextResource = text;
        imgUri = imgurl;
        this.radio_Name = radio_Name;
    }

//    public int getText() {
//        return mTextResource;
//    }
//
//    public int getTitle() {
//        return mTitleResource;
//    }
}
