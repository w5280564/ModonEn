package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/8/2.
 */

public class Project_TabSeek_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"ProjectID":28,"ProjectTypeID":2,"UserID":0,"ProjectName":"戒游戏","ProjectUnit":"天","ReportFre":1628,"Praise":"3,5,7,10,14,21,30,50,100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":1,"Is_Recommend":false,"GroupNum":0,"Interval":0,"Is_Train":false,"RestInterval_l":0,"RestInterval_m":0,"RestInterval_s":0,"Trainlimit":0,"GroupCount":0,"GroupNums":null,"Join_Num":0},{"ProjectID":29,"ProjectTypeID":2,"UserID":0,"ProjectName":"戒网络小说","ProjectUnit":"天","ReportFre":1498,"Praise":"3,5,7,10,14,21,30,50,100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":1,"Is_Recommend":false,"GroupNum":0,"Interval":0,"Is_Train":false,"RestInterval_l":0,"RestInterval_m":0,"RestInterval_s":0,"Trainlimit":0,"GroupCount":0,"GroupNums":null,"Join_Num":0},{"ProjectID":64,"ProjectTypeID":2,"UserID":0,"ProjectName":"戒烟","ProjectUnit":"天","ReportFre":275,"Praise":"3,5,7,10,14,21,30,50,100,200,300,400,500,600,700,800,900,1000","Is_Disabled":false,"Limit":1,"Is_Recommend":false,"GroupNum":0,"Interval":0,"Is_Train":false,"RestInterval_l":0,"RestInterval_m":0,"RestInterval_s":0,"Trainlimit":0,"GroupCount":0,"GroupNums":null,"Join_Num":0}]
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
         * ProjectID : 28
         * ProjectTypeID : 2
         * UserID : 0
         * ProjectName : 戒游戏
         * ProjectUnit : 天
         * ReportFre : 1628
         * Praise : 3,5,7,10,14,21,30,50,100,200,300,400,500,600,700,800,900,1000
         * Is_Disabled : false
         * Limit : 1.0
         * Is_Recommend : false
         * GroupNum : 0
         * Interval : 0
         * Is_Train : false
         * RestInterval_l : 0
         * RestInterval_m : 0
         * RestInterval_s : 0
         * Trainlimit : 0.0
         * GroupCount : 0
         * GroupNums : null
         * Join_Num : 0
         */

        private int ProjectID;
        private int ProjectTypeID;
        private int UserID;
        private String ProjectName;
        private String ProjectUnit;
        private int ReportFre;
        private String Praise;
        private boolean Is_Disabled;
        private double Limit;
        private boolean Is_Recommend;
        private int GroupNum;
        private int Interval;
        private boolean Is_Train;
        private int RestInterval_l;
        private int RestInterval_m;
        private int RestInterval_s;
        private double Trainlimit;
        private int GroupCount;
        private Object GroupNums;
        private int Join_Num;

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

        public double getLimit() {
            return Limit;
        }

        public void setLimit(double Limit) {
            this.Limit = Limit;
        }

        public boolean isIs_Recommend() {
            return Is_Recommend;
        }

        public void setIs_Recommend(boolean Is_Recommend) {
            this.Is_Recommend = Is_Recommend;
        }

        public int getGroupNum() {
            return GroupNum;
        }

        public void setGroupNum(int GroupNum) {
            this.GroupNum = GroupNum;
        }

        public int getInterval() {
            return Interval;
        }

        public void setInterval(int Interval) {
            this.Interval = Interval;
        }

        public boolean isIs_Train() {
            return Is_Train;
        }

        public void setIs_Train(boolean Is_Train) {
            this.Is_Train = Is_Train;
        }

        public int getRestInterval_l() {
            return RestInterval_l;
        }

        public void setRestInterval_l(int RestInterval_l) {
            this.RestInterval_l = RestInterval_l;
        }

        public int getRestInterval_m() {
            return RestInterval_m;
        }

        public void setRestInterval_m(int RestInterval_m) {
            this.RestInterval_m = RestInterval_m;
        }

        public int getRestInterval_s() {
            return RestInterval_s;
        }

        public void setRestInterval_s(int RestInterval_s) {
            this.RestInterval_s = RestInterval_s;
        }

        public double getTrainlimit() {
            return Trainlimit;
        }

        public void setTrainlimit(double Trainlimit) {
            this.Trainlimit = Trainlimit;
        }

        public int getGroupCount() {
            return GroupCount;
        }

        public void setGroupCount(int GroupCount) {
            this.GroupCount = GroupCount;
        }

        public Object getGroupNums() {
            return GroupNums;
        }

        public void setGroupNums(Object GroupNums) {
            this.GroupNums = GroupNums;
        }

        public int getJoin_Num() {
            return Join_Num;
        }

        public void setJoin_Num(int Join_Num) {
            this.Join_Num = Join_Num;
        }
    }
}
