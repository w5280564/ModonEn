package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/10/27.
 */

public class likes_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"LikesNum":61,"LikesRanking":255}
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
         * LikesNum : 61
         * LikesRanking : 255
         */

        private int LikesNum;
        private int LikesRanking;

        public int getLikesNum() {
            return LikesNum;
        }

        public void setLikesNum(int LikesNum) {
            this.LikesNum = LikesNum;
        }

        public int getLikesRanking() {
            return LikesRanking;
        }

        public void setLikesRanking(int LikesRanking) {
            this.LikesRanking = LikesRanking;
        }
    }
}
