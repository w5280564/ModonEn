package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/25.
 */

public class Committed_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"PostID":8,"PostContent":"【Start丶小春的公众承诺】，从2017-05-25至2017-06-16 ，每天完成健康走项目500步。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-25 17:16:32","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":null,"Is_Like":false,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"},{"PostID":7,"PostContent":"【Start丶小春的公众承诺】，从2017-05-25至2017-05-29 ，每天完成坐姿收腹举腿项目50次。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-25 16:21:38","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":"http://172.16.0.111/Uploads/2017-05-18/b4bb276e-abae-4688-bfca-12c5924fb511.png","Is_Like":true,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"},{"PostID":6,"PostContent":"【Start丶小春的公众承诺】，从2017-05-25至2017-05-29 ，每天完成背单词项目50个。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-25 16:18:34","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":null,"Is_Like":false,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"},{"PostID":5,"PostContent":"【Start丶小春的公众承诺】，从2017-05-25至2017-05-29 ，每天完成KEEP专区项目50分。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-25 15:23:49","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":"http://172.16.0.111/Uploads/2017-05-18/89fc0c32-e175-486e-9a52-07be28415972.png","Is_Like":false,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"},{"PostID":4,"PostContent":"【Start丶小春的公众承诺】，从2017-05-25至2017-05-29 ，每天完成坐姿收腹举腿项目50次。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-25 15:18:51","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":"http://172.16.0.111/Uploads/2017-05-18/b4bb276e-abae-4688-bfca-12c5924fb511.png","Is_Like":false,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"},{"PostID":3,"PostContent":"【Start丶小春的公众承诺】，从2017-05-25至2017-05-29 ，每天完成健康走项目1000步。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-25 15:16:58","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":null,"Is_Like":false,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"},{"PostID":2,"PostContent":"【Start丶小春的公众承诺】，从2017/05/23至2017/05/27 ，每天完成俯卧撑项目50个。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！","PostType":3,"UserID":2,"CreateTime":"2017-05-23 14:52:08","CommentNum":0,"Likes":0,"Is_Choice":false,"Is_Del":false,"FilePath":"http://172.16.0.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png","Is_Like":false,"SourceID":0,"SourceName":null,"FileIDs":null,"ToUsers":null,"NickName":"Start丶小春","ProfilePicture":"http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917"}]
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
         * PostID : 8
         * PostContent : 【Start丶小春的公众承诺】，从2017-05-25至2017-06-16 ，每天完成健康走项目500步。我将坚守承诺，每天全力以赴，完成目标.若食言，将无法获得奖励积分，若退出承诺将扣除100积分作为惩罚，立帖为证！
         * PostType : 3
         * UserID : 2
         * CreateTime : 2017-05-25 17:16:32
         * CommentNum : 0
         * Likes : 0
         * Is_Choice : false
         * Is_Del : false
         * FilePath : null
         * Is_Like : false
         * SourceID : 0
         * SourceName : null
         * FileIDs : null
         * ToUsers : null
         * NickName : Start丶小春
         * ProfilePicture : http://qlogo3.store.qq.com/qzone/214468358/214468358/100?1343713917
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
        private int SourceID;
        private Object SourceName;
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

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public Object getSourceName() {
            return SourceName;
        }

        public void setSourceName(Object SourceName) {
            this.SourceName = SourceName;
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
