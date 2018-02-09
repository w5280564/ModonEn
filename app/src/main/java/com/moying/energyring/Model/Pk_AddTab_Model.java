package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2018/1/8.
 */

public class Pk_AddTab_Model {

    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"ProjectTypeID":-2,"ProjectTypeName":"推荐","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":-1,"ProjectTypeName":"自定义分类","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":1,"ProjectTypeName":"分类一","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":2,"ProjectTypeName":"分类二","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":3,"ProjectTypeName":"分类三","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":4,"ProjectTypeName":"粉蕾丝","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":28,"ProjectTypeName":"分类五","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":29,"ProjectTypeName":"分类六","_Project_List":null,"FileID":0,"FilePath":null},{"ProjectTypeID":-3,"ProjectTypeName":"最近","_Project_List":null,"FileID":0,"FilePath":null}]
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
         * ProjectTypeID : -2
         * ProjectTypeName : 推荐
         * _Project_List : null
         * FileID : 0
         * FilePath : null
         */

        private int ProjectTypeID;
        private String ProjectTypeName;
        private Object _Project_List;
        private int FileID;
        private Object FilePath;

        public int getProjectTypeID() {
            return ProjectTypeID;
        }

        public void setProjectTypeID(int ProjectTypeID) {
            this.ProjectTypeID = ProjectTypeID;
        }

        public String getProjectTypeName() {
            return ProjectTypeName;
        }

        public void setProjectTypeName(String ProjectTypeName) {
            this.ProjectTypeName = ProjectTypeName;
        }

        public Object get_Project_List() {
            return _Project_List;
        }

        public void set_Project_List(Object _Project_List) {
            this._Project_List = _Project_List;
        }

        public int getFileID() {
            return FileID;
        }

        public void setFileID(int FileID) {
            this.FileID = FileID;
        }

        public Object getFilePath() {
            return FilePath;
        }

        public void setFilePath(Object FilePath) {
            this.FilePath = FilePath;
        }
    }
}
