package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2017/6/6.
 */

public class RadioList_Model implements Parcelable {



    private static final long serialVersionUID = 8565198351058235015L;

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"RadioID":1,"RadioName":"BBC","RadioUrl":"http://bbcwssc.ic.llnwd.net/stream/bbcwssc_mp1_ws-einws","Is_Disabled":false,"FileID_Icon":271,"FileID_Bg":286,"Radio_Icon":"http://172.16.0.111/Uploads/2017-06-06/a45ca361-8b9a-4c6e-89dd-8dd7f83e7bc8.jpg","Radio_Bg":"http://172.16.0.111/Uploads/2017-06-07/ee082015-c286-4f89-851f-43979404cc5e.png","FileID_Bg_Dim":285,"Radio_Bg_Dim":"http://172.16.0.111/Uploads/2017-06-07/332b4fa4-0394-4c4a-80e4-13b35241c679.png"}]
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
         * RadioID : 1
         * RadioName : BBC
         * RadioUrl : http://bbcwssc.ic.llnwd.net/stream/bbcwssc_mp1_ws-einws
         * Is_Disabled : false
         * FileID_Icon : 271
         * FileID_Bg : 286
         * Radio_Icon : http://172.16.0.111/Uploads/2017-06-06/a45ca361-8b9a-4c6e-89dd-8dd7f83e7bc8.jpg
         * Radio_Bg : http://172.16.0.111/Uploads/2017-06-07/ee082015-c286-4f89-851f-43979404cc5e.png
         * FileID_Bg_Dim : 285
         * Radio_Bg_Dim : http://172.16.0.111/Uploads/2017-06-07/332b4fa4-0394-4c4a-80e4-13b35241c679.png
         */

        private int RadioID;
        private String RadioName;
        private String RadioUrl;
        private boolean Is_Disabled;
        private int FileID_Icon;
        private int FileID_Bg;
        private String Radio_Icon;
        private String Radio_Bg;
        private int FileID_Bg_Dim;
        private String Radio_Bg_Dim;
        private boolean isPlay = false;

        public boolean getIsPlay() {
            return isPlay;
        }

        public void setIsPlay(boolean isPlay) {
            this.isPlay = isPlay;
        }


        public int getRadioID() {
            return RadioID;
        }

        public void setRadioID(int RadioID) {
            this.RadioID = RadioID;
        }

        public String getRadioName() {
            return RadioName;
        }

        public void setRadioName(String RadioName) {
            this.RadioName = RadioName;
        }

        public String getRadioUrl() {
            return RadioUrl;
        }

        public void setRadioUrl(String RadioUrl) {
            this.RadioUrl = RadioUrl;
        }

        public boolean isIs_Disabled() {
            return Is_Disabled;
        }

        public void setIs_Disabled(boolean Is_Disabled) {
            this.Is_Disabled = Is_Disabled;
        }

        public int getFileID_Icon() {
            return FileID_Icon;
        }

        public void setFileID_Icon(int FileID_Icon) {
            this.FileID_Icon = FileID_Icon;
        }

        public int getFileID_Bg() {
            return FileID_Bg;
        }

        public void setFileID_Bg(int FileID_Bg) {
            this.FileID_Bg = FileID_Bg;
        }

        public String getRadio_Icon() {
            return Radio_Icon;
        }

        public void setRadio_Icon(String Radio_Icon) {
            this.Radio_Icon = Radio_Icon;
        }

        public String getRadio_Bg() {
            return Radio_Bg;
        }

        public void setRadio_Bg(String Radio_Bg) {
            this.Radio_Bg = Radio_Bg;
        }

        public int getFileID_Bg_Dim() {
            return FileID_Bg_Dim;
        }

        public void setFileID_Bg_Dim(int FileID_Bg_Dim) {
            this.FileID_Bg_Dim = FileID_Bg_Dim;
        }

        public String getRadio_Bg_Dim() {
            return Radio_Bg_Dim;
        }

        public void setRadio_Bg_Dim(String Radio_Bg_Dim) {
            this.Radio_Bg_Dim = Radio_Bg_Dim;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.RadioID);
            dest.writeString(this.RadioName);
            dest.writeString(this.RadioUrl);
            dest.writeByte(this.Is_Disabled ? (byte) 1 : (byte) 0);
            dest.writeInt(this.FileID_Icon);
            dest.writeInt(this.FileID_Bg);
            dest.writeString(this.Radio_Icon);
            dest.writeString(this.Radio_Bg);
            dest.writeInt(this.FileID_Bg_Dim);
            dest.writeString(this.Radio_Bg_Dim);
            dest.writeByte(this.isPlay ? (byte) 1 : (byte) 0);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.RadioID = in.readInt();
            this.RadioName = in.readString();
            this.RadioUrl = in.readString();
            this.Is_Disabled = in.readByte() != 0;
            this.FileID_Icon = in.readInt();
            this.FileID_Bg = in.readInt();
            this.Radio_Icon = in.readString();
            this.Radio_Bg = in.readString();
            this.FileID_Bg_Dim = in.readInt();
            this.Radio_Bg_Dim = in.readString();
            this.isPlay = in.readByte() != 0;
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

    public RadioList_Model() {
    }

    protected RadioList_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<RadioList_Model> CREATOR = new Parcelable.Creator<RadioList_Model>() {
        @Override
        public RadioList_Model createFromParcel(Parcel source) {
            return new RadioList_Model(source);
        }

        @Override
        public RadioList_Model[] newArray(int size) {
            return new RadioList_Model[size];
        }
    };
}
