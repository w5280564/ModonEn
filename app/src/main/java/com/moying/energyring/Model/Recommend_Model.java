package com.moying.energyring.Model;

import java.util.List;

/**
 * Created by waylen on 2017/6/5.
 */

public class Recommend_Model {


    /**
     * IsSuccess : true
     * Msg : 操作成功！
     * Status : 200
     * Data : [{"UserID":25,"NickName":"Chester","ProfilePicture":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEKcuN2yeicYAsDOTCAPg47wtOechgK1jcWD4WNFx1MHu7HWAzjtp1KroBtrjxnsmHTZWoNLpiaPjuXw/0","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":1,"ProjectName":"俯卧撑","Img_List":["http://120.26.218.68:8038/Photo/20170327/dfc7e35b-5c9d-43e4-b706-d4e54d40dae2.jpg","http://120.26.218.68:8038/Photo/20170302/6c6405d0-41c5-4aa9-88cc-d744ad152918.jpg","http://120.26.218.68:8038/Photo/20170124/2e06e60f-a53b-4694-8787-fe1fe882bdb4.jpg"]},{"UserID":50,"NickName":"摩英教育海哥","ProfilePicture":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDTr6U3SCHLyRmCJ4ib79CbdQbrYhWJBhKGticshXIXZqET5ZrKVtIJYOE5vh8o8KTN6uxuHA7bCmzA/0","Brief":null,"Is_Attention":true,"Is_Attention_Me":true,"ProjectID":1,"ProjectName":"俯卧撑","Img_List":["http://120.26.218.68:1111/Uploads/2018-06-12/23b221d8-cb77-41ac-a375-e8b6c72c6bf2.jpg","http://120.26.218.68:1111/Uploads/2018-06-12/1e50c345-979b-482d-b3e7-f1cbd2be5b50.jpg","http://120.26.218.68:1111/Uploads/2018-06-12/ced30b7c-e02c-4e9e-89f2-8d5fd8ef8302.jpg"]},{"UserID":77,"NickName":"摩英教育魏老师","ProfilePicture":"http://120.26.218.68:8038/Attachment/1644_164477.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":1,"ProjectName":"俯卧撑","Img_List":["http://120.26.218.68:1111/Uploads/2017-10-10/2ad9bfd7-a398-4d10-bfe9-73dbe4f96c1f.jpg","http://120.26.218.68:1111/Uploads/2017-10-09/7bc7f675-cb55-4035-b488-e5b01d7653a5.jpg","http://120.26.218.68:1111/Uploads/2017-07-16/1f5c27c9-4345-47b9-8d64-0dddb6b5884d.jpg"]},{"UserID":87,"NickName":"摩英教育宗夕容","ProfilePicture":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDjwQjC7xibt0tFcX1fWGKXyvKXBG3icqwVARiaeSPA5rhH0dGDibr908yXXYc3MpEL8U6XUc0mXKiaNgw/0","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":3,"ProjectName":"深蹲","Img_List":["http://120.26.218.68:8038/Photo/20170116/728faff7-3858-46ef-9d27-f248a4640409.jpg","http://120.26.218.68:8038/Photo/20170116/16c98802-c30a-44a2-8e41-a7fe56644abb.jpg","http://120.26.218.68:8038/Photo/20170108/c9f42e95-85f8-435c-9266-26689e6da65e.jpg"]},{"UserID":88,"NickName":"熊威Anson","ProfilePicture":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCa5tQF7CXKSs37l61J1I1jQB8vL9YvkNRmFg4a2ed7JhyYADEIauibZ0Vdv4ZoK8kpGN7fau9ct4A/0","Brief":null,"Is_Attention":true,"Is_Attention_Me":false,"ProjectID":3,"ProjectName":"深蹲","Img_List":["http://120.26.218.68:8038/Photo/20170519/5fef233c-93f1-4c4e-a49e-a10b1bd5af6a.jpg","http://120.26.218.68:8038/Photo/20170519/5be15f4e-749c-4933-974a-21f7522b0a55.jpg","http://120.26.218.68:8038/Photo/20170519/c61fb147-b847-4741-b5b0-b872f61cf16a.jpg"]},{"UserID":114,"NickName":"摩英教育Amy老师","ProfilePicture":"http://120.26.218.68:8038/Attachment/9848_9848114.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":3,"ProjectName":"深蹲","Img_List":["http://120.26.218.68:8038/Photo/20161231/3433c932-d816-4827-be1c-f2b3623acdca.jpg","http://120.26.218.68:8038/Photo/20161231/c7d75761-abcb-41eb-bc38-0eb37fa91dd8.jpg","http://120.26.218.68:8038/Photo/20161231/b37fe033-8e64-4c30-a995-52f3962b695a.jpg"]},{"UserID":161,"NickName":"摩英教育刘苏绒","ProfilePicture":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCEkx4sE63ibADxRvpOPSscLpBhaRfouOtPiaHyk3QUlcICbYLhJPNa6Q1lEiceWYKTMdp7iaY2APH0Aw/0","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":3,"ProjectName":"深蹲","Img_List":["http://120.26.218.68:8038/Photo/20170117/289d0650-fd3d-4527-91e4-4f07c260d49e.jpg","http://120.26.218.68:8038/Photo/20170117/1129fdb9-51be-468f-9ab0-9fed96b5fe38.jpg","http://120.26.218.68:8038/Photo/20170116/9eb9c534-1b7c-4662-985f-02c553726a1d.jpg"]},{"UserID":162,"NickName":"摩英教育楊尌安","ProfilePicture":"http://120.26.218.68:8038/Attachment/7179_7179162.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":50,"ProjectName":"早起","Img_List":["http://120.26.218.68:8038/Photo/20160623/2bddc7f4-9dec-4612-b2fc-fd730510e212.jpg","http://120.26.218.68:8038/Photo/20160623/4cf8f3ef-15ff-4c4d-bad9-87789d1222cc.jpg","http://120.26.218.68:8038/Photo/20160623/eadece84-f2c1-432c-88e6-6fb8995fb263.jpg"]},{"UserID":190,"NickName":"摩英教育许晶","ProfilePicture":"http://120.26.218.68:8038/Attachment/8965_8965190.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":0,"ProjectName":null,"Img_List":["http://120.26.218.68:8038/Photo/20160803/a44e766e-c638-4543-a463-f0b503c824ad.jpg","http://120.26.218.68:8038/Photo/20160803/6ab4f5f9-d0ee-4456-b0a6-437fb8d30a3b.jpg","http://120.26.218.68:8038/Photo/20160803/b5d56a35-bedb-4097-b929-a3d822126d87.jpg"]},{"UserID":394,"NickName":"陈金涵","ProfilePicture":"http://wx.qlogo.cn/mmopen/bbD9jy51jR0b1ZicXQtibw3xz8hS8tqp92cbYcLtxb5jjmTJhic3kPKEOaJx6PSaib9v2NBPuOKTeGASKlZtKJAavSamia6vHyh2D/0","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":50,"ProjectName":"早起","Img_List":["http://120.26.218.68:8038/Photo/20160612/16ac0ca2-7d37-4d76-a70e-352aaffae897.jpg","http://120.26.218.68:8038/Photo/20160606/e8aa47a9-03f8-43ab-9f57-14bb947800b7.jpg","http://120.26.218.68:8038/Photo/20160326/f93bd88d-cd62-4c8c-a0e1-bbe04a252b7c.jpg"]},{"UserID":396,"NickName":"媛子","ProfilePicture":"http://120.26.218.68:8038/Attachment/3612_3612396.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":3,"ProjectName":"深蹲","Img_List":["http://120.26.218.68:8038/Photo/20161222/955ea5a2-ac0d-465a-95f5-a3ab0ccde325.jpg","http://120.26.218.68:8038/Photo/20161222/9807a779-9deb-4b0b-8dba-aec96ac0ef67.jpg","http://120.26.218.68:8038/Photo/20161221/8d000f28-99da-4a08-994b-116cbdd87c84.jpg"]},{"UserID":415,"NickName":"摩英教育沈尔文","ProfilePicture":"http://wx.qlogo.cn/mmopen/aNHibhpdLicKNUuD4gY3uuSoYLjJElo7JKbmEibyyUibLQx1SLt0ibVeNRffmjfBL1q5jr3cO2Jich5icDlKmQX8KLlibjMicfWOQPQox/0","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":1,"ProjectName":"俯卧撑","Img_List":["http://120.26.218.68:8038/Photo/20160603/89e21856-77e7-447c-817d-619a60a22f24.jpg","http://120.26.218.68:8038/Photo/20160603/05b11360-2e9b-4456-b850-28cc18f8db07.jpg","http://120.26.218.68:8038/Photo/20160603/721985d9-6a92-4ddf-896b-64ab8d08a99e.jpg"]},{"UserID":453,"NickName":"摩英李云","ProfilePicture":"http://120.26.218.68:1111/Uploads/2018-01-26/a80f38e5-b9f5-43a0-a6fc-25b52f81454a.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":3,"ProjectName":"深蹲","Img_List":["http://120.26.218.68:8038/Photo/20170111/ff1b3d8f-35a1-46e2-91e4-dc0267ffdb1a.jpg","http://120.26.218.68:8038/Photo/20170107/aa583291-558d-400a-abc7-d15bfb74f9d6.jpg","http://120.26.218.68:8038/Photo/20170107/888f86fd-b8b5-4ad1-9009-f6791c5bc202.jpg"]},{"UserID":457,"NickName":"摩英教育余雅芳","ProfilePicture":"http://120.26.218.68:8038/Attachment/4688_4688457.jpg","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":50,"ProjectName":"早起","Img_List":["http://120.26.218.68:1111/Uploads/2017-10-13/e02b4f07-e2e2-43e0-80f9-f10251e61bfd.jpg","http://120.26.218.68:8038/Photo/20160908/79fc6884-76c9-4734-91df-439d24685621.jpg","http://120.26.218.68:8038/Photo/20160908/a7ab6021-bdd1-4d4d-a086-35670cf56a15.jpg"]},{"UserID":465,"NickName":"摩英教育Regina","ProfilePicture":"http://wx.qlogo.cn/mmopen/aNHibhpdLicKNUuD4gY3uuSpnFF4dNH7W50jBUBFStonOx8pszz65rwNbQOSaZomiaibY9eDeSSpOWiceQvEiaHxFnPjiazHQ9CcoicM/0","Brief":null,"Is_Attention":false,"Is_Attention_Me":false,"ProjectID":50,"ProjectName":"早起","Img_List":["http://120.26.218.68:8038/Photo/20161031/f880a8d0-a7a2-4ebd-a717-8133f8f43bcf.jpg","http://120.26.218.68:8038/Photo/20161016/cc6678d8-7b26-4eb1-9da0-b22700a46a0c.jpg","http://120.26.218.68:8038/Photo/20161016/7b2de03b-c910-4c95-8731-f096864ba7d4.jpg"]}]
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
         * UserID : 25
         * NickName : Chester
         * ProfilePicture : http://wx.qlogo.cn/mmopen/PiajxSqBRaEKcuN2yeicYAsDOTCAPg47wtOechgK1jcWD4WNFx1MHu7HWAzjtp1KroBtrjxnsmHTZWoNLpiaPjuXw/0
         * Brief : null
         * Is_Attention : false
         * Is_Attention_Me : false
         * ProjectID : 1
         * ProjectName : 俯卧撑
         * Img_List : ["http://120.26.218.68:8038/Photo/20170327/dfc7e35b-5c9d-43e4-b706-d4e54d40dae2.jpg","http://120.26.218.68:8038/Photo/20170302/6c6405d0-41c5-4aa9-88cc-d744ad152918.jpg","http://120.26.218.68:8038/Photo/20170124/2e06e60f-a53b-4694-8787-fe1fe882bdb4.jpg"]
         */

        private int UserID;
        private String NickName;
        private String ProfilePicture;
        private Object Brief;
        private boolean Is_Attention;
        private boolean Is_Attention_Me;
        private int ProjectID;
        private String ProjectName;
        private List<String> Img_List;
        private int IntegralLevel;

        public int getIntegralLevel() {
            return IntegralLevel;
        }

        public void setIntegralLevel(int integralLevel) {
            IntegralLevel = integralLevel;
        }


        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getProfilePicture() {
            return ProfilePicture;
        }

        public void setProfilePicture(String ProfilePicture) {
            this.ProfilePicture = ProfilePicture;
        }

        public Object getBrief() {
            return Brief;
        }

        public void setBrief(Object Brief) {
            this.Brief = Brief;
        }

        public boolean isIs_Attention() {
            return Is_Attention;
        }

        public void setIs_Attention(boolean Is_Attention) {
            this.Is_Attention = Is_Attention;
        }

        public boolean isIs_Attention_Me() {
            return Is_Attention_Me;
        }

        public void setIs_Attention_Me(boolean Is_Attention_Me) {
            this.Is_Attention_Me = Is_Attention_Me;
        }

        public int getProjectID() {
            return ProjectID;
        }

        public void setProjectID(int ProjectID) {
            this.ProjectID = ProjectID;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public List<String> getImg_List() {
            return Img_List;
        }

        public void setImg_List(List<String> Img_List) {
            this.Img_List = Img_List;
        }
    }
}
