package com.moying.energyring.Model;

/**
 * Created by waylen on 2018/8/6.
 */

public class Community_Status_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"ProjectID":2,"ProjectName":"跑步","FilePath":"http://120.26.218.68:1111/Uploads/2018-06-01/3b207577-f04e-4298-9e1c-4892575680ef.png","ProjectUnit":"公里","Is_Join":0,"Privacy":1,"NickName":"摩英 王丰","ProfilePicture":"http://120.26.218.68:1111/Uploads/2018-01-03/1434f3d2-6811-4f00-831a-94efd6d94163.jpg","Join_Num":638,"MaxContinueDays":0,"JoinDays":609,"Report_Days":10,"IntegralLevel":3,"ReportFre":12,"Is_SendPost":0,"ClockID":0,"ReportNum":16.4}
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
         * ProjectID : 2
         * ProjectName : 跑步
         * FilePath : http://120.26.218.68:1111/Uploads/2018-06-01/3b207577-f04e-4298-9e1c-4892575680ef.png
         * ProjectUnit : 公里
         * Is_Join : 0
         * Privacy : 1
         * NickName : 摩英 王丰
         * ProfilePicture : http://120.26.218.68:1111/Uploads/2018-01-03/1434f3d2-6811-4f00-831a-94efd6d94163.jpg
         * Join_Num : 638
         * MaxContinueDays : 0
         * JoinDays : 609
         * Report_Days : 10
         * IntegralLevel : 3
         * ReportFre : 12
         * Is_SendPost : 0
         * ClockID : 0
         * ReportNum : 16.4
         */

        private int ProjectID;
        private String ProjectName;
        private String FilePath;
        private String ProjectUnit;
        private int Is_Join;
        private int Privacy;
        private String NickName;
        private String ProfilePicture;
        private int Join_Num;
        private int MaxContinueDays;
        private int JoinDays;
        private int Report_Days;
        private int IntegralLevel;
        private int ReportFre;
        private int Is_SendPost;
        private int ClockID;
        private double ReportNum;
        private boolean Is_Train;

        public boolean isIs_Train() {
            return Is_Train;
        }

        public void setIs_Train(boolean is_Train) {
            Is_Train = is_Train;
        }


        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
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

        public String getProjectUnit() {
            return ProjectUnit;
        }

        public void setProjectUnit(String ProjectUnit) {
            this.ProjectUnit = ProjectUnit;
        }

        public int getIs_Join() {
            return Is_Join;
        }

        public void setIs_Join(int Is_Join) {
            this.Is_Join = Is_Join;
        }

        public int getPrivacy() {
            return Privacy;
        }

        public void setPrivacy(int Privacy) {
            this.Privacy = Privacy;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getProfilePicture() {
            return ProfilePicture;
        }

        public void setProfilePicture(String ProfilePicture) {
            this.ProfilePicture = ProfilePicture;
        }

        public int getJoin_Num() {
            return Join_Num;
        }

        public void setJoin_Num(int Join_Num) {
            this.Join_Num = Join_Num;
        }

        public int getMaxContinueDays() {
            return MaxContinueDays;
        }

        public void setMaxContinueDays(int MaxContinueDays) {
            this.MaxContinueDays = MaxContinueDays;
        }

        public int getJoinDays() {
            return JoinDays;
        }

        public void setJoinDays(int JoinDays) {
            this.JoinDays = JoinDays;
        }

        public int getReport_Days() {
            return Report_Days;
        }

        public void setReport_Days(int Report_Days) {
            this.Report_Days = Report_Days;
        }

        public int getIntegralLevel() {
            return IntegralLevel;
        }

        public void setIntegralLevel(int IntegralLevel) {
            this.IntegralLevel = IntegralLevel;
        }

        public int getReportFre() {
            return ReportFre;
        }

        public void setReportFre(int ReportFre) {
            this.ReportFre = ReportFre;
        }

        public int getIs_SendPost() {
            return Is_SendPost;
        }

        public void setIs_SendPost(int Is_SendPost) {
            this.Is_SendPost = Is_SendPost;
        }

        public int getClockID() {
            return ClockID;
        }

        public void setClockID(int ClockID) {
            this.ClockID = ClockID;
        }

        public double getReportNum() {
            return ReportNum;
        }

        public void setReportNum(double ReportNum) {
            this.ReportNum = ReportNum;
        }
    }
}
