package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2017/10/24.
 */

public class newPk_Model implements Parcelable{

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectID":1,"CreateTime":null,"ProjectUnit":"个","ProjectName":"俯卧撑","FilePath":"http://localhost:58245/Uploads/2017-10-25/b60e7b79-f3fb-44d7-861f-5de50a391366.png","ReportNum":10,"ReportID":50127,"Ranking":2,"Report_Num_Month":260,"Limit":100,"Report_Days":45,"Gray_FilePath":"http://localhost:58245/Uploads/2017-10-25/4770dff0-2a71-4950-9ecc-4ace5cd2763e.png"},{"ProjectID":7,"CreateTime":null,"ProjectUnit":"个","ProjectName":"背单词","FilePath":"http://120.26.218.68:8040/uploads/i/2017/03/11fae8a5-4438-41d6-a435-e7deb194c2b1.png","ReportNum":20,"ReportID":50125,"Ranking":1,"Report_Num_Month":20,"Limit":100,"Report_Days":2,"Gray_FilePath":"http://localhost:58245/Uploads/2017-10-25/28ab5976-5d72-431a-ae52-97f7fe9d2e83.jpg"}]
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

    public static class DataBean implements Parcelable{
        /**
         * ProjectID : 1
         * CreateTime : null
         * ProjectUnit : 个
         * ProjectName : 俯卧撑
         * FilePath : http://localhost:58245/Uploads/2017-10-25/b60e7b79-f3fb-44d7-861f-5de50a391366.png
         * ReportNum : 10
         * ReportID : 50127
         * Ranking : 2
         * Report_Num_Month : 260.0
         * Limit : 100
         * Report_Days : 45
         * Gray_FilePath : http://localhost:58245/Uploads/2017-10-25/4770dff0-2a71-4950-9ecc-4ace5cd2763e.png
         * ClockID
         * Is_CanDel
         * Is_Train
         */

        private int ProjectID;
        private Object CreateTime;
        private String ProjectUnit;
        private String ProjectName;
        private String FilePath;
        private double ReportNum;
        private int ReportID;
        private int Ranking;
        private double Report_Num_Month;
        private int Limit;
        private int Report_Days;
        private String Gray_FilePath;
        private int ProjectTypeID;
        private boolean Is_CanDel;
        private boolean Is_Train;

        public boolean isIs_CanDel() {
            return Is_CanDel;
        }

        public void setIs_CanDel(boolean is_CanDel) {
            Is_CanDel = is_CanDel;
        }

        public boolean isIs_Train() {
            return Is_Train;
        }

        public void setIs_Train(boolean is_Train) {
            Is_Train = is_Train;
        }



        public int getClockID() {
            return ClockID;
        }

        public void setClockID(int clockID) {
            ClockID = clockID;
        }

        private int ClockID;

        public int getProjectTypeID() {
            return ProjectTypeID;
        }

        public void setProjectTypeID(int projectTypeID) {
            ProjectTypeID = projectTypeID;
        }
        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public Object getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(Object CreateTime) {
            this.CreateTime = CreateTime;
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

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }

        public int getReportID() {
            return ReportID;
        }

        public void setReportID(int ReportID) {
            this.ReportID = ReportID;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public double getReport_Num_Month() {
            return Report_Num_Month;
        }

        public void setReport_Num_Month(double Report_Num_Month) {
            this.Report_Num_Month = Report_Num_Month;
        }

        public int getLimit() {
            return Limit;
        }

        public void setLimit(int Limit) {
            this.Limit = Limit;
        }

        public int getReport_Days() {
            return Report_Days;
        }

        public void setReport_Days(int Report_Days) {
            this.Report_Days = Report_Days;
        }

        public String getGray_FilePath() {
            return Gray_FilePath;
        }

        public void setGray_FilePath(String Gray_FilePath) {
            this.Gray_FilePath = Gray_FilePath;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.ProjectID);
            dest.writeString(String.valueOf(this.CreateTime));
            dest.writeString(this.ProjectUnit);
            dest.writeString(this.ProjectName);
            dest.writeString(this.FilePath);
            dest.writeDouble(this.ReportNum);
            dest.writeInt(this.ReportID);
            dest.writeInt(this.Ranking);
            dest.writeDouble(this.Report_Num_Month);
            dest.writeInt(this.Limit);
            dest.writeInt(this.Report_Days);
            dest.writeString(this.Gray_FilePath);
            dest.writeInt(this.ProjectTypeID);
            dest.writeInt(this.ClockID);
            dest.writeByte(this.Is_Train ? (byte) 1 : (byte) 0);
            dest.writeByte(this.Is_CanDel ? (byte) 1 : (byte) 0);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.ProjectID = in.readInt();
            this.CreateTime = in.readString();
            this.ProjectUnit = in.readString();
            this.ProjectName = in.readString();
            this.FilePath = in.readString();
            this.ReportNum = in.readDouble();
            this.ReportID = in.readInt();
            this.Ranking = in.readInt();
            this.Report_Num_Month = in.readDouble();
            this.Limit = in.readInt();
            this.Report_Days = in.readInt();
            this.Gray_FilePath = in.readString();
            this.ProjectTypeID = in.readInt();
            this.ClockID = in.readInt();
            this.Is_Train = in.readByte() != 0;
            this.Is_CanDel = in.readByte() != 0;
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
        dest.writeList(this.Data);
    }

    public newPk_Model() {
    }

    protected newPk_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Creator<newPk_Model> CREATOR = new Creator<newPk_Model>() {
        @Override
        public newPk_Model createFromParcel(Parcel source) {
            return new newPk_Model(source);
        }

        @Override
        public newPk_Model[] newArray(int size) {
            return new newPk_Model[size];
        }
    };
}
