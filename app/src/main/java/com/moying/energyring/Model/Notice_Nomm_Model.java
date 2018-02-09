package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/19.
 */

public class Notice_Nomm_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","CreateTime":"2017-06-19 12:11:29","CommentContent":null,"PostID":1,"PostContent":"你好能量圈2.0","FilePath":null}]
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
         * CreateTime : 2017-06-19 12:11:29
         * CommentContent : null
         * PostID : 1
         * PostContent : 你好能量圈2.0
         * FilePath : null
         */

        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private String CreateTime;
        private Object CommentContent;
        private int PostID;
        private String PostContent;
        private Object FilePath;

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

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public Object getCommentContent() {
            return CommentContent;
        }

        public void setCommentContent(Object CommentContent) {
            this.CommentContent = CommentContent;
        }

        public int getPostID() {
            return PostID;
        }

        public void setPostID(int PostID) {
            this.PostID = PostID;
        }

        public String getPostContent() {
            return PostContent;
        }

        public void setPostContent(String PostContent) {
            this.PostContent = PostContent;
        }

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }
    }
}
