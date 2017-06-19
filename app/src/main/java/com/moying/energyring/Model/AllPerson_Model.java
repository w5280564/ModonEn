package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/3/17.
 */

public class AllPerson_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":"http://192.168.1.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png","TotalDays":5,"FinishDays":0,"ReportDate":null,"TargetID":3,"StartDate":"2017/5/19 0:00:00","EndDate":"2017/5/23 23:59:00","ProjectID":0,"UserID":2,"ReportNum":50,"CreateDate":null,"Is_Del":false,"Is_Finish":false}]
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
         * ProjectUnit : 个
         * FilePath : http://192.168.1.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png
         * TotalDays : 5
         * FinishDays : 0
         * ReportDate : null
         * TargetID : 3
         * StartDate : 2017/5/19 0:00:00
         * EndDate : 2017/5/23 23:59:00
         * ProjectID : 0
         * UserID : 2
         * ReportNum : 50.0
         * CreateDate : null
         * Is_Del : false
         * Is_Finish : false
         */

        private String ProjectName;
        private String ProjectUnit;
        private String FilePath;
        private int TotalDays;
        private int FinishDays;
        private Object ReportDate;
        private int TargetID;
        private String StartDate;
        private String EndDate;
        private int ProjectID;
        private int UserID;
        private double ReportNum;
        private Object CreateDate;
        private boolean Is_Del;
        private boolean Is_Finish;

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public int getTotalDays() {
            return TotalDays;
        }

        public void setTotalDays(int TotalDays) {
            this.TotalDays = TotalDays;
        }

        public int getFinishDays() {
            return FinishDays;
        }

        public void setFinishDays(int FinishDays) {
            this.FinishDays = FinishDays;
        }

        public Object getReportDate() {
            return ReportDate;
        }

        public void setReportDate(Object ReportDate) {
            this.ReportDate = ReportDate;
        }

        public int getTargetID() {
            return TargetID;
        }

        public void setTargetID(int TargetID) {
            this.TargetID = TargetID;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }

        public Object getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(Object CreateDate) {
            this.CreateDate = CreateDate;
        }

        public boolean isIs_Del() {
            return Is_Del;
        }

        public void setIs_Del(boolean Is_Del) {
            this.Is_Del = Is_Del;
        }

        public boolean isIs_Finish() {
            return Is_Finish;
        }

        public void setIs_Finish(boolean Is_Finish) {
            this.Is_Finish = Is_Finish;
        }
    }
}
