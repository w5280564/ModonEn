package com.moying.energyring.Model;

/**
 * Created by waylen on 2018/1/5.
 */

public class ProjectListBean {

        /**
         * ProjectID : 51
         * ProjectName : 自定义1
         * ProjectUnit : 天
         * FilePath : null
         * ReportFre : 0
         * Is_Disabled : false
         * Limit : 1.0
         * FileID : 0
         * CreateTime : null
         * ReportNum : 0.0
         * Gray_FileID : 0
         * Gray_FilePath : null
         * ProjectTypeID : -1
         * UserID : 2066
         */

        private int ProjectID;
        private String ProjectName;
        private String ProjectUnit;
        private Object FilePath;
        private int ReportFre;
        private boolean Is_Disabled;
        private double Limit;
        private int FileID;
        private Object CreateTime;
        private double ReportNum;
        private int Gray_FileID;
        private Object Gray_FilePath;
        private int ProjectTypeID;
        private int UserID;

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

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }

        public int getReportFre() {
            return ReportFre;
        }

        public void setReportFre(int ReportFre) {
            this.ReportFre = ReportFre;
        }

        public boolean isIs_Disabled() {
            return Is_Disabled;
        }

        public void setIs_Disabled(boolean Is_Disabled) {
            this.Is_Disabled = Is_Disabled;
        }

        public double getLimit() {
            return Limit;
        }

        public void setLimit(double Limit) {
            this.Limit = Limit;
        }

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }

        public Object getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(Object CreateTime) {
            this.CreateTime = CreateTime;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }

        public int getGray_FileID() {
            return Gray_FileID;
        }

        public void setGray_FileID(int Gray_FileID) {
            this.Gray_FileID = Gray_FileID;
        }

        public Object getGray_FilePath() {
            return Gray_FilePath;
        }

        public void setGray_FilePath(Object Gray_FilePath) {
            this.Gray_FilePath = Gray_FilePath;
        }

        public int getProjectTypeID() {
            return ProjectTypeID;
        }

        public void setProjectTypeID(int ProjectTypeID) {
            this.ProjectTypeID = ProjectTypeID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

}
