package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/6/29.
 */

public class PersonAndOther_Train_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"TrainCount":3,"Tarin_List":[{"RowsNum":1,"UserID":2066,"ProjectID":1,"FinishCount":2,"ProjectName":"俯卧撑","ProjectUnit":"个","Duration":70},{"RowsNum":2,"UserID":2066,"ProjectID":3,"FinishCount":2,"ProjectName":"深蹲","ProjectUnit":"个","Duration":73},{"RowsNum":3,"UserID":2066,"ProjectID":31,"FinishCount":1,"ProjectName":"卷腹","ProjectUnit":"个","Duration":35}]}
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
         * TrainCount : 3
         * Tarin_List : [{"RowsNum":1,"UserID":2066,"ProjectID":1,"FinishCount":2,"ProjectName":"俯卧撑","ProjectUnit":"个","Duration":70},{"RowsNum":2,"UserID":2066,"ProjectID":3,"FinishCount":2,"ProjectName":"深蹲","ProjectUnit":"个","Duration":73},{"RowsNum":3,"UserID":2066,"ProjectID":31,"FinishCount":1,"ProjectName":"卷腹","ProjectUnit":"个","Duration":35}]
         */

        private int TrainCount;
        private List<TarinListBean> Tarin_List;

        public int getTrainCount() {
            return TrainCount;
        }

        public void setTrainCount(int TrainCount) {
            this.TrainCount = TrainCount;
        }

        public List<TarinListBean> getTarin_List() {
            return Tarin_List;
        }

        public void setTarin_List(List<TarinListBean> Tarin_List) {
            this.Tarin_List = Tarin_List;
        }

        public static class TarinListBean {
            /**
             * RowsNum : 1
             * UserID : 2066
             * ProjectID : 1
             * FinishCount : 2
             * ProjectName : 俯卧撑
             * ProjectUnit : 个
             * Duration : 70
             */

            private int RowsNum;
            private int UserID;
            private int ProjectID;
            private int FinishCount;
            private String ProjectName;
            private String ProjectUnit;
            private int Duration;

            public int getRowsNum() {
                return RowsNum;
            }

            public void setRowsNum(int RowsNum) {
                this.RowsNum = RowsNum;
            }

            public int getUserID() {
                return UserID;
            }

            public void setUserID(int UserID) {
                this.UserID = UserID;
            }

            public int getProjectID() {
                return ProjectID;
            }

            public void setProjectID(int ProjectID) {
                this.ProjectID = ProjectID;
            }

            public int getFinishCount() {
                return FinishCount;
            }

            public void setFinishCount(int FinishCount) {
                this.FinishCount = FinishCount;
            }

            public String getProjectName() {
                return ProjectName;
            }

            public void setProjectName(String ProjectName) {
                this.ProjectName = ProjectName;
            }

            public String getProjectUnit() {
                return ProjectUnit;
            }

            public void setProjectUnit(String ProjectUnit) {
                this.ProjectUnit = ProjectUnit;
            }

            public int getDuration() {
                return Duration;
            }

            public void setDuration(int Duration) {
                this.Duration = Duration;
            }
        }
    }
}
