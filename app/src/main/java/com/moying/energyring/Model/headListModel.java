package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/11/30.
 */

public class headListModel {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"UserID":50,"NickName":"摩英教育海哥","ProfilePicture":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDTr6U3SCHLyRmCJ4ib79CbdQbrYhWJBhKGticshXIXZqET5ZrKVtIJYOE5vh8o8KTN6uxuHA7bCmzA/0","Is_Attention":true,"Ranking":0,"Integral":0},{"UserID":9087,"NickName":"雨梦","ProfilePicture":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPgrDjxwH5JwlQuiaJCibgibugDic9PHUO8QK6oHm5p57zBbZ6lDQPPBdt7jZEvBJ7GPYEubYsS4XsYQ/0","Is_Attention":false,"Ranking":0,"Integral":0},{"UserID":9146,"NickName":"娟丽","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-11-16/77dc3612-2c54-4eef-91c4-480802a8681b.jpg","Is_Attention":false,"Ranking":0,"Integral":0},{"UserID":8567,"NickName":"21期11张奕珂爸爸","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-10-16/4ab39607-b87f-420e-8247-f3d33e756616.jpg","Is_Attention":false,"Ranking":0,"Integral":0},{"UserID":7321,"NickName":"天天向上","ProfilePicture":null,"Is_Attention":false,"Ranking":0,"Integral":0},{"UserID":1421,"NickName":"摩英iOS开发","ProfilePicture":"http://120.26.218.68:1111/Uploads/2017-11-21/8866e625-382a-49ed-9033-676acdb6fa8b.jpg","Is_Attention":false,"Ranking":0,"Integral":0},{"UserID":2066,"NickName":"摩英 王丰","ProfilePicture":"http://120.26.218.68:8038/Attachment/6652_66522066.jpg","Is_Attention":true,"Ranking":0,"Integral":0},{"UserID":768,"NickName":"王松","ProfilePicture":"http://wx.qlogo.cn/mmopen/jPDJkcZQ8doJpgbyek9Vef4FRuWKYA7XjUMWatKgpEFKgoRibZYX2RrxT7D2v9VXvnRicIocaL2XFpicCQicsZwa53TdaVsQQy7G/0","Is_Attention":false,"Ranking":0,"Integral":0}]
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
         * UserID : 50
         * NickName : 摩英教育海哥
         * ProfilePicture : http://wx.qlogo.cn/mmopen/ajNVdqHZLLDTr6U3SCHLyRmCJ4ib79CbdQbrYhWJBhKGticshXIXZqET5ZrKVtIJYOE5vh8o8KTN6uxuHA7bCmzA/0
         * Is_Attention : true
         * Ranking : 0
         * Integral : 0
         */

        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private boolean Is_Attention;
        private int Ranking;
        private int Integral;

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

        public boolean isIs_Attention() {
            return Is_Attention;
        }

        public void setIs_Attention(boolean Is_Attention) {
            this.Is_Attention = Is_Attention;
        }

        public int getRanking() {
            return Ranking;
        }

        public void setRanking(int Ranking) {
            this.Ranking = Ranking;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }
    }
}
