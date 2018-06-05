package com.moying.energyring.Model;

/**
 * Created by waylen on 2018/3/29.
 */

public class Training_TodaySet_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"ProjectID":3,"ProjectName":"深蹲","ProjectUnit":"个","FilePath":null,"ReportFre":10420,"Is_Disabled":false,"Limit":300,"FileID":0,"CreateTime":null,"ReportNum":0,"Gray_FileID":0,"Gray_FilePath":null,"ProjectTypeID":1,"UserID":0,"Is_Recommend":true,"Is_Recent":false,"Praise":"100,200,300,400,500,600,700,800,900,1000","ReportID":0,"RestInterval_s":30,"RestInterval_m":40,"RestInterval_l":50,"Interval":3,"Trainlimit":300,"GroupNum":100,"Is_Train":true,"GroupNums":"20,40,60,80,100","GroupCount":10}
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
        /**
         * ProjectID : 3
         * ProjectName : 深蹲
         * ProjectUnit : 个
         * FilePath : null
         * ReportFre : 10420
         * Is_Disabled : false
         * Limit : 300
         * FileID : 0
         * CreateTime : null
         * ReportNum : 0
         * Gray_FileID : 0
         * Gray_FilePath : null
         * ProjectTypeID : 1
         * UserID : 0
         * Is_Recommend : true
         * Is_Recent : false
         * Praise : 100,200,300,400,500,600,700,800,900,1000
         * ReportID : 0
         * RestInterval_s : 30
         * RestInterval_m : 40
         * RestInterval_l : 50
         * Interval : 3
         * Trainlimit : 300
         * GroupNum : 100
         * Is_Train : true
         * GroupNums : 20,40,60,80,100
         * GroupCount : 10
         */

        private int ProjectID;
        private String ProjectName;
        private String ProjectUnit;
        private Object FilePath;
        private int ReportFre;
        private boolean Is_Disabled;
        private int Limit;
        private int FileID;
        private Object CreateTime;
        private int ReportNum;
        private int Gray_FileID;
        private Object Gray_FilePath;
        private int ProjectTypeID;
        private int UserID;
        private boolean Is_Recommend;
        private boolean Is_Recent;
        private String Praise;
        private int ReportID;
        private int RestInterval_s;
        private int RestInterval_m;
        private int RestInterval_l;
        private int Interval;
        private int Trainlimit;
        private int GroupNum;
        private boolean Is_Train;
        private String GroupNums;
        private int GroupCount;

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
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

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }

        public int getReportFre() {
            return ReportFre;
        }

        public void setReportFre(int ReportFre) {
            this.ReportFre = ReportFre;
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

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }

        public Object getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(Object CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getReportNum() {
            return ReportNum;
        }

        public void setReportNum(int ReportNum) {
            this.ReportNum = ReportNum;
        }

        public int getGray_FileID() {
            return Gray_FileID;
        }

        public void setGray_FileID(int Gray_FileID) {
            this.Gray_FileID = Gray_FileID;
        }

        public Object getGray_FilePath() {
            return Gray_FilePath;
        }

        public void setGray_FilePath(Object Gray_FilePath) {
            this.Gray_FilePath = Gray_FilePath;
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

        public boolean isIs_Recommend() {
            return Is_Recommend;
        }

        public void setIs_Recommend(boolean Is_Recommend) {
            this.Is_Recommend = Is_Recommend;
        }

        public boolean isIs_Recent() {
            return Is_Recent;
        }

        public void setIs_Recent(boolean Is_Recent) {
            this.Is_Recent = Is_Recent;
        }

        public String getPraise() {
            return Praise;
        }

        public void setPraise(String Praise) {
            this.Praise = Praise;
        }

        public int getReportID() {
            return ReportID;
        }

        public void setReportID(int ReportID) {
            this.ReportID = ReportID;
        }

        public int getRestInterval_s() {
            return RestInterval_s;
        }

        public void setRestInterval_s(int RestInterval_s) {
            this.RestInterval_s = RestInterval_s;
        }

        public int getRestInterval_m() {
            return RestInterval_m;
        }

        public void setRestInterval_m(int RestInterval_m) {
            this.RestInterval_m = RestInterval_m;
        }

        public int getRestInterval_l() {
            return RestInterval_l;
        }

        public void setRestInterval_l(int RestInterval_l) {
            this.RestInterval_l = RestInterval_l;
        }

        public int getInterval() {
            return Interval;
        }

        public void setInterval(int Interval) {
            this.Interval = Interval;
        }

        public int getTrainlimit() {
            return Trainlimit;
        }

        public void setTrainlimit(int Trainlimit) {
            this.Trainlimit = Trainlimit;
        }

        public int getGroupNum() {
            return GroupNum;
        }

        public void setGroupNum(int GroupNum) {
            this.GroupNum = GroupNum;
        }

        public boolean isIs_Train() {
            return Is_Train;
        }

        public void setIs_Train(boolean Is_Train) {
            this.Is_Train = Is_Train;
        }

        public String getGroupNums() {
            return GroupNums;
        }

        public void setGroupNums(String GroupNums) {
            this.GroupNums = GroupNums;
        }

        public int getGroupCount() {
            return GroupCount;
        }

        public void setGroupCount(int GroupCount) {
            this.GroupCount = GroupCount;
        }
    }
}
