package com.moying.energyring.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by waylen on 2017/4/5.
 */

public class BadgeList_Model implements Serializable{

    /**
     * IsSuccess : true
     * Msg : 操作成功!
     * Status : 200
     * Data : {"MyBadgeNum":9,"_Badge":[{"BadgeID":25,"BadgeName":"累计30天徽章","BadgeDays":30,"BadgeType":1,"Is_Have":true,"HaveNum":137,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/884a1565-9ef5-4215-925b-b3a1e6ffae00.png","FileID_Gray":55969,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/48168b34-7811-4f97-a217-3999229e44e3.png"},{"BadgeID":26,"BadgeName":"累计50天徽章","BadgeDays":50,"BadgeType":1,"Is_Have":true,"HaveNum":63,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/e142932a-e92c-4764-9715-678015335892.png","FileID_Gray":55971,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/7c86933a-afba-4a0e-a589-1eada3cb6e6d.png"},{"BadgeID":27,"BadgeName":"累计100天徽章","BadgeDays":100,"BadgeType":1,"Is_Have":false,"HaveNum":23,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/4c3f42f5-5780-47af-9754-4c43f4a7e26f.png","FileID_Gray":55975,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/05e85810-5730-49b9-94bf-03059f7dfe68.png"},{"BadgeID":28,"BadgeName":"累计150天徽章","BadgeDays":150,"BadgeType":1,"Is_Have":false,"HaveNum":9,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/37921d3f-6099-4890-9b4a-3da1f1a40f3f.png","FileID_Gray":55977,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/9f22e88f-98e7-41a1-8278-1cb2781a5e41.png"},{"BadgeID":29,"BadgeName":"累计200天徽章","BadgeDays":200,"BadgeType":1,"Is_Have":false,"HaveNum":4,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/f7864ab4-7d64-4e4e-9c9b-0311f3c27709.png","FileID_Gray":55979,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/73a7fb2b-29ad-46d7-94ac-c29617c34a98.png"},{"BadgeID":30,"BadgeName":"累计260天徽章","BadgeDays":260,"BadgeType":1,"Is_Have":false,"HaveNum":3,"FileID":0,"FilePath":null,"FileID_Gray":0,"FilePath_Gray":null},{"BadgeID":31,"BadgeName":"累计300天徽章","BadgeDays":300,"BadgeType":1,"Is_Have":false,"HaveNum":3,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/871b79f1-8fee-4230-abd1-035c6a24f758.png","FileID_Gray":55981,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/c7749c1b-6930-49a6-8ac2-cff1eb05d563.png"}]}
     */

    private boolean IsSuccess;
    private String Msg;
    private int Status;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * MyBadgeNum : 9
         * _Badge : [{"BadgeID":25,"BadgeName":"累计30天徽章","BadgeDays":30,"BadgeType":1,"Is_Have":true,"HaveNum":137,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/884a1565-9ef5-4215-925b-b3a1e6ffae00.png","FileID_Gray":55969,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/48168b34-7811-4f97-a217-3999229e44e3.png"},{"BadgeID":26,"BadgeName":"累计50天徽章","BadgeDays":50,"BadgeType":1,"Is_Have":true,"HaveNum":63,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/e142932a-e92c-4764-9715-678015335892.png","FileID_Gray":55971,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/7c86933a-afba-4a0e-a589-1eada3cb6e6d.png"},{"BadgeID":27,"BadgeName":"累计100天徽章","BadgeDays":100,"BadgeType":1,"Is_Have":false,"HaveNum":23,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/4c3f42f5-5780-47af-9754-4c43f4a7e26f.png","FileID_Gray":55975,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/05e85810-5730-49b9-94bf-03059f7dfe68.png"},{"BadgeID":28,"BadgeName":"累计150天徽章","BadgeDays":150,"BadgeType":1,"Is_Have":false,"HaveNum":9,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/37921d3f-6099-4890-9b4a-3da1f1a40f3f.png","FileID_Gray":55977,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/9f22e88f-98e7-41a1-8278-1cb2781a5e41.png"},{"BadgeID":29,"BadgeName":"累计200天徽章","BadgeDays":200,"BadgeType":1,"Is_Have":false,"HaveNum":4,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/f7864ab4-7d64-4e4e-9c9b-0311f3c27709.png","FileID_Gray":55979,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/73a7fb2b-29ad-46d7-94ac-c29617c34a98.png"},{"BadgeID":30,"BadgeName":"累计260天徽章","BadgeDays":260,"BadgeType":1,"Is_Have":false,"HaveNum":3,"FileID":0,"FilePath":null,"FileID_Gray":0,"FilePath_Gray":null},{"BadgeID":31,"BadgeName":"累计300天徽章","BadgeDays":300,"BadgeType":1,"Is_Have":false,"HaveNum":3,"FileID":0,"FilePath":"http://172.16.0.111/Uploads/2017-11-27/871b79f1-8fee-4230-abd1-035c6a24f758.png","FileID_Gray":55981,"FilePath_Gray":"http://172.16.0.111/Uploads/2017-11-27/c7749c1b-6930-49a6-8ac2-cff1eb05d563.png"}]
         */

        private int MyBadgeNum;
        private List<BadgeBean> _Badge;

        public int getMyBadgeNum() {
            return MyBadgeNum;
        }

        public void setMyBadgeNum(int MyBadgeNum) {
            this.MyBadgeNum = MyBadgeNum;
        }

        public List<BadgeBean> get_Badge() {
            return _Badge;
        }

        public void set_Badge(List<BadgeBean> _Badge) {
            this._Badge = _Badge;
        }

        public static class BadgeBean {
            /**
             * BadgeID : 25
             * BadgeName : 累计30天徽章
             * BadgeDays : 30
             * BadgeType : 1
             * Is_Have : true
             * HaveNum : 137
             * FileID : 0
             * FilePath : http://172.16.0.111/Uploads/2017-11-27/884a1565-9ef5-4215-925b-b3a1e6ffae00.png
             * FileID_Gray : 55969
             * FilePath_Gray : http://172.16.0.111/Uploads/2017-11-27/48168b34-7811-4f97-a217-3999229e44e3.png
             */

            private int BadgeID;
            private String BadgeName;
            private int BadgeDays;
            private int BadgeType;
            private boolean Is_Have;
            private int HaveNum;
            private int FileID;
            private String FilePath;
            private int FileID_Gray;
            private String FilePath_Gray;

            public int getBadgeID() {
                return BadgeID;
            }

            public void setBadgeID(int BadgeID) {
                this.BadgeID = BadgeID;
            }

            public String getBadgeName() {
                return BadgeName;
            }

            public void setBadgeName(String BadgeName) {
                this.BadgeName = BadgeName;
            }

            public int getBadgeDays() {
                return BadgeDays;
            }

            public void setBadgeDays(int BadgeDays) {
                this.BadgeDays = BadgeDays;
            }

            public int getBadgeType() {
                return BadgeType;
            }

            public void setBadgeType(int BadgeType) {
                this.BadgeType = BadgeType;
            }

            public boolean isIs_Have() {
                return Is_Have;
            }

            public void setIs_Have(boolean Is_Have) {
                this.Is_Have = Is_Have;
            }

            public int getHaveNum() {
                return HaveNum;
            }

            public void setHaveNum(int HaveNum) {
                this.HaveNum = HaveNum;
            }

            public int getFileID() {
                return FileID;
            }

            public void setFileID(int FileID) {
                this.FileID = FileID;
            }

            public String getFilePath() {
                return FilePath;
            }

            public void setFilePath(String FilePath) {
                this.FilePath = FilePath;
            }

            public int getFileID_Gray() {
                return FileID_Gray;
            }

            public void setFileID_Gray(int FileID_Gray) {
                this.FileID_Gray = FileID_Gray;
            }

            public String getFilePath_Gray() {
                return FilePath_Gray;
            }

            public void setFilePath_Gray(String FilePath_Gray) {
                this.FilePath_Gray = FilePath_Gray;
            }
        }
    }
}
