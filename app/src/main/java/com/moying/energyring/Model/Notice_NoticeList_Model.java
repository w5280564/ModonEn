package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/19.
 */

public class Notice_NoticeList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"NoticeID":2,"NoticeEvent":"@","NoticeContent":"有人提到了你","NoticeType":1,"CreateTime":"2017-05-31 18:51:05","UserID":1,"SourceID":41,"SourceName":"Post_@","Is_Read":false},{"NoticeID":1,"NoticeEvent":"@","NoticeContent":"有人提到了你","NoticeType":1,"CreateTime":"2017-05-31 18:51:00","UserID":1,"SourceID":40,"SourceName":"Post_@","Is_Read":false}]
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
         * NoticeID : 2
         * NoticeEvent : @
         * NoticeContent : 有人提到了你
         * NoticeType : 1
         * CreateTime : 2017-05-31 18:51:05
         * UserID : 1
         * SourceID : 41
         * SourceName : Post_@
         * Is_Read : false
         */

        private int NoticeID;
        private String NoticeEvent;
        private String NoticeContent;
        private int NoticeType;
        private String CreateTime;
        private int UserID;
        private int SourceID;
        private String SourceName;
        private boolean Is_Read;

        public int getNoticeID() {
            return NoticeID;
        }

        public void setNoticeID(int NoticeID) {
            this.NoticeID = NoticeID;
        }

        public String getNoticeEvent() {
            return NoticeEvent;
        }

        public void setNoticeEvent(String NoticeEvent) {
            this.NoticeEvent = NoticeEvent;
        }

        public String getNoticeContent() {
            return NoticeContent;
        }

        public void setNoticeContent(String NoticeContent) {
            this.NoticeContent = NoticeContent;
        }

        public int getNoticeType() {
            return NoticeType;
        }

        public void setNoticeType(int NoticeType) {
            this.NoticeType = NoticeType;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public String getSourceName() {
            return SourceName;
        }

        public void setSourceName(String SourceName) {
            this.SourceName = SourceName;
        }

        public boolean isIs_Read() {
            return Is_Read;
        }

        public void setIs_Read(boolean Is_Read) {
            this.Is_Read = Is_Read;
        }
    }
}
