package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/6/19.
 */

public class ShopDetails_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"ProductID":1,"ProductName":"手环","RefPrice":200,"Integral":2000,"Brief":"能量手环,戴上每天都能量满满","Is_Del":false,"FileID":411,"FilePath":"http://172.16.0.111/Uploads/2017-06-18/1c501c7d-e447-4763-b07c-7e729179d3e5.jpg"}
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * ProductID : 1
         * ProductName : 手环
         * RefPrice : 200.0
         * Integral : 2000.0
         * Brief : 能量手环,戴上每天都能量满满
         * Is_Del : false
         * FileID : 411
         * FilePath : http://172.16.0.111/Uploads/2017-06-18/1c501c7d-e447-4763-b07c-7e729179d3e5.jpg
         */

        private int ProductID;
        private String ProductName;
        private int RefPrice;
        private int Integral;
        private String Brief;
        private boolean Is_Del;
        private int FileID;
        private String FilePath;

        public int getProductID() {
            return ProductID;
        }

        public void setProductID(int ProductID) {
            this.ProductID = ProductID;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public int getRefPrice() {
            return RefPrice;
        }

        public void setRefPrice(int RefPrice) {
            this.RefPrice = RefPrice;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public String getBrief() {
            return Brief;
        }

        public void setBrief(String Brief) {
            this.Brief = Brief;
        }

        public boolean isIs_Del() {
            return Is_Del;
        }

        public void setIs_Del(boolean Is_Del) {
            this.Is_Del = Is_Del;
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
