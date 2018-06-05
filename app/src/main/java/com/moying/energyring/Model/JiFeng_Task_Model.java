package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waylen on 2018/6/1.
 */

public class JiFeng_Task_Model implements Parcelable {

    /**
     * DailyTask : {"TaskID":2,"TaskName":"每日汇总","Summary":"命名命名木木木","Integral":30,"Condition":"4","BtnText":"去完成,已完成"}
     */

    private DailyTaskBean DailyTask;

    public DailyTaskBean getDailyTask() {
        return DailyTask;
    }

    public void setDailyTask(DailyTaskBean DailyTask) {
        this.DailyTask = DailyTask;
    }

    public static class DailyTaskBean implements Parcelable {

        /**
         * TaskID : 2
         * TaskName : 每日汇总
         * Summary : 命名命名木木木
         * Integral : 30
         * Condition : 4
         * BtnText : 去完成,已完成
         */

        private int TaskID;
        private String TaskName;
        private String Summary;
        private int Integral;
        private String Condition;
        private String BtnText;

        public int getTaskID() {
            return TaskID;
        }

        public void setTaskID(int TaskID) {
            this.TaskID = TaskID;
        }

        public String getTaskName() {
            return TaskName;
        }

        public void setTaskName(String TaskName) {
            this.TaskName = TaskName;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public String getCondition() {
            return Condition;
        }

        public void setCondition(String Condition) {
            this.Condition = Condition;
        }

        public String getBtnText() {
            return BtnText;
        }

        public void setBtnText(String BtnText) {
            this.BtnText = BtnText;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.TaskID);
            dest.writeString(this.TaskName);
            dest.writeString(this.Summary);
            dest.writeInt(this.Integral);
            dest.writeString(this.Condition);
            dest.writeString(this.BtnText);
        }

        public DailyTaskBean() {
        }

        protected DailyTaskBean(Parcel in) {
            this.TaskID = in.readInt();
            this.TaskName = in.readString();
            this.Summary = in.readString();
            this.Integral = in.readInt();
            this.Condition = in.readString();
            this.BtnText = in.readString();
        }

        public static final Creator<DailyTaskBean> CREATOR = new Creator<DailyTaskBean>() {
            @Override
            public DailyTaskBean createFromParcel(Parcel source) {
                return new DailyTaskBean(source);
            }

            @Override
            public DailyTaskBean[] newArray(int size) {
                return new DailyTaskBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.DailyTask, flags);
    }

    public JiFeng_Task_Model() {
    }

    protected JiFeng_Task_Model(Parcel in) {
        this.DailyTask = in.readParcelable(DailyTaskBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<JiFeng_Task_Model> CREATOR = new Parcelable.Creator<JiFeng_Task_Model>() {
        @Override
        public JiFeng_Task_Model createFromParcel(Parcel source) {
            return new JiFeng_Task_Model(source);
        }

        @Override
        public JiFeng_Task_Model[] newArray(int size) {
            return new JiFeng_Task_Model[size];
        }
    };
}
