package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/8/8.
 */

public class Communit_Rank_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"My_Ranking":{"ComID":2,"UserID":1,"ProjectID":35,"CreateDate":"2018-08-06 00:00:00","Is_Del":false,"Privacy":1,"ContinueDays":0,"MaxContinueDays":0,"LastCheckInDate":"2018-08-07 00:00:00","Likes":0,"NickName":"好文推荐","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-07-07/a195fc72-7aef-4c49-830d-b96778c16265.jpg","Report_Days":25,"Is_Like":0,"Report_Num":45837,"Limit":30000},"CommRanking":[{"Ranking":1,"ComID":1,"UserID":1421,"ProjectID":35,"CreateDate":"2018-08-06 00:00:00","Is_Del":false,"Privacy":1,"ContinueDays":1,"MaxContinueDays":1,"LastCheckInDate":"2018-08-07 00:00:00","Likes":2,"NickName":"摩英iOS开发","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-11-21/8866e625-382a-49ed-9033-676acdb6fa8b.jpg","Report_Days":75,"Is_Like":0,"Report_Num":151297,"Limit":30000},{"Ranking":2,"ComID":2,"UserID":1,"ProjectID":35,"CreateDate":"2018-08-06 00:00:00","Is_Del":false,"Privacy":1,"ContinueDays":0,"MaxContinueDays":0,"LastCheckInDate":"2018-08-07 00:00:00","Likes":0,"NickName":"好文推荐","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-07-07/a195fc72-7aef-4c49-830d-b96778c16265.jpg","Report_Days":25,"Is_Like":0,"Report_Num":45837,"Limit":30000}]}
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
         * My_Ranking : {"ComID":2,"UserID":1,"ProjectID":35,"CreateDate":"2018-08-06 00:00:00","Is_Del":false,"Privacy":1,"ContinueDays":0,"MaxContinueDays":0,"LastCheckInDate":"2018-08-07 00:00:00","Likes":0,"NickName":"好文推荐","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-07-07/a195fc72-7aef-4c49-830d-b96778c16265.jpg","Report_Days":25,"Is_Like":0,"Report_Num":45837,"Limit":30000}
         * CommRanking : [{"Ranking":1,"ComID":1,"UserID":1421,"ProjectID":35,"CreateDate":"2018-08-06 00:00:00","Is_Del":false,"Privacy":1,"ContinueDays":1,"MaxContinueDays":1,"LastCheckInDate":"2018-08-07 00:00:00","Likes":2,"NickName":"摩英iOS开发","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-11-21/8866e625-382a-49ed-9033-676acdb6fa8b.jpg","Report_Days":75,"Is_Like":0,"Report_Num":151297,"Limit":30000},{"Ranking":2,"ComID":2,"UserID":1,"ProjectID":35,"CreateDate":"2018-08-06 00:00:00","Is_Del":false,"Privacy":1,"ContinueDays":0,"MaxContinueDays":0,"LastCheckInDate":"2018-08-07 00:00:00","Likes":0,"NickName":"好文推荐","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-07-07/a195fc72-7aef-4c49-830d-b96778c16265.jpg","Report_Days":25,"Is_Like":0,"Report_Num":45837,"Limit":30000}]
         */

        private MyRankingBean My_Ranking;
        private List<CommRankingBean> CommRanking;

        public MyRankingBean getMy_Ranking() {
            return My_Ranking;
        }

        public void setMy_Ranking(MyRankingBean My_Ranking) {
            this.My_Ranking = My_Ranking;
        }

        public List<CommRankingBean> getCommRanking() {
            return CommRanking;
        }

        public void setCommRanking(List<CommRankingBean> CommRanking) {
            this.CommRanking = CommRanking;
        }

        public static class MyRankingBean {
            /**
             * ComID : 2
             * UserID : 1
             * ProjectID : 35
             * CreateDate : 2018-08-06 00:00:00
             * Is_Del : false
             * Privacy : 1
             * ContinueDays : 0
             * MaxContinueDays : 0
             * LastCheckInDate : 2018-08-07 00:00:00
             * Likes : 0
             * NickName : 好文推荐
             * ProfilePicture : http://120.26.218.68:1111/Uploads/2017-07-07/a195fc72-7aef-4c49-830d-b96778c16265.jpg
             * Report_Days : 25
             * Is_Like : 0
             * Report_Num : 45837
             * Limit : 30000
             */

            private int ComID;
            private int UserID;
            private int ProjectID;
            private String CreateDate;
            private boolean Is_Del;
            private int Privacy;
            private int ContinueDays;
            private int MaxContinueDays;
            private String LastCheckInDate;
            private int Likes;
            private String NickName;
            private String ProfilePicture;
            private int Report_Days;
            private int Is_Like;
            private int Report_Num;
            private int Limit;

            public int getComID() {
                return ComID;
            }

            public void setComID(int ComID) {
                this.ComID = ComID;
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

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public boolean isIs_Del() {
                return Is_Del;
            }

            public void setIs_Del(boolean Is_Del) {
                this.Is_Del = Is_Del;
            }

            public int getPrivacy() {
                return Privacy;
            }

            public void setPrivacy(int Privacy) {
                this.Privacy = Privacy;
            }

            public int getContinueDays() {
                return ContinueDays;
            }

            public void setContinueDays(int ContinueDays) {
                this.ContinueDays = ContinueDays;
            }

            public int getMaxContinueDays() {
                return MaxContinueDays;
            }

            public void setMaxContinueDays(int MaxContinueDays) {
                this.MaxContinueDays = MaxContinueDays;
            }

            public String getLastCheckInDate() {
                return LastCheckInDate;
            }

            public void setLastCheckInDate(String LastCheckInDate) {
                this.LastCheckInDate = LastCheckInDate;
            }

            public int getLikes() {
                return Likes;
            }

            public void setLikes(int Likes) {
                this.Likes = Likes;
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

            public int getReport_Days() {
                return Report_Days;
            }

            public void setReport_Days(int Report_Days) {
                this.Report_Days = Report_Days;
            }

            public int getIs_Like() {
                return Is_Like;
            }

            public void setIs_Like(int Is_Like) {
                this.Is_Like = Is_Like;
            }

            public int getReport_Num() {
                return Report_Num;
            }

            public void setReport_Num(int Report_Num) {
                this.Report_Num = Report_Num;
            }

            public int getLimit() {
                return Limit;
            }

            public void setLimit(int Limit) {
                this.Limit = Limit;
            }
        }

        public static class CommRankingBean {
            /**
             * Ranking : 1
             * ComID : 1
             * UserID : 1421
             * ProjectID : 35
             * CreateDate : 2018-08-06 00:00:00
             * Is_Del : false
             * Privacy : 1
             * ContinueDays : 1
             * MaxContinueDays : 1
             * LastCheckInDate : 2018-08-07 00:00:00
             * Likes : 2
             * NickName : 摩英iOS开发
             * ProfilePicture : http://120.26.218.68:1111/Uploads/2017-11-21/8866e625-382a-49ed-9033-676acdb6fa8b.jpg
             * Report_Days : 75
             * Is_Like : 0
             * Report_Num : 151297
             * Limit : 30000
             */

            private int Ranking;
            private int ComID;
            private int UserID;
            private int ProjectID;
            private String CreateDate;
            private boolean Is_Del;
            private int Privacy;
            private int ContinueDays;
            private int MaxContinueDays;
            private String LastCheckInDate;
            private int Likes;
            private String NickName;
            private String ProfilePicture;
            private int Report_Days;
            private int Is_Like;
            private int Report_Num;
            private int Limit;

            public int getRanking() {
                return Ranking;
            }

            public void setRanking(int Ranking) {
                this.Ranking = Ranking;
            }

            public int getComID() {
                return ComID;
            }

            public void setComID(int ComID) {
                this.ComID = ComID;
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

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public boolean isIs_Del() {
                return Is_Del;
            }

            public void setIs_Del(boolean Is_Del) {
                this.Is_Del = Is_Del;
            }

            public int getPrivacy() {
                return Privacy;
            }

            public void setPrivacy(int Privacy) {
                this.Privacy = Privacy;
            }

            public int getContinueDays() {
                return ContinueDays;
            }

            public void setContinueDays(int ContinueDays) {
                this.ContinueDays = ContinueDays;
            }

            public int getMaxContinueDays() {
                return MaxContinueDays;
            }

            public void setMaxContinueDays(int MaxContinueDays) {
                this.MaxContinueDays = MaxContinueDays;
            }

            public String getLastCheckInDate() {
                return LastCheckInDate;
            }

            public void setLastCheckInDate(String LastCheckInDate) {
                this.LastCheckInDate = LastCheckInDate;
            }

            public int getLikes() {
                return Likes;
            }

            public void setLikes(int Likes) {
                this.Likes = Likes;
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

            public int getReport_Days() {
                return Report_Days;
            }

            public void setReport_Days(int Report_Days) {
                this.Report_Days = Report_Days;
            }

            public int getIs_Like() {
                return Is_Like;
            }

            public void setIs_Like(int Is_Like) {
                this.Is_Like = Is_Like;
            }

            public int getReport_Num() {
                return Report_Num;
            }

            public void setReport_Num(int Report_Num) {
                this.Report_Num = Report_Num;
            }

            public int getLimit() {
                return Limit;
            }

            public void setLimit(int Limit) {
                this.Limit = Limit;
            }
        }
    }
}
