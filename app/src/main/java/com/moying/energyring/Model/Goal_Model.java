package com.moying.energyring.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by waylen on 2017/4/5.
 */

public class Goal_Model implements Serializable{

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectID":12,"ProjectName":"俯卧撑","ProjectUnit":"个","FilePath":"http://192.168.1.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png","ReportFre":14,"Is_Disabled":false,"Limit":100,"FileID":54},{"ProjectID":11,"ProjectName":"深蹲","ProjectUnit":"个","FilePath":"http://192.168.1.111/Uploads/2017-05-23/da5ac740-1ebb-4959-9e87-e31248cd0046.png","ReportFre":13,"Is_Disabled":false,"Limit":150,"FileID":55},{"ProjectID":17,"ProjectName":"健康走","ProjectUnit":"步","FilePath":null,"ReportFre":0,"Is_Disabled":false,"Limit":20000,"FileID":0},{"ProjectID":19,"ProjectName":"背单词","ProjectUnit":"个","FilePath":null,"ReportFre":0,"Is_Disabled":false,"Limit":100,"FileID":0},{"ProjectID":20,"ProjectName":"戒游戏","ProjectUnit":"天","FilePath":"http://192.168.1.111/Uploads/2017-05-18/9b8c0fc9-2635-47f9-9a13-5781312ac183.png","ReportFre":0,"Is_Disabled":false,"Limit":1,"FileID":38},{"ProjectID":21,"ProjectName":"坐姿收腹举腿","ProjectUnit":"次","FilePath":"http://192.168.1.111/Uploads/2017-05-18/b4bb276e-abae-4688-bfca-12c5924fb511.png","ReportFre":0,"Is_Disabled":false,"Limit":100,"FileID":39},{"ProjectID":22,"ProjectName":"KEEP专区","ProjectUnit":"分","FilePath":"http://192.168.1.111/Uploads/2017-05-18/89fc0c32-e175-486e-9a52-07be28415972.png","ReportFre":0,"Is_Disabled":false,"Limit":100,"FileID":40}]
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
         * ProjectID : 12
         * ProjectName : 俯卧撑
         * ProjectUnit : 个
         * FilePath : http://192.168.1.111/Uploads/2017-05-23/719cc3a5-2714-442f-bf9c-18d41f9f21ac.png
         * ReportFre : 14
         * Is_Disabled : false
         * Limit : 100.0
         * FileID : 54
         */

        private int ProjectID;
        private String ProjectName;
        private String ProjectUnit;
        private String FilePath;
        private int ReportFre;
        private boolean Is_Disabled;
        private double Limit;
        private int FileID;

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
    }
}
