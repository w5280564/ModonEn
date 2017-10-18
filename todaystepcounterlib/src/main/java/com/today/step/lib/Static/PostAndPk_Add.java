package com.today.step.lib.Static;

/**
 * Created by waylen on 2017/7/21.
 */

public class PostAndPk_Add {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : 49979,0
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
