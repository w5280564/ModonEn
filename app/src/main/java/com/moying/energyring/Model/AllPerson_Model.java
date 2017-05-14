package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/3/17.
 */

public class AllPerson_Model {
    /**
     * IsSuccess : true
     * Msg : 操作成功
     * Data : [{"RowNum":1,"TargetID":17,"StartDate":"2017-03-15 00:00:00","EndDate":"2017-03-20 23:59:00","UserID":2066,"ProjectName":"深蹲","P_UNIT":"个","ReportNum":20,"P_PICURL":"http://192.168.1.111:7777/uploads/i/2017/02/ef52c864-4b07-4a32-a9e9-8c2275b410ec.png","AllDays":6,"FinishDays":1,"RowsCount":1}]
     * Code : 200
     */

    private boolean IsSuccess;
    private String Msg;
    private int Code;
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

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * RowNum : 1
         * TargetID : 17
         * StartDate : 2017-03-15 00:00:00
         * EndDate : 2017-03-20 23:59:00
         * UserID : 2066
         * ProjectName : 深蹲
         * P_UNIT : 个
         * ReportNum : 20.0
         * P_PICURL : http://192.168.1.111:7777/uploads/i/2017/02/ef52c864-4b07-4a32-a9e9-8c2275b410ec.png
         * AllDays : 6
         * FinishDays : 1
         * RowsCount : 1
         */

        private int RowNum;
        private int TargetID;
        private String StartDate;
        private String EndDate;
        private int UserID;
        private String ProjectName;
        private String P_UNIT;
        private int ReportNum;
        private String P_PICURL;
        private int AllDays;
        private int FinishDays;
        private int RowsCount;

        public int getRowNum() {
            return RowNum;
        }

        public void setRowNum(int RowNum) {
            this.RowNum = RowNum;
        }

        public int getTargetID() {
            return TargetID;
        }

        public void setTargetID(int TargetID) {
            this.TargetID = TargetID;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getP_UNIT() {
            return P_UNIT;
        }

        public void setP_UNIT(String P_UNIT) {
            this.P_UNIT = P_UNIT;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(int ReportNum) {
            this.ReportNum = ReportNum;
        }

        public String getP_PICURL() {
            return P_PICURL;
        }

        public void setP_PICURL(String P_PICURL) {
            this.P_PICURL = P_PICURL;
        }

        public int getAllDays() {
            return AllDays;
        }

        public void setAllDays(int AllDays) {
            this.AllDays = AllDays;
        }

        public int getFinishDays() {
            return FinishDays;
        }

        public void setFinishDays(int FinishDays) {
            this.FinishDays = FinishDays;
        }

        public int getRowsCount() {
            return RowsCount;
        }

        public void setRowsCount(int RowsCount) {
            this.RowsCount = RowsCount;
        }
    }
}
