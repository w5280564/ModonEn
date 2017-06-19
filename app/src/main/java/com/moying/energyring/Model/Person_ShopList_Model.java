package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/18.
 */

public class Person_ShopList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProductID":1,"ProductName":"手环","RefPrice":200,"Integral":2000,"Brief":"能量手环,戴上每天都能量满满","Is_Del":false,"FileID":320,"FilePath":"http://localhost:58245/Uploads/2017-06-09/60747b55-0153-4791-ad21-521786bcfc8b.jpg"},{"ProductID":2,"ProductName":"笔记本","RefPrice":30,"Integral":2000,"Brief":null,"Is_Del":false,"FileID":324,"FilePath":"http://localhost:58245/Uploads/2017-06-14/609463a0-2796-4a22-ad41-6fc2d6b891f4.jpg"}]
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
         * ProductID : 1
         * ProductName : 手环
         * RefPrice : 200.0
         * Integral : 2000.0
         * Brief : 能量手环,戴上每天都能量满满
         * Is_Del : false
         * FileID : 320
         * FilePath : http://localhost:58245/Uploads/2017-06-09/60747b55-0153-4791-ad21-521786bcfc8b.jpg
         */

        private int ProductID;
        private String ProductName;
        private double RefPrice;
        private double Integral;
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

        public double getRefPrice() {
            return RefPrice;
        }

        public void setRefPrice(double RefPrice) {
            this.RefPrice = RefPrice;
        }

        public double getIntegral() {
            return Integral;
        }

        public void setIntegral(double Integral) {
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
