package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/12/26.
 */

public class Pk_FenRankList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"Ranking":1,"UserID":2066,"NickName":"摩英 王丰","ProfilePicture":"http://172.16.0.111/Uploads/2017-12-21/572e4998-8ebb-48fb-9e63-da822973780b.jpg","AllIntegral":200086},{"Ranking":2,"UserID":1421,"NickName":"摩英iOS开发","ProfilePicture":"http://172.16.0.111/Uploads/2017-12-15/b1c1dcf1-28af-43e6-911b-b46d31b7050f.jpg","AllIntegral":200010},{"Ranking":3,"UserID":1,"NickName":"好文推荐","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-07-07/a195fc72-7aef-4c49-830d-b96778c16265.jpg","AllIntegral":67124},{"Ranking":4,"UserID":50,"NickName":"摩英教育海哥","ProfilePicture":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDTr6U3SCHLyRmCJ4ib79CbdQbrYhWJBhKGticshXIXZqET5ZrKVtIJYOE5vh8o8KTN6uxuHA7bCmzA/0","AllIntegral":63404},{"Ranking":5,"UserID":1087,"NickName":"摩英教育Shawty","ProfilePicture":"http://120.26.218.68:8038/Attachment/6845_68451087.jpg","AllIntegral":24747},{"Ranking":6,"UserID":768,"NickName":"王松","ProfilePicture":"http://wx.qlogo.cn/mmopen/jPDJkcZQ8doJpgbyek9Vef4FRuWKYA7XjUMWatKgpEFKgoRibZYX2RrxT7D2v9VXvnRicIocaL2XFpicCQicsZwa53TdaVsQQy7G/0","AllIntegral":12125},{"Ranking":7,"UserID":821,"NickName":"17期7组冯圣哲妈妈","ProfilePicture":"http://120.26.218.68:8038/Attachment/5379_5379821.jpg","AllIntegral":11335},{"Ranking":8,"UserID":457,"NickName":"摩英教育余雅芳","ProfilePicture":"http://120.26.218.68:8038/Attachment/4688_4688457.jpg","AllIntegral":11109},{"Ranking":9,"UserID":287,"NickName":"摩英教育刘圣依","ProfilePicture":"http://120.26.218.68:8038/Attachment/1067_1067287.jpg","AllIntegral":10443},{"Ranking":10,"UserID":1570,"NickName":"91期A2余典","ProfilePicture":"http://120.26.218.68:8038/Attachment/3064_30641570.jpg","AllIntegral":9702}]
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
         * Ranking : 1
         * UserID : 2066
         * NickName : 摩英 王丰
         * ProfilePicture : http://172.16.0.111/Uploads/2017-12-21/572e4998-8ebb-48fb-9e63-da822973780b.jpg
         * AllIntegral : 200086
         */

        private int Ranking;
        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private int AllIntegral;

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
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

        public int getAllIntegral() {
            return AllIntegral;
        }

        public void setAllIntegral(int AllIntegral) {
            this.AllIntegral = AllIntegral;
        }
    }
}
