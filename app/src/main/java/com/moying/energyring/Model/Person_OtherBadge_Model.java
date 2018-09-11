package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/7/5.
 */

public class Person_OtherBadge_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"RowsNum":1,"BadgeID":0,"BadgeName":"训练新手lv1","Caption":null,"FilePath":"http://172.16.0.222/Uploads/2018-07-03/246f7824-50d6-4e07-a1ff-91c1152e4200.png","Badge_Gray":null,"CreatDate":"2018/7/4 0:00:00","Is_Have":true,"HaveNum":13,"Progress":0,"BadgeType":4},{"RowsNum":2,"BadgeID":0,"BadgeName":"累计行走5公里","Caption":null,"FilePath":"http://172.16.0.222/Uploads/2018-07-04/9b512d31-c70a-48ae-9b6e-5fec40bc05a6.png","Badge_Gray":null,"CreatDate":"2018/7/4 0:00:00","Is_Have":true,"HaveNum":641,"Progress":0,"BadgeType":6},{"RowsNum":3,"BadgeID":0,"BadgeName":"累计行走10公里","Caption":null,"FilePath":null,"Badge_Gray":null,"CreatDate":"2018/7/4 0:00:00","Is_Have":true,"HaveNum":432,"Progress":0,"BadgeType":6}]
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
         * BadgeID : 0
         * BadgeName : 训练新手lv1
         * Caption : null
         * FilePath : http://172.16.0.222/Uploads/2018-07-03/246f7824-50d6-4e07-a1ff-91c1152e4200.png
         * Badge_Gray : null
         * CreatDate : 2018/7/4 0:00:00
         * Is_Have : true
         * HaveNum : 13
         * Progress : 0
         * BadgeType : 4
         */

        private int RowsNum;
        private int BadgeID;
        private String BadgeName;
        private Object Caption;
        private String FilePath;
        private Object Badge_Gray;
        private String CreatDate;
        private boolean Is_Have;
        private int HaveNum;
        private int Progress;
        private int BadgeType;

        public int getRowsNum() {
            return RowsNum;
        }

        public void setRowsNum(int RowsNum) {
            this.RowsNum = RowsNum;
        }

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

        public Object getCaption() {
            return Caption;
        }

        public void setCaption(Object Caption) {
            this.Caption = Caption;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public Object getBadge_Gray() {
            return Badge_Gray;
        }

        public void setBadge_Gray(Object Badge_Gray) {
            this.Badge_Gray = Badge_Gray;
        }

        public String getCreatDate() {
            return CreatDate;
        }

        public void setCreatDate(String CreatDate) {
            this.CreatDate = CreatDate;
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

        public int getProgress() {
            return Progress;
        }

        public void setProgress(int Progress) {
            this.Progress = Progress;
        }

        public int getBadgeType() {
            return BadgeType;
        }

        public void setBadgeType(int BadgeType) {
            this.BadgeType = BadgeType;
        }
    }
}
