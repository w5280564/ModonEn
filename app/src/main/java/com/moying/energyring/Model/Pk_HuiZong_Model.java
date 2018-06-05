package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waylen on 2018/6/1.
 */

public class Pk_HuiZong_Model implements Parcelable {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"PostID":null,"Integral":"10","DailyTask":{"TaskID":2,"TaskName":"每日汇总","Summary":"命名命名木木木","Integral":30,"Condition":"4","BtnText":"去完成,已完成"}}
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

    public static class DataBean implements Parcelable {

        /**
         * PostID : null
         * Integral : 10
         * DailyTask : {"TaskID":2,"TaskName":"每日汇总","Summary":"命名命名木木木","Integral":30,"Condition":"4","BtnText":"去完成,已完成"}
         */

        private String PostID;
        private String Integral;
        private DailyTaskBean DailyTask;

        public String getPostID() {
            return PostID;
        }

        public void setPostID(String PostID) {
            this.PostID = PostID;
        }

        public String getIntegral() {
            return Integral;
        }

        public void setIntegral(String Integral) {
            this.Integral = Integral;
        }

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
            dest.writeString(PostID);
            dest.writeString(this.Integral);
            dest.writeParcelable(this.DailyTask, flags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.PostID = in.readParcelable(Object.class.getClassLoader());
            this.Integral = in.readString();
            this.DailyTask = in.readParcelable(DailyTaskBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.IsSuccess ? (byte) 1 : (byte) 0);
        dest.writeString(this.Msg);
        dest.writeInt(this.Status);
        dest.writeParcelable(this.Data, flags);
    }

    public Pk_HuiZong_Model() {
    }

    protected Pk_HuiZong_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pk_HuiZong_Model> CREATOR = new Parcelable.Creator<Pk_HuiZong_Model>() {
        @Override
        public Pk_HuiZong_Model createFromParcel(Parcel source) {
            return new Pk_HuiZong_Model(source);
        }

        @Override
        public Pk_HuiZong_Model[] newArray(int size) {
            return new Pk_HuiZong_Model[size];
        }
    };
}