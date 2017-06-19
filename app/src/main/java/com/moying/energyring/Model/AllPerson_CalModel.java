package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/4/10.
 */

public class AllPerson_CalModel {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"Target_List":[{"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":null,"TotalDays":0,"FinishDays":0,"ReportDate":"2017-05-23 00:00:00","TargetID":3,"StartDate":null,"EndDate":null,"ProjectID":12,"UserID":2,"ReportNum":50,"CreateDate":null,"Is_Del":false,"Is_Finish":false},{"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":null,"TotalDays":0,"FinishDays":0,"ReportDate":"2017-05-24 00:00:00","TargetID":3,"StartDate":null,"EndDate":null,"ProjectID":12,"UserID":2,"ReportNum":50,"CreateDate":null,"Is_Del":false,"Is_Finish":false},{"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":null,"TotalDays":0,"FinishDays":0,"ReportDate":"2017-05-25 00:00:00","TargetID":3,"StartDate":null,"EndDate":null,"ProjectID":12,"UserID":2,"ReportNum":50,"CreateDate":null,"Is_Del":false,"Is_Finish":false},{"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":null,"TotalDays":0,"FinishDays":0,"ReportDate":"2017-05-26 00:00:00","TargetID":3,"StartDate":null,"EndDate":null,"ProjectID":12,"UserID":2,"ReportNum":50,"CreateDate":null,"Is_Del":false,"Is_Finish":false},{"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":null,"TotalDays":0,"FinishDays":0,"ReportDate":"2017-05-27 00:00:00","TargetID":3,"StartDate":null,"EndDate":null,"ProjectID":12,"UserID":2,"ReportNum":50,"CreateDate":null,"Is_Del":false,"Is_Finish":false}],"Target_Deials_List":[{"TargetDeialsID":0,"TargetID":0,"ReportDate":"2017-05-23 00:00:00","ReportNum":0,"Is_Finish":false},{"TargetDeialsID":0,"TargetID":0,"ReportDate":"2017-05-24 00:00:00","ReportNum":0,"Is_Finish":false},{"TargetDeialsID":0,"TargetID":0,"ReportDate":"2017-05-25 00:00:00","ReportNum":0,"Is_Finish":false},{"TargetDeialsID":0,"TargetID":0,"ReportDate":"2017-05-26 00:00:00","ReportNum":0,"Is_Finish":false},{"TargetDeialsID":0,"TargetID":0,"ReportDate":"2017-05-27 00:00:00","ReportNum":0,"Is_Finish":false}]}
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private List<TargetListBean> Target_List;
        private List<TargetDeialsListBean> Target_Deials_List;

        public List<TargetListBean> getTarget_List() {
            return Target_List;
        }

        public void setTarget_List(List<TargetListBean> Target_List) {
            this.Target_List = Target_List;
        }

        public List<TargetDeialsListBean> getTarget_Deials_List() {
            return Target_Deials_List;
        }

        public void setTarget_Deials_List(List<TargetDeialsListBean> Target_Deials_List) {
            this.Target_Deials_List = Target_Deials_List;
        }

        public static class TargetListBean {
            /**
             * ProjectName : 俯卧撑
             * ProjectUnit : 个
             * FilePath : null
             * TotalDays : 0
             * FinishDays : 0
             * ReportDate : 2017-05-23 00:00:00
             * TargetID : 3
             * StartDate : null
             * EndDate : null
             * ProjectID : 12
             * UserID : 2
             * ReportNum : 50.0
             * CreateDate : null
             * Is_Del : false
             * Is_Finish : false
             */

            private String ProjectName;
            private String ProjectUnit;
            private Object FilePath;
            private int TotalDays;
            private int FinishDays;
            private String ReportDate;
            private int TargetID;
            private Object StartDate;
            private Object EndDate;
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

            public Object getFilePath() {
                return FilePath;
            }

            public void setFilePath(Object FilePath) {
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

            public String getReportDate() {
                return ReportDate;
            }

            public void setReportDate(String ReportDate) {
                this.ReportDate = ReportDate;
            }

            public int getTargetID() {
                return TargetID;
            }

            public void setTargetID(int TargetID) {
                this.TargetID = TargetID;
            }

            public Object getStartDate() {
                return StartDate;
            }

            public void setStartDate(Object StartDate) {
                this.StartDate = StartDate;
            }

            public Object getEndDate() {
                return EndDate;
            }

            public void setEndDate(Object EndDate) {
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

        public static class TargetDeialsListBean {
            /**
             * TargetDeialsID : 0
             * TargetID : 0
             * ReportDate : 2017-05-23 00:00:00
             * ReportNum : 0.0
             * Is_Finish : false
             */

            private int TargetDeialsID;
            private int TargetID;
            private String ReportDate;
            private double ReportNum;
            private boolean Is_Finish;

            public int getTargetDeialsID() {
                return TargetDeialsID;
            }

            public void setTargetDeialsID(int TargetDeialsID) {
                this.TargetDeialsID = TargetDeialsID;
            }

            public int getTargetID() {
                return TargetID;
            }

            public void setTargetID(int TargetID) {
                this.TargetID = TargetID;
            }

            public String getReportDate() {
                return ReportDate;
            }

            public void setReportDate(String ReportDate) {
                this.ReportDate = ReportDate;
            }

            public double getReportNum() {
                return ReportNum;
            }

            public void setReportNum(double ReportNum) {
                this.ReportNum = ReportNum;
            }

            public boolean isIs_Finish() {
                return Is_Finish;
            }

            public void setIs_Finish(boolean Is_Finish) {
                this.Is_Finish = Is_Finish;
            }
        }
    }
}
