package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2018/5/18.
 */

public class Person_Exchange_Model implements Parcelable {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ExChangeID":136,"UserID":1421,"ProductID":13,"CreateTime":"2018-05-11 16:11:09","OrderState":1,"OrderCode":"EC20180511161109","ExpressCompany":null,"ExpressCode":null,"ExpressState":1,"Receiver":"哦哦哦","ReceivePhone":"13333333333","ReceiveAddress":"中南美洲妈妈","Integral":2000,"ProductName":"能量鼠标垫","FilePath":"http://120.26.218.68:8040/uploads/i/2016/05/93b60c8b-1e7a-4749-83cc-8b147a0a1e6b.jpg","NickName":"摩英iOS开发","LoginName":"15021615315"},{"ExChangeID":135,"UserID":10307,"ProductID":19,"CreateTime":"2018-04-19 20:58:53","OrderState":1,"OrderCode":"EC20180419205853","ExpressCompany":null,"ExpressCode":null,"ExpressState":1,"Receiver":"林庆蕙","ReceivePhone":"13858857834","ReceiveAddress":"浙江省温州市瓯海区南白象街道新竹家园2栋三单元202室","Integral":10000,"ProductName":"摩英能量录音笔","FilePath":"http://120.26.218.68:1111/Uploads/2017-10-24/b4a62879-59af-413c-9944-ee159a324b70.jpg","NickName":"小公举","LoginName":null},{"ExChangeID":134,"UserID":10122,"ProductID":17,"CreateTime":"2018-04-09 19:18:55","OrderState":2,"OrderCode":"EC20180409191855","ExpressCompany":"顺丰速运","ExpressCode":"880830371706","ExpressState":2,"Receiver":"胡云霞","ReceivePhone":"15925962780","ReceiveAddress":"东阳市时代雅居X区一栋一单元1501","Integral":5000,"ProductName":"摩英能量水壶","FilePath":"http://120.26.218.68:1111/Uploads/2017-10-24/bf759ed1-d5f0-484f-858d-839e9e493579.jpg","NickName":"98期B1营赵泽鑫","LoginName":null},{"ExChangeID":133,"UserID":9947,"ProductID":17,"CreateTime":"2018-04-03 22:08:06","OrderState":2,"OrderCode":"EC20180403220806","ExpressCompany":"顺丰速运","ExpressCode":"880830371715","ExpressState":2,"Receiver":"陈宁旭","ReceivePhone":"13814052744","ReceiveAddress":"南京市鼓楼区江滨新寓50号102室","Integral":5000,"ProductName":"摩英能量水壶","FilePath":"http://120.26.218.68:1111/Uploads/2017-10-24/bf759ed1-d5f0-484f-858d-839e9e493579.jpg","NickName":"98期D2陈宁旭","LoginName":null},{"ExChangeID":132,"UserID":9627,"ProductID":9,"CreateTime":"2018-03-28 22:08:32","OrderState":2,"OrderCode":"EC20180328220832","ExpressCompany":"顺丰速运","ExpressCode":"880537331184","ExpressState":2,"Receiver":"林文轩","ReceivePhone":"13858857834","ReceiveAddress":"浙江省温州市瓯海区南白象街道新竹家园2栋3单元202室","Integral":3000,"ProductName":"摩英能量手环全套","FilePath":"http://120.26.218.68:8040/uploads/i/2016/05/98107c18-3a96-438a-bd55-c3944e0401f2.png","NickName":"温州分校首届B2林文轩","LoginName":null},{"ExChangeID":131,"UserID":10122,"ProductID":17,"CreateTime":"2018-03-27 22:05:10","OrderState":2,"OrderCode":"EC20180327220510","ExpressCompany":"顺丰速运","ExpressCode":"880440634552","ExpressState":2,"Receiver":"赵泽鑫","ReceivePhone":"15925962780","ReceiveAddress":"东阳市时代雅居北门C区一栋一单元1501","Integral":5000,"ProductName":"摩英能量水壶","FilePath":"http://120.26.218.68:1111/Uploads/2017-10-24/bf759ed1-d5f0-484f-858d-839e9e493579.jpg","NickName":"98期B1营赵泽鑫","LoginName":null},{"ExChangeID":130,"UserID":10167,"ProductID":14,"CreateTime":"2018-03-24 19:51:47","OrderState":2,"OrderCode":"EC20180324195147","ExpressCompany":"顺丰速运","ExpressCode":"880440634561","ExpressState":2,"Receiver":"吴春乐","ReceivePhone":"15372826821","ReceiveAddress":"浙江省温州市苍南县龙港镇沿河北路206","Integral":2000,"ProductName":"能量签字笔(颜色随机)","FilePath":"http://120.26.218.68:8040/uploads/i/2016/05/1571513f-ca45-4059-8ae0-9636f99110a9.jpg","NickName":"摩英温州分校第一届","LoginName":"15372826821"},{"ExChangeID":129,"UserID":10167,"ProductID":14,"CreateTime":"2018-03-24 19:51:45","OrderState":2,"OrderCode":"EC20180324195145","ExpressCompany":"顺丰速运","ExpressCode":"880440634561","ExpressState":2,"Receiver":"吴春乐","ReceivePhone":"15372826821","ReceiveAddress":"浙江省温州市苍南县龙港镇沿河北路206","Integral":2000,"ProductName":"能量签字笔(颜色随机)","FilePath":"http://120.26.218.68:8040/uploads/i/2016/05/1571513f-ca45-4059-8ae0-9636f99110a9.jpg","NickName":"摩英温州分校第一届","LoginName":"15372826821"},{"ExChangeID":128,"UserID":10167,"ProductID":14,"CreateTime":"2018-03-24 19:51:34","OrderState":2,"OrderCode":"EC20180324195134","ExpressCompany":"顺丰速运","ExpressCode":"880440634561","ExpressState":2,"Receiver":"吴春乐","ReceivePhone":"15372826821","ReceiveAddress":"浙江省温州市苍南县龙港镇沿河北路206","Integral":2000,"ProductName":"能量签字笔(颜色随机)","FilePath":"http://120.26.218.68:8040/uploads/i/2016/05/1571513f-ca45-4059-8ae0-9636f99110a9.jpg","NickName":"摩英温州分校第一届","LoginName":"15372826821"},{"ExChangeID":127,"UserID":10167,"ProductID":14,"CreateTime":"2018-03-24 19:51:33","OrderState":2,"OrderCode":"EC20180324195133","ExpressCompany":"顺丰速运","ExpressCode":"880440634561","ExpressState":2,"Receiver":"吴春乐","ReceivePhone":"15372826821","ReceiveAddress":"浙江省温州市苍南县龙港镇沿河北路206","Integral":2000,"ProductName":"能量签字笔(颜色随机)","FilePath":"http://120.26.218.68:8040/uploads/i/2016/05/1571513f-ca45-4059-8ae0-9636f99110a9.jpg","NickName":"摩英温州分校第一届","LoginName":"15372826821"}]
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

    public static class DataBean implements Parcelable {
        /**
         * ExChangeID : 136
         * UserID : 1421
         * ProductID : 13
         * CreateTime : 2018-05-11 16:11:09
         * OrderState : 1
         * OrderCode : EC20180511161109
         * ExpressCompany : null
         * ExpressCode : null
         * ExpressState : 1
         * Receiver : 哦哦哦
         * ReceivePhone : 13333333333
         * ReceiveAddress : 中南美洲妈妈
         * Integral : 2000
         * ProductName : 能量鼠标垫
         * FilePath : http://120.26.218.68:8040/uploads/i/2016/05/93b60c8b-1e7a-4749-83cc-8b147a0a1e6b.jpg
         * NickName : 摩英iOS开发
         * LoginName : 15021615315
         */

        private int ExChangeID;
        private int UserID;
        private int ProductID;
        private String CreateTime;
        private int OrderState;
        private String OrderCode;
        private String ExpressCompany;
        private String ExpressCode;
        private int ExpressState;
        private String Receiver;
        private String ReceivePhone;
        private String ReceiveAddress;
        private int Integral;
        private String ProductName;
        private String FilePath;
        private String NickName;
        private String LoginName;

        public int getExChangeID() {
            return ExChangeID;
        }

        public void setExChangeID(int ExChangeID) {
            this.ExChangeID = ExChangeID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getProductID() {
            return ProductID;
        }

        public void setProductID(int ProductID) {
            this.ProductID = ProductID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getOrderState() {
            return OrderState;
        }

        public void setOrderState(int OrderState) {
            this.OrderState = OrderState;
        }

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public String  getExpressCompany() {
            return ExpressCompany;
        }

        public void setExpressCompany(String ExpressCompany) {
            this.ExpressCompany = ExpressCompany;
        }

        public String getExpressCode() {
            return ExpressCode;
        }

        public void setExpressCode(String ExpressCode) {
            this.ExpressCode = ExpressCode;
        }

        public int getExpressState() {
            return ExpressState;
        }

        public void setExpressState(int ExpressState) {
            this.ExpressState = ExpressState;
        }

        public String getReceiver() {
            return Receiver;
        }

        public void setReceiver(String Receiver) {
            this.Receiver = Receiver;
        }

        public String getReceivePhone() {
            return ReceivePhone;
        }

        public void setReceivePhone(String ReceivePhone) {
            this.ReceivePhone = ReceivePhone;
        }

        public String getReceiveAddress() {
            return ReceiveAddress;
        }

        public void setReceiveAddress(String ReceiveAddress) {
            this.ReceiveAddress = ReceiveAddress;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.ExChangeID);
            dest.writeInt(this.UserID);
            dest.writeInt(this.ProductID);
            dest.writeString(this.CreateTime);
            dest.writeInt(this.OrderState);
            dest.writeString(this.OrderCode);
            dest.writeString(this.ExpressCompany);
            dest.writeString(this.ExpressCode);
            dest.writeInt(this.ExpressState);
            dest.writeString(this.Receiver);
            dest.writeString(this.ReceivePhone);
            dest.writeString(this.ReceiveAddress);
            dest.writeInt(this.Integral);
            dest.writeString(this.ProductName);
            dest.writeString(this.FilePath);
            dest.writeString(this.NickName);
            dest.writeString(this.LoginName);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.ExChangeID = in.readInt();
            this.UserID = in.readInt();
            this.ProductID = in.readInt();
            this.CreateTime = in.readString();
            this.OrderState = in.readInt();
            this.OrderCode = in.readString();
            this.ExpressCompany = in.readString();
            this.ExpressCode = in.readString();
            this.ExpressState = in.readInt();
            this.Receiver = in.readString();
            this.ReceivePhone = in.readString();
            this.ReceiveAddress = in.readString();
            this.Integral = in.readInt();
            this.ProductName = in.readString();
            this.FilePath = in.readString();
            this.NickName = in.readString();
            this.LoginName = in.readString();
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
        dest.writeList(this.Data);
    }

    public Person_Exchange_Model() {
    }

    protected Person_Exchange_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Person_Exchange_Model> CREATOR = new Parcelable.Creator<Person_Exchange_Model>() {
        @Override
        public Person_Exchange_Model createFromParcel(Parcel source) {
            return new Person_Exchange_Model(source);
        }

        @Override
        public Person_Exchange_Model[] newArray(int size) {
            return new Person_Exchange_Model[size];
        }
    };
}
