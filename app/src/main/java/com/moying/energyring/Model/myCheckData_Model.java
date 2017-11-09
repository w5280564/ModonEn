package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/10/23.
 */

public class myCheckData_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"CheckInDays":44,"ContinueDays":1,"EarlyRanking":0,"EarlyDays":0}
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
         * CheckInDays : 44
         * ContinueDays : 1
         * EarlyRanking : 0
         * EarlyDays : 0
         */

        private int CheckInDays;
        private int ContinueDays;
        private int EarlyRanking;
        private int EarlyDays;

        public int getCheckInDays() {
            return CheckInDays;
        }

        public void setCheckInDays(int CheckInDays) {
            this.CheckInDays = CheckInDays;
        }

        public int getContinueDays() {
            return ContinueDays;
        }

        public void setContinueDays(int ContinueDays) {
            this.ContinueDays = ContinueDays;
        }

        public int getEarlyRanking() {
            return EarlyRanking;
        }

        public void setEarlyRanking(int EarlyRanking) {
            this.EarlyRanking = EarlyRanking;
        }

        public int getEarlyDays() {
            return EarlyDays;
        }

        public void setEarlyDays(int EarlyDays) {
            this.EarlyDays = EarlyDays;
        }
    }
}
