package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/2.
 */

public class FindBanner_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"BannerID":1,"BannerName":"CC","BannerContent":"http://www.baidu.com","BannerType":1,"ViewArea":1,"Is_Disabled":false,"FileID":259,"FilePath":"http://172.16.0.111/Uploads/2017-06-02/d56716e3-d6d8-433e-80ee-89d57c10bb41.jpg"},{"BannerID":5,"BannerName":"淘宝","BannerContent":"http://www.taobao.com","BannerType":1,"ViewArea":1,"Is_Disabled":false,"FileID":260,"FilePath":"http://172.16.0.111/Uploads/2017-06-02/2697be5a-dfd2-4b97-8f62-1fe08eb948ac.jpg"},{"BannerID":6,"BannerName":"百度","BannerContent":"http://www.baidu.com","BannerType":1,"ViewArea":2,"Is_Disabled":false,"FileID":262,"FilePath":"http://172.16.0.111/Uploads/2017-06-02/52906825-d47e-4276-a683-98d5b2b99c2e.jpg"}]
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
         * BannerID : 1
         * BannerName : CC
         * BannerContent : http://www.baidu.com
         * BannerType : 1
         * ViewArea : 1
         * Is_Disabled : false
         * FileID : 259
         * FilePath : http://172.16.0.111/Uploads/2017-06-02/d56716e3-d6d8-433e-80ee-89d57c10bb41.jpg
         */

        private int BannerID;
        private String BannerName;
        private String BannerContent;
        private int BannerType;
        private int ViewArea;
        private boolean Is_Disabled;
        private int FileID;
        private String FilePath;

        public int getBannerID() {
            return BannerID;
        }

        public void setBannerID(int BannerID) {
            this.BannerID = BannerID;
        }

        public String getBannerName() {
            return BannerName;
        }

        public void setBannerName(String BannerName) {
            this.BannerName = BannerName;
        }

        public String getBannerContent() {
            return BannerContent;
        }

        public void setBannerContent(String BannerContent) {
            this.BannerContent = BannerContent;
        }

        public int getBannerType() {
            return BannerType;
        }

        public void setBannerType(int BannerType) {
            this.BannerType = BannerType;
        }

        public int getViewArea() {
            return ViewArea;
        }

        public void setViewArea(int ViewArea) {
            this.ViewArea = ViewArea;
        }

        public boolean isIs_Disabled() {
            return Is_Disabled;
        }

        public void setIs_Disabled(boolean Is_Disabled) {
            this.Is_Disabled = Is_Disabled;
        }

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }
    }
}
