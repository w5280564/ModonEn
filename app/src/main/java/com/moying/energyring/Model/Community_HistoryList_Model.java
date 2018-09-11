package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/8/10.
 */

public class Community_HistoryList_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"PostID":51981,"CreateTime":"2018/8/10 16:30:30","PostContent":"你好你好你好你好你好你好","Img_List":["http://172.16.0.222/Uploads/2018-08-10/a2ccf6af-1530-442a-9843-2bdecd620b6b.jpg"]}]
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
         * PostID : 51981
         * CreateTime : 2018/8/10 16:30:30
         * PostContent : 你好你好你好你好你好你好
         * Img_List : ["http://172.16.0.222/Uploads/2018-08-10/a2ccf6af-1530-442a-9843-2bdecd620b6b.jpg"]
         */

        private int PostID;
        private String CreateTime;
        private String PostContent;
        private List<String> Img_List;

        public int getPostID() {
            return PostID;
        }

        public void setPostID(int PostID) {
            this.PostID = PostID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getPostContent() {
            return PostContent;
        }

        public void setPostContent(String PostContent) {
            this.PostContent = PostContent;
        }

        public List<String> getImg_List() {
            return Img_List;
        }

        public void setImg_List(List<String> Img_List) {
            this.Img_List = Img_List;
        }
    }
}
