package com.moying.energyring.Model;

import java.io.Serializable;

/**
 * Created by waylen on 2017/8/2.
 */

public class ProjectModel implements Serializable{
    private int projectId;
    private String name;
    private String ImgUrl;
    private String unit;
    private String reportNum;
    private double Limit;

    public double getLimit() {
        return Limit;
    }

    public void setLimit(double limit) {
        Limit = limit;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }



    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }



}
