package com.moying.energyring.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by waylen on 2017/6/6.
 */

public class RadioList_Model implements Serializable{

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

    public static class DataBean {
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
    }
}
