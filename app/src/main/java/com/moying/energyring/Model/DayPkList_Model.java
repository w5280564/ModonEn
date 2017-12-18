package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/27.
 */

public class DayPkList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectUnit":"个","ProjectName":null,"NickName":"摩英 王丰","ProfilePicture":"http://120.26.218.68:8038/Attachment/6652_66522066.jpg","Likes":1,"Is_Like":false,"PKCoverImg":"http://120.26.218.68:1111/Uploads/2017-10-10/84b252c9-6f15-49d4-8241-19d6edac544e.jpg","Ranking":1,"Limit":300,"Report_Days":0,"FilePath_List":null,"ReportID":43,"UserID":2066,"ProjectID":1,"CreateTime":null,"ReportFre":0,"ReportNum":10,"FileIDs":null,"Is_Sync":false}]
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
         * ProjectUnit : 个
         * ProjectName : null
         * NickName : 摩英 王丰
         * ProfilePicture : http://120.26.218.68:8038/Attachment/6652_66522066.jpg
         * Likes : 1
         * Is_Like : false
         * PKCoverImg : http://120.26.218.68:1111/Uploads/2017-10-10/84b252c9-6f15-49d4-8241-19d6edac544e.jpg
         * Ranking : 1
         * Limit : 300.0
         * Report_Days : 0
         * FilePath_List : null
         * ReportID : 43
         * UserID : 2066
         * ProjectID : 1
         * CreateTime : null
         * ReportFre : 0
         * ReportNum : 10.0
         * FileIDs : null
         * Is_Sync : false
         */

        private String ProjectUnit;
        private Object ProjectName;
        private String NickName;
        private String ProfilePicture;
        private int Likes;
        private boolean Is_Like;
        private String PKCoverImg;
        private int Ranking;
        private double Limit;
        private int Report_Days;
        private Object FilePath_List;
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

        public Object getProjectName() {
            return ProjectName;
        }

        public void setProjectName(Object ProjectName) {
            this.ProjectName = ProjectName;
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

        public String getPKCoverImg() {
            return PKCoverImg;
        }

        public void setPKCoverImg(String PKCoverImg) {
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

        public int getReport_Days() {
            return Report_Days;
        }

        public void setReport_Days(int Report_Days) {
            this.Report_Days = Report_Days;
        }

        public Object getFilePath_List() {
            return FilePath_List;
        }

        public void setFilePath_List(Object FilePath_List) {
            this.FilePath_List = FilePath_List;
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
