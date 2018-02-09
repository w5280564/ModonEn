package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/28.
 */

public class Person_Intrgral_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"IntegralID":3,"UserID":3,"IntegralEvent":"签到打卡","CreateTime":"2017-06-23 22:33:52","SourceID":24,"SourceName":"CheckIn","Integral":10},{"IntegralID":5,"UserID":3,"IntegralEvent":"签到打卡","CreateTime":"2017-06-26 11:25:13","SourceID":26,"SourceName":"CheckIn","Integral":10},{"IntegralID":8,"UserID":3,"IntegralEvent":"签到打卡","CreateTime":"2017-06-27 23:22:53","SourceID":28,"SourceName":"CheckIn","Integral":10},{"IntegralID":10,"UserID":3,"IntegralEvent":"发表成长日志","CreateTime":"2017-06-28 13:49:38","SourceID":38,"SourceName":"Post","Integral":2}]
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
         * IntegralID : 3
         * UserID : 3
         * IntegralEvent : 签到打卡
         * CreateTime : 2017-06-23 22:33:52
         * SourceID : 24
         * SourceName : CheckIn
         * Integral : 10
         */

        private int IntegralID;
        private int UserID;
        private String IntegralEvent;
        private String CreateTime;
        private int SourceID;
        private String SourceName;
        private int Integral;

        public int getIntegralID() {
            return IntegralID;
        }

        public void setIntegralID(int IntegralID) {
            this.IntegralID = IntegralID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getIntegralEvent() {
            return IntegralEvent;
        }

        public void setIntegralEvent(String IntegralEvent) {
            this.IntegralEvent = IntegralEvent;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public String getSourceName() {
            return SourceName;
        }

        public void setSourceName(String SourceName) {
            this.SourceName = SourceName;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }
    }
}
