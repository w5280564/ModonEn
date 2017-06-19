package com.moying.energyring.Model;

/**
 * Created by waylen on 2017/5/9.
 */

public class Base_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : true
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private boolean Data;

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

    public boolean isData() {
        return Data;
    }

    public void setData(boolean Data) {
        this.Data = Data;
    }
}
