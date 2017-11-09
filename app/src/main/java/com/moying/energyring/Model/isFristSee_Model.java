package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/11/6.
 */

public class isFristSee_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"UserID":2066,"Is_FirstEditProfile":true,"Is_FirstEditProfile_Remind":false,"Is_FirstPool":true,"Is_FirstPool_Pic":false,"Is_CheckIn_Remind":false}
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
         * UserID : 2066
         * Is_FirstEditProfile : true
         * Is_FirstEditProfile_Remind : false
         * Is_FirstPool : true
         * Is_FirstPool_Pic : false
         * Is_CheckIn_Remind : false
         */

        private int UserID;
        private boolean Is_FirstEditProfile;
        private boolean Is_FirstEditProfile_Remind;
        private boolean Is_FirstPool;
        private boolean Is_FirstPool_Pic;
        private boolean Is_CheckIn_Remind;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public boolean isIs_FirstEditProfile() {
            return Is_FirstEditProfile;
        }

        public void setIs_FirstEditProfile(boolean Is_FirstEditProfile) {
            this.Is_FirstEditProfile = Is_FirstEditProfile;
        }

        public boolean isIs_FirstEditProfile_Remind() {
            return Is_FirstEditProfile_Remind;
        }

        public void setIs_FirstEditProfile_Remind(boolean Is_FirstEditProfile_Remind) {
            this.Is_FirstEditProfile_Remind = Is_FirstEditProfile_Remind;
        }

        public boolean isIs_FirstPool() {
            return Is_FirstPool;
        }

        public void setIs_FirstPool(boolean Is_FirstPool) {
            this.Is_FirstPool = Is_FirstPool;
        }

        public boolean isIs_FirstPool_Pic() {
            return Is_FirstPool_Pic;
        }

        public void setIs_FirstPool_Pic(boolean Is_FirstPool_Pic) {
            this.Is_FirstPool_Pic = Is_FirstPool_Pic;
        }

        public boolean isIs_CheckIn_Remind() {
            return Is_CheckIn_Remind;
        }

        public void setIs_CheckIn_Remind(boolean Is_CheckIn_Remind) {
            this.Is_CheckIn_Remind = Is_CheckIn_Remind;
        }
    }
}
