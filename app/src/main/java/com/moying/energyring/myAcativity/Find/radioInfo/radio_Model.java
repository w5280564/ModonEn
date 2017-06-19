package com.moying.energyring.myAcativity.Find.radioInfo;

/**
 * Created by waylen on 2017/6/9.
 */

public class radio_Model {

    private String radioName;
    private String radioImgpath;
    private String radioUrl;
    private int radioisPlay;//0是停止 1是暂停 2是播放
    private String radioStopColock;

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public String getRadioImgpath() {
        return radioImgpath;
    }

    public void setRadioImgpath(String radioImgpath) {
        this.radioImgpath = radioImgpath;
    }

    public String getRadioUrl() {
        return radioUrl;
    }

    public void setRadioUrl(String radioUrl) {
        this.radioUrl = radioUrl;
    }

    public int getRadioisPlay() {
        return radioisPlay;
    }

    public void setRadioisPlay(int radioisPlay) {
        this.radioisPlay = radioisPlay;
    }



    public String getRadioStopColock() {
        return radioStopColock;
    }

    public void setRadioStopColock(String radioStopColock) {
        this.radioStopColock = radioStopColock;
    }

//    public radio_Model(String radioName,String radioImgpath,String radioUrl){
//        this.radioName = radioName;
//        this.radioImgpath = radioImgpath;
//        this.radioUrl = radioUrl;
//    }



}
