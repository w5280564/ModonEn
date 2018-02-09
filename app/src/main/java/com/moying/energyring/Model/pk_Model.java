package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/15.
 */

public class pk_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectName":"总汇报次数","Num":0,"Unit":"次"},{"ProjectName":"累计天数","Num":0,"Unit":"天"},{"ProjectName":"获赞个数","Num":0,"Unit":"个"},{"ProjectName":"获赞排名","Num":1,"Unit":"名"},{"ProjectName":"徽章规则","Num":0,"Unit":"徽章规则"}]
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
         * ProjectName : 总汇报次数
         * Num : 0
         * Unit : 次
         */

        private String ProjectName;
        private int Num;
        private String Unit;

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }
    }



}
