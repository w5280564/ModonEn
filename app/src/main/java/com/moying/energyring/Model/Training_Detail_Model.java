package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/3/30.
 */

public class Training_Detail_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"Pro_Train":{"TrainID":"36","UserID":"2066","ProjectID":"31","AddDate":"2018/4/26 0:00:00","TargetNum":"60.00","GroupCount":"3","SoundType":"1","BGMFileName":"bgm_1.mp3","BGMFileID":"0","Status":"0","AddTime":"2018/4/26 10:45:40","GroupNum":"20","Trainlimit":"300.00","Interval":"3","ProjectName":"卷腹","ProjectUnit":"个","CurrGroupNo":"1","Pro_FilePath":"http://120.26.218.68:1111/Uploads/2017-12-18/4f37d3ee-8deb-470d-9998-6dd14ed4138c.png","RestInterval":"30","Duration":"0","NeedsTime":"4","TrainAudioName":"hg-20-3-31.mp3","TrainProName":"hg-proname-31.mp3"},"Audio_List":[["hg-proname-31.mp3","hg-first.mp3","hg-321go.mp3","hg-20-3-31.mp3","hg-rest-30.mp3"],["hg-next.mp3","hg-321go.mp3","hg-20-3-31.mp3","hg-rest-30.mp3"],["hg-last.mp3","hg-321go.mp3","hg-20-3-31.mp3"]]}
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
         * Pro_Train : {"TrainID":"36","UserID":"2066","ProjectID":"31","AddDate":"2018/4/26 0:00:00","TargetNum":"60.00","GroupCount":"3","SoundType":"1","BGMFileName":"bgm_1.mp3","BGMFileID":"0","Status":"0","AddTime":"2018/4/26 10:45:40","GroupNum":"20","Trainlimit":"300.00","Interval":"3","ProjectName":"卷腹","ProjectUnit":"个","CurrGroupNo":"1","Pro_FilePath":"http://120.26.218.68:1111/Uploads/2017-12-18/4f37d3ee-8deb-470d-9998-6dd14ed4138c.png","RestInterval":"30","Duration":"0","NeedsTime":"4","TrainAudioName":"hg-20-3-31.mp3","TrainProName":"hg-proname-31.mp3"}
         * Audio_List : [["hg-proname-31.mp3","hg-first.mp3","hg-321go.mp3","hg-20-3-31.mp3","hg-rest-30.mp3"],["hg-next.mp3","hg-321go.mp3","hg-20-3-31.mp3","hg-rest-30.mp3"],["hg-last.mp3","hg-321go.mp3","hg-20-3-31.mp3"]]
         */

        private ProTrainBean Pro_Train;
        private List<List<String>> Audio_List;

        public ProTrainBean getPro_Train() {
            return Pro_Train;
        }

        public void setPro_Train(ProTrainBean Pro_Train) {
            this.Pro_Train = Pro_Train;
        }

        public List<List<String>> getAudio_List() {
            return Audio_List;
        }

        public void setAudio_List(List<List<String>> Audio_List) {
            this.Audio_List = Audio_List;
        }

        public static class ProTrainBean {
            /**
             * TrainID : 36
             * UserID : 2066
             * ProjectID : 31
             * AddDate : 2018/4/26 0:00:00
             * TargetNum : 60.00
             * GroupCount : 3
             * SoundType : 1
             * BGMFileName : bgm_1.mp3
             * BGMFileID : 0
             * Status : 0
             * AddTime : 2018/4/26 10:45:40
             * GroupNum : 20
             * Trainlimit : 300.00
             * Interval : 3
             * ProjectName : 卷腹
             * ProjectUnit : 个
             * CurrGroupNo : 1
             * Pro_FilePath : http://120.26.218.68:1111/Uploads/2017-12-18/4f37d3ee-8deb-470d-9998-6dd14ed4138c.png
             * RestInterval : 30
             * Duration : 0
             * NeedsTime : 4
             * TrainAudioName : hg-20-3-31.mp3
             * TrainProName : hg-proname-31.mp3
             */

            private String TrainID;
            private String UserID;
            private String ProjectID;
            private String AddDate;
            private int TargetNum;
            private int GroupCount;
            private int SoundType;
            private String BGMFileName;
            private String BGMFileID;
            private String Status;
            private String AddTime;
            private int GroupNum;
            private int Trainlimit;
            private int Interval;
            private String ProjectName;
            private String ProjectUnit;
            private int CurrGroupNo;
            private String Pro_FilePath;
            private int RestInterval;
            private int Duration;
            private String NeedsTime;
            private String TrainAudioName;
            private String TrainProName;

            public String getTrainID() {
                return TrainID;
            }

            public void setTrainID(String TrainID) {
                this.TrainID = TrainID;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getProjectID() {
                return ProjectID;
            }

            public void setProjectID(String ProjectID) {
                this.ProjectID = ProjectID;
            }

            public String getAddDate() {
                return AddDate;
            }

            public void setAddDate(String AddDate) {
                this.AddDate = AddDate;
            }

            public int getTargetNum() {
                return TargetNum;
            }

            public void setTargetNum(int TargetNum) {
                this.TargetNum = TargetNum;
            }

            public int getGroupCount() {
                return GroupCount;
            }

            public void setGroupCount(int GroupCount) {
                this.GroupCount = GroupCount;
            }

            public int getSoundType() {
                return SoundType;
            }

            public void setSoundType(int SoundType) {
                this.SoundType = SoundType;
            }

            public String getBGMFileName() {
                return BGMFileName;
            }

            public void setBGMFileName(String BGMFileName) {
                this.BGMFileName = BGMFileName;
            }

            public String getBGMFileID() {
                return BGMFileID;
            }

            public void setBGMFileID(String BGMFileID) {
                this.BGMFileID = BGMFileID;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getAddTime() {
                return AddTime;
            }

            public void setAddTime(String AddTime) {
                this.AddTime = AddTime;
            }

            public int getGroupNum() {
                return GroupNum;
            }

            public void setGroupNum(int GroupNum) {
                this.GroupNum = GroupNum;
            }

            public int getTrainlimit() {
                return Trainlimit;
            }

            public void setTrainlimit(int Trainlimit) {
                this.Trainlimit = Trainlimit;
            }

            public int getInterval() {
                return Interval;
            }

            public void setInterval(int Interval) {
                this.Interval = Interval;
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

            public int getCurrGroupNo() {
                return CurrGroupNo;
            }

            public void setCurrGroupNo(int CurrGroupNo) {
                this.CurrGroupNo = CurrGroupNo;
            }

            public String getPro_FilePath() {
                return Pro_FilePath;
            }

            public void setPro_FilePath(String Pro_FilePath) {
                this.Pro_FilePath = Pro_FilePath;
            }

            public int getRestInterval() {
                return RestInterval;
            }

            public void setRestInterval(int RestInterval) {
                this.RestInterval = RestInterval;
            }

            public int getDuration() {
                return Duration;
            }

            public void setDuration(int Duration) {
                this.Duration = Duration;
            }

            public String getNeedsTime() {
                return NeedsTime;
            }

            public void setNeedsTime(String NeedsTime) {
                this.NeedsTime = NeedsTime;
            }

            public String getTrainAudioName() {
                return TrainAudioName;
            }

            public void setTrainAudioName(String TrainAudioName) {
                this.TrainAudioName = TrainAudioName;
            }

            public String getTrainProName() {
                return TrainProName;
            }

            public void setTrainProName(String TrainProName) {
                this.TrainProName = TrainProName;
            }
        }
    }
}
