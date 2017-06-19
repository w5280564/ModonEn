package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/5/28.
 */

public class PersonData_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"ProjectUnit":"个","NickName":"Start丶小夏","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917","Likes":0,"Is_Like":false,"PKCoverImg":null,"Ranking":0,"Limit":150,"ReportID":27,"UserID":3,"ProjectID":11,"CreateTime":null,"ReportFre":0,"ReportNum":50,"FileIDs":null,"Is_Sync":false}
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
         * ProjectUnit : 个
         * NickName : Start丶小夏
         * ProfilePicture : http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917
         * Likes : 0
         * Is_Like : false
         * PKCoverImg : null
         * Ranking : 0
         * Limit : 150.0
         * ReportID : 27
         * UserID : 3
         * ProjectID : 11
         * CreateTime : null
         * ReportFre : 0
         * ReportNum : 50.0
         * FileIDs : null
         * Is_Sync : false
         */

        private String ProjectUnit;
        private String NickName;
        private String ProfilePicture;
        private int Likes;
        private boolean Is_Like;
        private Object PKCoverImg;
        private int Ranking;
        private double Limit;
        private int ReportID;
        private int UserID;
        private int ProjectID;
        private Object CreateTime;
        private int ReportFre;
        private double ReportNum;
        private Object FileIDs;
        private boolean Is_Sync;

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getProfilePicture() {
            return ProfilePicture;
        }

        public void setProfilePicture(String ProfilePicture) {
            this.ProfilePicture = ProfilePicture;
        }

        public int getLikes() {
            return Likes;
        }

        public void setLikes(int Likes) {
            this.Likes = Likes;
        }

        public boolean isIs_Like() {
            return Is_Like;
        }

        public void setIs_Like(boolean Is_Like) {
            this.Is_Like = Is_Like;
        }

        public Object getPKCoverImg() {
            return PKCoverImg;
        }

        public void setPKCoverImg(Object PKCoverImg) {
            this.PKCoverImg = PKCoverImg;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public double getLimit() {
            return Limit;
        }

        public void setLimit(double Limit) {
            this.Limit = Limit;
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

        public Object getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(Object CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getReportFre() {
            return ReportFre;
        }

        public void setReportFre(int ReportFre) {
            this.ReportFre = ReportFre;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }

        public Object getFileIDs() {
            return FileIDs;
        }

        public void setFileIDs(Object FileIDs) {
            this.FileIDs = FileIDs;
        }

        public boolean isIs_Sync() {
            return Is_Sync;
        }

        public void setIs_Sync(boolean Is_Sync) {
            this.Is_Sync = Is_Sync;
        }
    }
}
