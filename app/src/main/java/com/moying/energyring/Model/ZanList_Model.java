package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/16.
 */

public class ZanList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917","Ranking":1,"Likes":2}]
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
         * UserID : 1
         * NickName : Start丶小冰
         * ProfilePicture : http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917
         * Ranking : 1
         * Likes : 2
         */

        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private int Ranking;
        private int Likes;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
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

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getLikes() {
            return Likes;
        }

        public void setLikes(int Likes) {
            this.Likes = Likes;
        }
    }
}
