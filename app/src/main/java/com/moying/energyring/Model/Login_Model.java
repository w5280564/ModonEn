package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/5/9.
 */

public class Login_Model{


    /**
     * IsSuccess : true
     * Msg : 登录成功！
     * Status : 200
     * Data : {"UserID":3,"UserName":"你好","NickName":"Start丶小夏","LoginName":"18721666525","Pwd":"","Birthday":"1991-11-14 00:00:00","Brief":"开心就好","Gender":1,"Role":3,"Integral":66718,"RegTime":"2017-05-09 20:05:08","InviteCode":"333333","RefUserID":0,"Is_Lock":false,"Is_Recommend":true,"Is_Del":false,"ProfilePicture":"http://172.16.0.111/Uploads/2017-06-28/dfd12c70-46d4-4a29-b1f6-9224a463c54c.jpg","CoverImg":"http://172.16.0.111/Uploads/2017-06-28/ecd35659-f850-4167-88f2-7106fd05deee.jpg","OpenID":null,"LoginType":0,"Email":"waylen1009@163.com","Code":0,"Attention":0,"Attention_Me":0,"Is_Attention":false}
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
         * UserName : 你好
         * NickName : Start丶小夏
         * LoginName : 18721666525
         * Pwd :
         * Birthday : 1991-11-14 00:00:00
         * Brief : 开心就好
         * Gender : 1
         * Role : 3
         * Integral : 66718
         * RegTime : 2017-05-09 20:05:08
         * InviteCode : 333333
         * RefUserID : 0
         * Is_Lock : false
         * Is_Recommend : true
         * Is_Del : false
         * ProfilePicture : http://172.16.0.111/Uploads/2017-06-28/dfd12c70-46d4-4a29-b1f6-9224a463c54c.jpg
         * CoverImg : http://172.16.0.111/Uploads/2017-06-28/ecd35659-f850-4167-88f2-7106fd05deee.jpg
         * OpenID : null
         * LoginType : 0
         * Email : waylen1009@163.com
         * Code : 0
         * Attention : 0
         * Attention_Me : 0
         * Is_Attention : false
         */

        private int UserID;
        private String UserName;
        private String NickName;
        private String LoginName;
        private String Pwd;
        private String Birthday;
        private String Brief;
        private int Gender;
        private int Role;
        private int Integral;
        private String RegTime;
        private String InviteCode;
        private int RefUserID;
        private boolean Is_Lock;
        private boolean Is_Recommend;
        private boolean Is_Del;
        private String ProfilePicture;
        private String CoverImg;
        private Object OpenID;
        private int LoginType;
        private String Email;
        private int Code;
        private int Attention;
        private int Attention_Me;
        private boolean Is_Attention;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
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

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getBrief() {
            return Brief;
        }

        public void setBrief(String Brief) {
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

        public String getInviteCode() {
            return InviteCode;
        }

        public void setInviteCode(String InviteCode) {
            this.InviteCode = InviteCode;
        }

        public int getRefUserID() {
            return RefUserID;
        }

        public void setRefUserID(int RefUserID) {
            this.RefUserID = RefUserID;
        }

        public boolean isIs_Lock() {
            return Is_Lock;
        }

        public void setIs_Lock(boolean Is_Lock) {
            this.Is_Lock = Is_Lock;
        }

        public boolean isIs_Recommend() {
            return Is_Recommend;
        }

        public void setIs_Recommend(boolean Is_Recommend) {
            this.Is_Recommend = Is_Recommend;
        }

        public boolean isIs_Del() {
            return Is_Del;
        }

        public void setIs_Del(boolean Is_Del) {
            this.Is_Del = Is_Del;
        }

        public String getProfilePicture() {
            return ProfilePicture;
        }

        public void setProfilePicture(String ProfilePicture) {
            this.ProfilePicture = ProfilePicture;
        }

        public String getCoverImg() {
            return CoverImg;
        }

        public void setCoverImg(String CoverImg) {
            this.CoverImg = CoverImg;
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

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public int getCode() {
            return Code;
        }

        public void setCode(int Code) {
            this.Code = Code;
        }

        public int getAttention() {
            return Attention;
        }

        public void setAttention(int Attention) {
            this.Attention = Attention;
        }

        public int getAttention_Me() {
            return Attention_Me;
        }

        public void setAttention_Me(int Attention_Me) {
            this.Attention_Me = Attention_Me;
        }

        public boolean isIs_Attention() {
            return Is_Attention;
        }

        public void setIs_Attention(boolean Is_Attention) {
            this.Is_Attention = Is_Attention;
        }
    }
}
