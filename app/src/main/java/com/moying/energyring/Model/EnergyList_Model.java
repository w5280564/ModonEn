package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/10.
 */

public class EnergyList_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"PostID":1,"PostContent":"你好能量圈2.0","PostType":0,"UserID":1,"CreateTime":"2017-04-24 18:14:27","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":null,"Is_Like":false,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小冰","ProfilePicture":"//qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"}]
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
         * PostID : 1
         * PostContent : 你好能量圈2.0
         * PostType : 0
         * UserID : 1
         * CreateTime : 2017-04-24 18:14:27
         * CommentNum : 0
         * Likes : 0
         * Is_Choice : false
         * Is_Del : false
         * FilePath : null
         * Is_Like : false
         * FileIDs : null
         * ToUsers : null
         * NickName : Start丶小冰
         * ProfilePicture : //qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917
         */

        private int PostID;
        private String PostContent;
        private int PostType;
        private int UserID;
        private String CreateTime;
        private int CommentNum;
        private int Likes;
        private boolean Is_Choice;
        private boolean Is_Del;
        private Object FilePath;
        private boolean Is_Like;
        private Object FileIDs;
        private Object ToUsers;
        private String NickName;
        private String ProfilePicture;

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

        public int getPostType() {
            return PostType;
        }

        public void setPostType(int PostType) {
            this.PostType = PostType;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getCommentNum() {
            return CommentNum;
        }

        public void setCommentNum(int CommentNum) {
            this.CommentNum = CommentNum;
        }

        public int getLikes() {
            return Likes;
        }

        public void setLikes(int Likes) {
            this.Likes = Likes;
        }

        public boolean isIs_Choice() {
            return Is_Choice;
        }

        public void setIs_Choice(boolean Is_Choice) {
            this.Is_Choice = Is_Choice;
        }

        public boolean isIs_Del() {
            return Is_Del;
        }

        public void setIs_Del(boolean Is_Del) {
            this.Is_Del = Is_Del;
        }

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }

        public boolean isIs_Like() {
            return Is_Like;
        }

        public void setIs_Like(boolean Is_Like) {
            this.Is_Like = Is_Like;
        }

        public Object getFileIDs() {
            return FileIDs;
        }

        public void setFileIDs(Object FileIDs) {
            this.FileIDs = FileIDs;
        }

        public Object getToUsers() {
            return ToUsers;
        }

        public void setToUsers(Object ToUsers) {
            this.ToUsers = ToUsers;
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
