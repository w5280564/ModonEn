package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/8/18.
 */

public class PkHistoryLineList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectID":7,"CreateTime":"2016-06-25 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":35},{"ProjectID":7,"CreateTime":"2016-06-23 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":35},{"ProjectID":7,"CreateTime":"2016-06-22 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":35},{"ProjectID":7,"CreateTime":"2016-06-21 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":35},{"ProjectID":7,"CreateTime":"2016-06-20 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":35},{"ProjectID":7,"CreateTime":"2016-06-19 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":35},{"ProjectID":7,"CreateTime":"2016-06-11 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":60},{"ProjectID":7,"CreateTime":"2016-06-05 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":44},{"ProjectID":7,"CreateTime":"2016-06-04 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":44},{"ProjectID":7,"CreateTime":"2016-06-03 00:00:00","ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":44}]
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
         * ProjectID : 7
         * CreateTime : 2016-06-25 00:00:00
         * ProjectUnit : 个
         * ProjectName : 背单词
         * FilePath : http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png
         * ReportNum : 35
         */

        private int ProjectID;
        private String CreateTime;
        private String ProjectUnit;
        private String ProjectName;
        private String FilePath;
        private int ReportNum;

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public int getReportNum() {
            return ReportNum;
        }

        public void setReportNum(int ReportNum) {
            this.ReportNum = ReportNum;
        }
    }
}
