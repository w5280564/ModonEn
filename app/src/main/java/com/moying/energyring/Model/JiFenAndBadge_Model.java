package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by waylen on 2017/11/28.
 */

public class JiFenAndBadge_Model implements Parcelable {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"Integral":0,"_Badge":[{"BadgeID":16,"BadgeName":"累计签到50天徽章","BadgeDays":50,"BadgeType":2,"Is_Have":false,"HaveNum":280,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png","FileID_Gray":0,"FilePath_Gray":null}],"_Praise":[{"PraiseID":14,"UserID":2066,"ProjectID":2,"PraiseNum":80,"CreateTime":"2018-01-15 16:08:06","HaveNum":1,"ProjectName":"跑步","ProjectUnit":"公里","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-18/1b21ffe6-7de6-4e08-ac6c-3aa143ac9169.png"},{"PraiseID":15,"UserID":2066,"ProjectID":7,"PraiseNum":100,"CreateTime":"2018-01-15 16:08:06","HaveNum":3,"ProjectName":"背单词","ProjectUnit":"个","FilePath":"http://120.26.218.68:1111/Uploads/2017-10-25/8a7d31d6-62eb-4184-ac84-7179b620f931.png"}]}
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
         *
         * Integral : 0
         * _Badge : [{"BadgeID":16,"BadgeName":"累计签到50天徽章","BadgeDays":50,"BadgeType":2,"Is_Have":false,"HaveNum":280,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png","FileID_Gray":0,"FilePath_Gray":null}]
         * _Praise : [{"PraiseID":14,"UserID":2066,"ProjectID":2,"PraiseNum":80,"CreateTime":"2018-01-15 16:08:06","HaveNum":1,"ProjectName":"跑步","ProjectUnit":"公里","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-18/1b21ffe6-7de6-4e08-ac6c-3aa143ac9169.png"},{"PraiseID":15,"UserID":2066,"ProjectID":7,"PraiseNum":100,"CreateTime":"2018-01-15 16:08:06","HaveNum":3,"ProjectName":"背单词","ProjectUnit":"个","FilePath":"http://120.26.218.68:1111/Uploads/2017-10-25/8a7d31d6-62eb-4184-ac84-7179b620f931.png"}]
         * DailyTask : {"TaskID":1,"TaskName":"每日签到","Summary":"啦啦啦啦啦绿绿绿","Integral":30,"Condition":"3","BtnText":"去签到,已签到"}
         */

        private int Integral;
        private List<BadgeBean> _Badge;
        private List<PraiseBean> _Praise;
        private int RewardIntegral;
        /**
         * ProjectID : 25
         * TrainFre : 5
         * TargetNum : 30
         */

        private int ProjectID;
        private int TrainFre;
        private int TargetNum;
        private DailyTaskBean DailyTask;


        protected DataBean(Parcel in) {
            Integral = in.readInt();
            RewardIntegral = in.readInt();
            ProjectID = in.readInt();
            TrainFre = in.readInt();
            TargetNum = in.readInt();
            _Badge = in.createTypedArrayList(BadgeBean.CREATOR);
            _Praise = in.createTypedArrayList(PraiseBean.CREATOR);
            DailyTask = in.readParcelable(DailyTaskBean.class.getClassLoader());

        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public int getRewardIntegral() {
            return RewardIntegral;
        }

        public void setRewardIntegral(int rewardIntegral) {
            RewardIntegral = rewardIntegral;
        }

        public List<BadgeBean> get_Badge() {
            return _Badge;
        }

        public void set_Badge(List<BadgeBean> _Badge) {
            this._Badge = _Badge;
        }

        public List<PraiseBean> get_Praise() {
            return _Praise;
        }

        public void set_Praise(List<PraiseBean> _Praise) {
            this._Praise = _Praise;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(Integral);
            parcel.writeInt(RewardIntegral);
            parcel.writeInt(ProjectID);
            parcel.writeInt(TrainFre);
            parcel.writeInt(TargetNum);
            parcel.writeTypedList(_Badge);
            parcel.writeTypedList(_Praise);
            parcel.writeParcelable(DailyTask, i);
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public int getTrainFre() {
            return TrainFre;
        }

        public void setTrainFre(int TrainFre) {
            this.TrainFre = TrainFre;
        }

        public int getTargetNum() {
            return TargetNum;
        }

        public void setTargetNum(int TargetNum) {
            this.TargetNum = TargetNum;
        }

        public DailyTaskBean getDailyTask() {
            return DailyTask;
        }

        public void setDailyTask(DailyTaskBean DailyTask) {
            this.DailyTask = DailyTask;
        }

        public static class BadgeBean implements Parcelable{
            /**
             * BadgeID : 16
             * BadgeName : 累计签到50天徽章
             * BadgeDays : 50
             * BadgeType : 2
             * Is_Have : false
             * HaveNum : 280
             * FileID : 0
             * FilePath : http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png
             * FileID_Gray : 0
             * FilePath_Gray : null
             *  RewardIntegral: 10
             */

            private int BadgeID;
            private String BadgeName;
            private int BadgeDays;
            private int BadgeType;
            private boolean Is_Have;
            private int HaveNum;
            private int FileID;
            private String FilePath;
            private int FileID_Gray;
            private Object FilePath_Gray;

            public int getBadgeID() {
                return BadgeID;
            }

            public void setBadgeID(int BadgeID) {
                this.BadgeID = BadgeID;
            }

            public String getBadgeName() {
                return BadgeName;
            }

            public void setBadgeName(String BadgeName) {
                this.BadgeName = BadgeName;
            }

            public int getBadgeDays() {
                return BadgeDays;
            }

            public void setBadgeDays(int BadgeDays) {
                this.BadgeDays = BadgeDays;
            }

            public int getBadgeType() {
                return BadgeType;
            }

            public void setBadgeType(int BadgeType) {
                this.BadgeType = BadgeType;
            }

            public boolean isIs_Have() {
                return Is_Have;
            }

            public void setIs_Have(boolean Is_Have) {
                this.Is_Have = Is_Have;
            }

            public int getHaveNum() {
                return HaveNum;
            }

            public void setHaveNum(int HaveNum) {
                this.HaveNum = HaveNum;
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

            public int getFileID_Gray() {
                return FileID_Gray;
            }

            public void setFileID_Gray(int FileID_Gray) {
                this.FileID_Gray = FileID_Gray;
            }

            public Object getFilePath_Gray() {
                return FilePath_Gray;
            }

            public void setFilePath_Gray(Object FilePath_Gray) {
                this.FilePath_Gray = FilePath_Gray;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.BadgeID);
                dest.writeString(this.BadgeName);
                dest.writeInt(this.BadgeDays);
                dest.writeInt(this.BadgeType);
                dest.writeByte(this.Is_Have ? (byte) 1 : (byte) 0);
                dest.writeInt(this.HaveNum);
                dest.writeInt(this.FileID);
                dest.writeString(this.FilePath);
                dest.writeInt(this.FileID_Gray);
                dest.writeString(String.valueOf(this.FilePath_Gray));
            }

            public BadgeBean() {
            }

            protected BadgeBean(Parcel in) {
                this.BadgeID = in.readInt();
                this.BadgeName = in.readString();
                this.BadgeDays = in.readInt();
                this.BadgeType = in.readInt();
                this.Is_Have = in.readByte() != 0;
                this.HaveNum = in.readInt();
                this.FileID = in.readInt();
                this.FilePath = in.readString();
                this.FileID_Gray = in.readInt();
                this.FilePath_Gray = in.readString();
            }

            public static final Creator<BadgeBean> CREATOR = new Creator<BadgeBean>() {
                @Override
                public BadgeBean createFromParcel(Parcel source) {
                    return new BadgeBean(source);
                }

                @Override
                public BadgeBean[] newArray(int size) {
                    return new BadgeBean[size];
                }
            };
        }

        public static class PraiseBean implements Parcelable{
            /**
             * PraiseID : 14
             * UserID : 2066
             * ProjectID : 2
             * PraiseNum : 80
             * CreateTime : 2018-01-15 16:08:06
             * HaveNum : 1
             * ProjectName : 跑步
             * ProjectUnit : 公里
             * FilePath : http://120.26.218.68:1111/Uploads/2017-12-18/1b21ffe6-7de6-4e08-ac6c-3aa143ac9169.png
             */

            private int PraiseID;
            private int UserID;
            private int ProjectID;
            private int PraiseNum;
            private String CreateTime;
            private int HaveNum;
            private String ProjectName;
            private String ProjectUnit;
            private String FilePath;

            public int getPraiseID() {
                return PraiseID;
            }

            public void setPraiseID(int PraiseID) {
                this.PraiseID = PraiseID;
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

            public int getPraiseNum() {
                return PraiseNum;
            }

            public void setPraiseNum(int PraiseNum) {
                this.PraiseNum = PraiseNum;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public int getHaveNum() {
                return HaveNum;
            }

            public void setHaveNum(int HaveNum) {
                this.HaveNum = HaveNum;
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

            public String getFilePath() {
                return FilePath;
            }

            public void setFilePath(String FilePath) {
                this.FilePath = FilePath;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.PraiseID);
                dest.writeInt(this.UserID);
                dest.writeInt(this.ProjectID);
                dest.writeInt(this.PraiseNum);
                dest.writeString(this.CreateTime);
                dest.writeInt(this.HaveNum);
                dest.writeString(this.ProjectName);
                dest.writeString(this.ProjectUnit);
                dest.writeString(this.FilePath);
            }

            public PraiseBean() {
            }

            protected PraiseBean(Parcel in) {
                this.PraiseID = in.readInt();
                this.UserID = in.readInt();
                this.ProjectID = in.readInt();
                this.PraiseNum = in.readInt();
                this.CreateTime = in.readString();
                this.HaveNum = in.readInt();
                this.ProjectName = in.readString();
                this.ProjectUnit = in.readString();
                this.FilePath = in.readString();
            }

            public static final Creator<PraiseBean> CREATOR = new Creator<PraiseBean>() {
                @Override
                public PraiseBean createFromParcel(Parcel source) {
                    return new PraiseBean(source);
                }

                @Override
                public PraiseBean[] newArray(int size) {
                    return new PraiseBean[size];
                }
            };
        }

        public static class DailyTaskBean implements Parcelable {
            /**
             * TaskID : 1
             * TaskName : 每日签到
             * Summary : 啦啦啦啦啦绿绿绿
             * Integral : 30
             * Condition : 3
             * BtnText : 去签到,已签到
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

    public JiFenAndBadge_Model() {
    }

    protected JiFenAndBadge_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<JiFenAndBadge_Model> CREATOR = new Creator<JiFenAndBadge_Model>() {
        @Override
        public JiFenAndBadge_Model createFromParcel(Parcel source) {
            return new JiFenAndBadge_Model(source);
        }

        @Override
        public JiFenAndBadge_Model[] newArray(int size) {
            return new JiFenAndBadge_Model[size];
        }
    };
}
