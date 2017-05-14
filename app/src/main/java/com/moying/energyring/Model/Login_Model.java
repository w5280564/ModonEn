package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/5/9.
 */

public class Login_Model{

    /**
     * IsSuccess : true
     * Msg : 登录成功！
     * Status : 200
     * Data : {"UserID":3,"UserName":null,"NickName":null,"LoginName":"18721666525","Pwd":"","Birthday":null,"Brief":null,"Gender":0,"Role":3,"Integral":0,"RegTime":"2017-05-09 20:05:08","InviteCode":null,"RefUserID":0,"IsLock":false,"ProfilePicture":null,"OpenID":null,"LoginType":0,"Email":null,"Ticket":"37391BC48BEC456B3E26F26F4044B6DEBABBEB5850DC5B35A9D58F2374000656109939FF1D55C5340B045D2C8AE4DEF782F4A0AEA77270C802F8DE23079FA49D964205047A5A81C68F027692BC7598A030CFE6AFD6CA351A453120635E4341F00542A090D647F755E426F105D9DD0E566CC5AC3E66C6F1DD3404ADEDA7B8E30E","Code":0}
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
         * UserID : 3
         * UserName : null
         * NickName : null
         * LoginName : 18721666525
         * Pwd :
         * Birthday : null
         * Brief : null
         * Gender : 0
         * Role : 3
         * Integral : 0
         * RegTime : 2017-05-09 20:05:08
         * InviteCode : null
         * RefUserID : 0
         * IsLock : false
         * ProfilePicture : null
         * OpenID : null
         * LoginType : 0
         * Email : null
         * Ticket : 37391BC48BEC456B3E26F26F4044B6DEBABBEB5850DC5B35A9D58F2374000656109939FF1D55C5340B045D2C8AE4DEF782F4A0AEA77270C802F8DE23079FA49D964205047A5A81C68F027692BC7598A030CFE6AFD6CA351A453120635E4341F00542A090D647F755E426F105D9DD0E566CC5AC3E66C6F1DD3404ADEDA7B8E30E
         * Code : 0
         */

        private int UserID;
        private Object UserName;
        private Object NickName;
        private String LoginName;
        private String Pwd;
        private Object Birthday;
        private Object Brief;
        private int Gender;
        private int Role;
        private int Integral;
        private String RegTime;
        private Object InviteCode;
        private int RefUserID;
        private boolean IsLock;
        private Object ProfilePicture;
        private Object OpenID;
        private int LoginType;
        private Object Email;
        private String Ticket;
        private int Code;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public Object getUserName() {
            return UserName;
        }

        public void setUserName(Object UserName) {
            this.UserName = UserName;
        }

        public Object getNickName() {
            return NickName;
        }

        public void setNickName(Object NickName) {
            this.NickName = NickName;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public String getPwd() {
            return Pwd;
        }

        public void setPwd(String Pwd) {
            this.Pwd = Pwd;
        }

        public Object getBirthday() {
            return Birthday;
        }

        public void setBirthday(Object Birthday) {
            this.Birthday = Birthday;
        }

        public Object getBrief() {
            return Brief;
        }

        public void setBrief(Object Brief) {
            this.Brief = Brief;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public int getRole() {
            return Role;
        }

        public void setRole(int Role) {
            this.Role = Role;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public String getRegTime() {
            return RegTime;
        }

        public void setRegTime(String RegTime) {
            this.RegTime = RegTime;
        }

        public Object getInviteCode() {
            return InviteCode;
        }

        public void setInviteCode(Object InviteCode) {
            this.InviteCode = InviteCode;
        }

        public int getRefUserID() {
            return RefUserID;
        }

        public void setRefUserID(int RefUserID) {
            this.RefUserID = RefUserID;
        }

        public boolean isIsLock() {
            return IsLock;
        }

        public void setIsLock(boolean IsLock) {
            this.IsLock = IsLock;
        }

        public Object getProfilePicture() {
            return ProfilePicture;
        }

        public void setProfilePicture(Object ProfilePicture) {
            this.ProfilePicture = ProfilePicture;
        }

        public Object getOpenID() {
            return OpenID;
        }

        public void setOpenID(Object OpenID) {
            this.OpenID = OpenID;
        }

        public int getLoginType() {
            return LoginType;
        }

        public void setLoginType(int LoginType) {
            this.LoginType = LoginType;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public String getTicket() {
            return Ticket;
        }

        public void setTicket(String Ticket) {
            this.Ticket = Ticket;
        }

        public int getCode() {
            return Code;
        }

        public void setCode(int Code) {
            this.Code = Code;
        }
    }




}
