package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/8/10.
 */

public class Community_List_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"RowsNum":1,"PostID":51981,"PostTitle":null,"PostContent":"你好你好你好你好你好你好","CreateTime":"2018-08-10 16:30:30","PostType":1,"CommentNum":0,"Likes":0,"UserID":2066,"NickName":"摩英 王丰","ProfilePicture":"http://120.26.218.68:1111/Uploads/2018-01-03/1434f3d2-6811-4f00-831a-94efd6d94163.jpg","FilePath":"http://172.16.0.222/Uploads/2018-08-10/a2ccf6af-1530-442a-9843-2bdecd620b6b.jpg","TagID":0,"Brief":"我就是我是不一样的奇异果。🙃","TagName":"","ReadCount":0,"Is_Like":0,"IntegralLevel":3,"Width":620,"Height":312,"ImgNum":1}]
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
         * RowsNum : 1
         * PostID : 51981
         * PostTitle : null
         * PostContent : 你好你好你好你好你好你好
         * CreateTime : 2018-08-10 16:30:30
         * PostType : 1
         * CommentNum : 0
         * Likes : 0
         * UserID : 2066
         * NickName : 摩英 王丰
         * ProfilePicture : http://120.26.218.68:1111/Uploads/2018-01-03/1434f3d2-6811-4f00-831a-94efd6d94163.jpg
         * FilePath : http://172.16.0.222/Uploads/2018-08-10/a2ccf6af-1530-442a-9843-2bdecd620b6b.jpg
         * TagID : 0
         * Brief : 我就是我是不一样的奇异果。🙃
         * TagName :
         * ReadCount : 0
         * Is_Like : 0
         * IntegralLevel : 3
         * Width : 620
         * Height : 312
         * ImgNum : 1
         */

        private int RowsNum;
        private int PostID;
        private Object PostTitle;
        private String PostContent;
        private String CreateTime;
        private int PostType;
        private int CommentNum;
        private int Likes;
        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private String FilePath;
        private int TagID;
        private String Brief;
        private String TagName;
        private int ReadCount;
        private int Is_Like;
        private int IntegralLevel;
        private int Width;
        private int Height;
        private int ImgNum;

        public int getRowsNum() {
            return RowsNum;
        }

        public void setRowsNum(int RowsNum) {
            this.RowsNum = RowsNum;
        }

        public int getPostID() {
            return PostID;
        }

        public void setPostID(int PostID) {
            this.PostID = PostID;
        }

        public Object getPostTitle() {
            return PostTitle;
        }

        public void setPostTitle(Object PostTitle) {
            this.PostTitle = PostTitle;
        }

        public String getPostContent() {
            return PostContent;
        }

        public void setPostContent(String PostContent) {
            this.PostContent = PostContent;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getPostType() {
            return PostType;
        }

        public void setPostType(int PostType) {
            this.PostType = PostType;
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

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public int getTagID() {
            return TagID;
        }

        public void setTagID(int TagID) {
            this.TagID = TagID;
        }

        public String getBrief() {
            return Brief;
        }

        public void setBrief(String Brief) {
            this.Brief = Brief;
        }

        public String getTagName() {
            return TagName;
        }

        public void setTagName(String TagName) {
            this.TagName = TagName;
        }

        public int getReadCount() {
            return ReadCount;
        }

        public void setReadCount(int ReadCount) {
            this.ReadCount = ReadCount;
        }

        public int getIs_Like() {
            return Is_Like;
        }

        public void setIs_Like(int Is_Like) {
            this.Is_Like = Is_Like;
        }

        public int getIntegralLevel() {
            return IntegralLevel;
        }

        public void setIntegralLevel(int IntegralLevel) {
            this.IntegralLevel = IntegralLevel;
        }

        public int getWidth() {
            return Width;
        }

        public void setWidth(int Width) {
            this.Width = Width;
        }

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
            this.Height = Height;
        }

        public int getImgNum() {
            return ImgNum;
        }

        public void setImgNum(int ImgNum) {
            this.ImgNum = ImgNum;
        }
    }
}
