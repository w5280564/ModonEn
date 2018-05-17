package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/11/6.
 */

public class isFristSee_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"UserID":2066,"Is_FirstEditProfile_Remind":true,"Is_FirstPool":true,"Is_FirstPool_Pic":true,"Is_FirstEditProfile":false,"Is_CheckIn_Remind":true,"Is_FirstPK_Pic":true,"Is_Suggest":false,"Is_PK_Guide":true,"Is_First_Post_Pic":false}
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
         * Is_FirstEditProfile_Remind : true
         * Is_FirstPool : true
         * Is_FirstPool_Pic : true
         * Is_FirstEditProfile : false
         * Is_CheckIn_Remind : true
         * Is_FirstPK_Pic : true
         * Is_Suggest : false
         * Is_PK_Guide : true
         * Is_First_Post_Pic : false
         */

        private int UserID;
        private boolean Is_FirstEditProfile_Remind;
        private boolean Is_FirstPool;
        private boolean Is_FirstPool_Pic;
        private boolean Is_FirstEditProfile;
        private boolean Is_CheckIn_Remind;
        private boolean Is_FirstPK_Pic;
        private boolean Is_Suggest;
        private boolean Is_PK_Guide;
        private boolean Is_First_Post_Pic;
        private boolean Is_First_Train;

        public boolean isIs_First_Train() {
            return Is_First_Train;
        }

        public void setIs_First_Train(boolean is_First_Train) {
            Is_First_Train = is_First_Train;
        }


        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
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

        public boolean isIs_FirstEditProfile() {
            return Is_FirstEditProfile;
        }

        public void setIs_FirstEditProfile(boolean Is_FirstEditProfile) {
            this.Is_FirstEditProfile = Is_FirstEditProfile;
        }

        public boolean isIs_CheckIn_Remind() {
            return Is_CheckIn_Remind;
        }

        public void setIs_CheckIn_Remind(boolean Is_CheckIn_Remind) {
            this.Is_CheckIn_Remind = Is_CheckIn_Remind;
        }

        public boolean isIs_FirstPK_Pic() {
            return Is_FirstPK_Pic;
        }

        public void setIs_FirstPK_Pic(boolean Is_FirstPK_Pic) {
            this.Is_FirstPK_Pic = Is_FirstPK_Pic;
        }

        public boolean isIs_Suggest() {
            return Is_Suggest;
        }

        public void setIs_Suggest(boolean Is_Suggest) {
            this.Is_Suggest = Is_Suggest;
        }

        public boolean isIs_PK_Guide() {
            return Is_PK_Guide;
        }

        public void setIs_PK_Guide(boolean Is_PK_Guide) {
            this.Is_PK_Guide = Is_PK_Guide;
        }

        public boolean isIs_First_Post_Pic() {
            return Is_First_Post_Pic;
        }

        public void setIs_First_Post_Pic(boolean Is_First_Post_Pic) {
            this.Is_First_Post_Pic = Is_First_Post_Pic;
        }
    }
}
