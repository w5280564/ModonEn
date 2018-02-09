package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by john on 2016/8/3.
 */
public class Friend_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917","Is_Friend":false},{"UserID":2,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917","Is_Friend":false}]
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
         * Is_Friend : false
         */

        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private boolean Is_Friend;

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

        public boolean isIs_Friend() {
            return Is_Friend;
        }

        public void setIs_Friend(boolean Is_Friend) {
            this.Is_Friend = Is_Friend;
        }
    }
}
