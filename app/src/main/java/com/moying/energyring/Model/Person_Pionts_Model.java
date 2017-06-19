package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/16.
 */

public class Person_Pionts_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"User_Sort":{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":1,"Integral":250},"User_Sort_List":[{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":1,"Integral":250},{"UserID":2,"NickName":"Start丶小春","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":2,"Integral":250},{"UserID":3,"NickName":"Start丶小夏","ProfilePicture":"http://172.16.0.111/Uploads/2017-06-15/d378df76-937d-43ab-b797-677abaa199e0.jpg","Ranking":3,"Integral":250},{"UserID":4,"NickName":"Start丶小秋","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":4,"Integral":250},{"UserID":5,"NickName":"Start丶小冬","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":5,"Integral":250},{"UserID":6,"NickName":"请记得","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":6,"Integral":250},{"UserID":7,"NickName":"Waylen似韦伦吾似沃伦","ProfilePicture":"http://tva2.sinaimg.cn/crop.0.0.640.640.1024/91f65f6djw8ehri51yrsuj20hs0ht3zw.jpg","Ranking":7,"Integral":250},{"UserID":8,"NickName":"╭⌒ 請記嘚","ProfilePicture":"http://q.qlogo.cn/qqapp/1104987324/407655539EC8E121BED799EEA2625CBB/100","Ranking":8,"Integral":250},{"UserID":9,"NickName":"肉灬肉","ProfilePicture":"http://wx.qlogo.cn/mmopen/5mxuSU5RGhbq5vDWTX5Zia6MJQwu9PfcztRiadNGHJstD8EKWMia6XiapicAb80zmR0EAonUsUYyCR2icSK1ibHZiaV9XgXwyawJT8NI/0","Ranking":9,"Integral":250},{"UserID":10,"NickName":"隔壁王叔不在家","ProfilePicture":"http://tva4.sinaimg.cn/crop.0.0.402.402.50/ec9ab37ajw8f1e7ud3rflj20b60b6aaz.jpg","Ranking":10,"Integral":250}]}
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
         * User_Sort : {"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":1,"Integral":250}
         * User_Sort_List : [{"UserID":1,"NickName":"Start丶小冰","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":1,"Integral":250},{"UserID":2,"NickName":"Start丶小春","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":2,"Integral":250},{"UserID":3,"NickName":"Start丶小夏","ProfilePicture":"http://172.16.0.111/Uploads/2017-06-15/d378df76-937d-43ab-b797-677abaa199e0.jpg","Ranking":3,"Integral":250},{"UserID":4,"NickName":"Start丶小秋","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":4,"Integral":250},{"UserID":5,"NickName":"Start丶小冬","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":5,"Integral":250},{"UserID":6,"NickName":"请记得","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","Ranking":6,"Integral":250},{"UserID":7,"NickName":"Waylen似韦伦吾似沃伦","ProfilePicture":"http://tva2.sinaimg.cn/crop.0.0.640.640.1024/91f65f6djw8ehri51yrsuj20hs0ht3zw.jpg","Ranking":7,"Integral":250},{"UserID":8,"NickName":"╭⌒ 請記嘚","ProfilePicture":"http://q.qlogo.cn/qqapp/1104987324/407655539EC8E121BED799EEA2625CBB/100","Ranking":8,"Integral":250},{"UserID":9,"NickName":"肉灬肉","ProfilePicture":"http://wx.qlogo.cn/mmopen/5mxuSU5RGhbq5vDWTX5Zia6MJQwu9PfcztRiadNGHJstD8EKWMia6XiapicAb80zmR0EAonUsUYyCR2icSK1ibHZiaV9XgXwyawJT8NI/0","Ranking":9,"Integral":250},{"UserID":10,"NickName":"隔壁王叔不在家","ProfilePicture":"http://tva4.sinaimg.cn/crop.0.0.402.402.50/ec9ab37ajw8f1e7ud3rflj20b60b6aaz.jpg","Ranking":10,"Integral":250}]
         */

        private UserSortBean User_Sort;
        private List<UserSortListBean> User_Sort_List;

        public UserSortBean getUser_Sort() {
            return User_Sort;
        }

        public void setUser_Sort(UserSortBean User_Sort) {
            this.User_Sort = User_Sort;
        }

        public List<UserSortListBean> getUser_Sort_List() {
            return User_Sort_List;
        }

        public void setUser_Sort_List(List<UserSortListBean> User_Sort_List) {
            this.User_Sort_List = User_Sort_List;
        }

        public static class UserSortBean {
            /**
             * UserID : 1
             * NickName : Start丶小冰
             * ProfilePicture : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0
             * Ranking : 1
             * Integral : 250
             */

            private int UserID;
            private String NickName;
            private String ProfilePicture;
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

        public static class UserSortListBean {
            /**
             * UserID : 1
             * NickName : Start丶小冰
             * ProfilePicture : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0
             * Ranking : 1
             * Integral : 250
             */

            private int UserID;
            private String NickName;
            private String ProfilePicture;
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
}
