package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/5/26.
 */

public class AddPhoto_Model {

    /**
     * IsSuccess : true
     * Msg : 上传成功!
     * Status : 200
     * Data : [86,87,88]
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private List<Integer> Data;

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

    public List<Integer> getData() {
        return Data;
    }

    public void setData(List<Integer> Data) {
        this.Data = Data;
    }
}
