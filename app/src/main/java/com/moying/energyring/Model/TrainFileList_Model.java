package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/4/23.
 */

public class TrainFileList_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : ["xw-20-3-3.mp3","xw-proname-3.mp3"]
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private List<String> Data;

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

    public List<String> getData() {
        return Data;
    }

    public void setData(List<String> Data) {
        this.Data = Data;
    }
}
