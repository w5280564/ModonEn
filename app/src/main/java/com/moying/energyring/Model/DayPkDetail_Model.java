package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waylen on 2017/12/28.
 */

public class DayPkDetail_Model implements Parcelable {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"Ranking":1,"Is_SenPost":0,"UpNum":-15,"ProjectUnit":"个","ProjectName":"俯卧撑","FilePath":"http://120.26.218.68:1111/Uploads/2017-12-18/733b7dd0-1277-40b2-bd22-43eb65395f51.png","Limit":1000,"ReportNum_All":3729,"ReportNum_Month":358,"ReportFre_Month":20,"ReportFre_All":66,"PKCoverImg":"http://172.16.0.111/Uploads/2018-01-16/157c2c7f-c440-4351-818c-13ec23af2c13.jpg","ReportID":127358}
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
         * Ranking : 1
         * Is_SenPost : 0
         * UpNum : -15.0
         * ProjectUnit : 个
         * ProjectName : 俯卧撑
         * FilePath : http://120.26.218.68:1111/Uploads/2017-12-18/733b7dd0-1277-40b2-bd22-43eb65395f51.png
         * Limit : 1000
         * ReportNum_All : 3729.0
         * ReportNum_Month : 358.0
         * ReportFre_Month : 20.0
         * ReportFre_All : 66
         * PKCoverImg : http://172.16.0.111/Uploads/2018-01-16/157c2c7f-c440-4351-818c-13ec23af2c13.jpg
         * ReportID : 127358
         * Is_Train
         */

        private int Ranking;
        private int Is_SenPost;
        private double UpNum;
        private String ProjectUnit;
        private String ProjectName;
        private String FilePath;
        private int Limit;
        private double ReportNum_All;
        private double ReportNum_Month;
        private double ReportFre_Month;
        private int ReportFre_All;
        private String PKCoverImg;
        private int ReportID;
        private boolean Is_Train;

        public boolean isIs_Train() {
            return Is_Train;
        }

        public void setIs_Train(boolean is_Train) {
            Is_Train = is_Train;
        }


        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getIs_SenPost() {
            return Is_SenPost;
        }

        public void setIs_SenPost(int Is_SenPost) {
            this.Is_SenPost = Is_SenPost;
        }

        public double getUpNum() {
            return UpNum;
        }

        public void setUpNum(double UpNum) {
            this.UpNum = UpNum;
        }

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public int getLimit() {
            return Limit;
        }

        public void setLimit(int Limit) {
            this.Limit = Limit;
        }

        public double getReportNum_All() {
            return ReportNum_All;
        }

        public void setReportNum_All(double ReportNum_All) {
            this.ReportNum_All = ReportNum_All;
        }

        public double getReportNum_Month() {
            return ReportNum_Month;
        }

        public void setReportNum_Month(double ReportNum_Month) {
            this.ReportNum_Month = ReportNum_Month;
        }

        public double getReportFre_Month() {
            return ReportFre_Month;
        }

        public void setReportFre_Month(double ReportFre_Month) {
            this.ReportFre_Month = ReportFre_Month;
        }

        public int getReportFre_All() {
            return ReportFre_All;
        }

        public void setReportFre_All(int ReportFre_All) {
            this.ReportFre_All = ReportFre_All;
        }

        public String getPKCoverImg() {
            return PKCoverImg;
        }

        public void setPKCoverImg(String PKCoverImg) {
            this.PKCoverImg = PKCoverImg;
        }

        public int getReportID() {
            return ReportID;
        }

        public void setReportID(int ReportID) {
            this.ReportID = ReportID;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.Ranking);
            dest.writeInt(this.Is_SenPost);
            dest.writeDouble(this.UpNum);
            dest.writeString(this.ProjectUnit);
            dest.writeString(this.ProjectName);
            dest.writeString(this.FilePath);
            dest.writeInt(this.Limit);
            dest.writeDouble(this.ReportNum_All);
            dest.writeDouble(this.ReportNum_Month);
            dest.writeDouble(this.ReportFre_Month);
            dest.writeInt(this.ReportFre_All);
            dest.writeString(this.PKCoverImg);
            dest.writeInt(this.ReportID);
            dest.writeByte(this.Is_Train ? (byte) 1 : (byte) 0);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.Ranking = in.readInt();
            this.Is_SenPost = in.readInt();
            this.UpNum = in.readDouble();
            this.ProjectUnit = in.readString();
            this.ProjectName = in.readString();
            this.FilePath = in.readString();
            this.Limit = in.readInt();
            this.ReportNum_All = in.readDouble();
            this.ReportNum_Month = in.readDouble();
            this.ReportFre_Month = in.readDouble();
            this.ReportFre_All = in.readInt();
            this.PKCoverImg = in.readString();
            this.ReportID = in.readInt();
            this.Is_Train = in.readByte() != 0;
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

    public DayPkDetail_Model() {
    }

    protected DayPkDetail_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<DayPkDetail_Model> CREATOR = new Creator<DayPkDetail_Model>() {
        @Override
        public DayPkDetail_Model createFromParcel(Parcel source) {
            return new DayPkDetail_Model(source);
        }

        @Override
        public DayPkDetail_Model[] newArray(int size) {
            return new DayPkDetail_Model[size];
        }
    };
}
