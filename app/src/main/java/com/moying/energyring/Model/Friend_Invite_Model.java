package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/8/24.
 */

public class Friend_Invite_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"InSys":[{"PhoneNum":"18721666525","UserID":2066,"NickName":"摩英 王丰","ProfilePicture":"http://120.26.218.68:1111/Uploads/2018-01-03/1434f3d2-6811-4f00-831a-94efd6d94163.jpg","Is_Friend":false,"Is_Attention":false,"Is_Attention_Me":true,"IntegralLevel":3,"ProjectID":3,"ProjectName":"深蹲","Report_Days":62}],"NotInSys":[{"PhoneNum":"13855061622","UserID":0,"NickName":null,"ProfilePicture":null,"Is_Friend":false,"Is_Attention":false,"Is_Attention_Me":false,"IntegralLevel":null,"ProjectID":null,"ProjectName":null,"Report_Days":null}]}
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
        private List<InSysBean> InSys;
        private List<NotInSysBean> NotInSys;

        public List<InSysBean> getInSys() {
            return InSys;
        }

        public void setInSys(List<InSysBean> InSys) {
            this.InSys = InSys;
        }

        public List<NotInSysBean> getNotInSys() {
            return NotInSys;
        }

        public void setNotInSys(List<NotInSysBean> NotInSys) {
            this.NotInSys = NotInSys;
        }

        public static class InSysBean {
            /**
             * PhoneNum : 18721666525
             * UserID : 2066
             * NickName : 摩英 王丰
             * ProfilePicture : http://120.26.218.68:1111/Uploads/2018-01-03/1434f3d2-6811-4f00-831a-94efd6d94163.jpg
             * Is_Friend : false
             * Is_Attention : false
             * Is_Attention_Me : true
             * IntegralLevel : 3
             * ProjectID : 3
             * ProjectName : 深蹲
             * Report_Days : 62
             */

            private String PhoneNum;
            private int UserID;
            private String NickName;
            private String ProfilePicture;
            private boolean Is_Friend;
            private boolean Is_Attention;
            private boolean Is_Attention_Me;
            private int IntegralLevel;
            private int ProjectID;
            private String ProjectName;
            private int Report_Days;

            public String getPhoneNum() {
                return PhoneNum;
            }

            public void setPhoneNum(String PhoneNum) {
                this.PhoneNum = PhoneNum;
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

            public boolean isIs_Friend() {
                return Is_Friend;
            }

            public void setIs_Friend(boolean Is_Friend) {
                this.Is_Friend = Is_Friend;
            }

            public boolean isIs_Attention() {
                return Is_Attention;
            }

            public void setIs_Attention(boolean Is_Attention) {
                this.Is_Attention = Is_Attention;
            }

            public boolean isIs_Attention_Me() {
                return Is_Attention_Me;
            }

            public void setIs_Attention_Me(boolean Is_Attention_Me) {
                this.Is_Attention_Me = Is_Attention_Me;
            }

            public int getIntegralLevel() {
                return IntegralLevel;
            }

            public void setIntegralLevel(int IntegralLevel) {
                this.IntegralLevel = IntegralLevel;
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

            public int getReport_Days() {
                return Report_Days;
            }

            public void setReport_Days(int Report_Days) {
                this.Report_Days = Report_Days;
            }
        }

        public static class NotInSysBean {
            /**
             * PhoneNum : 13855061622
             * UserID : 0
             * NickName : null
             * ProfilePicture : null
             * Is_Friend : false
             * Is_Attention : false
             * Is_Attention_Me : false
             * IntegralLevel : null
             * ProjectID : null
             * ProjectName : null
             * Report_Days : null
             */

            private String PhoneNum;
            private int UserID;
            private Object NickName;
            private Object ProfilePicture;
            private boolean Is_Friend;
            private boolean Is_Attention;
            private boolean Is_Attention_Me;
            private Object IntegralLevel;
            private Object ProjectID;
            private Object ProjectName;
            private Object Report_Days;

            public String getPhoneNum() {
                return PhoneNum;
            }

            public void setPhoneNum(String PhoneNum) {
                this.PhoneNum = PhoneNum;
            }

            public int getUserID() {
                return UserID;
            }

            public void setUserID(int UserID) {
                this.UserID = UserID;
            }

            public Object getNickName() {
                return NickName;
            }

            public void setNickName(Object NickName) {
                this.NickName = NickName;
            }

            public Object getProfilePicture() {
                return ProfilePicture;
            }

            public void setProfilePicture(Object ProfilePicture) {
                this.ProfilePicture = ProfilePicture;
            }

            public boolean isIs_Friend() {
                return Is_Friend;
            }

            public void setIs_Friend(boolean Is_Friend) {
                this.Is_Friend = Is_Friend;
            }

            public boolean isIs_Attention() {
                return Is_Attention;
            }

            public void setIs_Attention(boolean Is_Attention) {
                this.Is_Attention = Is_Attention;
            }

            public boolean isIs_Attention_Me() {
                return Is_Attention_Me;
            }

            public void setIs_Attention_Me(boolean Is_Attention_Me) {
                this.Is_Attention_Me = Is_Attention_Me;
            }

            public Object getIntegralLevel() {
                return IntegralLevel;
            }

            public void setIntegralLevel(Object IntegralLevel) {
                this.IntegralLevel = IntegralLevel;
            }

            public Object getProjectID() {
                return ProjectID;
            }

            public void setProjectID(Object ProjectID) {
                this.ProjectID = ProjectID;
            }

            public Object getProjectName() {
                return ProjectName;
            }

            public void setProjectName(Object ProjectName) {
                this.ProjectName = ProjectName;
            }

            public Object getReport_Days() {
                return Report_Days;
            }

            public void setReport_Days(Object Report_Days) {
                this.Report_Days = Report_Days;
            }
        }
    }
}
