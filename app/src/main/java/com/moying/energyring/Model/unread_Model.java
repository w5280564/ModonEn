package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/6/19.
 */

public class unread_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"NewFriend":0,"Post_Comment":1,"Post_Like":1,"Post_MenTion":0,"Notice":1}
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
         * NewFriend : 0
         * Post_Comment : 1
         * Post_Like : 1
         * Post_MenTion : 0
         * Notice : 1
         */

        private int NewFriend;
        private int Post_Comment;
        private int Post_Like;
        private int Post_MenTion;
        private int Notice;

        public int getNewFriend() {
            return NewFriend;
        }

        public void setNewFriend(int NewFriend) {
            this.NewFriend = NewFriend;
        }

        public int getPost_Comment() {
            return Post_Comment;
        }

        public void setPost_Comment(int Post_Comment) {
            this.Post_Comment = Post_Comment;
        }

        public int getPost_Like() {
            return Post_Like;
        }

        public void setPost_Like(int Post_Like) {
            this.Post_Like = Post_Like;
        }

        public int getPost_MenTion() {
            return Post_MenTion;
        }

        public void setPost_MenTion(int Post_MenTion) {
            this.Post_MenTion = Post_MenTion;
        }

        public int getNotice() {
            return Notice;
        }

        public void setNotice(int Notice) {
            this.Notice = Notice;
        }
    }
}
