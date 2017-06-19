package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/18.
 */

public class DayPkProject_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectID":22,"ProjectName":"KEEP专区","ProjectUnit":null,"FilePath":"http://172.16.0.111/Uploads/2017-05-18/89fc0c32-e175-486e-9a52-07be28415972.png","ReportFre":3,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-06-06 00:00:00"},{"ProjectID":21,"ProjectName":"坐姿收腹举腿","ProjectUnit":null,"FilePath":"http://172.16.0.111/Uploads/2017-05-18/b4bb276e-abae-4688-bfca-12c5924fb511.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-06-06 00:00:00"},{"ProjectID":12,"ProjectName":"俯卧撑","ProjectUnit":null,"FilePath":"http://172.16.0.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png","ReportFre":5,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-06-14 00:00:00"},{"ProjectID":11,"ProjectName":"深蹲","ProjectUnit":null,"FilePath":"http://172.16.0.111/Uploads/2017-05-23/da5ac740-1ebb-4959-9e87-e31248cd0046.png","ReportFre":4,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-05-28 00:00:00"},{"ProjectID":17,"ProjectName":"健康走","ProjectUnit":null,"FilePath":"http://172.16.0.111/Uploads/2017-06-15/449b2440-1c5e-4d29-8e2f-33ba9ba9d05e.jpg","ReportFre":2,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-05-31 00:00:00"}]
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
         * ProjectID : 22
         * ProjectName : KEEP专区
         * ProjectUnit : null
         * FilePath : http://172.16.0.111/Uploads/2017-05-18/89fc0c32-e175-486e-9a52-07be28415972.png
         * ReportFre : 3
         * Is_Disabled : false
         * Limit : 0.0
         * FileID : 0
         * CreateTime : 2017-06-06 00:00:00
         */

        private int ProjectID;
        private String ProjectName;
        private Object ProjectUnit;
        private String FilePath;
        private int ReportFre;
        private boolean Is_Disabled;
        private double Limit;
        private int FileID;
        private String CreateTime;

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

        public Object getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(Object ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
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

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
