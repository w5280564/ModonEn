package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/12/26.
 */

public class Pk_MyIntegral_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"Ranking":74,"UserID":2066,"NickName":"摩英 王丰","ProfilePicture":"http://172.16.0.111/Uploads/2017-12-21/572e4998-8ebb-48fb-9e63-da822973780b.jpg","AllIntegral":3430,"FriendRanking":4,"World":-73,"Friend":-3,"WorldExceedNum":9127,"WorldDiffIntegral":59,"FriendExceedNum":1,"FriendDiffIntegral":3422,"PreFriendName":"系统管理员"}
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
         * Ranking : 74
         * UserID : 2066
         * NickName : 摩英 王丰
         * ProfilePicture : http://172.16.0.111/Uploads/2017-12-21/572e4998-8ebb-48fb-9e63-da822973780b.jpg
         * AllIntegral : 3430
         * FriendRanking : 4
         * World : -73
         * Friend : -3
         * WorldExceedNum : 9127
         * WorldDiffIntegral : 59
         * FriendExceedNum : 1
         * FriendDiffIntegral : 3422
         * PreFriendName : 系统管理员
         * IntegralLevel
         */

        private int Ranking;
        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private int AllIntegral;
        private int FriendRanking;
        private int World;
        private int Friend;
        private int WorldExceedNum;
        private int WorldDiffIntegral;
        private int FriendExceedNum;
        private int FriendDiffIntegral;
        private String PreFriendName;
        private int IntegralLevel;

        public int getIntegralLevel() {
            return IntegralLevel;
        }

        public void setIntegralLevel(int integralLevel) {
            IntegralLevel = integralLevel;
        }


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

        public int getFriendRanking() {
            return FriendRanking;
        }

        public void setFriendRanking(int FriendRanking) {
            this.FriendRanking = FriendRanking;
        }

        public int getWorld() {
            return World;
        }

        public void setWorld(int World) {
            this.World = World;
        }

        public int getFriend() {
            return Friend;
        }

        public void setFriend(int Friend) {
            this.Friend = Friend;
        }

        public int getWorldExceedNum() {
            return WorldExceedNum;
        }

        public void setWorldExceedNum(int WorldExceedNum) {
            this.WorldExceedNum = WorldExceedNum;
        }

        public int getWorldDiffIntegral() {
            return WorldDiffIntegral;
        }

        public void setWorldDiffIntegral(int WorldDiffIntegral) {
            this.WorldDiffIntegral = WorldDiffIntegral;
        }

        public int getFriendExceedNum() {
            return FriendExceedNum;
        }

        public void setFriendExceedNum(int FriendExceedNum) {
            this.FriendExceedNum = FriendExceedNum;
        }

        public int getFriendDiffIntegral() {
            return FriendDiffIntegral;
        }

        public void setFriendDiffIntegral(int FriendDiffIntegral) {
            this.FriendDiffIntegral = FriendDiffIntegral;
        }

        public String getPreFriendName() {
            return PreFriendName;
        }

        public void setPreFriendName(String PreFriendName) {
            this.PreFriendName = PreFriendName;
        }
    }
}
