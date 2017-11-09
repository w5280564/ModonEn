package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/10/24.
 */

public class newPk_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectID":1,"CreateTime":null,"ProjectUnit":"个","ProjectName":"俯卧撑","FilePath":"http://localhost:58245/Uploads/2017-10-25/b60e7b79-f3fb-44d7-861f-5de50a391366.png","ReportNum":10,"ReportID":50127,"Ranking":2,"Report_Num_Month":260,"Limit":100,"Report_Days":45,"Gray_FilePath":"http://localhost:58245/Uploads/2017-10-25/4770dff0-2a71-4950-9ecc-4ace5cd2763e.png"},{"ProjectID":7,"CreateTime":null,"ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":20,"ReportID":50125,"Ranking":1,"Report_Num_Month":20,"Limit":100,"Report_Days":2,"Gray_FilePath":"http://localhost:58245/Uploads/2017-10-25/28ab5976-5d72-431a-ae52-97f7fe9d2e83.jpg"}]
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
         * ProjectID : 1
         * CreateTime : null
         * ProjectUnit : 个
         * ProjectName : 俯卧撑
         * FilePath : http://localhost:58245/Uploads/2017-10-25/b60e7b79-f3fb-44d7-861f-5de50a391366.png
         * ReportNum : 10
         * ReportID : 50127
         * Ranking : 2
         * Report_Num_Month : 260.0
         * Limit : 100
         * Report_Days : 45
         * Gray_FilePath : http://localhost:58245/Uploads/2017-10-25/4770dff0-2a71-4950-9ecc-4ace5cd2763e.png
         */

        private int ProjectID;
        private Object CreateTime;
        private String ProjectUnit;
        private String ProjectName;
        private String FilePath;
        private int ReportNum;
        private int ReportID;
        private int Ranking;
        private double Report_Num_Month;
        private int Limit;
        private int Report_Days;
        private String Gray_FilePath;

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public Object getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(Object CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

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

        public int getReportNum() {
            return ReportNum;
        }

        public void setReportNum(int ReportNum) {
            this.ReportNum = ReportNum;
        }

        public int getReportID() {
            return ReportID;
        }

        public void setReportID(int ReportID) {
            this.ReportID = ReportID;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public double getReport_Num_Month() {
            return Report_Num_Month;
        }

        public void setReport_Num_Month(double Report_Num_Month) {
            this.Report_Num_Month = Report_Num_Month;
        }

        public int getLimit() {
            return Limit;
        }

        public void setLimit(int Limit) {
            this.Limit = Limit;
        }

        public int getReport_Days() {
            return Report_Days;
        }

        public void setReport_Days(int Report_Days) {
            this.Report_Days = Report_Days;
        }

        public String getGray_FilePath() {
            return Gray_FilePath;
        }

        public void setGray_FilePath(String Gray_FilePath) {
            this.Gray_FilePath = Gray_FilePath;
        }
    }
}
