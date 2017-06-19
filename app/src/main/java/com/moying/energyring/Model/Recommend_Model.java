package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/5.
 */

public class Recommend_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Brief":null,"Is_Friend":true,"Is_Attention":true,"Is_Attention_Me":true}]
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
         * ProfilePicture : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0
         * Brief : null
         * Is_Friend : true
         * Is_Attention : true
         * Is_Attention_Me : true
         */

        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private Object Brief;
        private boolean Is_Friend;
        private boolean Is_Attention;
        private boolean Is_Attention_Me;

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

        public Object getBrief() {
            return Brief;
        }

        public void setBrief(Object Brief) {
            this.Brief = Brief;
        }

        public boolean isIs_Friend() {
            return Is_Friend;
        }

        public void setIs_Friend(boolean Is_Friend) {
            this.Is_Friend = Is_Friend;
        }

        public boolean isIs_Attention() {
            return Is_Attention;
        }

        public void setIs_Attention(boolean Is_Attention) {
            this.Is_Attention = Is_Attention;
        }

        public boolean isIs_Attention_Me() {
            return Is_Attention_Me;
        }

        public void setIs_Attention_Me(boolean Is_Attention_Me) {
            this.Is_Attention_Me = Is_Attention_Me;
        }
    }
}
