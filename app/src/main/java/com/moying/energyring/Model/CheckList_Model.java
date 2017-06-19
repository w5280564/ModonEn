package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/17.
 */

public class CheckList_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"CheckInID":0,"UserID":1,"CheckInTime":null,"ContinueDays":0,"Ranking":1,"EarlyDays":2,"NickName":"Start丶小冰","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"}]
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
         * CheckInID : 0
         * UserID : 1
         * CheckInTime : null
         * ContinueDays : 0
         * Ranking : 1
         * EarlyDays : 2
         * NickName : Start丶小冰
         * ProfilePicture : http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917
         */

        private int CheckInID;
        private int UserID;
        private Object CheckInTime;
        private int ContinueDays;
        private int Ranking;
        private int EarlyDays;
        private String NickName;
        private String ProfilePicture;

        public int getCheckInID() {
            return CheckInID;
        }

        public void setCheckInID(int CheckInID) {
            this.CheckInID = CheckInID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public Object getCheckInTime() {
            return CheckInTime;
        }

        public void setCheckInTime(Object CheckInTime) {
            this.CheckInTime = CheckInTime;
        }

        public int getContinueDays() {
            return ContinueDays;
        }

        public void setContinueDays(int ContinueDays) {
            this.ContinueDays = ContinueDays;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getEarlyDays() {
            return EarlyDays;
        }

        public void setEarlyDays(int EarlyDays) {
            this.EarlyDays = EarlyDays;
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
    }
}
