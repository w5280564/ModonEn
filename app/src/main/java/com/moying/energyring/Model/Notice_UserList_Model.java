package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/20.
 */

public class Notice_UserList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"MessageID":0,"UserID":1,"ToUserID":0,"MessageContent":"你好,管理员","CreateTime":"2017-06-20 10:29:40","Is_Read":false,"NotRead_Num":0,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0"}]
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
         * MessageID : 0
         * UserID : 1
         * ToUserID : 0
         * MessageContent : 你好,管理员
         * CreateTime : 2017-06-20 10:29:40
         * Is_Read : false
         * NotRead_Num : 0
         * NickName : Start丶小冰
         * ProfilePicture : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0
         */

        private int MessageID;
        private int UserID;
        private int ToUserID;
        private String MessageContent;
        private String CreateTime;
        private boolean Is_Read;
        private int NotRead_Num;
        private String NickName;
        private String ProfilePicture;

        public int getMessageID() {
            return MessageID;
        }

        public void setMessageID(int MessageID) {
            this.MessageID = MessageID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getToUserID() {
            return ToUserID;
        }

        public void setToUserID(int ToUserID) {
            this.ToUserID = ToUserID;
        }

        public String getMessageContent() {
            return MessageContent;
        }

        public void setMessageContent(String MessageContent) {
            this.MessageContent = MessageContent;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isIs_Read() {
            return Is_Read;
        }

        public void setIs_Read(boolean Is_Read) {
            this.Is_Read = Is_Read;
        }

        public int getNotRead_Num() {
            return NotRead_Num;
        }

        public void setNotRead_Num(int NotRead_Num) {
            this.NotRead_Num = NotRead_Num;
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
