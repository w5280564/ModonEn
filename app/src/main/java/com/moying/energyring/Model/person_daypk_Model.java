package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/15.
 */

public class person_daypk_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectName":"俯卧撑","FilePath":"http://172.16.0.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png","ProjectUnit":"个","Ranking":1,"ReportID":46,"UserID":0,"ProjectID":12,"ReportNum":50}]
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private List<DataBean> Data;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * ProjectName : 俯卧撑
         * FilePath : http://172.16.0.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png
         * ProjectUnit : 个
         * Ranking : 1
         * ReportID : 46
         * UserID : 0
         * ProjectID : 12
         * ReportNum : 50.0
         */

        private String ProjectName;
        private String FilePath;
        private String ProjectUnit;
        private int Ranking;
        private int ReportID;
        private int UserID;
        private int ProjectID;
        private double ReportNum;

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getReportID() {
            return ReportID;
        }

        public void setReportID(int ReportID) {
            this.ReportID = ReportID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }
    }
}
