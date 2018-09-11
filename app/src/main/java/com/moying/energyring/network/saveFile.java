package com.moying.energyring.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.google.gson.Gson;
import com.moying.energyring.Model.ProjectModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
public class saveFile {
//        public static String BaseUrl = "http://www.ec.dev.com/";
//    public static String BaseUrl = "http://172.16.0.222/";//本地
    public static String BaseUrl = "http://www.myjy.biz/";//域名
//    public static String BaseUrl = "http://120.26.218.68:1111/";
    public static String Post_Add_Url = "ec/v3/Post/Post_Add";

    public static String AddPk_Url = "ec/v4/PK/Report_Add";
    //    public static String AddPkV3_Url = "ec/v3/PK/Report_Add";
    public static String Report_Status_Url = "ec/v4/Post/Report_Post_Add";
    public static String EditInfo_Url = "ec/v2/User/UserInfo_Edit";

    //    public static String CodeUrl = "ec/Account/PhoneCode_Get";
    public static String CodeUrl = "ec/Account/SendPhoneVerificationCode";
    public static String LoginUrl = "ec/Account/Login";
    public static String EnergyListUrl = "ec/Post/Post_List";
    public static String pkUrl = "ec/PK/PK_Statistics_List";
    public static String rankUrl = "ec/PK/PK_LikesRank_List";
    public static String checkUrl = "ec/CheckIn/CheckIn_Early_List";
    public static String checMyDatakUrl = "ec/CheckIn/MyCheckIn_Get";
    public static String BadgeGuiZeUrl = "ec/Badge/MyBadge_List";
    public static String dayPkProjectUrl = "ec/PK/Project_List";
    public static String ReportRankUrl = "ec/PK/Report_Rank_List";
    public static String PersonData_Url = "ec/PK/MyReport_Get";
    public static String TargetDetails_ListUrl = "ec/Target/My_TargetDetails_List_Android";
    public static String Target_ListUrl = "ec/Target/My_Target_List";
    public static String Target_DetailsUrl = "ec/Target/Target_Details_Get";
    public static String Target_DelsUrl = "ec/Target/Target_Del";
    public static String Target_AddsUrl = "ec/Target/Target_ADD";
    public static String uploadPhoto_Url = "ec/File/File_Upload";
    public static String like_Url = "ec/PK/Likes_Add";
    public static String AddPkBg_Url = "ec/User/PKCoverImg_Upd";
    public static String Check_Url = "ec/CheckIn/CheckIn_IsExists";
    public static String CheckAdd_Url = "ec/v2/CheckIn/CheckIn_Add";
    public static String My_Friend_Url = "ec/User/My_Friend_List";
    public static String banner_Url = "ec/Banner/Banner_List";
    public static String seekUser_Url = "ec/User/Discover_UserInfo_List";
    public static String Recommend_User_Url = "ec/User/Recommend_User_List";
    public static String Friend_Add_User_Url = "ec/User/Friend_Add";
    public static String RadioList_Url = "ec/Radio/Radio_List";
    public static String UserInfo_Url = "ec/User/UserInfo_GetByUserID";
    public static String PkHistory_Url = "ec/PK/Report_List_UserID";
    public static String DayPk_Url = "ec/PK/Report_List_Today_UserID";
    public static String PersonBg_Url = "ec/User/User_CoverImg_Upd";
    public static String PersonHead_Url = "ec/User/User_ProfilePicture_Edit";
    public static String DelePost_Url = "ec/Post/Post_Del";
    public static String PersonRank_List_Url = "ec/User/Integral_Rank_List";
    public static String Product_List_Url = "ec/Product/Product_List";
    public static String Notice_Like_Url = "ec/Notice/Notice_Like_List";
    public static String Notice_Comment_Url = "ec/Notice/Notice_Comment_List";
    public static String Notice_Attention_Url = "ec/User/Attention_Me_List";
    public static String Notice_NoticeList_Url = "ec/Notice/Notice_List";
    public static String Notice_Unread_Url = "ec/Notice/Notice_NotRead_Num";
    public static String Product_Detrail_Url = "ec/Product/Product_Get";
    public static String shop_Adress_Url = "ec/Product/Exchange_Add";
    public static String Mess_UserList_Url = "ec/Message/Message_User_List";
    public static String MesList_Url = "ec/Message/Message_List";
    public static String SendMess_Url = "ec/Message/Message_Add";
    public static String Integral_Url = "ec/User/IntegralSource_List";
    public static String Attention_Url = "ec/User/My_Attention_List";
    public static String Fans_Url = "ec/User/Attention_Me_List";
    public static String AtMe_Url = "ec/Notice/Notice_MenTion_List";
    public static String PostLike_Url = "ec/Post/Likes_Add";

    public static String HistoryPk_Url = "ec/PK/Report_Sta_ByPID";
    public static String HistoryPk_List_Url = "ec/PK/Report_List_Record";
    public static String NoticeHasMes_Url = "ec/Notice/Notice_NotReadCount_Num";
    public static String Version_Url = "ec/SysConfig/Version_Last_Get";
    public static String Sys_Notice_Url = "ec/Notice/Sys_Notice_List";
    public static String DayPk_ProjectNotWalk = "ec/PK/Project_List_NotHaveWalk";
    public static String Tag_List_Url = "ec/Post/Post_Tag_List";
    public static String Phone_ChangeUrl = "ec/Account/Phone_Change";
    public static String MyCheckIn_Url = "ec/CheckIn/MyCheckIn_Get";
    public static String Likes_Url = "ec/user/Likes_Sta_Get";
    public static String GuidePerFirst_Url = "ec/user/FirstEnter_Get";
    public static String upd_guidePerFirst_Url = "ec/user/FirstEnter_Upd";
    public static String reportImg_Url = "ec/pk/Today_Report_Img_List";
    public static String haveNewRepost_Url = "ec/v3/post/Report_Post_IsHaveNewRepost";
    public static String badge_List_Url = "ec/badge/User_Badge_List";
    public static String user_hot_Url = "ec/user/User_Hot_List";
    public static String My_Rank_Url = "ec/User/MyIntegral_Ranking";
    public static String My_FriendRank_Url = "ec/User/Friend_Integral_Ranking_List";
    public static String My_ReportRank_Url = "ec/pk/My_Report_Ranking";
    public static String My_ReportRecordProject_Url = "ec/pk/Report_Record_Project_List";
    public static String My_ProjectType_Url = "ec/pk/ProjectType_List";
    public static String My_ProjectType_List_Url = "ec/PK/ProjectType_List";
    public static String My_ProjectList_Search_Url = "ec/PK/Project_List_Search";
    public static String My_Ranking_One_Url = "ec/pk/Report_Ranking_One_Get";
    public static String My_preoject_Dele_Url = "ec/pk/ReportProject_Del";
    public static String My_ReportProject_List_Url = "ec/pk/ReportProject_List";
    public static String Limit_like_Url = "ec/pk/Likes_Add_Limit_One";
    public static String Early_like_Url = "ec/pk/Likes_Add_Early";
    public static String Clock_Add_Url = "ec/pk/Project_Clock_Add";
    public static String Project_Clock_Data_Url = "ec/pk/Project_Clock_Get";
    public static String Project_Add_Url = "ec/pk/Project_Add";
    public static String Share_Project_Url = "ec/pk/Project_RankingNum_Get";
    public static String Invite_Bind_Url = "ec/user/Invite_Bind";

    public static String PK_Project_Get_Url = "ec/PK/Project_Get";
    public static String TrainAdd_Post_Url = "ec/Train/Pro_Train_Add";
    public static String TrainDetail_Get_Url = "ec/Train/Pro_Train_Get";
    public static String TrainDown_Get_Url = "ec/Train/Pro_Train_File_Get_ByTrainID";
    public static String Train_CommonFileList_Url = "ec/Train/Pro_Train_CommonFileList";
    public static String Train_FileList_Url = "ec/Train/Pro_Train_FileList";
    public static String Train_GroupUpd_Url = "ec/Train/Pro_Train_Group_Upd";
    public static String Train_Finish_Url = "ec/Train/Pro_Train_Finish";
    public static String Train_End_Url = "ec/Train/Pro_Train_End";
    public static String Person_Exchange_Url = "ec/Product/Exchange_List";
    public static String LoginName_IsHaveRefUser_Url = "ec/User/User_IsHaveRefUser_ByLoginName";
    public static String User_IsHaveRefUser_Url = "ec/User/User_IsHaveRefUser_ByUserID";
    public static String Phone_ChangeAndInvite_Url = "ec/Account/Phone_ChangeAndInvite_Bind";
    public static String InviteISHave_Url = "ec/User/Invite_Is_Exists";
    public static String Task_List_Url = "ec/Task/My_Task_List";
    public static String Task_Finish_Url = "ec/Task/Task_Finish_Share";
    public static String PhotoList_Url = "ec/User/Photo_Album";
    public static String TrainList_Url = "ec/Train/Pro_Train_List";
    public static String Report_Add_Url = "ec/Report/ReportInfo_Add";
    public static String Badge_List_Url = "ec/Badge/User_Insignia_List";
    public static String OtherBadge_List_Url = "ec/Badge/Insignia_List";
    public static String Sys_Notice_List_Url = "ec/Notice/Sys_Notice_List_New";
    public static String ProjectCommunity_List_Url = "ec/Community/Project_List_Hot";
    public static String ProjectCommunity_Seek_Url = "ec/Community/Project_List_New";
    public static String Community_Status_Url = "ec/Community/Community_Status";
    public static String Community_Post_List_Url = "ec/Community/Community_Post_List";
    public static String Community_Ranking_List_Url = "ec/Community/Community_Ranking_List";
    public static String Community_Likes_Add_Url = "ec/Community/Likes_Add";
    public static String Community_Privacy_Url = "ec/Community/Community_Privacy_Update";
    public static String Community_Del_Url = "ec/Community/Community_Del";
    public static String Community_Add_Url = "ec/Community/Community_Add";
    public static String My_Community_Post_List = "ec/Community/My_Community_Post_List";
    public static String AddressBook_List_Url = "ec/User/AddressBook_List";


    private static SharedPreferences mShared = null;
    /**
     * 程序中可以同时存在多个SharedPreferences数据， 根据SharedPreferences的名称就可以拿到对象
     **/
    public final static String SHARED_MAIN = "main";
    /**
     * SharedPreferences中储存数据的Key名称
     **/
    public final static String KEY_NAME = "name";
    public final static String KEY_NUMBER = "number";

    /**
     * SharedPreferences中储存数据的路径
     **/
    public final static String DATA_URL = "/data/test/";
    public final static String SHARED_MAIN_XML = "test.xml";

    public static void saveShareData(String keyname, String key, Context context) {
        mShared = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE);
        Editor editor = mShared.edit();
        editor.putString(keyname, key);
        /**put完毕必需要commit()否则无法保存**/
        editor.commit();
    }

    public static String getShareData(String keyname, Context context) {
        mShared = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE);
        String name = mShared.getString(keyname, "false");
        return name;
    }

    public static void clearShareData(String keyname, Context context) {
        mShared = context.getSharedPreferences(SHARED_MAIN, Context.MODE_PRIVATE);
        Editor editor = mShared.edit();
        editor.remove(keyname);
        editor.commit();
    }

    //存List<String>
    public static void saveUserList(Context context, List<String> baseArr, String KEY_NAME) {
//        baseArr = new ArrayList<>(new HashSet<>(baseArr));
        Editor editor = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.putInt("size", baseArr.size());
        for (int i = 0; i < baseArr.size(); i++) {
            editor.remove("seekname" + i);
            editor.putString("seekname" + i, baseArr.get(i));
        }
        editor.commit();
    }

    //取List<String>
    public static List<String> getUserList(Context context, String KEY_NAME) {
        List<String> baseArr = null;
        if (baseArr != null) {
            baseArr.clear();
        }
        baseArr = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        int size = sp.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            baseArr.add(sp.getString("seekname" + i, null));
        }
//        baseArr = new ArrayList<String>(new HashSet<String>(baseArr));
        return baseArr;
    }

    //删除一条订阅
    public static void removeUserOne(Context context, String KEY_NAME, String valen_Name) {
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        int size = sp.getInt("size", 0);
        List<String> baseArr = getUserList(context, "listselect");//
        for (int i = 0; i < size; i++) {//删除
            if (baseArr.get(i).equals(valen_Name)) {
                baseArr.remove(i);
//                size = size-1;//删除一个数据 size-1
                break;
            }
        }
        saveUserList(context, baseArr, "listselect");//保存
        editor.commit();
    }

    //添加一条订阅
    public static void addUserone(Context context, String KEY_NAME, String valen_Name) {
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        List<String> baseArr = getUserList(context, "listselect");
        baseArr.add(valen_Name);
        saveUserList(context, baseArr, "listselect");//保存
        editor.commit();
    }


    //保存map
    public static String SceneList2String(HashMap<Integer, Boolean> hashmap)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(hashmap);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<Integer, Boolean> String2SceneList(
            String SceneListString) throws StreamCorruptedException,
            IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        HashMap<Integer, Boolean> SceneList = (HashMap<Integer, Boolean>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }


    public static boolean putHashMap(HashMap<Integer, Boolean> hashmap, String key, Context context) {
        SharedPreferences settings = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        try {
            String liststr = SceneList2String(hashmap);
            editor.putString(key, liststr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editor.commit();
    }


    public static HashMap<Integer, Boolean> getHashMap(String key, Context context) {
        SharedPreferences settings = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        String liststr = settings.getString(key, "");
        try {
            return String2SceneList(liststr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * true在今天之前flase在之后
     *
     * @param str
     * @return
     */
    public static Boolean getBig(String str) {
        Boolean flag = null;
        try {
            Date nowdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            Date d = sdf.parse(str);
            if (d == nowdate) {
                flag = true;//日期相同
            } else {
                flag = d.before(nowdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //trues今天  其他flase
    public static Boolean getTimeBig(String str) {
        Boolean flag = null;
        try {
            Date nowdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date d = sdf.parse(str);

            String time = sdf.format(d);
            String timenow = sdf.format(nowdate);
            if (time.equals(timenow)) {
                flag = true;
            } else {
                flag = false;//日期相同
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是MMdd，注意字母y不能大写。
     *
     * @param sformat MMdd
     * @return
     */
    public static String getUserDate(String sformat) {
        String dateString = "2017-03-28";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sformat);//先转固定格式
            dateString = new SimpleDateFormat("MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 保存实体类
     *
     * @param
     */
    public static void putClass(Context context, String KEY_NAME, List<ProjectModel> model) {//需要实体类继承一个基类
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt("size", model.size());
        for (int i = 0; i < model.size(); i++) {
//            String key = model.getClass().getName() + i;
            String key = KEY_NAME + i;
            String value = new Gson().toJson(model.get(i));
            editor.putString(key, value);
        }
        editor.commit();
    }

    /**
     * 获取实体类
     *
     * @param
     * @param
     * @return
     */
    public static <T> List<T> getGosnClass(Context context, String KEY_NAME, Class<T> model) {
        List<T> listModel = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        int size = sp.getInt("size", 0);
        for (int i = 0; i < size; i++) {
//            String key = model.getName() + i;
            String key = KEY_NAME + i;
            String value = sp.getString(key, "");
            T t = new Gson().fromJson(value, model);
            listModel.add(t);
        }
        return listModel;
    }

    //删除一条实体类
    public static void removeGsonOne(Context context, String KEY_NAME, int valen_Id) {
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        List<ProjectModel> listModel = getGosnClass(context, KEY_NAME, ProjectModel.class);
        int size = sp.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            if (listModel.get(i).getProjectId() == valen_Id) {
                listModel.remove(i);//删除
                break;
            }
        }
        putClass(context, "moreModel", listModel);//保存
        editor.commit();
    }
    //保存一条实体类
    public static void saveGsonOne(Context context, String model_NAME, ProjectModel model) {
        SharedPreferences sp = context.getSharedPreferences(model_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        List<ProjectModel> listModel = getGosnClass(context, model_NAME, ProjectModel.class);

        listModel.add(model);
//        int size = sp.getInt("size", 0);
//        for (int i = 0; i < size; i++) {
//            if (listModel.get(i).getProjectId() == valen_Id) {
//                listModel.remove(i);//删除
//                break;
//            }
//        }
        putClass(context, model_NAME, listModel);//保存
        editor.commit();
    }

    public static void clearGson(Context context, String KEY_NAME) {
        SharedPreferences sp = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
