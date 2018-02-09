package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/9/1.
 */

public class Sys_NoticeList_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"Sys_NoticeID":56,"NoticeType":1,"Notice_Content":"2.2更新\\n1.老鸡鸡","VersionID":1,"NoticeUrl":null,"StartDate":null,"EndDate":null,"CreateTime":"2017-09-05 15:42:06","Target":0,"Is_Old":false,"FileID":0,"FilePath":null},{"Sys_NoticeID":59,"NoticeType":2,"Notice_Content":"2.2更新\\n1.老鸡鸡","VersionID":1,"NoticeUrl":null,"StartDate":null,"EndDate":null,"CreateTime":"2017-09-06 15:42:06","Target":0,"Is_Old":false,"FileID":0,"FilePath":null},{"Sys_NoticeID":58,"NoticeType":3,"Notice_Content":"2.2更新\\n1.老鸡鸡","VersionID":1,"NoticeUrl":null,"StartDate":null,"EndDate":null,"CreateTime":"2017-09-05 15:42:06","Target":0,"Is_Old":false,"FileID":0,"FilePath":null}]
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
         * Sys_NoticeID : 56
         * NoticeType : 1
         * Notice_Content : 2.2更新\n1.老鸡鸡
         * VersionID : 1
         * NoticeUrl : null
         * StartDate : null
         * EndDate : null
         * CreateTime : 2017-09-05 15:42:06
         * Target : 0
         * Is_Old : false
         * FileID : 0
         * FilePath : null
         */

        private int Sys_NoticeID;
        private int NoticeType;
        private String Notice_Content;
        private int VersionID;
        private Object NoticeUrl;
        private Object StartDate;
        private Object EndDate;
        private String CreateTime;
        private int Target;
        private boolean Is_Old;
        private int FileID;
        private Object FilePath;

        public int getSys_NoticeID() {
            return Sys_NoticeID;
        }

        public void setSys_NoticeID(int Sys_NoticeID) {
            this.Sys_NoticeID = Sys_NoticeID;
        }

        public int getNoticeType() {
            return NoticeType;
        }

        public void setNoticeType(int NoticeType) {
            this.NoticeType = NoticeType;
        }

        public String getNotice_Content() {
            return Notice_Content;
        }

        public void setNotice_Content(String Notice_Content) {
            this.Notice_Content = Notice_Content;
        }

        public int getVersionID() {
            return VersionID;
        }

        public void setVersionID(int VersionID) {
            this.VersionID = VersionID;
        }

        public Object getNoticeUrl() {
            return NoticeUrl;
        }

        public void setNoticeUrl(Object NoticeUrl) {
            this.NoticeUrl = NoticeUrl;
        }

        public Object getStartDate() {
            return StartDate;
        }

        public void setStartDate(Object StartDate) {
            this.StartDate = StartDate;
        }

        public Object getEndDate() {
            return EndDate;
        }

        public void setEndDate(Object EndDate) {
            this.EndDate = EndDate;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getTarget() {
            return Target;
        }

        public void setTarget(int Target) {
            this.Target = Target;
        }

        public boolean isIs_Old() {
            return Is_Old;
        }

        public void setIs_Old(boolean Is_Old) {
            this.Is_Old = Is_Old;
        }

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }
    }
}
