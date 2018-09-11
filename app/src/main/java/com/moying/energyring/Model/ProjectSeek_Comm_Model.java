package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/8/2.
 */

public class ProjectSeek_Comm_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"ProjectID":35,"ProjectTypeID":1,"UserID":0,"ProjectName":"健康走","ProjectUnit":"步","ReportFre":164546,"Praise":"5000,10000,20000,30000,40000,50000,60000,70000,80000,90000,100000,110000,120000,130000,140000","Is_Disabled":false,"Limit":30000,"Is_Recommend":true,"GroupNum":null,"Interval":null,"Is_Train":false,"RestInterval_l":null,"RestInterval_m":null,"RestInterval_s":null,"Trainlimit":null,"GroupCount":null,"GroupNums":null},{"ProjectID":50,"ProjectTypeID":2,"UserID":0,"ProjectName":"早起","ProjectUnit":"天","ReportFre":11636,"Praise":"3,5,7,10,14,21,30,50,100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":1,"Is_Recommend":false,"GroupNum":0,"Interval":0,"Is_Train":false,"RestInterval_l":0,"RestInterval_m":0,"RestInterval_s":0,"Trainlimit":0,"GroupCount":0,"GroupNums":null},{"ProjectID":3,"ProjectTypeID":1,"UserID":0,"ProjectName":"深蹲","ProjectUnit":"个","ReportFre":10904,"Praise":"100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":300,"Is_Recommend":true,"GroupNum":100,"Interval":3,"Is_Train":true,"RestInterval_l":50,"RestInterval_m":40,"RestInterval_s":30,"Trainlimit":300,"GroupCount":10,"GroupNums":"10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200"},{"ProjectID":1,"ProjectTypeID":1,"UserID":0,"ProjectName":"俯卧撑","ProjectUnit":"个","ReportFre":7668,"Praise":"100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":300,"Is_Recommend":true,"GroupNum":100,"Interval":3,"Is_Train":true,"RestInterval_l":50,"RestInterval_m":40,"RestInterval_s":30,"Trainlimit":300,"GroupCount":10,"GroupNums":"10,20,30,40,50,60,70,80,90,100"},{"ProjectID":31,"ProjectTypeID":1,"UserID":0,"ProjectName":"卷腹","ProjectUnit":"个","ReportFre":5907,"Praise":"100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":300,"Is_Recommend":true,"GroupNum":100,"Interval":3,"Is_Train":true,"RestInterval_l":50,"RestInterval_m":40,"RestInterval_s":30,"Trainlimit":300,"GroupCount":10,"GroupNums":"10,20,30,40,50,60,70,80,90,100"}]
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
         * ProjectID : 35
         * ProjectTypeID : 1
         * UserID : 0
         * ProjectName : 健康走
         * ProjectUnit : 步
         * ReportFre : 164546
         * Praise : 5000,10000,20000,30000,40000,50000,60000,70000,80000,90000,100000,110000,120000,130000,140000
         * Is_Disabled : false
         * Limit : 30000
         * Is_Recommend : true
         * GroupNum : null
         * Interval : null
         * Is_Train : false
         * RestInterval_l : null
         * RestInterval_m : null
         * RestInterval_s : null
         * Trainlimit : null
         * GroupCount : null
         * GroupNums : null
         */

        private int ProjectID;
        private int ProjectTypeID;
        private int UserID;
        private String ProjectName;
        private String ProjectUnit;
        private int ReportFre;
        private String Praise;
        private boolean Is_Disabled;
        private int Limit;
        private boolean Is_Recommend;
        private Object GroupNum;
        private Object Interval;
        private boolean Is_Train;
        private Object RestInterval_l;
        private Object RestInterval_m;
        private Object RestInterval_s;
        private Object Trainlimit;
        private Object GroupCount;
        private Object GroupNums;

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public int getProjectTypeID() {
            return ProjectTypeID;
        }

        public void setProjectTypeID(int ProjectTypeID) {
            this.ProjectTypeID = ProjectTypeID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

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

        public int getReportFre() {
            return ReportFre;
        }

        public void setReportFre(int ReportFre) {
            this.ReportFre = ReportFre;
        }

        public String getPraise() {
            return Praise;
        }

        public void setPraise(String Praise) {
            this.Praise = Praise;
        }

        public boolean isIs_Disabled() {
            return Is_Disabled;
        }

        public void setIs_Disabled(boolean Is_Disabled) {
            this.Is_Disabled = Is_Disabled;
        }

        public int getLimit() {
            return Limit;
        }

        public void setLimit(int Limit) {
            this.Limit = Limit;
        }

        public boolean isIs_Recommend() {
            return Is_Recommend;
        }

        public void setIs_Recommend(boolean Is_Recommend) {
            this.Is_Recommend = Is_Recommend;
        }

        public Object getGroupNum() {
            return GroupNum;
        }

        public void setGroupNum(Object GroupNum) {
            this.GroupNum = GroupNum;
        }

        public Object getInterval() {
            return Interval;
        }

        public void setInterval(Object Interval) {
            this.Interval = Interval;
        }

        public boolean isIs_Train() {
            return Is_Train;
        }

        public void setIs_Train(boolean Is_Train) {
            this.Is_Train = Is_Train;
        }

        public Object getRestInterval_l() {
            return RestInterval_l;
        }

        public void setRestInterval_l(Object RestInterval_l) {
            this.RestInterval_l = RestInterval_l;
        }

        public Object getRestInterval_m() {
            return RestInterval_m;
        }

        public void setRestInterval_m(Object RestInterval_m) {
            this.RestInterval_m = RestInterval_m;
        }

        public Object getRestInterval_s() {
            return RestInterval_s;
        }

        public void setRestInterval_s(Object RestInterval_s) {
            this.RestInterval_s = RestInterval_s;
        }

        public Object getTrainlimit() {
            return Trainlimit;
        }

        public void setTrainlimit(Object Trainlimit) {
            this.Trainlimit = Trainlimit;
        }

        public Object getGroupCount() {
            return GroupCount;
        }

        public void setGroupCount(Object GroupCount) {
            this.GroupCount = GroupCount;
        }

        public Object getGroupNums() {
            return GroupNums;
        }

        public void setGroupNums(Object GroupNums) {
            this.GroupNums = GroupNums;
        }
    }
}
