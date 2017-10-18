package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/9/30.
 */

public class postTagList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"TagID":11,"TagName":"早起签到","Role":0},{"TagID":12,"TagName":"摩英英雄榜","Role":1}]
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
         * TagID : 11
         * TagName : 早起签到
         * Role : 0
         */

        private int TagID;
        private String TagName;
        private int Role;

        public int getTagID() {
            return TagID;
        }

        public void setTagID(int TagID) {
            this.TagID = TagID;
        }

        public String getTagName() {
            return TagName;
        }

        public void setTagName(String TagName) {
            this.TagName = TagName;
        }

        public int getRole() {
            return Role;
        }

        public void setRole(int Role) {
            this.Role = Role;
        }
    }
}
