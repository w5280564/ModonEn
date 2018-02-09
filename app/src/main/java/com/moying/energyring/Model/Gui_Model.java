package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/17.
 */

public class Gui_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"BadgeID":1,"BadgeName":"累计30天徽章","BadgeDays":30,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/f8edacd2-6536-4f5f-b964-ab5b542507c5.png","Is_Have":true,"HaveNum":100,"FileID":0},{"BadgeID":2,"BadgeName":"累计50天徽章","BadgeDays":50,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/849fd865-c3c8-422f-bbf1-01f3d3e2153b.png","Is_Have":false,"HaveNum":44,"FileID":0},{"BadgeID":4,"BadgeName":"累计100天徽章","BadgeDays":100,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/a4928cc9-0556-48da-8661-813007c72aac.png","Is_Have":false,"HaveNum":16,"FileID":0},{"BadgeID":5,"BadgeName":"累计150天徽章","BadgeDays":150,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/0501052a-63a5-498a-abd1-4ca8cbf4908f.png","Is_Have":false,"HaveNum":7,"FileID":0},{"BadgeID":6,"BadgeName":"累计200天徽章","BadgeDays":200,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/8412f7c9-2a18-4047-83b6-7e18c17f7589.png","Is_Have":false,"HaveNum":3,"FileID":0},{"BadgeID":7,"BadgeName":"累计260天徽章","BadgeDays":260,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/e5198277-3af4-4ddb-aed1-981f77df9149.png","Is_Have":false,"HaveNum":2,"FileID":0},{"BadgeID":8,"BadgeName":"累计300天徽章","BadgeDays":300,"FilePath":"http://120.26.218.68:1111/Uploads/2017-05-18/4f291021-2fc4-4459-b644-738893fd0007.png","Is_Have":false,"HaveNum":1,"FileID":0}]
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
         * BadgeID : 1
         * BadgeName : 累计30天徽章
         * BadgeDays : 30
         * FilePath : http://120.26.218.68:1111/Uploads/2017-05-18/f8edacd2-6536-4f5f-b964-ab5b542507c5.png
         * Is_Have : true
         * HaveNum : 100
         * FileID : 0
         */

        private int BadgeID;
        private String BadgeName;
        private int BadgeDays;
        private String FilePath;
        private boolean Is_Have;
        private int HaveNum;
        private int FileID;

        public int getBadgeID() {
            return BadgeID;
        }

        public void setBadgeID(int BadgeID) {
            this.BadgeID = BadgeID;
        }

        public String getBadgeName() {
            return BadgeName;
        }

        public void setBadgeName(String BadgeName) {
            this.BadgeName = BadgeName;
        }

        public int getBadgeDays() {
            return BadgeDays;
        }

        public void setBadgeDays(int BadgeDays) {
            this.BadgeDays = BadgeDays;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public boolean isIs_Have() {
            return Is_Have;
        }

        public void setIs_Have(boolean Is_Have) {
            this.Is_Have = Is_Have;
        }

        public int getHaveNum() {
            return HaveNum;
        }

        public void setHaveNum(int HaveNum) {
            this.HaveNum = HaveNum;
        }

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }
    }
}
