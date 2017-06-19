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
     * Data : [{"BadgeID":1,"BadgeName":"累计30天徽章","BadgeDays":30,"FilePath":null,"Is_Have":false,"FileID":0},{"BadgeID":2,"BadgeName":"累计50天徽章","BadgeDays":50,"FilePath":null,"Is_Have":false,"FileID":0},{"BadgeID":4,"BadgeName":"累计100天徽章","BadgeDays":100,"FilePath":null,"Is_Have":false,"FileID":0},{"BadgeID":5,"BadgeName":"累计150天徽章","BadgeDays":150,"FilePath":null,"Is_Have":false,"FileID":0},{"BadgeID":6,"BadgeName":"累计200天徽章","BadgeDays":200,"FilePath":null,"Is_Have":false,"FileID":0},{"BadgeID":7,"BadgeName":"累计260天徽章","BadgeDays":260,"FilePath":null,"Is_Have":false,"FileID":0},{"BadgeID":8,"BadgeName":"累计300天徽章","BadgeDays":300,"FilePath":null,"Is_Have":false,"FileID":0}]
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
         * FilePath : null
         * Is_Have : false
         * FileID : 0
         */

        private int BadgeID;
        private String BadgeName;
        private int BadgeDays;
        private Object FilePath;
        private boolean Is_Have;
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

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }

        public boolean isIs_Have() {
            return Is_Have;
        }

        public void setIs_Have(boolean Is_Have) {
            this.Is_Have = Is_Have;
        }

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }
    }
}
