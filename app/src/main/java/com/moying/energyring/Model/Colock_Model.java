package com.moying.energyring.Model;

/**
 * Created by waylen on 2018/1/25.
 */

public class Colock_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"ClockID":10,"ProjectID":3,"UserID":2066,"Is_Enabled":true,"RemindTime":"20:36","RemindDate":"1,3,4,0,"}
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
         * ClockID : 10
         * ProjectID : 3
         * UserID : 2066
         * Is_Enabled : true
         * RemindTime : 20:36
         * RemindDate : 1,3,4,0,
         */

        private int ClockID;
        private int ProjectID;
        private int UserID;
        private boolean Is_Enabled;
        private String RemindTime;
        private String RemindDate;

        public int getClockID() {
            return ClockID;
        }

        public void setClockID(int ClockID) {
            this.ClockID = ClockID;
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public boolean isIs_Enabled() {
            return Is_Enabled;
        }

        public void setIs_Enabled(boolean Is_Enabled) {
            this.Is_Enabled = Is_Enabled;
        }

        public String getRemindTime() {
            return RemindTime;
        }

        public void setRemindTime(String RemindTime) {
            this.RemindTime = RemindTime;
        }

        public String getRemindDate() {
            return RemindDate;
        }

        public void setRemindDate(String RemindDate) {
            this.RemindDate = RemindDate;
        }
    }
}
