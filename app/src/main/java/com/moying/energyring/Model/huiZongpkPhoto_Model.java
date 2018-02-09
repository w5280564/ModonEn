package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/11/24.
 */

public class huiZongpkPhoto_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"FileID":0,"SourceID":0,"SourceName":null,"FileName":"Luban_1511493104515.jpg","NewFileName":"4d35ea75-3060-4785-b7bc-baef9ae91fed.jpg","FilePath":"http://172.16.0.111/Uploads/2017-11-24/4d35ea75-3060-4785-b7bc-baef9ae91fed.jpg","ThumbnailsPath":null}]
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
         * FileID : 0
         * SourceID : 0
         * SourceName : null
         * FileName : Luban_1511493104515.jpg
         * NewFileName : 4d35ea75-3060-4785-b7bc-baef9ae91fed.jpg
         * FilePath : http://172.16.0.111/Uploads/2017-11-24/4d35ea75-3060-4785-b7bc-baef9ae91fed.jpg
         * ThumbnailsPath : null
         */

        private int FileID;
        private int SourceID;
        private Object SourceName;
        private String FileName;
        private String NewFileName;
        private String FilePath;
        private Object ThumbnailsPath;

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public Object getSourceName() {
            return SourceName;
        }

        public void setSourceName(Object SourceName) {
            this.SourceName = SourceName;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
        }

        public String getNewFileName() {
            return NewFileName;
        }

        public void setNewFileName(String NewFileName) {
            this.NewFileName = NewFileName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public Object getThumbnailsPath() {
            return ThumbnailsPath;
        }

        public void setThumbnailsPath(Object ThumbnailsPath) {
            this.ThumbnailsPath = ThumbnailsPath;
        }
    }
}
