package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/5/31.
 */

public class Person_TaskList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"TaskID":1,"TaskName":"每日签到","Summary":"啦啦啦啦啦绿绿绿","Integral":30,"Condition":"3","BtnText":"去签到,已签到","FinishID":0},{"TaskID":2,"TaskName":"每日汇总","Summary":"命名命名木木木","Integral":30,"Condition":"4","BtnText":"去完成,已完成","FinishID":0},{"TaskID":4,"TaskName":"完成训练","Summary":"那你呢","Integral":20,"Condition":"6","BtnText":"未完成,已完成","FinishID":0},{"TaskID":5,"TaskName":"每日分享","Summary":"啦啦啦啦啦","Integral":20,"Condition":"5","BtnText":"去分享,已分享","FinishID":0},{"TaskID":6,"TaskName":"邀请好友","Summary":"得到的","Integral":20,"Condition":"2","BtnText":"去邀请,已邀请","FinishID":0},{"TaskID":7,"TaskName":"送出3个赞","Summary":"买买买","Integral":10,"Condition":"7","BtnText":"去点赞,已完成","FinishID":0}]
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
         * TaskID : 1
         * TaskName : 每日签到
         * Summary : 啦啦啦啦啦绿绿绿
         * Integral : 30
         * Condition : 3
         * BtnText : 去签到,已签到
         * FinishID : 0
         */

        private int TaskID;
        private String TaskName;
        private String Summary;
        private int Integral;
        private String Condition;
        private String BtnText;
        private int FinishID;

        public int getTaskID() {
            return TaskID;
        }

        public void setTaskID(int TaskID) {
            this.TaskID = TaskID;
        }

        public String getTaskName() {
            return TaskName;
        }

        public void setTaskName(String TaskName) {
            this.TaskName = TaskName;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public String getCondition() {
            return Condition;
        }

        public void setCondition(String Condition) {
            this.Condition = Condition;
        }

        public String getBtnText() {
            return BtnText;
        }

        public void setBtnText(String BtnText) {
            this.BtnText = BtnText;
        }

        public int getFinishID() {
            return FinishID;
        }

        public void setFinishID(int FinishID) {
            this.FinishID = FinishID;
        }
    }
}
