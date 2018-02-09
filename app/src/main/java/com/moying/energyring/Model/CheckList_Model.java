package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/17.
 */

public class CheckList_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : [{"CheckInID":0,"UserID":768,"CheckInTime":null,"ContinueDays":0,"Ranking":1,"EarlyDays":438,"NickName":"王松","ProfilePicture":"http://wx.qlogo.cn/mmopen/jPDJkcZQ8doJpgbyek9Vef4FRuWKYA7XjUMWatKgpEFKgoRibZYX2RrxT7D2v9VXvnRicIocaL2XFpicCQicsZwa53TdaVsQQy7G/0","Is_Like":false},{"CheckInID":0,"UserID":848,"CheckInTime":null,"ContinueDays":0,"Ranking":2,"EarlyDays":402,"NickName":"阿勇","ProfilePicture":"http://wx.qlogo.cn/mmopen/aNHibhpdLicKNUuD4gY3uuSnknDkqfCMC20iajU5YLdl0k7bF7TDZIeVKNnp4jC8MSUF6M49egHfpiaNCj8kLsb6YyFGBJAKcZU1/0","Is_Like":false},{"CheckInID":0,"UserID":1029,"CheckInTime":null,"ContinueDays":0,"Ranking":3,"EarlyDays":320,"NickName":"Stone","ProfilePicture":"http://120.26.218.68:8038/Attachment/9793_97931029.jpg","Is_Like":false},{"CheckInID":0,"UserID":293,"CheckInTime":null,"ContinueDays":0,"Ranking":4,"EarlyDays":304,"NickName":"如山如水","ProfilePicture":"http://120.26.218.68:8038/Attachment/7132_1362293.jpg","Is_Like":false},{"CheckInID":0,"UserID":838,"CheckInTime":null,"ContinueDays":0,"Ranking":5,"EarlyDays":266,"NickName":"林 峻","ProfilePicture":"http://wx.qlogo.cn/mmopen/bbD9jy51jR0b1ZicXQtibw33KaCNmRKZsEbdZNSp1SibdtBKvmRXSSg2zGX9MuECS6jO1Woj8gKv0lLk4VqN2grXwWOtyZauazU/0","Is_Like":false},{"CheckInID":0,"UserID":3076,"CheckInTime":null,"ContinueDays":0,"Ranking":6,"EarlyDays":225,"NickName":"笑哈哈","ProfilePicture":null,"Is_Like":false},{"CheckInID":0,"UserID":3078,"CheckInTime":null,"ContinueDays":0,"Ranking":7,"EarlyDays":224,"NickName":"奈我何","ProfilePicture":null,"Is_Like":false},{"CheckInID":0,"UserID":6153,"CheckInTime":null,"ContinueDays":0,"Ranking":8,"EarlyDays":208,"NickName":"17期第八组黄林蔚","ProfilePicture":null,"Is_Like":false},{"CheckInID":0,"UserID":5474,"CheckInTime":null,"ContinueDays":0,"Ranking":9,"EarlyDays":187,"NickName":"15期张凯爸爸","ProfilePicture":"http://120.26.218.68:8038/Attachment/8671_86715474.jpg","Is_Like":false},{"CheckInID":0,"UserID":239,"CheckInTime":null,"ContinueDays":0,"Ranking":10,"EarlyDays":186,"NickName":"摩英教育D2胡家赫","ProfilePicture":"http://120.26.218.68:8038/Attachment/2258_2258239.jpg","Is_Like":false}]
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
         * CheckInID : 0
         * UserID : 768
         * CheckInTime : null
         * ContinueDays : 0
         * Ranking : 1
         * EarlyDays : 438
         * NickName : 王松
         * ProfilePicture : http://wx.qlogo.cn/mmopen/jPDJkcZQ8doJpgbyek9Vef4FRuWKYA7XjUMWatKgpEFKgoRibZYX2RrxT7D2v9VXvnRicIocaL2XFpicCQicsZwa53TdaVsQQy7G/0
         * Is_Like : false
         */

        private int CheckInID;
        private int UserID;
        private Object CheckInTime;
        private int ContinueDays;
        private int Ranking;
        private int EarlyDays;
        private String NickName;
        private String ProfilePicture;
        private boolean Is_Like;

        public int getCheckInID() {
            return CheckInID;
        }

        public void setCheckInID(int CheckInID) {
            this.CheckInID = CheckInID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public Object getCheckInTime() {
            return CheckInTime;
        }

        public void setCheckInTime(Object CheckInTime) {
            this.CheckInTime = CheckInTime;
        }

        public int getContinueDays() {
            return ContinueDays;
        }

        public void setContinueDays(int ContinueDays) {
            this.ContinueDays = ContinueDays;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getEarlyDays() {
            return EarlyDays;
        }

        public void setEarlyDays(int EarlyDays) {
            this.EarlyDays = EarlyDays;
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

        public boolean isIs_Like() {
            return Is_Like;
        }

        public void setIs_Like(boolean Is_Like) {
            this.Is_Like = Is_Like;
        }
    }
}
