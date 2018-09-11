package com.moying.energyring.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2018/6/29.
 */

public class Person_Photo_Model implements Parcelable {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"RowsNum":1,"FilePath":"http://172.16.0.222/Uploads/2018-07-13/305bb25f-e808-4b15-ac90-a30be035b65a.jpg","PostID":51965,"Likes":1,"CommentNum":0,"CreateTime":"2018-07-13 13:51:22","PostContent":"知道知道知道知道知道知道知道知道","Is_Like":1},{"RowsNum":2,"FilePath":"http://172.16.0.222/Uploads/2018-06-22/1f788cf3-8a39-4b35-a3e0-65e9316bf67d.jpg","PostID":51963,"Likes":0,"CommentNum":0,"CreateTime":"2018-06-22 15:51:07","PostContent":"您哦市民婆婆哦前三名哦您","Is_Like":0},{"RowsNum":3,"FilePath":"http://120.26.218.68:1111/Uploads/2018-06-10/460740d3-b130-4aeb-89bf-910f6c8430cf.jpg","PostID":51687,"Likes":0,"CommentNum":0,"CreateTime":"2018-06-10 10:22:05","PostContent":"早","Is_Like":0},{"RowsNum":4,"FilePath":"http://120.26.218.68:1111/Uploads/2018-06-01/514fd597-743a-4f9d-b3f7-80e30202a106.jpg","PostID":50820,"Likes":1,"CommentNum":0,"CreateTime":"2018-06-01 15:37:09","PostContent":"今日已完成健康走55步，","Is_Like":0},{"RowsNum":5,"FilePath":"http://120.26.218.68:1111/Uploads/2018-01-22/4c7d4073-0e12-4951-9bf5-0ca13a31af85.jpg","PostID":34794,"Likes":1,"CommentNum":0,"CreateTime":"2018-01-22 13:39:24","PostContent":"今日已完成俯卧撑10个，","Is_Like":0},{"RowsNum":6,"FilePath":"http://120.26.218.68:1111/Uploads/2018-01-22/4c7d4073-0e12-4951-9bf5-0ca13a31af85.jpg","PostID":34793,"Likes":1,"CommentNum":0,"CreateTime":"2018-01-22 13:22:39","PostContent":"【摩英 王丰蜕变之旅2018-01-22】我刚才完成了俯卧撑10个,欢迎到每日PK来挑战我！\n","Is_Like":0},{"RowsNum":7,"FilePath":"http://120.26.218.68:1111/Uploads/2018-01-03/52e8b4f5-3447-4648-a894-a9536a6f6a10.jpg","PostID":33768,"Likes":1,"CommentNum":0,"CreateTime":"2018-01-03 13:59:11","PostContent":"你好","Is_Like":0},{"RowsNum":8,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-30/f41f3bb6-8ffa-4ed5-bee5-2d1d942da8bb.jpg","PostID":33622,"Likes":1,"CommentNum":0,"CreateTime":"2017-12-30 23:43:17","PostContent":"【摩英 王丰蜕变之旅2017-12-30】我刚才完成了深蹲40个,欢迎到每日PK来挑战我！\n","Is_Like":0},{"RowsNum":9,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-30/39648208-de4f-4dd8-9ad1-6c87957182ee.jpg","PostID":33621,"Likes":0,"CommentNum":0,"CreateTime":"2017-12-30 23:43:02","PostContent":"今日已完成健康走0步，深蹲20个，跑步2公里，","Is_Like":0},{"RowsNum":10,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-30/41663870-0592-45cc-a0de-aa81a9b235e7.jpg","PostID":33620,"Likes":0,"CommentNum":0,"CreateTime":"2017-12-30 23:42:19","PostContent":"13343464","Is_Like":0},{"RowsNum":11,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-30/39648208-de4f-4dd8-9ad1-6c87957182ee.jpg","PostID":33619,"Likes":1,"CommentNum":0,"CreateTime":"2017-12-30 23:41:53","PostContent":"今日已完成健康走0步，深蹲20个，跑步2公里，","Is_Like":0},{"RowsNum":12,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-30/39648208-de4f-4dd8-9ad1-6c87957182ee.jpg","PostID":33611,"Likes":0,"CommentNum":0,"CreateTime":"2017-12-30 23:32:58","PostContent":"【摩英 王丰蜕变之旅2017-12-30】我刚才完成了深蹲20个,欢迎到每日PK来挑战我！\n","Is_Like":0},{"RowsNum":13,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-15/57c4c7bd-e8f3-4c5b-80a5-e9085a5c7f34.jpg","PostID":32751,"Likes":0,"CommentNum":0,"CreateTime":"2017-12-15 21:41:53","PostContent":"今日已完成深蹲45个，健康走0步，","Is_Like":0},{"RowsNum":14,"FilePath":"http://120.26.218.68:1111/Uploads/2017-12-15/57c4c7bd-e8f3-4c5b-80a5-e9085a5c7f34.jpg","PostID":32749,"Likes":0,"CommentNum":0,"CreateTime":"2017-12-15 21:41:37","PostContent":"【摩英 王丰蜕变之旅2017-12-15】我刚才完成了深蹲1个,欢迎到每日PK来挑战我！\n","Is_Like":0},{"RowsNum":15,"FilePath":"http://120.26.218.68:1111/Uploads/2017-10-17/f457929d-5b07-4fcc-bfb1-24e29a95e059.jpg","PostID":29460,"Likes":0,"CommentNum":0,"CreateTime":"2017-10-17 16:18:58","PostContent":"hhh","Is_Like":0}]
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
         * RowsNum : 1
         * FilePath : http://172.16.0.222/Uploads/2018-07-13/305bb25f-e808-4b15-ac90-a30be035b65a.jpg
         * PostID : 51965
         * Likes : 1
         * CommentNum : 0
         * CreateTime : 2018-07-13 13:51:22
         * PostContent : 知道知道知道知道知道知道知道知道
         * Is_Like : 1
         */

        private int RowsNum;
        private String FilePath;
        private int PostID;
        private int Likes;
        private int CommentNum;
        private String CreateTime;
        private String PostContent;
        private int Is_Like;

        public int getRowsNum() {
            return RowsNum;
        }

        public void setRowsNum(int RowsNum) {
            this.RowsNum = RowsNum;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public int getPostID() {
            return PostID;
        }

        public void setPostID(int PostID) {
            this.PostID = PostID;
        }

        public int getLikes() {
            return Likes;
        }

        public void setLikes(int Likes) {
            this.Likes = Likes;
        }

        public int getCommentNum() {
            return CommentNum;
        }

        public void setCommentNum(int CommentNum) {
            this.CommentNum = CommentNum;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getPostContent() {
            return PostContent;
        }

        public void setPostContent(String PostContent) {
            this.PostContent = PostContent;
        }

        public int getIs_Like() {
            return Is_Like;
        }

        public void setIs_Like(int Is_Like) {
            this.Is_Like = Is_Like;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.RowsNum);
            dest.writeString(this.FilePath);
            dest.writeInt(this.PostID);
            dest.writeInt(this.Likes);
            dest.writeInt(this.CommentNum);
            dest.writeString(this.CreateTime);
            dest.writeString(this.PostContent);
            dest.writeInt(this.Is_Like);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.RowsNum = in.readInt();
            this.FilePath = in.readString();
            this.PostID = in.readInt();
            this.Likes = in.readInt();
            this.CommentNum = in.readInt();
            this.CreateTime = in.readString();
            this.PostContent = in.readString();
            this.Is_Like = in.readInt();
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

    public Person_Photo_Model() {
    }

    protected Person_Photo_Model(Parcel in) {
        this.IsSuccess = in.readByte() != 0;
        this.Msg = in.readString();
        this.Status = in.readInt();
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Person_Photo_Model> CREATOR = new Parcelable.Creator<Person_Photo_Model>() {
        @Override
        public Person_Photo_Model createFromParcel(Parcel source) {
            return new Person_Photo_Model(source);
        }

        @Override
        public Person_Photo_Model[] newArray(int size) {
            return new Person_Photo_Model[size];
        }
    };
}
