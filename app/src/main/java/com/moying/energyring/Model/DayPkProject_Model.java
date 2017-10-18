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
     * Data : [{"ProjectID":1,"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/7a539a33-309b-491e-8ac4-a8630b9bf477.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":100},{"ProjectID":31,"ProjectName":"卷腹","ProjectUnit":"个","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/887a6aef-81cc-4f7c-b569-2b1e1b5ff7f7.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":100},{"ProjectID":45,"ProjectName":"站桩","ProjectUnit":"分钟","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/96d017cc-c941-4f6c-bb03-f7eb7c40e3b2.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":20},{"ProjectID":41,"ProjectName":"游泳","ProjectUnit":"分钟","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/ab8dbd7a-960a-4ccc-90e9-4ca413496676.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":20},{"ProjectID":34,"ProjectName":"KEEP专区","ProjectUnit":"分钟","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/ad8eac54-81de-4e2b-bd12-4987060bda26.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":26},{"ProjectID":38,"ProjectName":"器械锻炼","ProjectUnit":"分钟","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/afcdc46d-9f67-4d98-840d-53b4079e31a3.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":50},{"ProjectID":39,"ProjectName":"今日背诵","ProjectUnit":"遍","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/b6cd0593-fb53-4cc6-8c40-ad6ed6fd375e.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":10},{"ProjectID":37,"ProjectName":"坐姿臂屈伸","ProjectUnit":"个","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/c42a2b76-0f5e-43bc-a7e1-c9805ace2782.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":20},{"ProjectID":35,"ProjectName":"健康走","ProjectUnit":"步","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/e4a8bde9-1988-4abd-80d7-73f48a0d6715.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":5000},{"ProjectID":36,"ProjectName":"瑜伽","ProjectUnit":"小时","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/f9edb63f-fff3-4187-9d0a-ac80fe9119e0.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":2},{"ProjectID":32,"ProjectName":"平板撑","ProjectUnit":"秒","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/fa39ad66-cf1f-42fa-a48d-240ecd95e966.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":20},{"ProjectID":40,"ProjectName":"仰卧起坐","ProjectUnit":"个","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/fd87c142-5708-4bbc-9853-888a22bf15b9.png","ReportFre":1,"Is_Disabled":false,"Limit":0,"FileID":0,"CreateTime":"2017-08-24 00:00:00","ReportNum":20}]
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
         * ProjectID : 1
         * ProjectName : 俯卧撑
         * ProjectUnit : 个
         * FilePath : http://120.26.218.68:8040/uploads/i/2017/03/7a539a33-309b-491e-8ac4-a8630b9bf477.png
         * ReportFre : 1
         * Is_Disabled : false
         * Limit : 0.0
         * FileID : 0
         * CreateTime : 2017-08-24 00:00:00
         * ReportNum : 100.0
         */

        private int ProjectID;
        private String ProjectName;
        private String ProjectUnit;
        private String FilePath;
        private int ReportFre;
        private boolean Is_Disabled;
        private double Limit;
        private int FileID;
        private String CreateTime;
        private double ReportNum;

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

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }
    }
}
