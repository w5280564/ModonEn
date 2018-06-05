package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waylen on 2017/6/12.
 */

public class UserInfo_Model implements Parcelable {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : {"UserID":1,"UserName":"徐卫","NickName":"Start丶小冰","LoginName":"15901857927","Pwd":null,"Birthday":"1992-09-25 00:00:00","Brief":"拉拉","Gender":2,"Role":0,"Integral":0,"RegTime":"2017-05-08 15:41:16","InviteCode":"111111","RefUserID":0,"Is_Lock":false,"Is_Recommend":true,"Is_Del":false,"ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0","CoverImg":null,"OpenID":null,"LoginType":0,"Email":"xuwei2144@live.cn","Code":0,"Attention":3,"Attention_Me":1}
     */

    public boolean IsSuccess;
    public String Msg;
    public int Status;
    public DataBean Data;


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

    public static class DataBean implements Parcelable {

        /**
         * UserID : 1
         * UserName : 徐卫
         * NickName : Start丶小冰
         * LoginName : 15901857927
         * Pwd : null
         * Birthday : 1992-09-25 00:00:00
         * Brief : 拉拉
         * Gender : 2
         * Role : 0
         * Integral : 0
         * RegTime : 2017-05-08 15:41:16
         * InviteCode : 111111
         * RefUserID : 0
         * Is_Lock : false
         * Is_Recommend : true
         * Is_Del : false
         * ProfilePicture : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0
         * CoverImg : null
         * OpenID : null
         * LoginType : 0
         * Email : xuwei2144@live.cn
         * Code : 0
         * Attention : 3
         * Attention_Me : 1
         * PostCount
         */

        private int UserID;
        private String UserName;
        private String NickName;
        private String LoginName;
        private Object Pwd;
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
        private Object CoverImg;
        private Object OpenID;
        private int LoginType;
        private String Email;
        private int Code;
        private int Attention;
        private int Attention_Me;
        private boolean Is_Attention;
        private int PostCount;

        public boolean is_Attention() {
            return Is_Attention;
        }

        public void setIs_Attention(boolean is_Attention) {
            Is_Attention = is_Attention;
        }


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

        public Object getPwd() {
            return Pwd;
        }

        public void setPwd(Object Pwd) {
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

        public Object getCoverImg() {
            return CoverImg;
        }

        public void setCoverImg(Object CoverImg) {
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

        public int getPostCount() {
            return PostCount;
        }

        public void setPostCount(int postCount) {
            PostCount = postCount;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.UserID);
            dest.writeString(this.UserName);
            dest.writeString(this.NickName);
            dest.writeString(this.LoginName);
            dest.writeString(String.valueOf(this.Pwd));
//            dest.writeParcelable(this.Pwd, flags);
            dest.writeString(this.Birthday);
            dest.writeString(this.Brief);
            dest.writeInt(this.Gender);
            dest.writeInt(this.Role);
            dest.writeInt(this.Integral);
            dest.writeString(this.RegTime);
            dest.writeString(this.InviteCode);
            dest.writeInt(this.RefUserID);
            dest.writeByte(this.Is_Lock ? (byte) 1 : (byte) 0);
            dest.writeByte(this.Is_Recommend ? (byte) 1 : (byte) 0);
            dest.writeByte(this.Is_Del ? (byte) 1 : (byte) 0);
            dest.writeString(this.ProfilePicture);
            dest.writeString(String.valueOf(this.CoverImg));
//            dest.writeParcelable(this.CoverImg, flags);
            dest.writeString(String.valueOf(this.OpenID));
//            dest.writeParcelable(this.OpenID, flags);
            dest.writeInt(this.LoginType);
            dest.writeString(this.Email);
            dest.writeInt(this.Code);
            dest.writeInt(this.Attention);
            dest.writeInt(this.Attention_Me);
            dest.writeInt(this.PostCount);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.UserID = in.readInt();
            this.UserName = in.readString();
            this.NickName = in.readString();
            this.LoginName = in.readString();
            this.Pwd = in.readString();
//            this.Pwd = in.readParcelable(Object.class.getClassLoader());
            this.Birthday = in.readString();
            this.Brief = in.readString();
            this.Gender = in.readInt();
            this.Role = in.readInt();
            this.Integral = in.readInt();
            this.RegTime = in.readString();
            this.InviteCode = in.readString();
            this.RefUserID = in.readInt();
            this.Is_Lock = in.readByte() != 0;
            this.Is_Recommend = in.readByte() != 0;
            this.Is_Del = in.readByte() != 0;
            this.ProfilePicture = in.readString();
            this.CoverImg = in.readString();
//            this.CoverImg = in.readParcelable(Object.class.getClassLoader());
            this.OpenID = in.readString();
//            this.OpenID = in.readParcelable(Object.class.getClassLoader());
            this.LoginType = in.readInt();
            this.Email = in.readString();
            this.Code = in.readInt();
            this.Attention = in.readInt();
            this.Attention_Me = in.readInt();
            this.PostCount = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.IsSuccess ? (byte) 1 : (byte) 0);
        dest.writeString(this.Msg);
        dest.writeInt(this.Status);
        dest.writeParcelable(this.Data,flags);
    }

    public UserInfo_Model() {
    }

    protected UserInfo_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserInfo_Model> CREATOR = new Parcelable.Creator<UserInfo_Model>() {

        @Override
        public UserInfo_Model createFromParcel(Parcel source) {
            boolean IsSuccess = source.readByte() !=0;
            String Msg = source.readString();
            int Status =source.readInt();
            DataBean data = source.readParcelable(DataBean.class.getClassLoader());

            UserInfo_Model userModel = new UserInfo_Model();
            userModel.IsSuccess = IsSuccess;
            userModel.Msg = Msg;
            userModel.Status = Status;
            userModel.Data = data;

//            return new UserInfo_Model(source);
            return userModel;
        }

        @Override
        public UserInfo_Model[] newArray(int size) {
            return new UserInfo_Model[size];
        }
    };
}
