package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/5/9.
 */

public class BaseDataString_Model {


    /**
     * IsSuccess : true
     * Msg : xjbl
     * Status : 200
     *
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private String Data;

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

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
