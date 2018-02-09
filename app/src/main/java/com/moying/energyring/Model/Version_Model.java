package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/9/1.
 */

public class Version_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"VersionID":1,"ios":"2.5","android":"2.5"}
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
         * VersionID : 1
         * ios : 2.5
         * android : 2.5
         */

        private int VersionID;
        private String ios;
        private String android;

        public int getVersionID() {
            return VersionID;
        }

        public void setVersionID(int VersionID) {
            this.VersionID = VersionID;
        }

        public String getIos() {
            return ios;
        }

        public void setIos(String ios) {
            this.ios = ios;
        }

        public String getAndroid() {
            return android;
        }

        public void setAndroid(String android) {
            this.android = android;
        }
    }
}
