package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/12/29.
 */

public class ReportHistory_Model {
    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"RowsNum":1,"ReportRankingID":80052,"UserID":1384,"Ranking":1,"CreateDate":"2018-01-02 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":16,"ReportNum_All":16,"ReportNum_Month":1,"ReportFre":14,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":2,"ReportRankingID":79922,"UserID":1384,"Ranking":1,"CreateDate":"2017-12-31 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":15,"ReportNum_All":15,"ReportNum_Month":5,"ReportFre":14,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":3,"ReportRankingID":79402,"UserID":1384,"Ranking":1,"CreateDate":"2017-12-26 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":14,"ReportNum_All":14,"ReportNum_Month":4,"ReportFre":14,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":4,"ReportRankingID":78513,"UserID":1384,"Ranking":6,"CreateDate":"2017-12-15 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":13,"ReportNum_All":13,"ReportNum_Month":3,"ReportFre":13,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":5,"ReportRankingID":77906,"UserID":1384,"Ranking":1,"CreateDate":"2017-12-07 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":12,"ReportNum_All":12,"ReportNum_Month":2,"ReportFre":12,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":6,"ReportRankingID":77747,"UserID":1384,"Ranking":1,"CreateDate":"2017-12-06 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":11,"ReportNum_All":11,"ReportNum_Month":1,"ReportFre":11,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":7,"ReportRankingID":73410,"UserID":1384,"Ranking":2,"CreateDate":"2017-10-25 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":10,"ReportNum_All":10,"ReportNum_Month":1,"ReportFre":10,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":8,"ReportRankingID":68323,"UserID":1384,"Ranking":14,"CreateDate":"2017-09-08 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":9,"ReportNum_All":9,"ReportNum_Month":1,"ReportFre":9,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":9,"ReportRankingID":63618,"UserID":1384,"Ranking":23,"CreateDate":"2017-08-11 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":8,"ReportNum_All":8,"ReportNum_Month":2,"ReportFre":8,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1},{"RowsNum":10,"ReportRankingID":61485,"UserID":1384,"Ranking":14,"CreateDate":"2017-08-03 00:00:00","ReportNum":1,"ProjectID":50,"ReportDays":7,"ReportNum_All":7,"ReportNum_Month":1,"ReportFre":7,"ProjectName":"早起","ProjectUnit":"天","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png","Limit":1}]
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
         * RowsNum : 1
         * ReportRankingID : 80052
         * UserID : 1384
         * Ranking : 1
         * CreateDate : 2018-01-02 00:00:00
         * ReportNum : 1.0
         * ProjectID : 50
         * ReportDays : 16
         * ReportNum_All : 16.0
         * ReportNum_Month : 1.0
         * ReportFre : 14
         * ProjectName : 早起
         * ProjectUnit : 天
         * FilePath : http://120.26.218.68:1111/Uploads/2017-12-01/f6406880-6762-496b-b899-6c93578bd71a.png
         * Limit : 1.0
         */

        private int RowsNum;
        private int ReportRankingID;
        private int UserID;
        private int Ranking;
        private String CreateDate;
        private double ReportNum;
        private int ProjectID;
        private int ReportDays;
        private double ReportNum_All;
        private double ReportNum_Month;
        private int ReportFre;
        private String ProjectName;
        private String ProjectUnit;
        private String FilePath;
        private double Limit;

        public int getRowsNum() {
            return RowsNum;
        }

        public void setRowsNum(int RowsNum) {
            this.RowsNum = RowsNum;
        }

        public int getReportRankingID() {
            return ReportRankingID;
        }

        public void setReportRankingID(int ReportRankingID) {
            this.ReportRankingID = ReportRankingID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public int getReportDays() {
            return ReportDays;
        }

        public void setReportDays(int ReportDays) {
            this.ReportDays = ReportDays;
        }

        public double getReportNum_All() {
            return ReportNum_All;
        }

        public void setReportNum_All(double ReportNum_All) {
            this.ReportNum_All = ReportNum_All;
        }

        public double getReportNum_Month() {
            return ReportNum_Month;
        }

        public void setReportNum_Month(double ReportNum_Month) {
            this.ReportNum_Month = ReportNum_Month;
        }

        public int getReportFre() {
            return ReportFre;
        }

        public void setReportFre(int ReportFre) {
            this.ReportFre = ReportFre;
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

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public double getLimit() {
            return Limit;
        }

        public void setLimit(double Limit) {
            this.Limit = Limit;
        }
    }
}
