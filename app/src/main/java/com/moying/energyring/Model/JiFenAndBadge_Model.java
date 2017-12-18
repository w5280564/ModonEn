package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2017/11/28.
 */

public class JiFenAndBadge_Model implements Parcelable {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"Integral":10,"_Badge":[{"BadgeID":16,"BadgeName":"累计签到50天徽章","BadgeDays":50,"BadgeType":2,"Is_Have":false,"HaveNum":280,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png","FileID_Gray":0,"FilePath_Gray":null}]}
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
         * Integral : 10
         * _Badge : [{"BadgeID":16,"BadgeName":"累计签到50天徽章","BadgeDays":50,"BadgeType":2,"Is_Have":false,"HaveNum":280,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png","FileID_Gray":0,"FilePath_Gray":null}]
         */

        private int Integral;
        private List<BadgeBean> _Badge;

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public List<BadgeBean> get_Badge() {
            return _Badge;
        }

        public void set_Badge(List<BadgeBean> _Badge) {
            this._Badge = _Badge;
        }

        public static class BadgeBean implements Parcelable {
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
                this.FilePath_Gray =in.readString();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.Integral);
            dest.writeList(this._Badge);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.Integral = in.readInt();
            this._Badge = new ArrayList<BadgeBean>();
            in.readList(this._Badge, BadgeBean.class.getClassLoader());
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
