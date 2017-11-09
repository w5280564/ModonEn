package com.moying.energyring.waylenBaseView.viewpagercards;


public class PkSuccess_CardItem {
    private String imgUri;
    private String projectName;
    private String leiName;
    private String todayName;
    private String projectUilt;

    public String getProjectUilt() {
        return projectUilt;
    }

    public void setProjectUilt(String projectUilt) {
        this.projectUilt = projectUilt;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLeiName() {
        return leiName;
    }

    public void setLeiName(String leiName) {
        this.leiName = leiName;
    }


    public String getTodayName() {
        return todayName;
    }

    public void setTodayName(String todayName) {
        this.todayName = todayName;
    }


    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public PkSuccess_CardItem(String imgurl, String projectName, String leiName, String todayName ,String projectUilt) {
        imgUri = imgurl;
        this.projectName = projectName;
        this.leiName = leiName;
        this.todayName = todayName;
        this.projectUilt = projectUilt;
    }

}
