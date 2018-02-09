package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/15.
 */

public class person_daypk_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ReportID":50097,"ProjectID":1,"ProjectName":"俯卧撑","ProjectUnit":"个","Ranking":1,"ReportNum":50,"Report_Days":183,"FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/7a539a33-309b-491e-8ac4-a8630b9bf477.png"}]
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
         * ReportID : 50097
         * ProjectID : 1
         * ProjectName : 俯卧撑
         * ProjectUnit : 个
         * Ranking : 1
         * ReportNum : 50.0
         * Report_Days : 183
         * FilePath : http://120.26.218.68:8040/uploads/i/2017/03/7a539a33-309b-491e-8ac4-a8630b9bf477.png
         */

        private int ReportID;
        private int ProjectID;
        private String ProjectName;
        private String ProjectUnit;
        private int Ranking;
        private double ReportNum;
        private int Report_Days;
        private String FilePath;

        public int getReportID() {
            return ReportID;
        }

        public void setReportID(int ReportID) {
            this.ReportID = ReportID;
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }

        public int getReport_Days() {
            return Report_Days;
        }

        public void setReport_Days(int Report_Days) {
            this.Report_Days = Report_Days;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }
    }
}
